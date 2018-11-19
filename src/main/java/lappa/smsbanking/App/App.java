package lappa.smsbanking.App;

import java.util.List;
import lappa.smsbanking.web.Beans.ReceiveSMS;
import org.apache.log4j.Logger;
/**
 * Hello world!
 *
 */
class App{

    public static void main(String[] args)  {
       
        ReceiveSMS re=new ReceiveSMS();
        try {
            List expediteurList=re.doIt("ALL");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
