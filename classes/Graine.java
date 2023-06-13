public class Graine extends Ressource implements Ramassable{

    /*** Attributs ***/
    protected int maturite;


    /*** Constructeurs ***/
    public Graine(){
        super("🌰", 1);
        maturite = 0;
    }

    public Graine(String nom ,int q, int maturite){
        super(nom, q);
        this.maturite = maturite;
    }
    
    public Graine(Graine g){
        super(g.type,g.getQuantite());
        
    }


    /*** Méthodes ***/
	public void pousser(Simulation s){
        if(maturite==30){
    		int x = getX();
    		int y = getY();
    		s.vider(x,y);
    		s.placer(new Pousse(), x,y);
    		return;
    	}
    	if(maturite==80){
    		int x = getX();
    		int y = getY();
    		s.vider(x,y);
    		s.placer(new Ble(), x,y);
    		return;
    	}
    	maturite += 10;
    }


    /*** Getter ***/
    public int getMaturite(){
        return maturite;
    }

}
