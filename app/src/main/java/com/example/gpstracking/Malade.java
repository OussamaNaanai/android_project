package com.example.gpstracking;

public class Malade {
    String nom,prenom,latitude,longitude;
   // Double latitude,longitude;
    public Malade(){}
    public Malade(String n,String p,String la,String lo){
        this.nom=n;
        this.prenom=p;
        this.latitude=la;
        this.longitude=lo;
    }
    public String getNom(){
        return this.nom;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
