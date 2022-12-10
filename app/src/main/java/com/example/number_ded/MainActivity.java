package com.example.number_ded;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.number_ded.Fragments.SplashFragment;
import com.example.number_ded.Fragments.WebviewFragment;
import com.example.number_ded.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {

     ActivityMainBinding binding;
    Handler handler;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    String firstTime,israted;
    String text1= null;
    SharedPreferences sharedPreferences,sharedPreferencesText;
    SharedPreferences.Editor myEdit,myEditText;
    FragmentTransaction ft2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler=new Handler();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        sharedPreferencesText = getSharedPreferences("MyTextPref",MODE_PRIVATE);
        myEditText = sharedPreferencesText.edit();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft2 = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, new SplashFragment());
        ft.commit();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//

        text1  = sharedPreferencesText.getString("text1", "");
        firstTime  = sharedPreferences.getString("first_time", "");
        israted  = sharedPreferences.getString("israted", "");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (firstTime.isEmpty()){
                    myEdit.putString("first_time", "first_timer");
                    myEdit.commit();
                    getValueFromFireBaseCOnfig();
                }else if(israted.isEmpty()) {
                    showRateUs();
                }else {
                    getValueFromFireBaseCOnfig();
                }

            }
        },3000);

    }

    private void showRateUs() {

        Rect displayRectangle = new Rect();
        Window window = MainActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, androidx.appcompat.R.style.Base_Widget_AppCompat_ActionBar_Solid);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.rate_us_dialog, viewGroup, false);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button button_rate=dialogView.findViewById(R.id.rate_button);
        ImageView button_cancel=dialogView.findViewById(R.id.cancel_button);
        button_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEdit.putString("israted", "israted");
                myEdit.commit();
                alertDialog.dismiss();
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                startActivity(launchIntent);
                finish();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getValueFromFireBaseCOnfig();
            }
        });
        alertDialog.show();
    }

    private void getValueFromFireBaseCOnfig() {

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            myEditText.clear();
                            myEditText.putString("text1", mFirebaseRemoteConfig.getString("text1"));
                            myEditText.commit();
                            Log.d("my-api", "==== " + mFirebaseRemoteConfig.getString("text1"));
                            if(mFirebaseRemoteConfig.getString("text1").equals("aaa")) {
                                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                getSupportFragmentManager().beginTransaction().remove(new SplashFragment()).commit();
                                ft2.replace(R.id.your_placeholder, new WebviewFragment());
                                ft2.commit();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}