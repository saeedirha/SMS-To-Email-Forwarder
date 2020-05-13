package net.ghiassy.smstoemailforwarder;

public interface MessageListener {

    /**
     * To call this method when new message received and send back
     */
    void messageReceived(String smsMessage, String smsSender);

}