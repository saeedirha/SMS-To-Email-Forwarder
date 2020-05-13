package net.ghiassy.smstoemailforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecieveSms extends BroadcastReceiver {

    public static final String TAG = "ReceiveSMS";

    private static MessageListener mListener;
    public static final String EmailAddress="";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "SMS received", Toast.LENGTH_LONG).show();
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle  = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if(bundle != null)
            {
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i< msgs.length; i++)
                    {
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msg_body  = msgs[i].getMessageBody();
                        //Toast.makeText(context, "From " + msg_from + " Content: " + msg_body, Toast.LENGTH_LONG).show();

                        Log.i("SMS Received: " ,"From " + msg_from + " Content: " + msg_body );

                        if(EmailAddress.isEmpty() || EmailAddress.equals(""))
                        {
                            Toast.makeText(context, "Email Address Cannot be empty!", Toast.LENGTH_LONG).show();
                        }

                        SimpleDateFormat formatter = new SimpleDateFormat("E dd/MM/yyyy HH:mm:ss");
                        String strDate= formatter.format(new Date());

                        SendMail mailer = new SendMail(EmailAddress, "Date: " + strDate +" From: " + msg_from ,  msg_body);
                        mailer.execute();

                        mListener.messageReceived(msg_body, msg_from);

                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bindListener(MessageListener listener){
        mListener = listener;
    }

}