/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/

package shoptool;

import java.util.Iterator;

/**
 *
 * @author Felix Wiemuth
 */
public interface PriceListInterface {
    /**
     * Get an iterator over the items in the pricelist.
     * @return 
     */
    public Iterator<PriceListItem> iterator();
}
