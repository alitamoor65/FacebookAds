package com.example.alitamoor.facebookads;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class MainActivity extends AppCompatActivity {

    AdView adView;
    LinearLayout adLayout;
    InterstitialAd interstitialAd;
    private String TAG = "MainAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NativeAdsActivity.class));
            }
        });

        setUpBannerAds();

        interstitialAd = new InterstitialAd(this,"YOUR_PLACEMENT_ID");
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.i(TAG, "onInterstitialDisplayed: Interstitial");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.i(TAG, "onInterstitialDismissed: Interstitial");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.i(TAG, "onError: Interstitial: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.i(TAG, "onAdLoaded: Interstitial");
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.i(TAG, "onAdClicked: Interstitial");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.i(TAG, "onLoggingImpression: Interstitial");
            }
        });

        interstitialAd.loadAd();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Check if interstitialAd has been loaded successfully
                if(interstitialAd == null || !interstitialAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if(interstitialAd.isAdInvalidated()) {
                    return;
                }
                // Show the ad
                interstitialAd.show();
            }
        }, 1000 * 60 * 3);

    }

    private void setUpBannerAds() {
        adView = new AdView(this,"YOUR_PLACEMENT_ID",AdSize.BANNER_HEIGHT_50);
        adLayout = findViewById(R.id.adContainerLayoutID);
        adLayout.addView(adView);
        adView.loadAd();

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.i(TAG, "onError: Banner :" + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.i(TAG, "onAdLoaded: Banner");
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.i(TAG, "onAdClicked: Banner");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.i(TAG, "onLoggingImpression: Banner");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adView.destroy();
        interstitialAd.destroy();
    }
}
