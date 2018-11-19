/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import com.douwe.generic.dao.DataAccessException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import lappa.smsbanking.Entities.Agence;
import IServiceSupport.IServiceAgence;

/**
 *
 * @author lappa
 */
@ManagedBean
@RequestScoped
public class AgenceBean implements Serializable {

    @ManagedProperty(value = "#{serviceAgence}")
    private IServiceAgence serviceAgence;
    private Agence agence;
    private List<Agence> agences;
    private Long idAgence;

    public AgenceBean() {
        agence = new Agence();
    }

    public void saveAgence(ActionEvent actionEven) throws DataAccessException {
        try {
            Agence a = serviceAgence.findByCodeAgence(agence.getCodeAgence());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Code agence '" + agence.getCodeAgence() + "' existe déja", ""));
        } catch (NoResultException ex) {
            serviceAgence.save(agence);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "'agence' " + agence.getNomAgence() + " enregistrée avec succés", ""));
        }
    }

    public void updateAdresse(ActionEvent actionEven) throws DataAccessException {
        Agence a = serviceAgence.findByIdA(agence.getId());
        agence.setId(a.getId());
        serviceAgence.update(agence);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "mise à jour avec succés", ""));
    }

    public IServiceAgence getServiceAgence() {
        return serviceAgence;
    }

    public void delete(ActionEvent actionEven) throws DataAccessException {
        System.out.println(""+agence.getId());
        serviceAgence.delete(agence.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "suppréssion avec succées", ""));
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

    public List<Agence> getAgences() throws DataAccessException {
        agences = serviceAgence.findAll();
        return agences;
    }

    public void setAgences(List<Agence> agences) {
        this.agences = agences;
    }

    public Long getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(Long idAgence) {
        this.idAgence = idAgence;
    }
}
