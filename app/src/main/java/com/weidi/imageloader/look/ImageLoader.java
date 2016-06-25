package com.xianglin.station.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 加载图片
 * 
 * @author huangyang
 *
 */
public class ImageLoader {

	private static ImageLoader mImageLoaderInstance = new ImageLoader();
	private LruCache<String, Bitmap> mLruCache;
	private DownLoadImageFromInternet mDownLoadImageFromIntenet;

	private ImageLoader() {
		// 获取到可用的内存最大值，使用内存超出这个值会引起OutOfMemory异常。
		int cacheSize = getCacheSize();
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap bitMap) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量
				return (bitMap.getRowBytes() * bitMap.getHeight()) / 1024;
			}
		};
	}

	public static ImageLoader getInstance() {
		return mImageLoaderInstance;
	}

	/**
	 * 添加BitMap到内存
	 * 
	 * @param key
	 * @param bitMap
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitMap) {
		if (getBitmapFormMemoryCache(key) == null)
			mLruCache.put(key, bitMap);
	}

	/**
	 * 通过key从内存中获取BitMap
	 * 
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFormMemoryCache(String key) {
		return mLruCache.get(key);
	}

	/**
	 * 获取缓存大小
	 * 
	 * @return
	 */
	public int getCacheSize() {
		// 获取到可用的内存最大值，使用内存超出这个值会引起OutOfMemory异常。
		// LruCache通过构造函数传入值，以kb为单位
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int cacheSize = maxMemory / 8;
		return cacheSize;
	}

	/**
	 * 获取LruCache集合
	 * 
	 * @return
	 */
	public LruCache<String, Bitmap> getLruCache() {
		if (mLruCache == null)
			mLruCache = new LruCache<String, Bitmap>(getCacheSize());
		return mLruCache;
	}

	/**
	 * 加载图片 1.加载内存中的图片 2.加载本地图片 3.加载网络图片
	 * 
	 * @param context
	 * @param imageUrl
	 * @param imageView
	 */
	public void loadImage(String imageUrl, ImageView imageView) {
		if (TextUtils.isEmpty(imageUrl) || imageView == null)
			return;
		String fileName = FileOperateUtils.getFileName(imageUrl);
		// 判断缓存中是否存在图片资源
		if (mLruCache.get(fileName) != null) {
			imageView.setImageBitmap(mLruCache.get(fileName));
			return;
		} else {
			// 判断本地是否有图片资源
			String imgPath = FileOperateUtils.getAppImageCachePath() + fileName;
			if (FileOperateUtils.isExist(imgPath)) {
				Bitmap bitmap = BitmapUtils.loadImage(imgPath);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				}
			} else {
				// 网络加载图片资源
				if (mDownLoadImageFromIntenet != null)
					mDownLoadImageFromIntenet.postDwonLoadImage(imageUrl, 0, imageView);
			}
		}
	}

	/**
	 * 加载图片 1.加载内存中的图片 2.加载本地图片
	 * 
	 * @param path
	 * @param fileName
	 * @param id
	 * @param imageView
	 * @param scale
	 */
	public void loadImage(String path, String fileName, long id, ImageView imageView, float scale) {
		if (TextUtils.isEmpty(path) || imageView == null || fileName == null)
			return;
		// 判断缓存中是否存在图片资源
		if (mLruCache.get(fileName) != null) {
			imageView.setImageBitmap(BitmapUtils.scaleBitmap(mLruCache.get(fileName), scale));
			return;
		} else {
			// 判断本地是否有图片资源
			String imgPath = path + fileName;
			if (FileOperateUtils.isExist(imgPath)) {
				Bitmap bitmap = BitmapUtils.loadImage(imgPath);
				if (bitmap != null) {
					imageView.setImageBitmap(BitmapUtils.scaleBitmap(bitmap, scale));
				}
			} else {
				// 从网络中加载图片
				if (mDownLoadImageFromIntenet != null)
					mDownLoadImageFromIntenet.postDwonLoadImage(path, id, imageView);
			}
		}
	}

	/**
	 * 加载图片 1.加载内存中的图片 2.加载本地图片
	 * 
	 * @param path
	 * @param fileName
	 * @param id
	 * @param imageView
	 * @param scale
	 */
	public void loadCertificateImage(String path, long id, ImageView imageView1, ImageView imageView2, boolean isEnableInternet) {
		if (TextUtils.isEmpty(path) || imageView1 == null || imageView2 == null)
			return;
		final String fileName1 = id + "_0";
		final String fileName2 = id + "_1";
		boolean hasLoadImageView1 = false;
		boolean hasLoadImageView2 = false;
		// 判断缓存中是否存在图片资源
		if (mLruCache.get(fileName1) != null) {
			imageView1.setImageBitmap(BitmapUtils.scaleBitmap(mLruCache.get(fileName1), 0.8f));
			hasLoadImageView1 = true;
		}
		if (mLruCache.get(fileName2) != null) {
			imageView2.setImageBitmap(BitmapUtils.scaleBitmap(mLruCache.get(fileName2), 0.8f));
			hasLoadImageView2 = true;
		}
		// 从本地加载
		if (!(hasLoadImageView1 & hasLoadImageView2)) {
			hasLoadImageView1 = false;
			hasLoadImageView2 = false;
			// 判断本地是否有图片资源
			String imgPath1 = path + fileName1;
			String imgPath2 = path + fileName2;
			if (FileOperateUtils.isExist(imgPath1)) {
				Bitmap bitmap1 = BitmapUtils.loadImage(imgPath1);
				if (bitmap1 != null) {
					imageView1.setImageBitmap(BitmapUtils.scaleBitmap(bitmap1, 0.8f));
					hasLoadImageView1 = true;
				}
			}
			if (FileOperateUtils.isExist(imgPath2)) {
				Bitmap bitmap2 = BitmapUtils.loadImage(imgPath2);
				if (bitmap2 != null) {
					imageView2.setImageBitmap(BitmapUtils.scaleBitmap(bitmap2, 0.8f));
					hasLoadImageView2 = true;
				}
			}
		}
		// 从网络中加载
		if (!(hasLoadImageView1 & hasLoadImageView2)) {
			if (isEnableInternet) {
				// 从网络中加载图片
				if (mDownLoadImageFromIntenet != null)
					mDownLoadImageFromIntenet.postDwonLoadImage(path, id, null);
			}
		}
	}

	/**
	 * 保存文件到缓存
	 * 
	 * @param byteArray
	 * @param fileName
	 * @param path
	 */
	public void saveImageToCache(byte[] byteArray, String fileName, String path) {
		if (byteArray == null || fileName == null || path == null)
			return;
		if (byteArray.length <= 0)
			return;
		Bitmap bitmap = BitmapUtils.byteArrayToBitmap(byteArray);
		if (bitmap != null)
			return;
		long freeSize = FileOperateUtils.getSdCardFreeSize();
		long bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
		// 判断缓存中是否存在图片资源
		if (mLruCache.get(fileName) == null) {
			mLruCache.put(fileName, bitmap);
		}
		// 将图片存储在本地磁盘
		// 判断sd卡是否可用
		if (FileOperateUtils.isSdCardMounted()) {
			// 判断磁盘剩余空间是否足够容纳图片大小
			if (freeSize < bitmapSize)
				return;
			FileOperateUtils.saveByteToFile(byteArray, path, fileName);
		}
	}

	/**
	 * 注册网络下载监听事件
	 * 
	 * @param dft
	 */
	public void setOnDownLoadImageFormInternetListener(DownLoadImageFromInternet dft) {
		this.mDownLoadImageFromIntenet = dft;
	}

	public interface DownLoadImageFromInternet {
		void postDwonLoadImage(String imageUrl, long id, ImageView imageView);
	}

}
