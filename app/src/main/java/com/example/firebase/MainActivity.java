package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button sign,reg;
    EditText email,pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        sign=findViewById(R.id.sign);
        sign.setOnClickListener(this);
        reg=findViewById(R.id.reg);
        reg.setOnClickListener(this);
        email=findViewById(R.id.email);
        email.setOnClickListener(this);
        pass=findViewById(R.id.pass);
        pass.setOnClickListener(this);

        if(mAuth.getCurrentUser()!=null){
            loggin();
        }
    }
    public void loggin(){
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        if(v==reg){
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Authenication failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else if(v==sign){
            mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"sign in success",Toast.LENGTH_LONG).show();
                        loggin();
                    }else{
                        Toast.makeText(getApplicationContext(),"login fail",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}