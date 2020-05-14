package net.ghiassy.smstoemailforwarder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SettingsActivity extends AppCompatActivity {

    public static final String TAG = "SettingsActivity";

    AdView ads1;
    TextView txtViewSMTP , txtViewUsername, txtViewPassword, txtViewReceiverEmail;
    String SMTPAddress, Username, Password, ReceiverEmail;

    int Port;
    boolean isTTLS, isAuth, isSelfSign;


    public void btnSendClick(View view)
    {
        Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();

    }
    public void btnSaveSettingsClick(View view)
    {
        Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
    }
    public void btnStartMonitoringClick(View view)
    {
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();

    }
    public void btnStopMonitoringClick(View view)
    {
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();

    }
    public void btnHideWindowClick(View view)
    {
        Toast.makeText(this, "Hide", Toast.LENGTH_SHORT).show();

    }

    private void LoadSettings()
    {

    }
    private void ErrorCheck()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txtViewSMTP = findViewById(R.id.txtSMTPAddress);
        txtViewPassword = findViewById(R.id.txtPassword);
        txtViewUsername = findViewById(R.id.txtUsername);
        txtViewReceiverEmail = findViewById(R.id.txtReceiverEmail);

        if(getIntent().getBooleanExtra("LoadSettings", false))
        {
            Toast.makeText(this, "Load Settings", Toast.LENGTH_SHORT).show();
            LoadSettings();
        }

        ads1 = findViewById(R.id.adView);
        MobileAds.initialize(this, "ca-app-pub-8053134103811321/8990930675");
        AdRequest adRequest = new AdRequest.Builder().build();
        ads1.loadAd(adRequest);

    }
}
