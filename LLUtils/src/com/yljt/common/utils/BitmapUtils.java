package com.yljt.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView.ScaleType;
/**
 * 图片操作工具类
 * @author ws
 *
 */
public class BitmapUtils
{
	private BitmapUtils()
	{
	}

	/**
	 * 缩放读取磁盘上的文件
	 * @param filename
	 * @param reqWidth
	 * @param reqHeight
	 * @param bitmapConfig 图片参数设置
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight,Bitmap.Config bitmapConfig)
	{
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		if(bitmapConfig != null)
		{
			options.inPreferredConfig = bitmapConfig;
		}
		BitmapFactory.decodeFile(filename, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		try
		{
			return BitmapFactory.decodeFile(filename, options);
		} 
		catch (OutOfMemoryError e)
		{
			Log.e(BaseUtils.TOOL_LOG,"读取磁盘上的图片异常------>"+e.getMessage());
		}
		return null;
	}
	/**
	 * 计算需要缩放的尺寸
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{
			if (width > height)
			{
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else
			{
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}

			final float totalPixels = width * height;

			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
			{
				inSampleSize++;
			}
		}
		return inSampleSize;
	}
	/**
	 * 缩放图片
	 * @param bitmap 原图片
	 * @param w	宽
	 * @param h	高
	 * @param scaleType 缩放类型
	 * @return
	 */
	public static Bitmap getScaledBitmap(Bitmap bitmap,int w, int h, ScaleType scaleType)
	{
		Rect srcRect = calculateSrcRect(bitmap.getWidth(),bitmap.getHeight(), w, h, scaleType);
		Rect dstRect = calculateDstRect(bitmap.getWidth(),bitmap.getHeight(), w, h, scaleType);
		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(),dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.drawBitmap(bitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
		return scaledBitmap;
	}

	/**
	 * 根据dstWOrH计算原图应该截取的截图合适的高宽比例图
	 * @param srcWidth
	 * @param srcHeight
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic
	 * @return
	 */
	private static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScaleType scalingLogic)
	{
		if (scalingLogic == ScaleType.CENTER_CROP)
		{
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect)
			{
				final int srcRectWidth = (int) (srcHeight * dstAspect);
				final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
				return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
			} 
			else
			{
				final int srcRectHeight = (int) (srcWidth / dstAspect);
				final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
				return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
			}
		} 
		return new Rect(0, 0, srcWidth, srcHeight);
	}

	/**
	 * 根据dstWOrH计算原图应该截取的期望图合适的高宽比例图
	 * @param srcWidth
	 * @param srcHeight
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic
	 * @return
	 */
	private static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScaleType scalingLogic)
	{
		if (scalingLogic == ScaleType.FIT_XY)
		{
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect)
			{
				return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
			} 
			return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
		}
		return new Rect(0, 0, dstWidth, dstHeight);
	}



	/**
	 * 比例缩放
	 * @param unscaledBitmap
	 * @param scale
	 * @param scalingLogic
	 * @return
	 */
	public static Bitmap getScaledBitmap(Bitmap unscaledBitmap, Float scale, ScaleType scalingLogic)
	{
		int dstWidth = (int) (unscaledBitmap.getWidth() * scale);
		int dstHeight = (int) (unscaledBitmap.getHeight() * scale);
		return getScaledBitmap(unscaledBitmap, dstWidth, dstHeight, scalingLogic);
	}
	/**
	 * 把图片处理成圆角图片
	 * 
	 * @param bitmap
	 * @param angle
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int angle)
	{
		if (bitmap == null) return null;
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = angle;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	/**
	 * 合成图片
	 * @param firstBitmap
	 * @param secondBitmap
	 * @return
	 */
	public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap)
	{
		Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(),firstBitmap.getHeight(), firstBitmap.getConfig());
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(firstBitmap, new Matrix(), null);
		canvas.drawBitmap(secondBitmap, (firstBitmap.getWidth()-secondBitmap.getWidth())/2, (firstBitmap.getHeight()-secondBitmap.getHeight())/2, null);
		return bitmap;
	}
	/**
	 * 图片转化成字节
	 * @param bitmap
	 * @return
	 */
	public static byte[] getByteArr(Bitmap bitmap)
	{
		if(bitmap == null)
		{
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	/**
	 * Write By LILIN
	 * 2014-6-30
	 * @param srcPath   图片路径
	 * @param standardW 标准压缩宽度
	 * @param standardH 标准压缩高度
	 * @return
	 */
	public static Bitmap readCompressBitmap(String srcPath,float standardW,float standardH) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
		
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		LG.e(BitmapUtils.class, "图片压缩前尺寸大小----->"+w+" X "+h);
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = standardH;//这里设置高度
		float ww = standardW;//这里设置宽度
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		LG.e(BitmapUtils.class, "图片压缩倍数----------->"+be);
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;//压缩好比例大小后再进行质量压缩
	}
	/**
	 * 从SD卡读取图片 以优化的方式读取，以防止OOM
	 * 
	 * @author LILINQ
	 * @param path
	 *            图片本地的地址
	 * @return
	 */
	public static Bitmap readBitMap(String path) {
		File f = new File(path);
		int size = (int) f.length();
		int times = 2;
		if (size < 100000) {
			times = 1;
		} else if (100000 < size && size < 500000) {
			times = 2;
		} else if (500000 < size && size < 1500000) {
			times = 3;
		} else if (1500000 < size && size < 2500000) {
			times = 4;
		} else if (2500000 < size && size < 4000000) {
			times = 5;
		} else {
			times = 6;
		}
		System.out.print("压缩倍数---->" + times);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		options.inSampleSize = times;
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		return bm;
	}
	/**
	 * 保存图片
	 * 
	 * @param bmp
	 * @param name
	 * @return
	 */
	public static String SaveBitmap(Bitmap bmp,String IMAGE_PATH, String name) {
		File file = new File(IMAGE_PATH + "");
		String path = null;
		if (!file.exists())
			file.mkdirs();
		try {
			path = file.getPath() + "/" + name;
			FileOutputStream fileOutputStream = new FileOutputStream(path);

			bmp.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return path;
	}
}
