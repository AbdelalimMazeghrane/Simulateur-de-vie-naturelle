import java.util.ArrayList;

public class Fermier extends Agent implements PeutMourir{

    /*** Attributs ***/
    private double money;
    private ArrayList<Ramassable> inventaire;


    /*** Constructeurs ***/
    public Fermier(int x, int y, double money){
        super(x,y,100);
        this.money = money;
        inventaire = new ArrayList<Ramassable>();
    }

    public Fermier(int x, int y){
        this(x, y, 100);
    }


    /*** Méthodes ***/
    /*** Partie Champs ***/
   	public void recolterChamps(Simulation s){

   		ArrayList<Ble> tab = new ArrayList<Ble>();

   		for(int i=0; i<s.getTerrain().lesRessources().size();i++){
   			if(s.getTerrain().lesRessources().get(i) instanceof Ble){
   				Ble b = (Ble)s.getTerrain().lesRessources().get(i);
   				if(b.getMaturite()>=100){
   					tab.add(b);
   					Graine g = new Graine();
   					inventaire.add(g);
   					inventaire.add(new Graine(g));
   				}
   			}
   		}

   		int nb = tab.size();

   		for(int i=0; i<tab.size();i++){
   			Ble b = (Ble)s.vider(tab.get(i).getX(),tab.get(i).getY());
   			inventaire.add(b);
   		}

        energie-=5;
        
        if(nb>0){
           	System.out.println("Le fermier a récolté : "+nb+ " blés.");
        }


   	}


   	public void semerChamps(Simulation s){

   		ArrayList<Graine> tab = new ArrayList<Graine>();
   		energie-=5;

   		for(int i=0; i<inventaire.size();i++){
   			if(inventaire.get(i) instanceof Ble){continue;}
   			if(inventaire.get(i) instanceof Graine){
   				Graine g = (Graine)inventaire.get(i);
   				tab.add(g);
   			}
   		}
   		int nb = tab.size();
   		int t = (s.getTaille()/2)*(s.getTaille());

   		if(nb<t){
   			for(int i=0; i<tab.size();i++){
   				s.semer(tab.get(i));
   				inventaire.remove(tab.get(i));
   			}
   			if(nb>0){
   				System.out.println("Le fermier a planté : "+nb+ " graines.");
   			}

   		}
   		else{
   			for(int i=0; i<nb; i++){
                if(i<t){
   				    s.semer(tab.get(i));
   				    inventaire.remove(tab.get(i));
                }
                else{
                    //vendreGraine();
                }
   			}
   			if(t>0){
   				System.out.println("Le fermier a planté : "+t+ " graines.");
   			}
            

   		}
   	}


   	public void vendreBle(){

   		ArrayList<Ble> tab = new ArrayList<Ble>();

   		for(int i=0; i<inventaire.size();i++){
   			if(inventaire.get(i) instanceof Ble){
   				Ble b = (Ble)inventaire.get(i);
   				tab.add(b);
   			}
   		}

   		int nb = tab.size();
   		int argent = nb*10;

   		for(int i=0; i<tab.size();i++){
   			inventaire.remove(tab.get(i));
   		}

   		this.money+=argent;
   		
   		if(argent>0){
   			System.out.println("Le fermier a vendu "+nb+ " blés pour "+argent+" euros (solde : "+ money +")");
   		}


   	}


    /*** Partie Ferme ***/
    
    public void recolterViande(Simulation s){

   		ArrayList<Viande> tab = new ArrayList<Viande>();

   		for(int i=0; i<s.getTerrain().lesRessources().size();i++){
   			if(s.getTerrain().lesRessources().get(i) instanceof Viande){
   				Viande v = (Viande)s.getTerrain().lesRessources().get(i);
   				tab.add(v);
   				}
   			}
   		

   		int nb = tab.size();

   		for(int i=0; i<tab.size();i++){
   			Viande v = (Viande)s.vider(tab.get(i).getX(),tab.get(i).getY());
   			inventaire.add(v);
   		}

        energie-=5;
        
        if(nb>0){
           	System.out.println("Le fermier a récolté : "+nb+ " viandes.");
        }


   	}

    /*** Partie Self ***/
    public void mourir(){
        if(energie<=0){
   			vivant=false;
   		}
        return;
    }

   	public void energie(){
   		mourir();
   		System.out.print("PV : ");
   		if(energie<10){
   			System.out.println("❤️");
   		}
   		if(energie>=10 && energie<20){
   			System.out.println("❤️❤️");
   		}
   		if(energie>=20 && energie<30){
   			System.out.println("❤️❤️❤️");
   		}
   		if(energie>=30 && energie<40){
   			System.out.println("❤️❤️❤️❤️");
   		}
   		if(energie>=40 && energie<50){
   			System.out.println("❤️❤️❤️❤️❤️");
   		}
   		if(energie>=50 && energie<60){
   			System.out.println("❤️❤️❤️❤️❤️❤️");
   		}
   		if(energie>=60 && energie<70){
   			System.out.println("❤️❤️❤️❤️❤️❤️❤️");
   		}
   		if(energie>=70 && energie<80){
   			System.out.println("❤️❤️❤️❤️❤️❤️❤️❤️");
   		}
   		if(energie>=80 && energie<90){
   			System.out.println("❤️❤️❤️❤️❤️❤️❤️❤️❤️");
   		}
   		if(energie>=90 && energie<100){
   			System.out.println("❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️");
   		}
   		
   	}

   	public void manger() throws EnergieException{
   		money-=10;
   		energie=(energie+(int)(Math.random()*(35+1)+0));
   		if(energie>100){
   			throw new EnergieException(" b ");
   		}
	
   	}
   	
   	public void race(){
   		System.out.println("Je suis un humain.");
   	}


    /*** Getter ***/
   	public boolean getVivant(){
   		return vivant;
   	}

    public ArrayList<Ramassable> getPanier(){
        return inventaire;
    }

}
