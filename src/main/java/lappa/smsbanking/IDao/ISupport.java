/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Support;

/**
 *
 * @author lappa
 */
public interface ISupport  extends IDao<Support, Long>{
    
    public Support findByIdC(Long id) throws DataAccessException;
   
}
