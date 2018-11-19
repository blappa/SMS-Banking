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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.Entities.Sms;
import lappa.smsbanking.Entities.SmsCompte;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServicePersonne;
import IServiceSupport.IServicePersonneMoral;
import IServiceSupport.IServicePersonnePhys;
import com.twilio.sdk.resource.factory.AccountFactory;
import com.twilio.sdk.resource.factory.OutgoingCallerIdFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.CallerIdValidation;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import javax.el.PropertyNotFoundException;
import javax.servlet.ServletContext;
import lappa.smsbanking.Entities.CompteParent;
import lappa.smsbanking.Entities.Support;
import lappa.smsbanking.IService.IServiceCompteParent;
import lappa.smsbanking.IService.IServiceSms;
import lappa.smsbanking.IService.IServiceSmsCompte;

/**
 *
 * @author lappa
 */
@ManagedBean
@SessionScoped
public class SmsBean implements Serializable {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "scriptShell";
    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{serviceSmsCompte}")
    private IServiceSmsCompte serviceSmsCompte;
    @ManagedProperty(value = "#{serviceSms}")
    private IServiceSms serviceSms;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{servicePersonnePhysique}")
    private IServicePersonnePhys servicePersonnePhys;
    @ManagedProperty(value = "#{servicePersonneMorale}")
    private IServicePersonneMoral servicePersonneMoral;
    @ManagedProperty(value = "#{serviceCompteParent}")
    private IServiceCompteParent serviceCompteParent;
    private CompteParent compteParent;
    private SmsCompte smsCompte;
    private Sms sms;
    private Personne personne;
    private Personne current;
    private Support support;
    private List<SmsCompte> smsComptes = new ArrayList<SmsCompte>();
    private List<CompteParent> compteParents;
    private List<Sms> smsSoldes;
    private List<Sms> smsHistos;
    private List<Sms> smsCheques;
    private List<Sms> smsTransferts;
    private String idClient;
    private String code;
    private String codeV;
    private String mobile;
    private List<PersonnePhysique> personnePhysiques = new ArrayList<PersonnePhysique>();
    private List<Personne> personnes;
    private List<PersonneMorale> personneMorales = new ArrayList<PersonneMorale>();
    private List<Support> supports = new ArrayList<Support>();
    private int i = 0;
    private int pin = 0;
    private String passerelle = "localhost";
    private String receip;
    private String text;
    private String username = "lappa";
    private String nomAgence;
    private String passwd = "lappa";
    private String form;

    public SmsBean() {
        personne = new Personne();
        sms = new Sms();
        smsCompte = new SmsCompte();
        compteParent = new CompteParent();
        support = new Support();
        current = new Personne();
        compteParents = new ArrayList<CompteParent>();
        smsTransferts = new ArrayList<Sms>();
        smsHistos = new ArrayList<Sms>();
        smsSoldes = new ArrayList<Sms>();
        smsCheques = new ArrayList<Sms>();
    }

