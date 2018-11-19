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
public class PersonneMorale implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nomEntreprise;
    private String constitution;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEnregistrement;
    private String numEnregistrement;
    private String secteurActivite;
    private String siteWeb;
    private String commentaire;
    private String ouvertPar;
     @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOuverture;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Personne idPersonne;
    @OneToMany(mappedBy = "idPersonneMoral")
    private List<Compte> comptes;
    @OneToMany(mappedBy = "idPersonneMoral")
    private List<Operation> operations;
    @OneToMany(mappedBy = "idPersonneMoral")
    private List<SmsCompte> smsComptes;

    public PersonneMorale() {
    }

    public PersonneMorale(String nomEntreprise, String constitution, Date dateEnregistrement, String numEnregistrement, String secteurActivite, String siteWeb, String commentaire,String ouvertPar, Date dateOuverture, Personne idPersonne) {
        this.nomEntreprise = nomEntreprise;
        this.constitution = constitution;
        this.dateEnregistrement = dateEnregistrement;
        this.numEnregistrement = numEnregistrement;
        this.secteurActivite = secteurActivite;
        this.siteWeb = siteWeb;
        this.commentaire = commentaire;
        this.ouvertPar = ouvertPar;
        this.dateOuverture = dateOuverture;
        this.idPersonne = idPersonne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public String getNumEnregistrement() {
        return numEnregistrement;
    }

    public void setNumEnregistrement(String numEnregistrement) {
        this.numEnregistrement = numEnregistrement;
    }

    public String getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getOuvertPar() {
        return ouvertPar;
    }

    public void setOuvertPar(String ouvertPar) {
        this.ouvertPar = ouvertPar;
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
        return nomEntreprise;
    }
    
}
