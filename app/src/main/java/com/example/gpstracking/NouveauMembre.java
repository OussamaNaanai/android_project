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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;

public class NouveauMembre extends AppCompatActivity {
    EditText nom,prenom,mail,pass,repass;
    String nomS,prenomS,mailS,passS,rpassS;
    Button Enr;
    DatabaseReference mDatabase;
    FirebaseAuth Fauth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_membre);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Fauth = FirebaseAuth.getInstance();
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.pass);
        repass=findViewById(R.id.Repass);
        Enr=findViewById(R.id.BtnCreer);
        Enr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomS=nom.getText().toString().trim();
                prenomS=prenom.getText().toString().trim();
                mailS=mail.getText().toString().trim();
                passS=pass.getText().toString().trim();
                rpassS=repass.getText().toString().trim();
                if(nomS.isEmpty()){
                    nom.setError("Veuillez remplir le nom");
                    nom.requestFocus();
                    return;
                }
                else if(prenomS.isEmpty()){
                    prenom.setError("Veuillez remplir le nom");
                    prenom.requestFocus();
                    return;
                }
                else if (mailS.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mailS).matches()) {
                    mail.setError("Email non Valid");
                    mail.requestFocus();
                    return;
                }
                else if (passS.isEmpty() || passS.length() < 6) {
                    pass.setError("le mot de passe faut dépasser plus de 6 characteres");
                    pass.requestFocus();
                    return;
                }
                else if(rpassS.isEmpty()){
                    repass.setError("Veuillez remplir");
                    repass.requestFocus();
                    return;
                }
                else if(!rpassS.equals(passS)){
                    Toast.makeText(NouveauMembre.this,"Les mots de passe ne sont pas identiques!!",LENGTH_LONG).show();
                }
                else {
                    Fauth.createUserWithEmailAndPassword(mailS,passS).addOnCompleteListener(NouveauMembre.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                user=Fauth.getCurrentUser();
                                final Malade mm=new Malade("","","","");
                                Toast.makeText(NouveauMembre.this,"En cours ...", LENGTH_LONG).show();
                                Membre m=new Membre(nomS,prenomS,mailS,passS,mm);
                                mDatabase.child(user.getUid()).setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            startActivity(new Intent(NouveauMembre.this, Map.class));
                                            Toast.makeText(NouveauMembre.this,"Créer avec Succés", LENGTH_LONG).show();
                                            //mDatabase.child(user.getUid()).child("Malade").setValue(mm);
                                            finish();
                                        }
                                        else {Toast.makeText(NouveauMembre.this,"un probleme a été generer !!!", LENGTH_LONG).show();}
                                    }
                                });
                            }
                        }
                    });
                }

            }});

    }
}

