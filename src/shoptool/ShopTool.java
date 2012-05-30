/******************************************************
 * Copyright (C) 2012 Felix Wiemuth                   *
 * Licensed under the GNU GENERAL PUBLIC LICENSE      *
 * See LICENSE or http://www.gnu.org/licenses/gpl.txt *
 ******************************************************/
package shoptool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static shoptool.Util.err;
import static shoptool.Util.log;

/**
 *
 * @author Felix Wiemuth
 */
public class ShopTool {

    public static void main(String[] args) {
        run(); //DEBUG
    }
    public static final String CMD_HELP = "help";
    public static final String CMD_PRICELIST = "pl";
    public static final String CMD_PRICELIST_SYNTAX = "pl [factor] [interest buy] [interest sell] [dest] [file 1] [file 2] ...";
    public static final String CMD_PRICELIST_HELP = "Usage: ";
    public static final String CMD_SHOP = "shp";
    public static final String CMD_SHOP_SYNTAX = "";
    public static final String CMD_SHOP_HELP = "Usage: ";
    public static final String CMD_EVENT = "ev";
    public static final String CMD_EVENT_SYNTAX = "";
    public static final String CMD_EVENT_HELP = "Usage: ";

    //DEBUG
    public static void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (true) {
            try {
                line = in.readLine();
                execute(line.split(" "));
            } catch (IOException e) {
                err("I/O error: " + e.getMessage());
            }
        }

    }

    private static void execute(String[] args) {
        if (args.length == 0) {
            return;
        }
        if (args[0].equals(CMD_PRICELIST)) {
            if (args.length < 4) {
                log("Incorrect number of arguments!");
                log(CMD_PRICELIST_HELP);
            }
            try {
                cmdPricelist(parseDouble(args[1]), parseDouble(args[2]), parseDouble(args[3]), args[4], args);
            } catch (Exception e) {
            }
        } else if (args[0].equals(CMD_SHOP)) {
        } else if (args[0].equals(CMD_EVENT)) {
        } else if (args[0].equals(CMD_HELP)) {
            logCommands();
        } else {
            log("The command '" + args[0] + "' does not exist!");
        }

    }

    private static void logCommands() {
        log("Available commands:");
        log(CMD_PRICELIST_SYNTAX);
        log(CMD_SHOP_SYNTAX);
        log(CMD_EVENT_SYNTAX);

    }
    
    private static void cmdPricelist(double factor, double interestBuy, double interestSell, String dest, String[] files) {
        LinkedList<PriceList> pricelists = new LinkedList<PriceList>();
        for (int i = 5; i < files.length; i++) {
            try {
                pricelists.add(new PriceList(new File(files[i])));
            } catch (Exception e) {
                //do not add pricelist - it cannot be loaded correctly from args[i]
            }
        }
        PriceList p = new PriceList(pricelists, factor, interestBuy, interestSell);
        p.save(dest);
    }
    
    private static double parseDouble(String in) throws Exception {
        try {
            return Double.parseDouble(in);
        } catch (NumberFormatException e) {
            err("Value '" + in + "' should be a floating-point number!");
            throw new Exception();
        }
    }
}
