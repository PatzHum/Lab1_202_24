package lab0_202_24.uwaterloo.ca.lab1_202_24;

import android.app.ActionBar;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private LineGraphView lineGraphView;

    double[][] accelArray = new double[100][3];     //csv file array


    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private Sensor mAccelerometer;
    private Sensor mMagSensor;
    private Sensor mRotSensor;

    private AccelerationHandler accelerationHandler;
    private GeneralSensorHandler lightHandler, mFieldHandler, rotHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize sensors
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mMagSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mRotSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);


        //reference linear layout
        LinearLayout layout = (LinearLayout)findViewById(R.id.lin_layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Create linegraphview programmatically
        lineGraphView = new LineGraphView(getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
        lineGraphView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(lineGraphView);
        lineGraphView.setVisibility(View.VISIBLE);

        //Create handlers for sensors
        lightHandler = new GeneralSensorHandler(getApplicationContext(), layout, "light");
        accelerationHandler = new AccelerationHandler(getApplicationContext(), layout, "acceleration", lineGraphView);
        mFieldHandler = new GeneralSensorHandler(getApplicationContext(), layout, "magnetic");
        rotHandler = new GeneralSensorHandler(getApplicationContext(), layout, "rotation");


        //BUTTONS
        Button resetButton = new Button(getApplicationContext());
        resetButton.setText("Reset Readings");
        layout.addView(resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetMax();
            }
        });


        Button csvDeposit = new Button(getApplicationContext());
        csvDeposit.setText("Deposit Acceleration Readings");
        layout.addView(csvDeposit);
        csvDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileWrite();
            }
        });



    }



    @Override
    protected void onResume(){
        super.onResume();
        //Register sensors with device
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);       //should make SENSOR_DELAY_GAME?
        mSensorManager.registerListener(this, mMagSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mRotSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //De-register sensors
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //Do nothing...
    }

    @Override
    public void onSensorChanged(SensorEvent event){

        //changes in light sensor
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            lightHandler.HandleOutput(event.values, 1);      //Pass values to handler
        }

        //changes in accelerometer
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerationHandler.HandleOutput(event.values); //Pass values to handler
            accelArray = accelerationHandler.GetAccelArray();
        }

        //changes in magnetic sensor
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mFieldHandler.HandleOutput(event.values);
        }
        //Changes in Rotation Vector
        else if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            rotHandler.HandleOutput(event.values, 4);       //Pass values to handler,
                                                            //Note the sensor has weird amounts of data, so we limit to 4 values
        }
    }

    public void fileWrite(){
        File accelRead = null;
        PrintWriter writer1 = null;

        try{
            accelRead = new File(getExternalFilesDir("Lab 1"), "AccelReadings.csv");
            writer1 = new PrintWriter(accelRead);

            for( int i = 0; i< 99; i++) {
                writer1.println(accelArray[i][0] + ", " + accelArray[i][1] + ", " + accelArray[i][2]);
            }
        }
        catch(IOException ex1){
            Log.d("Lab 1", "Failed to Write File: " + ex1.toString());
        }
        finally{
           if(writer1 != null){
               writer1.flush();
               writer1.close();
           }
        }
    }

    public  void resetMax(){
        accelerationHandler.Reset();
        lightHandler.Reset();
        rotHandler.Reset();
        mFieldHandler.Reset();
    }

}
