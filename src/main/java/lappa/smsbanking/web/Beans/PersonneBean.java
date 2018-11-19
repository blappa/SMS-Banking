/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import com.douwe.generic.dao.DataAccessException;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Autorisation;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceAutorisation;
import IServiceSupport.IServicePersonne;
import IServiceSupport.IServicePersonneMoral;
import IServiceSupport.IServicePersonnePhys;

/**
 *
 * @author lappa
 */
@ManagedBean
@SessionScoped
public class PersonneBean implements Serializable {

    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{servicePersonnePhysique}")
    private IServicePersonnePhys servicePersonnePhys;
    @ManagedProperty(value = "#{servicePersonneMorale}")
    private IServicePersonneMoral servicePersonneMoral;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    @ManagedProperty(value = "#{serviceAdmin}")
    private IServiceAutorisation serviceAutorisation;
    private Agence agence;
    private Autorisation autorisation;
    private PersonnePhysique personnePhysique;
    private PersonneMorale personneMorale;
    private List<PersonnePhysique> personnePhysiques;
    private List<PersonneMorale> personneMorales;
    private Personne personne;
    private PersonneMorale currentMorale;
    private PersonnePhysique currentPhysique;
    private Long idPersPhys;
    private Long idPersMorale;
    private String code;
    private String idClient;
     private String pwd;
    private int i = 0;

    public PersonneBean() {
        personnePhysique = new PersonnePhysique();
        personneMorale = new PersonneMorale();
        currentPhysique = new PersonnePhysique();
        currentMorale = new PersonneMorale();
        personne = new Personne();
        autorisation=new Autorisation();
    }

    public String saveUser(ActionEvent actionEven) throws DataAccessException {
        if (i == 0) {
            try {
                Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
                idClient = generIdClient(personne);
                if (personne.getTypePersonne().equals("Physique")) {
                    personne.setIdAgence(a);
                    personne.setIdClient(idClient);
                    servicePersonne.save(personne);
                    personne = servicePersonne.findByIdClient(personne.getIdClient());
                    servicePersonnePhys.save(personnePhysique, personne.getId());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "enregistrement avec succés", ""));
                } else {
                    personne.setIdAgence(a);
                    personne.setIdClient(idClient);
                    servicePersonne.save(personne);
                    personne = servicePersonne.findByIdClient(personne.getIdClient());
                    servicePersonneMoral.save(personneMorale, personne.getId());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "enregistrement avec succés", ""));
                }
                i = 1;
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "code agence " + personne.getCodeAgence() + " inexistant", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate 'ID Client' veillez effacer pour initialiser '" + idClient + "' existe déja", ""));
        }
        return "save";
    }

    public String effacer() {
        personne = new Personne();
        personnePhysique = new PersonnePhysique();
        personneMorale = new PersonneMorale();
        idClient = null;
        i = 0;
        return "save";
    }

    public void saveLogin(ActionEvent actionEven) throws DataAccessException {
        try {
            Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                Personne p = servicePersonne.findByIdClient(personne.getIdClient());
                p.setId(p.getId());
                p.setCodeAgence(personne.getCodeAgence());
                p.setIdClient(personne.getIdClient());
                p.setLogin(personne.getLogin());
                autorisation.setUsername(personne.getLogin());
                autorisation.setAuthority("ROLE_USER");
                autorisation.setPassword(pwd);
                autorisation.setAnabled(true);
                autorisation.setPersonnel(p);
                serviceAutorisation.enregistrerNouveauLogin(autorisation);
                servicePersonne.update(p);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Compte de connection crée avec succés", ""));
            } catch (NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, personne.getNomClient() + " " + personne.getIdClient() + " 'cette personne n'exite pas'", ""));
            }
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "code agence inexistant", ""));
        }
    }

    public String generIdClient(Personne p) throws DataAccessException {
        String num = null;
        int nbre = (int) (Math.random() * 9999);
        num = p.getCodeAgence() + 0 + 0 + 0 + 0 + 0 + nbre;
        try {
            Personne p1 = servicePersonne.findByNumeroCompte(num);
            num = generIdClient(p);
        } catch (NoResultException ex) {
            return num;
        }
        return num;
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

    public void deleteUserPhysique() throws DataAccessException {
        PersonnePhysique p = servicePersonnePhys.findById(personnePhysique.getId());
        servicePersonnePhys.delete(p.getId());
    }

    public void deleteUserMorale() throws DataAccessException {
        PersonneMorale p = servicePersonneMoral.findById(personneMorale.getId());
        servicePersonneMoral.delete(p.getId());
    }

    public void setPersonneMorales(List<PersonneMorale> personneMorales) {
        this.personneMorales = personneMorales;
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

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public void setServicePersonneMoral(IServicePersonneMoral servicePersonneMoral) {
        this.servicePersonneMoral = servicePersonneMoral;
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

    public PersonneMorale getCurrentMorale() {
        return currentMorale;
    }

    public void setCurrentMorale(PersonneMorale currentMorale) {
        this.currentMorale = currentMorale;
    }

    public PersonnePhysique getCurrentPhysique() {
        return currentPhysique;
    }

    public void setCurrentPhysique(PersonnePhysique currentPhysique) {
        this.currentPhysique = currentPhysique;
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
