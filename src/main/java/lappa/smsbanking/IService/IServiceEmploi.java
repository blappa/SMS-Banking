/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Emploi;

/**
 *
 * @author lappa
 */
public interface IServiceEmploi {
    
    
     public Emploi findByIdE(Long id) throws DataAccessException;
    
     public Emploi findByTypeEmploi(String typeEmploi) throws DataAccessException;
     
     public Emploi findByProfession(String profession) throws DataAccessException;

    public void save(Emploi c, Long idu) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(Emploi c) throws DataAccessException;

    public List<Emploi> findAll() throws DataAccessException;

    public Emploi findById(Long id) throws DataAccessException;
}
