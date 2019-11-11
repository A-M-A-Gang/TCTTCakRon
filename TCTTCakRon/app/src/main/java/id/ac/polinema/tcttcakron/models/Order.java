package id.ac.polinema.tcttcakron.models;

import java.util.List;

public class Order {
    private String nama;
    private List<KeranjangMenu> foods;

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

    public Order(String nama, List<KeranjangMenu> foods) {
        this.nama = nama;
        this.foods = foods;
    }

    public Order() {
    }
}
