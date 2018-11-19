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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.Entities.SmsCompte;
import IServiceSupport.IServiceAdresse;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceCompte;
import IServiceSupport.IServicePersonne;
import IServiceSupport.IServicePersonneMoral;
import IServiceSupport.IServicePersonnePhys;
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
public class CompteBean implements Serializable {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "scriptShell";
    @ManagedProperty(value = "#{serviceCompte}")
    private IServiceCompte serviceCmpte;
    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{servicePersonnePhysique}")
    private IServicePersonnePhys servicUserPhys;
    @ManagedProperty(value = "#{servicePersonneMorale}")
    private IServicePersonneMoral servicUserMoral;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{serviceAdresse}")
    private IServiceAdresse serviceAdresse;
    @ManagedProperty(value = "#{serviceSmsCompte}")
    private IServiceSmsCompte serviceSmsCompte;
   @ManagedProperty(value = "#{serviceCompteParent}")
    private IServiceCompteParent serviceCompteParent;
    private Adresse adresse;
    private List<Adresse> adresses;
    private Agence agence;
    private CompteParent compteParent;
    private PersonneMorale personneMoral;
    private PersonnePhysique personnePhysique;
    private List<Compte> comptes;
    private SmsCompte smsCompte;
    private Compte compte;
    private Personne personne;
    private Personne current;
    private Long idUser;
    private String idClient;
    private Long idCmpte;
    private String numCmpte;
    private String code;
    private Date dateDebut;
    private Date dateFin;
    private String passerelle = "localhost";
    private String receip;
    private String text;
    private String username = "lappa";
    private String nomAgence;
    private String passwd = "lappa";
    private int j = 0;

    public CompteBean() {
        personnePhysique = new PersonnePhysique();
        compte = new Compte();
        personneMoral = new PersonneMorale();
        personne = new Personne();
        current = new Personne();
        smsCompte = new SmsCompte();
        compteParent =new CompteParent(); 
    }

    public String saveCompte() throws DataAccessException, Exception {
        if (j == 0) {
            int i = 0;
            try {
                agence = serviceAgence.findByCodeAgence(personne.getCodeAgence());
                nomAgence = agence.getVille();
                try {
                    current = servicePersonne.findByIdClient(personne.getIdClient());
                    try {
                        smsCompte = serviceSmsCompte.findByIdClient(personne.getIdClient());
                        compteParent=serviceCompteParent.findByIdClient(smsCompte.getCompteParent().getIdClient());
                        receip = smsCompte.getMobile();
                        comptes = getComptes();
                        for (Compte compte1 : comptes) {
                            if (compte1.getIdPersonnePhysique() != null) {
                                if (compte1.getIdPersonnePhysique().getIdPersonne().getIdClient().equals(current.getIdClient())) {
                                    i = 1;
                                }
                            }
                            if (compte1.getIdPersonneMoral() != null) {
                                if (compte1.getIdPersonneMoral().getIdPersonne().getIdClient().equals(current.getIdClient())) {
                                    i = 1;
                                }
                            }
                        }
                        if (i == 0) {
                            Personne p = servicePersonne.findById(current.getId());
                            p.setId(p.getId());
                            p.setIdAgence(agence);
                            p.setNomClient(personne.getNomClient());
                            try {
                                testsms();
                                numCmpte = generNumComp(p);
                                text = "Le+CREDIT+du+SAHEL+vous+informe+de+l'+ouverture+de+votre+compte+" + compte.getClasseCompte() + "+a+l+'+agence+de+" + nomAgence + "+.+Votre+Num+.+Compte+est+:+" + numCmpte + "+et+votre+solde+est+de+" + compte.getSolde() + "+FCFA+.Envoyez+votre+'+'numero_compte+PIN+HELP'+'+au+73751558+pour+plus+d'information+.+Merci+pour+votre+confiance";
                                sendsms();
                                p.setNumeroCompte(numCmpte);
                                p.setCodeProduit(numCmpte);
                                servicePersonne.update(p);
                                serviceCmpte.save(compte, p.getId());
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'compte' enregistré avec succés", ""));
                            } catch (ConnectException ed) {
                                try {
                                    //twilio("73246159", "lappa");
                                    numCmpte = generNumComp(p);
                                    p.setNumeroCompte(numCmpte);
                                    p.setCodeProduit(numCmpte);
                                    servicePersonne.update(p);
                                    serviceCmpte.save(compte, p.getId());
                                    text = "Le CREDIT du SAHEL vous informe de l'ouverture de votre compte " + compte.getClasseCompte() + " a l'agence de " + nomAgence + ".";
//                                    twilio(receip, text,smsCompte);
                                    shellSend(receip, text, compteParent);
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'compte' enregistré avec succés", ""));
                                } catch (RuntimeException run) {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connection au serveur SMS 'données du compte restent non enregistrées'", ""));
                                }
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ID CLient' '" + current.getIdClient() + "' a déja un compte", ""));
                        }
                    } catch (NoResultException ex) {
                        Personne p = servicePersonne.findById(current.getId());
                        p.setId(p.getId());
                        p.setIdAgence(agence);
                        p.setNomClient(personne.getNomClient());
                        numCmpte = generNumComp(p);
                        p.setNumeroCompte(numCmpte);
                        p.setCodeProduit(numCmpte);
                        servicePersonne.update(p);
                        serviceCmpte.save(compte, p.getId());
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'Compte' enregistré avec succés.", ""));
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Vous devez creer un compte 'SMS' pour ce client. Merci", ""));
                    }
                } catch (NoResultException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'ID Du Client' n'existe pas", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "erreur 'Code agence' '" + personne.getCodeAgence() + "' n'existe pas", ""));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate 'Numéro Compte' veillez cliquez sur effacer pour initialiser '" + numCmpte + "' existe déja", ""));
        }
        return "save";
    }

    public void sendCredit() throws DataAccessException {
        try {
            agence = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                current = servicePersonne.findByCode(personne.getNumeroCompte());
                try {
                    smsCompte = serviceSmsCompte.findByIdClient(current.getIdClient());
                     compteParent=serviceCompteParent.findByIdClient(smsCompte.getIdClient());
                    receip = smsCompte.getMobile();
                    try {
                        testsms();
                        text = "Le+CREDIT+du+SAHEL+vous+informe+que+votre+demande+de+credit+a+ete+validee+.+Merci+de+venir+decharger+et+merci+pour+votre+confiance";
                        sendsms();
                    } catch (ConnectException ed) {
                        try {
                            text = "Le CREDIT du SAHEL vous informe que votre demande de credit a ete validee. Merci de venir decharger et merci pour votre confiance";
//                            twilio(receip, text, smsCompte);
                            shellSend(receip, text, compteParent);
                        } catch (RuntimeException run) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur de connection au serveur Twilio SMS, 'opérations non effectuées!'", ""));
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Message transmis avec succés", ""));
                } catch (NoResultException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'SVP' vous devez creer un compte 'SMS' pour ce client. Merci", ""));
                } catch (Exception ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "erreur 'Code agence' '" + personne.getCodeAgence() + "' n'existe pas", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "'ID Du Client' n'existe pas", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "erreur 'Code agence' '" + personne.getCodeAgence() + "' n'existe pas", ""));
        }
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

    public String generNumComp(Personne p) throws DataAccessException {
        String num = null;
        String cod = "3731";
        int nbre = (int) (Math.random() * 9999);
        num = cod + 0 + 0 + 0 + 0 + 0 + nbre;
        try {
            Personne p1 = servicePersonne.findByNumeroCompte(num);
            num = generNumComp(p);
        } catch (NoResultException ex) {
            return num;
        }
        return num;
    }

    public String effacer() {
        personne = new Personne();
        compte = new Compte();
        numCmpte = null;
        j = 0;
        return "save";
    }

    public void delete(ActionEvent actionEven) throws DataAccessException {
        idCmpte = compte.getId();
        serviceCmpte.delete(idCmpte);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "suppression reussit", ""));
    }

