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
@Table(name = "CountryResume")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CountryResume.findAll", query = "SELECT c FROM CountryResume c")
    , @NamedQuery(name = "CountryResume.findByName", query = "SELECT c FROM CountryResume c WHERE c.name = :name")
    , @NamedQuery(name = "CountryResume.findByTweetsCount", query = "SELECT c FROM CountryResume c WHERE c.tweetsCount = :tweetsCount")
    , @NamedQuery(name = "CountryResume.findByReTweetsCount", query = "SELECT c FROM CountryResume c WHERE c.reTweetsCount = :reTweetsCount")})
public class CountryResume implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 52)
    @Column(name = "Name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "TweetsCount")
    private Double tweetsCount;
    @Id
    @Column(name = "ReTweetsCount")
    private Double reTweetsCount;

    public CountryResume() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTweetsCount() {
        return tweetsCount;
    }

    public void setTweetsCount(Double tweetsCount) {
        this.tweetsCount = tweetsCount;
    }

    public Double getReTweetsCount() {
        return reTweetsCount;
    }

    public void setReTweetsCount(Double reTweetsCount) {
        this.reTweetsCount = reTweetsCount;
    }
    
}
