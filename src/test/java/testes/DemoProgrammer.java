package testes;
import parser.pomParser;
import Consulta.Consulta;
import org.junit.jupiter.api.Test;

import java.io.File;

public class DemoProgrammer {

    @Test
    void testeAnnalisaXml(){
        pomParser parser = new pomParser();
        Consulta consulta = new Consulta();


        parser.analyzerXML(new File("xml.xml"));

        parser.showDependencies();




    }

}
