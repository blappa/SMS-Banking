/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.CompteParent;

/**
 *
 * @author lappa
 */
public interface ICompteParent extends IDao<CompteParent, Long> {

    public CompteParent findById(Long id) throws DataAccessException;

    public CompteParent findByIdClient(String idClient) throws DataAccessException;
     
}
