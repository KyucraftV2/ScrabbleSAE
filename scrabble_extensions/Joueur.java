public class Joueur{

	private String nom;
	private MEE chevalet;
	private int score;

	public Joueur(String unNom){
		this.nom=unNom;
		this.chevalet = new MEE(26);
		this.score=0;
		this.chevalet.ajoute(4);this.chevalet.ajoute(4);this.chevalet.ajoute(1);this.chevalet.ajoute(0);
	}

	public String toString(){
		return "Le joueur : "+this.nom+" a un score de "+this.score;
	}

	public int getScore(){
		//renvoie le score de joueur
		return this.score;
	}

	public void ajouteScore(int nb){
		// ajoute nb au score
		//pré-requis : nb>=0
		this.score = this.score + nb;
	}

	public int nbPointsChevalet(int[] nbPointsJet){
		// pré-requis : nbPointsJet indique le nombre de points rapportés par chaque jeton/lettre
		// renvoie le nb total de points sur le chevalet de this
		return this.chevalet.sommeValeurs(nbPointsJet);
	}

	public void prendJetons(MEE s, int nbJetons){
		/** pré-requis : les éléments de s sont inférieurs à 26
		 * action : simule la prise de nbJetons jetons par this dans le sac s,
		 * dans la limite de son contenu.*/
		this.chevalet.transfereAleat(s,nbJetons);
	}

	public int joue(Plateau p, MEE sac, int[] nbPointsJet){
		System.out.print("Choix:\n1)Echanges de jetons\n2)Poser un mot\n3)Passe ton tour\n\n");
		System.out.print("Saisis ton choix entre 1 et 3 : ");
		int choix_joueur = Ut.saisirEntier();
		int[] tab_verif = {1,2,3};
		int res=0;
		while(Ut.in(tab_verif,choix_joueur)==false){	//tant que le choix n'est pas un deux ou trois
			System.out.print("Réessaie! Saisis un nombre entre 1 et 3 : ");
			choix_joueur = Ut.saisirEntier();
		}
		if(choix_joueur==1){	//le choix un permet d'échanger les jetons
			this.echangeJetons(sac);
		}
		else if(choix_joueur==2){	//le choix deux permet de jouer un mot
			this.joueMot(p,sac,nbPointsJet);
			if(this.chevalet.estVide()){
				res=1;
			}
		}
		else{	//le choix trois permet de passer son tour
			res=-1;
		}
		return res;
	}

	public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {
		/** pré-requis : les éléments de s sont inférieurs à 26
		 * *et nbPointsJet.length >= 26
		 * *  action : simule le placement d’un mot de this (le mot, sa position
		 * *sur le plateau et sa direction, sont saisies au clavier)
		 * *  résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par
		 * *CapeloDico et satisfaisant les règles détaillées plus haut
		 */
		String mot;
		char ligneLettre;
		int ligne;
		int col;
		char sens;
		boolean res;

		System.out.print("Saisis un mot en majuscule : ");
		mot=Ut.saisirChaine();
		System.out.print("Saisis une ligne entre les lettres A et O : ");
		ligneLettre=Ut.saisirCaractere();
		while(Ut.majToIndex(ligneLettre)<65 && Ut.majToIndex(ligneLettre)>79){
			Ut.afficherSL("Tu n'as pas saisi un entier valide");
			ligneLettre=Ut.saisirCaractere();
		}
		ligne=Ut.majToIndex(ligneLettre);
		System.out.print("Saisis une colonne entre 0 et 14 : ");
		col=Ut.saisirEntier();
		while(col>14 && col<0){
			Ut.afficherSL("Tu n'as pas saisi un entier valide");
			col=Ut.saisirEntier();
		}
		System.out.print("Saisis un sens entre v (vertical) et h (horizontal) : ");
		sens=Ut.saisirCaractere();
		while(sens!='v' && sens!='h'){
			Ut.afficherSL("Le sens n'est pas valide");
			sens=Ut.saisirCaractere();
		}
		System.out.print("Saisir si le mot est valide (true or false) : ");
		res=Ut.saisirBooleen();

		if(p.placementValide(mot,ligne,col,sens,s) && res==true){
			this.joueMotAux(p,s,nbPointsJet,mot,ligne,col,sens);
			return true;
		}
		else{return false;}
	}

	public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot,int numLig, int numCol, char sens){
		if(p.placementValide(mot,numLig,numCol,sens,s)){
			p.place(mot,numLig,numCol,sens,s);	//place le mot sur le plateau
			this.score=this.score+p.nbPointsPlacement(mot,numLig,numCol,sens,nbPointsJet);	//ajoute le score du mot au score du joueur
			this.prendJetons(s,mot.length());	//pioche le nombre de jetons et les ajoute au chevalet du joueur
		}
	}

	public void echangeJetons(MEE sac){
		/*** pré-requis : sac peut contenir des entiers de 0 à 25
		 * action : simule l’échange de jetons de ce joueur :
		 *   - saisie de la suite de lettres du chevalet à échanger
		 *		en vérifiant que la suite soit correcte
		 *   - échange de jetons entre le chevalet du joueur et le sac
		 * stratégie : appelle les méthodes estCorrectPourEchange et echangeJetonsAux*/
		String mot = new String();
		Ut.afficher("Saisis la suite de lettres que tu souhaites échanger :");
		mot=Ut.saisirChaine();
		while(!(this.estCorrectPourEchange(mot))){
			Ut.afficherSL("MOT INCORRECT !");
			mot=Ut.saisirChaine();
		}
		this.echangeJetonsAux(sac,mot);
	}

	public void echangeJetonsAux(MEE sac,String ensJetons) {
		/** pré-requis : sac peut contenir des entiers de 0 à 25 et ensJetons
		 *est un ensemble de jetons correct pour l’échange
		 *  action : simule l’échange de jetons de ensJetons avec des
		 *jetons du sac tirés aléatoirement.*/
		int j;

		if(sac.somme_freq()<7 && ensJetons.length()>sac.somme_freq()){
			int compteur=ensJetons.length()-sac.somme_freq();
			sac.transfereAleat(this.chevalet,compteur);
			for (int i = 0; i < sac.somme_freq(); i++) {
				j=i;
				while(!this.chevalet.retire(Ut.index(ensJetons.charAt(i)))){
					j++;
				}
				sac.ajoute(j);
			}
		}
		else {
			sac.transfereAleat(this.chevalet,ensJetons.length());
			for (int i = 0; i < ensJetons.length(); i++) {
				j = i;
				while (!this.chevalet.retire(Ut.index(ensJetons.charAt(i)))) {
					j++;
				}
				sac.ajoute(j);
			}
		}
	}

	public boolean estCorrectPourEchange(String mot){
		/** résultat : vrai ssi les caractères de mot correspondent tous à des
		 *lettres majuscules et l’ensemble de ces caractères est un
		 *sous-ensemble des jetons du chevalet de this
		 */

		boolean res = true;
		if(this.contientchaine(mot)==false){	//si le joueur a la chaine de caractères sur son chevalet
			res = false;
		}
		for(int i=0;i<mot.length();i++){
			if(Ut.estUneMajuscule(mot.charAt(i))==false){	//si la lettre n'est pas une majuscule
				res=false;
			}
		}
		return res;
	}

	private boolean contientchaine(String chaine){
		//vérifie si le joueur a la chaine de caractères sur son chevalet
		boolean res = true;
		int i=0;
		while(res && i<chaine.length()){
			if(this.chevalet.contient_i(Ut.index(chaine.charAt(i)))==0){
				res=false;
			}
			i++;
		}
		return res;
	}

}

