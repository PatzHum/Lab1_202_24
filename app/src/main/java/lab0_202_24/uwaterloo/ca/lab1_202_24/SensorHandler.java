package lab0_202_24.uwaterloo.ca.lab1_202_24;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by patri on 2017-01-18.
 */

public abstract class SensorHandler {
    TextView mOutputLabel, mOutputValue, mMaxOutputLabel, mMaxOutputValue;
    Vector<Float> mMaximumValues;

    SensorHandler(Context applicationContext, LinearLayout layout, String sensorType){
        mOutputLabel = new TextView(applicationContext);
        mOutputValue = new TextView(applicationContext);
        mMaxOutputLabel = new TextView(applicationContext);
        mMaxOutputValue = new TextView(applicationContext);

        layout.addView(mOutputLabel);
        layout.addView(mOutputValue);
        layout.addView(mMaxOutputLabel);
        layout.addView(mMaxOutputValue);


        mOutputLabel.setText(String.format("The %s sensor reading is: "));
        mMaxOutputLabel.setText(String.format("The maximum %s reading is: "));
    }

    protected void UpdateMaxValues(float[] values){
        for (int i = 0; i < values.length; ++i){
            if (Math.abs(values[i]) > Math.abs(mMaximumValues.get(i)))
                mMaximumValues.set(i, values[i]);
        }
    }

    protected float[] ProcessData(float[] values){
        return values;
    }

    public void HandleOutput(float[] values){
        values = ProcessData(values);

        UpdateMaxValues(values);

        String outputValueString = "";
        for (int i = 0; i < values.length; ++i){
            if (i != 0){
                outputValueString += ",";
            }
            outputValueString += Float.toString(values[i]);
        }
        mOutputValue.setText(outputValueString);

        String outputMaxValueString = "";
        for (int i = 0; i < mMaximumValues.size(); ++i){
            if (i != 0){
                outputMaxValueString += ",";
            }
            outputMaxValueString += Float.toString(mMaximumValues.get(i));
        }
    }
}
