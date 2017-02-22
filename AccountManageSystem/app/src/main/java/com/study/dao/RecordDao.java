package com.study.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.study.bean.AccountBean;
import com.study.bean.RecordBean;
import com.study.bean.UserBean;
import com.study.db.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 胡钰 on 2017/1/6.
 */

public class RecordDao {

    private SqliteHelper helper;
    private SQLiteDatabase db;

    public RecordDao(Context context){
        helper=new SqliteHelper(context);
    }

    //添加收入或者支出的条目
    public void addRecord(RecordBean recordBean){

        db=helper.getWritableDatabase();
//        private String inOrOut;//支出或者收入
//        private String content;//事情
//        private double money;//多少钱
//        private String type;//什么类型的账户
//        private String date;//日期
//        private int user_id;//用户id
        ContentValues values=new ContentValues();
        values.put("inOrOut",recordBean.getInOrOut());
        values.put("content",recordBean.getContent());
        values.put("money",recordBean.getMoney());
        values.put("type",recordBean.getType());
        values.put("date",recordBean.getDate());
        values.put("user_id",recordBean.getUser_id());

        db.insert("tbl_record",null,values);
        Log.i("recordDao","----------------addRecord success");
    }
    //删除账目，暂时用不到，先不写
    public void deleteRecord(){

    }
    //修改账目的信息,以id唯一标识每条账目，主要修改type,content和date，所以账目的bean类里面应该添加id这个属性，保留
    public void updateRecord(RecordBean recordBean){
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",recordBean.getType());
        values.put("content",recordBean.getContent());
        values.put("date",recordBean.getDate());

        //db.update("tbl_record",values,"_id=?",new String[]{});
    }

    //查找用户所有的账目信息，返回一个List集合,通过用户id找
    public List<RecordBean> queryRecord(String user_id){

        List<RecordBean> mlist=null;
        Cursor cursor=null;
        db=helper.getReadableDatabase();
        cursor=db.query("tbl_record",null,"user_id=?",new String[]{user_id},null,null,null);
        if(cursor!=null){
            mlist=new ArrayList<RecordBean>();
            while (cursor.moveToNext()){
                String inOrOut=cursor.getString(cursor.getColumnIndex("inorout"));
                String content=cursor.getString(cursor.getColumnIndex("content"));
                double money=cursor.getDouble(cursor.getColumnIndex("money"));
                String type=cursor.getString(cursor.getColumnIndex("type"));
                String date=cursor.getString(cursor.getColumnIndex("date"));//取数据这里后期还要用类去转换出来
//                int user_id2=cursor.getInt(cursor.getColumnIndex("user_id"));
                RecordBean recordBean=new RecordBean(inOrOut,content,money,type,date,user_id);
                mlist.add(recordBean);
                Log.i("RecordDao","我读取数据成功");
            }

        }
        return mlist;
    }

}
