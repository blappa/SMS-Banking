/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IServiceSupport;

import com.douwe.generic.dao.DataAccessException;
import lappa.smsbanking.Entities.Autorisation;
import lappa.smsbanking.Entities.Personne;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author lappa
 */
public interface IServiceAutorisation{

    public Personne enregistrerNouveauPersonnel(Personne p) throws DataAccessException, NoResultException;

    public void enregistrerNouveauLogin(Autorisation au) throws DataAccessException, NoResultException;

    public void annulerEnregistrementPersonnel(Personne p) throws DataAccessException;

    public Personne getPersonnelById(Long id) throws DataAccessException;

    public void annulerEnregistrementLogin(Autorisation au) throws DataAccessException;

    public boolean identifierLogin(String login) throws DataAccessException;
    
    public Autorisation getAutorisationByLogin(String login) throws DataAccessException;
            
    public Autorisation update(Autorisation p) throws DataAccessException;
    
    public List<Personne> getAllPersonnel() throws DataAccessException;
    
    public List<Autorisation> findAll() throws DataAccessException ;
    
    public Autorisation findByLogin(String login) throws DataAccessException ;
            
     public Autorisation getAutorisationByPwd(String pwd) throws DataAccessException;
    
    
   
}
