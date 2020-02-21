package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.History;

public class DBAdapter {

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;

    public DBAdapter(Context context) {
        this.context = context;
        this.dbHelper =  new DBHelper(context, DBHelper.DB_NAME,null,DBHelper.DB_VERSION);
    }

    public void openMaBd(){
        db=dbHelper.getWritableDatabase();
    }

    public void closeMaBd(){
        db.close();
    }

    public void deleteTable(String TABLE_NAME){
        db.execSQL("delete from "+ TABLE_NAME);
    }

    public void insertHistory(History h){
        openMaBd();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_A_NAME, String.valueOf(h.getDateChange()));
        cv.put(DBHelper.COL_B_NAME, h.getCurrencyNameA());
        cv.put(DBHelper.COL_C_NAME, h.getCurrencyNameB());
        cv.put(DBHelper.COL_D_NAME, h.getValueCurrencyA());
        cv.put(DBHelper.COL_E_NAME, h.getValueCurrencyB());
        db.insert(DBHelper.TABLE_NAME,null,cv);
        closeMaBd();
    }

    public Cursor getHistories(){
        RegistreHistory registreHistory = new RegistreHistory();
        RegistreHistory.clearListe();
        openMaBd();
        String[] cols = {DBHelper.COL_ID, DBHelper.COL_A_NAME, DBHelper.COL_B_NAME,
                DBHelper.COL_C_NAME, DBHelper.COL_D_NAME,DBHelper.COL_E_NAME};

        Cursor cursor = db.query(DBHelper.TABLE_NAME,cols,
                null,null,null,null, null);

        //just pour tester
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //int id = cursor.getInt(0);
            String dateChange = cursor.getString(1);
            String nameCurrencyA = cursor.getString(2);
            String nameCurrencyB = cursor.getString(3);
            float valueCurrencyA = cursor.getFloat(4);
            float valueCurrencyb = cursor.getFloat(5);
            //Toast.makeText(context, id +" - "+name, Toast.LENGTH_LONG).show();
            registreHistory.addHistory(new History(dateChange,nameCurrencyA,nameCurrencyB,valueCurrencyA,valueCurrencyb));
            cursor.moveToNext();
        }
        return cursor;
    }
}
