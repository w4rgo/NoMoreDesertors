package NoMoreDesertors;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.topcat.npclib.NPCManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Fran
 */
public class DesertListener implements Listener, CommandExecutor {
//Hacer que de vez en cuando te salga un bloque de hielo en el inventario cuando piques.
    //Usar lo del bloque para desactivar las botas.

    private NoMoreDesertors father;
    private NPCManager manager;

    public DesertListener(NoMoreDesertors firelord) {
        //if(active()) {
        father = firelord;
        father.getServer().getPluginManager().registerEvents(this, father);
        manager = new NPCManager(father);
        //father.getScheduler().restoreBlocksRepeat();
        //father.getCommand("icestep").setExecutor(this);
        //}
    }

    public void activate() {
        father.getServer().getPluginManager().registerEvents(this, father);
    }

    public boolean active() {
        //return Config.isActive();
        return true;
    }

    @EventHandler(priority = EventPriority.LOW)

    public void onPVPEvent(EntityDamageByEntityEvent event) throws InterruptedException {
        if(event.getEntity() instanceof Animals) return;
            if (event.getDamager() instanceof Player) { //If he is a player
                //

                if(Config.getBoolProp("onlyAttackers")) setCaotic((Player) event.getDamager());

                //Dbg.p("Evento de PVP");

            }

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDeathEvent(EntityDeathEvent event) throws InterruptedException, FileNotFoundException, IOException {

        String name = manager.getNPCIdFromEntity(event.getEntity());
        if (name != null) {//Si es un clon
            //if (father.getDesertorsClones().containsKey(name)) {//Si está en la lista de clones
                Desertor des = father.getDesertorsClones().get(name);
                father.getDesertorsClones().remove(name); 
                //Dropeamos su inventorio
                dropInventory(des);
            //}
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLeave(PlayerQuitEvent event) throws InterruptedException {
        createDesertor(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerConnect(PlayerJoinEvent event) throws InterruptedException, FileNotFoundException, IOException {
        //Si vuelve a entrar un caotico matamos a su clon
        String name = event.getPlayer().getName();
        
        //Si tiene un clon
        
        
        if (father.getDesertorsClones().containsKey(name)) {
            HumanEntity en = (HumanEntity) manager.getNPC(name).getBukkitEntity();
            //Lo teleportamos al clon
            event.getPlayer().teleport(en);
            //Le ponemos su vida
            event.getPlayer().setHealth(en.getHealth());
            //Eliminamos el clon
            killDesertor(name);
                

        } 
        //Si dropeo antes de entrar
        if (father.getPlayersDropped().contains(name)) {
            clearInventory(event.getPlayer());
            event.getPlayer().setHealth(0);
        }

    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void dropInventory(Desertor des) throws FileNotFoundException, IOException {
        //Drop inventory
        des.dropInventory();
        //Remove from the list.
        if(Config.getBoolProp("onlyAttackers"))father.getCaoticPlayers().remove(des.getName());
        //Add to the dropped arraylist
        father.getPlayersDropped().add(des.getName());
        //Add to the dropped file
        father.getAlfDropped().write(des.getName());
    }

    public void createDesertor(Player player) {
        //If the player is caotic;
        if (Config.getBoolProp("onlyAttackers") &&!father.getCaoticPlayers().contains(player.getName())) {
            return;
        }
            //Create the clone
            Desertor des = new Desertor(player,manager);
            des.create();
            //des.walkRandomly();
            //Add the clone to the desertorClones
            father.getDesertorsClones().put(player.getName(),des);
            //Broadcast message
            player.getServer().broadcastMessage("The player " + player.getName() + " is a desertor!");
            //Lanzar recogida de basura
            father.getScheduler().killDesertor(des);

        



    }

    public void killDesertor(String name) {
        //Mata al clon
        father.getDesertorsClones().get(name).detask();
        
        father.getDesertorsClones().get(name).kill();
        //Borra al clon de la lista
        
        father.getDesertorsClones().remove(name);
        
    }

    public void clearInventory(Player player) throws FileNotFoundException, IOException {
        //Si entra un tio que su clon dropeo le vaciamos el inventario.
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        //Lo quitamos de la lista de dropeados
        father.getPlayersDropped().remove(player.getName());
        //del from the dropped file
        father.getAlfDropped().delete(player.getName());

    }

    private void setCaotic(Player player) {
        if(father.getCaoticPlayers().contains(player.getName())) return;
        Desertor des = new Desertor(player, manager);
        //Add to caotic list
        father.getCaoticPlayers().add(des.getName());
        //Change player color name- TODO
        player.setDisplayName("&1" + player.getDisplayName());
        //Comunicate the player
        player.sendMessage("You are now caotic!");
         //Delay the end of caotic state   
        father.getScheduler().delayEndCaotic(player.getName());
    }
}
