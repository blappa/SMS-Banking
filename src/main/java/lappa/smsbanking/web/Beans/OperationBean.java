/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import com.douwe.generic.dao.DataAccessException;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.net.ConnectException;
import java.io.InputStream;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import lappa.smsbanking.Entities.Adresse;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Operation;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.SmsCompte;
import IServiceSupport.IServiceAdresse;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceCompte;
import IServiceSupport.IServiceOperation;
import IServiceSupport.IServicePersonne;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import lappa.smsbanking.Entities.CompteParent;
import lappa.smsbanking.IService.IServiceCompteParent;
import lappa.smsbanking.IService.IServiceSmsCompte;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author lappa
 */
@ManagedBean
@SessionScoped
public class OperationBean implements Serializable {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "scriptShell";
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
    @ManagedProperty(value = "#{serviceCompteParent}")
    private IServiceCompteParent serviceCompteParent;
    private CompteParent compteParent;
    private Agence agence;
    private Operation operation;
    private Adresse adresse;
    private Personne personne;
    private SmsCompte smsCompte;
    private Compte compteSrc;
    private Compte compteDest;
    private List<Operation> operations;
    private List<Compte> comptes;
    private List<Operation> opers;
    private String code;
    private String numSrc;
    private String numCmpte;
    private String numDest;
    private Date dateDebut;
    private Date dateFin;
    private String passerelle = "localhost";
    private String receip;
    private String receio;
    private String text;
    private String textC;
    private String username = "lappa";
    private String passwd = "lappa";

    public OperationBean() {
        agence = new Agence();
        adresse = new Adresse();
        operation = new Operation();
        personne = new Personne();
        compteSrc = new Compte();
        compteDest = new Compte();
        opers = new ArrayList<Operation>();
        smsCompte = new SmsCompte();
        compteParent = new CompteParent();
    }

