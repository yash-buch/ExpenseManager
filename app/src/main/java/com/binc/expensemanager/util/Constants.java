package com.binc.expensemanager.util;

/**
 * Created by yashbuch on 12/03/16.
 */
public class Constants {

    public static final String CATEGORY = "_category";

    public static class TableConstants {
        public static final String DATABASE_NAME = "MyExpenseData";
        public static final String TABLE_NAME = "expensedata";
    }

    public static class ProviderConstants {

        public static final int DB_VERSION = 1;
        public static final String AUTHORITY = "com.binc.expensemanager.db."+TableConstants.DATABASE_NAME;
        public static final String URL = "content://"+ AUTHORITY +"/"+TableConstants.TABLE_NAME;

        public static final String ID = "_id";
        public static final String NAME_EN = "_name_en";
        public static final String NAME_TE = "_name_te";
        public static final String CATEGORY = "_category";
        public static final String REQUIRED_QTY = "_req_qty";
        public static final String AVAILABLE_QTY = "_avb_qty";

        public static final String CREATE_TABLE_QUERY = " CREATE TABLE " + TableConstants.TABLE_NAME+
                " ("+
                ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_EN       + " TEXT NOT NULL, " +
                NAME_TE       + " TEXT NOT NULL, " +
                CATEGORY      + " TEXT NOT NULL, " +
                REQUIRED_QTY  + " TEXT NOT NULL, " +
                AVAILABLE_QTY + " TEXT NOT NULL" +
                ");";
    }
}
