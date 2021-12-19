package lima.jogodaforca.view;

import java.io.IOException;

import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Palavra;
import lima.jogodaforca.utils.Console;
import lima.jogodaforca.utils.TerminalUtils;

public class TerminalJogoView {

//	private Jogador jogador;
//	private String tema;
//	private Palavra palavra;

	public TerminalJogoView(/*Jogador jogador, String tema, Palavra palavra*/) {
		super();
//		this.jogador = jogador;
//		this.tema = tema;
//		this.palavra = palavra;
	}

	public char imprimirJogada(Jogador jogador, String tema, Palavra palavra, int quantidadeErros) throws IOException {
		char caractere = 0;
		System.out.println();
		System.out.printf("Jogador: %s [Vitórias: %02d, Derrotas: %02d]%n", 
				jogador.getNome(), jogador.getVitorias(), jogador.getDerrotas());
		this.exibirForca(quantidadeErros);
		this.exibirPalavraOculta(tema, palavra.getLetras(), palavra.getMascara());
		System.out.println("Digite uma letra:");
		System.out.print(">>");
		caractere = Console.readCharacter();
		return caractere;
	}
	
	private void exibirPalavraOculta(String tema, int quantidadeLetras, String mascara) {
		System.out.println("Palavra com " + quantidadeLetras + " letras no tema " + tema);
		char[] array = mascara.toCharArray();
		for (int i = 0; i < array.length; i++) {
			System.out.print(" " + Character.toUpperCase(array[i]) + " ");
		}
		System.out.printf("%n%n");
	}
	
	private void exibirForca(int quantidadeErros) {
		String cabeca = (quantidadeErros >= 1) ? "O" : "";
		String bracoDiretiro = (quantidadeErros >= 2) ? "---|" : "";
		String bracoEsquerdo = (quantidadeErros >= 3) ? "---" : "";
		String troco = (quantidadeErros >= 4) ? "|" : "";
		String pernaDireita = (quantidadeErros >= 5) ? "/" : "";
		String pernaEsquerda = (quantidadeErros >= 6) ? "\\" : "";

		System.out.println("__________________");
		System.out.println("|                |");
		System.out.println("|                |");
		System.out.println("|                " + cabeca);
		System.out.println("|             " + bracoDiretiro + bracoEsquerdo);
		System.out.println("|                " + troco);
		System.out.println("|               " + pernaDireita + " " + pernaEsquerda);
		System.out.println("|              " + pernaDireita + "   " + pernaEsquerda);
		System.out.println("|                 ");
		System.out.println("|                 ");
		System.out.println("|                 \n");
	}

	public void imprimirResultado(String mensagem, Jogador jogador, String tema, Palavra palavra, int quantidadeErros) {
		System.out.println(mensagem);
		System.out.printf("Jogador: %s [Vitórias: %02d, Derrotas: %02d]%n", 
				jogador.getNome(), jogador.getVitorias(), jogador.getDerrotas());
		this.exibirForca(quantidadeErros);
		System.out.println("O tema era " + tema);
		System.out.println("A palavra era " + palavra.getPalavra());
	}
}
