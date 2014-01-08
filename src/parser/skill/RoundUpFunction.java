package parser.skill;

import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.InvalidCustomFunctionException;


/**
 * A Simple implementation of the CustomFunction class to represent a function
 * that rounds up the data passed to it through the ExpressionBuilder.
 * @author Rahul
 */
public class RoundUpFunction extends CustomFunction {
    
    /**
     * Default constructor.
     * @param name the name of this custom function
     * @throws InvalidCustomFunctionException 
     */
    public RoundUpFunction(String name) throws InvalidCustomFunctionException {
        super(name);
    }

    /**
     * Rounds up the values passed to this RoundUpFunction instance.
     * @param values the values to apply the function to.
     * @return the Math.ceil result of the sum of the values
     */
    @Override
    public double applyFunction(double[] values) {
        double ret = 0;
        for (int i = 0; i < values.length; i++) {
            ret += values[i];
        }
        return Math.ceil(ret);
    }
}
