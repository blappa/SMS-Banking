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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import lappa.smsbanking.Entities.Adresse;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Personne;
import IServiceSupport.IServiceAdresse;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServicePersonne;

/**
 *
 * @author lappa
 */
@ManagedBean
@RequestScoped
public class AdresseBean implements Serializable {

    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{serviceAdresse}")
    private IServiceAdresse serviceAdresse;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    private Agence agence;
    private Adresse adresse;
    private Personne personne;
    private Personne current;
    private List<Adresse> adresses;
    private Long idClient;
    private String code;

    public AdresseBean() {
        personne = new Personne();
        adresse = new Adresse();
        current = new Personne();
    }

    public void saveAdresse(ActionEvent actionEven) throws DataAccessException {
        int i = 0;
        try {
            Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                current = servicePersonne.findByIdClient(personne.getIdClient());
                idClient = current.getId();
                adresses = getAdresses();
                if (!adresses.isEmpty()) {
                    for (Adresse adresse1 : adresses) {
                        if (personne.getIdClient().equals(adresse1.getIdPersonne().getIdClient())) {
                            i = 1;
                        }
                    }
                }
                if (i == 0) {
                    serviceAdresse.save(adresse, idClient);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'adresse' enregistré avec succés", ""));
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


    public void delete() throws DataAccessException {
        Adresse a = serviceAdresse.findById(adresse.getId());
        serviceAdresse.delete(a.getId());
    }

    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
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

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public List<Adresse> getAdresses() throws DataAccessException {
        adresses = serviceAdresse.findAll();
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
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
}
