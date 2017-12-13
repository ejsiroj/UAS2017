package uaspraktek.ej.com.uaspraktek.adapter;

/**
 * Created by EJ Public on 13/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

import uaspraktek.ej.com.uaspraktek.ListActivity;
import uaspraktek.ej.com.uaspraktek.R;
import uaspraktek.ej.com.uaspraktek.pojo.Pasien;

public class PasienAdapter  extends RecyclerView.Adapter<PasienAdapter.ViewHoder> {
    private List<Pasien> pasienList;

    private Context context;

    //Fragment Manager
    private static FragmentManager fragmentManager;

    public PasienAdapter(Context context, List<Pasien> pasienList){
        //super(context, R.layout.item_pasien, pasienList);

        this.context = context;
        this.pasienList = pasienList;
    }

    public Context getContext() {
        return context;
    }

    public void setItem(List<Pasien> newPasien){
        this.pasienList = newPasien;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_pasien, parent,false);

        ViewHoder pasienVh = new ViewHoder(view);
        return pasienVh;
    }

    @Override
    public void onBindViewHolder(final ViewHoder holder, int position) {
        final Pasien newPasien = pasienList.get(position) ;

        TextView tvNamaPasien = holder.namaPasien;
        TextView tvNoReg = holder.noReg;

        tvNamaPasien.setText(newPasien.getPatientName());
        tvNoReg.setText(newPasien.getPatientId());

        //Clickable
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("HASIL", "onClick: "+newPasien.getNamaPasien());
                //Toast.makeText(v.getContext(), newPasien.getNamaPasien(), Toast.LENGTH_SHORT).show();

                //Membuat Bundle
//                Fragment pasienDetail = new PasienDetail();
//                Bundle data = new Bundle();
//                data.putString("pasien", newPasien.getNamaPasien());
//
//                pasienDetail.setArguments(data);
//
//                fragmentManager.beginTransaction().replace(R.id.fl_content, pasienDetail).commit();

                //Parcelable
                Intent i = new Intent(v.getContext(), ListActivity.class);
                i.putExtra("KEY_PASIEN", newPasien);

                holder.itemView
                        .getContext()
                        .startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pasienList.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {

        public TextView namaPasien;
        public TextView noReg;

        public ViewHoder(View itemView) {
            super(itemView);
            namaPasien = itemView.findViewById(R.id.tv_nama_pasien);
            noReg = itemView.findViewById(R.id.tv_no_registrasi);
        }
    }
}
