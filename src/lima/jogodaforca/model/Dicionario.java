package lima.jogodaforca.model;

import java.util.ArrayList;
import java.util.List;

import lima.jogodaforca.DAO.DicionarioArquivoDAO;
import lima.jogodaforca.DAO.DicionarioDAO;
import lima.jogodaforca.DAO.DicionarioDataBaseDAO;
import lima.jogodaforca.exceptions.ModelException;

public class Dicionario {

	//private static DicionarioDAO dicionarioDAO = new DicionarioArquivoDAO();
	private static DicionarioDAO dicionarioDAO = new DicionarioDataBaseDAO();
	private static Dicionario instance = null;
	
	private List<String> temas;
	private String palavra;
	
	private Dicionario() {
		super();
		this.temas = new ArrayList<>();
	}
	
	public static Dicionario getDicionario() {
		if (Dicionario.instance == null) {
			Dicionario.instance = new Dicionario();
		}
		return Dicionario.instance;
	}

	public List<String> getTemas() {
		return temas;
	}
	
	public String getPalavra() {
		return palavra;
	}

	public List<String> carregarTemas() throws ModelException {
		this.temas = Dicionario.dicionarioDAO.carregarTemas();
		return this.temas;
	}

	public String sotearPalavra(int opcao) throws ModelException {
		String tema = this.temas.get(opcao - 1);
		this.palavra = Dicionario.dicionarioDAO.sotearPalavra(tema);
		return this.palavra;
	}
}
