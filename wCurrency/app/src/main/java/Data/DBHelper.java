package Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "wCurrencyDB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "history";
    public static final String COL_ID = "_id";
    public static final String COL_A_NAME = "dateChange";
    public static final String COL_B_NAME = "currencyNameA";
    public static final String COL_C_NAME = "currencyNameB";
    public static final String COL_D_NAME = "valueCurrencyA";
    public static final String COL_E_NAME = "valueCurrencyB";

    public static String PERSONNE_DDL = "create table "+TABLE_NAME+"("
            + COL_ID + " integer primary key autoincrement unique, "
            + COL_A_NAME + " text,"
            + COL_B_NAME + " text,"
            + COL_C_NAME + " text,"
            + COL_D_NAME + " reel,"
            + COL_E_NAME + " reel )";


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PERSONNE_DDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
