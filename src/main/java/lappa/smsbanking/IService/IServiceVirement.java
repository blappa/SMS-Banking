/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Virement;

/**
 *
 * @author lappa
 */
public interface IServiceVirement {

    public void save(Virement u, Long idC) throws DataAccessException;
    
    public void save(Virement u) throws DataAccessException ;

    public void delete(Long id) throws DataAccessException;

    public void update(Virement u) throws DataAccessException;

    public List<Virement> findAll() throws DataAccessException;

    public Virement findByNumCompte(String num) throws DataAccessException;

    public Virement findByCodeDocument(String code) throws DataAccessException;
    
    public Virement findByCodeFichier(String codeF) throws DataAccessException;

    public Virement findByIdV(Long id) throws DataAccessException;

    public Virement findByTypeDocument(String type) throws DataAccessException;
}
