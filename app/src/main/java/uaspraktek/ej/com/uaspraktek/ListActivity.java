package uaspraktek.ej.com.uaspraktek;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uaspraktek.ej.com.uaspraktek.adapter.PasienAdapter;
import uaspraktek.ej.com.uaspraktek.db.PatientDAO;
import uaspraktek.ej.com.uaspraktek.fragments.CustomPatDialogFragment;
import uaspraktek.ej.com.uaspraktek.fragments.PatAddFragment;
import uaspraktek.ej.com.uaspraktek.fragments.PatientList;
import uaspraktek.ej.com.uaspraktek.pojo.Pasien;

public class ListActivity extends FragmentActivity implements CustomPatDialogFragment.EmpDialogFragmentListener {

    Context context = this;

//    private Toolbar toolbar;
//    private NavigationView navigationView;
//    private DrawerLayout dw;


    private Fragment contentFragment;
    private PatientList patientListFragment;
    private PatAddFragment patAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_list);
        setContentView(R.layout.activity_list);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(PatAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager
                            .findFragmentByTag(PatAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.add_emp);
                        contentFragment = fragmentManager
                                .findFragmentByTag(PatAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(PatientList.ARG_ITEM_ID) != null) {
                patientListFragment = (PatientList) fragmentManager
                        .findFragmentByTag(PatientList.ARG_ITEM_ID);
                contentFragment = patientListFragment;
            }
        } else {
            patientListFragment = new PatientList();
            setFragmentTitle(R.string.app_name);
            switchContent(patientListFragment, PatientList.ARG_ITEM_ID);
        }

        //Membuat default halaman yang ditampilkan ketika pertama kali
        //displayHalamanNav(R.id.menu_1);
    }

    private void switchContent(PatientList fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.fl_content, fragment, tag);
            // Only EmpAddFragment is added to the back stack.
            if (!(fragment instanceof PatientList)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        //Memanggil fungsi berdasarkan menu yang diklik
//        displayHalamanNav(id);
//
//        dw.closeDrawer(GravityCompat.START);
//
//        return true;
//    }

//    public void displayHalamanNav(int itemNav){
//
//        //Membuat Fragment V.4
//        Fragment fragment = new Fragment();
//
//        switch (itemNav){
//            case R.id.menu_0:
//                Toast.makeText(this, "Menu Tebus Obat", Toast.LENGTH_SHORT).show();
//                //fragment = new PatientList();
//                break;
//            case R.id.menu_1:
//                //Toast.makeText(this, "Menu List Pasien", Toast.LENGTH_SHORT).show();
//                fragment = new PatientList();
//                break;
//            case R.id.menu_2:
//                Toast.makeText(this, "Menu List Obat", Toast.LENGTH_SHORT).show();
//                //fragment = new MedicineList();
//                break;
//            case R.id.menu_3:
//                Toast.makeText(this, "Menu List Dokter", Toast.LENGTH_SHORT).show();
//                //fragment = new DoctorList();
//                break;
//            case R.id.menu_4:
//                Toast.makeText(this, "Menu Setting", Toast.LENGTH_SHORT).show();
//                //fragment = new Setting();
//                break;
//            case R.id.menu_logout:
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//
//        if(fragment != null){
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//            //Mereplace dengan FrameLayout
//
//            fragmentTransaction.replace(R.id.fl_content, fragment);
//            fragmentTransaction.commit();
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof PatAddFragment) {
            outState.putString("content", PatAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", PatientList.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                setFragmentTitle(R.string.add_emp);
                patAddFragment = new PatAddFragment();
                //Aneh
                switchContent(patientListFragment, PatAddFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof PatientList
                || fm.getBackStackEntryCount() == 0) {
            //Shows an alert dialog on quit
            onShowQuitDialog();
        }
    }

    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setMessage("Do You Want To Quit?");
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }


    @Override
    public void onFinishDialog() {
        if (patientListFragment != null) {
            patientListFragment.updateView();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getActionBar().setTitle(resourseId);
    }
}
