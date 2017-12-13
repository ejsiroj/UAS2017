package uaspraktek.ej.com.uaspraktek.fragments;

/**
 * Created by EJ Public on 13/12/2017.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import uaspraktek.ej.com.uaspraktek.ListActivity;
import uaspraktek.ej.com.uaspraktek.R;
import uaspraktek.ej.com.uaspraktek.db.PatientDAO;
import uaspraktek.ej.com.uaspraktek.pojo.Pasien;

public class CustomPatDialogFragment extends DialogFragment {

    // UI references
    private EditText patNameET;
    private EditText patAddressET;
    private EditText patDobET;
    private EditText patcardNumberET;
    private RadioButton patGenderET;
    private LinearLayout submitLayout;

    private Pasien pasien;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    PatientDAO patientDAO;

    public static final String ARG_ITEM_ID = "emp_dialog_fragment";

    public interface EmpDialogFragmentListener {
        void onFinishDialog();
    }

    public CustomPatDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        patientDAO = new PatientDAO(getActivity());

        Bundle bundle = this.getArguments();
        pasien = bundle.getParcelable("selectedPatient");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_patient,
                null);
        builder.setView(customDialogView);

        patNameET = (EditText) customDialogView.findViewById(R.id.etxt_name);
        patAddressET = (EditText) customDialogView
                .findViewById(R.id.etxt_address);
        patDobET = (EditText) customDialogView.findViewById(R.id.etxt_dob);
        patcardNumberET = (EditText) customDialogView.findViewById(R.id.etxt_card);
        patGenderET = (RadioButton) customDialogView.findViewById(R.id.rb_gender);
        submitLayout = (LinearLayout) customDialogView
                .findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);

        setValue();

        builder.setTitle(R.string.update_emp);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            pasien.setPatientDOB(formatter.parse(patDobET.getText().toString()));
                        } catch (ParseException e) {
                            Toast.makeText(getActivity(),
                                    "Invalid date format!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pasien.setPatientName(patNameET.getText().toString());
                        pasien.setPatientAddress(patAddressET.getText().toString());
                        pasien.setPatientCardNumber(Integer.parseInt(patcardNumberET.getText().toString()));
                        pasien.setPatientGender(patGenderET.getText().toString());

                        long result = patientDAO.update(pasien);
                        if (result > 0) {
                            ListActivity activity = (ListActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update patient",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setValue() {
        if (pasien != null) {
            patNameET.setText(pasien.getPatientName());
            patDobET.setText(formatter.format(pasien.getPatientDOB()));
            patAddressET.setText(pasien.getPatientAddress());
            patcardNumberET.setText(pasien.getPatientAddress());
            //patGenderET.setText(pasien.getPatientGender());
        }
    }
}
