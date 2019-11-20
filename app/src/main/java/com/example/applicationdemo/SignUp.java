package com.example.applicationdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

public class SignUp extends AppCompatActivity {
    ProgressBar progressBar;
   TextView textView;
   Button button;
   EditText editText1,editText2;
   FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.TextViewId1);
        button=findViewById(R.id.EnterSignUpButtonId);
        editText1=findViewById(R.id.emailId);
        editText2=findViewById(R.id.passwordId);
        progressBar=findViewById(R.id.Id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),SingIn.class);
               startActivity(intent);
               finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=editText1.getText().toString().trim();
                String password=editText2.getText().toString().trim();

                if(email.isEmpty()){
                    editText1.setError("Enter Your Email");
                    editText1.requestFocus();
                    return;
                }

                //android.util.Patterns
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editText1.setError("Enter Valid Email");
                    editText1.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    editText2.setError("Enter Your Password");
                    editText2.requestFocus();
                    return;
                }
                if(password.length()<6){
                    editText2.setError("Please enter at least 6 charecture password");
                    editText2.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"User signup sucessfully",Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert;
                            alert=new AlertDialog.Builder(SignUp.this);
                            alert.setTitle("Now do you want access app?");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(getApplicationContext(),TabActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            AlertDialog alertDialog1=alert.create();
                            alertDialog1.show();

                        }
                        else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"User already registered",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Sorry!Can't added user",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}
