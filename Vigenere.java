package securiteL3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Chafik DALI, Josselin DIBON, Romain STASYSZYN
 */
class Vigenere {
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

   

    /**
     * Permet de chiffrer un texte selon la m�thode du codage de Vigenere
     * @param chaineDeBase Le texte source � modifier
     * @param decalage	La valeur du d�calage
     * @return Le texte chiffr�
     */
    public static StringBuilder decalage(String chaineDeBase, String decalage) {
        StringBuilder nouveau = new StringBuilder();

        for (int i = 0; i < chaineDeBase.length(); i++) {
            if (Character.isWhitespace(chaineDeBase.charAt(i))) {
                nouveau.append(" ");
            } else {
                int decal = alphabet.indexOf(decalage.charAt(i % (decalage.length())));
                int index = alphabet.indexOf(chaineDeBase.charAt(i)); //recupere l'indice dans notre alphabet
                if (index != -1) {
                    nouveau.append(alphabet.charAt((index + decal + 26) % 26)); //on ajoute la lettre permutee
                } else {
                    nouveau.append(chaineDeBase.charAt(i));
                }
            }
        }
        nouveau.append("\n");
        return nouveau;
    }
    
    /**
     *Permet de dechiffrer un texte selon la m�thode du codage de Vigenere
     * @param chaineDeBase Le texte source � modifier
     * @param decalage	La valeur du d�calage
     * @return Le texte d�chiffr�
     */
    public static StringBuilder decalageBis(String chaineDeBase, String decalage) {
        StringBuilder nouveau = new StringBuilder();

        for (int i = 0; i < chaineDeBase.length(); i++) {
            if (Character.isWhitespace(chaineDeBase.charAt(i))) {
                nouveau.append(" ");
            } else {
                int decal = alphabet.indexOf(decalage.charAt(i % (decalage.length()))); // r�cup la distance de decalage
                int index = alphabet.indexOf(chaineDeBase.charAt(i)); //recupere l'indice dans notre alphabet
                if (index != -1) {
                    nouveau.append(alphabet.charAt((index - decal + 26) % 26)); //on ajoute la lettre correspondante de l'alphabet
                } else {
                    nouveau.append(chaineDeBase.charAt(i));
                }
            }
        }
        nouveau.append("\n");
        return nouveau;
    }
    
    /**
     * Supprime tous les accents d'un texte
     * @param chaineDeBase Le texte source
     * @return Le texte sans accents
     */
    public static String removeAccents(String chaineDeBase) {
        StringBuffer result = new StringBuffer();

        if (chaineDeBase != null && chaineDeBase.length() != 0) {
            int index = -1;
            char c = (char) 0;
            String chars = "�����������������";
            String replace = "aaaaeeeeiiiuuuooy";
            for (int i = 0; i < chaineDeBase.length(); i++) {
                c = chaineDeBase.charAt(i);
                if ((index = chars.indexOf(c)) != -1) {
                    result.append(replace.charAt(index));
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }

    public void chiffrer(String decalage, String fichier) {
        StringBuilder buffer = new StringBuilder();

        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                line = removeAccents(line);
                buffer.append(decalage(line, decalage));
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        System.out.println(buffer);
    }

    public void dechiffrer(String decalage, String fichier) {
        StringBuilder buffer = new StringBuilder();

        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(decalageBis(line, decalage));
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(buffer);
    }

    /**
     * Renvoie la position de la lettre la plus fr�quente dans l'alphabet.
     *
     * @param texteChiffrer le texte chiffrer qu'on veut analyser.
     * @return l'index de la lettre la plus frequente du texte passer en param�tre.
     */
    public int lettrePlusFrequente(String texteChiffrer) {
        char[] lettresAlphabet = alphabet.toCharArray();
        int[] compteur = new int[26];
        int compteurMax = 0;
        int indexResultat = 0;

        for (int i = 0; i < 26; i++) {
            compteur[i] = 0;
        }

        for (int i = 0; i < texteChiffrer.length(); i++) {
            for (int j = 0; j < 26; j++) {
                if (texteChiffrer.charAt(i) == lettresAlphabet[j]) {
                    compteur[j] += 1;
                    break;
                }
            }
        }

        for (int i = 0; i < 26; i++) {
            if (compteur[i] > compteurMax) {
                indexResultat = i;
                compteurMax = compteur[i];
            }
        }

        return indexResultat;
    }

    /**
     * Retrouve la cle en utilisant les lettres les plus fr�quentes en fonction de la taille de la clef.
     *
     * @param texteChiffrer le texte chiffr� avec Vigen�re.
     * @param tailleCle     la taille de clef utilis�e pour le chiffrement.
     * @return la clef du texte chiffr� avec Vigen�re.
     */
    public String casseCle(String texteChiffrer, int tailleCle) {
        char[] lettresAlphabet = alphabet.toCharArray();
        String cle = "";

        int tailleTexte = texteChiffrer.length();

        for (int i = 0; i < tailleCle; i++) {
            String lettres = "";
            int j = i;

            while (j < tailleTexte) {
                lettres += texteChiffrer.charAt(j);
                j = j + tailleCle;
            }

            int indexLettre = lettrePlusFrequente(lettres);
            int indexCle = (indexLettre - 4 + 26) % 26;
            cle += lettresAlphabet[indexCle];
        }
        return cle;
    }

    /**
     * Decrypte un fichier chiffr� par la m�thode de Vigen�re en utilisant uniquement la longueur de la cl�.
     *
     * @param fichier   le fichier chiffr�.
     * @param tailleCle la taille de la cle utilis�e.
     */
    public  void decrypter(String fichier, int tailleCle) {
        StringBuilder texte = new StringBuilder();

        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            while ((line = br.readLine()) != null) {
                texte.append(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        String texteChiffrer = texte.toString();
        String cle = casseCle(texteChiffrer, tailleCle);
        System.out.println("La cl� utilis�e pour le d�cryptage est: " + cle);
        dechiffrer(cle, fichier);
    }
}