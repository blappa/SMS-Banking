/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.PersonneMorale;

/**
 *
 * @author lappa
 */
public interface IServicePersonneMoral {

    public void save(PersonneMorale u, Long idp) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(PersonneMorale u) throws DataAccessException;

    public List<PersonneMorale> findAll() throws DataAccessException;

    public PersonneMorale findById(Long id) throws DataAccessException;

    public PersonneMorale findByNomEntreprise(String nom) throws DataAccessException;
     
     public PersonneMorale findBySecteurActivite(String secteurActivite) throws DataAccessException;
      
     public PersonneMorale findByNumEnregistrement(String numEnregistrement) throws DataAccessException;
     
     public PersonneMorale findByIdP(Long id) throws DataAccessException;
}
