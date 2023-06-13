public class Vache extends Ressource implements PeutMourir{

    /*** Attributs ***/
    private int masse;
    private int age;
    private boolean vivant;
    private int[] tab_dir;
    private static final float P_CH_DIR = (float)0.43;
    private static final float P_REPR = (float)0.02;


    /*** Constructeurs ***/
    public Vache(){
        super("🐄", 1); //🐂, 𓃒
        masse =(int)(Math.random()*(50-15+1)+10);
        age =(int)(Math.random()*(80-3+1)+3);
        vivant = true;
        tab_dir = new int[2];
        tab_dir[0] = (int)(Math.random()*(2-0+1)+0)-1;
        tab_dir[1] = (int)(Math.random()*(2-0+1)+0)-1;
    }

    public Vache(int age, int masse){
        super("🐄", 1); //🐂, 𓃒
        this.masse = masse;
        this.age = age;
        vivant = true;
        tab_dir = new int[2];
        tab_dir[0] = (int)(Math.random()*(2-0+1)+0)-1;
        tab_dir[1] = (int)(Math.random()*(2-0+1)+0)-1;
    }
   

    /*** Méthodes ***/
    public void mourir(){
        if(Math.random()<=age/100){
        	System.out.println("la vache "+this+" est morte.");
        	vivant=false;
        }
        return ;
    }

    public void ageplus(){
        age += 1;
        masse += 10;
    }

    public boolean seReproduire(Simulation s){
        if(Math.random()<P_REPR){
            int x = getX()+1;
            int y = getY()+1;
            if( ((y>=0 && y<=(s.getTaille()-1)) && (x>=(s.getTaille()/2) && x<=(s.getTaille()-1))) && s.getTerrain().caseEstVide(x,y) ){
                Vache v = new Vache(0, 5);
                s.getTerrain().setCase(x,y, v);
                s.getListeRessource().add(v);
                return true;
            }
        }
        return false;
    }

    /*** Getter ***/
    public boolean getVivant(){
    	return vivant;
    }
    
    public int[] getTab_dir(){
    	return tab_dir;
    }
    
    public static float getP_CH_DIR(){
    	return P_CH_DIR;
    }
    
    public static float getP_REPR(){
    	return P_REPR;
    }

    public int getMasse(){
        return masse;
    }



}
