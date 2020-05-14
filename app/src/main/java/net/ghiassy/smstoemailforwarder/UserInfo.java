package net.ghiassy.smstoemailforwarder;

public class UserInfo
{
    public String SMTPServer;
    public String Username;
    public String Password;

    public String ReceiverEmail;
    int Port;

    public UserInfo(String SMTPServer, int port,
                    String username, String password, String receiverEmail)
    {
        this.SMTPServer = SMTPServer;
        this.Username = username;
        this.Password = password;
        this.ReceiverEmail = receiverEmail;
        this.Port = port;
    }


    public String getSMTPServer() {
        return SMTPServer;
    }

    public void setSMTPServer(String SMTPServer) {
        this.SMTPServer = SMTPServer;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getReceiverEmail() {
        return ReceiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        ReceiverEmail = receiverEmail;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

}
