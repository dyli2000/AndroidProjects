package com.testSQLite;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class GVTable extends LinearLayout 
{
	protected GridView gvTable,gvPage;	
	protected SimpleAdapter saPageID,saTable;// ������
	protected ArrayList<HashMap<String, String>> srcPageID,srcTable;// ����Դ
	protected int TableRowCount=10;//��ҳʱ��ÿҳ��Row����
	protected int TableColCount=0;//ÿҳcol������

	protected SQLiteDatabase db;
	protected String rawSQL="";
	protected Cursor curTable;//��ҳʱʹ�õ�Cursor
	protected OnTableClickListener clickListener;//������ҳ�ؼ������ʱ�Ļص�����
	protected OnPageSwitchListener switchListener;//��ҳ�л�ʱ�Ļص�����
	
	public GVTable(Context context) 
	{
		super(context);
		this.setOrientation(VERTICAL);//��ֱ

		gvTable=new GridView(context);
		addView(gvTable, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));//��ʽ��
		
		srcTable = new ArrayList<HashMap<String, String>>();
		saTable = new SimpleAdapter(context,
				srcTable,// ������Դ
				R.layout.items,//XMLʵ��
				new String[] { "ItemText" },// ��̬������ImageItem��Ӧ������
				new int[] { R.id.ItemText });
		
		// ��Ӳ�����ʾ
		gvTable.setAdapter(saTable);
		gvTable.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int y=arg2/curTable.getColumnCount()-1;//�������Ĳ���
				int x=arg2 % curTable.getColumnCount();
				if (clickListener != null//��ҳ���ݱ����
						&& y!=-1) {//���еĲ��Ǳ�����ʱ
					clickListener.onTableClickListener(x,y,curTable);
				}
			}
		});
		
		gvPage=new GridView(context);
		gvPage.setColumnWidth(40);	//����ÿ����ҳ��ť�Ŀ��
		gvPage.setNumColumns(GridView.AUTO_FIT);	//��ҳ��ť�����Զ�����
		addView(gvPage, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));//��ʽ��
		srcPageID = new ArrayList<HashMap<String, String>>();
		
		saPageID = new SimpleAdapter(context,
				srcPageID,// ������Դ
				R.layout.items,//XMLʵ��
				new String[] { "ItemText" },// ��̬������ImageItem��Ӧ������
				new int[] { R.id.ItemText });

		// ��Ӳ�����ʾ
		gvPage.setAdapter(saPageID);
		// �����Ϣ����
		gvPage.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				LoadTable(arg2);//������ѡ��ҳ��ȡ��Ӧ������
			    if(switchListener!=null){//��ҳ�л�ʱ
			    	switchListener.onPageSwitchListener(arg2,srcPageID.size());
			    }
			}
	    });
	}

	/**
	 * �����������
	 */
	public void gvRemoveAll()
	{
		if(this.curTable!=null)
			curTable.close();
		srcTable.clear();
		saTable.notifyDataSetChanged();
	
		srcPageID.clear();
		saPageID.notifyDataSetChanged();
	}
	
	/**
	 * ��ȡָ��ID�ķ�ҳ����,���ص�ǰҳ��������
	 * SQL:Select * From TABLE_NAME Limit 9 Offset 10;
	 * ��ʾ��TABLE_NAME���ȡ���ݣ�����10�У�ȡ9��
	 * @param pageID ָ���ķ�ҳID
	 */
	protected void LoadTable(int pageID)
	{
		if(curTable!=null)//�ͷ��ϴε�����
			curTable.close();
		
	    String sql= rawSQL+" Limit "+String.valueOf(TableRowCount)+ " Offset " +String.valueOf(pageID*TableRowCount);
	    curTable = db.rawQuery(sql, null);
	    
	    gvTable.setNumColumns(curTable.getColumnCount());//����Ϊ���Ĺؼ��㣡
	    TableColCount=curTable.getColumnCount();
	    srcTable.clear();
	    // ȡ���ֶ�����
	    int colCount = curTable.getColumnCount();
		for (int i = 0; i < colCount; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ItemText", curTable.getColumnName(i));
			srcTable.add(map);
		}
		
		// �оٳ���������
		int recCount=curTable.getCount();
		for (int i = 0; i < recCount; i++) {//��λ��һ������
			curTable.moveToPosition(i);
			for(int ii=0;ii<colCount;ii++)//��λ��һ�������е�ÿ���ֶ�
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ItemText", curTable.getString(ii));
				srcTable.add(map);
			}
		}
		
		saTable.notifyDataSetChanged();
	}

	/**
	 * ���ñ��������ʾ������
	 * @param row ��������
	 */
	public void gvSetTableRowCount(int row)
	{
		TableRowCount=row;
	}
	
	/**
	 * ȡ�ñ����������	
	 * @return ����
	 */
	public int gvGetTableRowCount()
	{
		return TableRowCount;
	}
	
	/**
	 * ȡ�õ�ǰ��ҳ��Cursor
	 * @return ��ǰ��ҳ��Cursor
	 */
	public Cursor gvGetCurrentTable()
	{
		return curTable;
	}
		
	/**
	 * ׼����ҳ��ʾ����
	 * @param rawSQL sql���
	 * @param db ���ݿ�
	 */
	public void gvReadyTable(String rawSQL,SQLiteDatabase db)
	{
		this.rawSQL=rawSQL;
		this.db=db;
	}
	
	/**
	 * ˢ�·�ҳ�������°�ť����
	 * @param sql SQL���
	 * @param db ���ݿ�
	 */
	public void gvUpdatePageBar(String sql,SQLiteDatabase db)
	{
		Cursor rec = db.rawQuery(sql, null);
		rec.moveToLast();
		long recSize=rec.getLong(0);//ȡ������
		rec.close();
		int pageNum=(int)(recSize/TableRowCount) + 1;//ȡ�÷�ҳ��
		
		srcPageID.clear();
		for (int i = 0; i < pageNum; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ItemText", "No." + String.valueOf(i));// ���ͼ����Դ��ID

			srcPageID.add(map);
		}
		saPageID.notifyDataSetChanged();
	}

	//---------------------------------------------------------
	/**
	 * ��񱻵��ʱ�Ļص�����
	 */
	public void setTableOnClickListener(OnTableClickListener click) 
	{
		this.clickListener = click;
	}
	
	public interface OnTableClickListener 
	{
		public void onTableClickListener(int x,int y,Cursor c);
	}
	
	//---------------------------------------------------------
	/**
	 * ��ҳ�������ʱ�Ļص�����
	 */
	public void setOnPageSwitchListener(OnPageSwitchListener pageSwitch) 
	{
		this.switchListener = pageSwitch;
	}
	public interface OnPageSwitchListener 
	{
		public void onPageSwitchListener(int pageID,int pageCount);
	}
}
