package id.ac.polinema.tcttcakron;

public class KeranjangMenu {
    private String namaMenu;
    private int harga, jumlah;

    public KeranjangMenu() {
    }

    public KeranjangMenu(String namaMenu, int harga, int jumlah) {
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
