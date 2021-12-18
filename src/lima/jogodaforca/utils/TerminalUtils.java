package lima.jogodaforca.utils;

import java.io.IOException;

public class TerminalUtils {

	private static final char CARACTERE = '*';
	private static final int LIMITE = 120;

	public static Integer menuOpcoes(String titulo, String[] titulos) throws IOException {
		if (titulos == null) {
			throw new IllegalArgumentException("Argumento nulo");
		}
		if (titulos.length == 0) {
			throw new IllegalArgumentException("Array com tamanho 0");
		}

		int opcao = 0;
		while (true) {
			TerminalUtils.imprimirCabecalho(titulo);
			for (int i = 0; i < titulos.length; i++) {
				System.out.println((i + 1) + " - " + titulos[i]);
			}
			try {
				System.out.print(">>");
				opcao = Console.readInteger();
			} catch (NumberFormatException e) {
				TerminalUtils.imprimirMensagemErro("Digite um valor inteiro");
				System.out.printf("%n%n");
				continue;
			}
			break;
		}
		return opcao;
	}

	public static void imprimirMensagem(String texto) {
		TerminalUtils.imprimirLinha();
		System.out.printf("%n\t%s%n", texto);
		TerminalUtils.imprimirLinha();
	}

	public static void imprimirMensagemErro(String texto) {
		String s = String.format("\tErro: %s", texto);
		TerminalUtils.imprimirMensagem(s);
	}

	public static void imprimirMensagemErro(Exception exc) {
		String s = String.format("\tErro: %s", exc.getMessage());
		TerminalUtils.imprimirMensagem(s);
	}

	public static void imprimirCabecalho(String titulo) {
		int l = titulo.length();
		TerminalUtils.imprimirLinha();
		System.out.println();
		TerminalUtils.imprimirLinha((TerminalUtils.LIMITE / 2) - (l / 2));
		System.out.print(titulo);
		if (l % 2 == 0) {
			TerminalUtils.imprimirLinha((TerminalUtils.LIMITE / 2) - (l / 2));
		} else {
			TerminalUtils.imprimirLinha((TerminalUtils.LIMITE / 2) - (l / 2) - 1);
		}
		System.out.println();
		TerminalUtils.imprimirLinha();
		System.out.println();
	}

	private static void imprimirLinha() {
		for (int i = 0; i < TerminalUtils.LIMITE; i++) {
			System.out.print(TerminalUtils.CARACTERE);
		}
	}

	private static void imprimirLinha(int limite) {
		for (int i = 0; i < limite; i++) {
			System.out.print(TerminalUtils.CARACTERE);
		}
	}
}
