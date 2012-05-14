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
public interface ShopInterface {
    /**
     * Get the name of the shop type.
     * @return 
     */
    public String name();
    
    /**
     * Get the 'PriceList' containing the items available in this shop type.
     * @return 
     */
    public PriceList pricelist();
}
