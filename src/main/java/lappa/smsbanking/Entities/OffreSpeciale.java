/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.Entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author lappa
 */
@Entity
public class OffreSpeciale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean recevoireSalutation;
    private boolean recevoireOffreSpeciale;
    private boolean recevoireReleveCompte;
    private boolean recevoireAlerte;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Personne idPersonne;

    public OffreSpeciale() {
    }

    public OffreSpeciale(boolean recevoireSalutation, boolean recevoireOffreSpeciale, boolean recevoireReleveCompte, boolean recevoireAlerte, Personne idPersonne) {
        this.recevoireSalutation = recevoireSalutation;
        this.recevoireOffreSpeciale = recevoireOffreSpeciale;
        this.recevoireReleveCompte = recevoireReleveCompte;
        this.recevoireAlerte = recevoireAlerte;
        this.idPersonne = idPersonne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRecevoireSalutation() {
        return recevoireSalutation;
    }

    public void setRecevoireSalutation(boolean recevoireSalutation) {
        this.recevoireSalutation = recevoireSalutation;
    }

    public boolean isRecevoireOffreSpeciale() {
        return recevoireOffreSpeciale;
    }

    public void setRecevoireOffreSpeciale(boolean recevoireOffreSpeciale) {
        this.recevoireOffreSpeciale = recevoireOffreSpeciale;
    }

    public boolean isRecevoireReleveCompte() {
        return recevoireReleveCompte;
    }

    public void setRecevoireReleveCompte(boolean recevoireReleveCompte) {
        this.recevoireReleveCompte = recevoireReleveCompte;
    }

    public boolean isRecevoireAlerte() {
        return recevoireAlerte;
    }

    public void setRecevoireAlerte(boolean recevoireAlerte) {
        this.recevoireAlerte = recevoireAlerte;
    }

    public Personne getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Personne idPersonne) {
        this.idPersonne = idPersonne;
    }

   
}