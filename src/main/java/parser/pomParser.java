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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class pomParser {
    private ArrayList<Dependency> dependencies;

    public pomParser(){
        this.dependencies = new ArrayList<>();
    }

    public ArrayList<Dependency> getDependencies() {
        return this.dependencies;
    }

    public void analyzerXML(File file)  {

        try {
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

            //taking content of XML

            Document document = builder.parse(file);

            NodeList dependencyList = document.getElementsByTagName("dependency");

            for (int i = 0; i < dependencyList.getLength();i++){
                Dependency dependency = new Dependency();

                Element dependencyNode = (Element) dependencyList.item(i);

                String groupID = dependencyNode.getElementsByTagName("groupId").item(0).getTextContent();
                String artifactID = dependencyNode.getElementsByTagName("artifactId").item(0).getTextContent();
                String version = dependencyNode.getElementsByTagName("version").item(0).getTextContent();

                dependency.setGroupid(groupID);
                dependency.setArtifact(artifactID);
                dependency.setVersion(version);

                this.dependencies.add(dependency);
            }

        } catch (Exception  e) {
            System.err.println("ERRO AO LER XML!!! ");
            throw new RuntimeException("Não foi possível preparar o parser.",e);
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
