package lima.jogodaforca.view;

import java.io.IOException;
import java.util.List;

import lima.jogodaforca.utils.Console;
import lima.jogodaforca.utils.TerminalUtils;

public class TerminalTemaView {
	
	public TerminalTemaView() {
		super();
	}

	public int imprimirSelecaoTema(List<String> temas) throws IOException {
		TerminalUtils.imprimirMensagem(" SELECIONAR TEMA");
		System.out.println();
		int opcao = 0;
		int cont = 0;
		while (true) {
			try {
				cont = 1;
				for (String string : temas) {
					System.out.println(cont + " - " + string);
					cont++;
				}
				System.out.println("0 - Sair");
				System.out.print(">>");
				opcao = Console.readInteger();
				if ((opcao < 0) || (opcao > temas.size())) {
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
