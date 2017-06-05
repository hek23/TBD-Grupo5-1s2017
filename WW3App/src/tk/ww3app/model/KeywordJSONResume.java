/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.ww3app.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Immutable;

/**
 *
 * @author hek23
 */
@Entity 
@IdClass(KeywordJSONResume.class)
@Immutable
@Table(name = "KeywordJSONResume")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeywordJSONResume.findAll", query = "SELECT k FROM KeywordJSONResume k")
    , @NamedQuery(name = "KeywordJSONResume.findByRtc", query = "SELECT k FROM KeywordJSONResume k WHERE k.rtc = :rtc")
    , @NamedQuery(name = "KeywordJSONResume.findByTc", query = "SELECT k FROM KeywordJSONResume k WHERE k.tc = :tc")
    , @NamedQuery(name = "KeywordJSONResume.findByWord", query = "SELECT k FROM KeywordJSONResume k WHERE k.word = :word")
    , @NamedQuery(name = "KeywordJSONResume.findByDate", query = "SELECT k FROM KeywordJSONResume k WHERE k.date = :date")})
public class KeywordJSONResume implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Id
    @NotNull
	@Basic(optional = false)
    @Column(name = "rtc")
    private Double rtc;
    @Id
    @NotNull
	@Basic(optional = false)
	@Column(name = "tc")
    private Double tc;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "word")
    private String word;
    @Id
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public KeywordJSONResume() {
    }

    public Double getRtc() {
        return rtc;
    }

    public void setRtc(Double rtc) {
        this.rtc = rtc;
    }

    public Double getTc() {
        return tc;
    }

    public void setTc(Double tc) {
        this.tc = tc;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
