/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author lappa
 */
@Entity
public class Chequier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String typeChequier;
    private int nbrePage;
    private String prefixeChequier;
    private String numReference;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEmission;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebut;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFin;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Compte idCompte;

    public Chequier() {
    }

    public Chequier(String typeChequier, int nbrePage, String prefixeChequier, String numReference, Date dateEmission, Date dateDebut, Date dateFin, Compte idCompte) {
        this.typeChequier = typeChequier;
        this.nbrePage = nbrePage;
        this.prefixeChequier = prefixeChequier;
        this.numReference = numReference;
        this.dateEmission = dateEmission;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idCompte = idCompte;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeChequier() {
        return typeChequier;
    }

    public void setTypeChequier(String typeChequier) {
        this.typeChequier = typeChequier;
    }

    public int getNbrePage() {
        return nbrePage;
    }

    public void setNbrePage(int nbrePage) {
        this.nbrePage = nbrePage;
    }

    public String getPrefixeChequier() {
        return prefixeChequier;
    }

    public void setPrefixeChequier(String prefixeChequier) {
        this.prefixeChequier = prefixeChequier;
    }

    public String getNumReference() {
        return numReference;
    }

    public void setNumReference(String numReference) {
        this.numReference = numReference;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Compte getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Compte idCompte) {
        this.idCompte = idCompte;
    }

    @Override
    public String toString() {
        return "lappa.smsbanking.Entities.Chequier[ id=" + id + " ]";
    }
}
