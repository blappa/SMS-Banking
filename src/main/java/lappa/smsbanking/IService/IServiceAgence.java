/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Agence;

/**
 *
 * @author lappa
 */
public interface IServiceAgence {

     public Agence findByIdA(Long id) throws DataAccessException;
    
     public Agence findByNomAgence(String nomAgence) throws DataAccessException;
    
     public Agence findByCodeAgence(String codeAgence) throws DataAccessException;
     
    public void save(Agence c) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(Agence c) throws DataAccessException;

    public List<Agence> findAll() throws DataAccessException;

    public Agence findById(Long id) throws DataAccessException;
}
