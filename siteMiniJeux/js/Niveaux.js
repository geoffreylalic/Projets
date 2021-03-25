//liste des niveaux
const JEUX = [
    //niveau facile
    {
        nom: "Mot Fléchés",
        difficulte: "Facile",
        listeMots: [
            "|G|C|G%E|O%",
            "PALACE|CAME",
            "|SARRASIN%S",
            "APITOIEMENT",
            "|INES|RE%E%",
            "BLESSER|SUC",
            "|LUI|RAMENE",
            "PESER%IMPER",
            "%RENAIT%TUF"
        ],
        listeIndices: [
            "C'EST GÂCHER ↓", "BEL HÔTEL →",
            "LOGIQUE D'ESPRIT↓", "DUVETEUSE↓",
            "OISEAU AU PLUMAGE BIGARRE ↓", "COURSE DE FOND ↓",
            "QUI A PERDU DE SA HAUTEUR ↓",
            "CLUB MARSEILLAIS ↓", "LE BAUDET ↓",
            "LE PREMIER A ËTRE ECLAIRE ↓",
            "DROGUE →", "COMPRIMAIT ↓",
            "CEREALE →", "COMPASSION →",
            "UN PEU BÊTE MAIS PAS MECHANT ↓",
            "PRENOM ESPAGNOL →", "FAIRE DE LA PEINE →",
            "SOUS/MI →", "FIN DE VERBE ↓",
            "NOMBRE DE COTES D'UN HEPTAGONE ↓",
            "IL A DES CORS SUR LA TÊTE ↓",
            "EXTRAIT VEGETAL →", "PARTIE DU METRE ↓",
            "C'EST TOUT POUR ELLE →", "TARER →",
            "FAIT REVENIR AU BERCAIL →", "OU RE ↓",
            "IL PROTEGE DE LA PLUIE →",
            "RECOMMENCE A VIVRE →",
            "MATERIAU DE CONSTRUCTION POREUX →"
        ],
        solutionLigne: [
            "PALACE", "CAME", "SARRASIN", "APITOIEMENT", "INES", "RE", "BLESSER", "SUC", "LUI", "RAMENE", "PESER", "IMPER", "RENAIT", "TUF"
        ],
        solutionColonne: [
            "MARAT", "GASPILLER", "LAINEUSE", "CARTESIEN", "CROSS", "RA", "GEAI", "ER", "SERRAIT", "ECIME", "MM", "ANE", "SEPT", "OM", "NEUNEU", "EST", "CERF"
        ]
    },
    //niveau moyen
    {
        nom: "Mot Fléchés",
        difficulte: "Moyen",
        listeMots: [
            "|R|R%N|P|N%",
            "GEAI|ALESIA",
            "|SUREMENT%O",
            "ASSAGIES|CC",
            "|AS|AB|AIR%",
            "ASIALIE|DUC",
            "|SERIEUSE%R",
            "MARAT%RISEE",
            "%SELENE%TNT"
        ],
        listeIndices: [
            "COGITAS LONGTEMPS ↓", "IL CAJOLE →",
            "S'AMUSERA ↓", "CORDAGE DE HALAGE ↓",
            "ETAT DE L'AFRIQUE AUSTRALE ↓",
            "SE FIT DES IDEES ↓", "GENERAL ORIENTE AU SUD ↓",
            "NICKEL ↓", "PERSONNAGE SUR LE CALENDRIER ↓",
            "APPELLATION DES VINS DE FRANCE ↓",
            "VERCINGETORIX Y FUT VAINCU →", "PARITE ↓",
            "SANS PRENDRE DE RISQUE →", "APAISEES →",
            "CE N'EST PAS DU TOUT CUIT ! ↓",
            "DEUX CENTS A ROME → ", "LOCUTION LATINE ↓",
            "BRILLANT SUJET →", "MANQUE DE SALIVE →",
            "PREMIERE LETTRES →", "MER EN ASIE ↓",
            "IL EST GRAND EN BORD DE MER →", "RIVIERE ↓",
            "ESCARPEMENT ROCHEUX ↓",
            "NOBLE →", "IL PERMET DE REVER DE L'IMPOSSIBLE ↓",
            "GRAVE →", "VICTIME DE CHARLOTTE CORDAY ↓→",
            "INDIQUE LA FACON DE FAIRE ↓",
            "UN AIR APPRECIE DU REGATIER →",
            "ELLE AURAIT TOUJOURS ETE DANS LA LUNE →",
            "IL A LE TEMPERAMENT EXPLOSIF →"
        ],
        solutionLigne: [
            "GEAI", "ALESIA", "SUREMENT", "ASSAGIES", "CC", "AS", "AB", "AIR", "ASIALIE", "DUC", "SERIEUSE", "RISEE", "SELENE", "TNT"
        ],
        solutionColonne: [
            "RESSASSAS", "AUSSIERE", "RIRA", "ARAL", "EGALITE", "NAMIBIE", "LEE", "EURE", "PENSA", "SI", "ST", "IDEST", "NI", "CRU", "EN", "AOC", "CRET"
        ]
    },
    //difficile
    {
        nom: "Mot Fléchés",
        difficulte: "Difficile",
        listeMots: [
            "|S|J%Q|H|M%",
            "AUNE|USAGER",
            "|ROUCOULADE",
            "CHINOISE%E%",
            "|USER|HISSE",
            "AME|SAINT%X",
            "|ATTAR|EOLE",
            "LITIGES%RIA",
            "%NERE%AVENT"
        ],
        listeIndices: [
            "QUI NOUS DEPASSE", "DEPASSAIT LE METRE",
            "FAIT CEINTURE", "AKENE OVOÏDE",
            "COMME CELA", "PARFUM D'INTERIEUR",
            "BOUCHEE D'ORIENT",
            "VOISINS DES PERSES", "HAUT DE GAMME",
            "UN POINT SUR L'ATLANTIQUE",
            "HOMME TRANSPORTE", "HAUT DE ROBE",
            "MUSIQUE DE BISET", "ERGOTE",
            "VENITIEN SOPHISTIQUE",
            "BILLET DE SORTIE",
            "AMENER A LA REFORME", "CHEF D'ETATS",
            "BIEN ELEVE",
            "DIVISION DE TERRITOIRE",
            "AMOUR EN AOUT", "ACTIVITE DU CHAMP",
            "QUELQUE CHOSE QUI SE TRAME", "POETE PERSAN",
            "AFFAIRE EN COUR", "AUTEUR DU COUP DE SIROCCO",
            "FIRME",
            "VALLEE ENGLOUTIE",
            "CAROUBIER AFRICAIN", "IL COMPTE QUATRE DIMANCHES"
        ],
        solutionLigne: [
            "AUNE", "USAGER", "ROUCOULADE", "CHINOISE", "USER", "HISSE", "AME", "SAINT", "ATTAR", "EOLE", "LITIGES", "RIA", "NERE", "AVENT"
        ],
        solutionColonne: [
            "SURHUMAIN", "NOISETTE", "JEUNE", "TIR", "CORSAGE", "QUOI", "ARE", "SUSHI", "SA", "HALEINE", "GA", "STORE", "MEDES", "LIN", "RE", "EXEAT"
        ]
    },
    {
        nom: "Démineur",
        difficulte: "Facile",
        nbMines : 10,
    },
    {
        nom: "Démineur",
        difficulte: "Moyen",
        nbMines : 20,
    },
    {
        nom: "Démineur",
        difficulte: "Difficile",
        nbMines : 30,
    }

]
