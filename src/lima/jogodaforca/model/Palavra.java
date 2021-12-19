package lima.jogodaforca.model;

public class Palavra {

	private String palavra;
	private String mascara;
	private int letras;

	public Palavra(String palavra) {
		super();
		this.palavra = palavra.toUpperCase();
		this.letras = this.contarLetras();
		this.mascara = this.palavra.replaceAll("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]", "_");
	}

	public String getPalavra() {
		return palavra;
	}
	
	public String getMascara() {
		return mascara;
	}
	
	public int getLetras() {
		return letras;
	}
	
	public int contarLetras() {
		int cont = 0;
		char[] c = this.palavra.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (Character.isLetter(c[i]) == true) {
				cont++;
			}
		}
		return cont;
	}

	public boolean verificarCaractere(char caractere) {
		boolean result = false;
		char[] caracteresPalavra = this.palavra.toCharArray();
		char[] caracteresMascara = this.mascara.toCharArray();
		for (int i = 0; i < caracteresPalavra.length; i++) {
			if (caracteresPalavra[i] == caractere) {
				result = true;
				caracteresMascara[i] = caractere;
			}
		}
		if (result == true) {
			this.mascara = new String(caracteresMascara);
		}
		return result;
	}
	
}
