package com.nazer.manager.fb;

import android.app.Activity;
import android.os.Bundle;

import com.nazer.callbacks.GenericApiCallBack;
import com.nazer.util.PrintLog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginIntegration {

    private static String TAG = "FacebookLoginIntegration";
    private static boolean isShare;

    /**
     * ==========================================   Call FaceBook Login
     * @param activity
     * @param callbackManager
     * @param genricCallback
     */
    public static void callFacebookLogin(final Activity activity, final CallbackManager callbackManager, final GenericApiCallBack genricCallback) {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        PrintLog.e(TAG, "onSuccess: " + loginResult.getAccessToken());
                        // App code
                        if(!isShare)
                            RequestData(loginResult.getAccessToken(), genricCallback);
                        else {
                            isShare=false;
                            shareOnFaceBook(activity, callbackManager, genricCallback);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        PrintLog.e(TAG, "onCancel: ");

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        PrintLog.e(TAG, "onError: "+exception.getMessage());
                        faceBookLogout();
                        genricCallback.onFailuire(exception);
                    }
                });
    }

    public static boolean isFaceBookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }

    public static void faceBookLogout() {
        if (isFaceBookLogin())
            LoginManager.getInstance().logOut();
    }

    /**
     * ==========================  Request data to get information through facebook graph Api
     *
     * @param currentAccessToken
     */
    public static void RequestData(AccessToken currentAccessToken, final GenericApiCallBack genricCallback) {

        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                PrintLog.i(TAG, " Facebook Json data :" + json);
                try {
                    if (json != null) {
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> " + json.getString("link");

                    }
                    genricCallback.onSuccess(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    /**
     * ================================= share on FaceBook
     * @param activity
     * @param callbackManager
     * @param genricCallback
     */
    public static void shareOnFaceBook(Activity activity, CallbackManager callbackManager, GenericApiCallBack genricCallback)
    {

        if(isFaceBookLogin())
        {
            isShare =false;




        }
        else {
            isShare =true;
            callFacebookLogin(activity, callbackManager, genricCallback);
        }

    }


}
