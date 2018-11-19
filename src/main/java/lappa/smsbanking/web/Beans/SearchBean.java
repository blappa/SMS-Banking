/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import com.douwe.generic.dao.DataAccessException;
import java.io.InputStream;
import javax.persistence.NoResultException;
import java.lang.NullPointerException;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import lappa.smsbanking.Entities.Adresse;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Chequier;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Emploi;
import lappa.smsbanking.Entities.OffreSpeciale;
import lappa.smsbanking.Entities.Operation;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.Entities.Sms;
import lappa.smsbanking.Entities.SmsCompte;
import lappa.smsbanking.Entities.Virement;
import IServiceSupport.IServiceAdresse;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceChequier;
import IServiceSupport.IServiceCompte;
import IServiceSupport.IServiceEmploi;
import IServiceSupport.IServiceOffreSpeciale;
import IServiceSupport.IServiceOperation;
import IServiceSupport.IServicePersonne;
import IServiceSupport.IServicePersonneMoral;
import IServiceSupport.IServicePersonnePhys;
import IServiceSupport.IServiceVirement;
import lappa.smsbanking.Entities.CompteParent;
import lappa.smsbanking.IService.IServiceCompteParent;
import lappa.smsbanking.IService.IServiceSms;
import lappa.smsbanking.IService.IServiceSmsCompte;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author lappa
 */
@ManagedBean
@SessionScoped
public class SearchBean implements Serializable {

    @ManagedProperty(value = "#{serviceOperation}")
    private IServiceOperation serviceOperation;
    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{serviceCompte}")
    private IServiceCompte serviceCmpte;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{serviceAdresse}")
    private IServiceAdresse serviceAdresse;
    @ManagedProperty(value = "#{serviceSmsCompte}")
    private IServiceSmsCompte serviceSmsCompte;
    @ManagedProperty(value = "#{serviceSms}")
    private IServiceSms serviceSms;
    @ManagedProperty(value = "#{servicePersonnePhysique}")
    private IServicePersonnePhys servicePersonnePhys;
    @ManagedProperty(value = "#{servicePersonneMorale}")
    private IServicePersonneMoral servicePersonneMoral;
    @ManagedProperty(value = "#{serviceEmploi}")
    private IServiceEmploi serviceEmploi;
    @ManagedProperty(value = "#{serviceChequier}")
    private IServiceChequier serviceChequier;
    @ManagedProperty(value = "#{serviceOffreSpeciale}")
    private IServiceOffreSpeciale serviceOffreSpeciale;
    @ManagedProperty(value = "#{serviceVirement}")
    private IServiceVirement serviceVirement;
    @ManagedProperty(value = "#{serviceCompteParent}")
    private IServiceCompteParent serviceCompteParent;
    private List<Operation> operations;
    private List<Operation> opers;
    private Operation operation;
    private SmsBean sm;
    private CompteParent compteParent;
    private List<Adresse> adresses;
    private Adresse adresse;
    private List<Agence> agences;
    private Agence agence;
    private List<Chequier> chequiers;
    private Chequier cheque;
    private List<Emploi> emplois;
    private Emploi emploi;
    private List<OffreSpeciale> offreSpeciales;
    private OffreSpeciale offreSpeciale;
    private List<Compte> comptes;
    private Compte compte;
    private Compte compteSrc;
    private Compte compteDest;
    private Long idClient;
    private String code;
    private Long idAgence;
    private Chequier chequier;
    private String numR;
    private String numSrc;
    private String numCmpte;
    private String numDest;
    private Date dateDebut;
    private Date dateFin;
    private PersonnePhysique personnePhysique;
    private PersonneMorale personneMorale;
    private Personne personne;
    private List<PersonnePhysique> personnePhysiques;
    private List<PersonneMorale> personneMorales;
    private Long idPersPhys;
    private Long idPersMorale;
    private SmsCompte smsCompte;
    private Sms sms;
    private List<SmsCompte> smsComptes;
    private List<CompteParent> compteParents;
    private Virement virement;
    private List<Virement> virements;
    private Personne current;
    private String passerelle = "localhost";
    private String receip;
    private String text;
    private String username = "lappa";
    private String nomAgence;
    private String passwd = "lappa";

