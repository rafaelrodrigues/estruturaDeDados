package redBlackTrees;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * Classe principal para execução do programa. 
 * 
 * @author rafaelrodrigues
 */
public class Main {

	public enum typeFileParameter {FIXED,ARGS,OPENDIALOG};
	
	public static void main(String[] args) {

		FileReader f = null;
		StringBuffer saida;
		RBTree arb;

		f = getFile(typeFileParameter.FIXED, args);
		
//		f = new FileReader(args[0]);
		saida = new StringBuffer();
		arb = new RBTree();
		
//		saida.append("Arquivo de Entrada: ");
		
		lerArquivoPopularArvoreRB(f, saida, arb);
		printAndCheckRBTree(saida, arb);
		gravarSaida(saida);
			
	}

	private static FileReader getFile(typeFileParameter type, String[] args) {
		try {
			switch (type.ordinal()) {
				case 0 : 
					File file = new File("/Users/rafaelrodrigues/Dev/workspace/dicionario.txt");
					return new FileReader(file);
				case 1 :
					return new FileReader(args[0]);
				case 2 :
					JFileChooser fileChoose = new JFileChooser(); 
					fileChoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChoose.showOpenDialog(null);
					return new FileReader(fileChoose.getSelectedFile());
				default :
					return null;
			}
			
		} catch (Exception e) {
			System.out.print("ERRO AO LER O DICIONÁRIO");
			return null;
		}
	}

	private static void lerArquivoPopularArvoreRB(FileReader f, StringBuffer saida, RBTree arb) {
		Scanner entrada = new Scanner(f);
		
		while (entrada.hasNext()) {
			String value = entrada.next();
			
			Node no = arb.rbBusca(arb.root, value);
			switch (entrada.next()) {
			case "1":
				if (no == RBTree.nil) {
					if (value.length() <= 20) {
						no = new Node(value);
						arb.rbInsert(arb, no);
					} else {
						saida.append("\n\nA palavra " + value + " É maior que 20 caracteres");
					}
				} else {
					saida.append("\n\nA palavra " + value + " já existe.");
				}
				break;
			case "0":
				if (no != RBTree.nil) {
					saida.append("\n\nRemovendo a palavra " + value + ".");
					arb.rbDelete(arb, no);
					printAndCheckRBTree(saida, arb);
				} else {
					saida.append("\n\nA palavra "+value+" foi removida anteriormente ou não foi inserida.");
				}
				break;

			}
		}
		entrada.close();
	}

	private static void printAndCheckRBTree(StringBuffer saida, RBTree arb) {
		saida.append("\n\n");
		saida.append(arb.rbPrintOut(arb.root));
		saida.append("\n");
		saida.append(arb.rbCheckOut(arb.root));
	}
	
	public static void gravarSaida(StringBuffer strSaida){
		File arquivo = new File("saida.txt");
	    try {
	    	FileWriter fw = new FileWriter(arquivo);  
	    	BufferedWriter bw = new BufferedWriter(fw);  
	    	System.out.println(strSaida.toString());
	    	bw.write(strSaida.toString());  
	    	bw.flush();  
	    	bw.close();  
	    	
	    } catch (IOException ex) {
	    	System.out.print("ERRO AO GRAVAR ARQUIVO DE SAÍDA");
	    }
	}
}