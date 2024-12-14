package it.unicam.cs.asdl2425.mp1;

/**
 * Rappresenta un nodo di un albero di Merkle.
 * 
 * @author Luca Tesei, Marco Caputo (template) **INSERIRE NOME, COGNOME ED EMAIL
 *         xxxx@studenti.unicam.it DELLO STUDENTE** (implementazione)
 */
public class MerkleNode {
    private final String hash; // Hash associato al nodo.

    private final MerkleNode left; // Figlio sinistro del nodo.

    private final MerkleNode right; // Figlio destro del nodo.

    /**
     * Costruisce un nodo Merkle foglia con un valore di hash, quindi,
     * corrispondente all'hash di un dato.
     *
     * @param hash
     *                 l'hash associato al nodo.
     */
    public MerkleNode(String hash) {
        this(hash, null, null);
    }

    /**
     * Costruisce un nodo Merkle con un valore di hash e due figli, quindi,
     * corrispondente all'hash di un branch.
     *
     * @param hash
     *                  l'hash associato al nodo.
     * @param left
     *                  il figlio sinistro.
     * @param right
     *                  il figlio destro.
     */
    public MerkleNode(String hash, MerkleNode left, MerkleNode right) {
        this.hash = hash;
        this.left = left;
        this.right = right;
    }

    /**
     * Restituisce l'hash associato al nodo.
     *
     * @return l'hash associato al nodo.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Restituisce il figlio sinistro del nodo.
     *
     * @return il figlio sinistro del nodo.
     */
    public MerkleNode getLeft() {
        return left;
    }

    /**
     * Restituisce il figlio destro del nodo.
     *
     * @return il figlio destro del nodo.
     */
    public MerkleNode getRight() {
        return right;
    }

    /**
     * Restituisce true se il nodo è una foglia, false altrimenti.
     *
     * @return true se il nodo è una foglia, false altrimenti.
     */
    public boolean isLeaf() {
        // TODO implementare
		// Se il nodo non ha figli(ergo i nodi a sx e dx sono vuoti) è una foglia
		return (this.left == null && this.right == null);
    }

    @Override
    public String toString() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO implementare
		if(this == obj) return true;	//confronto riferimenti, se uguali lo saranno anche gli oggetti
		if(obj == null || this.getClass() != obj.getClass()) return false; //confronto tipi

		return ((MerkleNode) obj).hash.equals(this.hash); //infine, confronto hash
        /* due nodi sono uguali se hanno lo stesso hash */

    }

    @Override
    public int hashCode() {
        // TODO implementare
		StringBuilder str = new StringBuilder();
        str.append(this.hash).append(this.isLeaf());

        return HashUtil.dataToHash(str.toString()).hashCode();
    }
}