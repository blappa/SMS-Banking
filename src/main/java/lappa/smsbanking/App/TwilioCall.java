/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.App;

import com.twilio.sdk.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lappa
 */
public class TwilioCall {
    /* Twilio REST API version */

    public static final String APIVERSION = "2010-04-01";

    public static void main(String[] args) {
        /* Twilio AccountSid and AuthToken */
        String AccountSid = "ACca9020ef95fb01af84f254f31f763721";
        String AuthToken = "1d9852d6acca8b0b55f932fa8b1ae5c9";
        
        /* Outgoing Caller ID previously validated with Twilio */
        String CallerID = "+23773246159";
        String ToCall = "+17409696745";
        String Url = "http://twimlets.com/message?Message%5B0%5D=Hello%20from%20my%20java%20application.&Message%5B1%5D=http%3A%2F%2Fcom.twilio.music.electronica.s3.amazonaws.com%2Fteru_-_110_Downtempo_Electronic_4.mp3";
        /* Instantiate a new Twilio Rest Client */
        TwilioRestClient client = new TwilioRestClient(AccountSid, AuthToken, null);
      //build map of post parameters
        Map params = new HashMap();
        params.put("From", CallerID);
        params.put("To", ToCall);
        params.put("Url", Url);
        TwilioRestResponse response;
        try {
            response = client.request("/" + APIVERSION + "/Accounts/" + client.getAccountSid() + "/Calls", "POST", params);
            if (response.isError()) {
                System.out.println("Error making outgoing call: " + response.getHttpStatus() + "n" + response.getResponseText());
            } else {
                System.out.println(response.getResponseText());
            }
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }
}