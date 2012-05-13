/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/

package shoptool;

/**
 *
 * @author Felix Wiemuth
 */
public class ShopType implements ShopTypeInterface {
    private String name;
    private PriceList pricelist;
    
    public ShopType(String name) {
        this.name = name;
    }
    
    public void setPricelist(PriceList p) {
        pricelist = p;
    }
    
    public void addItem(PriceListItem item) {
        pricelist.addItem(item);
    }
    
    @Override
    public String name() {
        return name;
    }
    
    @Override
    public PriceList pricelist() {
        return pricelist;
    }
}
