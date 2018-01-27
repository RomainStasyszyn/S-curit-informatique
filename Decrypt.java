package securiteL3;

/**
 * @author Chafik DALI, Josselin DIBON, Romain STASYSZYN
 */
public class Decrypt {
    public static void main(String[] args) {
        String codage = "";
        if (args.length <= 4) {
            switch (args[0]) {
                case "c":
                    if (args[2].equals("1")) {
                        if (args[3] != null) {
                            codage = "C�sar";
                            System.out.println("Bonjour, vous avez choisi le d�codage " + codage + " bas� sur la connaissance d'un mot");
                            Cesar codeC = new Cesar();
                            long startTime = System.currentTimeMillis();
                            codeC.dechiffrer_un_mot_connu(args[3], args[1]);
	                        long endTime = System.currentTimeMillis();
	                        System.err.println("Temps du decryptage de C�sar avec la connaissance d'un mot: " + (endTime-startTime) + "ms");
	                        
                        } else {
                            System.out.println("Il manque un arugment, renseignez le mot en clair");
                        }
                    } else if (args[2].equals("2")) {
                        codage = "C�sar";
                        System.out.println("Bonjour, vous avez choisi le d�codage " + codage + " bas� sur l'analyse des fr�quences");
                        Cesar codeC = new Cesar();
                        long startTime = System.currentTimeMillis();
                        codeC.dechifferFrequence(args[1]);
                        long endTime = System.currentTimeMillis();
                        System.err.println("Temps du decryptage de C�sar par analyse fr�quentielle: " + (endTime-startTime) + "ms");
                    } else if (args[2].equals("3")) {
                        codage = "C�sar";
                        System.out.println("Bonjour, vous avez choisi le d�codage " + codage + " par force brute");
                        Cesar codeC = new Cesar();
                        long startTime = System.currentTimeMillis();
                        codeC.dechiffrerForceBrute(args[1]);
                        long endTime = System.currentTimeMillis();
                        System.err.println("Temps du decryptage de C�sar par force brute: " + (endTime-startTime) + "ms");
                        
                    } else {
                        System.out.println("Erreur d'argument, r�essayez avec 1,2 ou 3");
                    }

                    break;
                case "p":
                    codage = "Permutation";
                    System.out.println("Bonjour, vous avez choisi le d�codage " + codage);
                    Permutation codeP = new Permutation();
                    long startTime = System.currentTimeMillis();
                    codeP.dechiffrerSansCle(args[1]);
                    long endTime = System.currentTimeMillis();
                    System.err.println("Temps du decryptage partiel par permutation: " + (endTime-startTime) + "ms");
                    break;
                case "v":
                    if (args[2] != null) {
                        codage = "Vigenere";
                        System.out.println("Bonjour, vous avez choisi le d�codage de " + codage);
                        int tailleCle = Integer.parseInt(args[2]);
                        Vigenere codeV = new Vigenere();
                        long start = System.currentTimeMillis();
                        codeV.decrypter(args[1], tailleCle);;
                        long end = System.currentTimeMillis();
                        System.err.println("Temps du decryptage Vigenere: " + (end-start) + "ms");
                        
                    } else {
                        System.out.println("Il manque un arugment, renseignez la longueur du mot-cl�");
                    }
                    break;
                default:
                    System.out.println("Erreur d'argument");
            }
        } else {
            System.out.println("Nombre d'arguments incorrect !");
        }
    }
}
