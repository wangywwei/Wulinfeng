package com.hxwl.common.dbdaoutils.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hxwl.wulinfeng.db.DaoMaster;
import com.hxwl.wulinfeng.db.DaoSession;


/**
 * 功能:greenDao 数据库的工具类 wulinfeng 数据库
 */

public class GreenDaoUtils {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static GreenDaoUtils greenDaoUtils;
    private Context mContext;

    private GreenDaoUtils(Context ctx){
        initGreenDao(ctx);
    }

    public static GreenDaoUtils getSingleTon(Context context){
        if (greenDaoUtils==null){
            greenDaoUtils=new GreenDaoUtils(context);
        }
        return greenDaoUtils;
    }

    private void initGreenDao(Context ctx){
        mHelper=new DaoMaster.DevOpenHelper(ctx,"wulinfeng-db",null);
        db=mHelper.getWritableDatabase();
        mDaoMaster=new DaoMaster(db);
        mDaoSession=mDaoMaster.newSession();
    }

    public DaoSession getDaoSession(Context ctx) {
        if (mDaoMaster==null){
            initGreenDao(ctx);
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb(Context ctx) {
        if (db==null){
            initGreenDao(ctx);
        }
        return db;
    }

}