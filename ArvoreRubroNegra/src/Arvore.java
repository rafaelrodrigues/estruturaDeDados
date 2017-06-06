/**
 * Classe que implementa uma árvore Rubro Negra. 
 * 
 * @author rafaelrodrigues
 *
 */
public class Arvore {

	public Node raiz;
	public Node result;

	//Sentinela
	public static Node nil = new Node("", true);
	
	public static final boolean PRETO = true;
	public static final boolean VERMELHO = false;

	public String auxSaida;
	
	public Arvore() {
		this.raiz = Arvore.nil;
	}

	
	private void rbLeftRotate(Arvore arvore, Node x) {
		Node y = x.direita;
		x.direita = y.esquerda;

		if (y.esquerda != Arvore.nil) {
			y.esquerda.pai = x;
		}

		y.pai = x.pai;

		if (x.pai == Arvore.nil) {
			arvore.raiz = y;
		} else if (x == x.pai.esquerda) {
			x.pai.esquerda = y;
		} else {
			x.pai.direita = y;
		}

		y.esquerda = x;
		x.pai = y;
	}

	private void rbRightRotate(Arvore arvore, Node x) {
		Node y = x.esquerda;
		x.esquerda = y.direita;

		if (y.direita != Arvore.nil) {
			y.direita.pai = x;
		}

		y.pai = x.pai;

		if (x.pai == Arvore.nil) {
			arvore.raiz = y;
		} else if (x == x.pai.direita) {
			x.pai.direita = y;
		} else {
			x.pai.esquerda = y;
		}

		y.direita = x;
		x.pai = y;
	}
	
	public void rbInsert(Arvore arvore, Node z) {

		Node y = Arvore.nil;
		Node x = arvore.raiz;

		while (x != Arvore.nil) {
			y = x;

			if (z.key.toLowerCase().compareTo(x.key.toLowerCase()) < 0) {
				x = x.esquerda;
			} else {
				x = x.direita;
			}
		}

		z.pai = y;

		if (y == Arvore.nil) {
			arvore.raiz = z;
		} else if (z.key.toLowerCase().compareTo(y.key.toLowerCase()) < 0) {
			y.esquerda = z;
		} else {
			y.direita = z;
		}

		z.esquerda = Arvore.nil;
		z.direita = Arvore.nil;
		z.cor = VERMELHO;
		
		rbInsertFixUp(arvore, z);
		
	}

	private void rbInsertFixUp(Arvore arvore, Node z) {
		while (!z.pai.cor) {
			if (z.pai.pai != null && z.pai == z.pai.pai.esquerda) {
				Node y = z.pai.pai.direita;
				// Caso 1
				if (y != null && y.cor == VERMELHO) {
					z.pai.cor = PRETO;
					y.cor = PRETO;
					z.pai.pai.cor = VERMELHO;
					z = z.pai.pai;
				} else {
					// Caso 2
					if (z == z.pai.direita) {
						z = z.pai;
						rbLeftRotate(arvore, z);
					}

					z.pai.cor = PRETO;
					z.pai.pai.cor = VERMELHO;
					rbRightRotate(arvore, z.pai.pai);
				}
			} else {
				if (z.pai.pai != null && z.pai == z.pai.pai.direita) {
					Node y = z.pai.pai.esquerda;
					// Caso 1
					if (y != null && !y.cor) {
						z.pai.cor = PRETO;
						y.cor = PRETO;
						z.pai.pai.cor = VERMELHO;
						z = z.pai.pai;
					} else {
						// Caso 2
						if (z == z.pai.esquerda) {
							z = z.pai;
							rbRightRotate(arvore, z);
						}

						z.pai.cor = PRETO;
						z.pai.pai.cor = VERMELHO;
						rbLeftRotate(arvore, z.pai.pai);
					}
				}
			}
		}
		arvore.raiz.cor = PRETO;
	}

	private void rbTransplant(Arvore arb, Node u, Node v) {
		if (u.pai == Arvore.nil) {
			arb.raiz = v;
		} else if (u == u.pai.esquerda) {
			u.pai.esquerda = v;
		} else 
			u.pai.direita = v;
		if (v != null)
			v.pai = u.pai;
	}

	public void rbDelete(Arvore arvore, Node z) {
		Node x, y = z;
		boolean auxCor = y.cor;

		if (z.esquerda == Arvore.nil) {
			x = z.direita;
			rbTransplant(arvore, z, z.direita);
		} else if (z.direita == Arvore.nil) {
			x = z.esquerda;
			rbTransplant(arvore, z, z.esquerda);
		} else {
			y = rbMinimo(z.direita);
			auxCor = y.cor;
			x = y.direita;

			if (y.pai == z) {
				if (x != null)
					x.pai = y;
			} else {
				rbTransplant(arvore, y, y.direita);
				y.direita = z.direita;
				y.direita.pai = y;
			}

			rbTransplant(arvore, z, y);
			y.esquerda = z.esquerda;
			y.esquerda.pai = y;
			y.cor = z.cor;
		}

		if ((auxCor == PRETO)) {
			rbDeleteFixUp(arvore, x);
		}
	}

