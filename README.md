# SAE 1.01
## Scrabble

Le but de cette SAE de développer le jeu du scrabble.<br>
Nous étions par groupe de 2, nous avions les signatures de chaque fonction qu'il fallait développer.<br>

On pouvait aussi rajouter des extensions, nous avons donc rajouté deux extensions :
- Prise en compte d’un dictionnaire de référence
Pour vérifier qu’un mot proposé par un joueur est bien un mot de la langue française autorisé par le jeu de Scrabble, le programme peut faire appel à un dictionnaire de référence.
* Détermination du joueur qui commence
Chaque joueur tire un jeton du sac. celui qui a tiré la plus petite lettre selon l’ordre alphabétique
commence. S’il y a des ex-aequos, les joueurs ex-aequos re-tirent un jeton pour se départager.
S’il y a encore des ex-aequos, ces joueurs ex-aequos retirent un jeton pour se départager etc.
Tous les jetons sont remis dans le sac, puis le joueur qui commence tire ses 7 jetons, puis le
suivant...

Pour plus d'informations sur le sujet, vous pouvez regarder le PDF se trouvant à la racine du projet.

Le code se sépare en deux, la version basique dans le dossier scrabble_defaut et la version avec nos deux extensions rajoutées dans le dossier scrabble_extensions.

