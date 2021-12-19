package lima.jogodaforca.view;

import java.io.IOException;
import java.util.List;

import lima.jogodaforca.exceptions.ViewException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Palavra;

public class TerminalView implements View {

	private TerminalMenuView tMenu;
	private TerminalLoginView tLogin;
	private TerminalCadastroJogadorView tCadastroJogador;
	private TerminalNotificacaoView tNotifica;
	private TerminalTemaView tTema;
	private TerminalJogoView tJogo;

	public TerminalView() {
		super();
		this.tMenu = new TerminalMenuView();
		this.tLogin = new TerminalLoginView();
		this.tCadastroJogador = new TerminalCadastroJogadorView();
		this.tNotifica = new TerminalNotificacaoView();
		this.tTema = new TerminalTemaView();
		this.tJogo = new TerminalJogoView();
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
	public int selecionarTema(List<String> temas) throws ViewException {
		try {
			return this.tTema.imprimirSelecaoTema(temas);
		} catch (IOException e) {
			throw new ViewException("Erro de I/O ao realizar cadastro jogador", e);
		}
	}
	
	@Override
	public char jogar(Jogador jogador, String tema, Palavra palavra, int quantidadeErros) throws ViewException {
		try {
			return this.tJogo.imprimirJogada(jogador, tema, palavra, quantidadeErros);
		} catch (IOException e) {
			throw new ViewException("Erro de I/O ao realizar jogada", e);
		}
	}
	
	@Override
	public void notificarResultado(String mensagem, Jogador jogador, String tema, Palavra palavra, int quantidadeErros) {
		this.tJogo.imprimirResultado(mensagem, jogador, tema, palavra, quantidadeErros);
	}
}
