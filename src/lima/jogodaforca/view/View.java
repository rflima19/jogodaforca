package lima.jogodaforca.view;

import lima.jogodaforca.exceptions.ViewException;

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

	public abstract int selecionarTema() throws ViewException;
}
