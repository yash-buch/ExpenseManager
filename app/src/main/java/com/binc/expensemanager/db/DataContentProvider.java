package com.binc.expensemanager.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.binc.expensemanager.util.Constants;

public class DataContentProvider extends ContentProvider {
    private static int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public DataContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = db.insert(Constants.TableConstants.TABLE_NAME, null, values);

        if(rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(Uri.parse(Constants.ProviderConstants.URL), rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        Context mContext = getContext();
        DBHelper dbHelper = new DBHelper(mContext);

        db = dbHelper.getReadableDatabase();

        return (db == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Constants.TableConstants.TABLE_NAME);

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    private static class DBHelper extends SQLiteOpenHelper {
        int version;

        DBHelper(Context context){
            super(context, Constants.TableConstants.DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Constants.ProviderConstants.CREATE_TABLE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Constants.TableConstants.TABLE_NAME);
            onCreate(db);
        }
    }
}
