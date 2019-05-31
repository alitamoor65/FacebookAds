# Facebook Network audience - FacebookAds
This guide will walk you through all the steps to integrate the SDK for.
- 1- Facebook Banner Ads
- 2- Facebook Interstitial ads
- 3- Facebook Native ads
- 4- Facebook Native ads on back press dialog

## 1- Facebook Banner Ads
Add the following compile statement to your app-level build.gradle (not project!), to use the latest Audience Network SDK:
```
dependencies {
  compile 'com.android.support:recyclerview-v7:25.3.1'
  compile 'com.facebook.android:audience-network-sdk:5.+'
}
```
### Step 1: Adding a Layout Container for the Banner Ad
```
     <LinearLayout
        android:id="@+id/banner_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:orientation="vertical"
        />
```
### Step 2: Implementing the Banner in your Activity
```
  private AdView adView = new AdView(this, "YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

  // Find the Ad Container
  LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

  // Add the ad view to your activity layout
  adContainer.addView(adView);
 
  // Request an ad
  adView.loadAd();
```
add the following code to your activity's onDestroy() function to release resources the AdView uses:
```
@Override
protected void onDestroy() {
  if (adView != null) {
    adView.destroy();
  }
  super.onDestroy();
}
```
### Step 3: Adding an Ad Listener
```
    adView.setAdListener(new AdListener() {
      @Override
      public void onError(Ad ad, AdError adError) {
        // Ad error callback
        Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
          Toast.LENGTH_LONG).show();
        }

      @Override
      public void onAdLoaded(Ad ad) {
        // Ad loaded callback
      }

      @Override
      public void onAdClicked(Ad ad) {
        // Ad clicked callback
      }

      @Override
      public void onLoggingImpression(Ad ad) {
        // Ad impression logged callback
      }
    });
```

## 2- facebook interstitial ads
## 3- facebook native ads
## 4- facebook native ads on back press dialog
