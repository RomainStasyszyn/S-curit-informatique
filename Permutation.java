package securiteL3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * @author Chafik DALI, Josselin DIBON, Romain STASYSZYN
 */
class Permutation {
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static String alphabetFrequence = "esaitnrulodcpmvqfbghjxyzwk";
   
    /**
     * Permet de chiffrer un texte selon la méthode de permutation
     * @param chaineDeBase	Le texte source à modifier
     * @param permutation 	La permutation
     * @return 				Le texte chiffré
     */
    public static StringBuilder decalage(String chaineDeBase, String permutation) {
        StringBuilder nouveau = new StringBuilder();

        for (int i = 0; i < chaineDeBase.length(); i++) {
            if (Character.isWhitespace(chaineDeBase.charAt(i))) {
                nouveau.append(" ");
            } else {
                int index = alphabet.indexOf(chaineDeBase.charAt(i)); //recupere l'indice dans notre alphabet
                if (index != -1) {
                    nouveau.append(permutation.charAt(index)); //on ajoute la lettre permutee
                } else {
                    nouveau.append(chaineDeBase.charAt(i));
                }
            }
        }
        nouveau.append("\n");
        return nouveau;
    }
    /**
     * Permet de dechiffrer un texte selon la méthode de permutation
     * @param chaineDeBase 	Le texte source à modifier
     * @param permutation	La permutation
     * @return 				Le texte déchiffré
     */
    public static StringBuilder decalageBis(String chaineDeBase, String permutation) {
        StringBuilder nouveau = new StringBuilder();

        for (int i = 0; i < chaineDeBase.length(); i++) {
            if (Character.isWhitespace(chaineDeBase.charAt(i))) {
                nouveau.append(" ");
            } else {
                int index = permutation.indexOf(chaineDeBase.charAt(i)); //recupere l'indice dans notre permutation
                if (index != -1) {
                    nouveau.append(alphabet.charAt(index)); //on ajoute la lettre correspondante de l'alphabet
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
     * @param chaineDeBase 	Le texte source
     * @return 				Le texte sans accents
     */
    public static String removeAccents(String chaineDeBase) {
        StringBuffer result = new StringBuffer();
        if (chaineDeBase != null && chaineDeBase.length() != 0) {
            int index = -1;
            char c = (char) 0;
            String chars = "àâäåéèêëîïìùûüôöÿ";
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

    /**
     * Chiffre un texte codé par la méthode de permutation
     * @param permutation 		Correspond à la clé de chiffrage
     * @param fichier 			Le fichier à chiffrer
     */
    public void chiffrer(String permutation, String fichier) {
        StringBuilder buffer = new StringBuilder();

        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                line = removeAccents(line);
                buffer.append(decalage(line, permutation));
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        System.out.println(buffer);
    }
    
    /**
     * Déchiffre un texte codé par la méthode de permutation en connaissant la clé
     * @param permutation Correspond à la clé de chiffrage
     * @param fichier Le fichier à déchiffrer
     */
    public void dechiffrer(String permutation, String fichier) {
        StringBuilder buffer = new StringBuilder();

        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(decalageBis(line, permutation));
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        System.out.println(buffer);
    }
    
    /**
     * Effectue un déchiffrage partiel d'un texte codé selon la méthode de permutation
     * @param fichier Le fichier à déchiffrer
     */
    public void dechiffrerSansCle(String fichier){
    	StringBuilder buffer = new StringBuilder();
		int[] frequenceLettre=new int[26];
		File f=new File(fichier);
		
		int indiceDuMax=0;
		String contenu=lireFichier(f);//contenu du fichier source
		contenu = contenu.toLowerCase();//on passe tout en minuscule
		StringBuilder permutation= new StringBuilder(alphabet);
		
		try 
		{
			for(int i=0; i<frequenceLettre.length;i++){
				frequenceLettre[i]=0;
			}
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while((line = br.readLine()) != null) 
			{
				line = line.toLowerCase();
				line = removeAccents(line);
				for(int i=0;i<line.length();i++){
					int c=line.charAt(i);
					if(c>96 && c<123){
						frequenceLettre[c-97]+=1;
					}
				}
				
			}
			for(int i=0;i<26;i++)
				System.out.print(frequenceLettre[i]+" ");
			System.out.println("");
			for(int i=0;i<26;i++){
				indiceDuMax=trouveIndiceDuMax(frequenceLettre);
				int indiceLettreApermuter=alphabet.indexOf(alphabetFrequence.charAt(i));
				permutation.setCharAt(indiceLettreApermuter,alphabet.charAt(indiceDuMax));
				
			}
			buffer=decalageBis(contenu, permutation.toString());
			
		System.out.println(permutation);
			System.out.println(buffer);
		}
		catch(Exception e){
			
		}
    }
    /**
     * Lit le fichier passé en paramètre et place son contenu dans une variable String
     * @param f Le fichier à lire
     * @return Un string correspondant au contenu du fichier
     */
    public String lireFichier(File f) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
            StringWriter out = new StringWriter();
            int b;
            while ((b = in.read()) != -1)
	            out.write(b);
	            out.flush();
	            out.close();
	            in.close();
	            return out.toString();
        } 
        catch (IOException ie) {
        	ie.printStackTrace();
        }
        return null;
    }
    
    /**
	 * Trouve l'indice de la plus grande valeur d'un tableau et gère les égalités
	 * @param tableau Un tableu d'entier
	 * @return L'indice du maximum
	 */
    public int trouveIndiceDuMax(int tableau[]) {
    	ArrayList<Integer> egalite = new ArrayList<Integer>();
        int indiceMax = 0;
        int max = -100;
        int min=27;
        egalite.clear();
        for (int i = 0; i < tableau.length; i++) {
        	
            if (tableau[i] > max) {
                max = tableau[i];
                indiceMax = i;
            }
        }     
        for (int i = 0; i < tableau.length; i++) {
	        if(tableau[i]==max){
	        	egalite.add(i);
	        }
        }
        	for(int i=0;i<egalite.size();i++){
        		if(alphabetFrequence.indexOf(alphabet.charAt(egalite.get(i)))<min){
        			min=egalite.get(i);
        			indiceMax=min;
        		}	
        	}      
        tableau[indiceMax] = -1;
        return indiceMax;
    }
}