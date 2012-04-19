/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NoMoreDesertors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;

public class YamlLoader {

    private HashMap<String, Object> yamlFile = new HashMap<String, Object>();
    Yaml yaml;

    YamlLoader(String path) throws FileNotFoundException {
        yaml = new Yaml();
        InputStream input = new FileInputStream(new File(path));
        yamlFile = (HashMap<String, Object>) yaml.load(input);
    }

    public ArrayList<String> getDroppedPlayers() {
        return new ArrayList(yamlFile.keySet());
    }
    
    public void addDroppedPlayer(String name) {
        yamlFile.put(name, name);
    }
    
    public void delDroppedPlayer(String name) {
        yamlFile.remove(name);
    }

    public Yaml getYaml() {
        return yaml;
    }

    public void setYaml(Yaml yaml) {
        this.yaml = yaml;
    }
    
//    public static void main(String [] args) throws FileNotFoundException {
//        
//        YamlLoader yl = new YamlLoader("./NoMoreDesertors/dropped.yml");
//        yl.addDroppedPlayer("pepe");
//        yl.addDroppedPlayer("Juan");
//        yl.getYaml().
//        System.out.println(yl.getDroppedPlayers());
//    }
}
