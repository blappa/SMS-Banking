/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.App;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class Twilio {
// Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC3ad996e21f2d20c2b74785666c4773a3";
    public static final String AUTH_TOKEN = "102bba20c5b3ae32261706bc16623ec4";

    public static void main(String[] args) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
// Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", "lappa twilio marche correctement! merci"));
        params.add(new BasicNameValuePair("To", "+23773751558"));
        params.add(new BasicNameValuePair("From", "+12027598851"));
//        MessageFactory messageFactory = client.getAccount().getMessageFactory();
//        Message message = messageFactory.create(params);
//        System.out.println(message.getSid());
        
    }
}