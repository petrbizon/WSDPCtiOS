package wsdpctios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Input {

    private String inputFile;
    private int pocetPosidentu = 0;
    private int pocetJedinecnychPosidentu = 0;

    public Input(String inputFile) {
        this.inputFile = inputFile;
    }

    public List<String> getPosidents() {
        String radek;
        Scanner s = null;
        try {
            s = new Scanner(new File(this.inputFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> list = new ArrayList<String>();

        while (s.hasNextLine()) {
            radek = s.nextLine();
            this.pocetPosidentu = this.pocetPosidentu + 1;
            if (!list.contains(radek)) {
                list.add(radek);
                this.pocetJedinecnychPosidentu = this.pocetJedinecnychPosidentu + 1;
            }
        }
        s.close();

        Log.addToString("Celkem zadáno POSIDENT: " + this.pocetPosidentu + "\n"
                + "Celkem zavoláno POSIDENT: " + this.pocetJedinecnychPosidentu + "\n");
        return list;
    }



}
