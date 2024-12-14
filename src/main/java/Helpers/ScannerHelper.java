package Helpers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerHelper {

	public static int recogerValor(Scanner teclado) {
		int valor = 0;
		boolean isNumeric = false;
		while(!isNumeric)
			try {
				valor = teclado.nextInt(); 
		        isNumeric = true;
		        } catch(InputMismatchException ime) {
		        	teclado.nextLine();
		        	}
		return valor;
	}
	
}
