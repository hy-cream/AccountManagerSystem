package com.study.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.study.bean.UserBean;
import com.study.db.SqliteHelper;

/**
 * Created by 胡钰 on 2017/1/6.
 */

public class UserDao {

    private SqliteHelper helper;
    private SQLiteDatabase db;

    public UserDao(Context context){

            helper=new SqliteHelper(context);
           //打开数据库，让它执行oncreate方法，执行建表语句
           db=helper.getWritableDatabase();

    }

    //添加用户，注册功能，需要传入用户名和密码
    public void addUser(UserBean userBean){

        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",userBean.getName());
        values.put("passwd",userBean.getPasswd());
        db.insert("tbl_user",null,values);
        Log.i("userDao","----------------addUser success");
    }
    //删除用户,暂时没有功能要用到，不需要实现什么内容
    public void deleteUser(){

    }
    //修改用户的用户名或者密码
    public void updateUser(UserBean newUserBean,String oldName){
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",newUserBean.getName());
        values.put("passwd",newUserBean.getPasswd());
        db.update("tbl_user",values,"name=?",new String[]{oldName});
    }

    //查找用户，匹配密码,在登录的时候用
    public UserBean queryUser(String name,String passwd){
        UserBean userBean=null;
        String passwd2 = null;
        db=helper.getReadableDatabase();
        Cursor cursor=db.query("tbl_user",null,"name=?",new String[]{name},null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                passwd2=cursor.getString(cursor.getColumnIndex("passwd"));
            }
           
            if(passwd2.equals(passwd)){
                userBean=new UserBean(name, passwd2);
            }
        }
        return userBean;
    }

    //通过name查找他的Id
    public int queryIdByName(String name){
        int id=0;
        db=helper.getReadableDatabase();
        Cursor cursor=db.query("tbl_user",null,"name=?",new String[]{name},null,null,null);
        while(cursor.moveToNext()){
            id=cursor.getInt(cursor.getColumnIndex("_id"));
        }

        return id;
    }

}
