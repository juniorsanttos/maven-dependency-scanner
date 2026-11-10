package testes;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import parser.PomParser;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DemoProgrammer {

    @Test
    void testeAnnalisaXml() throws SAXException, ParserConfigurationException, IOException {
        PomParser parser = new PomParser();

        try {
            NodeList xmlContent = parser.analyzeXML(new File("src/test/java/testes/xml.xml"));
            parser.parseDependencies(xmlContent);

        }catch (ParserConfigurationException e) {
            throw new ParserConfigurationException("ERRO AO CRIAR E CONFIGURAR PARSER!!!" + e);

        } catch (SAXParseException e){
            throw new SAXParseException("O XML ESTA MALFORMADO! VERIFIQUE A ESTRUTURA DO ARQUIVO ", e.getPublicId(),e.getSystemId(), e.getLineNumber() ,e.getColumnNumber());
        } catch (IOException e) {
            throw new IOException("O ARQUIVO NÃO FOI ENCONTRADO!!!" + e.getMessage());
        } catch (SAXException e){
            throw new SAXException("ERRO AO INTERPRETAR O XML" + e.getMessage());
        }

        parser.showDependencies();

    }

}

/*
Caminho XMLS:

src/test/java/testes/

*/
