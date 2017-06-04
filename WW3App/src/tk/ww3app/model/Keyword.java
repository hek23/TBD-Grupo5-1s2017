/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.ww3app.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hek23
 */
@Entity
@Table(name = "Keyword")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Keyword.findAll", query = "SELECT k FROM Keyword k")
    , @NamedQuery(name = "Keyword.findByIdKeyword", query = "SELECT k FROM Keyword k WHERE k.idKeyword = :idKeyword")
    , @NamedQuery(name = "Keyword.findByWord", query = "SELECT k FROM Keyword k WHERE k.word = :word")})
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKeyword")
    private Integer idKeyword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "word")
    private String word;
    @JoinColumn(name = "creator", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User creator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keyword")
    private List<CountryStat> countryStatCollection;
    @OneToMany(mappedBy = "concepto")
    private List<Sinonimos> sinonimosList;

    public Keyword() {
    }

    public Keyword(Integer idKeyword) {
        this.idKeyword = idKeyword;
    }

    public Keyword(Integer idKeyword, String word) {
        this.idKeyword = idKeyword;
        this.word = word;
    }

    public Integer getIdKeyword() {
        return idKeyword;
    }

    public void setIdKeyword(Integer idKeyword) {
        this.idKeyword = idKeyword;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @XmlTransient
    public List<CountryStat> getCountryStatCollection() {
        return countryStatCollection;
    }

    public void setCountryStatCollection(List<CountryStat> countryStatCollection) {
        this.countryStatCollection = countryStatCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKeyword != null ? idKeyword.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Keyword)) {
            return false;
        }
        Keyword other = (Keyword) object;
        if ((this.idKeyword == null && other.idKeyword != null) || (this.idKeyword != null && !this.idKeyword.equals(other.idKeyword))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Keyword[ idKeyword=" + idKeyword + " ]";
    }
    
}
