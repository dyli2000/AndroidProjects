package sam.sucl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CutAnimActivity extends Activity {
    Context context=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context=this;
        
        Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				Intent intent=new Intent(context, Cutact.class);
				startActivity(intent);

			}
		});
    }
}