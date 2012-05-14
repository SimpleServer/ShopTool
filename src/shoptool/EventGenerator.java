/******************************************************
 * Copyright (C) 2012 Anton Pirogov                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/
package shoptool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author Anton Pirogov
 */
public class EventGenerator {
	// From SimpleServer events implementation
	private static String escape(String str, String chars) {
		String ret = str;
		for (int i = 0; i < chars.length(); i++) {
			String c = chars.substring(i, i + 1);
			ret = ret.replaceAll(c, "\\\\" + c);
		}
		return ret;
	}

	private static String fromHash(HashMap<String, String> val) {
		String s = "{";

		if (val.size() > 0) {
			for (String key : val.keySet()) {
				String t = val.get(key);
				key = escape(key, ",:");
				t = escape(t, ",:");
				s += key + ":" + t + ",";
			}
			s = s.substring(0, s.length() - 1);
		}

		s += "}";
		return s;
	}

	private static String fromArray(ArrayList<String> val) {
		String s = "[";
		while (val.size() != 0) {
			String t = val.remove(0);
			t = escape(t, ",");
			s += t;

			if (val.size() != 0) {
				s += ",";
			}
		}
		s += "]";
		return s;
	}

	// Generation funcs

	public static String generateShops(LinkedList<Shop> shops,
			boolean withstatic) {
		String output = "";

		// Load static NPC logic code for output
		if (withstatic) {
			InputStream input = ShopGenerator.class
					.getResourceAsStream("static.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			try {
				while ((line = in.readLine()) != null)
					output += line + "\n";
			} catch (IOException e) {
				System.out.println("Error while reading!");
				return null;
			}
		}

		// Now generate
		output += "<!-- Dynamic generated logic -->\n";

		output += generateAreas(shops);
		output += "\n";
		output += generateAreaList(shops);
		output += generateAreaBots(shops);
		output += generateBotCoords(shops);
		output += "\n";
		output += generateListBuy(shops);
		output += generateListSell(shops);
		output += generateListStock(shops);
		output += generateStockConf(shops);

		// return string for output
		return output;
	}

	// ----

	private static String generateAreas(LinkedList<Shop> shops) {
		String open = "\n    <dimension name=\"Earth\">\n";
		String close = "    </dimension>\n";

		String content = "";
		// TODO: use shop area coordinates
		for (Shop s : shops) {
			String shopname = s.name();
			String startcoord = "[0,0,0]";
			String endcoord = "[0,0,0]";

			content += "        <area name=\"" + shopname;
			content += "\" start=\"" + startcoord + "\" end=\"" + endcoord
					+ "\" ";
			content += "onenter=\"storeEnter\" onleave=\"storeLeave\" />\n";
		}

		return open + content + close;
	}

	private static String generateAreaList(LinkedList<Shop> shops) {
		final String ATTNAME = "store_areas";
		String open = "    <event name=\"" + ATTNAME + "\" value=\"";
		String close = "\" />\n";

		ArrayList<String> arr = new ArrayList<String>();
		for (Shop s : shops) {
			arr.add(s.name());
		}

		return open + fromArray(arr) + close;
	}

	private static String generateAreaBots(LinkedList<Shop> shops) {
		final String ATTNAME = "store_areaBots";
		String open = "    <event name=\"" + ATTNAME + "\" value=\"";
		String close = "\" />\n";

		// TODO: use bot name instead of shop name
		HashMap<String, String> hash = new HashMap<String, String>();
		for (Shop s : shops) {
			String shopname = s.name();
			String botname = s.name();
			hash.put(shopname, botname);
		}

		return open + fromHash(hash) + close;
	}

	private static String generateBotCoords(LinkedList<Shop> shops) {
		final String ATTNAME = "store_botCoords";
		String open = "    <event name=\"" + ATTNAME + "\" value=\"";
		String close = "\" />\n";

		// TODO: use bot name instead of shop name, use given coordinate
		HashMap<String, String> hash = new HashMap<String, String>();
		for (Shop s : shops) {
			String botname = s.name();
			String coordinate = "[0,0,0]";
			hash.put(botname, coordinate);
		}

		return open + fromHash(hash) + close;
	}

	private static String generateListBuy(LinkedList<Shop> shops) {
		final String ATTNAME = "store_listBuy";
		String open = "    <event name=\"" + ATTNAME + "\" value=\"";
		String close = "\" />\n";

		HashMap<String, String> hash = new HashMap<String, String>();
		for (Shop s : shops) {
			HashMap<String, String> entry = new HashMap<String, String>();
			// TODO: skip items not to be bought
			for (PriceListItem i : s.pricelist().items) {
				entry.put(String.valueOf(i.id()), String.valueOf(i.priceBuy()));
			}
			hash.put(s.name(), fromHash(entry));
		}

		return open + fromHash(hash) + close;
	}

	private static String generateListSell(LinkedList<Shop> shops) {
		final String ATTNAME = "store_listSell";
		String open = "    <event name=\"" + ATTNAME + "\" value=\"";
		String close = "\" />\n";

		HashMap<String, String> hash = new HashMap<String, String>();
		for (Shop s : shops) {
			HashMap<String, String> entry = new HashMap<String, String>();
			// TODO: skip items not to be sold
			for (PriceListItem i : s.pricelist().items) {
				entry.put(String.valueOf(i.id()), String.valueOf(i.priceSell()));
			}
			hash.put(s.name(), fromHash(entry));
		}

		return open + fromHash(hash) + close;
	}

	private static String generateListStock(LinkedList<Shop> shops) {
		final String ATTNAME = "store_listStock";
		String open = "    <event name=\"" + ATTNAME + "\" value=\"";
		String close = "\" />\n";

		HashMap<String, String> hash = new HashMap<String, String>();
		for (Shop s : shops) {
			HashMap<String, String> entry = new HashMap<String, String>();
			for (PriceListItem i : s.pricelist().items) {
				entry.put(String.valueOf(i.id()),
						String.valueOf(i.normalStock()));
			}
			hash.put(s.name(), fromHash(entry));
		}

		return open + fromHash(hash) + close;
	}

	private static String generateStockConf(LinkedList<Shop> shops) {
		final String ATTNAME = "store_stockConf";
		String open = "    <event name=\"" + ATTNAME + "\" value=\"";
		String close = "\" />\n";

		HashMap<String, String> hash = new HashMap<String, String>();
		for (Shop s : shops) {
			HashMap<String, String> entry = new HashMap<String, String>();
			for (PriceListItem i : s.pricelist().items) {
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(String.valueOf(i.normalStock()));
				arr.add(String.valueOf(i.maxStock()));
				arr.add(String.valueOf(i.stockUpdateTime() * 1000)); // convert
																		// to ms
				entry.put(String.valueOf(i.id()), fromArray(arr));
			}
			hash.put(s.name(), fromHash(entry));
		}

		return open + fromHash(hash) + close;
	}
}
