package parser.data;

/**
 * A MapleData is the lowest form of data in the parsed tree.
 * It has simply a value and a type (@link MapleDataType), and represents
 * a singular form of data.
 * @author Rahul
 */
public class MapleData {
    private Object value;
    private MapleDataType type;

    public MapleData(MapleDataType type, Object value) {
	this.type = type;
	this.value = value;
    }

    public Object getValue() {
	return value;
    }

    public MapleDataType getType() {
	return type;
    }
}
