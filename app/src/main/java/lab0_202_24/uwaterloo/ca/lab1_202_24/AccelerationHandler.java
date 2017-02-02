package lab0_202_24.uwaterloo.ca.lab1_202_24;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by patri on 2017-01-18.
 */

public class AccelerationHandler extends SensorHandler {

    float[] gravity = new float[3]; //Gravity Filter values
    LineGraphView mLineGraphView;
    double[][] accelArray = new double[100][3];
    AccelerationHandler(Context applicationContext, LinearLayout layout, String sensorType, LineGraphView lineGraphView){
        super(applicationContext, layout, sensorType);
        mLineGraphView = lineGraphView;         //Get line graph view reference to manage
    }

    @Override
    protected float[] ProcessData(float[] values){
        //Filter rate
        float alpha = (float) 0.8;

        //Filter gravity out of data
        gravity[0] = alpha * gravity[0] + (1 - alpha) * values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * values[2];

        //New values after filtering
        float[] acc = new float[3];
        acc[0] = values[0] - gravity[0];
        acc[1] = values[1] - gravity[1];
        acc[2] = values[2] - gravity[2];

        return acc;
    }

    @Override
    public void HandleOutput(float[] v){
        HandleOutput(v, v.length);
    }
    @Override
    public void HandleOutput(float[] v, int maxLen) {
        super.HandleOutput(v, maxLen);
        v = ProcessData(v);
        //Log data points on visual graph
        mLineGraphView.addPoint(v);

        //Shift data points internally
        for (int i = 1; i < 100; ++i){
            for (int j = 0; j < 3; ++j){
                accelArray[i-1][j] = accelArray[i][j];
            }
        }
        //Log new data point
        for(int i = 0; i<3; i++) {
            accelArray[99][i] = v[i];
        }
    }

    //Standard getter method.
    public double[][] GetAccelArray(){
        return accelArray;
    }

}
