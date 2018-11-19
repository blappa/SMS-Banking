/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Operation;

/**
 *
 * @author lappa
 */
public interface IOperation extends IDao<Operation, Long> {

    public Operation findByIdO(Long id) throws DataAccessException;

    public Operation findByNomOperation(String nomOperation) throws DataAccessException;
}
