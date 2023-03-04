public class Scrabble{
    private Joueur[] joueurs;
    private int numJoueur; // joueur courant (entre 0 et joueurs.length-1) private Plateau plateau;
    private MEE sac;
    private Plateau plateau;
    private static int [] nbPointsJeton = {1,3,3,2,1,4,2,4,1,8,10,1,2,1,1,3,8,1,1,1,1,4,10,10,10,10};

    public Scrabble(String[] pseudos){
        this.joueurs = new Joueur[pseudos.length];
        this.plateau = new Plateau();
        for(int i=0;i<pseudos.length;i++){
            this.joueurs[i] = new Joueur(pseudos[i]);
        }
        this.numJoueur = Ut.randomMinMax(0,this.joueurs.length-1);
        int[]lettre = {9,2,2,3,15,2,2,2,8,1,1,5,3,6,6,2,1,6,6,6,6,2,1,1,1,1};
        this.sac = new MEE(lettre);
    }
    
    public String toString(){
        String res = "Le joueur qui doit jouer est : "+ this.joueurs[numJoueur].toString()+"\n"+ this.plateau.toString();
        return res;
    }

    public int commence(Joueur[] joueurs) {
        //Résultat : retourne l'indice du tableau du joueur qui commence
        int res=0;
        int min = 26;
        int[] ordreLettres=new int[joueurs.length];

        int compteur = 4;
        while(compteur>=2){
            for (int i = 0; i < joueurs.length; i++) {
                ordreLettres[i]=sac.retireAleat();
                if(ordreLettres[i]<min){ // va remplacer le min et va donner le resultat
                    res = i; // res devient le num i qui est le min
                    min = ordreLettres[i]; // le min devient le plus petit
                }
            }
            compteur=0; // compteur de cmb de joueur ont le min
            for(int i=0;i<joueurs.length;i++){
                if(min==ordreLettres[i]){
                    compteur++; //augmente si i a le min
                }
            }
        }
        return res;
    }

    public Joueur partie(){

        int res=commence(joueurs);                                  //on récupère la position du joueur qui commence
        Joueur jgagnant = new Joueur("test");
        for(int i=res;i<this.joueurs.length;i++){                   //débute du joueur qui commence jusqu'à la longueur du tableau joueurs
            if(numJoueur==this.joueurs.length){
                numJoueur=0;
            }
            this.joueurs[numJoueur].prendJetons(sac, 7);
            numJoueur++;
        }
        for(int i=0;i<res;i++){                                     //débute de 0 jusqu'au joueur qui commence
            if(numJoueur==this.joueurs.length){                     //devrait ne jamais arriver mais par précaution
                numJoueur=0;
            }
            this.joueurs[numJoueur].prendJetons(sac, 7);
            numJoueur++;
        }

        boolean sacvide = this.sac.estVide();
        boolean toutlemondepasse = false;
        int enregistrement = 0;
        int compte_passe = 0;
        while(sacvide==false || toutlemondepasse==false){
            if(numJoueur==this.joueurs.length){
                numJoueur=0;
            }
            enregistrement = this.joueurs[numJoueur].joue(this.plateau, this.sac, nbPointsJeton);
            if(enregistrement==-1){compte_passe++;}
            if(compte_passe==this.joueurs.length){toutlemondepasse=true;}
            if(enregistrement==0){sacvide=this.sac.estVide();}
            sacvide = this.sac.estVide();
            numJoueur++;
        } 
        int score_a_retire;
        if(!(this.sac.estVide())){  //si le sac n'est pas vide
            for(int i =0;i<this.joueurs.length;i++){
                score_a_retire = this.joueurs[i].nbPointsChevalet(nbPointsJeton);   //compte le nombre de points à retirer du score du joueur
                this.joueurs[i].ajouteScore(-score_a_retire);   //retire au score du joueur le nombre de points restants
            }
        }
        else{
            numJoueur--; //retombe sur le numéro qui a stoppé <-- changer et faire celui qui a gagné
            for(int i =0;i<this.joueurs.length;i++){
                if(i!=numJoueur){
                    this.joueurs[numJoueur].ajouteScore(this.joueurs[i].nbPointsChevalet(nbPointsJeton));
                    this.joueurs[i].ajouteScore(-this.joueurs[i].nbPointsChevalet(nbPointsJeton));
                }
            }
        }
        Ut.afficher(jgagnant.toString());

        return jgagnant;
    }
}