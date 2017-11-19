/**
 * Classe que implementa o nó da árvore Rubro Negra.
 * 
 * @author rafaelrodrigues
 *
 */
public class RBElement {
	RBElement left;
	RBElement right;
	RBElement parent;
	String key;

	// Black == True; Red == False;
	boolean color;

	public RBElement(String key) {
		this.parent = this.left = this.right = RBTree.nil;
		this.key = key;
		this.color = false;
	}

	public RBElement(String key, boolean black) {
		this.parent = this.left = this.right = RBTree.nil;
		this.key = key;
		this.color = black;
	}
	
	public RBElement getLeft() {
		return left;
	}

	public void setLeft(RBElement left) {
		this.left = left;
	}

	public RBElement getRight() {
		return right;
	}

	public void setRight(RBElement right) {
		this.right = right;
	}

	public RBElement getParent() {
		return parent;
	}

	public void setParent(RBElement parent) {
		this.parent = parent;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isBlack() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

}
