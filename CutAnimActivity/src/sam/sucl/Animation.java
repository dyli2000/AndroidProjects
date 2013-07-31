package sam.sucl;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//
public class Animation 
{
    /** 上一帧播放时间 **/
    private long mLastPlayTime = 0;
    /** 播放当前帧的ID **/
    private int mPlayID = 0;
    /** 动画frame数量 **/
    private int mFrameCount = 0;
    /** 用于储存动画资源图片 **/
    private Bitmap[] mframeBitmap = null;
    /** 是否循环播放 **/
    private boolean mIsLoop = false;
    /** 播放结束 **/
    private boolean mIsend = false;
    /** 动画播放间隙时间 **/
    private static final int ANIM_TIME = 1;
    
    /** *************************************************************************************************
     *       DESC :     构造函数-1，本工程暂时使用这个构造函数。
     *       ARGC :
     *                      @context:       
     *                      @frameBitmapID:       
     *                      @isloop:   
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/    
    public Animation(Context context, int [] frameBitmapID, boolean isloop) 
    {
		mFrameCount = frameBitmapID.length;
		mframeBitmap = new Bitmap[mFrameCount];
		
		for(int i =0; i < mFrameCount; i++) 
		{
		    mframeBitmap[i] = ReadBitMap(context,frameBitmapID[i]);
		}
		mIsLoop = isloop;
    }
    
    /** *************************************************************************************************
     *       DESC :     构造函数-2.
     *       ARGC :
     *                      @context:       
     *                      @frameBitmapID:       
     *                      @isloop:   
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/        
    public Animation(Context context, Bitmap [] frameBitmap, boolean isloop) 
    {
		mFrameCount = frameBitmap.length;
		mframeBitmap = frameBitmap;
		mIsLoop = isloop;
    }
    
   /**
    * 绘制动画中的其中一帧
    * @param Canvas
    * @param paint
    * @param x
    * @param y
    * @param frameID
    */
    public void DrawFrame(Canvas Canvas, Paint paint, int x, int y,int frameID) 
    {
    	Canvas.drawBitmap(mframeBitmap[frameID], x, y, paint);
    }
    

    
    /** *************************************************************************************************
     *       DESC :     绘制动画.
     *       ARGC :
     *                      @Canvas:       
     *                      @paint:       
     *                      @pointX:   
     *                      @pointY:
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/        
    public void DrawAnimation(Canvas Canvas, Paint paint, int pointX, int pointY) 
    {    	
		//如果没有播放结束则继续播放
		if (!mIsend) 
		{
		    Canvas.drawBitmap(mframeBitmap[mPlayID], pointX, pointY, paint);
		    long time = System.currentTimeMillis();
		    
		    if (time - mLastPlayTime > ANIM_TIME) 
		    {
				mPlayID++;
				mLastPlayTime = time;
			
				if (mPlayID >= mFrameCount) 
				{
				    //标志动画播放结束
				    mIsend = true;
				    if (mIsLoop) 
				    {
						//设置循环播放
						mIsend = false;
						mPlayID = 0;
				    }
				}
		    }
		}// 	if(!mIsend)
    }
    
    /** *************************************************************************************************
     *       DESC :     读取图片资源
     *       ARGC :
     *                      @context:       
     *                      @resId:       
     *          RET:		Bitmap对象
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/      
    public Bitmap ReadBitMap(Context context, int resId) 
    {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
    }
}
