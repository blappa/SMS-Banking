/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import com.douwe.generic.dao.DataAccessException;
import java.io.IOException;
import java.io.InputStream;
import javax.persistence.NoResultException;
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
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.SmsCompte;
import lappa.smsbanking.Entities.Virement;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceCompte;
import IServiceSupport.IServicePersonne;
import IServiceSupport.IServiceVirement;
import java.io.File;
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
public class VirementBean implements Serializable {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "scriptShell";
    @ManagedProperty(value = "#{serviceVirement}")
    private IServiceVirement serviceVirement;
    @ManagedProperty(value = "#{serviceCompte}")
    private IServiceCompte serviceCompte;
    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{serviceSmsCompte}")
    private IServiceSmsCompte serviceSmsCompte;
     @ManagedProperty(value = "#{serviceCompteParent}")
    private IServiceCompteParent serviceCompteParent;
    private Personne personne;
    private Virement virement;
    private Virement virement1;
    private Agence agence;
     private CompteParent compteParent;
    private Agence agence1;
    private SmsCompte smsCompte;
    private List<Compte> comptes;
    private Compte compte;
    private List<Virement> virements;
    private Long idCompte;
    private String code;
    private String passerelle = "localhost";
    private String receip;
    private String text;
    private String username = "lappa";
    private String passwd = "lappa";

    public VirementBean() {
        personne = new Personne();
        compte = new Compte();
        virement = new Virement();
        smsCompte = new SmsCompte();
         compteParent = new CompteParent();
    }

    public void saveVirement(ActionEvent actionEven) throws DataAccessException, Exception {
        try {
            agence = serviceAgence.findByCodeAgence(virement.getCodeAgenceEnvoi());
            try {
                agence1 = serviceAgence.findByCodeAgence(virement.getCodeAgenceRecep());
                try {
                    personne = servicePersonne.findByCode(virement.getNumCompte());
                    try {
                        compte = serviceCompte.findById(personne.getId());
                        smsCompte = serviceSmsCompte.findByIdClient(personne.getIdClient());
                        compteParent = serviceCompteParent.findByIdClient(smsCompte.getCompteParent().getIdClient());
                        receip = smsCompte.getMobile();
                        idCompte = compte.getId();
                        serviceVirement.save(virement, idCompte);
                        text = "Le+CREDIT+du+SAHEL+vous+informe+,+vous+avez+effectué+un+virement+de+votre+compte+de+agence+'+" + agence.getNomAgence() + "+'+vers+'+" + agence1.getNomAgence() + "+'+.+Merci pour votre fidèlité";
                        try {
                            sendsms(receip);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération enregistrée avec succées", ""));
                        } catch (IOException ex) {
                            try {
                    text = "Le CREDIT du SAHEL vous informe, vous avez effectué un virement sur votre compte";
                    shellSend(receip, text, compteParent);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'compte' enregistré avec succés", ""));
                } catch (RuntimeException run) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connection au serveur SMS ou Code Client '" + personne.getNumeroCompte() + "' introuvable'", ""));
                }
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connection au serveur SMS", ""));
                        }
                    } catch (NullPointerException ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numéro compte introuvable", ""));
                    }
                } catch (NoResultException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numéro compte introuvable", ""));
                }
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Agence Recepteur de code '" + virement.getCodeAgenceRecep() + "' n'existe pas", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agence D'Envoi de code '" + virement.getCodeAgenceEnvoi() + "' n'existe pas", ""));
        }

    }

    public void delete() throws DataAccessException {
        Virement s = serviceVirement.findByIdV(virement.getId());
        serviceVirement.delete(s.getId());
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

    public void shellSend(String num, String text, CompteParent s) throws IOException {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        String cmd[] = {path + File.separator + "send.sh", s.getCallerID(), s.getAccountID(), s.getToken(), "+237" + num, text};
        p = r.exec(cmd);
    }
    
    public IServiceVirement getServiceVirement() {
        return serviceVirement;
    }

    public void setServiceVirement(IServiceVirement serviceVirement) {
        this.serviceVirement = serviceVirement;
    }

    public IServiceCompte getServiceCompte() {
        return serviceCompte;
    }

    public void setServiceCompte(IServiceCompte serviceCompte) {
        this.serviceCompte = serviceCompte;
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public IServiceAgence getServiceAgence() {
        return serviceAgence;
    }

    public void setServiceAgence(IServiceAgence serviceAgence) {
        this.serviceAgence = serviceAgence;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Virement getVirement() {
        return virement;
    }

    public void setVirement(Virement virement) {
        this.virement = virement;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public List<Virement> getVirements() {
        return virements;
    }

    public void setVirements(List<Virement> virements) {
        this.virements = virements;
    }

    public Long getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Long idCompte) {
        this.idCompte = idCompte;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IServiceSmsCompte getServiceSmsCompte() {
        return serviceSmsCompte;
    }

    public void setServiceSmsCompte(IServiceSmsCompte serviceSmsCompte) {
        this.serviceSmsCompte = serviceSmsCompte;
    }

    public Virement getVirement1() {
        return virement1;
    }

    public void setVirement1(Virement virement1) {
        this.virement1 = virement1;
    }

    public SmsCompte getSmsCompte() {
        return smsCompte;
    }

    public void setSmsCompte(SmsCompte smsCompte) {
        this.smsCompte = smsCompte;
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

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Agence getAgence1() {
        return agence1;
    }

    public void setAgence1(Agence agence1) {
        this.agence1 = agence1;
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
    
    
}
