/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.OffreSpeciale;

/**
 *
 * @author lappa
 */
public interface IServiceOffreSpeciale {

    public OffreSpeciale findByIdO(Long id) throws DataAccessException;

    public void save(OffreSpeciale o, String idu) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(OffreSpeciale c) throws DataAccessException;

    public List<OffreSpeciale> findAll() throws DataAccessException;

    public OffreSpeciale findById(Long id) throws DataAccessException;
}
