package tools;

/**
 * Helper class with useful String manipulation methods.
 * @author Rahul
 */
public class StringUtil {
    
    /**
     * Returns an enum name (as given by the enum.toString() method) in a 
     * readable form.
     * @param enumName enum name as given by the enum.toString() method
     * @param opcode is this for an opcode? If so, it does not add spaces.
     * @param lowercase returns the string in lower case
     * @return a readable enum name, i.e. 
     * if (opcode) CHAR_SELECTED = CharSelected<br> 
     * else CHAR_SELECTED = Char Selected
     */
    public static String makeEnumReadable(String enumName, boolean opcode, boolean lowercase) {
        StringBuilder ret = new StringBuilder();
        String[] words = enumName.split("_");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            ret.append(lowercase ? Character.toLowerCase(word.charAt(0)) : word.charAt(0));
            ret.append(word.substring(1).toLowerCase());
            if (!opcode && i != words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }
    
    public static String makeEnumReadable(String enumName, boolean opcode) {
        return makeEnumReadable(enumName, opcode, false);
    }
    
    public static String makeEnumReadable(String enumName) {
        return makeEnumReadable(enumName, false);
    }
    
    /**
     * Appends padChar to the end of the String until the String's length
     * is totalLength.
     * @param s the String to pad
     * @param padChar the with which to pad s
     * @param totalLength the final total length of the String
     * @return the padded string with length totalLength
     */
    public static String padRight(String s, char padChar, int totalLength) {
        int length = s.length();
        for (int i = length; i < totalLength; i++) {
            s += padChar;
        }
        return s;
    }
    
    /**
     * Prepends padChar to the start of the String until the String's length
     * is totalLength.
     * @param s the String to pad
     * @param padChar the with which to pad s
     * @param totalLength the final total length of the String
     * @return the padded string with length totalLength
     */
    public static String padLeft(String s, char padChar, int totalLength) {
        StringBuilder sb = new StringBuilder();
        int length = s.length();
        for (int i = 0; i < totalLength - length; i++) {
            sb.append(padChar);
        }
        sb.append(s);
        return sb.toString();
    }
}
