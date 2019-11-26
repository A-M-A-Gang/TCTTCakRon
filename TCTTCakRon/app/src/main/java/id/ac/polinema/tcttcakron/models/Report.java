package id.ac.polinema.tcttcakron.models;

import java.util.Date;
import java.util.List;

public class Report {
    private String nama;
    private List<KeranjangMenu> foods;
    private String date;

    public Report() {
    }

    public Report(String nama, List<KeranjangMenu> foods, String date) {
        this.nama = nama;
        this.foods = foods;
        this.date = date;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<KeranjangMenu> getFoods() {
        return foods;
    }

    public void setFoods(List<KeranjangMenu> foods) {
        this.foods = foods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
