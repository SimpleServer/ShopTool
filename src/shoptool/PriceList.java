/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/
package shoptool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static shoptool.Util.*;

/**
 * 
 * @author Felix Wiemuth
 */
public class PriceList implements PriceListInterface {

    private final static String extension = "pricelist";
    private LinkedList<PriceListItem> items = new LinkedList<PriceListItem>();

    /**
     * Create a new pricelist from existing pricelist
     * @param base
     * @param factor
     * @param interestBuy
     * @param interestSell 
     */
    public PriceList(PriceList base, double factor, double interestBuy, double interestSell) {
        //TODO implement
    }

    public PriceList(File file) throws Exception {
        if (!checkFile(file, extension)) {
            throw new Exception();
        }
        BufferedReader reader = getReader(file);
        String line;
        while (true) {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                err("I/O error: " + e.getMessage());
                break;
            }
            if (line == null) {
                break;
            }
            addItem(line);
        }
        closeReader(reader);
    }

    private void addItem(String raw) {
        String line = extractCode(raw);
        if (line.equals("")) {
            return; //no code in this line
        }
        try {
            addItem(new PriceListItem(line));
        } catch (Exception e) {
            //do not add item - it cannot be build from 'line'
        }
    }

    private void addItem(PriceListItem item) {
        items.add(item);
    }
    
    /**
     * Call 'revise()' on all items in the pricelist and remove uncorrectables.
     */
    public void revise() {
        info("Revising pricelist:");
        Iterator<PriceListItem> it = items.iterator();
        while(it.hasNext()) {
            PriceListItem item = it.next();
            if (!item.revise()) {
                err("Could not revise item '" + item.toStr() + "' - removing from pricelist!");
                it.remove();
            }
                
        }
        info("Finished revising pricelist.");
    }

    public static String extension() {
        return extension;
    }

    @Override
    public LinkedList<PriceListItem> items() {
        return items;
    }
}
