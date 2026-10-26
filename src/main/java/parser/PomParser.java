package parser;
import dependency.Dependency;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PomParser {
    private ArrayList<Dependency> dependencies;

    public PomParser(){
        this.dependencies = new ArrayList<>();
    }

    public List<Dependency> getDependencies() {
        return Collections.unmodifiableList(this.dependencies);
    }

    private Document takeContent(File xml) throws ParserConfigurationException, SAXException, IOException {

        //Preparando maquina para leitura
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //Eliminando XXE e configurando parser
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); //Desabilita DOCTYPE
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false); //Desabilita entidades externas
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false); //Desabilita entidades externas parametizadas
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD,""); //Bloqueando acesso externo ao DTD
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); //Bloqueando acesso externo a schemas

        //Criando Parser
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Previnindo Fatal Error

        builder.setErrorHandler(new DefaultHandler(){

            @Override
            public void fatalError(SAXParseException e) throws SAXException{
                throw e;
            }
        });

        //taking content of XML
        return builder.parse(xml);
    }

    public NodeList analyzeXML(File file) throws ParserConfigurationException, SAXException, IOException {

        Document xml = takeContent(file);

        return xml.getElementsByTagName("dependency");
    }

    public void parseDependencies(NodeList list) {

        for (int i = 0; i < list.getLength();i++){
            Dependency dependency = new Dependency();

            Element dependencyNode = (Element) list.item(i);

            NodeList nodeGroupId = dependencyNode.getElementsByTagName("groupId");
            NodeList nodeArtifactId = dependencyNode.getElementsByTagName("artifactId");
            NodeList nodeVersion = dependencyNode.getElementsByTagName("version");

            if (nodeGroupId.getLength() <= 0){
                System.err.println("Dependencia Não Possui Tags Necessarias Para Analise");
                continue;
            } else if (nodeArtifactId.getLength() <= 0) {
                System.err.println("Dependencia Não Possui Tags Necessarias para Analise");
                continue;
            } else {
                String groupId = dependencyNode.getElementsByTagName("groupId").item(0).getTextContent();
                String artifactId = dependencyNode.getElementsByTagName("artifactId").item(0).getTextContent();
                dependency.setGroupid(groupId);
                dependency.setArtifact(artifactId);

                if (nodeVersion.getLength() <= 0){
                    System.err.println("NÃO FOI POSSIVEL RESOLVER A VERSÃO DA " + (i + 1) + " DEPENDENCIA");

                } else {
                    String version = dependencyNode.getElementsByTagName("version").item(0).getTextContent();
                    dependency.setVersion(version);
                }

                this.dependencies.add(dependency);
            }



        }
    }

    public void showDependencies(){

        for (Dependency D : this.dependencies){

            System.out.println("====================================");

            System.out.println("Group ID: " + D.getGroupid());
            System.out.println("Version: " + D.getVersion());
            System.out.println("Artifact: " + D.getArtifact());

            System.out.println("====================================");
        }

    }

}