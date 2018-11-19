/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.PersonnePhysique;

/**
 *
 * @author lappa
 */
public interface IServicePersonnePhys {

    public void save(PersonnePhysique u, Long idp) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(PersonnePhysique u) throws DataAccessException;

    public List<PersonnePhysique> findAll() throws DataAccessException;

    public PersonnePhysique findById(Long id) throws DataAccessException;

    public PersonnePhysique findByNom(String nom) throws DataAccessException;

    public PersonnePhysique findByCni(String cni) throws DataAccessException;

    public PersonnePhysique findByIdP(Long id) throws DataAccessException;
}
