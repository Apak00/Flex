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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_login;
    private EditText loginetemail;
    private EditText loginetpassword;
    private TextView twregister;
private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),FlancerMain.class));

        }
        button_login = (Button) findViewById(R.id.button_Login);
        loginetemail = (EditText) findViewById(R.id.Loginetemail);
        loginetpassword = (EditText) findViewById(R.id.Loginetpassword);
        twregister = (TextView) findViewById(R.id.twRegister);

        button_login.setOnClickListener(this);
        twregister.setOnClickListener(this);
    }
    private void userLogin(){
        String email = loginetemail.getText().toString().trim();
        String password = loginetpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Logining..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                           finish();
                           startActivity(new Intent(getApplicationContext(),FlancerMain.class));
                       }else {
                           Toast.makeText(LoginActivity.this, "Login Failed... Pls Try Again",
                                   Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view==button_login){
            userLogin();
        }
        if(view==twregister){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
