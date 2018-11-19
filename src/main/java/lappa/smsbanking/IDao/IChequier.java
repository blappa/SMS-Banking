/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Chequier;

/**
 *
 * @author lappa
 */
public interface IChequier extends IDao<Chequier, Long>{
   
     public Chequier findByIdC(Long id) throws DataAccessException;
    
     public Chequier findByNumReference(String numReference) throws DataAccessException;
    
}
