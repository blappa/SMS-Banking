/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.PersonneMorale;

/**
 *
 * @author lappa
 */
public interface IPersonneMorale extends IDao<PersonneMorale, Long> {
    
     public PersonneMorale findByNomEntreprise(String nom) throws DataAccessException;
     
     public PersonneMorale findBySecteurActivite(String secteurActivite) throws DataAccessException;
      
     public PersonneMorale findByNumEnregistrement(String numEnregistrement) throws DataAccessException;
     
     public PersonneMorale findByIdP(Long id) throws DataAccessException;
     
}
