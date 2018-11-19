/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.CompteParent;

/**
 *
 * @author lappa
 */
public interface IServiceCompteParent {
    
    public void save(CompteParent s) throws DataAccessException;
            
    public void delete(Long id) throws DataAccessException;

    public void update(CompteParent s) throws DataAccessException;

    public List<CompteParent> findAll() throws DataAccessException;
    
    public CompteParent findById(Long id) throws DataAccessException;
    
    public CompteParent findByIdClient(String idClient) throws DataAccessException;

}
