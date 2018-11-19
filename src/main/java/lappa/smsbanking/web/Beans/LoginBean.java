/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import com.douwe.generic.dao.DataAccessException;
import java.lang.NullPointerException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Autorisation;
import lappa.smsbanking.Entities.Personne;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceAutorisation;
import IServiceSupport.IServicePersonne;

/**
 *
 * @author lappa
 */
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne serviceUser;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{serviceAdmin}")
    private IServiceAutorisation serviceAutorisation;
    private Agence agence;
    private Autorisation autorisation;
    private Personne utilisateur;
    private Personne current;
    private String pwd;
    private List<Personne> utilisateurs;

    public LoginBean() {
        utilisateur = new Personne();
        current = new Personne();
        agence = new Agence();
        autorisation = new Autorisation();
    }

    public void init(ActionEvent actionEvent) throws DataAccessException {
        utilisateurs = serviceUser.findAll();
        if (utilisateurs.isEmpty()) {
            Agence a = new Agence("MAROUA", "001", null, null, "Cameroun");
            serviceAgence.save(a);
            Personne u = new Personne("0001", "super user", "0000", "0000", "admin", "admin", "Physique", a);
            serviceUser.save(u);
            autorisation.setUsername("admin");
            autorisation.setAnabled(true);
            autorisation.setAuthority("ROLE_USER");
            autorisation.setPersonnel(u);
            autorisation.setPassword("admin");
            serviceAutorisation.enregistrerNouveauLogin(autorisation);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Un compte par defaut s'est crée avec: \n\t\t LOGIN : admin \n\t\t MOT DE PASSE: admin", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un compte super utilisateur existe déja", ""));
        }
    }

    public IServicePersonne getServiceUser() {
        return serviceUser;
    }

    public void setServiceUser(IServicePersonne serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Personne getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Personne utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getPwd() {
        return pwd;
    }

    public IServiceAutorisation getServiceAutorisation() {
        return serviceAutorisation;
    }

    public void setServiceAutorisation(IServiceAutorisation serviceAutorisation) {
        this.serviceAutorisation = serviceAutorisation;
    }

    public Autorisation getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(Autorisation autorisation) {
        this.autorisation = autorisation;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Personne getCurrent() {
        return current;
    }

    public void setCurrent(Personne current) {
        this.current = current;
    }

    public List<Personne> getUtilisateurs() throws DataAccessException {
        utilisateurs = serviceUser.findAll();
        return utilisateurs;
    }

    public void setUtilisateurs(List<Personne> utilisateurs) {
        this.utilisateurs = utilisateurs;
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
}
