package com.ThomasHorgaGmailCom.JustMaBitCnx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ThomasHorgaGmailCom.JustMaBitCnx.estimote.BeaconID;
import com.ThomasHorgaGmailCom.JustMaBitCnx.estimote.EstimoteCloudBeaconDetails;
import com.ThomasHorgaGmailCom.JustMaBitCnx.estimote.EstimoteCloudBeaconDetailsFactory;
import com.ThomasHorgaGmailCom.JustMaBitCnx.estimote.ProximityContentManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static boolean NO_BEACON = true; // true if testing without a beacon

    private static final String TAG = "MainActivity";

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 41991, 17469)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text;
                Integer backgroundColor;
                if (content != null || NO_BEACON) {
                    if(content != null){
                        EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;

                        text = "You're in " + beaconDetails.getBeaconName() + "'s range!";
                    }

                    backgroundColor = BACKGROUND_COLOR_NEUTRAL; // green
                    // Assuming you are using xml layout
                    System.out.print('d');
                    Button button = (Button) findViewById(R.id.buttonTip);

                    Log.d("myTag","This is  a log ");
                    button.setOnClickListener(new View.OnClickListener() { //com.example.tom_h.coinbase_implementation://oauth.callback$code=code
                        public void onClick(View arg0) { //urn:ietf:wg:oauth:2.0:oob
                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse("https://www.coinbase.com/oauth/authorize?response_type=code&client_id=856fb5f74b2f139c88d49f44ace25eb67bf26ec0089ad5e2ca6d5b9f96864d22&redirect_uri=https://x-com.ThomasHorgaGmailCom.JustMaBitCnx://oauth.callback$code=code&scope=send&meta[send_limit_amount]=1&meta[send_limit_currency]=USD&meta[send_limit_period]=day"));
                            startActivity(viewIntent);

                            //System.out.println(authzCode);
                        }
                    });

                    text = "John is performing near you!";
                    backgroundColor = 0xff90ff99; // green



                } else {
                    text = "No performers in range.";
                    backgroundColor = 0xff444444; //some color
                }
                ((TextView) findViewById(R.id.textView)).setText(text);
                findViewById(R.id.relativeLayout).setBackgroundColor(
                        backgroundColor != null ? backgroundColor : BACKGROUND_COLOR_NEUTRAL);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }
    public void imageClick(View view) {
         /*
         code to load a profile or something, ratings etc.
         */

    }

    public void tipButton(View view) {
        /* code to implement tipping
         if (balance - 1 >= 0){
        }
        else {
            // you poor
        }*/
    }
}
