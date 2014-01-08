package parser.data;

import tools.StringUtil;

/**
 *
 * @author Rahul
 */
public enum MapleDataType {
    INT,
    STRING,
    FLOAT,
    POINT;

    public static String[] getNames() {
	String[] ret = new String[4];
	for (MapleDataType t : values()) {
	    ret[t.ordinal()] = StringUtil.makeEnumReadable(t.toString(), false, true);
	}
	return ret;
    }
}
