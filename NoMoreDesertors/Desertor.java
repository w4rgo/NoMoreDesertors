package NoMoreDesertors;

import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.NPC;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fran
 */
public class Desertor {

    private Player player;
    private String name;
    private Location loc;
    private PlayerInventory inv;
    private World world;
    private long time;
    private NPCManager manager;
    int task = 0;

    Desertor(Player player, NPCManager manager) {
        this.name = player.getName();
        this.loc = player.getLocation();
        this.player = player;
        this.world = player.getWorld();
        this.time = System.currentTimeMillis();
        this.manager = manager;
    }

    public void create() {

        this.inv = player.getInventory();
        
        manager.spawnHumanNPC(name, loc);
        HumanEntity en = (HumanEntity) manager.getNPC(name).getBukkitEntity();
        if (en.getHealth() > 0 && en.getHealth() < 30) {
            en.setHealth(player.getHealth());
        }

    }

    public void killAndDrop() {
        dropInventory();
        kill();


    }

    public void dropInventory() {

        for (ItemStack is : inv.getArmorContents()) {
            if (is != null && !(is.getType() == Material.AIR)) {
                //Dbg.p("objeto : "+is.getType());

                world.dropItemNaturally(getLoc(), is);
                inv.remove(is);
            }
        }
        for (ItemStack is : inv.getContents()) {
            if (is != null && !(is.getType() == Material.AIR)) {
                //Dbg.p("objeto : "+is.getType());
                world.dropItemNaturally(getLoc(), is);
                inv.remove(is);
            }
        }
    }

    public int walkRandomly() {

           int taskid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NPCManager.plugin, new Thread() {

                @Override
                public void run() {
                    NPC npc = manager.getNPC(name);
                   // while (npc != null) {
                    if (npc != null) {
                        Vector v = npc.getBukkitEntity().getVelocity();
                        int velangular = v.getBlockX() + v.getBlockY() + v.getBlockX();
                        if (velangular < 1) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Desertor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            npc.walkTo(randomizeNearLoc());
                        }
                    }
                    //}//
                        
                }
            }, 6L, 6L);
           task = taskid;
        
return taskid;
    }

    public int randomNegPos(int lenght) {
        int center = lenght / 2;

        int tirada = (int) Math.floor(Math.random() * lenght);
        if (tirada > center) {
            return tirada - center;
        } else {
            return 0 - (center - tirada);
        }

    }

    public Location randomizeNearLoc() {
        Location now = getLoc();
        int distance = 5;
        int newX = (int) (getLoc().getBlockX() + randomNegPos(distance));
        int newY = (int) (getLoc().getBlockY() );//+ randomNegPos(distance));
        int newZ = (int) (getLoc().getBlockZ() + randomNegPos(distance));
        return new Location(player.getWorld(), newX, newY, newZ);
    }

    public void kill() {
        manager.despawnById(name);
    }

    public PlayerInventory getInv() {
        return inv;
    }

    public void setInv(PlayerInventory inv) {
        this.inv = inv;
    }

    public Location getLoc() {
        Location location = this.loc;
        if (manager.getNPC(name) != null) {
            location = manager.getNPC(name).getBukkitEntity().getLocation();
        }
        return location;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public NPCManager getManager() {
        return manager;
    }

    public void setManager(NPCManager manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public static void main(String args[]) {
//        System.out.println(Desertor.randomNegPos(10));
    }

    void detask() {
        player.getServer().getScheduler().cancelTask(task);
    }
}
