package com.example.gpstracking;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView membre;
    FirebaseAuth mAuth;
    EditText Log;
    EditText Pass;
    Button Conn;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        membre=findViewById(R.id.membre);
        Log=findViewById(R.id.LoginTxt);
        Pass=findViewById(R.id.PassTxt);
        Conn=findViewById(R.id.BtnConn);
        membre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NouveauMembre.class));
            }});
        Conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    email = Log.getText().toString().trim();
                    password=Pass.getText().toString().trim();
                    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Log.setError("Email non Valid");
                        Log.requestFocus();
                        return;
                    }
                    else if (password.isEmpty() || password.length() < 6) {
                        Pass.setError("le mot de passe il faut dépasser plus de 6 characteres");
                        Pass.requestFocus();
                        return;
                    }
                   else{
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Connexion","Veuillez patientez ...", true);
                                        //dialog.show();
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(MainActivity.this, Map.class));
                                            Toast.makeText(MainActivity.this, "Sign successfully", Toast.LENGTH_LONG).show();
                                            finish();

                                        } else {
                                           // dialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Vous netes pas un membre !!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }

            }
        });
    }
}
