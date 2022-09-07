package wsdpctios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Vfk {

    String outputFile;
    String charSet;

    public Vfk(String outputFile, String charSet) {
        this.outputFile = outputFile;
        this.charSet = charSet;
    }

    public void write(ArrayList<String> radkyVfk) {
        OutputStreamWriter writer = null;

        try {
            if (this.charSet.equals("WE8ISO8859P2")) {
                writer = new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("ISO-8859-2"));
            } else if (this.charSet.equals("EE8MSWIN1250")) {
                writer = new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("Windows-1250"));
            } else {
                writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
            }

            writer.write("&HVERZE;\"6.0\"\r\n");
            writer.write("&HCODEPAGE;\"" + this.charSet + "\"\r\n");
            writer.write("&BOPSUB;POSIDENT T255;ID N30;STAV_DAT N2;DATUM_VZNIKU D;DATUM_ZANIKU D;PRIZNAK_KONTEXTU N1;RIZENI_ID_VZNIKU N30;RIZENI_ID_ZANIKU N30;ID_JE_1_PARTNER_BSM N30;ID_JE_2_PARTNER_BSM N30;ID_ZDROJ N30;OPSUB_TYPE T10;CHAROS_KOD N2;ICO N8;DOPLNEK_ICO N3;NAZEV T255;NAZEV_U T255;RODNE_CISLO T10;TITUL_PRED_JMENEM T35;JMENO T100;JMENO_U T100;PRIJMENI T100;PRIJMENI_U T100;TITUL_ZA_JMENEM T10;CISLO_DOMOVNI N4;CISLO_ORIENTACNI T4;NAZEV_ULICE T48;CAST_OBCE T48;OBEC T48;OKRES T32;STAT T100;PSC N5;MESTSKA_CAST T48;CP_CE N1;DATUM_VZNIKU2 D;RIZENI_ID_VZNIKU2 N30;KOD_ADRM N9;ID_NADRIZENE_PO N30\r\n");

            for (String string : radkyVfk) {
                writer.write(string + "\r\n");
            }
            writer.write("&K");
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Vfk.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
