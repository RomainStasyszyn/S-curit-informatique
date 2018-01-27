package securiteL3;

import java.io.*;

/**
 * @author Chafik DALI, Josselin DIBON, Romain STASYSZYN
 */
class Cesar implements Code {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private File fichierLexique = new File("lexique.txt");
    private String lexique = lireFichier(fichierLexique);

    /**
     * Permet de chiffrer un texte selon la méthode du codage de César
     * @param chaineDeBase Le texte source à modifier
     * @param decalage	La valeur du décalage
     * @return Le texte chiffré
     */
    public static StringBuilder decalage(String chaineDeBase, int decalage) {
        StringBuilder nouveau = new StringBuilder();

        for (int i = 0; i < chaineDeBase.length(); i++) {
            if (Character.isWhitespace(chaineDeBase.charAt(i))) {
                nouveau.append(" ");
            } else {
                int index = alphabet.indexOf(chaineDeBase.charAt(i)); //recupere l'indice dans notre alphabet
                if (index != -1) {
                    nouveau.append(alphabet.charAt((index + decalage) % 26));
                } else {
                    nouveau.append(chaineDeBase.charAt(i));
                }
            }
        }
        nouveau.append("\n");
        return nouveau;
    }
    
    /**
     * Permet de dechiffrer un texte selon la méthode du codage de César
     * @param chaineDeBase Le texte source à modifier
     * @param decalage	La valeur du décalage
     * @return Le texte déchiffré
     */
    public static StringBuilder decalageBis(String chaineDeBase, int decalage) {
        StringBuilder dechiffre = new StringBuilder();

        for (int i = 0; i < chaineDeBase.length(); i++) {
            if (Character.isWhitespace(chaineDeBase.charAt(i))) {
                dechiffre.append(" ");
            } else {
                int index = alphabet.indexOf(chaineDeBase.charAt(i)); //recupere l'indice dans notre alphabet
                if (index != -1) {
                    dechiffre.append(alphabet.charAt(((index - decalage) + 26) % 26));
                } else {
                    dechiffre.append(chaineDeBase.charAt(i));
                }
            }
        }
        dechiffre.append("\n");
        return dechiffre;
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

    @Override
    public void chiffrer(String cle, String fichier) {
        StringBuilder buffer = new StringBuilder();
        int valeurCle = Integer.parseInt(cle);
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                line = removeAccents(line);
                buffer.append(decalage(line, valeurCle));
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(buffer);

    }

    @Override
    public void dechiffrer(String cle, String fichier) {
        StringBuilder buffer = new StringBuilder();
        int valeurCle = Integer.parseInt(cle);
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                line = removeAccents(line);
                buffer.append(decalageBis(line, valeurCle));                
            } 
            br.close();
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(buffer);

    }

