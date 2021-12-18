package lima.jogodaforca.view;

import java.io.IOException;

import lima.jogodaforca.utils.Console;
import lima.jogodaforca.utils.TerminalUtils;

public class TerminalMenuView {
	
	public TerminalMenuView() {
		super();
	}

	public int imprimirMenu() throws IOException {
		TerminalUtils.imprimirCabecalho(" JOGO DA FORCA ");
		System.out.println();
		int opcao = 0;
		while (true) {
			try {
				System.out.println("1 - Login");
				System.out.println("2 - Cadastrar jogador");
				System.out.println("0 - Sair");
				System.out.print(">>");
				opcao = Console.readInteger();
				if ((opcao < 0) || (opcao > 2)) {
					TerminalUtils.imprimirMensagem("Opcão " + opcao + " não existe no menu");
					System.out.println();
					continue;
				}
			} catch (NumberFormatException e) {
				TerminalUtils.imprimirMensagem("Digite um número inteiro");
				System.out.println();
				continue;
			}
			break;
		}
		return opcao;
	}
}
