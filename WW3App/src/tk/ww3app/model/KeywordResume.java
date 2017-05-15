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
@Table(name = "KeywordResume")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeywordResume.findAll", query = "SELECT k FROM KeywordResume k")
    , @NamedQuery(name = "KeywordResume.findByWord", query = "SELECT k FROM KeywordResume k WHERE k.word = :word")
    , @NamedQuery(name = "KeywordResume.findByTweetsCount", query = "SELECT k FROM KeywordResume k WHERE k.tweetsCount = :tweetsCount")
    , @NamedQuery(name = "KeywordResume.findByReTweetsCount", query = "SELECT k FROM KeywordResume k WHERE k.reTweetsCount = :reTweetsCount")})
public class KeywordResume implements Serializable {

	
    private static final long serialVersionUID = 1L;
    @Id    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "word")
    private String word;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "TweetsCount)")
    private Double tweetsCount;
    @Id
    @Column(name = "ReTweetsCount)")
    private Double reTweetsCount;

    public KeywordResume() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
