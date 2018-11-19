/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.PersonnePhysique;

/**
 *
 * @author lappa
 */
public interface IPersonnePhys extends IDao<PersonnePhysique, Long> {
    
     public PersonnePhysique findByNom(String nom) throws DataAccessException;
     
     public PersonnePhysique findByCni(String cni) throws DataAccessException;
             
     public PersonnePhysique findByIdP(Long id) throws DataAccessException;
     
}
