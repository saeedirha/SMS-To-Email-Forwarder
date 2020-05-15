package net.ghiassy.smstoemailforwarder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SettingsActivity extends AppCompatActivity {

    public static final String TAG = "SettingsActivity";

    AdView ads1;
    SendMail sendMail;
    TextView txtViewSMTP, txtViewPort , txtViewUsername, txtViewPassword, txtViewReceiverEmail;
    CheckBox isTTLS, isAuth, isSelfSign, isSSL;

    SharedPreferences sharedPreferences;
    UserInfo userInfo;



    public void btnSendClick(View view)
    {
        closeKeyboard();
        if(hasError())
        {return;}

        Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
        int port = Integer.parseInt(txtViewPort.getText().toString());
        //setting new variables
        userInfo = new UserInfo(txtViewSMTP.getText().toString(), port,
                txtViewUsername.getText().toString(),
                txtViewPassword.getText().toString(),
                txtViewReceiverEmail.getText().toString());

        //calling Send Email class
        sendMail = new SendMail(userInfo,
                isTTLS.isChecked(), isAuth.isChecked(),isSSL.isChecked(),isSelfSign.isChecked(),
                "Testing from Forwarder App",
                "Testing Email!");
        sendMail.execute();


    }
    public void btnSaveSettingsClick(View view)
    {
        closeKeyboard();

        if(hasError())
        {return;}

        int port = Integer.parseInt(txtViewPort.getText().toString());
        //setting new variables
        userInfo = new UserInfo(txtViewSMTP.getText().toString(), port,
                txtViewUsername.getText().toString(),
                txtViewPassword.getText().toString(),
                txtViewReceiverEmail.getText().toString());


        sharedPreferences.edit().putString("SMTP" , userInfo.getSMTPServer()).apply();
        sharedPreferences.edit().putInt("Port" , port).apply();
        sharedPreferences.edit().putString("Username" , userInfo.getUsername()).apply();
        sharedPreferences.edit().putString("Password" , userInfo.getPassword()).apply();
        sharedPreferences.edit().putString("ReceiverEmail" , userInfo.getReceiverEmail()).apply();

        sharedPreferences.edit().putBoolean("isTTLS", isTTLS.isChecked()).apply();
        sharedPreferences.edit().putBoolean("isAuth", isAuth.isChecked()).apply();
        sharedPreferences.edit().putBoolean("isSSL", isSSL.isChecked()).apply();
        sharedPreferences.edit().putBoolean("isSelfSign", isSelfSign.isChecked()).apply();

        Toast.makeText(this, "Information Saved.", Toast.LENGTH_SHORT).show();

    }
    public void btnStartMonitoringClick(View view)
    {
        closeKeyboard();
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


    public void checkBoxSSLActivated(View view)
    {
        if(isSSL.isChecked())
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Use SSL")
                    .setMessage("For self signed certificate, please activate it.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    //load variables from the Shared preferences
    private void LoadSettings()
    {
        txtViewSMTP.setText(sharedPreferences.getString("SMTP", ""));
        txtViewUsername.setText(sharedPreferences.getString("Username", ""));
        txtViewPassword.setText(sharedPreferences.getString("Password", ""));
        txtViewReceiverEmail.setText(sharedPreferences.getString("ReceiverEmail", ""));
        txtViewPort.setText(String.valueOf(sharedPreferences.getInt("Port", 587)));

        isTTLS.setChecked(sharedPreferences.getBoolean("isTTLS", false));
        isAuth.setChecked(sharedPreferences.getBoolean("isAuth", false));
        isSSL.setChecked(sharedPreferences.getBoolean("isSSL", false));
        isSelfSign.setChecked(sharedPreferences.getBoolean("isSelfSign", false));



    }

    //basic error check
    private boolean hasError()
    {
        if(txtViewSMTP.getText().toString().isEmpty())
        {
            Toast.makeText(this, "SMTP Address Cannot be empty!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if( txtViewPort.getText().toString().isEmpty() || Integer.parseInt(txtViewPort.getText().toString()) <= 0)
        {
            Toast.makeText(this, "Invalid port number!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(txtViewUsername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Username Cannot be empty!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(txtViewPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Password Cannot be empty!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(txtViewReceiverEmail.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Email Cannot be empty!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(txtViewReceiverEmail.getText()).matches())
        {
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    //Closing keyboard
    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = this.getSharedPreferences("net.ghiassy.smstoemailforwarder", Context.MODE_PRIVATE);


        txtViewSMTP = findViewById(R.id.txtSMTPAddress);
        txtViewPort = findViewById(R.id.txtPortNumber);
        txtViewPassword = findViewById(R.id.txtPassword);
        txtViewUsername = findViewById(R.id.txtUsername);
        txtViewReceiverEmail = findViewById(R.id.txtReceiverEmail);
        txtViewReceiverEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {closeKeyboard();}
            }
        });

        isSelfSign = findViewById(R.id.ckBoxSelfSing);
        isAuth = findViewById(R.id.ckBoxAuth);
        isTTLS = findViewById(R.id.ckBoxTTLS);
        isSSL = findViewById(R.id.ckBoxSSL);


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
