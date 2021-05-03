package com.example.belajarcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContentMain extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecylerAdapter recylerAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<Biodata> biodataArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);

        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        floatingActionButton = findViewById(R.id.action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm dialogForm = new DialogForm("", "", "","","Tambah");
                dialogForm.show(getSupportFragmentManager(), "form");
            }
        });

        showData();
    }

    private void showData() {
            databaseReference.child("Data").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    biodataArrayList = new ArrayList<>();
                    for(DataSnapshot item : snapshot.getChildren()) {
                        Biodata biodata = item.getValue(Biodata.class);
                        biodata.setKey(item.getKey());
                        biodataArrayList.add(biodata);
                    }

                    recylerAdapter = new RecylerAdapter(biodataArrayList, ContentMain.this);
                    recyclerView.setAdapter(recylerAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}