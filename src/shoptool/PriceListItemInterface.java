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

    /**
     * Return 'true' if this item is available for buying.
     * @return 
     */
    public boolean isBuy();

    /**
     * Return 'true' if this item is available for selling.
     * @return 
     */
    public boolean isSell();
}
