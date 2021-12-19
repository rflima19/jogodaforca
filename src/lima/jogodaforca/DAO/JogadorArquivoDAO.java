package lima.jogodaforca.DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lima.jogodaforca.exceptions.ModelException;
import lima.jogodaforca.model.Jogador;
import lima.jogodaforca.model.Login;

public class JogadorArquivoDAO implements JogadorDAO {

	public static final File DIRETORIO = new File("files" + File.separator);
	public static final File ARQUIVO = new File(JogadorArquivoDAO.DIRETORIO, "jogadores.txt");

	@Override
	public boolean cadastrarJogador(Jogador jogador) throws ModelException {
		try {
			this.criarDiretorio(JogadorArquivoDAO.DIRETORIO);
			this.criarArquivo(JogadorArquivoDAO.ARQUIVO);
		} catch (IOException e) {
			throw new ModelException("Não foi possivel criar o arquivo", e);
		}
		try (Writer writer = new FileWriter(JogadorArquivoDAO.ARQUIVO, true);
				PrintWriter pw = new PrintWriter(writer)) {
			String s = this.converteJogador(jogador);
			pw.println(s);
		} catch (IOException e) {
			throw new ModelException(
					"Falha ao abrir o arquivo " + JogadorArquivoDAO.ARQUIVO.getAbsolutePath() + " para escrita", e);
		}
		return true;
	}
	
	@Override
	public Jogador pequisarJogador(Login login) throws ModelException {
		try {
			this.criarDiretorio(JogadorArquivoDAO.DIRETORIO);
			this.criarArquivo(JogadorArquivoDAO.ARQUIVO);
		} catch (IOException e) {
			throw new ModelException("Não foi possivel criar o arquivo", e);
		}
		Jogador jogador = null;
		try (Reader in = new FileReader(JogadorArquivoDAO.ARQUIVO);
				BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			while ((line = buffer.readLine()) != null) {
				String[] tokens = line.split(";");
				String loginHashHex = this.gerarHashSHA256(login.getLogin());
				//String passwordHashHex = this.gerarHashSHA256(login.getPassword());
				if ((loginHashHex.equals(tokens[0]) == true) /*&&
						(passwordHashHex.equals(tokens[1]) == true)*/) {
					Login l = new Login(tokens[0], tokens[1]);
					jogador = new Jogador(tokens[2], Integer.parseInt(tokens[3]), 
							Integer.parseInt(tokens[4]), l);
					break;
				}
			}
		} catch (IOException e) {
			throw new ModelException(
					"Falha ao abrir o arquivo " + JogadorArquivoDAO.ARQUIVO.getAbsolutePath() + " para leitura", e);
		}
		return jogador;
	}
	
	@Override
	public boolean update(Jogador jogador) throws ModelException {
		if (jogador == null) {
			throw new IllegalArgumentException("Argumento jogador nulo");
		}
		
		try {
			this.criarDiretorio(JogadorArquivoDAO.DIRETORIO);
			this.criarArquivo(JogadorArquivoDAO.ARQUIVO);
		} catch (IOException e) {
			throw new ModelException("Não foi possivel criar o arquivo", e);
		}
		
		if (JogadorArquivoDAO.ARQUIVO.length() == 0) {
			throw new ModelException("Base de dados vazia");
		}
		
		File arquivoTemporario = new File(JogadorArquivoDAO.DIRETORIO, "jogadores_temp.txt");
		try {
			arquivoTemporario.createNewFile();
		} catch (IOException e) {
			throw new ModelException("Não foi possivel criar o arquivo temporário", e);
		}
		
		try {
			try (Reader in = new FileReader(JogadorArquivoDAO.ARQUIVO); 
					BufferedReader buffer = new BufferedReader(in);
					Writer out = new FileWriter(arquivoTemporario); 
					PrintWriter print = new PrintWriter(out)) {
				String line = null;
				while ((line = buffer.readLine()) != null) {
					String[] tokens = line.split(";");
					String loginHashHex = jogador.getLogin().getLogin();
					//String passwordHashHex = this.gerarHashSHA256(login.getPassword());
					if ((loginHashHex.equals(tokens[0]) == true) /*&&
							(passwordHashHex.equals(tokens[1]) == true)*/) {
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
			
			try (Reader in = new FileReader(arquivoTemporario); 
					BufferedReader buffer = new BufferedReader(in);
					Writer out = new FileWriter(JogadorArquivoDAO.ARQUIVO); 
					PrintWriter print = new PrintWriter(out)) {
				String line = null;
				while ((line = buffer.readLine()) != null) {
					print.println(line);
				}
			} catch (IOException e) {
				throw new ModelException("Não foi possível restaurar a base de dados", e);
			}
		} finally {
			if (arquivoTemporario.exists() == true) {
				arquivoTemporario.delete();
			}
		}
		return true;
	}

	private void criarDiretorio(File diretorio) throws IOException {
		if ((diretorio.exists() == false) && (diretorio.isDirectory() == true)) {
			JogadorArquivoDAO.DIRETORIO.mkdir();
		}
	}

	private void criarArquivo(File arquivo) throws IOException {
		if ((arquivo.exists() == false) && (arquivo.isFile() == true)) {
			JogadorArquivoDAO.ARQUIVO.createNewFile();
		}
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
