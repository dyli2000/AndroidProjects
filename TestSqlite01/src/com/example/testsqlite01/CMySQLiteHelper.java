package com.example.testsqlite01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class CMySQLiteHelper  extends SQLiteOpenHelper
{
	public CMySQLiteHelper(Context contex,String name,CursorFactory factory,int version)
	{
		super(contex,name,factory,version);
	}

    /** *************************************************************************************************
    *       DESC :     �����ݿ��״δ���ʱִ�и÷�����һ�㽫������ȳ�ʼ���������ڸ÷�����ִ��. 
    *       				��дonCreate����������execSQL����������. 
    *       ARGC :
    *                      @db:  
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL("create table if not exists StarInfo( "+"id integer primary key," + "name varchar," + "ballAge integer)" ); 
	}

    /** *************************************************************************************************
    *       DESC :     �������ݿ�ʱ����İ汾���뵱ǰ�İ汾�Ų�ͬʱ����ø÷���
    *       ARGC :
    *                      @db:  
    *                      @oldVersion:
    *                      @newVersion:
    *       RET  :       Success,true; Fail,false.
    *---------------------------------------------------------------------------------------------------
    ****************************************************************************************************/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
	}
}

