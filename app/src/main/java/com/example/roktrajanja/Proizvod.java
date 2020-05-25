package com.example.roktrajanja;

public class Proizvod {

    int slika;

    String naziv,datum;

    public Proizvod(int slika, String naziv, String datum) {
        this.slika = slika;
        this.naziv = naziv;
        this.datum = datum;
    }

    public int getSlika() {
        return slika;
    }

    public void setSlika(int slika) {
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
