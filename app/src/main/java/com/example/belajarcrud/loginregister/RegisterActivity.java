package com.example.belajarcrud.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belajarcrud.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText regisUsername, regisEmail, regisPassword;
    private Button btnSignup;
    private ProgressDialog progressDialog;
    private TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        auth = FirebaseAuth.getInstance();
        
        regisUsername = findViewById(R.id.regisUsername);
        regisEmail = findViewById(R.id.regisEmail);
        regisPassword = findViewById(R.id.regisPassword);

        progressDialog = new ProgressDialog(this);

        linkLogin = findViewById(R.id.linkLogin);
        linkLogin.setOnClickListener(this);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linkLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.btnSignup:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String username = regisUsername.getText().toString().trim();
        String email = regisEmail.getText().toString().trim();
        String password = regisPassword.getText().toString().trim();

        if(username.isEmpty()){
            regisUsername.setError("username is required");
            regisUsername.requestFocus();
            return;
        }

        if(email.isEmpty()){
            regisEmail.setError("email is required");
            regisEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regisEmail.setError("harap berikan email yang valid");
            regisEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            regisPassword.setError("password is required");
            regisPassword.requestFocus();
            return;
        }

        if(password.length()< 6){
            regisPassword.setError("password minimal 6 karakter");
            regisPassword.requestFocus();
            return;
        }

        progressDialog.setMessage("harap tunggu");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Aku anda berhasil terdafter",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "gagal melakukan pendaftaran", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}