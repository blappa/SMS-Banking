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
public class SmsCompte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String idClient;
    private String langue;
    private String mobile;
    private String typeCompte;
    private String email;
    private String codeVerification;
    private String numServeur;
    private int pin;
      
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonneMorale idPersonneMoral;
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonnePhysique idPersonnePhysique;
    @ManyToOne(fetch = FetchType.EAGER)
    private CompteParent compteParent;

    public SmsCompte() {
    }

    public SmsCompte(String idClient, String langue, String mobile, String typeCompte, String email, String numServeur, PersonneMorale idPersonneMoral, PersonnePhysique idPersonnePhysique) {
        this.idClient = idClient;
        this.langue = langue;
        this.mobile = mobile;
        this.typeCompte = typeCompte;
        this.email = email;
        this.numServeur = numServeur;
        this.idPersonneMoral = idPersonneMoral;
        this.idPersonnePhysique = idPersonnePhysique;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumServeur() {
        return numServeur;
    }

    public void setNumServeur(String numServeur) {
        this.numServeur = numServeur;
    }

    public PersonneMorale getIdPersonneMoral() {
        return idPersonneMoral;
    }

    public void setIdPersonneMoral(PersonneMorale idPersonneMoral) {
        this.idPersonneMoral = idPersonneMoral;
    }

    public PersonnePhysique getIdPersonnePhysique() {
        return idPersonnePhysique;
    }

    public void setIdPersonnePhysique(PersonnePhysique idPersonnePhysique) {
        this.idPersonnePhysique = idPersonnePhysique;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public CompteParent getCompteParent() {
        return compteParent;
    }

    public void setCompteParent(CompteParent compteParent) {
        this.compteParent = compteParent;
    }

    public String getCodeVerification() {
        return codeVerification;
    }

    public void setCodeVerification(String codeVerification) {
        this.codeVerification = codeVerification;
    }

   
    @Override
    public String toString() {
        return idClient+" "+mobile;
    }
    
}
