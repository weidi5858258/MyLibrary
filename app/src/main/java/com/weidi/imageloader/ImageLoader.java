package com.weidi.imageloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ImageView;

import com.weidi.threadpool.CustomRunnable;
import com.weidi.threadpool.ThreadPool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1、图片的同步加载
 * 2、图片的异步加载
 * 3、图片压缩
 * 4、内存缓存
 * 5、磁盘缓存
 * 6、网络请求
 */
public class ImageLoader {

	private static final String TAG = "ImageLoader";

	public static final int MESSAGE_POST_RESULT = 1;

	// CPU核心数
//	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
//	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
//	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
//	private static final long KEEP_ALIVE = 10L;

	private static final int TAG_KEY_URI = 8888;
	//	private static final int TAG_KEY_URI = R.id.imageloader_uri;
	// 50MB容量用于缓存图片
	private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
	// IO流的缓存大小
	private static final int IO_BUFFER_SIZE = 1024 * 8;
	private static final int DISK_CACHE_INDEX = 0;
	private boolean mIsDiskLruCacheCreated = false;

	/**
	 * 创建对象
	 */
	public static ImageLoader build(Context context) {
		return new ImageLoader(context);
	}

	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mAtomicInteger = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			return new Thread(r, "ImageLoader#" + mAtomicInteger.getAndIncrement());
		}
	};

	// 创建一个线程池
	/*public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
			CORE_POOL_SIZE,
			MAXIMUM_POOL_SIZE,
			KEEP_ALIVE,
			TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(),
			sThreadFactory);*/

	private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			LoaderResult result = (LoaderResult) msg.obj;
			if (null == result) {
				return;
			}
			ImageView imageView = result.imageView;
			String uri = (String) imageView.getTag(TAG_KEY_URI);
			if (uri.equals(result.uri)) {
				imageView.setImageBitmap(result.bitmap);
			} else {
				Log.w(TAG, "set image bitmap,but url has changed, ignored!");
			}
		}
	};

	private Context mContext;
	private ImageResizer mImageResizer = new ImageResizer();
	// 内存缓存类
	private LruCache<String, Bitmap> mMemoryCache;
	// 磁盘缓存类
	private DiskLruCache mDiskLruCache;

	private ImageLoader(Context context) {
		// 用应用的上下文，防止内存泄漏
		mContext = context.getApplicationContext();
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int cacheSize = maxMemory / 8;
		// 创建内存缓存对象
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
		};
		// 对于经常要用到的图片格式可以用不同的目录进行缓存，如头像、点击头像后的大图
		File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
		if (!diskCacheDir.exists()) {
			diskCacheDir.mkdirs();
		}
		if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
			try {
				// 创建磁盘缓存对象
				mDiskLruCache = DiskLruCache.open(
						diskCacheDir,
						1,
						1,
						DISK_CACHE_SIZE);
				mIsDiskLruCacheCreated = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把bitmap添加到内存缓存中
	 *
	 * @param key
	 * @param bitmap
	 */
	private void putBitmapToMemoryCache(
			String key,
			Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从内存缓存中根据key去取bitmap
	 *
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFromMemCache(String url) {
		final String key = createHashKeyWithUrl(url);
		return mMemoryCache.get(key);
	}

	/**
	 * 在其他地方只要调用这个方法就行了
	 */
	public void bindBitmap(final String uri, final ImageView imageView) {
		bindBitmap(uri, imageView, 0, 0);
	}

	/**
	 * reqWidth、reqHeight的单位是px（像素）
	 */
	public void bindBitmap(
			final String uri,
			final ImageView imageView,
			final int reqWidth,
			final int reqHeight) {
		// 先给这个ImageView对象设置一个Tag
		imageView.setTag(TAG_KEY_URI, uri);
		Bitmap bitmap = getBitmapFromMemCache(uri);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		}

		ThreadPool.getCachedThreadPool().execute(
				new CustomRunnable().setCallBack(
						new CustomRunnable.CallBack() {
							@Override
							public void runBefore() {

							}

							@Override
							public Object running() {
								Bitmap bitmap = getBitmap(uri, reqWidth, reqHeight);
								if (bitmap != null) {
									LoaderResult result = new LoaderResult(imageView, uri, bitmap);
									mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
								}
								return null;
							}

							@Override
							public void runAfter(Object object) {

							}
						}));
		// 从磁盘缓存或者网络请求去取bitmap都要开启子线程去操作
		/*Runnable loadBitmapTask = new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = getBitmap(uri, reqWidth, reqHeight);
				if (bitmap != null) {
					LoaderResult result = new LoaderResult(imageView, uri, bitmap);
					mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
				}
			}
		};
		THREAD_POOL_EXECUTOR.execute(loadBitmapTask);*/
	}

	/**
	 *
	 */
	public Bitmap getBitmap(String uri,
							int reqWidth,
							int reqHeight) {
		Bitmap bitmap = null;
//		Bitmap bitmap = getBitmapFromMemCache(uri);
//		if (bitmap != null) {
//			CustomLog.d(TAG, "loadBitmapFromMemCache,url: " + uri);
//			return bitmap;
//		}

		try {
			bitmap = getBitmapFromDiskCache(uri, reqWidth, reqHeight);
			if (bitmap != null) {
				Log.d(TAG, "getBitmapFromDiskCache,url: " + uri);
				return bitmap;
			}
			bitmap = getBitmapFromNetwork(uri, reqWidth, reqHeight);
			Log.d(TAG, "getBitmapFromNetwork,url:" + uri);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bitmap == null && !mIsDiskLruCacheCreated) {
			Log.w(TAG, "encounter error, DiskLruCache is not created.");
			bitmap = downloadBitmapFromUrl(uri);
		}

		return bitmap;
	}

	/*private Bitmap loadBitmapFromMemCache(String url) {
		final String key = hashKeyFormUrl(url);
		Bitmap bitmap = getBitmapFromMemCache(key);
		return bitmap;
	}*/

	private Bitmap getBitmapFromNetwork(
			String url,
			int reqWidth,
			int reqHeight)
			throws IOException {
		// 如果在主线程中
		if (Looper.myLooper() == Looper.getMainLooper()) {
			throw new RuntimeException("Can not visit network from UI Thread.");
		}
		if (mDiskLruCache == null) {
			return null;
		}

		String key = createHashKeyWithUrl(url);
		DiskLruCache.Editor editor = mDiskLruCache.edit(key);
		if (editor != null) {
			OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
			if (downloadUrlToStream(url, outputStream)) {
				editor.commit();
			} else {
				editor.abort();
			}
			mDiskLruCache.flush();
		}
		// bitmap已经存到了磁盘里
		return getBitmapFromDiskCache(
				url,
				reqWidth,
				reqHeight);
	}

	private Bitmap getBitmapFromDiskCache(
			String url,
			int reqWidth,
			int reqHeight)
			throws IOException {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			Log.w(TAG, "load bitmap from UI Thread, it's not recommended!");
		}
		if (mDiskLruCache == null) {
			return null;
		}

		Bitmap bitmap = null;
		String key = createHashKeyWithUrl(url);
		DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
		if (snapShot != null) {
			FileInputStream fileInputStream = (FileInputStream) snapShot.getInputStream(DISK_CACHE_INDEX);
			FileDescriptor fileDescriptor = fileInputStream.getFD();
			// 对磁盘中保存的bitmap作处理，然后存到内存缓存中
			bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(
					fileDescriptor,
					reqWidth,
					reqHeight);
			if (bitmap != null) {
				// 加到内存中去
				putBitmapToMemoryCache(key, bitmap);
			}
		}

		return bitmap;
	}

	/**
	 * 从网络下载bitmap
	 * 此时的bitmap是没有经过处理的，如果直接显示的话可能会OOM异常。
	 *
	 * @param urlString
	 * @param outputStream
	 * @return
	 */
	public boolean downloadUrlToStream(
			String urlString,
			OutputStream outputStream) {
		HttpURLConnection urlConnection = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
			out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

			int read;
			while ((read = in.read()) != -1) {
				out.write(read);
			}
			out.flush();
			// 下载成功
			return true;
		} catch (IOException e) {
			Log.e(TAG, "downloadBitmap failed: " + e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
				urlConnection = null;
			}
			try {
				if (out != null) {
					out.close();
					out = null;
				}
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				Log.e(TAG, "Error in close: " + e);
			}
		}
		return false;
	}

	private Bitmap downloadBitmapFromUrl(String urlString) {
		Bitmap bitmap = null;
		HttpURLConnection urlConnection = null;
		BufferedInputStream in = null;

		try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
			bitmap = BitmapFactory.decodeStream(in);
		} catch (final IOException e) {
			Log.e(TAG, "Error in downloadBitmap: " + e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
				urlConnection = null;
			}
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					Log.e(TAG, "Error in close: " + e);
				}
			}
		}
		return bitmap;
	}

	/**
	 * 用url地址生成一个hash值作为key
	 * 建议：在生成java bean时就把这个url转化成key，这样可以在滑动过程中提高效率
	 *
	 * @param url
	 * @return
	 */
	private String createHashKeyWithUrl(String url) {
		String cacheKey = "";
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(url.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(url.hashCode());
		}
		return cacheKey;
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * 磁盘缓存目录
	 *
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	public File getDiskCacheDir(Context context, String uniqueName) {
		// 判断外部磁盘是否已挂载
		boolean externalStorageAvailable = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);
		// 还需要判断一下外部磁盘的剩余容量是否足够

		// /data/data/packagename/cache
		String cachePath = context.getCacheDir().getPath();
		if (externalStorageAvailable) {
			// /SDCard/Android/data/packagename/cache
			cachePath = context.getExternalCacheDir().getPath();
		}

		return new File(cachePath + File.separator + uniqueName);
	}

	private void getExternalStorageDirectoryAvailableSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = 0;
		long totalBlocks = 0;
		long availableBlocks = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			blockSize = stat.getBlockSizeLong();
			totalBlocks = stat.getBlockCountLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			totalBlocks = stat.getBlockCount();
			availableBlocks = stat.getAvailableBlocks();
		}
		// 利用formatSize函数把字节转换为用户等看懂的大小数值单位
		String totalText = Formatter.formatFileSize(mContext, blockSize * totalBlocks);
		String availableText = Formatter.formatFileSize(mContext, blockSize * availableBlocks);
	}

	@TargetApi(VERSION_CODES.GINGERBREAD)
	private long getUsableSpace(File path) {
		if (Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			return path.getUsableSpace();
		}
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}

	private static class LoaderResult {
		public ImageView imageView;
		public String uri;
		public Bitmap bitmap;

		public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
			this.imageView = imageView;
			this.uri = uri;
			this.bitmap = bitmap;
		}
	}
}
