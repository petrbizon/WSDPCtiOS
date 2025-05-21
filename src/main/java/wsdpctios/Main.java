package wsdpctios;

import gui.JFrame;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStoreType", "WINDOWS-ROOT");

        JFrame jframe = new JFrame();
        jframe.setVisible(true);

    }

    public static void run(String inputFile, String OutputFile, String charSet, String endpoint, String userName, String password)  {
        Input input = new Input(inputFile);
        List<String> posidents = input.getPosidents();

        Ws ws = new Ws();
        com.sun.org.apache.xml.internal.security.Init.init();
        ArrayList<String> radkyVfk = ws.getRadkyVfk(posidents, endpoint, userName, password);
        Vfk vfk = new Vfk(OutputFile, charSet);
        vfk.write(radkyVfk);

    }

    public static String getJarLocation() {

        try {
            String string = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            //System.out.println(string);
            String substring = string.substring(string.lastIndexOf('.') + 1);

            if (substring.equals("jar")) {
                return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            } else {
                return Paths.get("").toAbsolutePath().toString(); //vrátí pracovní adresář
            }

        } catch (URISyntaxException ex) {
            System.err.println(ex.getMessage());
        }
        return null;

    }

}
