package lab0_202_24.uwaterloo.ca.lab1_202_24;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xCoord, yCoord, zCoord;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    //private float[] mGravity = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xCoord = (TextView) findViewById(R.id.x_coord);
        yCoord = (TextView) findViewById(R.id.y_coord);
        zCoord = (TextView) findViewById(R.id.z_coord);

        /*LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.activity_main);

        xCoord = new TextView(getApplicationContext());
        xCoord.setText("Hello!");
        relativeLayout.addView(xCoord);*/

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //Do nothing...
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            xCoord.setText(Float.toString(event.values[0]));
            yCoord.setText(Float.toString(event.values[1]));
            zCoord.setText(Float.toString(event.values[2]));
        }
    }


}
