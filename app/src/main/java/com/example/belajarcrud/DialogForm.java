package com.example.belajarcrud;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class DialogForm extends DialogFragment {

    String nama, alamat, no, key, pilih;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String nama, String no, String alamat, String key, String pilih){
        this.nama = nama;
        this.no = no;
        this.alamat = alamat;
        this.key = key;
        this.pilih = pilih;
    }

    TextView et_nama, et_no, et_alamat;
    Button btn_simpan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.input_form, container, false);

        et_nama  = view.findViewById(R.id.nama);
        et_no = view.findViewById(R.id.noTelp);
        et_alamat = view.findViewById(R.id.alamat);

        et_nama.setText(nama);
        et_no.setText(no);
        et_alamat.setText(alamat);

        btn_simpan = view.findViewById(R.id.simpann);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString();
                String no = et_no.getText().toString();
                String alamat = et_alamat.getText().toString();

                if (TextUtils.isEmpty(nama)) {
                    input((EditText) et_nama, "Nama");
                } else if (TextUtils.isEmpty(no)) {
                    input((EditText) et_no, "No");
                } else if (TextUtils.isEmpty(alamat)) {
                    input((EditText) et_alamat, "Alamat");
                } else {
                    if(pilih.equals("Tambah")){
                        databaseReference.child("Data").push().setValue(new Biodata(nama, no, alamat)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(), "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if(pilih.equals("ubah")){
                        databaseReference.child("Data").child(key).setValue(new Biodata(nama, no, alamat)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(), "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data gagal diupdate", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        return view;
    }

    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
           dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void input(EditText txt, String s) {

    txt.setError(s+ " tidak boleh kosong");
    txt.requestFocus();
    }
}
