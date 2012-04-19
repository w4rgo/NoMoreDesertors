/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NoMoreDesertors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Fran
 */
public class ArrayListFile {

    private String path;

    public ArrayListFile(String path) {
        this.path = path;
    }

    public ArrayList<String> read() throws IOException {
         ArrayList<String> text = new ArrayList<String>();
        try {
           
            Scanner scanner = new Scanner(new FileInputStream(path));
            try {
                while (scanner.hasNextLine()) {
                    text.add(scanner.nextLine());
                }
            } finally {
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            File f = new File(path);
            f.createNewFile();
           // fi = new FileInputStream(path);
            //return new ArrayList<String>();
        }
        return text;
    }

    public void write(String string) throws IOException {
            FileWriter writer;
        try {
             writer = new FileWriter(new File(path), true);
            
        } catch(FileNotFoundException e) {
            File f = new File(path);
            f.createNewFile();
            writer = new FileWriter(f);
           // fi = new FileInputStream(path);
        }
            
            writer.append(string + "\n");
            writer.flush();
            writer.close();
        
    }

    public void delete(String string) throws FileNotFoundException, IOException {
        ArrayList<String> text = new ArrayList<String>();
        Scanner scanner = new Scanner(new FileInputStream(path));
        try {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                if (!name.equals(string)) {
                    text.add(name);
                    //break;
                }
            }
        } finally {
            scanner.close();
        }

        deleteAll();
        writeAll(text);

    }

    public void deleteAll() throws IOException {
        FileWriter writer = new FileWriter(new File(path));
        writer.flush();
        writer.close();
    }

    public void writeAll(ArrayList<String> names) throws IOException {
        FileWriter writer = new FileWriter(new File(path), true);
        for (String name : names) {
            writer.append(name + "\n");
        }
        writer.flush();
        writer.close();

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ArrayListFile alf = new ArrayListFile("./dropped");
        //alf.write("jono4");
        alf.delete("jono4");
        System.out.println(alf.read());
    }
}
