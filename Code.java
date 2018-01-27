package securiteL3;

/**
 * @author Chafik DALI, Josselin DIBON, Romain STASYSZYN
 */
interface Code {
	/**
	 * Méthode de chiffrement
	 * @param cle	La clé du chiffrement
	 * @param texte	Le texte à chiffrer
	 */
    void chiffrer(String cle, String texte);
    
    /**
     * Méthode de déchiffrement
	 * @param cle	La clé du chiffrement
	 * @param texte	Le texte à déchiffrer
	 */
    void dechiffrer(String cle, String texte);


}