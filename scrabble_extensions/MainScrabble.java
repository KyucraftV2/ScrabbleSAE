public class MainScrabble {

    // La procédure principale (main) de cette classe commence par la saisie du nombre de joueurs.
    // Cet entier doit évidemment être supérieur ou égal à 1, et ne doit pas être trop grand pour
    // permettre à chaque joueur de commencer la partie avec 7 jetons. Les joueurs sont numérotés
    // à partir de 0. La fonction procède ensuite à la saisie des noms des différents joueurs, avant
    // de créer un objet Scrabble et d’effectuer une partie.

    public static void main(String args[]) {


        System.out.print("Saisir le nombre de joueurs : "); //demande de saisir le nombre de joueurs
        int n = Ut.saisirEntier();
        while (n < 1 || n > 4) { //le nombre de joueurs doit être compris entre 1 et 4
            System.out.print("Le nombre de joueurs doit compris être entre 1 et 4 veuillez ressaisir un nombre : ");
            n = Ut.saisirEntier();
        }
        String[] nomJoueurs=new String[n];
        for (int i = 0; i < n; i++) {
            nomJoueurs = new String[n];
            System.out.print("Saisir le nom du joueur "+(i+1)+" : "); //demande le nom de chaque joueur
            nomJoueurs[i] = Ut.saisirChaine();
        }
        Scrabble s1 = new Scrabble(nomJoueurs); // créé un objet Scrabble
        System.out.println(s1.partie()); // lance une partie de Scrabble
    }

}