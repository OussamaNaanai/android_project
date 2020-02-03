package com.example.gpstracking;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Member;

public class Membre {
    String nom;
    String prenom;
    String mail;
    String pass;
    Malade malade;
    public Membre(String nom,String prenom,String mail,String pass,Malade m){
        this.nom=nom;
        this.prenom=prenom;
        this.mail=mail;
        this.pass=pass;
        this.malade=m;
    }
    Membre(){}
    public String getNom(){
        return this.nom;
    }
    public String getPrenom(){
        return this.prenom;
    }
}
