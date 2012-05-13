/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/

package sstool;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felix
 */
public class Generator {
    
    private final static String extension = "pricelist";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public static PriceListElement parseLine(String line) {
        PriceListElement item = new PriceListElement();
        //TODO implement
        return item;
    }
    
    public static ShopType getShopFromFile(File file) {
        ShopType shop = new ShopType();
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufRead = new BufferedReader(input);
            String line;
            while(true) {
                line = bufRead.readLine();
                if (line == null)
                    break;
                shop.addItem(parseLine(line));
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //I/O error
        }
        
        //TODO implement
        return shop;
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
    
    public static void log(Object o) {
        System.out.println(o);
    }
    
    public static void warn(Object o) {
        log("[WARNING] " + o);
    }
}
