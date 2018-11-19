/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Personne;

/**
 *
 * @author lappa
 */
public interface IPersonne extends IDao<Personne, Long> {
    
     public Personne findByNom(String nom) throws DataAccessException;
     
     public Personne findByCode(String code) throws DataAccessException;
             
     public Personne findByIdP(Long id) throws DataAccessException;
     
     public Personne findByIdClient(String idClient) throws DataAccessException ;
     
     public Personne findByNumeroCompte(String numeroCompte) throws DataAccessException;
     
}
