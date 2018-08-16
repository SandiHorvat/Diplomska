package com.diplomska.diplomska;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity implements SensorEventListener, MobvoiApiClient.ConnectionCallbacks, MobvoiApiClient.OnConnectionFailedListener {


    private static final String TAG = "MainActivity";

    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor mHeartRate;
    private MobvoiApiClient mMobvoiApiClient;
    private Button utrip;
    private View view;
    private static final int MY_PERMISSIONS_REQUEST_BODY_SENSORS = 1;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Enables Always-on
        setAmbientEnabled();

        mMobvoiApiClient = new MobvoiApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        checkSelfPermission(Manifest.permission.BODY_SENSORS);







/*
        utrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startMeasure();
            }
        });

*/
        //OnClickBtn(utrip.findViewById(R.id.utrip));





        mSensorManager =
                (SensorManager)getSystemService(SENSOR_SERVICE);
        mHeartRate = mSensorManager.getDefaultSensor(
                Sensor.TYPE_HEART_RATE);


    }








    public void OnClickBtn(View v){
       // checkSelfPermission(Manifest.permission.BODY_SENSORS);
        if (checkSelfPermission(Manifest.permission.BODY_SENSORS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.BODY_SENSORS},
                    MY_PERMISSIONS_REQUEST_BODY_SENSORS);
            if (checkSelfPermission(Manifest.permission.BODY_SENSORS)
                    != PackageManager.PERMISSION_GRANTED){
                System.out.print(" Drugi if stavek.");
            }
            else{
                System.out.print(" Drugi else.");
            }
        }
        else{
            System.out.print(" NE Deluje.");
        }
        startMeasure();
    }

    public void OnClickBtnStop (View v){
        stopMeasure();
    }


    private void startMeasure() {
        boolean sensorRegistered = mSensorManager.registerListener(this, mHeartRate, SensorManager.SENSOR_DELAY_FASTEST);
        Log.d("Sensor Status:", " Sensor registered: " + (sensorRegistered ? "yes" : "no"));
    }

    private void stopMeasure() {
        mSensorManager.unregisterListener(this);
    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHeartRate,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Log.d("Test", "Got the heart rate (beats per minute) : " +
                String.valueOf(sensorEvent.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed(): Failed to connect, with result: " + connectionResult);
    }


}
