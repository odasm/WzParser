package tools;

/**
 *
 * @author Rahul
 */
public class InventoryConstants {
    //Equip Slots
    public static final short WEAPON = -11, MOUNT = -18, BOTTOM = -6, SHIELD = -10, MEDAL = -49;
    public static final short SHOE = -7, TOP = -5, EARRING = -4, HAT = -1, GLOVE = -8, CAPE = -9, FACE = -13;
    public static final short RING1 = -12, RING2 = -13, RING3 = -14, RING4 = -15;
    public static final short SADDLE = -19, PENDANT = -17, BELT = -50, PET_EQUIP = -123, AUTO_HP = -124, AUTO_MP = -125;
    public static final short CS_RING1 = RING1 - 100, CS_RING2 = RING2 - 100, CS_RING3 = RING3 - 100, CS_RING4 = RING4 - 100;
    public static final short CODEX = 55;

    //Item Flags
    public final static int LOCK = 0x01, SPIKES = 0x02, COLD = 0x04, UNTRADEABLE = 0x08, KARMA = 0x10, CHARM_EXP = 0x20, PET_COME = 0x80, UNKNOWN_SKILL = 0x100;

    //Potentials - 5,6,7 iirc for v90 (Legendary did not exist)
    public static final byte HIDDEN = 1, RARE = 5, EPIC = 6, UNIQUE = 7, LEGENDARY = 8;
    
    public static final int DURABILITY_DECREASE = 5;

    public static boolean idMatchesSlot(int itemid, short slot) {
	if (slot == WEAPON && !isWeapon(itemid) ||
	    slot == MOUNT && !isMount(itemid) ||
	    slot == BOTTOM && !isBottom(itemid) ||
	    slot == SHIELD && (!isShield(itemid) && itemid / 10000 != 135) ||
	    slot == MEDAL && itemid / 10000 != 114 ||
	    slot == SHOE && !isShoe(itemid) ||
	    (slot == TOP && !isTop(itemid) && !isOverall(itemid)) ||
	    slot == EARRING && !isEarring(itemid) ||
	    slot == HAT && !isHat(itemid) ||
	    slot == GLOVE && !isGlove(itemid) ||
	    slot == CAPE && !isCape(itemid) ||
	    slot == FACE && !isFaceAccessory(itemid) ||
	    (slot == RING1 || slot == RING2 || slot == RING3 || slot == RING4 ||
	     slot == CS_RING1 || slot == CS_RING2 || slot == CS_RING3 || slot == CS_RING4) && !isRing(itemid) ||
	    slot == SADDLE && !isSaddle(itemid) ||
	    slot == PENDANT && !isNeckAccessory(itemid) ||
	    slot == BELT && !isBelt(itemid) ||
	    (slot == PET_EQUIP || slot == AUTO_HP || slot == AUTO_MP) && !isPetEquip(itemid) ||
	    slot == CODEX && itemid != 1172000)
	    return false;
	return true;
    }

    public static boolean isRechargeable(int itemid) {
        int root = itemid / 10000;
        return root == 207 || root == 233;
    }

    public static boolean isThrowingStar(int itemId) {
        return itemId / 10000 == 207;
    }

    public static boolean isBullet(int itemId) {
        return itemId / 10000 == 233;
    }

    public static boolean isArrowForCrossBow(int itemId) {
        return itemId / 1000 == 2061;
    }

    public static boolean isArrowForBow(int itemId) {
        return itemId / 1000 == 2060;
    }

    public static boolean isOverall(int itemId) {
        return itemId / 10000 == 105;
    }

    public static boolean isHat(int itemid) {
        return itemid / 10000 == 100;
    }

    public static boolean isFaceAccessory(int itemid) {
        return itemid / 10000 == 101;
    }

    public static boolean isTop(int itemid) {
        return itemid / 10000 == 104;
    }

    public static boolean isBottom(int itemid) {
        return itemid / 10000 == 106;
    }

    public static boolean isShoe(int itemid) {
        return itemid / 10000 == 107;
    }

    public static boolean isGlove(int itemid) {
        return itemid / 10000 == 108;
    }

    public static boolean isCape(int itemid) {
        return itemid / 10000 == 110;
    }

    public static boolean isNeckAccessory(int itemid) {
        return itemid / 10000 == 112;
    }

    public static boolean isBelt(int itemid) {
	return itemid / 10000 == 113;
    }

    public static boolean isEarring(int itemid) {
        return itemid / 10000 == 103;
    }

    public static boolean isEyeAccessory(int itemid) {
        return itemid / 10000 == 102;
    }

    public static boolean isArmor(int itemid) {
        return (isTop(itemid) || isBottom(itemid) || isOverall(itemid) || isHat(itemid) || isGlove(itemid) || isShoe(itemid) || isCape(itemid));
    }

    public static boolean isAccessory(int id) {
        return (isFaceAccessory(id) || isNeckAccessory(id) || isEarring(id) || isEyeAccessory(id) || isBelt(id) || isMedal(id));
    }

