/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.Entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author lappa
 */
@Entity
public class Emploi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String profession;
    private String typeEntreprise;
    private String nomEntreprise;
    private String designation;
    private String typeEmploi;
    private String numPermiTravail;
    private String fraisLoyer;
    private String travailDepuis;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Personne idPersonne;

    public Emploi() {
    }

    public Emploi(String profession, String typeEntreprise, String nomEntreprise, String designation, String typeEmploi, String numPermiTravail, String fraisLoyer, String travailDepuis, Personne idPersonne) {
        this.profession = profession;
        this.typeEntreprise = typeEntreprise;
        this.nomEntreprise = nomEntreprise;
        this.designation = designation;
        this.typeEmploi = typeEmploi;
        this.numPermiTravail = numPermiTravail;
        this.fraisLoyer = fraisLoyer;
        this.travailDepuis = travailDepuis;
        this.idPersonne = idPersonne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTypeEntreprise() {
        return typeEntreprise;
    }

    public void setTypeEntreprise(String typeEntreprise) {
        this.typeEntreprise = typeEntreprise;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTypeEmploi() {
        return typeEmploi;
    }

    public void setTypeEmploi(String typeEmploi) {
        this.typeEmploi = typeEmploi;
    }

    public String getNumPermiTravail() {
        return numPermiTravail;
    }

    public void setNumPermiTravail(String numPermiTravail) {
        this.numPermiTravail = numPermiTravail;
    }

    public String getFraisLoyer() {
        return fraisLoyer;
    }

    public void setFraisLoyer(String fraisLoyer) {
        this.fraisLoyer = fraisLoyer;
    }

    public String getTravailDepuis() {
        return travailDepuis;
    }

    public void setTravailDepuis(String travailDepuis) {
        this.travailDepuis = travailDepuis;
    }

    public Personne getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Personne idPersonne) {
        this.idPersonne = idPersonne;
    }

    @Override
    public String toString() {
        return profession + " " + typeEntreprise;
    }
}
