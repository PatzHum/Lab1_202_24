package lab0_202_24.uwaterloo.ca.lab1_202_24;

import android.widget.TextView;

/**
 * Created by patri on 2017-01-18.
 */

public class AccelerationHandler extends SensorHandler {

    AccelerationHandler(TextView OutputTextView){
        super(OutputTextView);
    }

    @Override
    protected void HandleOutput(float[] values){
        UpdateMaxValues(values);
        // Format Output...
    }


}
