public class Souris extends Agent{

    /*** Constructeurs ***/
    public Souris(int x, int y){
        super(x,y,10);
    }


    /*** Méthodes ***/
    public boolean manger(Simulation s){
		vivre();
    	while(true){
    		int x = (int)(Math.random()*s.getTaille());
    		int y = (int)(Math.random()*s.getTaille()/2);
    		if(s.getTerrain().caseEstVide(y,x)){continue;}
    		else{
        	s.vider(x,y);
        	this.energie=(this.energie+1)%11;
        	break;}
        }
        return true;
    }
    
    public void race(){
   		System.out.println("Je suis une souris.");
   	}
   	
   	public void vivre(){
   		if(Math.random()<0.9){
    		this.energie-=1;
    	}
    	return;
   	}
   	
}
