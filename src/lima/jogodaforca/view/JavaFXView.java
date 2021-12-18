package lima.jogodaforca.view;

import lima.jogodaforca.exceptions.ViewException;

public class JavaFXView implements View {

	private JavaFXMenuView jfxMenu;
	private JavaFXLoginView jfxLogin;
	private JavaFXCadastroJogadorView jfxCadastroJogador;
	private JavaFXTemaView jfxTema;

	public JavaFXView() {
		super();
		this.jfxMenu = new JavaFXMenuView();
		this.jfxLogin = new JavaFXLoginView();
		this.jfxCadastroJogador = new JavaFXCadastroJogadorView();
		this.jfxTema = new JavaFXTemaView();
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
	public int selecionarTema() throws ViewException {
		return this.jfxTema.exibirSelecaoTema();
	}
}
