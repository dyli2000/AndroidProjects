package sam.sucl;

import java.io.InputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
  
    /* *************************************************************************************************
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
    
    
    /* *************************************************************************************************
     *       DESC :      ����ͼƬ��ť
     *       ARGC :
     *                      @canvas:        ��ͼ��һ��������
     *                      @paint:           ��ͼ����
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/          
    public void DrawImageButton(Canvas canvas, Paint paint) 
    {
    	canvas.drawBitmap(mBitButton, mPosX, mPosY, paint);
    }

    /* *************************************************************************************************
     *       DESC :    ͨ����������ת��ʵ�����ֵ���ʾЧ��
     *       ARGC :
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/  
    void drawText(Canvas canvas ,String text , float x ,float y,Paint paint ,float angle)
    {
    	if(angle != 0)
        {
        	 /* �Ի�����ת���ٻ��ı� */
            canvas.rotate(angle, x, y);   
        }
        canvas.drawText(text, x, y, paint);
        
        /*  ʹ�û����ֳ�ʼ״���Ա㻭��һ������ʱ�ٽ�����ת*/
        if(angle != 0)
        {
            canvas.rotate(-angle, x, y); 
        }
    }
    
    public void DrawSubjectAndAnswer(Canvas canvas,Paint paint,GlobalData subject1,GlobalData subject2)
    {
    	try
    	{
	        Paint painter = new Paint();                
	        painter.setColor(Color.WHITE);
	        painter.setTextSize(20);                
	        canvas.drawLine(100, 100, 100, 400, painter);
	        drawText(canvas,subject1.MSubjectBody, 20, 200, painter,-90);
	        
	        painter.setColor(Color.RED);
	        painter.setTextSize(40);
	        drawText(canvas,subject1.MAnswer, 40, 180, painter,-90);
	        
	        paint.setColor(Color.BLUE);
	        drawText(canvas,subject2.MAnswer, 60, 80, painter,-90);
	        canvas.drawLine(100, 100, 400, 100, painter);
    	}
    	catch(Exception e)
    	{
    	}
        
//   Relate sentences:    	
//        Paint painter = new Paint();                
//        painter.setColor(Color.WHITE);
//        painter.setTextSize(20);                
//        canvas.drawLine(100, 100, 100, 400, painter);
//        drawText(canvas,"Hello", 80, 200, painter,-90);        
//        
//        painter.setColor(Color.RED);
//        painter.setTextSize(40);
//        //drawText(canvas,"free", 150, 180, painter,-45);
//        drawText(canvas,"free", 150, 180, painter,-90);
//        
//        paint.setColor(Color.BLUE);
//        drawText(canvas,"World", 150, 80, painter,0);
//        canvas.drawLine(100, 100, 400, 100, painter);
    }
    
    
    /* *************************************************************************************************
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
    
    /* *************************************************************************************************
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
