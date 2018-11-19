/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Sms;

/**
 *
 * @author lappa
 */
public interface ISms  extends IDao<Sms, Long>{
    
    public Sms findByIdC(Long id) throws DataAccessException;
   
}
