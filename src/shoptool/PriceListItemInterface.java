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
public interface PriceListItemInterface {
    /**
     * Get the minecraft block ID of the item.
     * @return 
     */
    public String id();
    
    /**
     * Get the minecraft block name of the item.
     * @return 
     */
    public String name();
    
//    /**
//     * Get the price of the item to be displayed in the game.
//     * @return 
//     */
//    public int price();
    
    /**
     * Get the price players get for selling this item.
     * @return 
     */
    public int priceSell();
    
    /**
     * Get the price players pay for buying this item.^
     * @return 
     */
    public int priceBuy();
    
    /**
     * Get the amount of items the store tries to have.
     * @return 
     */
    public int normalStock();
    
    /**
     * Get the maximum amount of items the store can keep.
     * @return 
     */
    public int maxStock();
    
    /**
     * Get the time in seconds the current stock is changed by one item
     * towards 'normalStock()'.
     * @return 
     */
    public int stockUpdateTime();
    
    
//    /**
//     * Get the SimpleServer event scripting code to
//     * calculate stock refill per time period.
//     * This is a "function" containg one variable, X,
//     * that stands for the current amount available.
//     * @return 'null' - no code
//     */
//    public String code();
}
