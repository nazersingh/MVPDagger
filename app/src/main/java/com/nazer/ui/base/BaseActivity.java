package com.nazer.ui.base;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nazer.R;
import com.nazer.callbacks.CurrentFragmentCallBack;
import com.nazer.manager.application.MyApplication;
import com.nazer.manager.db.PreferenceHandler;
import com.nazer.ui.dialogues.DateTimePickerDialogue;
import com.nazer.ui.dialogues.Dialogues;
import com.nazer.ui.dialogues.ImageVideoAudioPicker;
import com.nazer.util.LocationHelper;
import com.nazer.util.PrintLog;
import com.nazer.util.Utility;
import com.nazer.util.Validations;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    Dialogues dialogues;
    @Inject
    ImageVideoAudioPicker imageVideoAudioPicker;
    @Inject
    PreferenceHandler preferenceHandler;
    @Inject
    Utility utility;
    @Inject
    DateTimePickerDialogue dateTimePickerDialogue;
    @Inject
    Validations validations;
    @Inject
    LocationHelper locationHelper;

    Location mLocation = null;

    private long TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed = 0;
    private int mMenuItem = 0;
    CurrentFragmentCallBack currentfragmentCallBack;
    public final int ERROR = 400;
    private final int EMAIL_VERIFIACTION = 500;
    private final int ACCOUNT_DEACTIVATED = 800;
    private final int SESSION_EXPIRED = 1000;
    private final String TAG = this.getClass().getSimpleName();


    public abstract void setUpLayout();

    public abstract void setUpView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().getApplicationComponent().inject(this);
    }

    public void setToolbarTitle(String title) {
        showToolbar();
        getSupportActionBar().setTitle(title);
    }

    public void setToolbarSubTitle(String title) {
        getSupportActionBar().setSubtitle(title);
    }

    public void hideToolbar() {
        getSupportActionBar().hide();
    }

    public void showToolbar() {
        getSupportActionBar().show();
    }

    /**
     * to set the Optipon menu call this method in oncreate()/onCreateView()
     *
     * @param menu
     */
    public void setOptionMenu(int menu) {
        mMenuItem = menu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mMenuItem != 0) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(mMenuItem, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                // getInstance of Fragment
                return true;
            case R.id.filter:
                // getInstance of Fragment
                return false;
            default:
                break;
        }
        return false;
    }

    public void setCurrentfragmentCallBack(CurrentFragmentCallBack currentfragmentCallBack) {
        this.currentfragmentCallBack = currentfragmentCallBack;
    }

    public void onAddFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, fragment, fragment.getTag());
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commitAllowingStateLoss();
    }


    public void onReplaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.getTag());
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }


    public void clearBackStack() {
        getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * show Dialogue According the Status code
     *
     * @param statusCode
     */
    public void checkStatusCode(int statusCode, String message) {
        switch (statusCode) {
            case ERROR: {

            }
            break;
            case EMAIL_VERIFIACTION:
                break;
            case SESSION_EXPIRED:
                showSessionExpired(message);
                break;
            default:
        }
    }

    /**
     * show Error on Dialogue
     *
     * @param error
     */
    public void checkRetrofitfailure(Throwable error) {
        PrintLog.e(TAG, error.getMessage());
    }

    /**
     * =========== session expired
     *
     * @param msg= msg to show on dialogue
     */
    private void showSessionExpired(String msg) {
        dialogues.setPositiveButton(dialogues.POSITIVE_BUTTON_OK)
                .setMsg(msg)
                .setTitle(this.getResources().getString(R.string.app_name))
                .messageWithOneButton(this, new Dialogues.DialogueOneButtonCallback<String>() {
                    @Override
                    public void OnOkClick(String s) {
                        logoutToLogin();
                    }
                });
    }

    /**
     * ========= logout user
     */
    public void logout() {
        dialogues
                .setPositiveButton(dialogues.POSITIVE_BUTTON_YES)
                .setNegativeButton(dialogues.NEGATIVE_BUTTON_NO)
                .setMsg(this.getResources().getString(R.string.logout_mesg))
                .setTitle(this.getResources().getString(R.string.app_name))
                .messageWithTwoButton(this, new Dialogues.DialogueTwoButtonCallback<String>() {
                    @Override
                    public void OnOkClick(String s) {
                        logoutToLogin();
                    }

                    @Override
                    public void OnCancelClick(String s) {

                    }
                });
    }

    /**
     * ========= clear local data and move to login screen
     */
    private void logoutToLogin() {
        preferenceHandler.clearSavedPrefrences(this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//            popFragment();
            super.onBackPressed();
            Fragment ffragment = getSupportFragmentManager().findFragmentById(R.id.container);
            ffragment.setUserVisibleHint(true);
        } else {

            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                finish();
            } else {
                mBackPressed = System.currentTimeMillis();
                Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public Validations checkValidation() {
        return validations;
    }


    /**
     * ======== imagePicker Dialogue
     *
     * @return
     */
    public void showImagePickerDialogue() {
        dialogues.showImagePickerDialogue(imageVideoAudioPicker, this);
    }

    public void showVideoPickerDialogue() {
        dialogues.showVideoPickerDialogue(imageVideoAudioPicker, this);
    }

    /**
     * ======== datePicker Dialogue
     */
    public DateTimePickerDialogue getDatePicker() {
        return dateTimePickerDialogue;
    }

    /**
     * =========== Shared Prefrence Handler
     */
    public void savePrefrenceValueString(String key, String value) {
        preferenceHandler.writeString(this, key, value);
    }

    public void savePrefrenceValueBoolean(String key, Boolean value) {
        preferenceHandler.writeBoolean(this, key, value);
    }

    /**
     * ====== Load Pic with Progressbar
     */
    public void loadPic(String file, ImageView imageView, ProgressBar progressBar, boolean isRound) {
        utility.loadPic(this, file, imageView, progressBar, isRound);
    }

    public void loadPic(String file, ImageView imageView) {
        utility.loadPic(this, file, imageView);
    }


    public Location getLocation() {

//        LocationHelper locationHelper = new LocationHelper(this, new LocationHelper.LocationFetchedCallback() {
//            @Override
//            public void onLocationFetched(Location location) {
//                mLocation = location;
//            }
//        });
       return locationHelper.getLastLocation();
    }
    public String getAddressFromLocation(Location location)
    {
        return locationHelper.getAddress(location.getLatitude(),location.getLongitude()).toString();
    }

}
