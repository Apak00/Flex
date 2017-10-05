package com.example.apak.flancer.Entry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apak.flancer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_register;
    private EditText etemail;
    private EditText etpassword;
    private TextView twsingin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        button_register = (Button) findViewById(R.id.button_register);
        etemail = (EditText) findViewById(R.id.etemail);
        etpassword = (EditText) findViewById(R.id.etpassword);
        twsingin = (TextView) findViewById(R.id.twSingin);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),FlancerMain.class));
        }
        button_register.setOnClickListener(this);
        twsingin.setOnClickListener(this);
    }
    private void registerUser() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Registering..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(),FlancerMain.class));
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Register Failed...Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view==button_register){
            registerUser();
        }
        if(view==twsingin){
            finish();
            startActivity(new Intent(this,LoginActivity.class));

        }

    }
}
