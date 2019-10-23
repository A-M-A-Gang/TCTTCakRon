package id.ac.polinema.tcttcakron;

public class Upload {
    private String nameImage, imageUrl;
    private int harga;


    public Upload(String nameImage, String imageUrl, int harga) {
        if (nameImage.trim().equals("")) {
            nameImage = "No Name";
        }
        this.imageUrl = imageUrl;
        this.nameImage = nameImage;
        this.harga = harga;

    }

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
}
