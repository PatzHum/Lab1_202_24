package lab0_202_24.uwaterloo.ca.lab1_202_24;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by patri on 2017-01-18.
 */

public class AccelerationHandler extends SensorHandler {

    float[] gravity = new float[3];

    AccelerationHandler(Context applicationContext, LinearLayout layout, String sensorType){
        super(applicationContext, layout, sensorType);
    }

    @Override
    protected float[] ProcessData(float[] values){
        UpdateMaxValues(values);

        float alpha = (float) 0.8;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * values[2];

        float[] acc = new float[3];
        acc[0] = values[0] - gravity[0];
        acc[1] = values[1] - gravity[1];
        acc[2] = values[2] - gravity[2];

        return acc;
    }


}
