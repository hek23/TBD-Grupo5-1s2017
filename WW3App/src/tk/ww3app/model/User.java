/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.ww3app.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u.username FROM User u")
    , @NamedQuery(name = "User.findByIdUser", query = "SELECT u.username FROM User u WHERE u.idUser = :idUser")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u.username FROM User u WHERE u.username = :username")})
    //, @NamedQuery(name = "User.findByPassword", query = "SELECT u.username FROM User u WHERE u.password = :password")
    //, @NamedQuery(name = "User.findByPrivileges", query = "SELECT u.username FROM User u WHERE u.privileges = :privileges")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUser")
    private Integer idUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = true)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "privileges")
    private String privileges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private Collection<Keyword> keywordCollection;

    public User() {
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(Integer idUser, String username) {
        this.idUser = idUser;
        this.username = username;
        //this.password = password;
       //this.privileges = privileges;
    }
    
    public User(String username, String password){
    	this.username = username;
    	this.password = password;
    }

    public User(Integer idUser, String username, String password, String privileges) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.privileges = privileges;
    }
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    @XmlTransient
    public Collection<Keyword> getKeywordCollection() {
        return keywordCollection;
    }

    public void setKeywordCollection(Collection<Keyword> keywordCollection) {
        this.keywordCollection = keywordCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.User[ idUser=" + idUser + " ]";
    }
    
}

