package com.testSQLite;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class testSQLite extends Activity {
	GVTable table;
	Button btnCreateDB, btnInsert, btnClose;
	SQLiteDatabase db;
	int id;//��Ӽ�¼ʱ��id�ۼӱ�ǣ�����ȫ��

	private static final String TABLE_NAME = "stu";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String PHONE = "phone";
	private static final String ADDRESS = "address";
	private static final String AGE = "age";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnCreateDB = (Button) this.findViewById(R.id.btnCreateDB);
		btnCreateDB.setOnClickListener(new ClickEvent());

		btnInsert = (Button) this.findViewById(R.id.btnInsertRec);
		btnInsert.setOnClickListener(new ClickEvent());

		btnClose = (Button) this.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new ClickEvent());

		table=new GVTable(this);
		table.gvSetTableRowCount(8);//����ÿ����ҳ��ROW����
		LinearLayout ly = (LinearLayout) findViewById(R.id.MainLinearLayout);

		table.setTableOnClickListener(new GVTable.OnTableClickListener() {
			@Override
			public void onTableClickListener(int x,int y,Cursor c) {
				c.moveToPosition(y);
				String str=c.getString(x)+" λ��:("+String.valueOf(x)+","+String.valueOf(y)+")";
				Toast.makeText(testSQLite.this, str, 1000).show();
			}

		});
		table.setOnPageSwitchListener(new GVTable.OnPageSwitchListener() {
			
			@Override
			public void onPageSwitchListener(int pageID,int pageCount) {
				String str="����"+String.valueOf(pageCount)+
				" ��ǰ��"+String.valueOf(pageID)+"ҳ";
				Toast.makeText(testSQLite.this, str, 1000).show();
			}
		});
		
		ly.addView(table);
	}

	class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == btnCreateDB) {
				CreateDB();
			} else if (v == btnInsert) {
				InsertRecord(16);//����16����¼

				table.gvUpdatePageBar("select count(*) from " + TABLE_NAME,db);
				table.gvReadyTable("select * from " + TABLE_NAME,db);
			}else if (v == btnClose) {
				table.gvRemoveAll();
				db.close();
				
			}
		}
	}
	
	/**
	 * ���ڴ洴�����ݿ�����ݱ�
	 */
	void CreateDB() {
		// ���ڴ洴�����ݿ�
		db = SQLiteDatabase.create(null);
		Log.e("DB Path", db.getPath());
		String amount = String.valueOf(databaseList().length);
		Log.e("DB amount", amount);
		// �������ݱ�
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + 
		        ID	+ " text not null, " + NAME + " text not null," +
		        ADDRESS	+ " text not null, " + PHONE + " text not null," +
		        AGE	+ " text not null "+");";
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			db.execSQL(sql);
		} catch (SQLException e) {}
	}

	/**
	 * ����N������
	 */
	void InsertRecord(int n) {
		int total = id + n;
		for (; id < total; id++) {
			String sql = "insert into " + TABLE_NAME + " (" + 
			ID + ", " + NAME+", " + ADDRESS+", " + PHONE+", "+AGE
					+ ") values('" + String.valueOf(id) + "', 'man','address','123456789','18');";
			try {
				db.execSQL(sql);
			} catch (SQLException e) {
			}
		}
	}
	
	

}