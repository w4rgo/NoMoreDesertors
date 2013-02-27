/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NoMoreDesertors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.util.HashMap;

/**
 * FireLord 0.4
 * Copyright (C) 2011 W4rGo , Francisco Ruiz Valdes <franrv@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class Config {

    // public static PermissionHandler Permissions;
    private static String configPath = "plugins/NoMoreDesertors/config.properties";
    private static String configFile = "config.properties";
    private static Properties config;
    private static FileInputStream fi;
    private static boolean changed = false;
    private static HashMap<String, Object> props = new HashMap<String, Object>();

    public static void setProp(String name, String prop) {
        FileOutputStream fo = null;
        try {
            config.setProperty(name, String.valueOf(prop));
            Config.props.put(name, String.valueOf(prop));
            fo = new FileOutputStream(configPath);
            config.store(fo, configPath);
            fo.close();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getProp(String name) {
        return config.getProperty(name);
    }

    public static int getIntProp(String name ) {
        return Integer.valueOf(config.getProperty(name));
    }
    public static boolean getBoolProp(String value) {
        return boolValue(config.getProperty(value));
    }
    public static void defaultPropValue(String name, String prop) {
        if (config.getProperty(name) != null) {
            Config.props.put(name, config.getProperty(name));
        } else {
            config.setProperty(name, String.valueOf(prop));
            changed = true;
        }
    }

    private static boolean boolValue(String value) {
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            return false;
        }
    }

    public static void loadConfig(String filePath) throws IOException {

        configPath = filePath + File.separator + configFile;
        config = new Properties();
        try {
            fi = new FileInputStream(Config.configPath);

            config.load(fi);

        } catch (FileNotFoundException e) {
            File f = new File(configPath);
            f.createNewFile();
            fi = new FileInputStream(Config.configPath);
        }

        defaultPropValue("onlyAttackers","false");
        defaultPropValue("secsEndCaotic","60");
        //defaultPropValue("walkRandomly","false");
        //defaultPropValue("clonAttack","false");
        defaultPropValue("secsCloneDissapear","30");
        
        if (changed) {
            FileOutputStream fo = new FileOutputStream(configPath);
            config.store(fo, configPath);
            fo.close();
        }

    }

//    public static void initOnlyAttackers() {
//        if (config.getProperty("onlyAttackers") != null) {
//            onlyAttackers = boolValue(config.getProperty("onlyAttackers"));
//        } else {
//            config.setProperty("onlyAttackers", String.valueOf(onlyAttackers));
//            changed = true;
//        }
//    }

//    public static void setFireDuration(int value) {
//        FileOutputStream fo = null;
//        try {
//            fireDuration = value * 20; //Translation into bukkit ticks 1 s -> 1/20 ticks
//            config.setProperty("fireduration", String.valueOf(value));
//            fo = new FileOutputStream(configPath);
//            config.store(fo, configPath);
//            fo.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static void main(String [] args) throws IOException {
       // File fp = new File("manifiest.mf");
        //Config.loadConfig(".");
    }
}
