package com.qkun.wanandroid_java.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.qkun.wanandroid_java.db.HistoryModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HISTORY_MODEL".
*/
public class HistoryModelDao extends AbstractDao<HistoryModel, Long> {

    public static final String TABLENAME = "HISTORY_MODEL";

    /**
     * Properties of entity HistoryModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Date = new Property(2, java.util.Date.class, "date", false, "DATE");
    }


    public HistoryModelDao(DaoConfig config) {
        super(config);
    }
    
    public HistoryModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HISTORY_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"DATE\" INTEGER);"); // 2: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HISTORY_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HistoryModel entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(3, date.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HistoryModel entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(3, date.getTime());
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public HistoryModel readEntity(Cursor cursor, int offset) {
        HistoryModel entity = new HistoryModel( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)) // date
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HistoryModel entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HistoryModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HistoryModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HistoryModel entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
