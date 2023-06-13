import java.util.*;

public class TestSimulation{
    public static void main(String[] arg){

        Simulation s = new Simulation(10, 10, 3);
        Fermier f = (Fermier)s.getListeAgent().get(0);

		s.simuler(f);
    }

}
