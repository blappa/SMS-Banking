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
public class Adresse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String typeAdresse;
    private String adresse1;
    private String adresse2;
    private String ville;
    private String pays;
    private String telephone1;
    private String telephone2;
    private String zipCode;
    private String telPortable;
    private String email;
    private String telecopieur;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Personne idPersonne;

    public Adresse() {
    }

    public Adresse(String typeAdresse, String adresse1, String adresse2, String ville, String pays, String telephone1, String telephone2, String zipCode, String telPortable, String email, String telecopieur, Personne idPersonne) {
        this.typeAdresse = typeAdresse;
        this.adresse1 = adresse1;
        this.adresse2 = adresse2;
        this.ville = ville;
        this.pays = pays;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.zipCode = zipCode;
        this.telPortable = telPortable;
        this.email = email;
        this.telecopieur = telecopieur;
        this.idPersonne = idPersonne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAdresse() {
        return typeAdresse;
    }

    public void setTypeAdresse(String typeAdresse) {
        this.typeAdresse = typeAdresse;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTelPortable() {
        return telPortable;
    }

    public void setTelPortable(String telPortable) {
        this.telPortable = telPortable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelecopieur() {
        return telecopieur;
    }

    public void setTelecopieur(String telecopieur) {
        this.telecopieur = telecopieur;
    }

    public Personne getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Personne idPersonne) {
        this.idPersonne = idPersonne;
    }
   

    @Override
    public String toString() {
        return typeAdresse;
    }
    
}
