class MEE{
    private int[] tabFreq; //tabfreq[i] est le nombre d'exemplaire (fréquence de i)
    private int nbTotalEx; // nombre total d'exemplaires


//constructeurs

    public MEE(int max){ //pré-requis : max>=0 / crée un multi ensemble vide dont les éléments seront inférieurs à max
        this.tabFreq = new int[max];
        this.nbTotalEx = 0;
    }

    public MEE (int[] tab){ //pré-requis : les éléments de tab sont positifs ou nuls / crée un multiensemble dont le tableau de fréquences est une copie de tab
        this.tabFreq = new int[tab.length];
        this.nbTotalEx = 0;
        for(int i=0;i<tab.length;i++){
            this.tabFreq[i]=tab[i];
            this.nbTotalEx += tab[i];
        }

    }

    public MEE (MEE e){
        this.tabFreq = new int[e.tabFreq.length];
        this.nbTotalEx = e.nbTotalEx;
        for(int i=0;i<e.tabFreq.length;i++){
            this.tabFreq[i]=e.tabFreq[i];
        }
    }

//méthodes

    public boolean estVide(){
        // Si nbTotalEx==0 ca signifie que  tabfreq n'a que des zéros
        // Donc va renvoyer true
        // Sinon tabfreq n'est pas vide
        // et donc renvoie false
        return this.nbTotalEx==0;
    }

    public void ajoute(int i){
        //pré-requis 0<=i<tabFreq.length
        //ajoute un exemplaire de i a this
        this.nbTotalEx++;
        this.tabFreq[i]++;
    }

    public boolean retire(int i){
        //pré-requis 0<=i<tabFreq.length
        //va retirer un exemple de i et renvoie true
        //si tabfreq ne contient pas de i renvoie false
        boolean res = false;
        if(this.tabFreq[i]>0){
            this.tabFreq[i]--;
            this.nbTotalEx--;
            res = true;
        }
        return res;
    }

    public int retireAleat(){
        // pré requis : this est non vide
        // choisir un élement aléatoire et retire un exemplaire et le renvoie
        int choix=Ut.randomMinMax(0,this.tabFreq.length-1);
        while(this.tabFreq[choix]==0){
            choix=Ut.randomMinMax(0,this.tabFreq.length-1);
        }
        this.tabFreq[choix]--;
        this.nbTotalEx--;
        return choix;
    }

    public boolean transfere(MEE e, int i){
        // pré requis : 0<=i<tabFreq.length
        // va retirer i de this et le transfère vers e
        // Si i n'existe pas ne le transfere pas
        boolean res=false;
        if(this.retire(i)){
            if(i<e.tabFreq.length){
                e.ajoute(i);
                res=true;
            }
            else{
                this.ajoute(i);
            }
        }
        return res;
    }

    public void transfereAleat(MEE e, int k){
        // pré-requis : k>=0
        //action : transfère k exemplaires choisis
        //aléatoirement de this vers e dans la limite de this

        int i = Ut.randomMinMax(0,this.tabFreq.length-1);
        while(this.tabFreq[i]==0 && i<=e.tabFreq.length){
            i = Ut.randomMinMax(0,this.tabFreq.length-1);
        }
        while(this.tabFreq[i]>0 && k>0){
            this.transfere(e,i);
            k--;
        }
    }

    public int sommeValeurs(int[] v){
        //pré-requis : tableFreq.length <= v.length
        //résultat : retourne la somme des valeurs des
        //exemplaires des éléments de this, la valeur
        //d'un exemplaire d'un élément i de this étant égal à v[i]
        int res=0;
        for(int i=0;i<this.tabFreq.length;i++){
            res = res + v[i]*this.tabFreq[i];
        }
        return res;
    }

    public int contient_i(int n){
        // renvoie combien de fois apparait la valeur d'indice n
        return this.tabFreq[n];
    }

    public int somme_freq(){
        int res=0;
        for(int i=0;i<this.tabFreq.length;i++) {
            res = res + this.tabFreq[i];
        }
        return res;
    }

    // to string
    public String toString(){
        String liste = "{";
        for(int i=0;i<this.tabFreq.length;i++){
            if(i<this.tabFreq.length-1){
                liste = liste + this.tabFreq[i]+",";
            }
            else{liste = liste + this.tabFreq[i];}
        }
        liste=liste+"}\n";
        return liste;
    }

}