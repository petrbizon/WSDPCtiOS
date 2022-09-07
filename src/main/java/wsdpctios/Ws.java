package wsdpctios;

import com.sun.xml.wss.XWSSConstants;
import cz.cuzk.katastr.ctios.types.v2.CtiOsRequestType;
import cz.cuzk.katastr.ctios.types.v2.CtiOsResponseType;
import cz.cuzk.katastr.ctios.types.v2.OSListType;
import cz.cuzk.katastr.ctios.types.v2.OSTypeResult;
import cz.cuzk.katastr.ctios.v2.Ctios;
import cz.cuzk.katastr.ctios.v2.Ctios_Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;



public class Ws {

    public ArrayList<String> getRadkyVfk(List<String> posidents, String endpoint, String userName, String password) {

        ArrayList<String> radkyVfk = new ArrayList<>();

        CtiOsRequestType input = new CtiOsRequestType();
        int i = 0;
        int rozdelenoDo = 0;
        int uspesneStazeno = 0;
        int neplatnyIdentifikator = 0;
        int opravnenySubjektNeexistuje = 0;
        int expirovanyIdentifikator = 0;

        String string;
        for (Iterator<String> it = posidents.iterator(); it.hasNext();) {
            string = it.next();
            input.getPOSIdent().add(string);
            i = i + 1;

            if (i == 100 || !it.hasNext()) {
                i = 0;
                rozdelenoDo = rozdelenoDo + 1;
                CtiOsResponseType response = ctios(input, endpoint, userName, password);


                int posidentSize = input.getPOSIdent().size();
                for (int j = posidentSize - 1; j >= 0; j = j - 1) {
                    input.getPOSIdent().remove(j);
                }

                OSListType osList = response.getOsList();
                for (OSTypeResult os : osList.getOs()) {
                    if (os.getChybaPOSIdent() == null) {
                        uspesneStazeno = uspesneStazeno + 1;

                        radkyVfk.add("&DOPSUB;"
                                + "\"" + os.getPOSIdent() + "\";"
                                + os.getOsId() + ";"
                                + os.getOsDetail().get(0).getStavDat() + ";"
                                + "\"" + nvl(os.getOsDetail().get(0).getDatumVzniku()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getDatumZaniku()) + "\";"
                                + os.getOsDetail().get(0).getPriznakKontext() + ";"
                                + nvl(os.getOsDetail().get(0).getRizeniIdVzniku()) + ";"
                                + nvl(os.getOsDetail().get(0).getRizeniIdZaniku()) + ";"
                                + nvl(os.getOsDetail().get(0).getPartnerBsm1()) + ";"
                                + nvl(os.getOsDetail().get(0).getPartnerBsm2()) + ";"
                                + nvl(os.getOsDetail().get(0).getIdZdroj()) + ";"
                                + "\"" + os.getOsDetail().get(0).getOpsubType() + "\";"
                                + os.getOsDetail().get(0).getCharOsType() + ";"
                                + nvl(os.getOsDetail().get(0).getIco()) + ";"
                                + nvl(os.getOsDetail().get(0).getDoplnekIco()) + ";"
                                + "\"" + nvl(os.getOsDetail().get(0).getNazev()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getNazevU()) + "\";"
                                + nvl(os.getOsDetail().get(0).getRodneCislo()) + ";"
                                + "\"" + nvl(os.getOsDetail().get(0).getTitulPredJmenem()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getJmeno()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getJmenoU()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getPrijmeni()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getPrijmeniU()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getTitulZaJmenem()) + "\";"
                                + nvl(os.getOsDetail().get(0).getCisloDomovni()) + ";"
                                + "\"" + nvl(os.getOsDetail().get(0).getCisloOrientacni()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getNazevUlice()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getCastObce()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getObec()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getOkres()) + "\";"
                                + "\"" + nvl(os.getOsDetail().get(0).getStat()) + "\";"
                                + nvl(os.getOsDetail().get(0).getPsc()) + ";"
                                + "\"" + nvl(os.getOsDetail().get(0).getMestskaCast()) + "\";"
                                + nvl(os.getOsDetail().get(0).getCpCe()) + ";"
                                + "\"" + nvl(os.getOsDetail().get(0).getDatumVzniku2()) + "\";"
                                + nvl(os.getOsDetail().get(0).getRizeniIdVzniku2()) + ";"
                                + nvl(os.getOsDetail().get(0).getKodAdresnihoMista()) + ";"
                                + nvl(os.getOsDetail().get(0).getIdNadrizenePravnickeOsoby())
                        );
                    } else {
                        if (os.getChybaPOSIdent().toString().equals("NEPLATNY_IDENTIFIKATOR")) {
                            neplatnyIdentifikator = neplatnyIdentifikator + 1;
                        } else if (os.getChybaPOSIdent().toString().equals("OPRAVNENY_SUBJEKT_NEEXISTUJE")) {
                            opravnenySubjektNeexistuje = opravnenySubjektNeexistuje + 1;
                        } else if (os.getChybaPOSIdent().toString().equals("EXPIROVANY_IDENTIFIKATOR")) {
                            expirovanyIdentifikator = expirovanyIdentifikator + 1;
                        }
                    }
                }
            }
        }
        Log.addToString("rozděleno do: " + rozdelenoDo + " zavolání služby ctiOS\n");
        
        if (uspesneStazeno!=0){
            Log.addToString("Úspěšně staženo: " + uspesneStazeno + "\n");
        }
        
        if (neplatnyIdentifikator!=0){
            Log.addToString("neplatný identifikátor: " + neplatnyIdentifikator + "\n");
        }
        
        if (opravnenySubjektNeexistuje!=0){
            Log.addToString("orpávněný subjekt neexistuje: " + opravnenySubjektNeexistuje + "\n");
        }
        
        if (expirovanyIdentifikator!=0){
        Log.addToString( "expirovaný identifikator: " + expirovanyIdentifikator +   "\n");
        }
        
        return radkyVfk;

    }

    private CtiOsResponseType ctios(CtiOsRequestType input, String endpoint, String userName, String password) {
        Ctios_Service service = new Ctios_Service();
        Ctios port = service.getCtiosSoapHttpPort();
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();

        String wsdl = endpoint;
        String user = userName;
        String pass = password;

        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdl);
        requestContext.put(XWSSConstants.USERNAME_PROPERTY, user);

        requestContext.put(XWSSConstants.USERNAME_PROPERTY, user);
        requestContext.put(XWSSConstants.PASSWORD_PROPERTY, pass);
        return port.ctios(input);
    }

    public static String nvl(String value) {
        if (value == null) {
            return "";
        }

        return value;
    }

    public static String nvl(BigInteger value) {
        if (value == null) {
            return "";
        }

        return value.toString();
    }

    public static String nvl(Integer value) {
        if (value == null) {
            return "";
        }

        return value.toString();
    }

    public static String nvl(XMLGregorianCalendar value) {

        if (value == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            String date = sdf.format(value.toGregorianCalendar().getTime());
            return date;
        }

    }

}
