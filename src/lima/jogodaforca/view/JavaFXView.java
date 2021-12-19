package lima.jogodaforca.view;

import java.util.List;

import lima.jogodaforca.exceptions.ViewException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Palavra;

public class JavaFXView implements View {

	private JavaFXMenuView jfxMenu;
	private JavaFXLoginView jfxLogin;
	private JavaFXCadastroJogadorView jfxCadastroJogador;
	private JavaFXTemaView jfxTema;
	private JavaFXJogoView jfxJogo;

	public JavaFXView() {
		super();
		this.jfxMenu = new JavaFXMenuView();
		this.jfxLogin = new JavaFXLoginView();
		this.jfxCadastroJogador = new JavaFXCadastroJogadorView();
		this.jfxTema = new JavaFXTemaView();
		this.jfxJogo = new JavaFXJogoView();
	}

	@Override
	public int menu() throws ViewException {
		return this.jfxMenu.exibirMenu();
	}
	
	@Override
	public String[] login() throws ViewException {
		return this.jfxLogin.exibirLogin();
	}
	
	@Override
	public String[] cadastrarJogador() throws ViewException {
		return this.jfxCadastroJogador.exibirCadastroJogador();
	}
	
	@Override
	public void notificarUsuario(Exception exception) {
		
	}
	
	@Override
	public void notificarUsuario(String mensagem) {
		
	}
	
	@Override
	public int selecionarTema(List<String> temas) throws ViewException {
		return this.jfxTema.exibirSelecaoTema(temas);
	}
	
	@Override
	public char jogar(Jogador jogador, String tema, Palavra palavra, int quantidadeLetras) throws ViewException {
		return this.jfxJogo.exibirJogo(jogador, tema, palavra, quantidadeLetras);
	}
	
	@Override
	public void notificarResultado(String mensagem, Jogador jogador, String tema, Palavra palavra,
			int quantidadeErros) {
		
	}
	
}
