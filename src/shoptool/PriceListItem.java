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
public class PriceListElement {
    private String id; //minecraft block id
    private String name; //minecraft block name
    private int price; //pricein relation to base value 10
    private String code; //SimpleServer event scripting code to calculate stock refill per time period
    
    public String id() {
        return id;
    }
    
    public String name() {
        return name;
    }
    
    public int price() {
        return price;
    }
    
    public String code() {
        return code;
    }
    
}
