public abstract class Agent{

    /*** Attributs ***/
    protected int x;
    protected int y;
    protected int energie;
	protected boolean vivant;


    /*** Constructeurs ***/
    public Agent(int x, int y, int energie){
        this.x = x;
        this.y = y;
        this.energie = energie;
        this.vivant=true;
    }


    /*** Méthodes ***/
    public double distance(int x, int y){
        return Math.sqrt((this.x-x)*(this.x-x) + (this.y-y)*(this.y-y));
    }

    public void seDeplacer(int xnew, int ynew){
        x = xnew;
        y = ynew;
    }


    /*** Getter ***/
    public int getEnergie(){
        return energie;
    }


    /*** Setter ***/
    public void setEnergie(int energie){
        this.energie = energie;
    }
    
    public abstract void race();

}
