package com.brouding.blockbuttonsample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brouding.blockbutton.BlockButton;
import com.brouding.blockbuttonsample.Pref.Pref;
import com.brouding.simpledialog.SimpleDialog;
import com.transitionseverywhere.TransitionManager;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity thisActivity;
    private SharedPreferences mPreference;

    private ViewGroup    layoutMain;
    private BlockButton  btnResetGuidePref;
    private TextView     textChangeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        thisActivity = this;
        mPreference  = getSharedPreferences(Pref.PREFERENCE_NAME, MODE_PRIVATE);

        layoutMain = (ViewGroup) findViewById(R.id.layout_main);

        BlockButton btnShowBasicDialog = (BlockButton) findViewById(R.id.btn_show_basic_dialog);
        btnShowBasicDialog.setOnClickListener(this);

        BlockButton btnShowProgressDialog = (BlockButton) findViewById(R.id.btn_show_progress_dialog);
        btnShowProgressDialog.setOnClickListener(this);

        BlockButton btnShowGuideDialog = (BlockButton) layoutMain.findViewById(R.id.btn_show_guide_dialog);
        btnShowGuideDialog.setOnClickListener(this);

        textChangeEnabled = (TextView) layoutMain.findViewById(R.id.text_btn_change_enabled);

        btnResetGuidePref = (BlockButton) layoutMain.findViewById(R.id.btn_reset_guide);
        btnResetGuidePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGuidePref();

                setBtnGuideReset(false);
            }
        });

        setBtnGuideReset(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        resetGuidePref();
    }

    private void setBtnGuideReset(boolean enabled) {
        TransitionManager.beginDelayedTransition(layoutMain);

        btnResetGuidePref.setEnabled(enabled);
        textChangeEnabled.setText("setEnabled(" +enabled +")");
    }

    private void resetGuidePref() {
        SharedPreferences.Editor edit = mPreference.edit();
        edit.putBoolean(Pref.KEY_FIRST_WELCOME, Pref.FIRST_WELCOME_DEFAULT);
        edit.apply();
    }

    public void goToGithub(View v) {
        String url ="https://github.com/BROUDING/";
        Intent intent;

        switch ( v.getId() ) {
            case R.id.btn_goto_block_button:
                url += "BlockButton";
                break;

            case R.id.btn_goto_simple_dialog:
                url += "SimpleDialog";
                break;
        }

        intent = new Intent( Intent.ACTION_VIEW, Uri.parse(url) );
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.btn_show_basic_dialog:
                new SimpleDialog.Builder(thisActivity)
                        .setContent("This is basic SimpleDialog :)")
                        .setBtnConfirmText("Check!")
                        .setBtnConfirmTextColor("#de413e")

                        // Customizing

                        //.setTitle("Hello !", true)
                        //.setCancelable(true)          // Default value is false
                        //.onConfirm(new SimpleDialog.BtnCallback() {
                        //    @Override
                        //    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                        //        // Do something
                        //    }
                        //})
                        //.setBtnCancelText("Cancel", false)
                        //.onCancel(new SimpleDialog.BtnCallback() {
                        //    @Override
                        //    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                        //        // Do something
                        //    }
                        //})
                        .show();
                break;

            case R.id.btn_show_progress_dialog:
                new SimpleDialog.Builder(thisActivity)
                        .setContent("This is progress SimpleDialog :)")
                        .setProgressGIF(R.raw.loading_default)
                        .setBtnCancelText("Cancel")
                        .setBtnCancelTextColor("#2861b0")

                        // Customizing

                        //.setBtnCancelText("Cancel", false)
                        //.setBtnCancelTextColor(R.color.colorPrimary)
                        //.setBtnCancelShowTime(2000)
                        //.onCancel(new SimpleDialog.BtnCallback() {
                        //    @Override
                        //    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                        //        // thisActivity.finish();
                        //    }
                        //})
                        //.showProgress(true)
                        .show();
                break;

            case R.id.btn_show_guide_dialog:
                new SimpleDialog.Builder(thisActivity)
                        .setTitle("Hello !", true)      // Not necessary
                        .setContent("This is guide SimpleDialog :)\n\n- You can pinch the view !")
                        .setGuideImage(R.drawable.image_guide_pinch)    // Not necessary
                        .setGuideImageSizeDp(150, 150)
                        .setPreferenceName(Pref.PREFERENCE_NAME)
                        .setPermanentCheckKey(Pref.KEY_FIRST_WELCOME)
                        .onConfirmWithPermanentCheck(new SimpleDialog.BtnCallback() {
                            @Override
                            public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                                setBtnGuideReset(true);
                            }
                        })
                        .setBtnConfirmText("Check!")
                        .setBtnConfirmTextColor("#e6b115")

                        // Customizing

                        //.setTitle("Hello !", true)
                        //.setBtnPermanentCheckText("다시 보지 않기", true)
                        //.setGuideImagePaddingDp(10)
                        //.setGuideImageSizeDp(100, 100)
                        .showIfPermanentValueIsFalse();
                break;


        }
    }
}
