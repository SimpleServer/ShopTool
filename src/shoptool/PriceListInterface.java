/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/
package shoptool;

import java.util.LinkedList;

/**
 *
 * @author Felix Wiemuth
 */
public interface PriceListInterface {

    /**
     * Get a list of the items in the pricelist.
     * @return 
     */
    public LinkedList<? extends PriceListItemInterface> items();
}
