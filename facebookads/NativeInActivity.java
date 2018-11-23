package com.example.alitamoor.facebookads;

import android.app.Activity;
import android.app.NativeActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

public class NativeInActivity extends AppCompatActivity {

    NativeAd nativeAd;
    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_in);

        loadNativeAds((LinearLayout)findViewById(R.id.nativeAdContainerID));
    }
    void loadNativeAds(final LinearLayout linearLayout){
        AdSettings.addTestDevice("3f4178ce-9554-4801-a5fe-18c0ffa8e094");
        nativeAd = new NativeAd(this,"349632789123307_352451945508058");
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                Log.i(TAG, "onMediaDownloaded: ");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.i(TAG, "onError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.i(TAG, "onAdLoaded: ");
                nativeAd.unregisterView();

                LayoutInflater layoutInflater = LayoutInflater.from(NativeInActivity.this);
                CardView adView = (CardView) layoutInflater.inflate(R.layout.native_ad_layout,linearLayout,false);
                linearLayout.addView(adView);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(NativeInActivity.this, nativeAd, true);
                //adChoicesContainer.addView(adChoicesView, 0);

                // Create native UI using the ad metadata.
                AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
                TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
                Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdvertiserName());
                nativeAdBody.setText(nativeAd.getAdBodyText());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                sponsoredLabel.setText(nativeAd.getSponsoredTranslation());


                // Create a list of clickable views
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);

                // Register the Title and CTA button to listen for clicks.
                nativeAd.registerViewForInteraction(
                        adView,
                        nativeAdMedia,
                        nativeAdIcon,
                        clickableViews);

            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.i(TAG, "onAdClicked: ");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.i(TAG, "onLoggingImpression: ");
            }
        });
        nativeAd.loadAd();
    }
}
