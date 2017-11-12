package redBlackTrees;
/**
 * Classe que implementa o nó da árvore Rubro Negra.
 * 
 * @author rafaelrodrigues
 *
 */
public class Node {
	Node left;
	Node right;
	Node parent;
	String key;

	// Black == True; Red == False;
	boolean cor;

	public Node(String key) {
		this.parent = this.left = this.right = RBTree.nil;
		this.key = key;
		this.cor = false;
	}

	public Node(String key, boolean cor) {
		this.parent = this.left = this.right = RBTree.nil;
		this.key = key;
		this.cor = cor;
	}
	
	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isCor() {
		return cor;
	}

	public void setCor(boolean cor) {
		this.cor = cor;
	}

}
