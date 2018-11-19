/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Personne;

/**
 *
 * @author lappa
 */
public interface IServicePersonne {

    public void save(Personne u) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(Personne u) throws DataAccessException;

    public List<Personne> findAll() throws DataAccessException;

    public Personne findById(Long id) throws DataAccessException;

    public Personne findByNom(String nom) throws DataAccessException;

    public Personne findByPays(String pays) throws DataAccessException;

    public Personne findByVille(String ville) throws DataAccessException;

    public Personne findByCode(String code) throws DataAccessException;

    public Personne findByIdClient(String idClient) throws DataAccessException;

    public Personne findByNumeroCompte(String numeroCompte) throws DataAccessException;
}
