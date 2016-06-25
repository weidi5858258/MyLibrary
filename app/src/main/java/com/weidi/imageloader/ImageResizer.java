package com.weidi.imageloader;

import java.io.FileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public ImageResizer() {
    }

    public Bitmap decodeSampledBitmapFromResource(
    		Resources res,
            int resId, 
            int reqWidth, 
            int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(
        		options, 
        		reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public int calculateInSampleSize(
    		BitmapFactory.Options options,
            int reqWidth, 
            int reqHeight) {
    	int inSampleSize = 1;
        if (reqWidth == 0 || reqHeight == 0) {
            return inSampleSize;
        }

        // Raw height and width of image
        final int width = options.outWidth;
        final int height = options.outHeight;
        Log.d(TAG, "origin, w= " + width + " h=" + height);

        // 如果要解析的图片宽高大于所需要的宽高，那么需要缩放
        if (width > reqWidth || height > reqHeight) {
        	final int halfWidth = width / 2;
            final int halfHeight = height / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfWidth / inSampleSize) >= reqWidth && 
            		(halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }
    
    public Bitmap decodeSampledBitmapFromFileDescriptor( 
    		FileDescriptor fd, 
    		int reqWidth, 
    		int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(
        		options, 
        		reqWidth, 
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }
}
