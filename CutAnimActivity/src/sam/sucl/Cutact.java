package sam.sucl;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder.Callback;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import android.widget.*;

/*  ��Ҫ�����������  */
public class Cutact extends Activity
{
	CutAV mCut=null;
	public void onCreate(Bundle savedInstanceState)
	{
		try
		{
			super.onCreate(savedInstanceState);
			/*  ȥ������  */
			//requestWindowFeature(Window.FEATURE_NO_TITLE);
			setTitle("��Ŀ�����ڱ���");
			
			/*  ����ȫ���������ض����ĵ�ص�  */
			 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			 /*  �����������У�Ĭ���Ǵ��ҵ���  */
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			
			Display display=getWindowManager().getDefaultDisplay();
			mCut=new CutAV(this, display.getWidth(), display.getHeight());
			/*  ���û�����ݵ�һ���ض��Ŀؼ� */
			setContentView(mCut);
		}
		catch(Exception e)
		{
			setTitle(e.toString());
		}
	}

    /** *************************************************************************************************
     *       DESC :     �¼���⴦����
     *       ARGC :
      *                      @event:       Motion event.
     *       RET  :       false,û���¼�����.
     *---------------------------------------------------------------------------------------------------
     ****************************************************************************************************/	
	public boolean onTouchEvent(MotionEvent event)
	{
		int x=(int) event.getX();
		int y=(int) event.getY();
		
		switch (event.getAction()) 
		{
			case MotionEvent.ACTION_DOWN:
				mCut.UpdateTouchEvent(x, y);
				break;
			case MotionEvent.ACTION_MOVE:
				mCut.UpdateTouchEvent(x, y);
					break;
			case MotionEvent.ACTION_UP:				
					break;
			default:
				break;
		}
		return false;
	}
	
	public class CutAV extends SurfaceView implements Callback,Runnable
	{
		private int mScreenWidth = 0;
		private int mScreenHeight = 0;
		private static final int GO_GO = 0;
		private static final int GO_CUT = 1;
		
		private int mCutState=GO_GO;
		private Animation  mAnimation=null;
		private Animation  mAnimation1 = null;
		
		private int mTuPosX= 0;
		private int mTuPosY= 0;
		private Bitmap mTu1=null;
		private Bitmap mTu2=null;
		private ImageButton mGoButton=null;
		private ImageButton mGoButton1= null;
		
		private int mMove=20;
		private int mMoveCount=0;
		private Thread mThread=null;
		private Context mContext=null;
		private Canvas mCanvas=null;
		private Paint mPaint=null;
		private SurfaceHolder mSurfaceHolder=null;
		private boolean mIsRunning=false;

		public CutAV(Context context,int screenWidth,int screenHeight)
		{
			super(context);
			mContext=context;
			mCanvas=new Canvas();
			mPaint=new Paint();
			
			mScreenWidth=screenWidth;
			mScreenHeight=screenHeight;
			
			mSurfaceHolder=getHolder();
			mSurfaceHolder.addCallback(this);
			setFocusable(true);			
			
			/* ��Ҫ�ǳ�ʼ��һ����̬��ؼ����������п��ķְ��С�ؼ�  */
			init();
			/* ���������Ͽ�ʼ�ж� */
			setCutState(GO_GO);
		}
		
		protected void doDraw()
		{
			try
			{
				switch (mCutState) 
				{
				case GO_GO:
					mTuPosX -=20;  // ÿ�β��� 10 ���
					mCanvas.drawColor(Color.BLACK);
					/*  ������ﶥ����������0 */
					if(mTuPosX - 10 <= 0)
						mTuPosX = mScreenWidth;
					
					GlobalData.CSubject subject1= null;
					GlobalData.CSubject subject2 = null;
					subject1.SubjectBody = "Kobe is which team of nba?";
					subject1.Answer = "Laker";
					subject1.SubjectId = 100;
					subject1.IsCorrected = 0;
					
					subject2.SubjectBody = "James is which team of nba?";
					subject2.Answer = "Heats";
					subject2.SubjectId = 101;
					subject2.IsCorrected = 0;
					
					mGoButton=new ImageButton(mContext, R.drawable.ic_launcher, mTuPosX, mTuPosY+68);
					mGoButton.DrawImageButton(mCanvas, mPaint);
					mGoButton.DrawSubjectAndAnswer(mCanvas, mPaint,subject1,subject2);
					
					mGoButton1=new ImageButton(mContext, R.drawable.ic_launcherb, mTuPosX, mTuPosY-128);
					mGoButton1.DrawImageButton(mCanvas, mPaint);
					break;
					
				case GO_CUT:
					mCanvas.drawColor(Color.GREEN);
					mMove+=2;
					mMoveCount++;
					mCanvas.drawBitmap(mTu1, mTuPosX,mTuPosY-mMove, mPaint);
					mCanvas.drawBitmap(mTu2, mTuPosX,mTuPosY+mMove, mPaint);
					cutSt();
					break;
					
				default:
					break;
				}		
			}
			catch(Exception e)
			{
				setTitle(e.toString());
			}
		}

		private void cutSt() 
		{
			if (mMoveCount>10)
			{
				mCutState=GO_GO;
				mMoveCount=0;
				mMove=20;
				mTuPosX=mScreenWidth;
			}
		}

		private void init() 
		{
			mTuPosX=mScreenWidth;
			mTuPosY=mScreenHeight/2;
			
			/* ������̬���̵�ͼ */
			mAnimation = new Animation(mContext, 
															new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher1,R.drawable.ic_launcher2,R.drawable.ic_launcher3}, 
															true);
			mAnimation1 = new Animation(mContext, 
															new int[]{R.drawable.ic_launcherb,R.drawable.ic_launcher1b,R.drawable.ic_launcher2b,R.drawable.ic_launcher3b}, 
															true);
			
			/* �������п���ͼ */
			mTu1 =ReadBitMap(mContext, R.drawable.ic_1);
			mTu2=ReadBitMap(mContext, R.drawable.ic_2);	
		}
		
		public Bitmap ReadBitMap(Context context, int resId) 
		{
			BitmapFactory.Options opt=new BitmapFactory.Options();
			opt.inPreferredConfig=Bitmap.Config.RGB_565;
			opt.inPurgeable=true;
			opt.inInputShareable=true;
			
			InputStream is=context.getResources().openRawResource(resId);
			return BitmapFactory.decodeStream(is,null,opt);
		}
		
		private void setCutState(int state) 
		{
			mCutState=state;			
		}
		
		public void UpdateTouchEvent(int x, int y) 
		{
			switch (mCutState) 
			{
			case GO_GO:
				if (mGoButton.IsClick(x, y)) 
				{
					mCutState=GO_CUT;					
				}
				
				if (mGoButton1.IsClick(x, y))
				{
					mCutState = GO_CUT;
				}
				break;
			}
		}
		
		public void run()
		{
			while (mIsRunning) 
			{
				synchronized (mSurfaceHolder) 
				{
					mCanvas=mSurfaceHolder.lockCanvas();
					doDraw();
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
				try 
				{
					Thread.sleep(200);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public void surfaceChanged(SurfaceHolder holder,int arg1,int arg2,int arg3)
		{
		}
		
		public void surfaceCreated(SurfaceHolder surfaceHolder)
		{
			mIsRunning=true;
			mThread=new Thread(this);
			mThread.start();
		}
		
		public void surfaceDestroyed(SurfaceHolder surfaceHolder)
		{
			mIsRunning=false;
		}
	}
}
