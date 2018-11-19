/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.SmsCompte;

/**
 *
 * @author lappa
 */
public interface IServiceSmsCompte {
    
    public void save(SmsCompte s) throws DataAccessException;
            
    public void save(SmsCompte s, Long id1, Long id2) throws DataAccessException;

    public void delete(Long id) throws DataAccessException;

    public void update(SmsCompte s) throws DataAccessException;

    public List<SmsCompte> findAll() throws DataAccessException;
    
    public SmsCompte findByIdS(Long id) throws DataAccessException;

    public SmsCompte findByMobile(String mobile) throws DataAccessException;
    
    public SmsCompte findByTypeCompte(String typeCompte) throws DataAccessException;
    
     public SmsCompte findByIdClient(String idClient) throws DataAccessException;
     
      public SmsCompte findByPin(int pint) throws DataAccessException;
}
