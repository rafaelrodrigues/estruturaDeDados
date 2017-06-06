
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principal para execução do programa. 
 * 
 * @author rafaelrodrigues
 */
public class Main {

	public static void main(String[] args) {

		FileReader f = null;
		StringBuffer saida;
		Arvore arb;
		try {

			f = new FileReader(args[0]);
			saida = new StringBuffer();
			arb = new Arvore();
			
			saida.append("Arquivo de Entrada: "+args[0]);
			
			lerArquivoPopularArvoreRB(f, saida, arb);
			printAndCheckRBTree(saida, arb);
			gravarSaida(saida);
			
		} catch (FileNotFoundException e) {
			System.out.print("ERRO AO LER O DICIONÁRIO");
		} 
	}

	private static void lerArquivoPopularArvoreRB(FileReader f, StringBuffer saida, Arvore arb) {
		Scanner entrada = new Scanner(f);
		
		while (entrada.hasNext()) {
			String value = entrada.next();
			
			Node no = arb.rbBusca(arb.raiz, value);
			switch (entrada.next()) {
			case "1":
				if (no == Arvore.nil) {
					if (value.length() <= 20) {
						no = new Node(value);
						arb.rbInsert(arb, no);
					} else {
						saida.append("\n\nA palavra " + value + " é maior que 20 caracteres");
					}
				} else {
					saida.append("\n\nA palavra " + value + " já existe.");
				}
				break;
			case "0":
				if (no != Arvore.nil) {
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

	private static void printAndCheckRBTree(StringBuffer saida, Arvore arb) {
		saida.append("\n\n");
		saida.append(arb.rbPrintOut(arb.raiz));
		saida.append("\n");
		saida.append(arb.rbCheckOut(arb.raiz));
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