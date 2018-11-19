/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.IDao.ICompte;
import lappa.smsbanking.IDao.IPersonne;
import lappa.smsbanking.IDao.IPersonneMorale;
import lappa.smsbanking.IDao.IPersonnePhys;
import IServiceSupport.IServiceCompte;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceCompteImpl implements IServiceCompte {

    private ICompte iCompte;
    private IPersonne iPersonne;
    private IPersonneMorale iPersonneMorale;
    private IPersonnePhys iPersonnePhys;

    @Override
    public Compte findByNumeroCompte(String numeroCompte) throws DataAccessException {
        return iCompte.findByNumeroCompte(numeroCompte);
    }

    @Override
    public void save(Compte c, Long idClient) throws DataAccessException {
        Personne p1 = iPersonne.findByIdP(idClient);
        if (p1.getTypePersonne().equals("Physique")) {
            List<PersonnePhysique> personnePhysiques = iPersonnePhys.findAll();
            if (!personnePhysiques.isEmpty()) {
                for (PersonnePhysique personnePhysique : personnePhysiques) {
                    if (personnePhysique.getIdPersonne().getId().equals(p1.getId())) {
                        c.setIdPersonnePhysique(personnePhysique);
                        iCompte.create(c);
                        return;
                    }
                }
            } else {
                PersonnePhysique p2 = iPersonnePhys.findById(p1.getId());
                c.setIdPersonnePhysique(p2);
                iCompte.create(c);
            }
        } else {
            List<PersonneMorale> personneMorales = iPersonneMorale.findAll();
            if (!personneMorales.isEmpty()) {
                for (PersonneMorale personneMorale : personneMorales) {
                    if (personneMorale.getIdPersonne().getId().equals(p1.getId())) {
                        c.setIdPersonneMoral(personneMorale);
                        iCompte.create(c);
                        return;
                    }
                }
            } else {
                PersonneMorale p3 = iPersonneMorale.findById(p1.getId());
                c.setIdPersonneMoral(p3);
                iCompte.create(c);
            }
        }
    }

    @Override
    public void delete(Long id) throws DataAccessException {
        Compte c = iCompte.findByIdC(id);
        iCompte.delete(c);
    }

    @Override
    public void update(Compte c) throws DataAccessException {
        iCompte.update(c);
    }

    @Override
    public List<Compte> findAll() throws DataAccessException {
        return iCompte.findAll();
    }

    @Override
    public Compte findById(Long id) throws DataAccessException {
        return iCompte.findByIdC(id);
    }

    public ICompte getiCompte() {
        return iCompte;
    }

    public void setiCompte(ICompte iCompte) {
        this.iCompte = iCompte;
    }

    public IPersonneMorale getiPersonneMorale() {
        return iPersonneMorale;
    }

    public void setiPersonneMorale(IPersonneMorale iPersonneMorale) {
        this.iPersonneMorale = iPersonneMorale;
    }

    public IPersonnePhys getiPersonnePhys() {
        return iPersonnePhys;
    }

    public void setiPersonnePhys(IPersonnePhys iPersonnePhys) {
        this.iPersonnePhys = iPersonnePhys;
    }

    public IPersonne getiPersonne() {
        return iPersonne;
    }

    public void setiPersonne(IPersonne iPersonne) {
        this.iPersonne = iPersonne;
    }
}
