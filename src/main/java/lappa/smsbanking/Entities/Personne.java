/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author lappa
 */
@Entity
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codeAgence;
    private String idClient;
    private String nomClient;
    private String numeroCompte;
    private String codeProduit;
    private String login;
    private String typePersonne;
    
    
    @OneToMany(mappedBy = "idPersonne")
    private List<Adresse> adresses;
    @OneToMany(mappedBy = "idPersonne")
    private List<Emploi> emplois;
    @OneToMany(mappedBy = "idPersonne")
    private List<OffreSpeciale> offreSpeciales;
    @OneToMany(mappedBy = "idPersonne")
    private List<PersonneMorale> personneMorals;
    @OneToMany(mappedBy = "idPersonne")
    private List<PersonnePhysique> personnePhysiques;
    @ManyToOne(fetch = FetchType.EAGER)
    private Agence idAgence;
    @OneToOne(mappedBy="personne")
    private Autorisation iDAutorisation;

    public Personne() {
    }

    public Personne(String idClient, String nomClient, String numeroCompte, String codeProduit, String login, String password, String typePersonne, Agence idAgence) {
        this.idClient = idClient;
        this.nomClient = nomClient;
        this.numeroCompte = numeroCompte;
        this.codeProduit = codeProduit;
        this.login = login;
        this.typePersonne = typePersonne;
        this.idAgence = idAgence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    public List<Emploi> getEmplois() {
        return emplois;
    }

    public void setEmplois(List<Emploi> emplois) {
        this.emplois = emplois;
    }

    public List<OffreSpeciale> getOffreSpeciales() {
        return offreSpeciales;
    }

    public void setOffreSpeciales(List<OffreSpeciale> offreSpeciales) {
        this.offreSpeciales = offreSpeciales;
    }

    public List<PersonneMorale> getPersonneMorals() {
        return personneMorals;
    }

    public void setPersonneMorals(List<PersonneMorale> personneMorals) {
        this.personneMorals = personneMorals;
    }

    public List<PersonnePhysique> getPersonnePhysiques() {
        return personnePhysiques;
    }

    public void setPersonnePhysiques(List<PersonnePhysique> personnePhysiques) {
        this.personnePhysiques = personnePhysiques;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Agence getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(Agence idAgence) {
        this.idAgence = idAgence;
    }

    public Autorisation getiDAutorisation() {
        return iDAutorisation;
    }

    public void setiDAutorisation(Autorisation iDAutorisation) {
        this.iDAutorisation = iDAutorisation;
    }

    @Override
    public String toString() {
        return numeroCompte;
    }
}
