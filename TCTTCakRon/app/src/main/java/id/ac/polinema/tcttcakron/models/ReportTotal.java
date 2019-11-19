package id.ac.polinema.tcttcakron.models;

public class ReportTotal {
    private String namaMenu;
    private int totalPenjualan;

    public ReportTotal() {
    }

    public ReportTotal(String namaMenu, int totalPenjualan) {
        this.namaMenu = namaMenu;
        this.totalPenjualan = totalPenjualan;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public int getTotalPenjualan() {
        return totalPenjualan;
    }

    public void setTotalPenjualan(int totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }
}
