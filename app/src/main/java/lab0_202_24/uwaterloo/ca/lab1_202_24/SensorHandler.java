package lab0_202_24.uwaterloo.ca.lab1_202_24;

import android.widget.TextView;

import java.util.Vector;

/**
 * Created by patri on 2017-01-18.
 */

public abstract class SensorHandler {
    TextView mOutputTextView;
    Vector<Float> mMaximumValues;

    SensorHandler(TextView OutputTextView){
        mOutputTextView = mOutputTextView;
    }

    protected void UpdateMaxValues(float[] values){
        for (int i = 0; i < values.length; ++i){
            if (Math.abs(values[i]) > Math.abs(mMaximumValues.get(i)))
                mMaximumValues.set(i, values[i]);
        }
    }
    abstract protected void HandleOutput(float[] values);
}
