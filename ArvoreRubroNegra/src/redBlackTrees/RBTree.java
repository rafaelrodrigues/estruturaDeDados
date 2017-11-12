package redBlackTrees;
/**
 * Classe que implementa uma árvore Rubro Negra. 
 * 
 * @author rafaelrodrigues
 *
 */
public class RBTree {

	public Node root;
	public Node result;

	//Sentinela
	public static Node nil = new Node("", true);
	
	public static final boolean BLACK = true;
	public static final boolean RED = false;

	public String auxExit;
	
	public RBTree() {
		this.root = RBTree.nil;
	}

	
	private void rbLeftRotate(RBTree tree, Node x) {
		Node y = x.right;
		x.right = y.left;

		if (y.left != RBTree.nil) {
			y.left.parent = x;
		}

		y.parent = x.parent;

		if (x.parent == RBTree.nil) {
			tree.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}

		y.left = x;
		x.parent = y;
	}

	private void rbRightRotate(RBTree arvore, Node x) {
		Node y = x.left;
		x.left = y.right;

		if (y.right != RBTree.nil) {
			y.right.parent = x;
		}

		y.parent = x.parent;

		if (x.parent == RBTree.nil) {
			arvore.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}

		y.right = x;
		x.parent = y;
	}
	
	public void rbInsert(RBTree arvore, Node z) {

		Node y = RBTree.nil;
		Node x = arvore.root;

		while (x != RBTree.nil) {
			y = x;

			if (z.key.toLowerCase().compareTo(x.key.toLowerCase()) < 0) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		z.parent = y;

		if (y == RBTree.nil) {
			arvore.root = z;
		} else if (z.key.toLowerCase().compareTo(y.key.toLowerCase()) < 0) {
			y.left = z;
		} else {
			y.right = z;
		}

		z.left = RBTree.nil;
		z.right = RBTree.nil;
		z.cor = RED;
		
		rbInsertFixUp(arvore, z);
		
	}

	private void rbInsertFixUp(RBTree arvore, Node z) {
		while (!z.parent.cor) {
			if (z.parent.parent != null && z.parent == z.parent.parent.left) {
				Node y = z.parent.parent.right;
				// Caso 1
				if (y != null && y.cor == RED) {
					z.parent.cor = BLACK;
					y.cor = BLACK;
					z.parent.parent.cor = RED;
					z = z.parent.parent;
				} else {
					// Caso 2
					if (z == z.parent.right) {
						z = z.parent;
						rbLeftRotate(arvore, z);
					}

					z.parent.cor = BLACK;
					z.parent.parent.cor = RED;
					rbRightRotate(arvore, z.parent.parent);
				}
			} else {
				if (z.parent.parent != null && z.parent == z.parent.parent.right) {
					Node y = z.parent.parent.left;
					// Caso 1
					if (y != null && !y.cor) {
						z.parent.cor = BLACK;
						y.cor = BLACK;
						z.parent.parent.cor = RED;
						z = z.parent.parent;
					} else {
						// Caso 2
						if (z == z.parent.left) {
							z = z.parent;
							rbRightRotate(arvore, z);
						}

						z.parent.cor = BLACK;
						z.parent.parent.cor = RED;
						rbLeftRotate(arvore, z.parent.parent);
					}
				}
			}
		}
		arvore.root.cor = BLACK;
	}

	private void rbTransplant(RBTree arb, Node u, Node v) {
		if (u.parent == RBTree.nil) {
			arb.root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else 
			u.parent.right = v;
		if (v != null)
			v.parent = u.parent;
	}

	public void rbDelete(RBTree arvore, Node z) {
		Node x, y = z;
		boolean auxCor = y.cor;

		if (z.left == RBTree.nil) {
			x = z.right;
			rbTransplant(arvore, z, z.right);
		} else if (z.right == RBTree.nil) {
			x = z.left;
			rbTransplant(arvore, z, z.left);
		} else {
			y = rbMinimo(z.right);
			auxCor = y.cor;
			x = y.right;

			if (y.parent == z) {
				if (x != null)
					x.parent = y;
			} else {
				rbTransplant(arvore, y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			rbTransplant(arvore, z, y);
			y.left = z.left;
			y.left.parent = y;
			y.cor = z.cor;
		}

		if ((auxCor == BLACK)) {
			rbDeleteFixUp(arvore, x);
		}
	}

	private void rbDeleteFixUp(RBTree arvore, Node x) {
		Node w = null;
		
		while (x != arvore.root && x.cor) {
			if (x == x.parent.left) {
				w = x.parent.right;
				if (w != null) {
					if (w.cor == RED) { // Caso 1
						w.cor = BLACK;
						x.parent.cor = RED;
						rbLeftRotate(arvore, x.parent);
						w = x.parent.right;
					}

					if (w.left.cor == BLACK && w.right.cor == BLACK) { // Caso 2
						w.cor = RED;
						x = x.parent;
					
					} else { 
						if (w.right.cor == BLACK) { // Caso 3
							w.left.cor = BLACK;
							w.cor = RED;
							rbRightRotate(arvore, w);
							w = x.parent.right;
						}	
						// Caso 4
						w.cor = x.parent.cor;
						x.parent.cor = BLACK;
						w.right.cor = BLACK;
						rbLeftRotate(arvore, x.parent);
						x = arvore.root;
					}	
				} else {
					break;
				}
			} else if (x == x.parent.right){
				w = x.parent.left;
				if (w != null) {
					if (w.cor == RED) { // Caso 1
						w.cor = BLACK;
						x.parent.cor = RED;
						rbRightRotate(arvore, x.parent);
						w = x.parent.left;
					}
					if (w.left.cor == BLACK && w.right.cor == BLACK) { // Caso 2
						w.cor = RED;
						x = x.parent;
					
					} else { 
						if (w.left.cor == BLACK) { // Caso 3
							w.right.cor = BLACK;
							w.cor = RED;
							rbLeftRotate(arvore, w);
							w = x.parent.left;
						}
						// Caso 4
						w.cor = x.parent.cor;
						x.parent.cor = BLACK;
						w.left.cor = BLACK;
						rbRightRotate(arvore, x.parent);
						x = arvore.root;
					}	
					
				} else {
					break;
				}
			}
		}
		x.cor = BLACK;
	}

	private Node rbMinimo(Node busca) {
		result = RBTree.nil;

		if (busca.left == RBTree.nil) {
			result = busca;
		} else {
			rbMinimo(busca.left);
		}

		return result;
	}
	
	public void rbCheck(Node no) { 
		if (no != RBTree.nil) {
			int altura = rbAlturaNo(no);

			String pai = "";
			if (no == this.root) {
				pai = "NIL";
			} else {
				pai = no.parent.key;
			}

			String esq = "";
			if (no.left == RBTree.nil) {
				esq = "NIL";
			} else {
				esq = no.left.key;
			}

			String dir = "";
			if (no.right == RBTree.nil) {
				dir = "NIL";
			} else {
				dir = no.right.key;
			}

			auxExit += "(" + pai + ", " + no.key + ", " + getColor(no) + ", " + String.valueOf(altura) + ", " + esq + ", " + dir + ")\n";
			
			rbCheck(no.left);
			rbCheck(no.right);
		}
	}
	
	public String rbCheckOut(Node no){
		auxExit = "";
		rbCheck(no);
		return auxExit;
	}
	
	private int rbAlturaNo(Node no) {
		int contador = 1;
		while (no.left != RBTree.nil) {
			no = no.left;
			if (no.cor == BLACK)
				contador++;
		}
		return contador;
	}

	public void rbPrint(Node no) {
		if (no != RBTree.nil) {
			rbPrint(no.left);
			auxExit += no.key + " ";
			rbPrint(no.right);
		}
	}
	
	public String rbPrintOut(Node no){
		auxExit = "";
		rbPrint(no);
		return auxExit.trim().replace(" ",", ");
	}

	public Node rbBusca(RBTree arvore, String valor) {
		Node no = rbBusca(arvore.root, valor);
		rbCheck(no);
		return no;
	}
	
	public Node rbBusca(Node node, String valor) {
		result = RBTree.nil;
		if (node != RBTree.nil) {
			if (node.key.toLowerCase().compareTo(valor.toLowerCase()) == 0)
				return node;

			if (node.key.toLowerCase().compareTo(valor.toLowerCase()) > 0) {
				result = rbBusca(node.left, valor);
			} else {
				result = rbBusca(node.right, valor);
			}
		}
		return result;

	}

	private String getColor(Node no) {
		return no.cor ? "preto" : "vermelho";
	}

}
