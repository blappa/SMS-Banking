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
public class PersonnePhysique implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private String prenom1;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNais;
    private String civilite;
    private String prenom2;
    private String sexe;
    private String age;
    private String cni;
     @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateExpiration;
     @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOuverture;
   
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Personne idPersonne;
    @OneToMany(mappedBy = "idPersonnePhysique")
    private List<Compte> comptes;
    @OneToMany(mappedBy = "idPersonnePhysique")
    private List<Operation> operations;
    @OneToMany(mappedBy = "idPersonnePhysique")
    private List<SmsCompte> smsComptes;

    public PersonnePhysique() {
    }

    public PersonnePhysique(String nom, String prenom1, Date dateNais, String civilite, String prenom2, String sexe, String cni, Date dateExpiration, Date  dateOuverture,  Personne idPersonne) {
        this.nom = nom;
        this.prenom1 = prenom1;
        this.dateNais = dateNais;
        this.civilite = civilite;
        this.prenom2 = prenom2;
        this.sexe = sexe;
        this.cni = cni;
        this.dateExpiration = dateExpiration;
        this.dateOuverture = dateOuverture;
        this.idPersonne = idPersonne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom1() {
        return prenom1;
    }

    public void setPrenom1(String prenom1) {
        this.prenom1 = prenom1;
    }

    public Date getDateNais() {
        return dateNais;
    }

    public void setDateNais(Date dateNais) {
        this.dateNais = dateNais;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getPrenom2() {
        return prenom2;
    }

    public void setPrenom2(String prenom2) {
        this.prenom2 = prenom2;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Personne getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Personne idPersonne) {
        this.idPersonne = idPersonne;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<SmsCompte> getSmsComptes() {
        return smsComptes;
    }

    public void setSmsComptes(List<SmsCompte> smsComptes) {
        this.smsComptes = smsComptes;
    }

  
    @Override
    public String toString() {
        return nom+ " " +prenom1;
    }
    
}
