package cn.itcast.file;

import cn.itcast.service.FileService;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity 
{
    private EditText mNameText;
    private EditText mContentText;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mNameText = (EditText) this.findViewById(R.id.filename);
        mContentText = (EditText) this.findViewById(R.id.filecontent);
        Button button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(new ButtonClickListener());
        
        Button btnOpen = (Button) this.findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new CBtnClickListener());
    }
    private final class CBtnClickListener implements View.OnClickListener
    {
    	public void onClick(View v)
    	{
    		String fileName = mNameText.getText().toString();
    		FileService service = new FileService(getApplicationContext());
    		try 
    		{
				String result = service.read(fileName);
				mContentText.setText("Read string from " + fileName + "\n" + result);
			} 
    		catch (Exception e) 
    		{
    			//setTitle(e.toString());
    			setTitle(Environment.getExternalStorageDirectory().toString() + "/" + mNameText.getText());
    			mContentText.setText(e.toString());
			}
    	}
    }
    
    private final class ButtonClickListener implements View.OnClickListener
    {

		public void onClick(View v) 
		{
			String filename = mNameText.getText().toString();      // Get file name.
			String content = mContentText.getText().toString();   // Get file content.
			FileService service = new FileService(getApplicationContext());
			try 
			{
				/* 检测是否存在外扩的SD卡 */
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					service.saveToSDCard(filename, content);
					Toast.makeText(getApplicationContext(), R.string.success, 1).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), R.string.sdcardnoexsit, 1).show();
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), R.string.fail, 1).show();
			}
		}
    	
    }
}