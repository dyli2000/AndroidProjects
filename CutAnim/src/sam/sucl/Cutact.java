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

public class Cutact extends Activity{
	CutAV mCut=null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Display display=getWindowManager().getDefaultDisplay();
		mCut=new CutAV(this, display.getWidth(), display.getHeight());
		setContentView(mCut);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		int x=(int) event.getX();
		int y=(int) event.getY();
		
		switch (event.getAction()) {
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
	
	public class CutAV extends SurfaceView implements Callback,Runnable{
		private int mScreenWidth=0;
		private int mScreenHeight=0;
		
		private static final int GO_GO=0;
		private static final int GO_CUT=1;
//		private static final int GO_END=2;
//		private static final int GO_COUNT=3;
		
		private int cut_state=GO_GO;
		
		private Animation tu=null;
		private int tuPosX=0;
		private int tuPosY=0;
		
		private Bitmap tu1=null;
		private Bitmap tu2=null;
		
		private ImageButton goButton=null;
//		private int goPosX=250;
//		private int goPosY=150;
		
		private int move=20;
		private int moveCount=0;
		
//		private Bitmap bg=null;
		
		private Thread mThread=null;
		private Context mContext=null;
		private Canvas mCanvas=null;
		private Paint paint=null;
		private SurfaceHolder surfaceHolder=null;
		private boolean isRunning=false;
		
		public CutAV(Context context,int screenWidth,int screenHeight){
			super(context);
			mContext=context;
			mCanvas=new Canvas();
			paint=new Paint();
			
			mScreenWidth=screenWidth;
			mScreenHeight=screenHeight;
			
			surfaceHolder=getHolder();
			surfaceHolder.addCallback(this);
			setFocusable(true);
			
			init();
			setCutState(GO_GO);
		}
		
		protected void doDraw(){
			switch (cut_state) {
			case GO_GO:
				tuPosX-=10;
				mCanvas.drawColor(Color.BLACK);
				goButton=new ImageButton(mContext, R.drawable.ic_launcher, tuPosX, tuPosY);
				goButton.DrawImageButton(mCanvas, paint);
				tu.DrawAnimation(mCanvas, paint, tuPosX, tuPosY);
				break;
				
			case GO_CUT:
				mCanvas.drawColor(Color.BLACK);
				move+=2;
				moveCount++;
				mCanvas.drawBitmap(tu1, tuPosX,tuPosY-move, paint);
				mCanvas.drawBitmap(tu2, tuPosX,tuPosY+move, paint);
				cutSt();
				break;
				
			default:
				break;
			}		
			
		}

		private void cutSt() 
		{
			if (moveCount>10) {
				cut_state=GO_GO;
				moveCount=0;
				move=20;
				tuPosX=mScreenWidth;
			}
		}

		private void init() {
			tuPosX=mScreenWidth;
			tuPosY=mScreenHeight/2;
			
			tu=new Animation(mContext, new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher1,R.drawable.ic_launcher2,R.drawable.ic_launcher3}, true);
			
			tu1=ReadBitMap(mContext, R.drawable.ic_1);
			tu2=ReadBitMap(mContext, R.drawable.ic_2);	
		}
		
		public Bitmap ReadBitMap(Context context, int resId) {
			BitmapFactory.Options opt=new BitmapFactory.Options();
			opt.inPreferredConfig=Bitmap.Config.RGB_565;
			opt.inPurgeable=true;
			opt.inInputShareable=true;
			
			InputStream is=context.getResources().openRawResource(resId);
			return BitmapFactory.decodeStream(is,null,opt);
		}
		
		private void setCutState(int state) {
			cut_state=state;			
		}
		
		public void UpdateTouchEvent(int x, int y) {
			switch (cut_state) {
			case GO_GO:
				if (goButton.IsClick(x, y)) {
					cut_state=GO_CUT;					
				}
				break;
			}
		}
		
		public void run(){
			while (isRunning) {
				synchronized (surfaceHolder) {
					mCanvas=surfaceHolder.lockCanvas();
					doDraw();
					surfaceHolder.unlockCanvasAndPost(mCanvas);
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void surfaceChanged(SurfaceHolder holder,int arg1,int arg2,int arg3){
		}
		
		public void surfaceCreated(SurfaceHolder surfaceHolder){
			isRunning=true;
			mThread=new Thread(this);
			mThread.start();
		}
		
		public void surfaceDestroyed(SurfaceHolder surfaceHolder){
			isRunning=false;
		}
	}
}
