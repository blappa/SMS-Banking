/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.web.Beans;

import java.util.*;
import java.util.List;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Service;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.AGateway.Protocols;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.modem.SerialModemGateway;
 
public class ReceiveSMS
{
    public SerialModemGateway gateway=null;
    public List<InboundMessage> AllMessageList=null;
    private List<InboundMessage> msgList=null;
    Service srv;
 
    public List<InboundMessage> getMsgList() {
        return msgList; 
    }
 
    public void setMsgList(List<InboundMessage> msgList) {
        this.msgList = msgList;
    }
 
    public List<InboundMessage> getAllMessageList() {
        return AllMessageList;
    }
 
    public ReceiveSMS(){
        this.gateway=new SerialModemGateway("COM8","COM8",115200,"","");
        this.gateway.setSimPin("0000");
    }
 
    public ReceiveSMS(String portName,String cpin){
        this.gateway=new SerialModemGateway(portName,portName,115200,"not set","not set");
        this.gateway.setSimPin(cpin);
    }
 
    public List doIt(String messageClass) throws Exception
    {
        List expediteurList = null;
        // Define a list which will hold the read messages.
        InboundNotification inboundNotification = new InboundNotification();
        // Create the notification callback method for inbound voice calls.
        CallNotification callNotification = new CallNotification();
        //Create the notification callback method for gateway statuses.
        GatewayStatusNotification statusNotification = new GatewayStatusNotification();
        OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
        try
        {
            System.out.println("Read messages from a serial gsm modem.");
//            this.srv = new Service();
            // Create the Gateway representing the serial GSM modem.
            //SerialModemGateway gateway = new SerialModemGateway("modem.com1", "COM1", 57600, "Nokia", "");
            // Set the modem protocol to PDU (alternative is TEXT). PDU is the default, anyway...
            gateway.setProtocol(Protocols.PDU);
            // Do we want the Gateway to be used for Inbound messages?
            gateway.setInbound(true);
            // Do we want the Gateway to be used for Outbound messages?
            gateway.setOutbound(true);
            // Let SMSLib know which is the SIM PIN.
            //gateway.setSimPin("0000");
            // Set up the notification methods.
            this.srv.setInboundMessageNotification(inboundNotification);
            this.srv.setCallNotification(callNotification);
            this.srv.setGatewayStatusNotification(statusNotification);
            this.srv.setOrphanedMessageNotification(orphanedMessageNotification);
            // Add the Gateway to the Service object.
            this.srv.addGateway(gateway);
            // Similarly, you may define as many Gateway objects, representing
            // various GSM modems, add them in the Service object and control all of them.
            // Start! (i.e. connect to all defined Gateways)
            this.srv.startService();
            // Printout some general information about the modem.
            System.out.println();
            System.out.println("Modem Information:");
            System.out.println("  Manufacturer: " + gateway.getManufacturer());
            System.out.println("  Model: " + gateway.getModel());
            System.out.println("  Serial No: " + gateway.getSerialNo());
            System.out.println("  Code pin: " + gateway.getSimPin());
            System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
            System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
            System.out.println("  GatewayId: " + gateway.getGatewayId() + "%");
            System.out.println();
            if (messageClass.contentEquals("ALL")){
                AllMessageList=new ArrayList<InboundMessage>();
                this.srv.readMessages( AllMessageList, MessageClasses.ALL);
                for (InboundMessage msg : AllMessageList){
                    //hash.put(msg.getMemIndex(),msg);
                    System.out.println(msg);
                    /*expediteur = msg.getOriginator();
                    if ((expediteur.length() == 11) ){
                        listExpediteur.add(expediteur.substring(3));
                    }*/
                    //srv.deleteMessage(msg);
                    //System.out.println("Message supprimé!");
                }
           }
       }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            }
        return expediteurList;
    }
 
    public class InboundNotification implements IInboundMessageNotification
    {
        @Override
        public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg) {
//            AGateway gatewayId=gateway;
//        if (msgType == MessageTypes.INBOUND)
//                System.out.println(">>> New Inbound message detected from Gateway: " + gatewayId);
//            else if (msgType == MessageTypes.STATUSREPORT)
//                System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gatewayId);
//            System.out.println(msg);
        }
    }
 
    public class CallNotification implements ICallNotification
    {
        @Override
        public void process(AGateway gateway, String callerId) {
            AGateway gatewayId=gateway;
            System.out.println(">>> New call detected from Gateway: " + gatewayId + " : " + callerId);
        }
    }
 
    public class GatewayStatusNotification implements IGatewayStatusNotification
    {
        @Override
        public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus) {
                AGateway gatewayId=gateway;
          System.out.println(">>> Gateway Status change for " + gatewayId + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }
 
    public class OrphanedMessageNotification implements IOrphanedMessageNotification
    {
        @Override
        public boolean process(AGateway gateway, InboundMessage msg) {
            AGateway gatewayId=gateway;
          System.out.println(">>> Orphaned message part detected from " + gatewayId);
            System.out.println(msg);
            // Since we are just testing, return FALSE and keep the orphaned message part.
            return false;
        }
    }
}