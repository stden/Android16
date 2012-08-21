package com.example;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MyActivity extends Activity {
    private TextView text1;
    private Button button;
    private TextView t1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        String app_name = getAppID();

        String Id = getAndroidID();

        String Id2 = getDeviceID();

        text1 = (TextView) findViewById(R.id.text1);

        String sv = getSoftwareVersion();

        text1.setText(app_name + ' ' + Id + ' ' + Id2 + ' ' + sv);

        button = (Button) findViewById(R.id.sumButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i1 = inputInt(R.id.edit1);
                int i2 = inputInt(R.id.edit2);

                TextView res = (TextView) findViewById(R.id.res);
                res.setText("" + (i1 + i2));
            }
        });

        RegisterOnSite();
    }

    /**
     * можно использовать как имя приложения
     *
     * @return
     */
    private String getAppID() {
        return getApplicationContext().getPackageName();
    }

    /**
     * Returns the unique device ID, for example, the IMEI for GSM and the MEID or ESN for CDMA phones.
     * Return null if device ID is not available.
     *
     * @return
     */
    public String getDeviceID() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private void RegisterOnSite() {
        String urlsite = "http://stden.myjino.ru/phone_id/2/app_id/5";
        t1 = (TextView) findViewById(R.id.from_site);
        try {
            URL url = new URL(urlsite);
            URLConnection conn = url.openConnection();
            InputStreamReader rd = new InputStreamReader(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(rd);
            String read = br.readLine();

            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }

            t1.setText(sb.toString());

        } catch (Exception e) {
            t1.setText(e.getMessage());
        }
    }

    private int inputInt(int interface_id) {
        EditText editText = (EditText) findViewById(interface_id);
        return Integer.parseInt(editText.getText().toString());
    }

    /**
     * уникальный идентификатор. хотя может потребоваться и какой нибудь
     *
     * @return
     */
    private String getAndroidID() {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private String getSoftwareVersion() {
        PackageInfo pi;
        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (final PackageManager.NameNotFoundException e) {
            return "na";
        }
    }


}
