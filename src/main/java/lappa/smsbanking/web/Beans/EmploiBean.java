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
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.Entities.Emploi;
import lappa.smsbanking.Entities.Personne;
import IServiceSupport.IServiceAgence;
import IServiceSupport.IServiceEmploi;
import IServiceSupport.IServicePersonne;

/**
 *
 * @author lappa
 */
@ManagedBean
@RequestScoped
public class EmploiBean implements Serializable {

    @ManagedProperty(value = "#{servicePersonne}")
    private IServicePersonne servicePersonne;
    @ManagedProperty(value = "#{serviceEmploi}")
    private IServiceEmploi serviceEmploi;
    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    private Agence agence;
    private Emploi emploi;
    private Personne personne;
    private Personne current;
    private List<Emploi> emplois;
    private Long idClient;
    private String code;

    public EmploiBean() {
        personne = new Personne();
        emploi = new Emploi();
        current = new Personne();
    }

    public void saveEmploi(ActionEvent actionEven) throws DataAccessException {
        int i = 0;
        try {
            Agence a = serviceAgence.findByCodeAgence(personne.getCodeAgence());
            try {
                current = servicePersonne.findByIdClient(personne.getIdClient());
                idClient = current.getId();
                emplois = getEmplois();
                if (!emplois.isEmpty()) {
                    for (Emploi emploi1 : emplois) {
                        if (personne.getIdClient().equals(emploi1.getIdPersonne().getIdClient())) {
                            i = 1;
                        }
                    }
                }
                if (i == 0) {
                    serviceEmploi.save(emploi, idClient);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "emploi' enregistré avec succés", ""));
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

    
    public IServicePersonne getServicePersonne() {
        return servicePersonne;
    }

    public void setServicePersonne(IServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    public IServiceEmploi getServiceEmploi() {
        return serviceEmploi;
    }

    public void setServiceEmploi(IServiceEmploi serviceEmploi) {
        this.serviceEmploi = serviceEmploi;
    }

    public Emploi getEmploi() {
        return emploi;
    }

    public void setEmploi(Emploi emploi) {
        this.emploi = emploi;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public List<Emploi> getEmplois() throws DataAccessException {
        emplois = serviceEmploi.findAll();
        return emplois;
    }

    public void setEmplois(List<Emploi> emplois) {
        this.emplois = emplois;
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
