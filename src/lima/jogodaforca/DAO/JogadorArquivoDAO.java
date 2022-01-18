package lima.jogodaforca.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lima.jogodaforca.exceptions.ModelException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Login;


public class JogadorArquivoDAO implements JogadorDAO {

	public static final String SEPARATOR = FileSystems.getDefault().getSeparator();
	public static final Path DIRETORIO = Paths.get("files" + JogadorArquivoDAO.SEPARATOR);
	public static final Path ARQUIVO = JogadorArquivoDAO.DIRETORIO.resolve("jogadores.txt");

	@Override
	public boolean cadastrarJogador(Jogador jogador) throws ModelException {
		try (Writer writer = Files.newBufferedWriter(JogadorArquivoDAO.ARQUIVO, StandardOpenOption.CREATE,
				StandardOpenOption.WRITE, StandardOpenOption.APPEND); PrintWriter pw = new PrintWriter(writer)) {
			String s = this.converteJogador(jogador);
			pw.println(s);
		} catch (IOException e) {
			throw new ModelException(
					"Falha ao abrir o arquivo " + JogadorArquivoDAO.ARQUIVO.toAbsolutePath() + " para escrita", e);
		}
		return true;
	}

	@Override
	public Jogador pequisarJogador(Login login) throws ModelException {
		Jogador jogador = null;
		try (InputStream in = Files.newInputStream(JogadorArquivoDAO.ARQUIVO, StandardOpenOption.CREATE,
				StandardOpenOption.READ); BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = buffer.readLine()) != null) {
				String[] tokens = line.split(";");
				String loginHashHex = this.gerarHashSHA256(login.getLogin());
				// String passwordHashHex = this.gerarHashSHA256(login.getPassword());
				if ((loginHashHex.equals(tokens[0]) == true) /*
																 * && (passwordHashHex.equals(tokens[1]) == true)
																 */) {
					Login l = new Login(tokens[0], tokens[1]);
					jogador = new Jogador(tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), l);
					break;
				}
			}
		} catch (IOException e) {
			throw new ModelException(
					"Falha ao abrir o arquivo " + JogadorArquivoDAO.ARQUIVO.toString() + " para leitura", e);
		}
		return jogador;
	}

	@Override
	public boolean update(Jogador jogador) throws ModelException {
		if (jogador == null) {
			throw new IllegalArgumentException("Argumento jogador nulo");
		}
		try {
			if (Files.size(JogadorArquivoDAO.ARQUIVO) == 0) {
				throw new ModelException("Base de dados vazia");
			}
		} catch (IOException e) {
			throw new ModelException("Não foi possível ler a base de dados", e);
		}
		Path arquivoTemporario = null;
		try {
			arquivoTemporario = Files.createTempFile("jogadores", "temp");
		} catch (IOException e) {
			throw new ModelException("Não foi possivel criar o arquivo temporário", e);
		}
		try (InputStream in = Files.newInputStream(JogadorArquivoDAO.ARQUIVO, StandardOpenOption.CREATE,
				StandardOpenOption.READ);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
				Writer out = Files.newBufferedWriter(arquivoTemporario, StandardOpenOption.WRITE);
				PrintWriter print = new PrintWriter(out)) {
			String line = null;
			while ((line = buffer.readLine()) != null) {
				String[] tokens = line.split(";");
				String loginHashHex = jogador.getLogin().getLogin();
				// String passwordHashHex = this.gerarHashSHA256(login.getPassword());
				if ((loginHashHex.equals(tokens[0]) == true) 
						/*
						* && (passwordHashHex.equals(tokens[1]) == true)
						*/) {
					tokens[3] = Integer.toString(jogador.getVitorias());
					tokens[4] = Integer.toString(jogador.getDerrotas());
				}
				StringBuilder strb = new StringBuilder();
				strb.append(tokens[0]);
				strb.append(";");
				strb.append(tokens[1]);
				strb.append(";");
				strb.append(tokens[2]);
				strb.append(";");
				strb.append(tokens[3]);
				strb.append(";");
				strb.append(tokens[4]);
				print.println(strb.toString());
			}
		} catch (IOException e) {
			throw new ModelException("Não foi possível ler a base de dados", e);
		}
		try {
			Files.copy(arquivoTemporario, JogadorArquivoDAO.ARQUIVO, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ModelException("Não foi possível restaurar a base de dados", e);
		}
		return true;
	}

	private String converteJogador(Jogador jogador) {
		String login = jogador.getLogin().getLogin();
		String password = jogador.getLogin().getPassword();
		String nome = jogador.getNome();
		String vitorias = Integer.toString(jogador.getVitorias());
		String derrotas = Integer.toString(jogador.getDerrotas());
		StringBuilder strb = new StringBuilder();
		strb.append(this.gerarHashSHA256(login));
		strb.append(";");
		strb.append(this.gerarHashSHA256(password));
		strb.append(";");
		strb.append(nome);
		strb.append(";");
		strb.append(vitorias);
		strb.append(";");
		strb.append(derrotas);
		return strb.toString();
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
