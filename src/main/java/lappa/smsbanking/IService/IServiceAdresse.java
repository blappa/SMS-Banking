/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Adresse;
import lappa.smsbanking.Entities.Personne;

/**
 *
 * @author lappa
 */
public interface IServiceAdresse {

    public Adresse findByIdA(Long id) throws DataAccessException;
    
    public Adresse findByTypeAdresse(String typeAdresse) throws DataAccessException;

    public Personne findByIdClient(String idClient) throws DataAccessException;
     
    public void save(Adresse c, Long idu) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(Adresse c) throws DataAccessException;

    public List<Adresse> findAll() throws DataAccessException;

    public Adresse findById(Long id) throws DataAccessException;
}
