package Consulta;
import dependency.Dependency;
import java.util.ArrayList;

public class Consulta {
    private ArrayList<String> vulnerabilities;

    public Consulta(){
        vulnerabilities = new ArrayList<>();
    }

    public ArrayList<String> getVulnerabilities() {
        return this.vulnerabilities;
    }

    public void consultar(ArrayList<Dependency> dependencies){

        if (dependencies.isEmpty()){
            System.out.println("LISTA VAZIA");
        } else {
            for (Dependency dependency : dependencies){
                System.out.println("==== CONSULTANDO =====");
                System.out.println(dependency.getGroupid());
                System.out.println(dependency.getArtifact());
                System.out.println(dependency.getVersion());
            }
        }
    }
}
