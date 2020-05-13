package net.ghiassy.smstoemailforwarder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SettingsActivity extends AppCompatActivity {

    AdView ads1;

    public void btnSendClick(View view)
    {

    }
    public void btnSaveSettingsClick(View view)
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ads1 = findViewById(R.id.adView);
        MobileAds.initialize(this, "ca-app-pub-8053134103811321/8990930675");
        AdRequest adRequest = new AdRequest.Builder().build();
        ads1.loadAd(adRequest);



    }
}
