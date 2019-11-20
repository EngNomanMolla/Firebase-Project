package com.example.applicationdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SingIn extends AppCompatActivity implements View.OnClickListener {
   TextView textView;
   EditText editText1,editText2;
   Button button;
   ProgressBar progressBar;
   FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        editText1=findViewById(R.id.emailId);
        editText2=findViewById(R.id.passwordId);
        progressBar=findViewById(R.id.progressbarId);
        button=findViewById(R.id.enterSignButtonId);
        button.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.TextViewId2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.enterSignButtonId){
           signIn();
       }
    }

    private void signIn() {
        String email=editText1.getText().toString().trim();
        String password=editText2.getText().toString().trim();
        if(email.isEmpty()){
            editText1.setError("Enter your email");
            editText1.requestFocus();
            return;


        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText1.setError("Enter valid email");
            editText1.requestFocus();
            return;

        }
        if(password.isEmpty()){
            editText2.setError("Enter your password");
            editText2.requestFocus();
            return;
        }
        if (password.length()<6){
            editText2.setError("Please enter at least 6 charecture password");
            editText2.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Intent intent=new Intent(getApplicationContext(),TabActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Login Sucessfull",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Login not Sucessfull",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