    public String saveCompteClient(ActionEvent actionEven) throws DataAccessException, IOException, Exception {
//        try {
//            personne  = servicePersonne.findByIdClient(smsCompte.getIdClient());
        personnePhysiques = servicePersonnePhys.findAll();
        compteParent = serviceCompteParent.findByIdClient(idClient);
        for (PersonnePhysique personnePhys1 : personnePhysiques) {
            if (personnePhys1.getIdPersonne().getIdClient().equals(smsCompte.getIdClient())) {
                smsCompte.setIdPersonnePhysique(personnePhys1);
                smsCompte.setIdClient(personnePhys1.getIdPersonne().getIdClient());
                receip = smsCompte.getMobile();
            }
        }
        personneMorales = servicePersonneMoral.findAll();
        for (PersonneMorale personneMoral1 : personneMorales) {
            if (personneMoral1.getIdPersonne().getIdClient().equals(smsCompte.getIdClient())) {
                smsCompte.setIdPersonneMoral(personneMoral1);
                smsCompte.setIdClient(personneMoral1.getIdPersonne().getIdClient());
                receip = smsCompte.getMobile();
            }
        }
        try {
            SmsCompte s = serviceSmsCompte.findByIdClient(smsCompte.getIdClient());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte Client existe déja!", ""));
        } catch (NoResultException no) {
            try {
                testsms();
                pin = generCodePin();
                if (pin != 0) {
                    text = "Le+CREDIT+du+SAHEL+vous+informe+de+l'+ouverture+de+votre+compte+SMS,+votre+code+PIN+est:+" + pin + "+.+Merci";
                    sendsms();
                    smsCompte.setPin(pin);
                    smsCompte.setCompteParent(compteParent);
                    serviceSmsCompte.save(smsCompte);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "compte SMS '" + smsCompte.getIdClient() + "' enregistré avec succés", ""));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'application n'a pas pu generer un code PIN, contactez l'administrateur", ""));
                }
            } catch (ConnectException ex) {
                try {
                    // twilio("73246159", "lappa");
                    // deconect();
                    pin = generCodePin();
                    smsCompte.setPin(pin);
                    smsCompte.setCompteParent(compteParent);
//                codeV = verificationCode(compteParent, receip);
//                smsCompte.setCodeVerification(codeV);
//                SmsCompte sc = serviceSmsCompte.findByIdClient(smsCompte.getIdClient());
                    text = "Le CREDIT du SAHEL vous informe de l'ouverture de votre compte SMS, votre code PIN est: " + pin + " .Merci";
//                twilio(receip, text, smsCompte);
                    shellSend(receip, text, compteParent);
                    serviceSmsCompte.save(smsCompte);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "compte SMS '" + smsCompte.getIdClient() + "' enregistré avec succés", ""));
                } catch (RuntimeException run) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur de connection au serveur SMS, 'opération non effectuée!'", ""));
                }
            }
        }
        return "save";
    }

    public void saveCompteParent(ActionEvent actionEven) throws DataAccessException, IOException {
        personnePhysiques = servicePersonnePhys.findAll();
        for (PersonnePhysique personnePhys1 : personnePhysiques) {
            if (personnePhys1.getIdPersonne().getIdClient().equals(compteParent.getIdClient())) {
                smsCompte.setIdPersonnePhysique(personnePhys1);
                receip = smsCompte.getMobile();
            }
        }
        personneMorales = servicePersonneMoral.findAll();
        for (PersonneMorale personneMoral1 : personneMorales) {
            if (personneMoral1.getIdPersonne().getIdClient().equals(compteParent.getIdClient())) {
                smsCompte.setIdPersonneMoral(personneMoral1);
                receip = smsCompte.getMobile();
            }
        }
        try {
            CompteParent c= serviceCompteParent.findByIdClient(compteParent.getIdClient());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Compte Parent existe déja!", ""));
        } catch (NoResultException no) {
            try {
                pin = generCodePin();
                smsCompte.setPin(pin);
                smsCompte.setIdClient(compteParent.getIdClient());
                smsCompte.setCompteParent(compteParent);
                serviceCompteParent.save(compteParent);
                serviceSmsCompte.save(smsCompte);
                text = "Le CREDIT du SAHEL vous informe de l'ouverture de votre compte SMS, votre code PIN est: " + pin + " .Merci";
//                twilio(receip, text, smsCompte);
                shellSend(receip, text, compteParent);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "compte Parent enregistré avec succés", ""));
            } catch (java.lang.NullPointerException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur-- enregistrement non effectué!", ""));
            }
        }
    }

    public String verificationCode(CompteParent c, String num) throws TwilioRestException, DataAccessException {
        String ACCOUNT_SID = c.getAccountID();
        String AUTH_TOKEN = c.getToken();
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Map<String, String> params = new HashMap<String, String>();
        params.put("FriendlyName", "cl" + generCodePin());
        params.put("PhoneNumber", "+237" + num);
        AccountFactory accountFactory = client.getAccountFactory();
//        Account account = accountFactory.create(params);
        OutgoingCallerIdFactory callerIdFactory = client.getAccount().getOutgoingCallerIdFactory();
        CallerIdValidation validationAttempt = callerIdFactory.create(params);
        return validationAttempt.getValidationCode();
//        return account.getFriendlyName();
    }

    public String effacer() {
        smsCompte = new SmsCompte();
        compteParent = new CompteParent();
        pin = 0;
        return "save";
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

    public void shellSend(String num, String text, CompteParent s) throws IOException {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        String cmd[] = {path + File.separator + "send.sh", s.getCallerID(), s.getAccountID(), s.getToken(), "+237" + num, text};
        p = r.exec(cmd);
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

    public void readBashScript() {
        try {
            Process proc = Runtime.getRuntime().exec(path + File.separator + "bearerbox.sh"); //Whatever you want to execute
            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "bearbox lancé avec succés!'", ""));
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (read.ready()) {
                System.out.println(read.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur bearbox!'", ""));
        }
        try {
            Process proc1 = Runtime.getRuntime().exec(path + File.separator + "smsbox.sh"); //Whatever you want to execute
            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc1.getInputStream()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "smsbox lancé avec succés!'", ""));
            try {
                proc1.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (read.ready()) {
                System.out.println(read.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur smsbox!'", ""));
        }
    }

    public void delete() throws DataAccessException {
        Sms s = serviceSms.findByIdS(sms.getId());
        serviceSms.delete(s.getId());
    }

    public void deleteCompte() throws DataAccessException {
        SmsCompte s = serviceSmsCompte.findByIdS(smsCompte.getId());
        serviceSmsCompte.delete(s.getId());
    }

    public int generCodePin() throws DataAccessException {
        int pin = (int) (Math.random() * 9999);
        try {
            SmsCompte s = serviceSmsCompte.findByPin(pin);
            i++;
            if (i != 9999) {
                pin = generCodePin();
            } else {
                return 0;
            }
        } catch (NoResultException ex) {
            return pin;
        }
        return pin;
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

    public List<CompteParent> getCompteParents() throws DataAccessException {
        compteParents = serviceCompteParent.findAll();
        return compteParents;
    }

    public void setCompteParents(List<CompteParent> compteParents) {
        this.compteParents = compteParents;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public IServiceSmsCompte getServiceSmsCompte() {
        return serviceSmsCompte;
    }

    public void setServiceSmsCompte(IServiceSmsCompte serviceSmsCompte) {
        this.serviceSmsCompte = serviceSmsCompte;
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getPin() {
        return pin;
    }

    public String getCodeV() {
        return codeV;
    }

    public void setCodeV(String codeV) {
        this.codeV = codeV;
    }

    public void setPin(int pin) {
        this.pin = pin;
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

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getCurrent() {
        return current;
    }

    public void setCurrent(Personne current) {
        this.current = current;
    }

    public String couleur(SmsCompte v) throws DataAccessException {
//        smsComptes = serviceSmsCompte.findAll();
//        for (SmsCompte smsCompte1 : smsComptes) {
        if (v.getPin() == 0) {
            return "redc";
        } else {
        }
//        }
        return null;
    }

    public String saveChoix(ActionEvent actionEven) throws DataAccessException, MalformedURLException, IOException {
        Support s = serviceSms.findByIdSup(1L);
        if (s == null) {
            serviceSms.saveSupport(support);
        } else {
            support.setId(s.getId());
            serviceSms.updateSupport(support);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "enregistrement fait!", ""));
        return "affiche";
    }

    public String affich() throws DataAccessException {
        try {
            support = serviceSms.findByIdSup(1L);
        } catch (PropertyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun Support Selectionné!", ""));
        }
        return "affiche";
    }

    public List<Support> getSupports() throws DataAccessException {
        supports = serviceSms.findAllS();
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }

    public List<Sms> getSmsCheques() throws DataAccessException {
        List<Sms> smses = serviceSms.findAll();
        for (Sms sms1 : smses) {
            if (sms1.getType().equals("cheque")) {
                smsCheques.add(sms1);
            }
        }
        return smsCheques;
    }

    public void setSmsCheques(List<Sms> smsCheques) {
        this.smsCheques = smsCheques;
    }

    public List<SmsCompte> getSmsComptes() throws DataAccessException {
        smsComptes = serviceSmsCompte.findAll();
        return smsComptes;
    }

    public List<Sms> getSmsSoldes() throws DataAccessException {
        List<Sms> smses = serviceSms.findAll();
        for (Sms sms1 : smses) {
            if (sms1.getType().equals("solde")) {
                smsSoldes.add(sms1);
            }
        }
        return smsSoldes;
    }

    public void setSmsSoldes(List<Sms> smsSoldes) {
        this.smsSoldes = smsSoldes;
    }

    public List<Sms> getSmsHistos() throws DataAccessException {
        List<Sms> smses = serviceSms.findAll();
        for (Sms sms1 : smses) {
            if (sms1.getType().equals("historique")) {
                smsHistos.add(sms1);
            }
        }
        return smsHistos;
    }

    public void setSmsHistos(List<Sms> smsHistos) {
        this.smsHistos = smsHistos;
    }

    public List<Sms> getSmsTransferts() throws DataAccessException {
        List<Sms> smses = serviceSms.findAll();
        for (Sms sms1 : smses) {
            if (sms1.getType().equals("credit")) {
                smsTransferts.add(sms1);
            }
        }
        return smsTransferts;
    }

    public void setSmsTransferts(List<Sms> smsTransferts) {
        this.smsTransferts = smsTransferts;
    }

    public void setSmsComptes(List<SmsCompte> smsComptes) {
        this.smsComptes = smsComptes;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
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

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
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

    public List<PersonnePhysique> getPersonnePhysiques() {
        return personnePhysiques;
    }

    public void setPersonnePhysiques(List<PersonnePhysique> personnePhysiques) {
        this.personnePhysiques = personnePhysiques;
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(List<Personne> personnes) {
        this.personnes = personnes;
    }

    public List<PersonneMorale> getPersonneMorales() {
        return personneMorales;
    }

    public void setPersonneMorales(List<PersonneMorale> personneMorales) {
        this.personneMorales = personneMorales;
    }

    public void setServiceAgence(IServiceAgence serviceAgence) {
        this.serviceAgence = serviceAgence;
    }

    public IServiceSms getServiceSms() {
        return serviceSms;
    }

    public void setServiceSms(IServiceSms serviceSms) {
        this.serviceSms = serviceSms;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
