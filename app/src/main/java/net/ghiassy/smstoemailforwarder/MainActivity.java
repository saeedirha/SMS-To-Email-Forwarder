package net.ghiassy.smstoemailforwarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("net.ghiassy.smstoemailforwarder", Context.MODE_PRIVATE);

        new CountDownTimer(3000, 2000)
        {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);

                if(sharedPreferences.getBoolean("firstRun", true))
                {
                    //Toast.makeText(MainActivity.this, "First Time", Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().putBoolean("firstRun" , false).apply();
                    intent.putExtra("LoadSettings", false);

                }else
                {
                    //Toast.makeText(MainActivity.this, "Load Settings", Toast.LENGTH_SHORT).show();
                    intent.putExtra("LoadSettings", true);

                }
                startActivity(intent);
                finish();

            }
        }.start();
    }
}
