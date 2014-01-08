package parser.data;

import java.awt.Point;
import parser.WzObject;

/**
 * A Helper class that generates automatically type-casted values from 
 * MapleData objects.
 * @author Rahul
 */
public class MapleDataTool {

    private static String generateErrorStr(MapleData data) {
        return "[MapleDataTool] Attempting to cast " + data.getValue().getClass() + " to a(n) ";
    }

    public static int getInt(MapleData data) {
        try {
            return (int)data.getValue();
        } catch (ClassCastException e) {
            throw new RuntimeException(generateErrorStr(data) + " int");
        } catch (Exception f) {
            throw f;
        }
    }

    public static int getInt(MapleData data, int def) {
        if (data == null)
            return def;
        try {
            return getInt(data);
        } catch (Exception e) {
            return def;
        }
    }

    public static int getInt(String prop, WzObject parent, int def) {
	return getInt(parent.getProperty(prop), def);
    }

    public static int getInt(String prop, WzObject parent) {
	return getInt(parent.getProperty(prop));
    }

    public static String getString(MapleData data) {
        try {
            return (String)data.getValue();
        }  catch (ClassCastException e) {
            throw new RuntimeException(generateErrorStr(data) + " string");
        } catch (Exception f) {
            throw f;
        }
    }

    public static String getString(MapleData data, String def) {
        if (data == null)
            return def;
        try {
            return getString(data);
        } catch (Exception e) {
            return def;
        }
    }

    public static String getString(String prop, WzObject parent) {
	return getString(parent.getProperty(prop));
    }

    public static String getString(String prop, WzObject parent, String def) {
	return getString(parent.getProperty(prop), def);
    }

    public static Point getPoint(MapleData data) {
        try {
            return (Point)data.getValue();
        } catch (ClassCastException e) {
            throw new RuntimeException(generateErrorStr(data) + " point");
        } catch (Exception f) {
            throw f;
        }
    }

    public static float getFloat(MapleData data) {
        try {
            return (float)data.getValue();
        } catch (ClassCastException e) {
            throw new RuntimeException(generateErrorStr(data) + " float");
        } catch (Exception f) {
            throw f;
        }
    }

    public static float getFloat(String prop, WzObject parent) {
	return getFloat(parent.getProperty(prop));
    }

    public static float getFloat(String prop, WzObject parent, float def) {
	if (parent.getProperty(prop) == null) {
	    return def;
	}
	return getFloat(prop, parent);
    }

    public static int getIntConvert(String prop, WzObject parent, int def) {
	if (parent.getProperty(prop) == null) {
	    return def;
	}
	return getIntConvert(parent.getProperty(prop));
    }

    public static int getIntConvert(MapleData data) {
        if (data.getValue() instanceof String) {
            return Integer.parseInt(((String)data.getValue()));
        }
        return getInt(data);
    }

    public static float getFloatConvert(MapleData data) {
	if (data.getValue() instanceof Integer) {
	    return (float)getInt(data);
	} else if (data.getValue() instanceof String) {
	    return Float.valueOf(getString(data));
	} else {
	    return getFloat(data);
	}
    }
}
