/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.App;

// Install the Java helper library from twilio.com/docs/java/install
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.AccountFactory;
import com.twilio.sdk.resource.instance.Account;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class TwilioCreateAccount {
// Find your Account Sid and Token at twilio.com/user/account

    public static final String ACCOUNT_SID = "ACca9020ef95fb01af84f254f31f763721";
    public static final String AUTH_TOKEN = "1d9852d6acca8b0b55f932fa8b1ae5c9";

    public static void main(String[] args) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
// Build a filter for the AccountList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("FriendlyName", "Submarine"));
        AccountFactory accountFactory = client.getAccountFactory();
        Account account = accountFactory.create(params);
        System.out.println(account.getSid());
        System.out.println("\n" + account.getAuthToken());
        System.out.println("\n" + account.getFriendlyName());
        System.out.println("\n" + account.getStatus());
        System.out.println("\n" + account.getType());
    }

//    public Account sms() {
//       TwilioRestClient twilio = new TwilioRestClient("Master SID", "Master Auth Token");
//        Account subAccount = twilio.GetAccount("SubAccount SID");
//        TwilioRestClient subAccountClient = new TwilioRestClient(subAccount.Sid, subAccount.AuthToken);
//        Account response = subAccountClient.SendSmsMessage(sender, recipient.ConvertToE164Format(), message);
//        return response.Sid;
//    }
}