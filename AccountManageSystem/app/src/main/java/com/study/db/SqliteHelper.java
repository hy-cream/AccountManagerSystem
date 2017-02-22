package com.study.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 胡钰 on 2017/1/5.
 */

public class SqliteHelper extends SQLiteOpenHelper{

    private Context context;

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public SqliteHelper(Context context){

        super(context,"AccountManager.db",null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建三张表
        String sqlUser="create table tbl_user(_id integer primary key autoincrement,name,passwd)";
        String sqlAccount="create table tbl_account(_id integer primary key autoincrement,name,price,type,user_id)";
        String sqlRecord="create table tbl_record(_id integer primary key autoincrement,inorout,content,money,type,date,user_id)";
        db.execSQL(sqlUser);
        db.execSQL(sqlAccount);
        db.execSQL(sqlRecord);
        Log.i("SqliteHelper","创建完成三张表");

        //创建一个sharePreferences存储用户信息
        SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("user_id",0);
        editor.putString("user_name","");
        editor.putString("user_passwd","");
        editor.putBoolean("isLogin",false);
        editor.commit();
        Log.i("SqliteHelper","创建完成sharePreferences"+"===="+"name是"+sharedPreferences.getString("user_name","pppp"));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("SqliteHelper","执行了onUpgrade方法");
    }
}
