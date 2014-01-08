package parser;

import java.io.File;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import tools.InventoryConstants;
import tools.StringUtil;

/**
 * The Core class of this Wz Parsing API. The MapleDataParser is the entry-point
 * of parser requests: It internally handles all parsing when the parse() method
 * is called.
 *
 * @author Rahul
 */
public class MapleDataParser {
    // The directory on the disk which contains the XML files and folders

    private static final String wzpath = System.getProperty("wzpath") == null ? "" : System.getProperty("wzpath");
    private String path;
    private XMLReader xmlreader = null;
    private MapleParserHandler handler = new MapleParserHandler();
    private WzType type;

    /**
     * An enum which represents the different kinds of Wz Files that can be
     * parsed.
     */
    public enum WzType {

        EQUIP,
        ITEM,
        ITEM_ROOT,
        MAP,
        MAP_EFFECT,
        MOB,
        MOB_SKILL,
        NPC,
        REACTOR,
        QUEST,
        SKILL,
        ETC,
        STRING,;

        public String getPath() {
            if (this.equals(EQUIP)) {
                return "Character.wz/";
            } else if (this.equals(MOB_SKILL)) {
                return "Skill.wz/";
            } else if (this.equals(ITEM_ROOT)) {
                return "Item.wz/";
            }
            return StringUtil.makeEnumReadable(toString(), false) + ".wz/";
        }
    }

