/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/
package shoptool;

import java.io.File;
import java.util.LinkedList;
import static shoptool.Util.getShops;
import static shoptool.Util.log;

/**
 *
 * @author felix
 */
public class Main {

    public static void main(String[] args) {
        //test parsing
    	/*
         Shop shop = getShopFromFile(new File("ShopListExample2"));
         log(extract("NAME=Test SELL=5 PRICE=120", PRICE));
         log(extract("NAME=Test SELL=5 PRICE=120", NAME));
         log(extract("NAME=Test SELL=5 PRICE=120", "SELL"));
         */

//    	//Test generation
//		LinkedList<Shop> shops = getShops(new File("./shops"));
//		String output = EventGenerator.generateShops(shops, false);
//		if (output != null)
//			System.out.println(output);
//		else
//			System.out.println("Something went wrong :'(");

//        PriceListInterface pl = new PriceList(new File("PricelistExample3.pricelist"));
//        for (Iterator item = pl.itest(); item.hasNext();) {
//            PriceListItem next = (PriceListItem) item.next();
//            log(next.toStr());
//            
//        }
//        log("-----------");

        //test id with ":"
        int val = Integer.parseInt("0045:03".substring("0045:03".indexOf(':') + 1, "0045:03".length()));
        log(val);

        log("-----\nTest shops:");
        LinkedList<ShopInterface> shops = (LinkedList<ShopInterface>) getShops(new File("."));
        log("Amount of shops: " + shops.size());

        for (ShopInterface shop : shops) {
            log("---> SHOP:");
            log(shop.name());
            log(shop.startCoord());
            log(shop.endCoord());
            log(shop.botCoord());
            log(shop.vendorName());
            //pricelist
            for (PriceListItemInterface item : shop.pricelist().items()) {
                log("Item: " + item.id() + item.name() + " isBuy:" + item.isBuy() + " isSell:" + item.isSell());
            }
        }

    }
}
