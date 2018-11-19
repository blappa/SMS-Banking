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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author lappa
 */
@Entity
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomOperation;
    private int soldeCompte;
    private String recit;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJour;
    
    @JoinColumn(nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Compte cpteSrc;
    @JoinColumn(nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Compte cpteDest;
    @JoinColumn(nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonneMorale idPersonneMoral;
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonnePhysique idPersonnePhysique;

    public Operation() {
    }

    public Operation(String nomOperation, int soldeCompte, Compte cpteSrc, Compte cpteDest, PersonneMorale idPersonneMoral, PersonnePhysique idPersonnePhysique) {
        this.nomOperation = nomOperation;
        this.soldeCompte = soldeCompte;
        this.cpteSrc = cpteSrc;
        this.cpteDest = cpteDest;
        this.idPersonneMoral = idPersonneMoral;
        this.idPersonnePhysique = idPersonnePhysique;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomOperation() {
        return nomOperation;
    }

    public void setNomOperation(String nomOperation) {
        this.nomOperation = nomOperation;
    }

    public int getSoldeCompte() {
        return soldeCompte;
    }

    public void setSoldeCompte(int soldeCompte) {
        this.soldeCompte = soldeCompte;
    }

    public Compte getCpteSrc() {
        return cpteSrc;
    }

    public void setCpteSrc(Compte cpteSrc) {
        this.cpteSrc = cpteSrc;
    }

    public Compte getCpteDest() {
        return cpteDest;
    }

    public void setCpteDest(Compte cpteDest) {
        this.cpteDest = cpteDest;
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

    public String getRecit() {
        return recit;
    }

    public void setRecit(String recit) {
        this.recit = recit;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }
    
    @Override
    public String toString() {
        return nomOperation;
    }
}
