package securiteL3;

/**
 * @author Chafik DALI, Josselin DIBON, Romain STASYSZYN
 */
interface Code {
	/**
	 * M�thode de chiffrement
	 * @param cle	La cl� du chiffrement
	 * @param texte	Le texte � chiffrer
	 */
    void chiffrer(String cle, String texte);
    
    /**
     * M�thode de d�chiffrement
	 * @param cle	La cl� du chiffrement
	 * @param texte	Le texte � d�chiffrer
	 */
    void dechiffrer(String cle, String texte);


}