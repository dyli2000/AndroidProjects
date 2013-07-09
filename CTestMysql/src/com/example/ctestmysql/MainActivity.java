package com.example.ctestmysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.os.Bundle;
import android.app.Activity;
import android.database.SQLException;
import android.view.Menu;
import net.sourceforge.jtds.jdbc.Driver;
import android.util.Log;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("debug","111111111111111");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String userName = "root";
		String passWord = "";
		Connection con = null;
		Log.v("debug","2222222222222222");
		try
		{
			//Class.forName("net.sourceforge.jtds.jdbc.Driver");  
			//con = DriverManager.getConnection(  "jdbc:jtds:sqlserver://127.0.0.1:3306/CutAnim", userName,  passWord);  
			//con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CutAnim", userName,  passWord); 
			Log.v("debug","22222222222233333333");
			Class.forName("com.mysql.jdbc.Driver");    //com.mysql.jdbc.Driver
			Log.v("debug","33333333333333");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CutAnim", userName, passWord);
			Log.v("debug","4444444444444444");
		}
		catch(SQLException e)
		{
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (java.sql.SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void testConnection(Connection con) throws java.sql.SQLException {
		try {

			String sql = "SELECT * FROM tableA";//查询表名为“table_test”的所有内容
			Statement stmt = con.createStatement();//创建Statement
			ResultSet rs = stmt.executeQuery(sql);//ResultSet类似Cursor

			while (rs.next()) {//<code>ResultSet</code>最初指向第一行
				System.out.println(rs.getString("tid"));//输出第n行，列名为“test_id”的值
				//System.out.println(rs.getString("name"));
				
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage().toString());
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
				}
		}
	}
	
	
	

}