    /**
     * Permet de déchiffrer un fichier avec la méthode de la force brute
     * @param fichier Le nom du fichier à déchiffrer
     */
    public void dechiffrerForceBrute(String fichier) {
        StringBuilder buffer = new StringBuilder();
        String[] bufferSplited = null;
        StringBuilder mot = new StringBuilder();
        File f = new File(fichier);
        String contenu = lireFichier(f);//contenu du fichier source
        contenu = contenu.toLowerCase();//on passe tout en minuscule
        String lexiquePropre = removeAccents(lexique); //enleve les accents du dictonnaire pour comparer le resultat du buffer
        boolean decodage = false;
        int i = 1;
        int nbMotsCorrects = 0;

        try {
            while (decodage == false && i < 26) { //on repete l'opération jusqu'a trouver un resultat valable

                buffer.setLength(0); //on reset le buffer
                buffer = decalageBis(contenu, i);//decalage de i
                bufferSplited = buffer.toString().split("[\\p{P} \\t\\n\\r]");// on recupere chaque mot de la ligne
                nbMotsCorrects = 0;
                for (int j = 0; j < bufferSplited.length; j++) {//pour chaque mot du buffer
                    mot.setLength(0);
                    for (int k = 0; k < bufferSplited[j].length(); k++) {//on verifie la forme des mots (pas de , ou de : etc)
                        if (Character.isLetter((bufferSplited[j].charAt(k)))) {//si la lettre appartient a l alphabet on l ajoute
                            mot.append(bufferSplited[j].charAt(k));
                        }
                    }
                    if (lexiquePropre.contains(mot)) {//si le mot appartient au dictionnaire

                        nbMotsCorrects += 1; //on incrémente le compteur
                        if (nbMotsCorrects == bufferSplited.length) {
                            decodage = true; //si tous les mots du texte appartient au dico, alors on a trouvé
                            System.out.println("Voici le message décodé, le décalage était de : " + i);
                            System.out.println(buffer);
                        }
                    }
                }
                i++;
            }
            if (i > 25) {
                System.out.println("Impossible de trouver un décodage correct, vérifiez votre codage");
            }

        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Permet de déchiffrer un fichier en analysant la fréquence d'apparition des lettres
     * @param fichier Le nom du fichier à déchiffrer
     */
    public void dechifferFrequence(String fichier){
		StringBuilder buffer = new StringBuilder();
		int[] frequenceLettre=new int[26];
		File f=new File(fichier);
		String alphabetFrequence = "esaitnrulodcpmvqfbghjxyzwk";
		int indiceDuMax=0;
		int decalage=0;
		String[] bufferSplited=null;
		StringBuilder mot=new StringBuilder();
		String contenu=lireFichier(f);//contenu du fichier source
		contenu = contenu.toLowerCase();//on passe tout en minuscule
		String lexiquePropre=removeAccents(lexique); //enleve les accents du dictonnaire pour comparer le resultat du buffer
		boolean decodage=false;
		int index=0;
		int nbMotsCorrects=0;
		int a=1;
		
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
			while(decodage==false && a<26){ //on repete l'opération jusqu'a trouver un resultat valable et pas plus de 25 fois
					
				buffer.setLength(0); //on reset le buffer
				indiceDuMax=trouveIndiceDuMax(frequenceLettre);
				
				int indexLettreTeste=alphabet.indexOf(alphabetFrequence.charAt(index));// on recupere l index dans l alphabet de la lettre qu on test actuellement
				decalage=(indiceDuMax-indexLettreTeste+26)%26;//on en deduit le decalage 
				buffer=decalageBis(contenu,decalage);//decalage de i
				bufferSplited=buffer.toString().split("[\\p{P} \\t\\n\\r]");// on recupere chaque mot de la ligne
				nbMotsCorrects=0;
				for(int j=0;j<bufferSplited.length;j++){//pour chaque mot du buffer
					mot.setLength(0);
					for(int k=0;k<bufferSplited[j].length();k++){//on verifie la forme des mots (pas de , ou de : etc)
						if(Character.isLetter((bufferSplited[j].charAt(k)))){//si la lettre appartient a l alphabet on l ajoute
							mot.append(bufferSplited[j].charAt(k));	
						}	
					}	
					
					if(lexiquePropre.contains(mot)){//si le mot appartient au dictionnaire
						nbMotsCorrects+=1; //on incrémente le compteur
	
						if(nbMotsCorrects==bufferSplited.length){
							decodage=true; //si tous les mots du texte appartient au dico, alors on a trouvé
							System.out.println("Voici le message décodé, le décalage était de : "+decalage);
							System.out.println(buffer);
						}
					}	
				}
				
				index+=1;
				a++;
		}
		if (a > 25) {
            System.out.println("Impossible de trouver un décodage correct, vérifiez votre codage");
        }
		
		br.close();
		}
			
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		
	}
    
    /**
     * Permet de déchiffrer un fichier lorsqu'un mot est connu 
     * @param motConnu Le mot en clair
     * @param fichier Le nom du fichier à déchiffrer
     */
    public void dechiffrer_un_mot_connu(String motConnu, String fichier)
	{
		StringBuilder buffer = new StringBuilder();
		StringBuilder bufferFinal = new StringBuilder();
		int cle = decalageProbable(motConnu, fichier);
		String[] bufferSplited=buffer.toString().split("[\\p{P} \\t\\n\\r]");
		StringBuilder mot=new StringBuilder();
		boolean decodage=false;
		int i=1;
		int nbMotsCorrects=0;
		String lexiquePropre=removeAccents(lexique);
		
		
		try 
		{
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while((line = br.readLine()) != null) 
			{
				buffer.append(line);	
			}
			br.close();
			
			bufferSplited=buffer.toString().split(" ");// on recupere chaque mot de la ligne
			for(int j=0;j<bufferSplited.length;j++){//pour chaque mot du buffer
				mot.setLength(0);
				for(int k=0;k<bufferSplited[j].length();k++){//on verifie la forme des mots (pas de , ou de : etc)
					if(Character.isLetter((bufferSplited[j].charAt(k)))){//si la lettre appartient a l alphabet on l ajoute
						mot.append(bufferSplited[j].charAt(k));	
					}	
				}	
				bufferFinal.append(decalageBis(bufferSplited[j],cle) + " ");
				if(lexiquePropre.contains(mot)){//si le mot appartient au dictionnaire
					nbMotsCorrects+=1; //on incrémente le compteur
				}
			}

			if(nbMotsCorrects==bufferSplited.length){
				decodage=true; //si tous les mots du texte appartient au dico, alors on a trouvé
				System.out.println("Voici le message décodé, le décalage était de : "+ cle);
				System.out.print(bufferFinal);
			}	
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
	}
	
	public static int decalageProbable(String motConnu, String fichier)
	{
		int decalage = 0;
		int decalagePossible = 0;
		int compteur = 0;
		StringBuilder buffer = new StringBuilder();
		String[] bufferSplited=buffer.toString().split("[\\p{P} \\t\\n\\r]");
		
		try 
		{
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String lignelue;
			StringBuilder lignedecalee = new StringBuilder();
			while((lignelue = br.readLine()) != null) 
			{
				buffer.append((lignelue)+"\n");
			}
			br.close();
			
			bufferSplited=buffer.toString().split(" ");
			for(compteur = 0 ; compteur < bufferSplited.length ; compteur++)
			{
				if(bufferSplited[compteur].length() == motConnu.length())
				{
					char premiereLettre = bufferSplited[compteur].charAt(0);
					char premiereLettreConnue = motConnu.charAt(0);
					if(alphabet.indexOf(premiereLettreConnue) > alphabet.indexOf(premiereLettre)){
						decalagePossible = alphabet.indexOf(premiereLettre)-alphabet.indexOf(premiereLettreConnue);
					}
					lignedecalee = decalageBis(bufferSplited[compteur], decalagePossible);
					lignedecalee = lignedecalee.deleteCharAt(lignedecalee.length()-1);
					
					if((lignedecalee.toString()).equals(motConnu))
					{
						return decalagePossible;
					}
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		return decalagePossible;
	}
	
	/**
	 * Trouve l'indice de la plus grande valeur d'un tableau
	 * @param tableau Un tableu d'entier
	 * @return L'indice du maximum
	 */
	public int trouveIndiceDuMax(int tableau[]) {

        int indiceMax = 0;
        int max = 0;
        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i] > max) {
                max = tableau[i];
                indiceMax = i;
            }
        }

        tableau[indiceMax] = 0;
        return indiceMax;
    }

	/**
	 * Lit le contenu d'un ficher et le place dans un String
	 * @param f Le fichier à lire
	 * @return Un String correspondant au contenu du fichier
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

	

}