/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.App;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.AccountFactory;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.factory.OutgoingCallerIdFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.CallerIdValidation;
import com.twilio.sdk.resource.list.OutgoingCallerIdList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author lappa
 */
public class VerificationCode {
// Find your Account Sid and Token at twilio.com/user/account

    public static final String ACCOUNT_SID = "ACca9020ef95fb01af84f254f31f763721";
    public static final String AUTH_TOKEN = "1d9852d6acca8b0b55f932fa8b1ae5c9";

    public static void main(String[] args) throws TwilioRestException {
//        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
//// Build a filter for the OutgoingCallerIdList
//        Map<String, String> params = new HashMap<String, String>();
////        params.put("FriendlyName", "cl2");
//        params.put("PhoneNumber", "+23773984086");
//        
////        List<NameValuePair> params = new ArrayList<NameValuePair>();
////        params.add(new BasicNameValuePair("FriendlyName", "cl2"));
////        params.add(new BasicNameValuePair("PhoneNumber", "+23773984086"));
////        AccountFactory accountFactory = client.getAccountFactory();
////        Account account = accountFactory.create(params);
//        
//        OutgoingCallerIdFactory callerIdFactory = client.getAccount().getOutgoingCallerIdFactory();
//        CallerIdValidation validationAttempt = callerIdFactory.create(params);
//        System.out.println(validationAttempt.getValidationCode());
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account mainAccount = client.getAccount();
        CallFactory callFactory = mainAccount.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("PhoneNumber", "+23770132019"); // Replace with your phone number
//        callParams.put("From", "+17409696745"); // Replace with a Twilio number
        callParams.put("Url", "http://demo.twilio.com/welcome/voice/");
        // Make the text
        //        
        OutgoingCallerIdFactory callerIdFactory = client.getAccount().getOutgoingCallerIdFactory();
        CallerIdValidation validationAttempt = callerIdFactory.create(callParams);
        System.out.println(validationAttempt.getValidationCode());
        // Print the call SID (a 32 digit hex like CA123..)
//        System.out.println(call.getSid());
    }
}