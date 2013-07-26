package sam.sucl;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ImageButton 
{
    /**��ťͼƬ**/
    private Bitmap mBitButton = null;
    /**ͼƬ���Ƶ�XY����**/
    private int mPosX =0;
    private int mPosY =0;

    /**ͼƬ���ƵĿ��**/
    private int mWidth =0;
    private int mHeight =0;
  
    /** *************************************************************************************************
     *       DESC :      ���캯��������һ��BitMap
     *       ARGC :
     *                      @context:        
     *                      @frameBitmapID:
     *                      @pointX
     *                      @pointY        
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/         
    public ImageButton(Context context, int frameBitmapID, int pointX, int pointY) 
    {
		mBitButton = ReadBitMap(context,frameBitmapID);
		mPosX = pointX;
		mPosY = pointY;
		mWidth = mBitButton.getWidth();
		mHeight = mBitButton.getHeight();
    }


    /** *************************************************************************************************
     *       DESC :      ����ͼƬ��ť
     *       ARGC :
     *                      @canvas:        ��ͼ��һ��������
     *                      @paint:          ��ͼ����
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/          
    public void DrawImageButton(Canvas canvas, Paint paint) 
    {
    	canvas.drawBitmap(mBitButton, mPosX, mPosY, paint);
    }
    
    /** *************************************************************************************************
     *       DESC :      �ж��Ƿ����ͼƬ��ť
     *       ARGC :
     *                      @pointX:        X����
     *                      @pointY:        Y����
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/            
    public boolean IsClick(int pointX, int pointY) 
    {
		boolean isClick = false;
		if (pointX >= mPosX && pointX <= mPosX + mWidth &&
		    pointY >= mPosY && pointY <= mPosY + mHeight) 
		{
		    isClick = true;
		}
		return isClick;
    }
    
    /** *************************************************************************************************
     *       DESC :      ��ȡͼƬ��Դ
     *       ARGC :
     *                      @context:      ������
     *                      @resId:           ˭��ID��
     *          RET:		����һ��ͼƬ����	            
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/        
    public Bitmap ReadBitMap(Context context, int resId) 
    {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
    }
}
