/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Chequier;

/**
 *
 * @author lappa
 */
public interface IServiceChequier {

    public Chequier findByIdC(Long id) throws DataAccessException;

    public Chequier findByNumReference(String numReference) throws DataAccessException;

    public void save(Chequier c, Long idu) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(Chequier c) throws DataAccessException;

    public List<Chequier> findAll() throws DataAccessException;

    public Chequier findById(Long id) throws DataAccessException;
}
