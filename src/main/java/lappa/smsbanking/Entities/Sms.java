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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author lappa
 */
@Entity
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String telephoneExp;
    private String message;
    private String telephoneRecep;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJour;
    private String type;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonneMorale idPersonneMoral;
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonnePhysique idPersonnePhysique;
    @JoinColumn(nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Compte idCompte;

    public Sms() {
    }

    public Sms(String telephoneExp, String telephoneRecep, Date dateJour, PersonneMorale idPersonneMoral, PersonnePhysique idPersonnePhysique, Compte idCompte) {
        this.telephoneExp = telephoneExp;
        this.telephoneRecep = telephoneRecep;
        this.dateJour = dateJour;
        this.idPersonneMoral = idPersonneMoral;
        this.idPersonnePhysique = idPersonnePhysique;
        this.idCompte=idCompte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephoneExp() {
        return telephoneExp;
    }

    public void setTelephoneExp(String telephoneExp) {
        this.telephoneExp = telephoneExp;
    }

    public String getTelephoneRecep() {
        return telephoneRecep;
    }

    public void setTelephoneRecep(String telephoneRecep) {
        this.telephoneRecep = telephoneRecep;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Compte getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Compte idCompte) {
        this.idCompte = idCompte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return telephoneExp+" "+telephoneRecep;
    }
}
