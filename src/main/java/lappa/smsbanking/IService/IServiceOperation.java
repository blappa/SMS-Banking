/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Operation;

/**
 *
 * @author lappa
 */
public interface IServiceOperation {

    public void save(Operation o) throws DataAccessException;

    public void save(Operation o, Long id1, Long id2, Long idu) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(Operation o) throws DataAccessException;

    public List<Operation> findAll() throws DataAccessException;

    public Operation findById(Long id) throws DataAccessException;

    public Operation findByNomOperation(String nomOperation) throws DataAccessException;
}
