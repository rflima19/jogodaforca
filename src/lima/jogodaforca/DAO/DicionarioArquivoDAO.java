package lima.jogodaforca.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lima.jogodaforca.exceptions.ModelException;

public class DicionarioArquivoDAO implements DicionarioDAO {

	public static final String SEPARATOR = FileSystems.getDefault().getSeparator();
	public static final Path DIRETORIO = Paths.get("temas" + DicionarioArquivoDAO.SEPARATOR);
	
	@Override
	public List<String> carregarTemas() throws ModelException {
		if (Files.exists(DicionarioArquivoDAO.DIRETORIO) == false) {
			throw new ModelException("Diretório " + DicionarioArquivoDAO.DIRETORIO.toAbsolutePath() + " não existe");
		}
		List<String> temas = new ArrayList<>();
		try (DirectoryStream<Path> arquivos = Files.newDirectoryStream(
				DicionarioArquivoDAO.DIRETORIO, 
				p -> {
					return Files.isRegularFile(p) && p.getFileName().toString().endsWith(".txt");
				})) {
			for (Path p : arquivos) {
				String nome = p.getFileName().toString();
				temas.add(nome.replaceAll("\\.txt", ""));
			}
		} catch (IOException e) {
			throw new ModelException(e);
		}
		return temas;
	}
	
	@Override
	public String sotearPalavra(String nomeArquivo) throws ModelException {
		if (nomeArquivo.endsWith(".txt") == false) {
			nomeArquivo = nomeArquivo + ".txt";
		}
		Path arquivo = DicionarioArquivoDAO.DIRETORIO.resolve(nomeArquivo);
		if (Files.exists(arquivo) == false) {
			throw new ModelException("Arquivo " + arquivo.toAbsolutePath() + " não existe");
		}
		List<String> palavras = new ArrayList<>();
		try (Reader in = Files.newBufferedReader(arquivo, Charset.defaultCharset());
				BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			while ((line = buffer.readLine()) != null) {
				palavras.add(line);
			}
		} catch (IOException e) {
			throw new ModelException(
					"Falha ao abrir o arquivo " + arquivo.toAbsolutePath() + " para leitura", e);
		}
		if (palavras.isEmpty() == true) {
			throw new ModelException("Arquivo " + arquivo.toAbsolutePath() + " vazio");
		}
		Random r = new Random(Instant.now().toEpochMilli());
		int indexSoteado = r.nextInt(palavras.size());
		return palavras.get(indexSoteado);
	}
}
