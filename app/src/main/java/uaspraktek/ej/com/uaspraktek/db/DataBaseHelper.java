package uaspraktek.ej.com.uaspraktek.db;

/**
 * Created by EJ Public on 13/12/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "patientdb";
    private static final int DATABASE_VERSION = 1;

    public static final String PATIENT_TABLE = "employee";

    public static final String ID_COLUMN = "patientId";
    public static final String NAME_COLUMN = "patientName";
    public static final String PATIENT_DOB = "patientDOB";
    public static final String PATIENT_ADDRESS = "patientAddress";
    public static final String PATIENT_IDCARD = "patientIdCardNumber";
    public static final String PATIENT_GENDER = "patientGender";
    public static final String REDEEM_TIME = "patientRedeemTime";

    public static final String CREATE_PATIENT_TABLE = "CREATE TABLE "
            + PATIENT_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + PATIENT_ADDRESS + " TEXT, "
            + PATIENT_IDCARD + " INTEGER, "
            + PATIENT_DOB + " DATE"
            + PATIENT_GENDER + " CHARACTER(1)"
            + REDEEM_TIME + " DATETIME" + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys='ON';");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
