package lima.jogodaforca.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lima.jogodaforca.exceptions.ModelException;
import lima.jogodaforca.exceptions.ViewException;
import lima.jogodaforca.model.Dicionario;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Login;
import lima.jogodaforca.model.Palavra;
import lima.jogodaforca.view.TerminalView;
import lima.jogodaforca.view.View;

public class JogoController {

	private static final int MAX_TENTATIVAS = 6;
	
	private View jogoViews;
	private Jogador jogador;
	private Palavra palavra;
	private int erros;
	private Set<Character> caracteres;

	public JogoController() {
		super();
		this.jogoViews = new TerminalView();
		// this.jogoViews = new JavaFXView();
		this.jogador = null;
		this.palavra = null;
		this.erros = 0;
		this.caracteres = new HashSet<>();
	}
	
	public void iniciarApp() {
		try {
			while (true) {
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
						this.jogador = Jogador.pesquisar(l);
						if (this.jogador == null) {
							this.jogador = new Jogador(cadastro[2], l);
							result = this.jogador.cadastrar();
							if (result == true) {
								this.jogoViews.notificarUsuario("Jogador " + cadastro[2] + " cadastrado com sucesso");
								System.out.println();
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
							this.jogador = Jogador.pesquisar(l);
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
				} else if (opcao == 0) {
					System.exit(0);
				}
			}
		} catch (ViewException e) {
			this.jogoViews.notificarUsuario(e.getMessage());
			Throwable cause = e.getCause();
			if (cause != null) {
				if (cause instanceof java.io.IOException) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	private void iniciarJogo() {
		try {
			int opcao = -1;
			try {
				while (true) {
					Dicionario dicionario = Dicionario.getDicionario();
					List<String> temas = dicionario.carregarTemas();
					opcao = this.jogoViews.selecionarTema(temas);
					if (opcao == 0) {
						break;
					}
					String palavra = dicionario.sotearPalavra(opcao);
					this.palavra = new Palavra(palavra);
					String tema = temas.get(opcao - 1);
					
					boolean vitoria = false;
					char caractere = ' ';
					while (this.erros < JogoController.MAX_TENTATIVAS) {
						if (this.palavra.getMascara().equalsIgnoreCase(palavra)) {
							vitoria = true;
							break;
						}
						while (true) {
							caractere = this.jogoViews.jogar(this.jogador, tema, this.palavra, this.erros);
							if (Character.isLetter(caractere) == false) {
								this.jogoViews.notificarUsuario("caractere inválido");
								continue;
							}
							if (this.caracteres.contains(Character.toUpperCase(caractere)) == true) {
								this.jogoViews.notificarUsuario("caractere " + caractere + " já utilizado");
								continue;
							}
							this.caracteres.add(Character.toUpperCase(caractere));
							break;
						}
						boolean result = this.palavra.verificarCaractere(Character.toUpperCase(caractere));
						if (result == false) {
							this.erros++;
						}
					}
					if (vitoria == true) {
						this.jogador.incrementarVitorias();
						System.out.println();
						this.jogoViews.notificarResultado("Você venceu o jogo", this.jogador, tema, this.palavra, this.erros);
						System.out.println();
					} else {
						this.jogador.incrementarDerrotas();
						System.out.println();
						this.jogoViews.notificarResultado("Você perdeu o jogo", this.jogador, tema, this.palavra, this.erros);
						System.out.println();
					}
					this.erros = 0;
					this.caracteres.clear();
					
					// atualiza pontuação
					this.jogador.update();
				}
			} catch (ModelException e) {
				this.jogoViews.notificarUsuario(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		} catch (ViewException e) {
			this.jogoViews.notificarUsuario(e.getMessage());
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
