/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/

package sstool;

/**
 *
 * @author felix
 */
public class ShopType implements ShopTypeInterface {
    private String name;
    private PriceList pricelist;
    
    public void addItem(PriceListElement item) {
        pricelist.addItem(item);
    }
}
