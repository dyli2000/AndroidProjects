package sam.sucl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//import net.sourceforge.jtds.jdbc.Driver;

/* ��Ҫ��������������ĳ�ʼ���� */
public class CutAnimActivity extends Activity
{
    Context context = null;
    String UserName = "root";
    String Password = "";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context=this;
//		Connection con = null;  
//		try {
//			Class.forName( "org.gjt.mm.mysql.Driver" );
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}// ������������  
//		try {
//			con = DriverManager.getConnection( "jdbc:mysql://DbComputerNameOrIPAddr:3306/CutAnim", UserName, Password );
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  

        
        
        Button button1=(Button)findViewById(R.id.button1);
        
        /* ��Buttonע��һ���ص�����������onClick�¼�����ʱִ��.
         * ע�⣡������һ����䣬����һ������  */
        button1.setOnClickListener(new OnClickListener() 
	        {
				public void onClick(View v)
				{
					/*  ������ʹ��Cutact */
					Intent intent=new Intent(context, Cutact.class);
					startActivity(intent);
				}
			}
        );
        
    }
}