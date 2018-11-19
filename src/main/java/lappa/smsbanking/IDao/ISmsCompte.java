/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.SmsCompte;

/**
 *
 * @author lappa
 */
public interface ISmsCompte extends IDao<SmsCompte, Long> {

    public SmsCompte findByIdS(Long id) throws DataAccessException;

    public SmsCompte findByMobile(String mobile) throws DataAccessException;
    
    public SmsCompte findByTypeCompte(String typeCompte) throws DataAccessException;
    
     public SmsCompte findByIdClient(String idClient) throws DataAccessException;
     
     public SmsCompte findByPin(int pin) throws DataAccessException;
}
