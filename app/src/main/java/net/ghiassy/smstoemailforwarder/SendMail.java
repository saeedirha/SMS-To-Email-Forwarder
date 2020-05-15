package net.ghiassy.smstoemailforwarder;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 */

//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail extends AsyncTask<Void,Void,Void> {
    public static final String TAG = "SendMail";

    //Declaring Variables
    private Session session;
    private String subject;
    private String message;


    private boolean isTTLS, isAuth, isSelfSign, isSSL;

    Properties props;
    UserInfo userInfo;

    //Class Constructor
    public SendMail(UserInfo userInfo,
                    boolean isTTLS, boolean isAuth,
                    boolean isSSL, boolean isSelfSign,
                    String subject, String message)
    {
        this.userInfo = userInfo;

        this.isTTLS = isTTLS;
        this.isAuth = isAuth;
        this.isSSL = isSSL;
        this.isSelfSign = isSelfSign;
        this.subject = subject;
        this.message = message;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        //Showing progress dialog while sending email
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.i("Sending Email.....", "Email Sent!");
    }

    private void setProperties()
    {
        props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", userInfo.getSMTPServer());
        props.put("mail.smtp.port", String.valueOf(userInfo.getPort()));

        if(isTTLS)
        {
            props.put("mail.smtp.starttls.enable", "true");
        }
        if(isAuth)
        {
            props.put("mail.smtp.auth", "true");
        }
        if(isSSL)
        {
            props.put("mail.smtp.socketFactory.port", String.valueOf(userInfo.getPort()));
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.ssl.enable", "true");
        }
        if(isSelfSign)
        {
            props.put("mail.smtp.ssl.trust", "*");
        }

    }

    @Override
    protected Void doInBackground(Void... params) {

        //Creating properties
        setProperties();

        //Creating a new session
        session = null;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        //Log.i(TAG + "++Session++++" , userInfo.getUsername());
                        return new PasswordAuthentication(userInfo.getUsername(), userInfo.getPassword());
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(userInfo.getUsername()));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(userInfo.getReceiverEmail()));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);

            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
