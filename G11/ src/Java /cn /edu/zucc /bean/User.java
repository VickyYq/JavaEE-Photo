package cn.edu.zucc.bean;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {
    private String userid;
    private String userpwd;
    private String username;
    private double usercapacity;
    private String email;
    private Collection<Photoalbum> photoalbumsByUserid;

    @Id
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "userpwd")
    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "usercapacity")
    public double getUsercapacity() {
        return usercapacity;
    }

    public void setUsercapacity(double usercapacity) {
        this.usercapacity = usercapacity;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (Double.compare(user.usercapacity, usercapacity) != 0) return false;
        if (userid != null ? !userid.equals(user.userid) : user.userid != null) return false;
        if (userpwd != null ? !userpwd.equals(user.userpwd) : user.userpwd != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (userpwd != null ? userpwd.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        temp = Double.doubleToLongBits(usercapacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserid")
    public Collection<Photoalbum> getPhotoalbumsByUserid() {
        return photoalbumsByUserid;
    }

    public void setPhotoalbumsByUserid(Collection<Photoalbum> photoalbumsByUserid) {
        this.photoalbumsByUserid = photoalbumsByUserid;
    }
}
