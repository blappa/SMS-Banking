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
import java.io.InputStream;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.OffreSpeciale;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.SmsCompte;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceCompte;
import IServiceSupport.IServiceOffreSpeciale;
import IServiceSupport.IServicePersonne;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.el.ELException;
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
@RequestScoped
public class OffreBean implements Serializable {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "scriptShell";
    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{serviceOffreSpeciale}")
    private IServiceOffreSpeciale serviceOffreSpeciale;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{serviceSmsCompte}")
    private IServiceSmsCompte serviceSmsCompte;
    @ManagedProperty(value = "#{serviceCompte}")
    private IServiceCompte serviceCmpte;
    @ManagedProperty(value = "#{serviceCompteParent}")
    private IServiceCompteParent serviceCompteParent;
    private Agence agence;
    private SmsCompte smsCompte;
    private OffreSpeciale offreSpeciale;
    private CompteParent compteParent;
    private Personne personne;
    private Personne current;
    private List<OffreSpeciale> offreSpeciales;
    private List<SmsCompte> smsComptes = new ArrayList<SmsCompte>();
    private List<Compte> comptes;
    private Long idClient;
    private String code;
    private String num;
    private String observation;
    private String passerelle = "localhost";
    private String receip;
    private String text;
    private String username = "lappa";
    private String passwd = "lappa";
    private boolean bannee = false;
    private boolean mouton = false;
    private boolean noel = false;
    private boolean credit = false;

    public OffreBean() {
        personne = new Personne();
        smsCompte = new SmsCompte();
        offreSpeciale = new OffreSpeciale();
        current = new Personne();
        compteParent = new CompteParent();
    }

