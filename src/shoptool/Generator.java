/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/

package shoptool;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felix Wiemuth
 */
public class Generator {
    
    private final static String extension = "pricelist";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public static LinkedList<ShopType> getShops(File dir) {
        LinkedList<ShopType> shops = new LinkedList<ShopType>();
        
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() &&
                 file.getPath().substring(
                        file.getPath().lastIndexOf('.')
                 ).equals(extension);
            }
        });
        
        for (File file : files) {
            shops.add(getShopFromFile(file));
            if (shops.peekFirst() == null) //file was no correct shop file
                shops.removeFirst();
        }
        
        //TODO implement
        return shops;
    }
    
    private static PriceList loadBasePriceList(File file) {
        BufferedReader reader = getReader(file);
        return getPriceListFromReader(reader);
    }
    
    private static ShopType getShopFromFile(File file) {
        BufferedReader reader;
        ShopType shop = null;
        try {
            reader = getReader(file);
            shop = new ShopType(reader.readLine()); //first line is name
            shop.setPricelist(getPriceListFromReader(reader)); //rest is pricelist
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shop;
    }
    
    private static PriceList getPriceListFromReader(BufferedReader reader) {
        PriceList pricelist = new PriceList();
        String line = null;
        while(true) {
            try {
                line = reader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (line == null)
                break;
            pricelist.addItem(parseLine(line));
        }
        return pricelist;
    }
    
    private static PriceList getPriceListFromFile(File file) {
        return getPriceListFromReader(getReader(file));
    }
    
    private static PriceListItem parseLine(String line) {
        PriceListItem item = new PriceListItem();
        //TODO implement
        //NOTE: code optional!
        return item;
    }
    
    private static BufferedReader getReader(File file) {
        FileReader input = null;
        try {
            input = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader bufRead = new BufferedReader(input);
        return bufRead;
    }
    
    public static void log(Object o) {
        System.out.println(o);
    }
    
    public static void warn(Object o) {
        log("[WARNING] " + o);
    }
}