    public static boolean isMedal(int id) {
	return id / 10000 == 114;
    }

    public static boolean isShield(int id) {
        return id / 10000 == 109;
    }

    public static boolean isRing(int id) {
        return id / 10000 == 111;
    }

    public static boolean isMount(int id) {
        return id / 10000 == 190 || id / 10000 == 193;
    }

    public static boolean isSaddle(int id) {
        return id / 10000 == 191;
    }

    public static boolean isTamingMob(int id) {
        return isMount(id) || isSaddle(id);
    }


    public static boolean isPetEquip(int id) {
        return id / 10000 >= 180 && id / 10000 <= 183;
    }

    public static boolean isDragon(int id) {
        return id / 10000 >= 194 && id / 10000 <= 197;
    }

    public static boolean isMechanic(int id) {
        return id / 10000 >= 161 && id / 10000 <= 165;
    }

    public static boolean isMonsterBook(int id) {
        return id == 1172000;
    }

    public static boolean isAndroid(int id) {
        return id / 10000 >= 166 && id / 10000 <= 167;
    }

    public static boolean isWeapon(int id) {
        return id / 10000 >= 130 && id / 10000 <= 153 || id / 10000 == 170;
    }

    public static boolean isFamiliar(int id) {
        return id / 10000 == 996;
    }

    public static boolean isConsume(int id) {
        return id / 10000 >= 200 && id / 10000 < 300;
    }

    public static boolean isEtc(int id) {
        return id / 10000 >= 400 && id / 10000 < 500;
    }

    public static boolean isPet(int id) {
        return id / 10000 == 500;
    }

    public static boolean isInstall(int id) {
        return id / 10000 >= 300 && id / 10000 < 400;
    }

    public static boolean isCashNotEquip(int id) {
        return id / 1000 >= 500;
    }

    public static boolean isSpecial(int id) {
        return id / 10000 >= 900 && id / 10000 < 1000;
    }

    public static boolean isFriendshipRing(int itemid) {
        switch (itemid) {
            case 1112800: // - Friendship Ring: Clover
            case 1112801: // - Friendship Ring: Flower Petal
            case 1112802: // - Friendship Ring: Star
            case 1112810: // - Christmas Night Bells
            case 1112811: // - Christmas Party
            case 1112812: // - Shared Umbrella
            case 1112816: // - Couple Snow Dome
            case 1112817: // - Psyche Special Friendship Ring
                return true;
            default:
                return false;
        }
    }

    public static boolean isCrushRing(int itemid) {
        switch (itemid) {
            case 1112001: // - Crush Ring
            case 1112002: // - Cloud Ring
            case 1112003: // - Cupid Ring
            case 1112005: // - Venus Fireworks
            case 1112006: // - Crossed Hearts
            case 1112007: // - Mistletoe Crush Ring
            case 1112012: // - Rose Crush Ring
            case 1112015: // - Illumination Couples Ring
                return true;
            default:
                return false;
        }
    }

    public static boolean isWeddingRing(int itemid) {
        switch (itemid) {
            case 1112300: // - Ring of Moon Stone: 1 Carat
            case 1112301: // - Ring of Moon Stone: 2 Carats
            case 1112302: // - Ring of Moon Stone: 3 Carats
            case 1112303: // - Ring of Shining Star: 1 Carat
            case 1112304: // - Ring of Shining Star: 2 Carats
            case 1112305: // - Ring of Shining Star: 3 Carats
            case 1112306: // - Gold Heart Ring: 1 Carat
            case 1112307: // - Gold Heart Ring: 2 Carats
            case 1112308: // - Gold Heart Ring: 3 Carats
            case 1112309: // - Ring of Silver Wing: 1 Carat
            case 1112310: // - Ring of Silver Wing: 2 Carats
            case 1112311: // - Ring of Silver Wing: 3 Carats
            case 1112315: // - 1 Carat Lovebirds Wedding Ring
            case 1112316: // - 2 Carat Lovebirds Wedding Ring
            case 1112317: // - 3 Carat Lovebirds Wedding Ring
            case 1112318: // - 1 Carat Promise Wedding Ring
            case 1112319: // - 2 Carat Promise Wedding Ring
            case 1112320: // - 3 Carat Promise Wedding Ring
            case 1112803: // - Moonstone Wedding Ring
            case 1112806: // - Star gem Wedding Ring
            case 1112807: // - Golden Heart Wedding Ring
            case 1112808: // - MapleBowl Quote Ring
            case 1112809: // - Silver Swan Wedding Ring
                return true;
            default:
                return false;
        }
    }

    public static boolean is1hWeapon(int id) {
	return id / 10000 < 140;
    }

    public static boolean is2hWeapon(int id) {
	return !is1hWeapon(id);
    }
    
    public static boolean isTablet(int id) {
        return id >= 2047000 && id <= 2047999;
    }
    
    public static boolean isHiredMerchant(int id) {
        return id / 10000 == 503;
    }
}
