package com.example.roktrajanja;

public class Proizvod {

    int id;
    byte[] slika;

    String naziv,datum;

    public Proizvod(int id, byte[] slika, String naziv, String datum) {
        this.id = id;
        this.slika = slika;
        this.naziv = naziv;
        this.datum = datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
