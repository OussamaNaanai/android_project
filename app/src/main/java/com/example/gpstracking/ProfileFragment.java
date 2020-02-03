package com.example.gpstracking;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference database;
    TextView nom ;
    ImageView logout,add;
    private View v;

    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v=view;
        init();
        load();
        CheckMalade();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                if (auth.getCurrentUser()==null){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Toast.makeText(getActivity(),"Vous êtes déconnecter",Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                else if (auth.getCurrentUser()!=null){
                    Toast.makeText(getActivity(),"Vous n'êtes pas deconnecter !!!",Toast.LENGTH_LONG).show();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AjouterMalade.class));
            }
        });
    }
    public void init(){
        nom=v.findViewById(R.id.nom);
        logout=v.findViewById(R.id.signout);
        add=v.findViewById(R.id.ajouter);
    }
    public void load(){
        user=auth.getCurrentUser();
        //database=database.child(user.getUid()+"/");
        database.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Membre u=dataSnapshot.getValue(Membre.class);
                nom.setText(u.getNom()+" "+u.getPrenom());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void CheckMalade(){
        final ImageView ajouter=v.findViewById(R.id.ajouter);
        final TextView malade=v.findViewById(R.id.malade);
        final TextView lblajouter=v.findViewById(R.id.lblajouter);
        user=auth.getCurrentUser();
        database.child(user.getUid()).child("malade").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Malade m=dataSnapshot.getValue(Malade.class);
                if (!m.getNom().isEmpty()){
                    ajouter.setImageResource(0);
                    lblajouter.setVisibility(View.INVISIBLE);
                    malade.setText("Votre malade :      "+m.getNom());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
