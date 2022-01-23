package lima.jogodaforca.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import lima.jogodaforca.exceptions.ModelException;

public class DicionarioDataBaseDAO implements DicionarioDAO {

	private Properties props;
	private String driverClass;
	private String url;
	private String user;
	private String password;

	public DicionarioDataBaseDAO() {
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
	public List<String> carregarTemas() throws ModelException {
		List<String> temas = new ArrayList<>();
		String sql = "SELECT t.nome FROM tema t;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				try (ResultSet rs = stmt.executeQuery()) {
					String tema = null;
					while (rs.next() == true) {
						tema = rs.getString(1);
						temas.add(tema);
					}
				}
			}
		} catch (SQLException e) {
			throw new ModelException("Falha ao carregar temas", e);
		}
		return temas;
	}

	@Override
	public String sotearPalavra(String tema) throws ModelException {
		List<String> palavras = new ArrayList<>();
		String sql = "SELECT d.palavra FROM dicionario d INNER JOIN tema t " + 
						"WHERE d.tema_id = t.id AND t.nome = ?;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, tema);
				try (ResultSet rs = stmt.executeQuery()) {
					String palavra = null;
					while (rs.next() == true) {
						palavra = rs.getString(1);
						palavras.add(palavra);
					}
				}
			}
		} catch (SQLException e) {
			throw new ModelException("Falha ao carregar temas", e);
		}
		if (palavras.isEmpty() == true) {
			throw new ModelException("Base de dados vazia!");
		}
		Random r = new Random(Instant.now().toEpochMilli());
		int indexSoteado = r.nextInt(palavras.size());
		return palavras.get(indexSoteado);
	}

//	private void inserir() throws ModelException {
//		String sql = "INSERT INTO dicionario (palavra, tema_id) VALUES (?, ?);";
//		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
//			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//				try (BufferedReader buffer = Files.newBufferedReader(Paths.get("temas\\comidas.txt"), Charset.defaultCharset())) {
//					String s = null;
//					while ((s = buffer.readLine()) != null) {
//						stmt.setString(1, s);
//						stmt.setInt(2, 3);
//						stmt.addBatch();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				stmt.executeBatch();
//			}
//		} catch (SQLException e) {
//			throw new ModelException("Falha ao salvar aluno", e);
//		}
//	}
//	
//	public static void main(String[] args) {
//		DicionarioDataBaseDAO d = new DicionarioDataBaseDAO();
//		try {
//			d.inserir();
//		} catch (ModelException e) {
//			e.printStackTrace();
//		}
//	}
}
