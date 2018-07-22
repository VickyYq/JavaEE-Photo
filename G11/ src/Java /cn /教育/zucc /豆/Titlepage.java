package cn.edu.zucc.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Titlepage {
    private int tpid;
    private String tpurl;
    private int tppaid;

    @Id
    @Column(name = "tpid")
    public int getTpid() {
        return tpid;
    }

    public void setTpid(int tpid) {
        this.tpid = tpid;
    }

    @Basic
    @Column(name = "tpurl")
    public String getTpurl() {
        return tpurl;
    }

    public void setTpurl(String tpurl) {
        this.tpurl = tpurl;
    }

    @Basic
    @Column(name = "tppaid")
    public int getTppaid() {
        return tppaid;
    }

    public void setTppaid(int tppaid) {
        this.tppaid = tppaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Titlepage titlepage = (Titlepage) o;

        if (tpid != titlepage.tpid) return false;
        if (tppaid != titlepage.tppaid) return false;
        if (tpurl != null ? !tpurl.equals(titlepage.tpurl) : titlepage.tpurl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tpid;
        result = 31 * result + (tpurl != null ? tpurl.hashCode() : 0);
        result = 31 * result + tppaid;
        return result;
    }
}
