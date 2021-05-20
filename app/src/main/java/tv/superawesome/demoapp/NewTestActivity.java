package tv.superawesome.demoapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import tv.superawesome.sdk.publisher.SAInterface;
import tv.superawesome.sdk.publisher.managed.SAManagedBannerAd;
import tv.superawesome.sdk.publisher.managed.SAManagedInterstitialAd;

public class NewTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);

        SAManagedBannerAd banner = findViewById(R.id.managed_ad_banner);
        banner.setListener((SAInterface) (placementId, event) -> {

        });
        banner.load(58525);

        SAManagedBannerAd mpu = findViewById(R.id.managed_mpu);
        mpu.setListener((SAInterface) (placementId, event) -> {

        });
        mpu.load(58524);

        AppCompatButton button = findViewById(R.id.managed_interstitial);
        button.setOnClickListener(view -> SAManagedInterstitialAd.load(this, 58509));
    }
}
