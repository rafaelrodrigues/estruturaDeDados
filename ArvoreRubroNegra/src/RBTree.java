/**
 * Classe que implementa uma árvore Rubro Negra. 
 * 
 * @author rafaelrodrigues
 *
 */
public class RBTree {

	public RBElement root;
	public RBElement result;

	//Sentinela
	public static RBElement nil = new RBElement("", true);
	
	public static final boolean BLACK = true;
	public static final boolean RED = false;

	public String auxExit;
	
	public RBTree() {
		this.root = RBTree.nil;
	}

	
	private void rbLeftRotate(RBTree T, RBElement z) {
		RBElement y = z.right;
		z.right = y.left;

		if (y.left != RBTree.nil) {
			y.left.parent = z;
		}

		y.parent = z.parent;

		if (z.parent == RBTree.nil) {
			T.root = y;
		} else if (z == z.parent.left) {
			z.parent.left = y;
		} else {
			z.parent.right = y;
		}

		y.left = z;
		z.parent = y;
	}

	private void rbRightRotate(RBTree T, RBElement x) {
		RBElement y = x.left;
		x.left = y.right;

		if (y.right != RBTree.nil) {
			y.right.parent = x;
		}

		y.parent = x.parent;

		if (x.parent == RBTree.nil) {
			T.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}

		y.right = x;
		x.parent = y;
	}
	
	public void rbInsert(RBTree T, RBElement z) {

		RBElement y = RBTree.nil;
		RBElement x = T.root;

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
			T.root = z;
		} else if (z.key.toLowerCase().compareTo(y.key.toLowerCase()) < 0) {
			y.left = z;
		} else {
			y.right = z;
		}

		z.left = RBTree.nil;
		z.right = RBTree.nil;
		z.color = RED;
		
