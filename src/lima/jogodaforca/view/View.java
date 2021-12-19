package lima.jogodaforca.view;

import java.util.List;

import lima.jogodaforca.exceptions.ViewException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Palavra;

public interface View {

	public abstract int menu() throws ViewException;
	
	/**
	 * Login
	 * @return String[] array de String com o login no index 0 e password no index 1
	 * @throws ViewException
	 */
	public abstract String[] login() throws ViewException;
	
	public abstract String[] cadastrarJogador() throws ViewException;
	
	public abstract void notificarUsuario(String mensagem);
	
	public abstract void notificarUsuario(Exception exception);

	public abstract int selecionarTema(List<String> temas) throws ViewException;
	
	public abstract char jogar(Jogador jogador, String tema, Palavra palavra, int quantidadeErros) throws ViewException;
	
	public abstract void notificarResultado(String mensagem, Jogador jogador, String tema, Palavra palavra, int quantidadeErros);
}
