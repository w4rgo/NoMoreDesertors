package NoMoreDesertors;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import net.minecraft.server.Block;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Ajax
 */
public class Scheduler {

    //private BlockList blocksToAir = new BlockList();
    private NoMoreDesertors plugin;
    private int taskId;

    public Scheduler(NoMoreDesertors plugin) {
        this.plugin = plugin;
    }

    public void killDesertor(final Desertor des) {
        int sec = Config.getIntProp("secsCloneDissapear");
        int delay = 20 * sec;
        taskId = plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {
                if (plugin.getDesertorsClones().containsKey(des.getName())) {
                    plugin.getDesertorsClones().remove(des.getName());
                    des.kill();
                    //Dbg.p("Desertor caducado");
                }
            }
        }, delay);

    }

    public void delayEndCaotic(final String name) {
        int sec = Config.getIntProp("secsEndCaotic");
        int delay = 20 * sec;
        taskId = plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {
                if (plugin.getCaoticPlayers().contains(name)) {
                    plugin.getCaoticPlayers().remove(name);
                    if(plugin.getServer().getPlayer(name)!=null)plugin.getServer().getPlayer(name).sendMessage("You are no longer caotic!");
                }
            }
        }, delay);

    }
}
