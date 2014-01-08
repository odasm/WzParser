package parser.skill;

import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import parser.WzObjectChild;
import parser.data.MapleData;
import parser.data.MapleDataTool;
import parser.data.MapleDataType;

/**
 * Simple class to evaluate a Skill.wz file's "common" child's properties.
 * @author Rahul
 */
public class SkillParser {
    // The possible variable names that have been parsed
    private static final char[] varNames = {'x'};
    
    // The possible function names that have been parsed
    private static final char[] funcNames = {'d', 'u'};
    
    // The CustomFunction implementations of each function name
    private static final CustomFunction[] funcs;
    
    static {
        // Initialize the funcs array
        funcs = new CustomFunction[funcNames.length];
        try {
            funcs[0] = new RoundDownFunction(String.valueOf(funcNames[0]));
            funcs[1] = new RoundUpFunction(String.valueOf(funcNames[1]));
        } catch (InvalidCustomFunctionException e) {
            System.err.println("Error adding custom functions:");
            e.printStackTrace();
        }
    }
    
    /**
     * Evaluates a tree of WzObjectChild properties, parsing any expressions
     * if necessary.
     * @param common the 'common' child of the skill
     * @param level the level at which to evaluate the formulas
     * @return a Map<String, MapleData> containing the property names and the value pairs.
     * The Key will be the property name, <b>in lowercase</b>.
     */
    public static Map<String, MapleData> evaluate(WzObjectChild common, int level) {
	Map<String, MapleData> ret = new HashMap<>();
	for (Map.Entry<String, MapleData> entry : common.getPropertiesMap().entrySet()) {
	    MapleData prop = entry.getValue();
	    MapleData toAdd = prop;
	    if (prop.getType() == MapleDataType.STRING) {
		String expr = MapleDataTool.getString(prop);
		if (expr.startsWith("=")) { // remove stray operators
		    expr = expr.substring(1);
		}
		if (isFormula(expr)) {
                    //Create the ExpressionBuilder
		    ExpressionBuilder b = new ExpressionBuilder(expr);
                    // Add the defined custom functions
                    for (CustomFunction cf : funcs) {
                        b.withCustomFunction(cf);
                    }
                    // Add the defined variables 
                    for (char vName : varNames) {
                        b.withVariable(String.valueOf(vName), level);
                    }
		    try {
			double val = b.build().calculate();
			if (Math.floor(val) != val) { // not an integer
			    toAdd = new MapleData(MapleDataType.FLOAT, (float)val);
			} else {
			    toAdd = new MapleData(MapleDataType.INT, (int)val);
			}
		    } catch (UnknownFunctionException | UnparsableExpressionException e) {
			System.err.println("Exception for Property: " + entry.getKey() + " for skillId: " + common.getParent().getName());
			e.printStackTrace();
			return null;
		    }
		}
	    }
	    ret.put(entry.getKey().toLowerCase(), toAdd);
	}
	return ret;
    }
    
    /**
     * Determines if the given string is a formula.
     * A String is defined as a formula if it contains any variable name
     * defined in the varNames array, or any function name defined in
     * the funcNames array.
     * @param str the string that may or may not be a formula
     * @return 
     */
    private static boolean isFormula(String str) {
	for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                continue;
            }
	    if (Arrays.binarySearch(funcNames, c) < 0 && Arrays.binarySearch(varNames, c) < 0) {
                return false;
	    }
	}
	return true;
    }
}
