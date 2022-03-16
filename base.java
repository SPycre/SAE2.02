package TP6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class base {

    public static void main(String[] args) {
        try {
            File fichier = new File("fichier/test.txt"); //Objet manipulant un fichier
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));
            writer.write("jsp");
            writer.close(); //Il faut absolument fermer le writer après utilisation
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
