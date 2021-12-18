package lima.jogodaforca.view;

import java.io.IOException;

import lima.jogodaforca.utils.Console;
import lima.jogodaforca.utils.TerminalUtils;

public class TerminalLoginView {

	private String login;
	private String password;

	public TerminalLoginView() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String[] imprimirLogin() throws IOException {

		String[] login = new String[2];

		TerminalUtils.imprimirMensagem(" LOGIN ");
		System.out.println();
		System.out.println("Login:");
		System.out.print(">>");
		this.login = Console.readString();
		System.out.println("Password:");
		System.out.print(">>");
		this.password = Console.readString();
		
		login[0] = this.login;
		login[1] = this.password;
		return login;
	}
}
