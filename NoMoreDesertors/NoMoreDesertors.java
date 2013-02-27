package NoMoreDesertors;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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





import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author franrv
 *

 */
public class NoMoreDesertors extends JavaPlugin{
    
    private String path = "plugins/NoMoreDesertors/dropped.dat";
    
    
    
    private String name = "[NoMoreDesertors]";
    private String version = "0.1";
    public static final Logger log = Logger.getLogger("Minecraft");
    private Permission perm;
    //public final TrashBufferThread trash = new TrashBufferThread();
   // private Scheduler scheduler=new Scheduler(this);;
    
    
    private ArrayList<String> caoticPlayers = new ArrayList<String>();
    private HashMap<String,Desertor> desertorsClones = new HashMap<String,Desertor>();
    //private HashMap<String,Desertor> caoticPlayers = new HashMap<String,Desertor>();
    private ArrayList<String> playersDropped = new ArrayList<String>();
    private ArrayListFile alfDropped;
    
    private Scheduler scheduler;

    public ArrayListFile getAlfDropped() {
        return alfDropped;
    }

    public void setAlfDropped(ArrayListFile alfDropped) {
        this.alfDropped = alfDropped;
    }
    
//    public HashMap<String,Desertor> getCaoticPlayers() {
//        return caoticPlayers;
//    }

    public ArrayList<String> getCaoticPlayers() {
        return caoticPlayers;
    }

    public HashMap<String, Desertor> getDesertorsClones() {
        return desertorsClones;
    }
    
    
    public void onDisable() {
            log.info(name + " version " + version + " disabled");
    }

    private boolean setupVault() {
        
//        Plugin test = this.getServer().getPluginManager().getPlugin("Vault");
//        
//        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
//        if (permissionProvider != null) {
//            perm = permissionProvider.getProvider();
//        }
//        return (perm != null);
//        
        return true;
      }
  
//    private void setupBukkitPerm() {
//        IcePerm.setPermissions(true);
//    }
    
    //Java is like a beautifull woman. Beautifull but you have to understand it.
    @Override
    public void onEnable() {   

            try {
                Config.loadConfig(this.getDataFolder().getPath());
            } catch (IOException ex) {
                Logger.getLogger(NoMoreDesertors.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //Load list of persons to drop.
               // path = "plugins/NoMoreDesertors/dropped.dat";
                System.out.println(this.getDataFolder().getPath()+ File.separator+"dropped.dat");
                alfDropped= new ArrayListFile(this.getDataFolder().getPath()+ File.separator+"dropped.dat");
                playersDropped = alfDropped.read();
                
        //        try {
        //            Config.loadConfig(this.getDataFolder());
        //            if(setupVault()) {
        //                System.out.println("[Icelord] Valut is enabled, Using vault!");
        //                
        //                IcePerm.setPermVault(perm);
        //
        //            } else {
        //                setupBukkitPerm();
        //                System.out.println("[Icelord] You should use vault...");
        //            }
        //        } catch (IOException ex) {
        //        }
        //        }
            } catch (IOException ex) {
                Logger.getLogger(NoMoreDesertors.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        
        
        //System.out.println("QUE PASA ?");
        //Dbg.p("LO INTENTAMOS FERVIENTEMENTE!!!!!!!!!!!!!!!!!!!!!!!!!");
//        scheduler = new Scheduler(this);
        scheduler = new Scheduler(this);
        new DesertListener(this);
        //trash.start();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public ArrayList<String> getPlayersDropped() {
        return playersDropped;
    }

    public void setPlayersDropped(ArrayList<String> playersDropped) {
        this.playersDropped = playersDropped;
    }



    
    
}