		rbInsertFixUp(T, z);
		
	}

	private void rbInsertFixUp(RBTree T, RBElement x) {
		while (!x.parent.color) {
			if (x.parent.parent != null && x.parent == x.parent.parent.left) {
				RBElement y = x.parent.parent.right;
				// Caso 1
				if (y != null && y.color == RED) {
					x.parent.color = BLACK;
					y.color = BLACK;
					x.parent.parent.color = RED;
					x = x.parent.parent;
				} else {
					// Caso 2
					if (x == x.parent.right) {
						x = x.parent;
						rbLeftRotate(T, x);
					}

					x.parent.color = BLACK;
					x.parent.parent.color = RED;
					rbRightRotate(T, x.parent.parent);
				}
			} else {
				if (x.parent.parent != null && x.parent == x.parent.parent.right) {
					RBElement y = x.parent.parent.left;
					// Caso 1
					if (y != null && !y.color) {
						x.parent.color = BLACK;
						y.color = BLACK;
						x.parent.parent.color = RED;
						x = x.parent.parent;
					} else {
						// Caso 2
						if (x == x.parent.left) {
							x = x.parent;
							rbRightRotate(T, x);
						}

						x.parent.color = BLACK;
						x.parent.parent.color = RED;
						rbLeftRotate(T, x.parent.parent);
					}
				}
			}
		}
		T.root.color = BLACK;
	}

	private void rbTransplant(RBTree T, RBElement u, RBElement v) {
		if (u.parent == RBTree.nil) {
			T.root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else 
			u.parent.right = v;
		if (v != null)
			v.parent = u.parent;
	}

	public void rbDelete(RBTree T, RBElement z) {
		RBElement x, y = z;
		boolean auxCor = y.color;

		if (z.left == RBTree.nil) {
			x = z.right;
			rbTransplant(T, z, z.right);
		} else if (z.right == RBTree.nil) {
			x = z.left;
			rbTransplant(T, z, z.left);
		} else {
			y = rbTreeMinimum(z.right);
			auxCor = y.color;
			x = y.right;

			if (y.parent == z) {
				if (x != null)
					x.parent = y;
			} else {
				rbTransplant(T, y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			rbTransplant(T, z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}

		if ((auxCor == BLACK)) {
			rbDeleteFixUp(T, x);
		}
	}

	private void rbDeleteFixUp(RBTree T, RBElement x) {
		RBElement w = null;
		
		while (x != T.root && x.color) {
			if (x == x.parent.left) {
				w = x.parent.right;
				if (w != null) {
					if (w.color == RED) { // Caso 1
						w.color = BLACK;
						x.parent.color = RED;
						rbLeftRotate(T, x.parent);
						w = x.parent.right;
					}

					if (w.left.color == BLACK && w.right.color == BLACK) { // Caso 2
						w.color = RED;
						x = x.parent;
					
					} else { 
						if (w.right.color == BLACK) { // Caso 3
							w.left.color = BLACK;
							w.color = RED;
							rbRightRotate(T, w);
							w = x.parent.right;
						}	
						// Caso 4
						w.color = x.parent.color;
						x.parent.color = BLACK;
						w.right.color = BLACK;
						rbLeftRotate(T, x.parent);
						x = T.root;
					}	
				} else {
					break;
				}
			} else if (x == x.parent.right){
				w = x.parent.left;
				if (w != null) {
					if (w.color == RED) { // Caso 1
						w.color = BLACK;
						x.parent.color = RED;
						rbRightRotate(T, x.parent);
						w = x.parent.left;
					}
					if (w.left.color == BLACK && w.right.color == BLACK) { // Caso 2
						w.color = RED;
						x = x.parent;
					
					} else { 
						if (w.left.color == BLACK) { // Caso 3
							w.right.color = BLACK;
							w.color = RED;
							rbLeftRotate(T, w);
							w = x.parent.left;
						}
						// Caso 4
						w.color = x.parent.color;
						x.parent.color = BLACK;
						w.left.color = BLACK;
						rbRightRotate(T, x.parent);
						x = T.root;
					}	
					
				} else {
					break;
				}
			}
		}
		x.color = BLACK;
	}

	private RBElement rbTreeMinimum(RBElement T) {
		result = RBTree.nil;

		if (T.left == RBTree.nil) {
			result = T;
		} else {
			rbTreeMinimum(T.left);
		}

		return result;
	}
	
	public void rbCheck(RBElement z) { 
		if (z != RBTree.nil) {
			int altura = rbBlackHeight(z);

			String pai = "";
			if (z == this.root) {
				pai = "NIL";
			} else {
				pai = z.parent.key;
			}

			String esq = "";
			if (z.left == RBTree.nil) {
				esq = "NIL";
			} else {
				esq = z.left.key;
			}

			String dir = "";
			if (z.right == RBTree.nil) {
				dir = "NIL";
			} else {
				dir = z.right.key;
			}

			auxExit += "(" + pai + ", " + z.key + ", " + getColor(z) + ", " + String.valueOf(altura) + ", " + esq + ", " + dir + ")\n";
			
			rbCheck(z.left);
			rbCheck(z.right);
		}
	}
	
	public String rbCheckOut(RBElement z){
		auxExit = "";
		rbCheck(z);
		return auxExit;
	}
	
	private int rbBlackHeight(RBElement z) {
		int contador = 1;
		while (z.left != RBTree.nil) {
			z = z.left;
			if (z.color == BLACK)
				contador++;
		}
		return contador;
	}
	
	public void rbPrint(RBElement z) {
		if (z != RBTree.nil) {
			rbPrint(z.left);
			auxExit += z.key + " ";
			rbPrint(z.right);
		}
	}
	
	public String rbPrintOut(RBElement z){
		auxExit = "";
		rbPrint(z);
		return auxExit.trim().replace(" ",", ");
	}

	public RBElement rbSearch(RBTree T, String c) {
		RBElement x = rbSearch(T.root, c);
		rbCheck(x);
		return x;
	}
	
	public RBElement rbSearch(RBElement z, String c) {
		result = RBTree.nil;
		if (z != RBTree.nil) {
			if (z.key.toLowerCase().compareTo(c.toLowerCase()) == 0)
				return z;

			if (z.key.toLowerCase().compareTo(c.toLowerCase()) > 0) {
				result = rbSearch(z.left, c);
			} else {
				result = rbSearch(z.right, c);
			}
		}
		return result;

	}

	private String getColor(RBElement z) {
		return z.color ? "preto" : "vermelho";
	}

}
