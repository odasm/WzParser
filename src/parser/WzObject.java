package parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import parser.data.MapleData;

/**
 * The backbone of the "Tree" structure. The WzObject represents a 
 * subdirectory of data from the XML. All WzObjects are capable of having
 * child subdirectories (collections of WzObjectChild) and 
 * properties (collections of MapleData).
 * These fields are internally stored in HashMaps, so the preservation of 
 * order is NOT GUARANTEED.
 * Generally there is little importance in order, but they may be changed to
 * LinkedHashMap if necessary.
 * @author Rahul
 */
public class WzObject {
    protected Map<String, MapleData> properties = new HashMap<>();
    protected Map<String, WzObjectChild> children = new HashMap<>();
    protected String name;

    public WzObject(String name) {
	this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void addProperty(String property, MapleData value) {
        properties.put(property, value);
    }

    public MapleData getProperty(String property) {
        return properties.get(property);
    }

    public Map<String, MapleData> getPropertiesMap() {
        return properties;
    }

    public Collection<MapleData> getProperties() {
        return properties.values();
    }

    protected void addChild(WzObjectChild child) {
        children.put(child.getName(), child);
    }

    public WzObjectChild getChild(String name) {
        return children.get(name);
    }

    public Map<String, WzObjectChild> getChildrenMap() {
        return children;
    }

    public Collection<WzObjectChild> getChildren() {
        return children.values();
    }

    public void removeProperty(String propName) {
	properties.remove(propName);
    }

    public void removeChild(String childName) {
	children.remove(childName);
    }
}
