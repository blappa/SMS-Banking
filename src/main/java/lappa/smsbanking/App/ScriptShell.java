/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class ScriptShell {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "scriptSell";
    
    public void readBashScript() {
        try {
           Process proc = Runtime.getRuntime().exec(path + File.separator + "bearerbox.sh /"); //Whatever you want to execute
            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (read.ready()) {
                System.out.println(read.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
//import java.io.IOException;
//import org.apache.commons.exec.CommandLine;
//import org.apache.commons.exec.DefaultExecutor;
//import org.apache.commons.exec.ExecuteException;
//
//public class TestScriptShell {
//    int iExitValue;
//    String sCommandString;
//
//    public void runScript(String command){
//        sCommandString = command;
//        CommandLine oCmdLine = CommandLine.parse(sCommandString);
//        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
//        oDefaultExecutor.setExitValue(0);
//        try {
//            iExitValue = oDefaultExecutor.execute(oCmdLine);
//        } catch (ExecuteException e) {
//            // TODO Auto-generated catch block
//            System.err.println("Execution failed.");
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            System.err.println("permission denied.");
//            e.printStackTrace();
//        }
//    }

    public static void main(String args[]) {
        ScriptShell testScript = new ScriptShell();
        testScript.readBashScript();
    }
    private static final Logger LOG = Logger.getLogger(ScriptShell.class.getName());
}