/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Operation;
import lappa.smsbanking.IDao.IOperation;

/**
 *
 * @author lappa
 */
public class IOperationImpl extends GenericDao<Operation, Long> implements IOperation {

    @Override
    public Operation findByIdO(Long id) throws DataAccessException {
        return (Operation) getManager().createQuery("SELECT o FROM Operation o WHERE o.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public Operation findByNomOperation(String nomOperation) throws DataAccessException {
        return (Operation) getManager().createQuery("SELECT o FROM Operation o WHERE o.nomOperation = :nomOperation")
                .setParameter("nomOperation", nomOperation).getSingleResult();
    }
}
