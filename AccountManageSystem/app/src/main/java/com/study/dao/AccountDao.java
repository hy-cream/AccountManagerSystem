package com.study.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.study.bean.AccountBean;
import com.study.bean.UserBean;
import com.study.db.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 胡钰 on 2017/1/6.
 */

public class AccountDao {
    private SqliteHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public AccountDao(Context context){

        helper=new SqliteHelper(context);
        this.context=context;
    }

    //执行数据库的增删改查
    //添加账户，需要传进一个AccountDao对项
    public void addAccount(AccountBean accountBean){
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",accountBean.getName());
        values.put("price",accountBean.getPrice());
        values.put("user_id",accountBean.getUser_id());
        values.put("type",accountBean.getType());
        db.insert("tbl_account",null,values);
        Log.i("accountDao","----------------addaccount success");
    }
    //删除账户，在长摁Item删除里面调用，暂时不写，因为只有id才能唯一标识一个账户信息
    public void deleteAccount(){

    }
    //主要是修改账户的金额,这里也要拿_id,暂时不处理
    public void updateAccount(AccountBean accountBean){
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("price",accountBean.getPrice());
        //db.update("tbl_account",values,"_id=?",new String[]{});
    }

    //查找所有账户，显示recyclerView的时候使用
    public List<AccountBean> queryAccount(String user_id){
        List<AccountBean> mlist=null;
        Cursor cursor=null;
        db=helper.getReadableDatabase();
        cursor=db.query("tbl_account",null,"user_id=?",new String[]{user_id},null,null,null);
        //cursor=db.rawQuery("select * from tbl_account where user_id=1",null);
        if(cursor!=null){
            mlist=new ArrayList<AccountBean>();
            while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                double price=cursor.getDouble(cursor.getColumnIndex("price"));
                String type=cursor.getString(cursor.getColumnIndex("type"));
                //int user_id1=cursor.getInt(cursor.getColumnIndex("user_id"));
                //AccountBean accountBean=new AccountBean(name,price,user_id,type);
                Log.i("AccountDao",name+" "+price+" "+type+" "+user_id);
                mlist.add(new AccountBean(name,price,user_id,type));
                Log.i("AccountDao","我遍历了账户信息值");
            }
        }else{
            Log.i("AccountDao","cursor是空值");
        }
        Log.i("AccountDao","我查询了账户信息值");
        return mlist;
    }

}
