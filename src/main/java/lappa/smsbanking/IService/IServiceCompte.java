/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Compte;

/**
 *
 * @author lappa
 */
public interface IServiceCompte {

    public Compte findByNumeroCompte(String numeroCompte) throws DataAccessException;

    public void save(Compte c, Long idu) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(Compte c) throws DataAccessException;

    public List<Compte> findAll() throws DataAccessException;

    public Compte findById(Long id) throws DataAccessException;
}