    public void saveOffreSpeciale(ActionEvent actionEven) throws DataAccessException {
        int i = 0;
        try {
            Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                personne = servicePersonne.findByIdClient(personne.getIdClient());
                offreSpeciales = getOffreSpeciales();
                if (!offreSpeciales.isEmpty()) {
                    for (OffreSpeciale offreSpeciale1 : offreSpeciales) {
                        if (personne.getIdClient().equals(offreSpeciale1.getIdPersonne().getIdClient())) {
                            i = 1;
                        }
                    }
                }
                if (i == 0) {
                    serviceOffreSpeciale.save(offreSpeciale, personne.getIdClient());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "enregistrement avec succés", ""));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'ID Du Client' existe déja", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'ID Du Client' n'existe pas", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "erreur 'Code agence' '" + personne.getCodeAgence() + "' n'existe pas", ""));
        }
    }

    public void nosOffre(ActionEvent actionEven) throws DataAccessException, Exception, IOException {
        int i = 0;
        offreSpeciales = serviceOffreSpeciale.findAll();
        for (OffreSpeciale offreSpeciale1 : offreSpeciales) {
            try {
                smsCompte = serviceSmsCompte.findByIdClient(offreSpeciale1.getIdPersonne().getIdClient());
                compteParent = serviceCompteParent.findByIdClient(smsCompte.getCompteParent().getIdClient());
                if (offreSpeciale1.isRecevoireAlerte()) {
                    i = 1;
                    receip = smsCompte.getMobile();
                    try {
                        text = "Le+CREDIT+du+SAHEL+plus+proche+de+vous+pour+le+progres";
                        sendsms(receip);
                    } catch (ConnectException ex) {
                        try {
                            text = "Le CREDIT du SAHEL plus proche de vous pour le progres";
//                            twilio(receip, text, smsCompte);
                            shellSend(receip, text, compteParent);
                        } catch (UnknownHostException run) {
                            i = 2;
                        } catch (RuntimeException run) {
                            i = 2;
                        }
                    }
                }
                if (offreSpeciale1.isRecevoireOffreSpeciale()) {
                    i = 1;
                    receip = smsCompte.getMobile();
//                text = "";
//                  try {
//                    sendsms(receip);
//                     } catch (ConnectException ex) {
//                       i=2;
//                    }
                }
                if (offreSpeciale1.isRecevoireReleveCompte()) {
                    receip = smsCompte.getMobile();
                    if (smsCompte.getIdPersonnePhysique() != null) {
                        try {
                            comptes = serviceCmpte.findAll();
                            if (!comptes.isEmpty()) {
                                for (Compte compte1 : comptes) {
                                    if (compte1.getIdPersonnePhysique() != null) {
                                        if (smsCompte.getIdPersonnePhysique().getIdPersonne().getNumeroCompte().equals(compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte())) {
                                            i = 1;
                                            try {
                                                text = "Le+CREDIT+du+SAHEL+vous+informe+de+votre+releve+de+compte+:+-Numero+Compte+'+" + compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte() + "+'-+Solde+" + compte1.getSolde() + "+-+Date+de+création+du+compte+'+" + compte1.getDateCreation();
                                                sendsms(receip);
                                            } catch (ConnectException ex) {
                                                try {
                                                    text = "Le CREDIT du SAHEL vous informe de votre releve de compte: -Numéro Compte'" + compte1.getIdPersonnePhysique().getIdPersonne().getNumeroCompte() + " '-Solde" + compte1.getSolde() + "-Date de creation du compte' " + compte1.getDateCreation();
//                                                    twilio(receip, text, smsCompte);
                                                    shellSend(receip, text, compteParent);
                                                } catch (UnknownHostException run) {
                                                    i = 2;
                                                } catch (RuntimeException run) {
                                                    i = 2;
                                                }
                                            }
                                        }
                                        if (compte1.getIdPersonneMoral() != null) {
                                            if (current.getId().equals(compte1.getIdPersonneMoral().getId()) || current.getNumeroCompte().equals(compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte())) {
                                                i = 1;
                                                try {
                                                    text = "Le+CREDIT+du+SAHEL+vous+informe+de+votre+releve+de+compte+:+-Numéro+Compte+'+" + compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte() + "+'+-+" + compte1.getSolde() + "+-+Date+de+création+du+compte+'+" + compte1.getDateCreation();
                                                    sendsms(receip);
                                                } catch (ConnectException ex) {
                                                    try {
                                                        text = "Le CREDIT du SAHEL vous informe de votre releve de compte: -Numéro Compte' " + compte1.getIdPersonneMoral().getIdPersonne().getNumeroCompte() + "'-" + compte1.getSolde() + " - Date de creation du compte'" + compte1.getDateCreation();
//                                                        twilio(receip, text, smsCompte);
                                                        shellSend(receip, text, compteParent);
                                                    } catch (UnknownHostException run) {
                                                        i = 2;
                                                    } catch (RuntimeException run) {
                                                        i = 2;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (NullPointerException ex) {
                            i = 3;
                        }
                    }
                    if (smsCompte.getIdPersonneMoral() != null) {
                        try {
                            i = 1;
                            Compte c = serviceCmpte.findByNumeroCompte(smsCompte.getIdPersonneMoral().getIdPersonne().getNumeroCompte());
                            text = "Le+CREDIT+du+SAHEL+vous+informe+de+votre+releve+de+compte+:+-+" + c.getSolde() + "+\n-+" + c.getDateCreation();
                            try {
                                sendsms(receip);
                            } catch (ConnectException ex) {
                                try {
                                    text = "Le CREDIT du SAHEL vous informe de votre releve de compte: -" + c.getSolde() + "\n -" + c.getDateCreation();
//                                    twilio(receip, text, smsCompte);
                                    shellSend(receip, text, compteParent);
                                } catch (UnknownHostException run) {
                                    i = 2;
                                } catch (RuntimeException run) {
                                    i = 2;
                                }
                            }
                        } catch (NullPointerException ex) {
                            i = 1;
                        }
                    }
                }
                if (offreSpeciale1.isRecevoireSalutation()) {
                    i = 1;
                    receip = smsCompte.getMobile();
                    try {
                        text = "Le+CREDIT+du+SAHEL+vous+souhaite+une+bonne+journee";
                        sendsms(receip);
                    } catch (ConnectException ex) {
                        try {
                            text = "Le CREDIT du SAHEL vous souhaite une bonne journee";
//                            twilio(receip, text, smsCompte);
                            shellSend(receip, text, compteParent);
                        } catch (UnknownHostException run) {
                            i = 2;
                        } catch (RuntimeException run) {
                            i = 2;
                        }
                    }
                }
            } catch (NoResultException ex) {
                i = 4;
            }
        }
        if (i == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Les offres ont été transmises", ""));
        }
        if (i == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connection au serveur SMS, 'opérations non effectuées!'", ""));
        }
        if (i == 3) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Les Comptes SMS non existant", ""));
        }
        if (i == 4 || i == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aucune Offre n'a été enregistrée pour ce service ,'Les messages ne sont pas envoyés'", ""));
        }
    }

    public void sendoffre(ActionEvent actionEven) throws DataAccessException, Exception, IOException {
        int i = 0;
//        try {
        // testsms();
        smsComptes = serviceSmsCompte.findAll();
        for (SmsCompte smsCompte1 : smsComptes) {
            receip = smsCompte1.getMobile();
            compteParent = serviceCompteParent.findByIdClient(smsCompte1.getCompteParent().getIdClient());
            if (alerte(smsCompte1)) {
                if (bannee) {
                    try {
                        text = "Le+CREDIT+du+SAHEL+vous+souhaite+ces+voeux+de+bonheur+et+de+santé+pour+cette+nouvelle+annee.";
                        sendsms(receip);
                        i = 1;
                    } catch (ConnectException ex) {
                        try {
                            text = "Le CREDIT du SAHEL vous souhaite ces voeux de bonheur et de sante pour cette nouvelle annee.";
//                                twilio(receip, text, smsCompte1);
                            shellSend(receip, text, compteParent);
                            i = 1;
                        } catch (UnknownHostException run) {
                            i = 2;
                        }
                    } catch (RuntimeException run) {
                        i = 2;
                    }
                }
                if (mouton) {
                    try {
                        text = "Le+CREDIT+du+SAHEL+vous+souhaite+le+BARKA+DA+SALLAH+.";
                        sendsms(receip);
                        i = 1;
                    } catch (ConnectException ex) {
                        try {
                            text = "Le CREDIT du SAHEL vous souhaite le BARKA DA SALLAH.";
//                                twilio(receip, text, smsCompte1);
                            shellSend(receip, text, compteParent);
                            i = 1;
                        } catch (UnknownHostException run) {
                            i = 2;
                        } catch (RuntimeException run) {
                            i = 2;
                        }
                    }
                }
                if (noel) {
                    try {
                        text = "Le+CREDIT+du+SAHEL+vous+souhaite+joyeux+noél+.";
                        sendsms(receip);
                        i = 1;
                    } catch (ConnectException ex) {
                        text = "Le CREDIT du SAHEL vous souhaite joyeux noel.";
//                            twilio(receip, text, smsCompte);
                        shellSend(receip, text, compteParent);
                        i = 1;
                    } catch (UnknownHostException run) {
                        i = 2;
                    } catch (RuntimeException run) {
                        i = 2;
                    }
                }
                if (credit) {
                    try {
                        text = "Le+CREDIT+du+SAHEL+vous+informe+de+la+disponibilité+du+credit+.Venez+et+bénéficiez+de+cette+offre+spéciale+.+Merci";
                        sendsms(receip);
                        i = 1;
                    } catch (ConnectException ex) {
                        text = "Le CREDIT du SAHEL vous informe de la disponibilité du credit.Venez et beneficiez de cette offre speciale.Merci";
//                            twilio(receip, text, smsCompte);
                        shellSend(receip, text, compteParent);
                        i = 1;
                    } catch (UnknownHostException run) {
                        i = 2;
                    } catch (RuntimeException run) {
                        i = 2;
                    }
                }
            }
        }
        if (i == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Les offres ont été transmises", ""));
        }
        if (i == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun Serveur SMS Connecté!", ""));
        }
        if (i == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous Devez Choissir Une Offre! ", ""));
        }
//        } catch (ConnectException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connection au serveur SMS, 'opérations non effectuées!'", ""));
//        }

    }

    public void sendSms(ActionEvent actionEven) throws DataAccessException, Exception, IOException {
        int i = 0;
        smsComptes = serviceSmsCompte.findAll();
        for (SmsCompte smsCompte1 : smsComptes) {
            receip = smsCompte1.getMobile();
            compteParent = serviceCompteParent.findByIdClient(smsCompte1.getCompteParent().getIdClient());
            if (alerte(smsCompte1)) {
                if (num.equals(smsCompte1.getMobile())) {
                    try {
                        text = observation;
                        sendsms(receip);
                        i = 1;
                    } catch (ConnectException ex) {
                        try {
                            text = observation;
//                                twilio(receip, text, smsCompte1);
                            shellSend(receip, text, compteParent);
                            i = 1;
                        } catch (UnknownHostException run) {
                            i = 2;
                        }
                    } catch (IllegalArgumentException el) {
                        i = 2;
                    }
                }
            }
        }
        if (i == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Les offres ont été transmises", ""));
        }
        if (i == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun Serveur SMS Connecté!", ""));
        }
        if (i == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun Compte n'a étè enregistré pour ce type d'operation! ", ""));
        }
    }

    public boolean alerte(SmsCompte s) throws DataAccessException, Exception {
        boolean i = false;
        offreSpeciales = serviceOffreSpeciale.findAll();
        for (OffreSpeciale offreSpeciale1 : offreSpeciales) {
            try {
                smsCompte = serviceSmsCompte.findByIdClient(offreSpeciale1.getIdPersonne().getIdClient());
                if (smsCompte.getIdClient().equals(s.getIdClient())) {
                    if (offreSpeciale1.isRecevoireAlerte()) {
                        i = true;
                        return i;
                    }
                }
            } catch (NoResultException ex) {
                i = false;
            }
        }
        return i;
    }

    public void delete() throws DataAccessException {
        OffreSpeciale o = serviceOffreSpeciale.findByIdO(offreSpeciale.getId());
        serviceOffreSpeciale.delete(o.getId());
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

    public void testsms() throws Exception {
        String theUrl = "http://" + passerelle + ":14000/cgi-bin/sendsms?username=" + username + "&password=" + passwd + "&to=73751558&text=ok";
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

//        deconect();

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

    public List<SmsCompte> getSmsComptes() {
        return smsComptes;
    }

    public void setSmsComptes(List<SmsCompte> smsComptes) {
        this.smsComptes = smsComptes;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public boolean isBannee() {
        return bannee;
    }

    public void setBannee(boolean bannee) {
        this.bannee = bannee;
    }

    public boolean isMouton() {
        return mouton;
    }

    public void setMouton(boolean mouton) {
        this.mouton = mouton;
    }

    public boolean isNoel() {
        return noel;
    }

    public void setNoel(boolean noel) {
        this.noel = noel;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public IServiceOffreSpeciale getServiceOffreSpeciale() {
        return serviceOffreSpeciale;
    }

    public void setServiceOffreSpeciale(IServiceOffreSpeciale serviceOffreSpeciale) {
        this.serviceOffreSpeciale = serviceOffreSpeciale;
    }

    public OffreSpeciale getOffreSpeciale() {
        return offreSpeciale;
    }

    public void setOffreSpeciale(OffreSpeciale offreSpeciale) {
        this.offreSpeciale = offreSpeciale;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public List<OffreSpeciale> getOffreSpeciales() throws DataAccessException {
        offreSpeciales = serviceOffreSpeciale.findAll();
        return offreSpeciales;
    }

    public void setOffreSpeciales(List<OffreSpeciale> offreSpeciales) {
        this.offreSpeciales = offreSpeciales;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
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

    public Personne getCurrent() {
        return current;
    }

    public void setCurrent(Personne current) {
        this.current = current;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public void setSmsCompte(SmsCompte smsCompte) {
        this.smsCompte = smsCompte;
    }

    public IServiceCompte getServiceCmpte() {
        return serviceCmpte;
    }

    public void setServiceCmpte(IServiceCompte serviceCmpte) {
        this.serviceCmpte = serviceCmpte;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
