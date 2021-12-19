package lima.jogodaforca.model;

import java.util.regex.Pattern;

import lima.jogodaforca.DAO.JogadorArquivoDAO;
import lima.jogodaforca.DAO.JogadorDAO;
import lima.jogodaforca.exceptions.ModelException;

public class Jogador {

	private static JogadorDAO jogadorDAO = new JogadorArquivoDAO();
	
	private String nome;
	private Login login;
	private int vitorias;
	private int derrotas;

	public Jogador(String nome, int vitorias, int derrotas, Login login) /* throws ModelException */ {
		super();
//		if (Jogador.validarNome(nome) == false) {
//			throw new ModelException("Nome " + nome + " n�o � v�lido");
//		}
		this.nome = nome;
		this.login = login;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
	}

	public Jogador(String nome, Login login) /* throws ModelException */ {
		this(nome, 0, 0, login);
	}
	
	public String getNome() {
		return nome;
	}
	
	public Login getLogin() {
		return login;
	}

	public int getVitorias() {
		return vitorias;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public static boolean validarNome(String nome) throws ModelException {
		if ((nome.isBlank() == true) || (nome.isEmpty() == true) ||
				(Pattern.matches("^.*[^A-Za-z�������������������������������'\\s]+.*$", nome) == true)) {
			throw new ModelException("Nome " + nome + " n�o � v�lido");
		}
		return true;
	}
	
	public boolean cadastrar() throws ModelException {
		return Jogador.jogadorDAO.cadastrarJogador(this);
	}
	
	public static Jogador pesquisar(Login login) throws ModelException {
		return Jogador.jogadorDAO.pequisarJogador(login);
	}
	
	public boolean update() {
		return Jogador.jogadorDAO.update(this);
	}
	
	public void incrementarVitorias() {
		this.vitorias++;
	}
	
	public void incrementarDerrotas() {
		this.derrotas++;
	}
}