    /**
     * Constructs a new MapledataParser object with the specified WzType.
     * @param type the WzType to parse
     */
    public MapleDataParser(WzType type) {
        try {
            xmlreader = XMLReaderFactory.createXMLReader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        xmlreader.setContentHandler(handler);
        if (type != null) {
            path = wzpath + type.getPath();
        }
        this.type = type;
    }

    /**
     * An internal method to retrieve the full path of the XML file to parse.
     * It uses the friendly, numerical value of the data and the WzType to 
     * retrieve the correct path.
     * @param sourceid the friendly name of the XML data
     * @return the location on disk of the XML file to parse
     */
    private String getRelativePath(int sourceid) {
        String ret = "";
        if (type == WzType.MOB) {
            ret = StringUtil.padLeft(String.valueOf(sourceid), '0', 7);
        } else if (type == WzType.MAP) {
            int digit = sourceid / 100000000;
            ret = "Map/Map" + digit + "/" + StringUtil.padLeft(String.valueOf(sourceid), '0', 9);
        } else if (type == WzType.EQUIP) {
            ret = getEquipPath(sourceid) + '0' + sourceid;
        } else if (type == WzType.ITEM) {//Items are different, they are not separate XMLs (except for pets)
            if (InventoryConstants.isPet(sourceid)) {
                ret = getItemPath(sourceid) + sourceid;
            } else {
                ret = getItemPath(sourceid);
                ret += "0" + sourceid / 10000;
            }
        } else if (type == WzType.ITEM_ROOT) {//here we are expecting roots, i.e. 200 (200.img) etc.
            ret = getItemPath(sourceid * 10000);
            ret += "0" + sourceid;
        } else if (type == WzType.SKILL) {
            if (sourceid == 0) {//Beginner
                ret = "000";
            } else {
                ret = sourceid + "";
            }
        } else if (type == WzType.MOB_SKILL) {
            ret = "MobSkill";
        } else if (type == WzType.REACTOR || type == WzType.NPC) {
            ret = StringUtil.padLeft(String.valueOf(sourceid), '0', 7);
        }
        ret += ".img.xml";
        return ret;
    }
    
    private static String getEquipPath(int id) {
	String ret;
        if (InventoryConstants.isAccessory(id)) {
            ret = "Accessory";
        } else if (InventoryConstants.isTop(id)) {
            ret = "Coat";
        } else if (InventoryConstants.isBottom(id)) {
            ret = "Pants";
        } else if (InventoryConstants.isOverall(id)) {
            ret = "Longcoat";
        } else if (InventoryConstants.isHat(id)) {
            ret = "Cap";
        } else if (InventoryConstants.isCape(id)) {
            ret = "Cape";
        } else if (InventoryConstants.isGlove(id)) {
            ret = "Glove";
        } else if (InventoryConstants.isShoe(id)) {
            ret = "Shoes";
        } else if (InventoryConstants.isShield(id)) {
            ret = "Shield";
        } else if (InventoryConstants.isRing(id)) {
            ret = "Ring";
        } else if (InventoryConstants.isTamingMob(id)) {
            ret = "TamingMob";
        } else if (InventoryConstants.isPetEquip(id)) {
            ret = "PetEquip";
        } else if (InventoryConstants.isDragon(id)) {
            ret = "Dragon";
        } else if (InventoryConstants.isMonsterBook(id)) {
            ret = "MonsterBook";
        } else if (InventoryConstants.isMechanic(id)) {
            ret = "Mechanic";
        } else if (InventoryConstants.isAndroid(id)) {
            ret = "Android";
        } else if (InventoryConstants.isWeapon(id)) {
            ret = "Weapon";
        } else if (InventoryConstants.isFamiliar(id)) {
            ret = "Familiar";
        } else {
            throw new RuntimeException("Unable to find path for equipId: " + id);
        }
	ret += '/';
	return ret;
    }

    private static String getItemPath(int id) {
	String ret;
        if (InventoryConstants.isConsume(id)) {
            ret = "Consume";
        } else if (InventoryConstants.isInstall(id)) {
            ret = "Install";
        } else if (InventoryConstants.isEtc(id)) {
            ret = "Etc";
        } else if (InventoryConstants.isPet(id)) {
            ret = "Pet";
        } else if (InventoryConstants.isCashNotEquip(id)) {
            ret = "Cash";
        } else if (InventoryConstants.isSpecial(id)) {
            ret = "Special";
        } else {
            throw new RuntimeException("Unable to find path for itemId: " + id);
        }
	ret += '/';
	return ret;
    }

    private String getRelativePath(String source) {
        return source + ".img.xml";
    }

    /**
     * Parses the XML file containing data and returns the WzObject parent
     * of the entire tree.
     * @param dirName the name of the file
     * @return the parent of the parsed tree, or null if an exception was caught
     */
    public WzObject parse(String dirName) {
        return parse(dirName, false);
    }

    /**
     * Parses the XML file (or directory) and returns the WzObject parent of
     * the entire tree.
     * @param dirName the name of the file
     * @param directory whether this file represents a directory or not
     * @return the parent of the parsed tree, or null if an exception was caught
     */
    public WzObject parse(String dirName, boolean directory) {
        try {
            return parse(dirName, directory, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses the XML file (or directory) and returns the WzObject parent of
     * the entire tree.
     * This method is functionally equivalent to the previous parsing methods,
     * but throws any encountered exceptions, allowing for custom 
     * exception handling if desired. 
     * @param dirName the name of the file
     * @param directory whether this file represents a directory or not
     * @param throwException the exception flag
     * @return the parent of the parsed tree
     * @throws Exception if any exception is encountered during parsing
     */
    public WzObject parse(String dirName, boolean directory, boolean throwException) throws Exception {
        String relPath = path + getRelativePath(dirName);
        xmlreader.parse(relPath);
        WzObject ret = handler.getObject();
        handler.reset();
        return ret;
    }

    /**
     * Parses the XML file with the given name, in integer form.
     * For example, given 910000000, this method automatically parses the file
     * located at the path resolved by this WzType.
     * @param sourceid the id of the file
     * @return the parent of the parsed tree, or null if an exception was caught
     */
    public WzObject parse(int sourceid) {
        try {
            return parse(sourceid, true);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parses the XML file with the given name, in integer form.
     * For example, given 910000000, this method automatically parses the file
     * located at the path resolved by this WzType.
     * This method is functionally equivalent to the previous sourceid parsing
     * method, but throws any encountered exceptions, allowing for custom 
     * exception handling if desired. 
     * @param sourceid the id of the file
     * @param throwException the exception flag
     * @return the parent of the parsed tree
     * @throws Exception if any exception is encountered during parsing
     */
    public WzObject parse(int sourceid, boolean throwException) throws Exception {
        String relPath = path + getRelativePath(sourceid);
        xmlreader.parse(relPath);
        WzObject ret = handler.getObject();
        handler.reset();
        return ret;
    }

    /**
     * Returns true if the directory exists, given the dirName and the
     * WzType with which this class was instantiated.
     * @param dirName the name of the directory whose existence to check
     * @return true if the specified directory exists, false if not
     */
    public boolean directoryExists(String dirName) {
        File f = new File(path + getRelativePath(dirName));
        return f.exists();
    }
}
