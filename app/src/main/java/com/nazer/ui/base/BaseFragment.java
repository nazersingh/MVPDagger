package com.nazer.ui.base;

import android.location.Location;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nazer.ui.dialogues.DateTimePickerDialogue;
import com.nazer.util.Validations;

public abstract class BaseFragment extends Fragment {

    public abstract void setUpLayout();

    public abstract void setUpView();

    public void setTitle(String title) {
        showToolbar();
        ((BaseActivity) getActivity()).setToolbarTitle(title);
    }

    public void setToolbarSubTitle(String subTitle) {
        ((BaseActivity) getActivity()).setToolbarTitle(subTitle);
    }

    public void checkApiStatusCode(int statusCode, String message) {
        ((BaseActivity) getActivity()).checkStatusCode(statusCode, message);
    }

    public void checkApiFailure(Throwable throwable) {
        ((BaseActivity) getActivity()).checkRetrofitfailure(throwable);
    }

    public void showToolbar() {
        ((BaseActivity) getActivity()).showToolbar();
    }

    public void isOptionMenu(boolean isOptionMenu) {
        setHasOptionsMenu(isOptionMenu);
    }

    public void setOptionMenu(int mMenu) {
        ((BaseActivity) getActivity()).setOptionMenu(mMenu);
    }

    public void addFragment(Fragment fragment) {
        ((BaseActivity) getActivity()).onAddFragment(fragment);
    }

    public void showImagePicker()
    {
        ((BaseActivity) getActivity()).showImagePickerDialogue();
    }
    public void showVideoPicker()
    {
        ((BaseActivity) getActivity()).showVideoPickerDialogue();
    }

    public void savePrefrenceValueString(String key,String value)
    {
        ((BaseActivity) getActivity()).savePrefrenceValueString(key,value);
    }
    public void savePrefrenceValueBoolean(String key,Boolean value)
    {
        ((BaseActivity) getActivity()).savePrefrenceValueBoolean(key,value);
    }
    public void loadPic(String file, ImageView imageView, ProgressBar progressBar, boolean isRound) {
        ((BaseActivity) getActivity()).loadPic(file,imageView,progressBar,isRound);
    }
    public void loadPic(String file, ImageView imageView) {
        ((BaseActivity) getActivity()).loadPic(file,imageView);
    }
    public DateTimePickerDialogue getDatePicker()
    {
       return  ((BaseActivity) getActivity()).getDatePicker();
    }
    public Validations checkValidation()
    {
        return ((BaseActivity) getActivity()).checkValidation();
    }

    public Location getLocation()
    {
        return ((BaseActivity) getActivity()).getLocation();
    }
    public String getAddressFromLocation(Location location)
    {
        return ((BaseActivity) getActivity()).getAddressFromLocation(location);
    }
}
