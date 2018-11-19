/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.App;

/**
 *
 * @author lappa
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class Test {
 
    public static String ENVOYERSMSPRO_LOGIN    =   "";
    public static String ENVOYERSMSPRO_PASSWORD =   "";
    public static String ENVOYERSMSPRO_HOST     =   "www.envoyersmspro.com";
    public static String ENVOYERSMSPRO_PROTOCOL =   "https";
     
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
             
            HttpClient httpClient = new DefaultHttpClient();
             
            HttpPost request = new HttpPost(ENVOYERSMSPRO_PROTOCOL+"://"+ENVOYERSMSPRO_HOST+"/api/message/send");
             
            String identifiant= ENVOYERSMSPRO_LOGIN + ":" + ENVOYERSMSPRO_PASSWORD;
            String encoded = Base64.encodeBase64String(identifiant.getBytes());
             
            request.addHeader("Content-Type","application/x-www-form-urlencoded");
            request.addHeader("charset","utf-8");
            request.addHeader("Authorization","Basic "+encoded);
             
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("text","Nouveau message via l'API d'Envoyer SMS Pro depuis un script JAVA"));
            nameValuePairs.add(new BasicNameValuePair("recipients","+23773246159"));
            nameValuePairs.add(new BasicNameValuePair("sendername","Societe"));
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
             
            HttpResponse response = httpClient.execute(request);
             
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder    = dbFactory.newDocumentBuilder();
 
            Document doc = dBuilder.parse(response.getEntity().getContent());
                 
            doc.getDocumentElement().normalize();
             
            if(doc.getElementsByTagName("status").item(0).getTextContent().equals("success"))
            {
                System.out.println("Message envoyé");
                System.out.println("Votre messageid : "+doc.getElementsByTagName("message_id").item(0).getTextContent());
                System.out.println("Nombre de SMS envoyé : "+doc.getElementsByTagName("sms_sent").item(0).getTextContent());
                System.out.println("Nombre de SMS restant : "+doc.getElementsByTagName("sms_remaining").item(0).getTextContent());
            }
            else
            {
                System.out.println("Le message n'a pas été envoyé :");
                NodeList nList=doc.getElementsByTagName("error").item(0).getChildNodes();
                for (int i = 0; i < nList.getLength(); i++)
                {
                    System.out.println(nList.item(i).getNodeName()+" : "+nList.item(i).getTextContent());
                }
            }
            httpClient.getConnectionManager().shutdown();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
}
