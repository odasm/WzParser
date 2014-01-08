package parser.skill;

import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.InvalidCustomFunctionException;


/**
 * A Simple implementation of the CustomFunction class to represent a function
 * that rounds down the data passed to it through the ExpressionBuilder.
 * @author Rahul
 */
public class RoundDownFunction extends CustomFunction {
    
    /**
     * Default constructor.
     * @param name the name of this custom function
     * @throws InvalidCustomFunctionException 
     */
    public RoundDownFunction(String name) throws InvalidCustomFunctionException {
        super(name);
    }

    /**
     * Rounds down the values passed to this RoundDownFunction instance.
     * @param values the values to apply the function to.
     * @return the Math.floor result of the sum of the values
     */
    @Override
    public double applyFunction(double[] values) {
        double ret = 0;
        for (int i = 0; i < values.length; i++) {
            ret += values[i];
        }
        return Math.floor(ret);
    }
}
