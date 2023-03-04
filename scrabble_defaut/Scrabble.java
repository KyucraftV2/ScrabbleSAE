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

    
    public Joueur partie(){
        Joueur jgagnant = new Joueur("test");
        for(int i=0;i<this.joueurs.length;i++){	//parcoure la liste des joueurs
            if(numJoueur==this.joueurs.length){
                numJoueur=0;
            }
            this.joueurs[numJoueur].prendJetons(sac, 7);	//fait piocher 7 lettres à chaque joueurs
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
        if(!(this.sac.estVide())){	//si le sac n'est pas vide
            for(int i =0;i<this.joueurs.length;i++){
                score_a_retire = this.joueurs[i].nbPointsChevalet(nbPointsJeton);	//compte le nombre de points à retirer du score du joueur
                this.joueurs[i].ajouteScore(-score_a_retire);	//retire au score du joueur le nombre de points restants
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