package uaspraktek.ej.com.uaspraktek.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uaspraktek.ej.com.uaspraktek.R;
import uaspraktek.ej.com.uaspraktek.db.PatientDAO;
import uaspraktek.ej.com.uaspraktek.pojo.Pasien;

/**
 * Created by EJ Public on 13/12/2017.
 */

public class PatAddFragment extends Fragment implements View.OnClickListener {

    // UI references
    private EditText patNameET;
    private EditText patAddressET;
    private EditText patDobET;
    private EditText patcardNumberET;
    private RadioButton patGenderET;
    private Button addButton;
    private Button resetButton;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;

    Pasien pasien = null;
    private PatientDAO patientDAO;
    private AddPatTask task;

    public static final String ARG_ITEM_ID = "pat_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientDAO = new PatientDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_patient, container,
                false);

        findViewsById(rootView);

        setListeners();

        //For orientation change.
        if (savedInstanceState != null) {
            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));
        }

        return rootView;
    }

    private void findViewsById(View rootView) {
        patNameET = (EditText) rootView.findViewById(R.id.etxt_name);
        patAddressET = (EditText) rootView
                .findViewById(R.id.etxt_address);
        patcardNumberET = (EditText) rootView.findViewById(R.id.etxt_card);
        patGenderET = (RadioButton) rootView.findViewById(R.id.rb_gender);

        patDobET = (EditText) rootView.findViewById(R.id.etxt_dob);
        patDobET.setInputType(InputType.TYPE_NULL);

        addButton = (Button) rootView.findViewById(R.id.button_add);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    private void setListeners() {
        patDobET.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        patDobET.setText(formatter.format(dateCalendar
                                .getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == patDobET) {
            datePickerDialog.show();
        } else if (view == addButton) {
            setEmployee();

            task = new AddPatTask(getActivity());
            task.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    private void resetAllFields() {
        patNameET.setText("");
        patDobET.setText("");
        patAddressET.setText("");
        patcardNumberET.setText("");
    }

    private void setEmployee() {
        pasien = new Pasien();
        pasien.setPatientName(patNameET.getText().toString());
        pasien.setPatientAddress(patAddressET.getText().toString());
        pasien.setPatientCardNumber(Integer.parseInt(patcardNumberET.getText().toString()));
        pasien.setPatientGender(patGenderET.getText().toString());
        if (dateCalendar != null)
            pasien.setPatientDOB(dateCalendar.getTime());
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.add_emp);
        getActivity().getActionBar().setTitle(R.string.add_emp);
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (dateCalendar != null)
            outState.putLong("dateCalendar", dateCalendar.getTime().getTime());
    }

    public class AddPatTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddPatTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = patientDAO.save(pasien);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Patient Saved",
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}