    public String saveOperationCrediter(ActionEvent actionEven) throws DataAccessException, Exception, IOException {
        int solde = 0, soldeC = 0;
        int i = 0;
        try {
            personne = servicePersonne.findByIdClient(code);
            try {
                smsCompte = serviceSmsCompte.findByIdClient(code);
                 compteParent = serviceCompteParent.findByIdClient(smsCompte.getCompteParent().getIdClient());
                personne = servicePersonne.findByNumeroCompte(numSrc);
                receip = smsCompte.getMobile();
                comptes = serviceCmpte.findAll();
                for (Compte compte1 : comptes) {
                    if (compte1.getIdPersonnePhysique() != null && compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte() != null) {
                        if (compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte().equals(numSrc) && compte1.getIdPersonnePhysique().getIdPersonne().getIdClient().equals(code)) {
                            soldeC = operation.getSoldeCompte();
                            solde = compte1.getSolde();
                            solde = solde + operation.getSoldeCompte();
                            compte1.setSolde(solde);
                            compte1.setId(compte1.getId());
                            serviceCmpte.update(compte1);
                            operation.setCpteSrc(compte1);
                            operation.setIdPersonnePhysique(compte1.getIdPersonnePhysique());
                            i = 1;
                        }
                    }
                    if (compte1.getIdPersonneMoral() != null && compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte() != null) {
                        if (compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte().equals(numSrc) && compte1.getIdPersonneMoral().getIdPersonne().getIdClient().equals(code)) {
                            soldeC = operation.getSoldeCompte();
                            solde = compte1.getSolde();
                            solde = solde + operation.getSoldeCompte();
                            compte1.setSolde(solde);
                            compte1.setId(compte1.getId());
                            serviceCmpte.update(compte1);
                            operation.setCpteSrc(compte1);
                            operation.setIdPersonneMoral(compte1.getIdPersonneMoral());
                            i = 1;
                        }
                    }
                }
                if (i == 1) {
                    i = 0;
                    textC = personne.getCodeAgence() + "SOLDE";
                    try {
                        text = "Le+CREDIT+du+SAHEL+vous+informe+que+votre+compte+a+ete+credite+de+'+" + soldeC + "+FCFA+'+.+Votre+solde+actuel+est+de+'+" + solde + "+FCFA+'+.+Envoyez+votre+'numero_compte+PIN+HELP'+au+73751558+pour+plus+d+'+information+.+Merciz";
                        sendsms(receip);
                        serviceOperation.save(operation);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'opération créditation' réalisée avec succées", ""));
                        effacer();
                    } catch (ConnectException ex) {
                        try {
                            text = "Le CREDIT du SAHEL vous informe que votre compte a ete credite de '" + soldeC + " FCFA'. Votre solde actuel est de '" + solde + " FCFA'";
//                            twilio(receip, text, smsCompte);
                            shellSend(receip, text, compteParent);
                            serviceOperation.save(operation);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'opération créditation' réalisée avec succées", ""));
                            effacer();
                        } catch (RuntimeException run) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur de connection au serveur SMS, 'opération non effectuée!'", ""));
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Compte inexistant ou Code Employé ne correspond pas au numéro compte", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'SVP' vous devez creer un compte 'SMS' pour ce client. Merci", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'Code Du Client' introuvable", ""));
        }
        return "Crediter";
    }

    public String saveOperationDebiter(ActionEvent actionEven) throws DataAccessException, Exception, IOException {
        int solde = 0, soldeC = 0;
        int i = 0, j = 0;
        try {
            personne = servicePersonne.findByIdClient(code);
            try {
                smsCompte = serviceSmsCompte.findByIdClient(code);
                compteParent = serviceCompteParent.findByIdClient(smsCompte.getCompteParent().getIdClient());
                receip = smsCompte.getMobile();
                comptes = serviceCmpte.findAll();
                for (Compte compte1 : comptes) {
                    if (compte1.getIdPersonnePhysique() != null && compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte() != null) {
                        if (compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte().equals(numSrc) && compte1.getIdPersonnePhysique().getIdPersonne().getIdClient().equals(code)) {
                            soldeC = operation.getSoldeCompte();
                            solde = compte1.getSolde();
                            if (solde > soldeC) {
                                solde = solde - operation.getSoldeCompte();
                                compte1.setSolde(solde);
                                compte1.setId(compte1.getId());
                                serviceCmpte.update(compte1);
                                operation.setCpteSrc(compte1);
                                operation.setIdPersonnePhysique(compte1.getIdPersonnePhysique());
                            } else {
                                j = 1;
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'Votre compte est insuffisant pour etre débiter. Merci", ""));
                            }
                            i = 1;
                        }
                    }
                    if (compte1.getIdPersonneMoral() != null && compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte() != null) {
                        if (compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte().equals(numSrc) && compte1.getIdPersonneMoral().getIdPersonne().getIdClient().equals(code)) {
                            soldeC = operation.getSoldeCompte();
                            solde = compte1.getSolde();
                            if (solde > soldeC) {
                                solde = solde - operation.getSoldeCompte();
                                compte1.setSolde(solde);
                                compte1.setId(compte1.getId());
                                serviceCmpte.update(compte1);
                                operation.setCpteSrc(compte1);
                                operation.setIdPersonneMoral(compte1.getIdPersonneMoral());
                            } else {
                                j = 1;
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'Votre compte est insuffisant pour etre débiter. Merci", ""));
                            }
                            i = 1;
                        }
                    }
                }
                if (i == 1) {
                    if (j == 0) {
                        i = 0;
                        textC = personne.getCodeAgence() + "CSD";
                        try {
                            text = "Le+CREDIT+du+SAHEL+vous+informe+que+votre+compte+a+ete+debite+de+'+" + soldeC + "+FCFA+'+.+Votre+solde+actuel+est+de+'+" + solde + "+FCFA+'+.+Envoyez+votre+'numero_compte+PIN+HELP'+au+73751558+pour+plus+d+'+information+.+Merci";
                            sendsms(receip);
                            serviceOperation.save(operation);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'opération débitation' réalisée avec succées", ""));
                            effacer();
                        } catch (ConnectException ex) {
                            try {
                                text = "Le CREDIT du SAHEL vous informe que votre compte a ete debite de ' " + soldeC + " FCFA' .Votre solde actuel est de ' " + solde + " FCFA'";
//                                twilio(receip, text, smsCompte);
                                shellSend(receip, text, compteParent);
                                serviceOperation.save(operation);
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'opération débitation' réalisée avec succées", ""));
                                effacer();
                            } catch (RuntimeException run) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur de connection au serveur Twilio SMS, 'opération non effectuée!'", ""));
                            }
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Compte inexistant ou Code Employé ne correspond pas au numéro compte", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'SVP' vous devez creer un compte 'SMS' pour ce client. Merci", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'Code Du Client' introuvable", ""));
        }
        return "Debiter";
    }

    @SuppressWarnings("empty-statement")
    public String saveOperationTransfert(ActionEvent actionEven) throws DataAccessException, Exception, IOException {
        int solde = 0, soldeC = 0, soldeO = 0, soldeT = 0;
        String cmpte = null;
        int i = 0, j = 0;
        if (operation.getNomOperation().equals("Retrait")) {
            saveOperationDebiter(actionEven);
        } else if (operation.getNomOperation().equals("Crediter")) {
            saveOperationCrediter(actionEven);
        } else {
            try {
                personne = servicePersonne.findByIdClient(code);
                try {
                    smsCompte = serviceSmsCompte.findByIdClient(code);
                    compteParent = serviceCompteParent.findByIdClient(smsCompte.getCompteParent().getIdClient());
                    receip = smsCompte.getMobile();
                    comptes = serviceCmpte.findAll();
                    for (Compte compte1 : comptes) {
                        if (compte1.getIdPersonnePhysique() != null && compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte() != null) {
                            if (compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte().equals(numSrc) && compte1.getIdPersonnePhysique().getIdPersonne().getIdClient().equals(code)) {
                                soldeC = operation.getSoldeCompte();
                                solde = compte1.getSolde();
                                if (solde > soldeC) {
                                    solde = solde - operation.getSoldeCompte();
                                    soldeT = solde;
                                    compte1.setSolde(solde);
                                    compte1.setId(compte1.getId());
                                    serviceCmpte.update(compte1);
                                    operation.setCpteSrc(compte1);
                                    operation.setIdPersonnePhysique(compte1.getIdPersonnePhysique());
                                } else {
                                    j = 1;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'Votre compte est insuffisant pour etre débiter. Merci", ""));
                                }
                                i = 1;
                            }
                            if (compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte().equals(numDest)) {
                                solde = compte1.getSolde();
                                soldeO = operation.getSoldeCompte();
                                solde = solde + operation.getSoldeCompte();
                                compte1.setSolde(solde);
                                compte1.setId(compte1.getId());
                                cmpte = compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte();
                                serviceCmpte.update(compte1);
                                operation.setCpteDest(compte1);
                                operation.setIdPersonnePhysique(compte1.getIdPersonnePhysique());
                                if (compte1.getIdPersonnePhysique() != null) {
                                    SmsCompte s = serviceSmsCompte.findByIdClient(compte1.getIdPersonnePhysique().getIdPersonne().getIdClient());
                                    receio = s.getMobile();
                                }
                                i = 1;
                            }
                        }
                        if (compte1.getIdPersonneMoral() != null && compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte() != null) {
                            if (compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte().equals(numSrc) && compte1.getIdPersonneMoral().getIdPersonne().getIdClient().equals(code)) {
                                soldeC = operation.getSoldeCompte();
                                solde = compte1.getSolde();
                                if (solde > soldeC) {
                                    solde = solde - operation.getSoldeCompte();
                                    soldeT = solde;
                                    compte1.setSolde(solde);
                                    compte1.setId(compte1.getId());
                                    serviceCmpte.update(compte1);
                                    operation.setCpteSrc(compte1);
                                    operation.setIdPersonneMoral(compte1.getIdPersonneMoral());
                                } else {
                                    j = 1;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'Votre compte est insuffisant pour etre débiter. Merci", ""));
                                }
                                i = 1;
                            }
                            if (compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte().equals(numDest)) {
                                solde = compte1.getSolde();
                                soldeO = operation.getSoldeCompte();
                                solde = solde + operation.getSoldeCompte();
                                compte1.setSolde(solde);
                                compte1.setId(compte1.getId());
                                cmpte = compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte();
                                serviceCmpte.update(compte1);
                                operation.setCpteDest(compte1);
                                operation.setIdPersonneMoral(compte1.getIdPersonneMoral());
                                if (compte1.getIdPersonneMoral() != null) {
                                    SmsCompte s = serviceSmsCompte.findByIdClient(compte1.getIdPersonneMoral().getIdPersonne().getIdClient());
                                    receio = s.getMobile();
                                }
                                i = 1;
                            }
                        }
                    }
                    if (i == 1) {
                        if (j == 0) {
                            i = 0;
                            try {
                                text = "Le+CREDIT+du+SAHEL+vous+informe+que+votre+compte+a+ete+debite+de+'+" + soldeC + "+'+FCFA+et+transfere+vers+le+compte+'+" + cmpte + "+'+.+Votre+solde+actuel+est+de+'+" + soldeT + "+FCFA+'+.+Envoyez+votre+'numero_compte+PIN+HELP'+au+73751558+pour+plus+d+'+information+.+Merci";
                                sendsms(receip);
                                text = "Le+CREDIT+du+SAHEL+vous+informe+que+votre+compte+a+ete+crédite+de+'+" + soldeO + "+FCFA+'+.+Votre+solde+actuel+est+de+'+" + solde + "+FCFA+'+.+Envoyez+votre+'numero_compte+PIN+HELP'+au+73751558+pour+plus+d+'+information+.+Merci";
                                sendsms(receio);
                            } catch (ConnectException ex) {
                                try {
                                    text = "Le CREDIT du SAHEL vous informe que votre compte a ete debite de '" + soldeC + "'FCFA et transfere vers le compte '" + cmpte + "'.Votre solde actuel est de '" + soldeT + "FCFA'";
//                                    twilio(receip, text, smsCompte);
                                    shellSend(receip, text, compteParent);
                                    text = "Le CREDIT du SAHEL vous informe que votre compte a ete crédite de '" + soldeO + " FCFA'. Votre solde actuel est de '" + solde + "FCFA'";
//                                    twilio(receio, text, smsCompte);
                                    shellSend(receio, text, compteParent);
                                    serviceOperation.save(operation);
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'opération' réalisée avec succées", ""));
                                    effacer();
                                } catch (RuntimeException run) {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur de connection au serveur Twilio SMS, 'opération non effectuée!'", ""));
                                }
                            }
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numéro compte source ou destination' inexistant ou Code Employé ne correspond pas au numéro compte", ""));
                    }
                } catch (NoResultException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'SVP' vous devez creer un compte 'SMS' pour ce client. Merci", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'Code Du Client' introuvable", ""));
            }
        }
        return "Transfert";
    }

    public List<Operation> getOperations() throws DataAccessException {
        operations = serviceOperation.findAll();
        return operations;
    }

    public void delete() throws DataAccessException {
        Operation o = serviceOperation.findById(operation.getId());
        serviceOperation.delete(o.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "suppréssion réalisée avec succés", ""));
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Operation> getOpers() {
        return opers;
    }

    public void sendsms(String num) throws Exception {
        String theUrl = "http://" + passerelle + ":14000/cgi-bin/sendsms?username=" + username + "&password=" + passwd + "&to=" + num + "&text=" + text + "";
        InputStream inputStream = null;
        StringWriter stringWriter = null;
        URL url = new URL(theUrl);
        inputStream = url.openStream();
        stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter);
        stringWriter.toString();
    }

    public void deconect() throws Exception {
        String theUrl = "https://www.twilio.com/logout";
        InputStream inputStream = null;
        StringWriter stringWriter = null;
        URL url = new URL(theUrl);
        inputStream = url.openStream();
        stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter);
        stringWriter.toString();
    }

    public void shellSend(String num, String text, CompteParent s) throws IOException {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        String cmd[] = {path + File.separator + "send.sh", s.getCallerID(), s.getAccountID(), s.getToken(), "+237" + num, text};
        p = r.exec(cmd);
    }

    public void twilio(String num, String text, CompteParent s) throws TwilioRestException, Exception {
        // Find your Account Sid and Token at twilio.com/user/account
        String ACCOUNT_SID = s.getAccountID();
        String AUTH_TOKEN = s.getToken();

        deconect();

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
// Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", text));
        params.add(new BasicNameValuePair("To", "+237" + num));
        params.add(new BasicNameValuePair("From", s.getCallerID()));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);
        System.out.println(message.getSid());
    }

    public void effacer() {
        operation = new Operation();
        code = null;
        numCmpte = null;
    }

    public String getPasserelle() {
        return passerelle;
    }

    public void setPasserelle(String passerelle) {
        this.passerelle = passerelle;
    }

    public void setOpers(List<Operation> opers) {
        this.opers = opers;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTextC() {
        return textC;
    }

    public void setTextC(String textC) {
        this.textC = textC;
    }

    public String getReceio() {
        return receio;
    }

    public void setReceio(String receio) {
        this.receio = receio;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getNumSrc() {
        return numSrc;
    }

    public void setNumSrc(String numSrc) {
        this.numSrc = numSrc;
    }

    public String getNumDest() {
        return numDest;
    }

    public void setNumDest(String numDest) {
        this.numDest = numDest;
    }

    public IServiceOperation getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(IServiceOperation serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
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

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
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

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
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

    public String getNumCmpte() {
        return numCmpte;
    }

    public void setNumCmpte(String numCmpte) {
        this.numCmpte = numCmpte;
    }

    public IServiceAgence getServiceAgence() {
        return serviceAgence;
    }

    public void setServiceAgence(IServiceAgence serviceAgence) {
        this.serviceAgence = serviceAgence;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public IServiceAdresse getServiceAdresse() {
        return serviceAdresse;
    }

    public void setServiceAdresse(IServiceAdresse serviceAdresse) {
        this.serviceAdresse = serviceAdresse;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public IServiceSmsCompte getServiceSmsCompte() {
        return serviceSmsCompte;
    }

    public void setServiceSmsCompte(IServiceSmsCompte serviceSmsCompte) {
        this.serviceSmsCompte = serviceSmsCompte;
    }

    public SmsCompte getSmsCompte() {
        return smsCompte;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public void setSmsCompte(SmsCompte smsCompte) {
        this.smsCompte = smsCompte;
    }
}
