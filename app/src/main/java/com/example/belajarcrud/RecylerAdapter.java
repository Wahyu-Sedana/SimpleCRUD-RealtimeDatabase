package com.example.belajarcrud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.MyViewHolder> {

    private List<Biodata> biodataList;
    private Activity activity;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public RecylerAdapter(List<Biodata> biodataList, Activity activity) {
        this.biodataList = biodataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerAdapter.MyViewHolder holder, int position) {
        Biodata biodata = biodataList.get(position);
        holder.tv_nama.setText("Nama    : " + biodata.getNama());
        holder.tv_noTelp.setText("No Telp : " + biodata.getNo());
        holder.tv_alamat.setText("Alamat  : " + biodata.getAlamat());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialogForm = new DialogForm(biodata.getNama(),
                        biodata.getNo(),
                        biodata.getAlamat(),
                        biodata.getKey(),
                        "ubah");

                dialogForm.show(manager, "form");
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Data").child(biodata.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "Data berhasil di hapus", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Data gagal di hapus", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                }).setMessage("Apakah anda yakin ingin menghapus data ini?" + biodata.getNama());
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return biodataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nama, tv_noTelp, tv_alamat;
        CardView cardView;
        ImageView update, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_noTelp = itemView.findViewById(R.id.tv_noTelp);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            cardView = itemView.findViewById(R.id.card);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
