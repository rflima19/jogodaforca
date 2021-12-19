package lima.jogodaforca.DAO;

import java.util.List;

import lima.jogodaforca.exceptions.ModelException;

public interface DicionarioDAO {

	public abstract List<String> carregarTemas() throws ModelException;

	public abstract String sotearPalavra(String nomeArquivo) throws ModelException;
}
