/**
 * Classe que implementa o nó da árvore Rubro Negra.
 * 
 * @author rafaelrodrigues
 *
 */
public class Node {
	Node esquerda;
	Node direita;
	Node pai;
	String key;

	// Black == True; Red == False;
	boolean cor;

	public Node(String key) {
		this.pai = this.esquerda = this.direita = Arvore.nil;
		this.key = key;
		this.cor = false;
	}

	public Node(String key, boolean cor) {
		this.pai = this.esquerda = this.direita = Arvore.nil;
		this.key = key;
		this.cor = cor;
	}
	
	public Node getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(Node esquerda) {
		this.esquerda = esquerda;
	}

	public Node getDireita() {
		return direita;
	}

	public void setDireita(Node direita) {
		this.direita = direita;
	}

	public Node getPai() {
		return pai;
	}

	public void setPai(Node pai) {
		this.pai = pai;
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
