/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.App;

/**
 *
 * @author lappa
 */
public class Send {

    public static void main(String[] args) {

        Runtime r = Runtime.getRuntime();
        Process p = null;
        String cmd[] = {"/home/lappa/NetBeansProjects/spring_security/b/SMS-BANKING/src/main/webapp/scriptShell/send.sh", "argument1"};

        try {
            p = r.exec(cmd);
            System.out.println(r.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
