package lima.jogodaforca.DAO;

import lima.jogodaforca.exceptions.ModelException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Login;

public interface JogadorDAO {

	public abstract boolean cadastrarJogador(Jogador jogador) throws ModelException;

	public abstract Jogador pequisarJogador(Login login) throws ModelException;

	public abstract boolean update(Jogador jogador);
}
