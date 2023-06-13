import java.util.ArrayList;

public class Simulation{

    /*** Attributs ***/
    private Terrain terrain;
    private ArrayList<Agent> listeAgent;
    private ArrayList<Ressource> listeRessource;
	private int taille;


    /*** Constructeurs ***/
    public Simulation(int taille, int m, int n){
        //Cette classe possède un constructeur qui place aléatoirement m ressources sur le terrain et génère n agents.
        //Initialisation des attributs.
        this.taille=taille;
        terrain = new Terrain(taille, taille);
        listeRessource = new ArrayList<Ressource>();
        listeAgent = new ArrayList<Agent>();

        //Creation des ressources.
        //75% de graines
        //25% de vaches
        for(int i=0; i<m; i++){
            if(Math.random()<0.75){
                listeRessource.add(new Graine());
            }
            else{
                listeRessource.add(new Vache());
            }
        }

        //Creation des agents
        //1 fermier
        //le reste :  des souris
        listeAgent.add(new Fermier(0,0));
        for(int i=1; i<n; i++){
            listeAgent.add(new Souris(0,0));
        }


        //Placement des ressources sur le terrain:
        int x;
        int y;
        for(int i=0; i<listeRessource.size(); i++){                             //Parcours de la liste de ressources
            if(listeRessource.get(i) instanceof Vache){                         //Si c'est une vache, elle est placée dans la seconde partie du terrain (la ferme)
                x = (int)(Math.random()*((taille-1)-0+1)+0);                    //Coordonnées
                y = (int)(Math.random()*((taille-1)-(taille/2)+1)+(taille/2));  //Aléatoires
                if(terrain.caseEstVide(y,x)){                                   //On vérifie que la case est vide
                    terrain.setCase(y,x,listeRessource.get(i));                 //On place la vache.
                }
            }
            if(listeRessource.get(i) instanceof Graine){                        //De même pour les graines.


                x = (int)(Math.random()*((taille-1)-0+1)+0);
                y = (int)(Math.random()*((taille/2-1)-0+1)+0);
                if(terrain.caseEstVide(y,x)){
                	terrain.setCase(y,x,listeRessource.get(i));
                }
            }
        }

    }


    /*** Méthodes ***/
    public Ressource vider(int x, int y){
    	if(terrain.caseEstVide(x,y)==false){
    		Ressource r = terrain.videCase(x,y);
    		listeRessource.remove(r);
    		return r;
    	}
    	return null;
    }

    public void placer(Ressource r, int x, int y){
    	listeRessource.add(r);
    	terrain.setCase(x,y,r);
    }

    public void semer(Graine g){
    	int levier = 0;
    	for(int i=0;i< taille;i++){
    		for(int j=0;j<(taille/2);j++){
    			if(terrain.caseEstVide(j,i)){
    				terrain.setCase(j,i,g);
    				listeRessource.add(g);
    				levier = 1;
    				break;
    			}
    		}
    		if(levier==1){
    			break;
    		}
    	}
    }


    public void rafraichir(){
        ArrayList<Graine> tab = new ArrayList<Graine>();
        ArrayList<Vache> tab2 = new ArrayList<Vache>();

        for(int i=0; i<listeRessource.size(); i++){
            if(listeRessource.get(i) instanceof Graine){
                Graine g = (Graine)listeRessource.get(i);
                tab.add(g);
            }
            if(listeRessource.get(i) instanceof Vache){
                Vache v = (Vache)listeRessource.get(i);
                v.ageplus();
                v.mourir();
                v.seReproduire(this);
                if((v.getVivant()==false)){
                	tab2.add(v);
                }
            }
        }

        for(int i=0; i<tab.size(); i++){
        	tab.get(i).pousser(this);
        }

        for(int i=0; i<tab2.size(); i++){
            int x = tab2.get(i).getX();
            int y = tab2.get(i).getY();
            listeRessource.remove(tab2.get(i));
            terrain.videCase(x,y);
            Viande vi = new Viande(tab2.get(i).getMasse());
            terrain.setCase(x,y,vi);
            listeRessource.add(vi);
        }
		
		sourisManger();
		System.out.println("Les souris ont volé du blé.");

    }
    
    public void simuler(Fermier f){
    	
		int jour=1;

        while(f.getVivant()){
				System.out.println("JOUR : "+ jour);
				f.energie();
				try{
					f.manger();
				}catch(EnergieException e){
					f.setEnergie(100);
				}
				getTerrain().affiche(3);
				f.recolterViande(this);
				rafraichir();
				f.recolterChamps(this);
				f.semerChamps(this);
				f.vendreBle();
                bougerVache();
				jour++;

                try{
                    Thread.sleep(500);
                }
                catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
			}
        System.out.println("Le fermier est mort.");
    }
    

    public void bougerVache(){

        for (int i=0; i<listeRessource.size(); i++){
            if(listeRessource.get(i) instanceof Vache){
                Vache v = (Vache)listeRessource.get(i);
                int[] tab = v.getTab_dir();
                if(Math.random()<=Vache.getP_CH_DIR()){
                    tab[0] = (int)(Math.random()*(2-0+1)+0)-1;
                    tab[1] = (int)(Math.random()*(2-0+1)+0)-1;;
                }
                int x = (v.getX() + tab[0] + taille % taille);
                int y = (v.getY() + tab[1] + taille % taille);
                if( ((y>=0 && y<=(taille-1)) && (x>=(taille/2) && x<=(taille-1))) && terrain.caseEstVide(x,y) ){
                    //v.setPosition(y,x); //Ca marche pas ?
                    terrain.setCase(x,y,terrain.videCase(v.getX(),v.getY()));
                }
            }
        }
    }
    
    public void sourisManger(){
    	for(int i=0;i<listeAgent.size();i++){
    		if(listeAgent.get(i) instanceof Souris){
    			Souris s = (Souris)listeAgent.get(i);
    			s.manger(this);
    		}
    	}
    	return;
    }


    /*** Getter ***/
    public Terrain getTerrain(){
        return terrain;
    }

	public ArrayList<Ressource> getListeRessource(){
        return listeRessource;
    }

    public ArrayList<Agent> getListeAgent(){
        return listeAgent;
    }

    public int getTaille(){
    	return taille;
    }


    /*** toString ***/
    public String toStringListeRessource(){
    	String s ="";
    	for(int i=0;i<listeRessource.size();i++){
    		s+=listeRessource.toString()+"\n";
    	}
    	return s;
    }

}
