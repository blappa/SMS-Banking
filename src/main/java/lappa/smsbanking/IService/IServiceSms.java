/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Sms;
import lappa.smsbanking.Entities.Support;

/**
 *
 * @author lappa
 */
public interface IServiceSms {
    
    public void save(Sms s) throws DataAccessException;
            
    public void save(Sms s, Long idu, Long ido) throws DataAccessException;
    
    public void saveSupport(Support s) throws DataAccessException ;

    public void delete(Long id) throws DataAccessException;

    public void update(Sms s) throws DataAccessException;

    public List<Sms> findAll() throws DataAccessException;

    public Sms findByIdS(Long id) throws DataAccessException;
    
     public void updateSupport(Support s) throws DataAccessException ;
     
     public Support findByIdSup(Long id) throws DataAccessException ;
     
     public List<Support> findAllS() throws DataAccessException;
}
