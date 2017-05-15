/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.ww3app.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hek23
 */
@Entity
@Table(name = "CountryStat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CountryStat.findAll", query = "SELECT c FROM CountryStat c")
    , @NamedQuery(name = "CountryStat.findByIdTweetsCount", query = "SELECT c FROM CountryStat c WHERE c.idTweetsCount = :idTweetsCount")
    , @NamedQuery(name = "CountryStat.findByRetweetsCount", query = "SELECT c FROM CountryStat c WHERE c.retweetsCount = :retweetsCount")
    , @NamedQuery(name = "CountryStat.findByTweetsCount", query = "SELECT c FROM CountryStat c WHERE c.tweetsCount = :tweetsCount")
    , @NamedQuery(name = "CountryStat.findByDate", query = "SELECT c FROM CountryStat c WHERE c.date = :date")})
public class CountryStat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTweetsCount")
    private Integer idTweetsCount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "RetweetsCount")
    private String retweetsCount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TweetsCount")
    private String tweetsCount;
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "Country", referencedColumnName = "idCountry")
    @ManyToOne(optional = false)
    private Country country;
    @JoinColumn(name = "Keyword", referencedColumnName = "idKeyword")
    @ManyToOne(optional = false)
    private Keyword keyword;

    public CountryStat() {
    }

    public CountryStat(Integer idTweetsCount) {
        this.idTweetsCount = idTweetsCount;
    }

    public CountryStat(Integer idTweetsCount, String retweetsCount, String tweetsCount) {
        this.idTweetsCount = idTweetsCount;
        this.retweetsCount = retweetsCount;
        this.tweetsCount = tweetsCount;
    }

    public Integer getIdTweetsCount() {
        return idTweetsCount;
    }

    public void setIdTweetsCount(Integer idTweetsCount) {
        this.idTweetsCount = idTweetsCount;
    }

    public String getRetweetsCount() {
        return retweetsCount;
    }

    public void setRetweetsCount(String retweetsCount) {
        this.retweetsCount = retweetsCount;
    }

    public String getTweetsCount() {
        return tweetsCount;
    }

    public void setTweetsCount(String tweetsCount) {
        this.tweetsCount = tweetsCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTweetsCount != null ? idTweetsCount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CountryStat)) {
            return false;
        }
        CountryStat other = (CountryStat) object;
        if ((this.idTweetsCount == null && other.idTweetsCount != null) || (this.idTweetsCount != null && !this.idTweetsCount.equals(other.idTweetsCount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CountryStat[ idTweetsCount=" + idTweetsCount + " ]";
    }
    
    public void cambiarformatoFecha() throws ParseException { 
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String fechaString = this.getDate().toString(); // Convierte Date a String
        Date miFecha = formato.parse(fechaString); // convierte String a Date
        this.setDate(miFecha);
      }
}
    

