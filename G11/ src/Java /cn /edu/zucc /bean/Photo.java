package cn.edu.zucc.bean;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Photo {
    private int photoid;
    private String photodetail;
    private Timestamp photocreatdate;
    private String photourl;
    private String exphoto;
    private int paid;
    private double size;
    private Photoalbum photoalbumByPaid;

    @Id
    @Column(name = "photoid")
    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    @Basic
    @Column(name = "photodetail")
    public String getPhotodetail() {
        return photodetail;
    }

    public void setPhotodetail(String photodetail) {
        this.photodetail = photodetail;
    }

    @Basic
    @Column(name = "photocreatdate")
    public Timestamp getPhotocreatdate() {
        return photocreatdate;
    }

    public void setPhotocreatdate(Timestamp photocreatdate) {
        this.photocreatdate = photocreatdate;
    }

    @Basic
    @Column(name = "photourl")
    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    @Basic
    @Column(name = "exphoto")
    public String getExphoto() {
        return exphoto;
    }

    public void setExphoto(String exphoto) {
        this.exphoto = exphoto;
    }

    @Basic
    @Column(name = "paid")
    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    @Basic
    @Column(name = "size")
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (photoid != photo.photoid) return false;
        if (paid != photo.paid) return false;
        if (Double.compare(photo.size, size) != 0) return false;
        if (photodetail != null ? !photodetail.equals(photo.photodetail) : photo.photodetail != null) return false;
        if (photocreatdate != null ? !photocreatdate.equals(photo.photocreatdate) : photo.photocreatdate != null)
            return false;
        if (photourl != null ? !photourl.equals(photo.photourl) : photo.photourl != null) return false;
        if (exphoto != null ? !exphoto.equals(photo.exphoto) : photo.exphoto != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = photoid;
        result = 31 * result + (photodetail != null ? photodetail.hashCode() : 0);
        result = 31 * result + (photocreatdate != null ? photocreatdate.hashCode() : 0);
        result = 31 * result + (photourl != null ? photourl.hashCode() : 0);
        result = 31 * result + (exphoto != null ? exphoto.hashCode() : 0);
        result = 31 * result + paid;
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "paid", referencedColumnName = "paid", nullable = false)
    public Photoalbum getPhotoalbumByPaid() {
        return photoalbumByPaid;
    }

    public void setPhotoalbumByPaid(Photoalbum photoalbumByPaid) {
        this.photoalbumByPaid = photoalbumByPaid;
    }
}
