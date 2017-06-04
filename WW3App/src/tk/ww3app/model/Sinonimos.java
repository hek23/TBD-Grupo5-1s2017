/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.ww3app.model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hek23
 */
@Entity
@Table(name = "Sinonimos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sinonimos.findAll", query = "SELECT s FROM Sinonimos s")
    , @NamedQuery(name = "Sinonimos.findByIdSinonimo", query = "SELECT s FROM Sinonimos s WHERE s.idSinonimo = :idSinonimo")
    , @NamedQuery(name = "Sinonimos.findBySinonimo", query = "SELECT s FROM Sinonimos s WHERE s.sinonimo = :sinonimo")})
public class Sinonimos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sinonimo")
    private Integer idSinonimo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sinonimo")
    private String sinonimo;
    @JoinColumn(name = "concepto", referencedColumnName = "idKeyword")
    @ManyToOne
    private Keyword concepto;

    public Sinonimos() {
    }

    public Sinonimos(Integer idSinonimo) {
        this.idSinonimo = idSinonimo;
    }

    public Sinonimos(Integer idSinonimo, String sinonimo) {
        this.idSinonimo = idSinonimo;
        this.sinonimo = sinonimo;
    }

    public Integer getIdSinonimo() {
        return idSinonimo;
    }

    public void setIdSinonimo(Integer idSinonimo) {
        this.idSinonimo = idSinonimo;
    }

    public String getSinonimo() {
        return sinonimo;
    }

    public void setSinonimo(String sinonimo) {
        this.sinonimo = sinonimo;
    }

    public Keyword getConcepto() {
        return concepto;
    }

    public void setConcepto(Keyword concepto) {
        this.concepto = concepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSinonimo != null ? idSinonimo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sinonimos)) {
            return false;
        }
        Sinonimos other = (Sinonimos) object;
        if ((this.idSinonimo == null && other.idSinonimo != null) || (this.idSinonimo != null && !this.idSinonimo.equals(other.idSinonimo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sinonimos[ idSinonimo=" + idSinonimo + " ]";
    }
    
}
