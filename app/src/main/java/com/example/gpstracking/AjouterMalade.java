package com.example.gpstracking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import static android.widget.Toast.LENGTH_LONG;

public class AjouterMalade extends AppCompatActivity {


    private static final int CAMERA_PIC_REQUEST  = 1888;
    Bitmap theImage;
    String photo;
    EditText nom,prenom;
    Button add,camera;
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_malade);
        nom=findViewById(R.id.nom);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        prenom=findViewById(R.id.prenom);
        add=findViewById(R.id.ajouter);
        camera = findViewById(R.id.camera);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomS=nom.getText().toString().trim();
                String prenomS=prenom.getText().toString().trim();
                if(nomS.isEmpty()){
                    nom.setError("Veuillez remplir le nom");
                    nom.requestFocus();
                    return;
                }
                else if(prenomS.isEmpty()){
                    prenom.setError("Veuillez remplir le prenom");
                    prenom.requestFocus();
                    return;
                }
                else{
                    user=auth.getCurrentUser();
                    Malade m=new Malade(nomS,prenomS,"","");
                    mDatabase.child(user.getUid()).child("malade").setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //startActivity(new Intent(AjouterMalade.this, ProfileFragment.class));
                            Toast.makeText(AjouterMalade.this,"Ajouter avec succés",LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }
                    );
                }
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = findViewById(R.id.imageView1); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
        }
    }


}