    public SearchBean() {
        chequier = new Chequier();
        virement = new Virement();
        sms = new Sms();
        operation = new Operation();
        personne = new Personne();
        current = new Personne();
        compte = new Compte();
        compteSrc = new Compte();
        compteDest = new Compte();
        opers = new ArrayList<Operation>();
        offreSpeciale = new OffreSpeciale();
        smsCompte = new SmsCompte();
        sm = new SmsBean();
    }

    public void updateAdresse(ActionEvent actionEven) throws DataAccessException {
        int i = 0;
        try {
            current = servicePersonne.findByIdClient(personne.getIdClient());
            if (current != null) {
                Personne ps = servicePersonne.findByIdClient(personne.getIdClient());
                adresse.setIdPersonne(ps);
                adresse.setId(adresse.getId());
                serviceAdresse.update(adresse);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, current.getNomClient() + " " + current.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'ID Du Client' n'existe pas", ""));
        }
    }

    public void updateUser(ActionEvent actionEven) throws DataAccessException {
        int i = 0, j = 0;
        try {
            Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                Personne p = servicePersonne.findByIdClient(personne.getIdClient());
                if (a != null) {
                    if (p.getTypePersonne().equals("Physique") && p.getTypePersonne().equals(personne.getTypePersonne())) {
                        personne.setId(p.getId());
                        personne.setIdClient(p.getIdClient());
                        servicePersonne.update(personne);
                        Personne ps = servicePersonne.findByIdClient(personne.getIdClient());
                        personnePhysique.setIdPersonne(ps);
                        PersonnePhysique ph = servicePersonnePhys.findByCni(personnePhysique.getCni());
                        personnePhysique.setId(ph.getId());
                        servicePersonnePhys.update(personnePhysique);
                        j = 1;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
                    } else {
                        i = 1;
                    }
                    if (p.getTypePersonne().equals("Morale") && p.getTypePersonne().equals(personne.getTypePersonne())) {
                        personne.setId(p.getId());
                        personne.setIdClient(p.getIdClient());
                        servicePersonne.update(personne);
                        Personne ps = servicePersonne.findByIdClient(personne.getIdClient());
                        personneMorale.setIdPersonne(ps);
                        PersonneMorale pm = servicePersonneMoral.findByNumEnregistrement(personneMorale.getNumEnregistrement());
                        personneMorale.setId(pm.getId());
                        servicePersonneMoral.update(personneMorale);
                        j = 2;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour réalisée avec succées", ""));
                    } else {
                        i = 2;
                    }
                    if (i == 1 && j == 0) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " ce ID Client " + personne.getIdClient() + " appartient à une personne morale ou n'existe pas", ""));
                    }
                    if (i == 2 && j == 0) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ce ID Client " + personne.getIdClient() + " appartient à une personne physique ou n'existe pas", ""));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "code agence inexistant", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, personne.getNomClient() + " " + personne.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "code agence " + personne.getCodeAgence() + " inexistant", ""));
        }
    }

    public String afficher() {
        code = null;
        numCmpte = null;
        smsCompte = new SmsCompte();
        compteParent=new CompteParent();
        return "Search";
    }

    public void updateOffreSpeciale(ActionEvent actionEven) throws DataAccessException {
        int i = 0;
        try {
            current = servicePersonne.findByIdClient(personne.getIdClient());
            if (current != null) {
                Personne ps = servicePersonne.findByIdClient(personne.getIdClient());
                offreSpeciale.setIdPersonne(ps);
                OffreSpeciale s = serviceOffreSpeciale.findByIdO(offreSpeciale.getId());
                offreSpeciale.setId(offreSpeciale.getId());
                serviceOffreSpeciale.update(offreSpeciale);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, current.getNomClient() + " " + current.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'ID Du Client' n'existe pas", ""));
        }
    }

    public void updateAgence(ActionEvent actionEven) throws DataAccessException {
        int i = 0;
        try {
            Agence a = serviceAgence.findByCodeAgence(agence.getCodeAgence());
            if (agence != null) {
                agence.setId(a.getId());
                serviceAgence.update(agence);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, current.getNomClient() + " " + current.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'code agence' n'existe pas", ""));
        }
    }

    public void updateEmploi(ActionEvent actionEven) throws DataAccessException {
        int i = 0;
        try {
            current = servicePersonne.findByIdClient(personne.getIdClient());
            if (current != null) {
                Personne ps = servicePersonne.findByIdClient(personne.getIdClient());
                emploi.setIdPersonne(ps);
                emploi.setId(emploi.getId());
                serviceEmploi.update(emploi);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, current.getNomClient() + " " + current.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'ID Du Client' n'existe pas", ""));
        }
    }

    public void updateCompte(ActionEvent actionEven) throws DataAccessException {
        try {
            Compte cmpte = serviceCmpte.findById(compte.getId());
            compte.setId(cmpte.getId());
            serviceCmpte.update(compte);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour reussit", ""));
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "compte introuvable", ""));
        }
    }

    public String updateSmsCompte(ActionEvent actionEven) throws DataAccessException, Exception {
        int pin = sm.generCodePin();
        SmsCompte s = serviceSmsCompte.findByIdClient((smsCompte.getIdClient()));
        receip = smsCompte.getMobile();
        smsCompte.setId(s.getId());
        smsCompte.setPin(pin);
        text = "Le+CREDIT+du+SAHEL+vous+informe+que+votre+compte+SMS,+votre+nouveau+code+PIN+est:+" + pin + "+.+Merci";
        sendsms();
        serviceSmsCompte.update(smsCompte);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
        return "create";
    }

    public void updateVirement(ActionEvent actionEven) throws DataAccessException {
        try {
            personne = servicePersonne.findByCode(virement.getNumCompte());
            compte = serviceCmpte.findById(personne.getId());
            Virement s = serviceVirement.findByNumCompte(virement.getNumCompte());
            virement.setId(s.getId());
            virement.setIdCompte(compte);
            serviceVirement.update(virement);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'Numéro compte' n'existe pas", ""));
        }
    }

    public void updateCheque(ActionEvent actionEven) throws DataAccessException {
        try {
            Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                personne = servicePersonne.findByCode(personne.getCodeProduit());
                cheque.setId(personne.getId());
                serviceChequier.update(cheque);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " mise à jour réalisée avec succées", ""));
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " Code Client'" + personne.getCodeProduit() + "' introuvable", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "code agence " + personne.getCodeAgence() + " inexistant", ""));
        }
    }

    public String searchOperation() throws DataAccessException {
        int test = 0;
        int i = 0;
        int j = 0;
        int k = 0;
        opers = new ArrayList<Operation>();
        try {
            Agence a = serviceAgence.findByCodeAgence(code);
            try {
                personne = servicePersonne.findByCode(numCmpte);
                SimpleDateFormat tmp = new SimpleDateFormat("dd/MM/yyyy");
                String s1 = tmp.format(dateDebut);
                String tm1 = s1.substring(6, 10);
                String s2 = tmp.format(dateFin);
                String tm2 = s2.substring(6, 10);
                operations = getOperations();
                for (Operation operation1 : operations) {
                    String s3 = tmp.format(operation1.getDateJour());
                    String tm3 = s3.substring(6, 10);
                    if (tm3.equals(tm2)) {
                        if (operation1.getIdPersonnePhysique() != null) {
                            if (operation1.getIdPersonnePhysique().getIdPersonne().getCodeProduit().equals(numCmpte)) {
                                opers.add(operation1);
                                j = 1;
                            }
                        }
                        if (operation1.getIdPersonneMoral() != null) {
                            if (operation1.getIdPersonneMoral().getIdPersonne().getCodeProduit().equals(numCmpte)) {
                                opers.add(operation1);
                                j = 1;
                            }
                        }
                    }
                    if (i == 0 && k == 0 && j == 0) {
                        opers.add(operation1);
                        test = 1;
                    }
                    if (j == 1) {
                        k = 1;
                    }
                }
                return "Search";
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "numéro compte '" + numCmpte + "' inexistant", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "code agence " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchAdresse() throws DataAccessException {
        try {
            personne = servicePersonne.findByIdClient(code);
            adresses = getAdresses();
            if (!adresses.isEmpty()) {
                for (Adresse adresse1 : adresses) {
                    if (adresse1.getIdPersonne().getId().equals(personne.getId())) {
                        adresse = serviceAdresse.findByIdA(adresse1.getId());
                        return "EditAdresse";
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Adresse inexistant", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchCordonne() throws DataAccessException {
        try {
            Agence a = serviceAgence.findByCodeAgence(code);
            try {
                personne = servicePersonne.findByIdClient(smsCompte.getIdClient());
                smsComptes = getSmsComptes();
                if (!smsComptes.isEmpty()) {
                    for (SmsCompte smsCompte1 : smsComptes) {
                        if (smsCompte1.getIdClient().equals(smsCompte.getIdClient())) {
                            smsCompte = serviceSmsCompte.findByIdS(smsCompte1.getId());
                            return "search";
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte SMS " + smsCompte.getIdClient() + " inexistant", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, smsCompte.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }
    
     public String searchCordonneCompteParent() throws DataAccessException {
        try {
            Agence a = serviceAgence.findByCodeAgence(code);
            try {
                personne = servicePersonne.findByIdClient(compteParent.getIdClient());
                compteParents = getCompteParents();
                if (!compteParents.isEmpty()) {
                    for (CompteParent compteParent1 : compteParents) {
                        if (compteParent1.getIdClient().equals(compteParent.getIdClient())) {
                            compteParent = serviceCompteParent.findById(compteParent1.getId());
                            return "search";
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte " + smsCompte.getIdClient() + " inexistant", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, smsCompte.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }


    public String searchCheque() throws DataAccessException {
        try {
            personne = servicePersonne.findByIdClient(code);
            try {
                Chequier c = serviceChequier.findByNumReference(numR);
                chequiers = getChequiers();
                if (!chequiers.isEmpty()) {
                    for (Chequier chequier1 : chequiers) {
                        if (chequier1.getIdCompte().getIdPersonnePhysique().getIdPersonne().getId().equals(personne.getId())) {
                            cheque = serviceChequier.findByIdC(chequier1.getId());
                            compte = serviceCmpte.findById(cheque.getIdCompte().getId());
                            return "EditCheque";
                        }
                        if (chequier1.getIdCompte().getIdPersonneMoral().getIdPersonne().getId().equals(personne.getId())) {
                            chequier = serviceChequier.findByIdC(chequier1.getId());
                            compte = serviceCmpte.findByNumeroCompte(numR);
                            return "EditCheque";
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Numero Chèquier inexistant", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Numéro Chèquier Introuvable", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchCompte() throws DataAccessException {
        try {
            personne = servicePersonne.findByCode(numCmpte);
            comptes = getComptes();
            if (!comptes.isEmpty()) {
                for (Compte compte1 : comptes) {
                    if (compte1.getIdPersonnePhysique() != null) {
                        if (compte1.getIdPersonnePhysique().getIdPersonne().getId().equals(personne.getId())) {
                            compte = serviceCmpte.findById(compte1.getId());
                            return "EditCompte";
                        }
                    }
                    if (compte1.getIdPersonneMoral() != null) {
                        if (compte1.getIdPersonneMoral().getIdPersonne().getId().equals(personne.getId())) {
                            compte = serviceCmpte.findById(compte1.getId());
                            return "EditCompte";
                        }
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "aucun compte existant", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Numéro Compte '" + numCmpte + "' inexistant", ""));
        }
        return null;
    }

    public String searchEmploi() throws DataAccessException {
        try {
            personne = servicePersonne.findByIdClient(code);
            emplois = getEmplois();
            if (!emplois.isEmpty()) {
                for (Emploi emploi1 : emplois) {
                    if (emploi1.getIdPersonne().getId().equals(personne.getId())) {
                        emploi = serviceEmploi.findByIdE(emploi1.getId());
                        return "EditEmploi";
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Emploi inexistant", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchAgence() throws DataAccessException {
        try {
            agence = serviceAgence.findByCodeAgence(code);
            return "EditAgence";
        } catch (NullPointerException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchOffre() throws DataAccessException {
        try {
            personne = servicePersonne.findByIdClient(code);
            offreSpeciales = getOffreSpeciales();
            if (!offreSpeciales.isEmpty()) {
                for (OffreSpeciale offreSpeciale1 : offreSpeciales) {
                    if (offreSpeciale1.getIdPersonne().getId().equals(personne.getId())) {
                        offreSpeciale = serviceOffreSpeciale.findByIdO(offreSpeciale1.getId());
                        return "EditOffre";
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Offre inexistant", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchUser() throws DataAccessException {
        try {
            Personne p = servicePersonne.findByIdClient(code);
            if (p.getTypePersonne().equals("Physique")) {
                personnePhysiques = getPersonnePhysiques();
                if (!personnePhysiques.isEmpty()) {
                    for (PersonnePhysique personnePhysique1 : personnePhysiques) {
                        if (personnePhysique1.getIdPersonne().getId().equals(p.getId())) {
                            personne = servicePersonne.findByIdClient(code);
                            personnePhysique = servicePersonnePhys.findByIdP(personnePhysique1.getId());
                            return "EditPersPhys";
                        }
                    }
                } else {
                    personne = servicePersonne.findByIdClient(code);
                    personnePhysique = servicePersonnePhys.findByIdP(personnePhysique.getId());
                    return "EditPersPhys";
                }
            } else {
                personneMorales = getPersonneMorales();
                if (!personneMorales.isEmpty()) {
                    for (PersonneMorale personneMorale1 : personneMorales) {
                        if (personneMorale1.getIdPersonne().getId().equals(p.getId())) {
                            personne = servicePersonne.findByIdClient(code);
                            personneMorale = servicePersonneMoral.findByIdP(personneMorale1.getId());
                            return "EditPersMorale";
                        }
                    }
                } else {
                    personne = servicePersonne.findByIdClient(code);
                    personneMorale = servicePersonneMoral.findByIdP(personneMorale.getId());
                    return "EditPersMorale";
                }
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchSmsCompte() throws DataAccessException {
        try {
            Agence a = serviceAgence.findByCodeAgence(code);
            try {
                personne = servicePersonne.findByIdClient(smsCompte.getIdClient());
                smsComptes = getSmsComptes();
                if (!smsComptes.isEmpty()) {
                    for (SmsCompte smsCompte1 : smsComptes) {
                        if (smsCompte1.getIdClient().equals(smsCompte.getIdClient())) {
                            smsCompte = serviceSmsCompte.findByIdS(smsCompte1.getId());
                            return "EditSmsCompte";
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte SMS " + smsCompte.getIdClient() + " inexistant", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, smsCompte.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code " + code + " inexistant", ""));
        }
        return null;
    }

    public String searchVirement() throws DataAccessException {
        try {
            compte = serviceCmpte.findByNumeroCompte(virement.getNumControle());
            try {
                virement = serviceVirement.findByCodeFichier(virement.getCodeFichier());
                virements = getVirements();
                if (!virements.isEmpty()) {
                    for (Virement virement1 : virements) {
                        if (virement1.getCodeDocument().equals(virement.getCodeDocument())) {
                            virement = serviceVirement.findByIdV(virement1.getId());
                            return "EditVirement";
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte SMS " + smsCompte.getIdClient() + " inexistant", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Code fichier n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Numéro compte " + virement.getNumCompte() + " introuvable", ""));
        }
        return null;
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void delete() throws DataAccessException {
        Operation o = serviceOperation.findById(operation.getId());
        serviceOperation.delete(o.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "suppréssion réalisée avec succés", ""));
    }

    public void sendsms() throws Exception {
        String theUrl = "http://" + passerelle + ":14000/cgi-bin/sendsms?username=" + username + "&password=" + passwd + "&to=" + receip + "&text=" + text + "";
        InputStream inputStream = null;
        StringWriter stringWriter = null;
        URL url = new URL(theUrl);
        inputStream = url.openStream();
        stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter);
        stringWriter.toString();
    }

    public List<Virement> getVirements() throws DataAccessException {
        virements = serviceVirement.findAll();
        return virements;
    }

    public void setVirements(List<Virement> virements) {
        this.virements = virements;
    }

    public String getPasserelle() {
        return passerelle;
    }

    public void setPasserelle(String passerelle) {
        this.passerelle = passerelle;
    }

    public String getReceip() {
        return receip;
    }

    public void setReceip(String receip) {
        this.receip = receip;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public IServiceCompteParent getServiceCompteParent() {
        return serviceCompteParent;
    }

    public void setServiceCompteParent(IServiceCompteParent serviceCompteParent) {
        this.serviceCompteParent = serviceCompteParent;
    }

    public CompteParent getCompteParent() {
        return compteParent;
    }

    public void setCompteParent(CompteParent compteParent) {
        this.compteParent = compteParent;
    }

    public List<Operation> getOperations() throws DataAccessException {
        operations = serviceOperation.findAll();
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Adresse> getAdresses() throws DataAccessException {
        adresses = serviceAdresse.findAll();
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    public List<Chequier> getChequiers() throws DataAccessException {
        chequiers = serviceChequier.findAll();
        return chequiers;
    }

    public void setChequiers(List<Chequier> chequiers) {
        this.chequiers = chequiers;
    }

    public List<Compte> getComptes() throws DataAccessException {
        comptes = serviceCmpte.findAll();
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public List<Emploi> getEmplois() throws DataAccessException {
        emplois = serviceEmploi.findAll();
        return emplois;
    }

    public void setEmplois(List<Emploi> emplois) {
        this.emplois = emplois;
    }

    public List<CompteParent> getCompteParents() throws DataAccessException {
        compteParents=serviceCompteParent.findAll();
        return compteParents;
    }

    public void setCompteParents(List<CompteParent> compteParents) {
        this.compteParents = compteParents;
    }

    public List<OffreSpeciale> getOffreSpeciales() throws DataAccessException {
        offreSpeciales = serviceOffreSpeciale.findAll();
        return offreSpeciales;
    }

    public void setOffreSpeciales(List<OffreSpeciale> offreSpeciales) {
        this.offreSpeciales = offreSpeciales;
    }

    public List<PersonnePhysique> getPersonnePhysiques() throws DataAccessException {
        personnePhysiques = servicePersonnePhys.findAll();
        return personnePhysiques;
    }

    public void setPersonnePhysiques(List<PersonnePhysique> personnePhysiques) {
        this.personnePhysiques = personnePhysiques;
    }

    public List<PersonneMorale> getPersonneMorales() throws DataAccessException {
        personneMorales = servicePersonneMoral.findAll();
        return personneMorales;
    }

    public void setPersonneMorales(List<PersonneMorale> personneMorales) {
        this.personneMorales = personneMorales;
    }

    public List<SmsCompte> getSmsComptes() throws DataAccessException {
        smsComptes = serviceSmsCompte.findAll();
        return smsComptes;
    }

    public void setSmsComptes(List<SmsCompte> smsComptes) {
        this.smsComptes = smsComptes;
    }

    public IServiceOperation getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(IServiceOperation serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public IServiceVirement getServiceVirement() {
        return serviceVirement;
    }

    public void setServiceVirement(IServiceVirement serviceVirement) {
        this.serviceVirement = serviceVirement;
    }

    public Personne getCurrent() {
        return current;
    }

    public void setCurrent(Personne current) {
        this.current = current;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public IServiceCompte getServiceCmpte() {
        return serviceCmpte;
    }

    public void setServiceCmpte(IServiceCompte serviceCmpte) {
        this.serviceCmpte = serviceCmpte;
    }

    public IServiceAgence getServiceAgence() {
        return serviceAgence;
    }

    public void setServiceAgence(IServiceAgence serviceAgence) {
        this.serviceAgence = serviceAgence;
    }

    public IServiceAdresse getServiceAdresse() {
        return serviceAdresse;
    }

    public void setServiceAdresse(IServiceAdresse serviceAdresse) {
        this.serviceAdresse = serviceAdresse;
    }

    public IServiceSmsCompte getServiceSmsCompte() {
        return serviceSmsCompte;
    }

    public void setServiceSmsCompte(IServiceSmsCompte serviceSmsCompte) {
        this.serviceSmsCompte = serviceSmsCompte;
    }

    public IServiceSms getServiceSms() {
        return serviceSms;
    }

    public void setServiceSms(IServiceSms serviceSms) {
        this.serviceSms = serviceSms;
    }

    public IServicePersonnePhys getServicePersonnePhys() {
        return servicePersonnePhys;
    }

    public void setServicePersonnePhys(IServicePersonnePhys servicePersonnePhys) {
        this.servicePersonnePhys = servicePersonnePhys;
    }

    public IServicePersonneMoral getServicePersonneMoral() {
        return servicePersonneMoral;
    }

    public void setServicePersonneMoral(IServicePersonneMoral servicePersonneMoral) {
        this.servicePersonneMoral = servicePersonneMoral;
    }

    public IServiceEmploi getServiceEmploi() {
        return serviceEmploi;
    }

    public void setServiceEmploi(IServiceEmploi serviceEmploi) {
        this.serviceEmploi = serviceEmploi;
    }

    public IServiceChequier getServiceChequier() {
        return serviceChequier;
    }

    public void setServiceChequier(IServiceChequier serviceChequier) {
        this.serviceChequier = serviceChequier;
    }

    public IServiceOffreSpeciale getServiceOffreSpeciale() {
        return serviceOffreSpeciale;
    }

    public void setServiceOffreSpeciale(IServiceOffreSpeciale serviceOffreSpeciale) {
        this.serviceOffreSpeciale = serviceOffreSpeciale;
    }

    public List<Operation> getOpers() {
        return opers;
    }

    public void setOpers(List<Operation> opers) {
        this.opers = opers;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Agence> getAgences() {
        return agences;
    }

    public void setAgences(List<Agence> agences) {
        this.agences = agences;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Chequier getCheque() {
        return cheque;
    }

    public void setCheque(Chequier cheque) {
        this.cheque = cheque;
    }

    public Emploi getEmploi() {
        return emploi;
    }

    public void setEmploi(Emploi emploi) {
        this.emploi = emploi;
    }

    public SmsBean getSm() {
        return sm;
    }

    public void setSm(SmsBean sm) {
        this.sm = sm;
    }

    public OffreSpeciale getOffreSpeciale() {
        return offreSpeciale;
    }

    public void setOffreSpeciale(OffreSpeciale offreSpeciale) {
        this.offreSpeciale = offreSpeciale;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Compte getCompteSrc() {
        return compteSrc;
    }

    public void setCompteSrc(Compte compteSrc) {
        this.compteSrc = compteSrc;
    }

    public Compte getCompteDest() {
        return compteDest;
    }

    public void setCompteDest(Compte compteDest) {
        this.compteDest = compteDest;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(Long idAgence) {
        this.idAgence = idAgence;
    }

    public Chequier getChequier() {
        return chequier;
    }

    public void setChequier(Chequier chequier) {
        this.chequier = chequier;
    }

    public String getNumR() {
        return numR;
    }

    public void setNumR(String numR) {
        this.numR = numR;
    }

    public String getNumSrc() {
        return numSrc;
    }

    public void setNumSrc(String numSrc) {
        this.numSrc = numSrc;
    }

    public String getNumCmpte() {
        return numCmpte;
    }

    public void setNumCmpte(String numCmpte) {
        this.numCmpte = numCmpte;
    }

    public String getNumDest() {
        return numDest;
    }

    public void setNumDest(String numDest) {
        this.numDest = numDest;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public PersonnePhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public void setPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    public PersonneMorale getPersonneMorale() {
        return personneMorale;
    }

    public void setPersonneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Long getIdPersPhys() {
        return idPersPhys;
    }

    public void setIdPersPhys(Long idPersPhys) {
        this.idPersPhys = idPersPhys;
    }

    public Long getIdPersMorale() {
        return idPersMorale;
    }

    public void setIdPersMorale(Long idPersMorale) {
        this.idPersMorale = idPersMorale;
    }

    public SmsCompte getSmsCompte() {
        return smsCompte;
    }

    public void setSmsCompte(SmsCompte smsCompte) {
        this.smsCompte = smsCompte;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public Virement getVirement() {
        return virement;
    }

    public void setVirement(Virement virement) {
        this.virement = virement;
    }
}
