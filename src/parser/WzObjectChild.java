package parser;

/**
 * Represents a subdirectory in the XML file. A WzObjectChild is simply a 
 * WzObject with a WzObject parent. 
 * The only difference between WzObjects and WzObjectChilds is that a WzObject
 * cannot have a parent, and a WzObjectChild must have a parent.
 * @author Rahul
 */
public class WzObjectChild extends WzObject {

    private WzObject parent;

    public WzObjectChild(String name, WzObject parent) {
	super(name);
        this.parent = parent;
    }

    public WzObject getParent() {
        return parent;
    }
}
