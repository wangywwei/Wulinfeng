package com.hxwl.wulinfeng.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.hxwl.common.errorlog.ErrorMsg;
import com.hxwl.wulinfeng.db.User;

import com.hxwl.wulinfeng.db.ErrorMsgDao;
import com.hxwl.wulinfeng.db.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig errorMsgDaoConfig;
    private final DaoConfig userDaoConfig;

    private final ErrorMsgDao errorMsgDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        errorMsgDaoConfig = daoConfigMap.get(ErrorMsgDao.class).clone();
        errorMsgDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        errorMsgDao = new ErrorMsgDao(errorMsgDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(ErrorMsg.class, errorMsgDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        errorMsgDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public ErrorMsgDao getErrorMsgDao() {
        return errorMsgDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