	private void rbDeleteFixUp(Arvore arvore, Node x) {
		Node w = null;
		
		while (x != arvore.raiz && x.cor) {
			if (x == x.pai.esquerda) {
				w = x.pai.direita;
				if (w != null) {
					if (w.cor == VERMELHO) { // Caso 1
						w.cor = PRETO;
						x.pai.cor = VERMELHO;
						rbLeftRotate(arvore, x.pai);
						w = x.pai.direita;
					}

					if (w.esquerda.cor == PRETO && w.direita.cor == PRETO) { // Caso 2
						w.cor = VERMELHO;
						x = x.pai;
					
					} else { 
						if (w.direita.cor == PRETO) { // Caso 3
							w.esquerda.cor = PRETO;
							w.cor = VERMELHO;
							rbRightRotate(arvore, w);
							w = x.pai.direita;
						}	
						// Caso 4
						w.cor = x.pai.cor;
						x.pai.cor = PRETO;
						w.direita.cor = PRETO;
						rbLeftRotate(arvore, x.pai);
						x = arvore.raiz;
					}	
				} else {
					break;
				}
			} else if (x == x.pai.direita){
				w = x.pai.esquerda;
				if (w != null) {
					if (w.cor == VERMELHO) { // Caso 1
						w.cor = PRETO;
						x.pai.cor = VERMELHO;
						rbRightRotate(arvore, x.pai);
						w = x.pai.esquerda;
					}
					if (w.esquerda.cor == PRETO && w.direita.cor == PRETO) { // Caso 2
						w.cor = VERMELHO;
						x = x.pai;
					
					} else { 
						if (w.esquerda.cor == PRETO) { // Caso 3
							w.direita.cor = PRETO;
							w.cor = VERMELHO;
							rbLeftRotate(arvore, w);
							w = x.pai.esquerda;
						}
						// Caso 4
						w.cor = x.pai.cor;
						x.pai.cor = PRETO;
						w.esquerda.cor = PRETO;
						rbRightRotate(arvore, x.pai);
						x = arvore.raiz;
					}	
					
				} else {
					break;
				}
			}
		}
		x.cor = PRETO;
	}

	private Node rbMinimo(Node busca) {
		result = Arvore.nil;

		if (busca.esquerda == Arvore.nil) {
			result = busca;
		} else {
			rbMinimo(busca.esquerda);
		}

		return result;
	}
	
	public void rbCheck(Node no) { 
		if (no != Arvore.nil) {
			int altura = rbAlturaNo(no);

			String pai = "";
			if (no == this.raiz) {
				pai = "NIL";
			} else {
				pai = no.pai.key;
			}

			String esq = "";
			if (no.esquerda == Arvore.nil) {
				esq = "NIL";
			} else {
				esq = no.esquerda.key;
			}

			String dir = "";
			if (no.direita == Arvore.nil) {
				dir = "NIL";
			} else {
				dir = no.direita.key;
			}

			auxSaida += "(" + pai + ", " + no.key + ", " + getColor(no) + ", " + String.valueOf(altura) + ", " + esq + ", " + dir + ")\n";
			
			rbCheck(no.esquerda);
			rbCheck(no.direita);
		}
	}
	
	public String rbCheckOut(Node no){
		auxSaida = "";
		rbCheck(no);
		return auxSaida;
	}
	
	private int rbAlturaNo(Node no) {
		int contador = 1;
		while (no.esquerda != Arvore.nil) {
			no = no.esquerda;
			if (no.cor == PRETO)
				contador++;
		}
		return contador;
	}

	public void rbPrint(Node no) {
		if (no != Arvore.nil) {
			rbPrint(no.esquerda);
			auxSaida += no.key + " ";
			rbPrint(no.direita);
		}
	}
	
	public String rbPrintOut(Node no){
		auxSaida = "";
		rbPrint(no);
		return auxSaida.trim().replace(" ",", ");
	}

	public Node rbBusca(Arvore arvore, String valor) {
		Node no = rbBusca(arvore.raiz, valor);
		rbCheck(no);
		return no;
	}
	
	public Node rbBusca(Node node, String valor) {
		result = Arvore.nil;
		if (node != Arvore.nil) {
			if (node.key.toLowerCase().compareTo(valor.toLowerCase()) == 0)
				return node;

			if (node.key.toLowerCase().compareTo(valor.toLowerCase()) > 0) {
				result = rbBusca(node.esquerda, valor);
			} else {
				result = rbBusca(node.direita, valor);
			}
		}
		return result;

	}

	private String getColor(Node no) {
		return no.cor ? "preto" : "vermelho";
	}

}
