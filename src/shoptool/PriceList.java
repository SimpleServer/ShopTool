/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/

package sstool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author felix
 */
public class PriceList implements PriceListInterface {
    LinkedList<PriceListElement> items;

    @Override
    public HashMap<Integer, Integer> getPrices() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Iterator iterator() {
        return items.iterator();
    }

    void addItem(PriceListElement item) {
        items.add(item);
    }
    
    /**
     * Get the price lists for shops //TODO wrap in Type "ShopType"
     * @return 
     */
    
    
}
