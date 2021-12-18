package lima.jogodaforca.view;

import java.io.IOException;

import lima.jogodaforca.utils.Console;
import lima.jogodaforca.utils.TerminalUtils;

public class TerminalCadastroJogadorView {

	private String login;
	private String password;
	private String nome;

	public TerminalCadastroJogadorView() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getNome() {
		return nome;
	}

	public String[] imprimirCadastro() throws IOException {
		String[] dados = new String[3];

		TerminalUtils.imprimirMensagem(" CADASTRO JOGADOR ");
		System.out.println();
		System.out.println("Login:");
		System.out.print(">>");
		this.login = Console.readString();
		System.out.println("Password:");
		System.out.print(">>");
		this.password = Console.readString();
		System.out.println("Nome:");
		System.out.print(">>");
		this.nome = Console.readString();

		dados[0] = this.login;
		dados[1] = this.password;
		dados[2] = this.nome;
		return dados;
	}
}
