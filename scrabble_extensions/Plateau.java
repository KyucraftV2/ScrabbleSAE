public class Plateau{
	private Case [][] g;
	private static int[][] plateau ={
			{5,1,1,2,1,1,1,5,1,1,1,2,1,1,5},
			{1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
			{1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
			{2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
			{1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
			{1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
			{1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
			{5,1,1,2,1,1,1,4,1,1,1,2,1,1,5},
			{1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
			{1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
			{1,1,1,1,4,1,1,1,1,1,4,1,1,1,1},
			{2,1,1,4,1,1,1,2,1,1,1,4,1,1,2},
			{1,1,4,1,1,1,2,1,2,1,1,1,4,1,1},
			{1,4,1,1,1,3,1,1,1,3,1,1,1,4,1},
			{5,1,1,2,1,1,1,5,1,1,1,2,1,1,5}
	};
	private boolean estVide; // si il n'y a pas de mot estVide=true sinon false
	private static char[] alphab = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	public Plateau(){

		this.g = new Case[plateau.length][plateau[1].length];
		for(int i=0;i<plateau.length;i++){
			for(int j=0;j<plateau[i].length;j++){
				this.g[i][j] = new Case(plateau[i][j]);
			}
		}
		this.estVide=false;
	}

	public String toString(){
		//résultat : chaîne décrivant ce Plateau
		String res = "";
		for(int i=0;i<this.g.length;i++){
			if(i==0){
				res = "   ⒈ ⒉ ⒊ ⒋ ⒌ ⒍ ⒎ ⒏ ⒐ ⒑ ⒒ ⒓ ⒔ ⒕ ⒖\n";	//correspond à la première ligne du plateau
				res = res +alphab[i]+" |";							// Affiche correctement les caractères numériques comme une seule lettre
			}														// dans un terminal mais peut ne pas afficher correctement sur un IDE
			else{
				res = res +alphab[i]+" |";
			}
			for(int j=0;j<this.g[i].length;j++){
				if(this.g[i][j].estRecouverte()){
					res = res + this.g[i][j].getLettre()+"|";	// affiche sur le plateau la lettre à la case i,j si elle est recouverte
				}
				else{
					if(this.g[i][j].getCouleur()==1){
						res = res +"_|";
					}
					else{
						res = res + this.g[i][j].getCouleur()+"|";	// affiche sur le plateau le nombre correspondant à la couleur de la case
					}
				}
				if(j==this.g[1].length-1){
					res = res+"\n";
				}
			}
		}
		return res;
	}

	public int[] motTab(String mot) {
		int[] tabMotIndice;
		tabMotIndice= new int[mot.length()];
		char lettreactu= ' ';
		for(int i=0;i<mot.length();i++) {
			lettreactu=mot.charAt(i);
			int index=0;
			while(lettreactu!=alphab[index]) {	//si la lettre est différente de la lettre de l'aplphabet on cherche la lettre d'après
				index++;
			}
			tabMotIndice[i]=index;
		}
		return tabMotIndice; //Retourne un tableau d'entiers correspondant aux indices des lettres du mot dans l'alphabet
	}

	public boolean contientJeton(String mot, MEE e) {
		// Action: renvoie un boolean indiquant si les lettres du "mot" placé en
		// parametre sont présentes sur le chevalet 'e'
		boolean verif = true;
		int i = 0;
		MEE copie = new MEE(e);	//fait une copie de MEE
		int indice = 0;
		int[] indiceMot = motTab(mot);
		while (verif == true && i < mot.length()) {	//tant que la longueur du mot
			indice = indiceMot[i];
			if (copie.retire(indice) == true) {	//si on ne peut pas retirer la lettre alors on met verif à false
			} else {
				verif = false;
			}
			i++;
		}
		return verif;
	}

	public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e){
		// pré requis: mot est un mot accepté par CapeloDico,
		// 0<=NumLig<=14
		// 0<=numCol<=14
		// sens est un element de {'v','h'}
		// entier max pour e est au moins 25
		// retourne vrai si le placement de sur this à partir de la case (numlig,numcol) dans le sens donné par sens à l'aide des jetons de e est valide
		boolean res = true;
		boolean contient = true;

		int compteur_i = 0; // la ou on est dans le mot
		int compteur_j = 0; // indice de i
		// verif si e contient les lettres suffisantes de mot
		contient=contientJeton(mot,e);
		if(contient==false){res=false;}

		// Verif de placement pour le premier mot de jeu
		if(this.estVide){
			if(mot.length()<2){ //le premier mot doit être d'au moins 2 lettres
				res=false;
			}
			boolean passeMilieu = false;
			if(sens=='v' && numCol==8){
				for(int i=numLig;i<numLig+mot.length();i++){
					if(i==8){passeMilieu=true;}	//teste si les lettres du mot vertical passe par la 8ème ligne
				}
			}
			else if(sens=='h' && numLig==8){
				for(int j=numCol;j<numCol+mot.length();j++){
					if(j==8){passeMilieu=true;}	//teste si les lettres du mot horizontal passe par la 8ème colonne
				}
			}
			if(passeMilieu==false){res=false;}
		}

		else{

		}
		boolean deuxieme_verif = false;
		if(sens=='v' && numLig-1>0 && numLig+mot.length()<15){	//teste si le mot dépasse ou non la 15ème colonne
			if(numLig==0 || !(g[numLig-1][numCol].estRecouverte())){	//teste si la case d'avant est recouverte ou innacessible
				for(int i=numLig; i<numLig+mot.length();i++){
					if(g[i][numCol].estRecouverte() && mot.charAt(i)==g[i][numCol].getLettre()){	//teste si la lettre qui est posée est la même que la lettre du mot
						deuxieme_verif=true;
					}
					else{
						deuxieme_verif=false;
					}
				}
			}
		}
		else if(sens=='h' && numCol-1>0 && numCol+mot.length()<15){	//teste si le mot dépasse ou non la 15ème ligne
			if(numCol==0 || !(g[numLig][numCol-1].estRecouverte())){	//teste si la case d'avant est recouverte ou innacessible
				for(int i=numCol; i<numCol+mot.length();i++){
					if(g[numLig][i].estRecouverte() && mot.charAt(i)==g[numLig][i].getLettre()){	//teste si la lettre qui est posée est la même que la lettre du mot
						deuxieme_verif=true;
					}
					else if(g[numLig][i].estRecouverte()){
						deuxieme_verif=false;
					}
				}
			}
		}
		if(deuxieme_verif==false){res=false;}


		return res;
	}

	public int nbPointsPlacement(String mot, int numLig, int numCol,char sens, int[] nbPointsJet){
		int score_mot=0;
		int foisdeux=0;
		int foistrois=0;
		int indice_lettre=0;
		int compteur_lettre=0;
		if(sens=='v'){
			for(int i=numLig;i<numLig+mot.length();i++){
				while(alphab[indice_lettre]!=mot.charAt(compteur_lettre)){
					indice_lettre++;
				}
				if(g[i][numCol].getCouleur()==4){	//teste si la case
					foisdeux++;	//augmente de 1 le nombre de cases fois 2 recouvertes
					score_mot=score_mot+nbPointsJet[indice_lettre];
				}
				else if(g[i][numCol].getCouleur()==5){
					foistrois++; //augmente de 1 le nombre de cases fois 3 recouvertes
					score_mot=score_mot+nbPointsJet[indice_lettre];
				}
				else if(g[i][numCol].getCouleur()==2){
					score_mot=score_mot+nbPointsJet[indice_lettre]*2; //multiplie par 2 le score de la lettre posée
				}
				else if(g[i][numCol].getCouleur()==3){
					score_mot=score_mot+nbPointsJet[indice_lettre]*3;	//multiplie par 3 le score de la lettre posée
				}
				else {
					score_mot=score_mot+nbPointsJet[indice_lettre];	//ajoute le score de la lettre au score
				}
				indice_lettre=0;
				compteur_lettre++;
			}
		}
		else if(sens=='h'){
			for(int i=numCol;i<numCol+mot.length();i++){
				while(alphab[indice_lettre]!=mot.charAt(compteur_lettre)){
					indice_lettre++;
				}
				if(g[numLig][i].getCouleur()==4){
					foisdeux++;
					score_mot=score_mot+nbPointsJet[indice_lettre];
				}
				else if(g[numLig][i].getCouleur()==5){
					foistrois++;
					score_mot=score_mot+nbPointsJet[indice_lettre];
				}
				else if(g[numLig][i].getCouleur()==2){
					score_mot=score_mot+nbPointsJet[indice_lettre]*2;
				}
				else if(g[numLig][i].getCouleur()==3){
					score_mot=score_mot+nbPointsJet[indice_lettre]*3;
				}
				else {
					score_mot=score_mot+nbPointsJet[indice_lettre];
				}
				indice_lettre=0;
				compteur_lettre++;
			}
		}

		if(mot.length()==7){score_mot=score_mot+50;}	//rajoute 50 points si le mot est de longueur à 7 lettres
		if(foisdeux>0){score_mot=score_mot*(2*foisdeux);}	//multiplie le score du mot par 2 fois le nombre de cases fois 2 recouvertes
		if(foistrois>0){score_mot=score_mot*(3*foistrois);} 	//multiplie le score du mot par 3 fois le nombre de cases fois recouvertes
		return score_mot;
	}
	public int place(String mot, int numLig, int numCol, char sens, MEE e){
		/** pré-requis : le placement de mot sur this à partir de la case
		 *    (numLig, numCol) dans le sens donné par sens à l’aide des
		 *     jetons de e est valide.
		 *   action/résultat : effectue ce placement et retourne le
		 *     nombre de jetons retirés de e.
		 */
		if(placementValide(mot, numLig, numCol, sens, e)){
			for (int i = 0; i < mot.length(); i++) {
				//retire la lettre et ajoute +1
				plateau[numLig][numCol]=mot.charAt(i);
				int indice_lettre=0;
				while(alphab[indice_lettre]!=mot.charAt(i)){
					indice_lettre++;
				}
				e.retire(indice_lettre);
			}
		}
		return mot.length();
	}

}