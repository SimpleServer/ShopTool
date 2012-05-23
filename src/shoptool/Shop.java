/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/
package shoptool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static shoptool.Util.*;

/**
 *
 * @author Felix Wiemuth
 */
public class Shop implements ShopInterface {

    private final static String extension = "shop";
    private final static String NAME = "NAME";
    private final static String STARTCOORD = "STARTCOORD";
    private final static String ENDCOORD = "ENDCOORD";
    private final static String BOTCOORD = "BOTCOORD";
    private final static String VENDORNAME = "VENDORNAME";
    private final static String PRICELIST = "PRICELIST";
    private final static String DEFAULT_NAME = "Test Shop";
    private final static String DEFAULT_VENDORNAME = "Vendor";
    private String name;
    private String startCoord;
    private String endCoord;
    private String botCoord;
    private String vendorName;
    private PriceList pricelist;

    public Shop(File file) throws Exception {
        if (!checkFile(file, extension)) {
            throw new Exception();
        }
        BufferedReader reader = getReader(file);
        try {
            //set properties line by line
            setName(extract(reader.readLine(), NAME));
            setStartCoord(extractCoord(reader.readLine(), STARTCOORD));
            setEndCoord(extractCoord(reader.readLine(), ENDCOORD));
            setBotCoord(extractCoord(reader.readLine(), BOTCOORD));
            setVendorName(extract(reader.readLine(), VENDORNAME));

            File pricelistFile = new File(file.getParent(), extract(reader.readLine(), PRICELIST) + '.' + PriceList.extension());
            setPricelist(new PriceList(pricelistFile));
        } catch (IOException e) {
            err("I/O error: " + e.getMessage());
            closeReader(reader);
            throw new Exception("I/O error!");
        } catch (Exception e) {
            closeReader(reader);
            throw new Exception("Could not create shop from file '" + file.toString() + "': Could not load pricelist!");
        }
    }

    /**
     * Check this shop for correctness and set default values for
     * incorrect fields.
     * Only if building a correct shop failed, 'false' is returned.
     * @return 
     */
    public boolean revise() {
        info("Revising shop \"" + name + "\":");
        if (name == null) {
            setName(DEFAULT_NAME);
        }
        if (startCoord == null) {
            revErr(STARTCOORD);
            return false;
        }
        if (endCoord == null) {
            revErr(ENDCOORD);
            return false;
        }
        if (botCoord == null) {
            revErr(BOTCOORD);
            return false;
        }
        if (vendorName == null) {
            setVendorName(DEFAULT_VENDORNAME);
        }
        if (pricelist == null) {
            revErr("No pricelist specified - removing shop!");
            return false;
        }
        pricelist.revise();
        info("Successfully revised shop \"" + name + "\".");
        return true;
    }

//TODO if botCoord not specified: put bot in middle of area
//    private String areaMiddle(int x1, int y1, int z1, int x2, int y2, int z2) {        
//        int x = (x1 + x2) / 2;
//        int y = (y1 + y2) / 2;
//        int z;
//        if (z1 < z2) {
//            z = z1;
//        } else {
//            z = z2;
//        }
//        return Integer.toString(x) + "," + y + "," + z;
//    }
    public static LinkedList<Shop> getShops(File dir) {
        LinkedList<Shop> shops = new LinkedList<Shop>();

        File[] files = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return checkFile(file, extension);
            }
        });

        for (File file : files) {
            try {
                shops.add(new Shop(file));
            } catch (Exception e) {
                //do not add shop - it cannot be loaded correctly from 'file'
                err(e.getMessage());
            }
        }

        return shops;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String botCoord() {
        return botCoord;
    }

    @Override
    public String endCoord() {
        return endCoord;
    }

    @Override
    public String startCoord() {
        return startCoord;
    }

    @Override
    public String vendorName() {
        return vendorName;
    }

    @Override
    public PriceList pricelist() {
        return pricelist;
    }

    public static String extension() {
        return extension;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setEndCoord(String endCoord) {
        this.endCoord = endCoord;
    }

    private void setStartCoord(String startCoord) {
        this.startCoord = startCoord;
    }

    private void setBotCoord(String vendorCoord) {
        this.botCoord = vendorCoord;
    }

    private void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    private void setPricelist(PriceList p) {
        pricelist = p;
    }
}
