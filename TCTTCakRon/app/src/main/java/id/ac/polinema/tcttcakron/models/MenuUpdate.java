package id.ac.polinema.tcttcakron.models;

public class MenuUpdate {
    private String nameImage, imageUrl;
    private int harga;

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public  MenuUpdate(String nameImage, String imageUrl, int harga) {
        this.nameImage = nameImage;
        this.imageUrl = imageUrl;
        this.harga = harga;
    }

    public MenuUpdate() {
    }
}