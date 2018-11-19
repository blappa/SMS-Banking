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
public class Virement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codeAgenceEnvoi;
    private String codeAgenceRecep;
    private String numCompte;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNais;
    private String civilite;
    private String nomClient;
    private String typeDocument;
    private String codeDocument;
    private String telephone;
    private String mail;
    private String adresse;
    private String montant;
    private String commission;
    private String montantT;
    private String question;
    private String reponse;
    private String remarque;
    private String numControle;
    private String codeFichier;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJour;
    private String taxe;
    @ManyToOne(fetch = FetchType.EAGER)
    private Compte idCompte;

    public Virement() {
    }

    public Virement(String codeAgenceEnvoi, String numCompte, Date dateNais, String civilite, String nomClient, String typeDocument, String codeDocument, String telephone, String mail, String adresse, String montant, String commission, String montantT, String taxe) {
        this.codeAgenceEnvoi = codeAgenceEnvoi;
        this.numCompte = numCompte;
        this.dateNais = dateNais;
        this.civilite = civilite;
        this.nomClient = nomClient;
        this.typeDocument = typeDocument;
        this.codeDocument = codeDocument;
        this.telephone = telephone;
        this.mail = mail;
        this.adresse = adresse;
        this.montant = montant;
        this.commission = commission;
        this.montantT = montantT;
        this.taxe = taxe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAgenceEnvoi() {
        return codeAgenceEnvoi;
    }

    public void setCodeAgenceEnvoi(String codeAgenceEnvoi) {
        this.codeAgenceEnvoi = codeAgenceEnvoi;
    }

    public String getCodeAgenceRecep() {
        return codeAgenceRecep;
    }

    public void setCodeAgenceRecep(String codeAgenceRecep) {
        this.codeAgenceRecep = codeAgenceRecep;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
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

    public String getNomClient() {
        return nomClient;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getCodeDocument() {
        return codeDocument;
    }

    public void setCodeDocument(String codeDocument) {
        this.codeDocument = codeDocument;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getMontantT() {
        return montantT;
    }

    public void setMontantT(String montantT) {
        this.montantT = montantT;
    }

    public String getTaxe() {
        return taxe;
    }

    public void setTaxe(String taxe) {
        this.taxe = taxe;
    }

    public Compte getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Compte idCompte) {
        this.idCompte = idCompte;
    }

    
    public String getNumControle() {
        return numControle;
    }

    public void setNumControle(String numControle) {
        this.numControle = numControle;
    }

    public String getCodeFichier() {
        return codeFichier;
    }

    public void setCodeFichier(String codeFichier) {
        this.codeFichier = codeFichier;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }

    @Override
    public String toString() {
        return "lappa.smsbanking.Entities.Virement[ id=" + id + " ]";
    }
}
