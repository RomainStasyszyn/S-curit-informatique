package securiteL3;

/**
 * @author Chafik DALI, Josselin DIBON, Romain STASYSZYN
 */
public class Chiffre {
    public static void main(String[] args) {
        String codage = "";
        String cle = args[1];

        if (args.length == 3) {
            switch (args[0]) {
                case "c":
                    codage = "César";
                    System.out.println("Bonjour, vous avez choisi le codage " + codage + " avec comme clé la valeur " + cle);
                    Cesar codeC = new Cesar();
                    codeC.chiffrer(cle, args[2]);
                    break;
                case "p":
                    codage = "Permutation";
                    System.out.println("Bonjour, vous avez choisi le décodage " + codage + " avec la permutation suivante " + cle);
                    Permutation codeP = new Permutation();
                    codeP.chiffrer(cle, args[2]);
                    break;
                case "v":
                    codage = "Vigenere";
                    System.out.println("Bonjour, vous avez choisi le codage " + codage + " avec la clé " + cle);
                    Vigenere codeV = new Vigenere();
                    codeV.chiffrer(cle, args[2]);
                    break;
                default:
                    System.out.println("Erreur d'argument");
            }
        } else {
            System.out.println("Nombre d'arguments incorrect !");
        }
    }

}