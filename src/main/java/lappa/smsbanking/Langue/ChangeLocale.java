/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.Langue;

/**
 *
 * @author lappa
 */
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ChangeLocale implements Serializable {

// la locale des pages
    private String locale = "fr";

    public ChangeLocale() {
    }

    public String setFrenchLocale() {
        locale = "fr";
        return null;
    }

    public String setEnglishLocale() {
        locale = "en";
        return null;
    }

    public String getLocale() {
        return locale;
    }
}
