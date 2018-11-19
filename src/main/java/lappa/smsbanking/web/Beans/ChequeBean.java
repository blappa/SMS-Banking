/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import com.douwe.generic.dao.DataAccessException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Chequier;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.SmsCompte;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceChequier;
import IServiceSupport.IServiceCompte;
import IServiceSupport.IServicePersonne;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import lappa.smsbanking.Entities.CompteParent;
import lappa.smsbanking.IService.IServiceCompteParent;
import lappa.smsbanking.IService.IServiceSmsCompte;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author lappa
 */
@ManagedBean
@RequestScoped
public class ChequeBean implements Serializable {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "scriptShell";
    @ManagedProperty(value = "#{serviceChequier}")
    private IServiceChequier serviceChequier;
    @ManagedProperty(value = "#{serviceCompte}")
    private IServiceCompte serviceCmpte;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{serviceSmsCompte}")
    private IServiceSmsCompte serviceSmsCompte;
    @ManagedProperty(value = "#{serviceCompteParent}")
    private IServiceCompteParent serviceCompteParent;
    private Agence agence;
    private CompteParent compteParent;
    private List<Chequier> chequiers;
    private Chequier cheque;
    private Compte compte;
    private Chequier chequier;
    private Personne personne;
    private SmsCompte smsCompte;
    private String numR;
    private String code;
    private String passerelle = "localhost";
    private String receip;
    private String text;
    private String username = "lappa";
    private String passwd = "lappa";

    public ChequeBean() {
        compte = new Compte();
        chequier = new Chequier();
        cheque = new Chequier();
        personne = new Personne();
        smsCompte = new SmsCompte();
        compteParent = new CompteParent();
    }

    public void saveChequier(ActionEvent actionEven) throws DataAccessException, Exception {
        try {
            Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                personne = servicePersonne.findByNumeroCompte(personne.getNumeroCompte());
                smsCompte = serviceSmsCompte.findByIdClient(personne.getIdClient());
                compteParent = serviceCompteParent.findByIdClient(smsCompte.getCompteParent().getIdClient());
                receip = smsCompte.getMobile();
                serviceChequier.save(cheque, personne.getId());
                text = "Le+CREDIT+du+SAHEL+vous+informe+,+vous+avez+déchargé+un+chèquier+de+type+'+" + cheque.getTypeChequier() + "+'+le+'+" + cheque.getDateEmission() + "+'+.";
                sendsms(receip);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " enregistrement avec succés", ""));
            } catch (NoResultException ex) {
                try {
                    personne = servicePersonne.findByNumeroCompte(personne.getNumeroCompte());
                    smsCompte = serviceSmsCompte.findByIdClient(personne.getIdClient());
                    compteParent = serviceCompteParent.findByIdClient(smsCompte.getIdClient());
                    receip = smsCompte.getMobile();
                    serviceChequier.save(cheque, personne.getId());
                    text = "Le CREDIT du SAHEL vous informe, vous avez déchargé un chèquier de type '" + cheque.getTypeChequier() + "'le '" + cheque.getDateEmission() + "'";
                    shellSend(receip, text, compteParent);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'compte' enregistré avec succés", ""));
                } catch (RuntimeException run) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connection au serveur SMS ou Code Client '" + personne.getNumeroCompte() + "' introuvable'", ""));
                }
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "code agence " + personne.getCodeAgence() + " inexistant", ""));
        }
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

    public void delete(ActionEvent actionEven) throws DataAccessException {
    }

    public void shellSend(String num, String text, CompteParent s) throws IOException {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        String cmd[] = {path + File.separator + "send.sh", s.getCallerID(), s.getAccountID(), s.getToken(), "+237" + num, text};
        p = r.exec(cmd);
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

    public IServiceChequier getServiceChequier() {
        return serviceChequier;
    }

    public void setServiceChequier(IServiceChequier serviceChequier) {
        this.serviceChequier = serviceChequier;
    }

    public IServiceCompte getServiceCmpte() {
        return serviceCmpte;
    }

    public void setServiceCmpte(IServiceCompte serviceCmpte) {
        this.serviceCmpte = serviceCmpte;
    }

    public List<Chequier> getChequiers() throws DataAccessException {
        chequiers = serviceChequier.findAll();
        return chequiers;
    }

    public void setChequiers(List<Chequier> chequiers) {
        this.chequiers = chequiers;
    }

    public Chequier getCheque() {
        return cheque;
    }

    public void setCheque(Chequier cheque) {
        this.cheque = cheque;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Chequier getChequier() {
        return chequier;
    }

    public void setChequier(Chequier chequier) {
        this.chequier = chequier;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public IServiceAgence getServiceAgence() {
        return serviceAgence;
    }

    public void setServiceAgence(IServiceAgence serviceAgence) {
        this.serviceAgence = serviceAgence;
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public String getNumR() {
        return numR;
    }

    public void setNumR(String numR) {
        this.numR = numR;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}