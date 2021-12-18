package lima.jogodaforca.view;

import java.io.IOException;
import lima.jogodaforca.exceptions.ViewException;

public class TerminalView implements View {

	private TerminalMenuView tMenu;
	private TerminalLoginView tLogin;
	private TerminalCadastroJogadorView tCadastroJogador;
	private TerminalNotificacaoView tNotifica;
	private TerminalTemaView tTema;

	public TerminalView() {
		super();
		this.tMenu = new TerminalMenuView();
		this.tLogin = new TerminalLoginView();
		this.tCadastroJogador = new TerminalCadastroJogadorView();
		this.tNotifica = new TerminalNotificacaoView();
		this.tTema = new TerminalTemaView();
	}
	
	@Override
	public int menu() throws ViewException {
		try {
			return this.tMenu.imprimirMenu();
		} catch (IOException e) {
			throw new ViewException("Erro de I/O ao escolher opção do menu principal ", e);
		}
	}

	@Override
	public String[] login() throws ViewException {
		try {
			return this.tLogin.imprimirLogin();
		} catch (IOException e) {
			throw new ViewException("Erro de I/O ao realizar login", e);
		}
	}

	@Override
	public String[] cadastrarJogador() throws ViewException {
		try {
			return this.tCadastroJogador.imprimirCadastro();
		} catch (IOException e) {
			throw new ViewException("Erro de I/O ao realizar cadastro jogador", e);
		}
	}
	
	@Override
	public void notificarUsuario(String mensagem) {
		this.tNotifica.imprimirNotificacao(mensagem);
	}
	
	@Override
	public void notificarUsuario(Exception exception) {
		this.tNotifica.imprimirNotificacaoErro(exception);
	}
	
	@Override
	public int selecionarTema() throws ViewException {
		try {
			return this.tTema.imprimirSelecaoTema();
		} catch (IOException e) {
			throw new ViewException("Erro de I/O ao realizar cadastro jogador", e);
		}
	}
}
