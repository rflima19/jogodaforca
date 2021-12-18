package lima.jogodaforca.controller;

import lima.jogodaforca.exceptions.ModelException;
import lima.jogodaforca.exceptions.ViewException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Login;
import lima.jogodaforca.view.JavaFXView;
import lima.jogodaforca.view.TerminalView;
import lima.jogodaforca.view.View;

public class JogoController {

	private View jogoViews;
	
	private Jogador jogador;

	public JogoController() {
		super();
		this.jogoViews = new TerminalView();
		// this.jogoViews = new JavaFXView();
		this.jogador = null;
	}
	
	public void iniciarApp() {
		try {
			int opcao = this.jogoViews.menu();
			if (opcao == 2) {
				String[] cadastro = null;
				boolean result = false;
				while (result == false) {
					cadastro = this.jogoViews.cadastrarJogador();
					try { 
						result = Login.validar(cadastro[0], cadastro[1]) && 
								Jogador.validarNome(cadastro[2]);
					} catch (ModelException e) {
						this.jogoViews.notificarUsuario(e.getMessage());
						System.out.println();
					}
				}	
				try {
					Login l = new Login(cadastro[0], cadastro[1]);
					// Jogador j = Jogador.pesquisar(l);
					this.jogador = Jogador.pesquisar(l);
					// if (j == null) {
					if (this.jogador == null) {
						// j = new Jogador(cadastro[2], l);
						this.jogador = new Jogador(cadastro[2], l);
						// result = j.cadastrar();
						result = this.jogador.cadastrar();
						if (result == true) {
							this.jogoViews.notificarUsuario("Jogador " + cadastro[2] + " cadastrado com sucesso");
						}
					} else {
						this.jogoViews.notificarUsuario("Já existe um jogador cadastrado com login '" + cadastro[0] + "'");
						System.out.println();
					}
				} catch (ModelException e) {
					this.jogoViews.notificarUsuario(e.getMessage());
					System.out.println();
				}
			} else if (opcao == 1) {
				boolean result = false;
				while (result == false) {
					String[] login = this.jogoViews.login();
					try {
						result = Login.validar(login[0], login[1]);
					} catch (ModelException e) {
						this.jogoViews.notificarUsuario(e.getMessage());
						System.out.println();
						continue;
					}
					try {
						Login l = new Login(login[0], login[1]);
						// Jogador jogador = Jogador.pesquisar(l);
						this.jogador = Jogador.pesquisar(l);
						// if (jogador == null) {
						if (this.jogador == null) {
							this.jogoViews.notificarUsuario("Login e password inválidos");
							System.out.println();
							result = false;
						}
					} catch (ModelException e) {
						this.jogoViews.notificarUsuario(e.getMessage());
						System.out.println();
						result = true;
					}
				}
				
				this.iniciarJogo();
			}
		} catch (ViewException e) {
			System.out.println(e.getMessage());
			Throwable cause = e.getCause();
			if (cause != null) {
				if (cause instanceof java.io.IOException) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	public void iniciarJogo() {
		try {
			int opcao = this.jogoViews.selecionarTema();
		} catch (ViewException e) {
			System.out.println(e.getMessage());
			Throwable cause = e.getCause();
			if (cause != null) {
				if (cause instanceof java.io.IOException) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
}
