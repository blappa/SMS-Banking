/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.Entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "Autorisation")
@NamedQueries({
    @NamedQuery(name = "Autorisation.findAll", query = "SELECT a FROM Autorisation a"),
     @NamedQuery(name = "Autorisation.findByIdLogin", query = "SELECT p FROM Autorisation p WHERE p.idLogin = :idLogin"),
    @NamedQuery(name = "Autorisation.findByLogin", query = "SELECT a FROM Autorisation a WHERE a.username = :Login"),
    @NamedQuery(name = "Autorisation.findByPwd", query = "SELECT a FROM Autorisation a WHERE a.password = :pwd")})
public class Autorisation implements Serializable {
     private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "idLogin")
    private Long idLogin;
  
    @Column(name = "USERNAME",unique = true)
    private String username;
    
    @Column(name = "PASSWORD",unique = true)
    private String password;
    
    @Column(name = "AUTHORITY")
    private String authority;
    
    @Column(name = "ENABLED")
    private Boolean anabled;
   
    @JoinColumn(nullable = true)
    @OneToOne
    private Personne personne;
   

     public Autorisation() {
    }

    public Autorisation(Personne p, String password, String username, String authority, Boolean  anabled){
        this.personne=p;
        this.username = username;
        this.password = password;
        this.authority=authority;
        this.anabled=anabled;
    }

    public Autorisation(String username, String password, String authority, Boolean  anabled) {
        this.username = username;
        this.password = password;
        this.authority=authority;
        this.anabled=anabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonnel(Personne personne) {
        this.personne= personne;
    }

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Boolean getAnabled() {
        return anabled;
    }

    public void setAnabled(Boolean anabled) {
        this.anabled = anabled;
    }


    @Override
    public String toString() {
        return "Autorisation{" + "Login=" + username + ", pwd=" + password + " idLogin=" + idLogin + '}';
    }
  
}
