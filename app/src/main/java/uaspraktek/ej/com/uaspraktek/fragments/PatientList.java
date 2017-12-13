package uaspraktek.ej.com.uaspraktek.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import uaspraktek.ej.com.uaspraktek.R;
import uaspraktek.ej.com.uaspraktek.adapter.PasienAdapter;
import uaspraktek.ej.com.uaspraktek.db.PatientDAO;
import uaspraktek.ej.com.uaspraktek.pojo.Pasien;

/**
 * Created by EJ Public on 13/12/2017.
 */

public class PatientList extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "patient_list";


    Context context = getActivity();

    RecyclerView rvPasien;
    List<Pasien> pasienList;
    PasienAdapter pasienAdapter;
    PatientDAO patientDAO;

    private GetPatTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        patientDAO = new PatientDAO(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pasien_list, container, false);
        findViewsById(rootView);


        pasienList =  new ArrayList<>();

        task = new GetPatTask((Activity) context);
        task.execute((Void) null);

        //berdasar findViewsById
        rvPasien.setLayoutManager(new LinearLayoutManager(context));
        rvPasien.setAdapter(pasienAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Menu List Teman");
    }

    private void findViewsById(View view) {
        rvPasien = view.findViewById(R.id.rv_pasien);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pasien patient = (Pasien) parent.getItemAtPosition(position);

        if (patient != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedPatient", patient);
            CustomPatDialogFragment customPatDialogFragment = new CustomPatDialogFragment();
            customPatDialogFragment.setArguments(arguments);
            customPatDialogFragment.show(getFragmentManager(),
                    CustomPatDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public class GetPatTask extends AsyncTask<Void, Void, ArrayList<Pasien>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetPatTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Pasien> doInBackground(Void... arg0) {
            ArrayList<Pasien> patientList = patientDAO.getPatients();
            return patientList;
        }

        @Override
        protected void onPostExecute(ArrayList<Pasien> empList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("PATIENT", empList.toString());
                pasienList = empList;
                if (empList != null) {
                    if (empList.size() != 0) {
                        pasienAdapter = new PasienAdapter(context,
                                empList);
                        rvPasien.setAdapter(pasienAdapter);
                    } else {
                        Toast.makeText(context, "No Patient Records",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }

    /*
	 * This method is invoked from MainActivity onFinishDialog() method. It is
	 * called from CustomEmpDialogFragment when an employee record is updated.
	 * This is used for communicating between fragments.
	 */
    public void updateView() {
        task = new GetPatTask((Activity) context);
        task.execute((Void) null);
    }
}
