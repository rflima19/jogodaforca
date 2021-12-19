package lima.jogodaforca.DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lima.jogodaforca.exceptions.ModelException;

public class DicionarioArquivoDAO implements DicionarioDAO {

	public static final File DIRETORIO = new File("temas" + File.separator);
	
	@Override
	public List<String> carregarTemas() throws ModelException {
		if (DicionarioArquivoDAO.DIRETORIO.exists() == false) {
			throw new ModelException("Diretório " + DicionarioArquivoDAO.DIRETORIO.getAbsolutePath() + " não existe");
		}
		List<String> temas = new ArrayList<>();
		File[] arquivos = DicionarioArquivoDAO.DIRETORIO.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().endsWith(".txt");
			}
		});
		for (int i = 0; i < arquivos.length; i++) {
			File file = arquivos[i];
			String nome = file.getName();
			temas.add(nome.replaceAll("\\.txt", ""));
		}
		return temas;
	}
	
	@Override
	public String sotearPalavra(String nomeArquivo) throws ModelException {
		if (nomeArquivo.endsWith(".txt") == false) {
			nomeArquivo = nomeArquivo + ".txt";
		}
		File arquivo = new File(DicionarioArquivoDAO.DIRETORIO, nomeArquivo);
		if (arquivo.exists() == false) {
			throw new ModelException("Arquivo " + arquivo.getAbsolutePath() + " não existe");
		}
		List<String> palavras = new ArrayList<>();
		try (Reader in = new FileReader(arquivo);
				BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			while ((line = buffer.readLine()) != null) {
				palavras.add(line);
			}
		} catch (IOException e) {
			throw new ModelException(
					"Falha ao abrir o arquivo " + JogadorArquivoDAO.ARQUIVO.getAbsolutePath() + " para leitura", e);
		}
		if (palavras.isEmpty() == true) {
			throw new ModelException("Arquivo " + arquivo.getAbsolutePath() + " vazio");
		}
		Random r = new Random(Instant.now().toEpochMilli());
		int indexSoteado = r.nextInt(palavras.size());
		return palavras.get(indexSoteado);
	}
}
