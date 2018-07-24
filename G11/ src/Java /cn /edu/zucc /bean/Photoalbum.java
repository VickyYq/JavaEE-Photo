package cn.edu.zucc.bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Photoalbum {
    private int paid;
    private String paname;
    private String padetail;
    private String palimit;
    private String userid;
    private Timestamp pacreatedate;
    private String paurl;
    private int count;
    private Collection<Photo> photosByPaid;
    private User userByUserid;

    @Id
    @Column(name = "paid")
    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    @Basic
    @Column(name = "paname")
    public String getPaname() {
        return paname;
    }

    public void setPaname(String paname) {
        this.paname = paname;
    }

    @Basic
    @Column(name = "padetail")
    public String getPadetail() {
        return padetail;
    }

    public void setPadetail(String padetail) {
        this.padetail = padetail;
    }

    @Basic
    @Column(name = "palimit")
    public String getPalimit() {
        return palimit;
    }

    public void setPalimit(String palimit) {
        this.palimit = palimit;
    }

    @Basic
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "pacreatedate")
    public Timestamp getPacreatedate() {
        return pacreatedate;
    }

    public void setPacreatedate(Timestamp pacreatedate) {
        this.pacreatedate = pacreatedate;
    }

    @Basic
    @Column(name = "paurl")
    public String getPaurl() {
        return paurl;
    }

    public void setPaurl(String paurl) {
        this.paurl = paurl;
    }

    @Basic
    @Column(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photoalbum that = (Photoalbum) o;

        if (paid != that.paid) return false;
        if (count != that.count) return false;
        if (paname != null ? !paname.equals(that.paname) : that.paname != null) return false;
        if (padetail != null ? !padetail.equals(that.padetail) : that.padetail != null) return false;
        if (palimit != null ? !palimit.equals(that.palimit) : that.palimit != null) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (pacreatedate != null ? !pacreatedate.equals(that.pacreatedate) : that.pacreatedate != null) return false;
        if (paurl != null ? !paurl.equals(that.paurl) : that.paurl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paid;
        result = 31 * result + (paname != null ? paname.hashCode() : 0);
        result = 31 * result + (padetail != null ? padetail.hashCode() : 0);
        result = 31 * result + (palimit != null ? palimit.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (pacreatedate != null ? pacreatedate.hashCode() : 0);
        result = 31 * result + (paurl != null ? paurl.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }

    @OneToMany(mappedBy = "photoalbumByPaid")
    public Collection<Photo> getPhotosByPaid() {
        return photosByPaid;
    }

    public void setPhotosByPaid(Collection<Photo> photosByPaid) {
        this.photosByPaid = photosByPaid;
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    public User getUserByUserid() {
        return userByUserid;
    }

    public void setUserByUserid(User userByUserid) {
        this.userByUserid = userByUserid;
    }
}
