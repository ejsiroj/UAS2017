package uaspraktek.ej.com.uaspraktek.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import uaspraktek.ej.com.uaspraktek.pojo.Pasien;

/**
 * Created by EJ Public on 13/12/2017.
 */

public class PatientDAO extends PatientDBDAO {

    //Where
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public PatientDAO(Context context) {
        super(context);
    }

    public long save(Pasien patient) {
        ContentValues values = new ContentValues();

        Log.d("dob", patient.getPatientDOB().getTime() + "");

        values.put(DataBaseHelper.NAME_COLUMN, patient.getPatientName());
        values.put(DataBaseHelper.PATIENT_DOB, formatter.format(patient.getPatientDOB()));
        values.put(DataBaseHelper.PATIENT_ADDRESS, patient.getPatientAddress());
        values.put(DataBaseHelper.PATIENT_IDCARD, patient.getPatientCardNumber());
        values.put(DataBaseHelper.PATIENT_GENDER, patient.getPatientGender());
        values.put(DataBaseHelper.REDEEM_TIME, String.valueOf(patient.getPatientRedeemTime()));

        return database.insert(DataBaseHelper.PATIENT_TABLE, null, values);
    }

    public long update(Pasien patient) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.NAME_COLUMN, patient.getPatientName());
        values.put(DataBaseHelper.PATIENT_DOB, formatter.format(patient.getPatientDOB()));
        values.put(DataBaseHelper.PATIENT_ADDRESS, patient.getPatientAddress());
        values.put(DataBaseHelper.PATIENT_IDCARD, patient.getPatientCardNumber());
        values.put(DataBaseHelper.PATIENT_GENDER, patient.getPatientGender());
        values.put(DataBaseHelper.REDEEM_TIME, String.valueOf(patient.getPatientRedeemTime()));

        long result = database.update(DataBaseHelper.PATIENT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(patient.getPatientId()) });
        Log.d("Update Result:", "=" + result);
        return result;
    }

    public int delete(Pasien patient) {
        return database.delete(DataBaseHelper.PATIENT_TABLE, WHERE_ID_EQUALS,
                new String[] { patient.getPatientId() + "" });
    }

    //USING query() method
    public ArrayList<Pasien> getPatients() {
        ArrayList<Pasien> patients = new ArrayList<Pasien>();

        Cursor cursor = database.query(DataBaseHelper.PATIENT_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.NAME_COLUMN,
                        DataBaseHelper.PATIENT_DOB,
                        DataBaseHelper.PATIENT_ADDRESS,
                        DataBaseHelper.PATIENT_IDCARD,
                        DataBaseHelper.PATIENT_GENDER,
                        DataBaseHelper.REDEEM_TIME }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Pasien patient = new Pasien();
            patient.setPatientId(cursor.getString(0));
            patient.setPatientName(cursor.getString(1));
            try {
                patient.setPatientDOB(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                patient.setPatientDOB(null);
            }
            patient.setPatientAddress(cursor.getString(3));
            patient.setPatientCardNumber(cursor.getInt(4));
            patient.setPatientGender(cursor.getString(5));
            try {
                patient.setPatientRedeemTime(formatter.parse(cursor.getString(6)));
            } catch (ParseException e) {
                patient.setPatientRedeemTime(null);
            }

            patients.add(patient);
        }
        return patients;
    }

    //Retrieves a single patient record with the given id
    public Pasien getPatient(long id) {
        Pasien patient = null;

        String sql = "SELECT * FROM " + DataBaseHelper.PATIENT_TABLE
                + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            patient = new Pasien();
            patient.setPatientId(cursor.getString(0));
            patient.setPatientName(cursor.getString(1));
            try {
                patient.setPatientDOB(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                patient.setPatientDOB(null);
            }
            patient.setPatientAddress(cursor.getString(3));
            patient.setPatientCardNumber(cursor.getInt(4));
            patient.setPatientGender(cursor.getString(5));
            try {
                patient.setPatientRedeemTime(formatter.parse(cursor.getString(6)));
            } catch (ParseException e) {
                patient.setPatientRedeemTime(null);
            }
        }
        return patient;
    }


}
