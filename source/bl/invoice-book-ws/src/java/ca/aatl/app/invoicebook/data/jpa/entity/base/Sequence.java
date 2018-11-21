/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author GShokar
 */
@Entity
@Table(name = "tblsequence")
@NamedQueries({
    @NamedQuery(name = "Sequence.findAll", query = "SELECT s FROM Sequence s"),
    @NamedQuery(name = "Sequence.findByName", query = "SELECT s FROM Sequence s WHERE s.name = :name")})
public class Sequence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SequenceId", unique = true, nullable = false)
    private Integer id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "PreFixString", nullable = true)
    private String preFix;
    @Column(name = "PostFixString", nullable = true)
    private String postFix;
    @Column(name = "IdGenerator", nullable = true)
    private String generator;
    @Column(name = "Filler", nullable = true)
    private String filler;
    @Column(name = "LastNo", nullable = false)
    private Integer lastNo;
    @Column(name = "MaxLength", nullable = false)
    private Integer maxLength;

    public Sequence() {
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLastNo() {
        return lastNo;
    }

    public void setLastNo(Integer lastNo) {
        this.lastNo = lastNo;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostFix() {
        return postFix;
    }

    public void setPostFix(String postFix) {
        this.postFix = postFix;
    }

    public String getPreFix() {
        return preFix;
    }

    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }

    public int NextNo() {
        return lastNo + 1;
    }
}
