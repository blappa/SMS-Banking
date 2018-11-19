/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author lappa
 */
@Entity
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sigle;
    private String adresse1;
    private String adresse2;
    private String ville;
    private String pays;
    private String telephone1;
    private String telephone2;
    private String modeExpiration;
    private String email;
    private String classeCompte;
    private int solde;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreation;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonneMorale idPersonneMoral;
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonnePhysique idPersonnePhysique;
    @OneToMany(mappedBy = "idPersonnePhysique")
    private List<Sms> smss;
    @OneToMany(mappedBy = "idCompte")
    private List<Chequier> chequiers;
    @OneToMany(mappedBy = "idCompte")
    private List<Virement> virements;
    
    

    public Compte() {
    }

    public Compte(String sigle, String adresse1, String adresse2, String ville, String pays, String telephone1, String telephone2, String modeExpiration, String email, String classeCompte, int solde, Date dateCreation, PersonneMorale idPersonneMoral) {
        this.sigle = sigle;
        this.adresse1 = adresse1;
        this.adresse2 = adresse2;
        this.ville = ville;
        this.pays = pays;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.modeExpiration = modeExpiration;
        this.email = email;
        this.classeCompte = classeCompte;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.idPersonneMoral = idPersonneMoral;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getModeExpiration() {
        return modeExpiration;
    }

    public void setModeExpiration(String modeExpiration) {
        this.modeExpiration = modeExpiration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClasseCompte() {
        return classeCompte;
    }

    public void setClasseCompte(String classeCompte) {
        this.classeCompte = classeCompte;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public PersonneMorale getIdPersonneMoral() {
        return idPersonneMoral;
    }

    public void setIdPersonneMoral(PersonneMorale idPersonneMoral) {
        this.idPersonneMoral = idPersonneMoral;
    }

    public List<Sms> getSmss() {
        return smss;
    }

    public void setSmss(List<Sms> smss) {
        this.smss = smss;
    }

    public PersonnePhysique getIdPersonnePhysique() {
        return idPersonnePhysique;
    }

    public void setIdPersonnePhysique(PersonnePhysique idPersonnePhysique) {
        this.idPersonnePhysique = idPersonnePhysique;
    }

    public List<Chequier> getChequiers() {
        return chequiers;
    }

    public void setChequiers(List<Chequier> chequiers) {
        this.chequiers = chequiers;
    }

    public List<Virement> getVirements() {
        return virements;
    }

    public void setVirements(List<Virement> virements) {
        this.virements = virements;
    }
    

    @Override
    public String toString() {
        return sigle;
    }
}
