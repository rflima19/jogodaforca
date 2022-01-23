package lima.jogodaforca.DAO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import lima.jogodaforca.exceptions.ModelException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Login;

public class JogadorDataBaseDAO implements JogadorDAO {

	private Properties props;
	private String driverClass;
	private String url;
	private String user;
	private String password;

	public JogadorDataBaseDAO() {
		super();
		Path arquivoProps = Paths.get("database.properties");
		try {
			this.props = new Properties();
			this.props.load(Files.newInputStream(arquivoProps, StandardOpenOption.READ));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.driverClass = this.props.getProperty("jdbc.driver");
		this.url = this.props.getProperty("jdbc.url");
		this.user = this.props.getProperty("jdbc.user");
		this.password = this.props.getProperty("jdbc.pass");
//		try {
//			Class.forName(this.driverClass);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			System.exit(1);
//		}
	}

	@Override
	public boolean cadastrarJogador(Jogador jogador) throws ModelException {
		String sql = "INSERT INTO jogador (login, pw, nome, qtd_vitorias, qtd_derrotas) " + 
						"VALUES (?, ?, ?, ?, ?);";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, this.gerarHashSHA256(jogador.getLogin().getLogin()));
				stmt.setString(2, this.gerarHashSHA256(jogador.getLogin().getPassword()));
				stmt.setString(3, jogador.getNome());
				stmt.setInt(4, jogador.getVitorias());
				stmt.setInt(5, jogador.getDerrotas());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModelException("Falha ao cadastrar jogador", e);
		}
		return true;
	}

	@Override
	public Jogador pequisarJogador(Login login) throws ModelException {
		Jogador jogador = null;
		String sql = "SELECT j.nome, j.qtd_vitorias, j.qtd_derrotas FROM jogador j "
				+ "WHERE j.login = ? AND j.pw = ?;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				String l = this.gerarHashSHA256(login.getLogin());
				String p = this.gerarHashSHA256(login.getPassword());
				stmt.setString(1, l);
				stmt.setString(2, p);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next() == true) {
						jogador = new Jogador(rs.getString(1), rs.getInt(2), rs.getInt(3), login);
					}
				}
			}
		} catch (SQLException e) {
			throw new ModelException("Falha ao pesquisar jogador", e);
		}
		return jogador;
	}

	@Override
	public boolean update(Jogador jogador) throws ModelException {
		String sql = "UPDATE jogador j SET j.nome = ?, j.qtd_vitorias = ?, j.qtd_derrotas = ? "
				+ "WHERE j.login = ? AND j.pw = ?;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				String l = this.gerarHashSHA256(jogador.getLogin().getLogin());
				String p = this.gerarHashSHA256(jogador.getLogin().getPassword());
				stmt.setString(4, l);
				stmt.setString(5, p);
				stmt.setString(1, jogador.getNome());
				stmt.setInt(2, jogador.getVitorias());
				stmt.setInt(3, jogador.getDerrotas());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new ModelException("Falha atualizar jogador", e);
		}
		return true;
	}

	private String gerarHashSHA256(String str) {
		StringBuilder hexHash = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA256");
			byte[] hash = md.digest(str.getBytes("UTF-8"));

			for (byte b : hash) {
				hexHash.append(String.format("%02X", 0xFF & b));
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexHash.toString();
	}
}
