package com.ThomasHorgaGmailCom.JustMaBitCnx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coinbase.api.Coinbase;
import com.coinbase.api.CoinbaseBuilder;
import com.coinbase.api.entity.Transaction;
import com.coinbase.api.exception.CoinbaseException;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.joda.money.Money;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Tom-H on 9/17/2016.
 */
public class MyAppCallback extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MyAppCallback Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ThomasHorgaGmailCom.JustMaBitCnx/com.ThomasHorgaGmailCom.JustMaBitCnx/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    // HTTP POST request
    private String sendPost(String token) throws Exception {

        final String USER_AGENT = "Mozilla/5.0";

        String url = "https://api.coinbase.com/oauth/token";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        //con.setRequestProperty("User-Agent", USER_AGENT);
        //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        //String urlParameters = "client_id=856fb5f74b2f139c88d49f44ace25eb67bf26ec0089ad5e2ca6d5b9f96864d22&redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob%0D&response_type=code&scope=wallet%3Auser%3Aread";
        //
        //
        String urlParameters = "grant_type=authorization_code&code="+token+"&client_id=856fb5f74b2f139c88d49f44ace25eb67bf26ec0089ad5e2ca6d5b9f96864d22&client_secret=ec2eb5737e2309fb08d2e498920c928b51be9b561696ed0992b7d3781d2ef861&redirect_uri=https://x-com.ThomasHorgaGmailCom.JustMaBitCnx://oauth.callback$code=code";
        //https://www.coinbase.com/oauth/authorize?client_id=856fb5f74b2f139c88d49f44ace25eb67bf26ec0089ad5e2ca6d5b9f96864d22&redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob%0D&response_type=code&scope=wallet%3Auser%3Aread
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            Log.d("myTag",inputLine);
            response.append(inputLine);

        }
        in.close();
        Log.d("myTag","sendPost Finished");
        //print result
        System.out.println(response.toString());
        return response.toString();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.justmabitcnx);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String token = getIntent().getData().getQueryParameter("code");

        String access_token = "";

        Button button = (Button) findViewById(R.id.backButton);

        button.setOnClickListener(new View.OnClickListener() { //com.example.tom_h.coinbase_implementation://oauth.callback$code=code
            public void onClick(View arg0) { //urn:ietf:wg:oauth:2.0:oob
                Intent backIntent =
                        new Intent(MyAppCallback.this,MainActivity.class);
                startActivity(backIntent);

                //System.out.println(authzCode);
            }
        });

        try {
            String authInfo = sendPost(token);
            access_token = authInfo.substring((authInfo.indexOf("\"access_token\":")+16),authInfo.indexOf(",\"token_type\"")-1);
            Log.d("myTag",access_token);
        } catch (Exception e) {
            Log.d("myTag","sendPost failed");
            e.printStackTrace();
            return;
        }



        Log.d("myTag",access_token + "asd");
        Coinbase cb = new CoinbaseBuilder()
                .withAccessToken(access_token)
                .build();


        /*Coinbase cb = new CoinbaseBuilder()
                .withApiKey(System.getenv("G2KbsMi40ZLAxn9s"), System.getenv("JY59cIL4t2Ppil7QFWQ5dBHSpyFggOyJ"))
                .build();*/

        Log.d("myTag",access_token);

        /*
        try {
            Log.d("myTag", cb.getUser().getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CoinbaseException e) {
            e.printStackTrace();
        }
        */

        Transaction t = new Transaction();
        t.setTo("jacobsolis123@gmail.com");
        t.setAmount(Money.parse("BTC 0.00001"));
        //Transaction r = cb.sendMoney(t);
        try {
            Transaction r = cb.sendMoney(t);
            Log.d("myTag", "Money sent");
        } catch (CoinbaseException e) {
            Log.d("myTag", "Money Failed");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("myTag", "Money Failed");
            e.printStackTrace();
        }

        //String test = getIntent().getData().getQueryParameter("code");
        //Log.d("myTag",test + "asd");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MyAppCallback Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ThomasHorgaGmailCom.JustMaBitCnx/com.ThomasHorgaGmailCom.JustMaBitCnx/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}


