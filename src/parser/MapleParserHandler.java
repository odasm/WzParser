package parser;

import java.awt.Point;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import parser.data.MapleData;
import parser.data.MapleDataType;

/**
 * The custom implementation of the SAX DefaultHandler class.
 * This class defines the way the xml file is parsed and is responsible for
 * generating the "tree" of XML data in memory.
 * @author Rahul
 */
public class MapleParserHandler extends DefaultHandler {
    private WzObject currentObject = null;

    /**
     * The entry point of the parser when a new node is read
     * @param uri
     * @param localName The datatype of the current element
     * @param qName useless, same as localName for us
     * @param attributes The list of properties 
     * (typically follows format of attributes[n] = data name, attributes[n + 1] = data value <br>
     * i.e. attributes[0] = "maxHP", attributes[1] = 100)
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName.equals("imgdir") || localName.equals("canvas")) {
            if (currentObject == null) { // first element accessed is the main parent
		String name = attributes.getValue(0).replace(".img", "");
                currentObject = new WzObject(name);
            } else {
                String wzName = attributes.getValue(0).replace(".img", "");
                WzObjectChild child = new WzObjectChild(wzName, currentObject);
                currentObject.addChild(child);
                currentObject = child;
            }
	}
        if (attributes.getLength() > 1 && attributes.getLength() <= 3) {
            addProperty(localName, currentObject, attributes);
        } else if (attributes.getLength() > 3) { // Unexpected case
            throw new RuntimeException("Attributes.getLength() > 3!");
        }
    }

    /**
     * Adds a MapleData property to the target WzObject after determining
     * its type and value.
     * @param type the type, as read from the xml file
     * @param target the target parent for this MapleData property
     * @param a The attributes containing the name and value of the data
     */
    private void addProperty(String type, WzObject target, Attributes a) {
        String prop = a.getValue(0);
        String value = a.getValue(1);
	if (type.equals("short") || type.equals("int")) {
	    target.addProperty(prop, new MapleData(MapleDataType.INT, Integer.valueOf(value)));
	} else if (type.equals("string")) {
	    target.addProperty(prop, new MapleData(MapleDataType.STRING, value));
	} else if (type.equals("float") || type.equals("double")) {
	    target.addProperty(prop, new MapleData(MapleDataType.FLOAT, Float.valueOf(value)));
	} else if (type.equals("vector")) {
	    String value2 = a.getValue(2);
	    Point p = new Point(Integer.valueOf(value).intValue(), Integer.valueOf(value2).intValue());
            target.addProperty(prop, new MapleData(MapleDataType.POINT, p));
	} else if (!type.equals("uol") && !type.equals("canvas")) {
	    System.out.println("[ParserHandler.addProperty] Unhandled type: " + type + ": " + prop + "," + value);
	}
    }

    public void endElement(String uri, String localName, String qName) {
        if (localName.equals("imgdir") || localName.equals("canvas")) {
            if (currentObject instanceof WzObjectChild) {
                // if it's still a child, surrender control back to the parent object
                currentObject = ((WzObjectChild)currentObject).getParent();
            }
        }
    }

    public WzObject getObject() {
        return currentObject;
    }

    public void reset() {
	currentObject = null;
    }
}
