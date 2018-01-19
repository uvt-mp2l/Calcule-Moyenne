package tn.rnu.isi.calcule_moyenne.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tn.rnu.isi.calcule_moyenne.constant.MatiereField;
import tn.rnu.isi.calcule_moyenne.model.Matiere;

/**
 * Created by wim on 4/26/16.
 */
public class SQLiteDB extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Matiere.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MatiereField.TABLE_NAME + " (" +
                    MatiereField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MatiereField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    MatiereField.COLUMN_DS_NOTE + TEXT_TYPE + COMMA_SEP +
                    MatiereField.COLUMN_DS_COEF + TEXT_TYPE + COMMA_SEP +
                    MatiereField.COLUMN_TP_NOTE + TEXT_TYPE + COMMA_SEP +
                    MatiereField.COLUMN_TP_COEF + TEXT_TYPE + COMMA_SEP +
                    MatiereField.COLUMN_EXM_NOTE + TEXT_TYPE + COMMA_SEP +
                    MatiereField.COLUMN_EXM_COEF + TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MatiereField.TABLE_NAME;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void create(Matiere matiere){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MatiereField.COLUMN_NAME, matiere.getName());
        values.put(MatiereField.COLUMN_DS_NOTE, "");
        values.put(MatiereField.COLUMN_DS_COEF, matiere.getDs_coef());
        values.put(MatiereField.COLUMN_TP_NOTE, "");
        values.put(MatiereField.COLUMN_TP_COEF, matiere.getTp_coef());
        values.put(MatiereField.COLUMN_EXM_NOTE, "");
        values.put(MatiereField.COLUMN_EXM_COEF, matiere.getExm_coef());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MatiereField.TABLE_NAME,
                null,
                values);
    }

    public Cursor retrieve(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MatiereField.COLUMN_ID,
                MatiereField.COLUMN_NAME,
                MatiereField.COLUMN_DS_NOTE,
                MatiereField.COLUMN_DS_COEF,
                MatiereField.COLUMN_TP_NOTE,
                MatiereField.COLUMN_TP_COEF,
                MatiereField.COLUMN_EXM_NOTE,
                MatiereField.COLUMN_EXM_COEF };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MatiereField.COLUMN_NAME + " ASC";

        Cursor c = db.query(
                MatiereField.TABLE_NAME,                    // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        return c;
    }

    public void update(Matiere matiere){
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(MatiereField.COLUMN_NAME, matiere.getName());
        values.put(MatiereField.COLUMN_DS_NOTE, matiere.getDs_note());
        values.put(MatiereField.COLUMN_DS_COEF, matiere.getDs_coef());
        values.put(MatiereField.COLUMN_TP_NOTE, matiere.getTp_note());
        values.put(MatiereField.COLUMN_TP_COEF, matiere.getTp_coef());
        values.put(MatiereField.COLUMN_EXM_NOTE, matiere.getExm_note());
        values.put(MatiereField.COLUMN_EXM_COEF, matiere.getExm_coef());

        // Which row to update, based on the ID
        String selection = MatiereField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(matiere.getId()) };

        int count = db.update(
                MatiereField.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void delete(int id){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = MatiereField.COLUMN_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        // Issue SQL statement.
        db.delete(MatiereField.TABLE_NAME, selection, selectionArgs);
    }
}