    public List<Compte> getComptes() throws DataAccessException {
        comptes = serviceCmpte.findAll();
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public IServiceCompte getServiceCmpte() {
        return serviceCmpte;
    }

    public void setServiceCmpte(IServiceCompte serviceCmpte) {
        this.serviceCmpte = serviceCmpte;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdCmpte() {
        return idCmpte;
    }

    public void setIdCmpte(Long idCmpte) {
        this.idCmpte = idCmpte;
    }

    public String getNumCmpte() {
        return numCmpte;
    }

    public void setNumCmpte(String numCmpte) {
        this.numCmpte = numCmpte;
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public IServicePersonnePhys getServicUserPhys() {
        return servicUserPhys;
    }

    public void setServicUserPhys(IServicePersonnePhys servicUserPhys) {
        this.servicUserPhys = servicUserPhys;
    }

    public IServicePersonneMoral getServicUserMoral() {
        return servicUserMoral;
    }

    public void setServicUserMoral(IServicePersonneMoral servicUserMoral) {
        this.servicUserMoral = servicUserMoral;
    }

    public PersonneMorale getPersonneMoral() {
        return personneMoral;
    }

    public void setPersonneMoral(PersonneMorale personneMoral) {
        this.personneMoral = personneMoral;
    }

    public PersonnePhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public void setPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Personne getCurrent() {
        return current;
    }

    public void setCurrent(Personne current) {
        this.current = current;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public IServiceAgence getServiceAgence() {
        return serviceAgence;
    }

    public void setServiceAgence(IServiceAgence serviceAgence) {
        this.serviceAgence = serviceAgence;
    }

    public IServiceSmsCompte getServiceSmsCompte() {
        return serviceSmsCompte;
    }

    public void setServiceSmsCompte(IServiceSmsCompte serviceSmsCompte) {
        this.serviceSmsCompte = serviceSmsCompte;
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

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
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

    public SmsCompte getSmsCompte() {
        return smsCompte;
    }

    public void setSmsCompte(SmsCompte smsCompte) {
        this.smsCompte = smsCompte;
    }
}
