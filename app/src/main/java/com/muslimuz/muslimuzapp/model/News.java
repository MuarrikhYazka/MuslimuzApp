package com.muslimuz.muslimuzapp.model;


public class News {
    private String title;
    private String img;
    private String link;
    private String sumber;
    private String tanggal;
    private String teks;
    private String slug;

    public News() {
    }

    public News(String title, String img, String link, String sumber, String tanggal, String teks, String slug) {
        this.title = title;
        this.img = img;
        this.link = link;
        this.sumber = sumber;
        this.tanggal = tanggal;
        this.teks = teks;
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getLink() {
        return link;
    }

    public String getSumber() {
        return sumber;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTeks(){ return teks; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setTeks(String teks){ this.teks = teks; }
}
