package com.nazer.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationHelper implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, PermissionUtils.PermissionResultCallback {

    private Activity context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isPermissionGranted;
    private Location mLastLocation = null;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // list of permissions
    private ArrayList<String> permissions = new ArrayList<>();
    private PermissionUtils permissionUtils;

    private final static int PLAY_SERVICES_REQUEST = 1000;
    public final static int REQUEST_CHECK_SETTINGS = 2000;
    public final static int LOCATION_REQUEST_CODE = 2000;

    private final long INTERVAL = 1000 * 10;
    private long FASTEST_INTERVAL = 1000 * 5;

    private LocationRequest mLocationRequest;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationFetchedCallback locationFetchedCallback;

    public LocationHelper(Activity context, LocationFetchedCallback locationFetchedCallback) {

        this.context = context;
        this.locationFetchedCallback = locationFetchedCallback;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        checkpermission();
    }

    public LocationHelper() {

    }

    /**
     * Method to check the availability of location permissions
     */

    private void checkpermission() {
        permissionUtils = new PermissionUtils(context, this);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", LOCATION_REQUEST_CODE);
    }

    private boolean isPermissionGranted() {
        return isPermissionGranted;
    }


    /**
     * Method to verify google play services on the device
     */

    public boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(context, resultCode,
                        PLAY_SERVICES_REQUEST).show();
                PrintLog.e(TAG, "checkPlayServices yes hai");
            } else {
                PrintLog.e(TAG, "checkPlayServices No hai");
                Toast.makeText(context, "This device is not supported.", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    public void connectGoogleApi() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    public void disconnectGoogle() {
        mGoogleApiClient.disconnect();
        PrintLog.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    protected void stopLocationUpdates() {
//        fusedLocationProviderClient.removeLocationUpdates(this);

        PrintLog.d(TAG, "Location update stopped .......................");

        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        PrintLog.d(TAG, "Stop isConnected ...............: " + mGoogleApiClient.isConnected());

    }


    /**
     * Method to return the location on UI
     */

    @SuppressLint("MissingPermission")
    public Location getLastLocation() {
        this.context=context;
        if (isPermissionGranted()) {
            PrintLog.d(TAG, "getLastLocation  ..............: ");
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(context, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();
                                String result = "Last known Location Latitude is " +
                                        mLastLocation.getLatitude() + "\n" +
                                        "Last known longitude Longitude is " + mLastLocation.getLongitude();
                                PrintLog.d(TAG, "getLastLocation  ..............: " + result);

                                if (mLastLocation != null) {
                                    mLastLocation = mLastLocation;
                                    locationFetchedCallback.onLocationFetched(mLastLocation);
                                }

                            } else {
                                callCurrentLocation();
                                PrintLog.d(TAG, "No Last known location found. Try current location..!");
                            }
                        }
                    });


            return mLastLocation;
        } else
            checkpermission();
        return null;
    }


    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    //Check for location settings. if the location disabled prompt an alert dialog to redirect user.
    public void checkForLocationSettings() {
        try {
            LocationSettingsRequest settingsRequest = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest).build();
            SettingsClient settingsClient = LocationServices.getSettingsClient(context);

            Task<LocationSettingsResponse> task = settingsClient
                    .checkLocationSettings(settingsRequest);
            task.addOnSuccessListener(context, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    //Setting is success...
                    PrintLog.d(TAG, "LocationSettingsStatusCodes  ..............: " + locationSettingsResponse.getLocationSettingsStates());
                    // All location settings are satisfied. The client can initialize location requests here
                    getLastLocation();
                }
            }).addOnFailureListener(context, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            PrintLog.d(TAG, "dialogue open settings  ..............: ");
                            try {
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                sie.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Toast.makeText(context, "Setting change is not available.Try in another device.", Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void callCurrentLocation() {
        try {


            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    mLastLocation = (Location) locationResult.getLastLocation();

                    String result = "Current Location Latitude is " +
                            mLastLocation.getLatitude() + "\n" +
                            "Current location Longitude is " + mLastLocation.getLongitude();
                    PrintLog.d(TAG, "callCurrentLocation ..............: " + result);
                    locationFetchedCallback.onLocationFetched(mLastLocation);
                    fusedLocationProviderClient.removeLocationUpdates(this);
                }
            }, Looper.myLooper());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        PrintLog.d(TAG, "onLocationChanged ..............: " + location);
        if (location != null) {
            mLastLocation = location;
            locationFetchedCallback.onLocationFetched(mLastLocation);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        PrintLog.d(TAG, "onConnected ..............: ");
        checkForLocationSettings();
    }

    @Override
    public void onConnectionSuspended(int i) {
        PrintLog.d(TAG, "onConnectionSuspended ..............: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        PrintLog.d(TAG, "onConnectionFailed ..............: " + connectionResult.toString());
    }

    @Override
    public void PermissionGranted(int request_code) {
        PrintLog.i(TAG, "PermissionGranted" + request_code);
        isPermissionGranted = true;
        if (checkPlayServices())
            connectGoogleApi();

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        PrintLog.i(TAG, "PartialPermissionGranted" + request_code);
    }

    @Override
    public void PermissionDenied(int request_code) {
        PrintLog.i(TAG, "PermissionDenied" + request_code);
    }

    @Override
    public void NeverAskAgain(int request_code) {
        PrintLog.i(TAG, "NeverAskAgain" + request_code);
    }

    public interface LocationFetchedCallback {
        public void onLocationFetched(Location location);
    }
}
