public class Case{
	private int couleur; // entre 1 et 5
	// 1 ==> gris / 2 ==> lettre double / 3 ==> lettre triple / 4 ==> mot doble / 5 ==> mot triple
	private int dispo; // 0=libre et 1=dispo
	private int chara; // -1=si libre sinon place de la lettre dans l'alphabet
	private static char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	public Case(int uneCouleur){
		// Constructeur
		this.couleur=uneCouleur;
		this.dispo=0;
		this.chara=-1;
	}

	public int getCouleur(){
		// Renvoie la couleur de this
		return this.couleur;
	}

	public char getLettre(){
		//pré-requis : dispo==1
		//renvoie la lettre sur la case
		return alphabet[this.chara];
	}

	public void setLettre(char let){
		// pré-requis let est une lettre majuscule
		for(int i=0;i<alphabet.length;i++){
			if(alphabet[i]==let){
				this.chara=i;
			}
		}
		this.dispo=1;
	}

	public boolean estRecouverte(){
		return this.dispo==1;
	}

	public String toString(){
		if(this.estRecouverte()==true){
			return "Cette case a la lettre: "+alphabet[this.chara]+" et a la couleur : "+this.couleur;
		}
		else{
			return "Cette case n'est pas recouverte, et elle a la couleur :"+this.couleur;
		}	
	}

}