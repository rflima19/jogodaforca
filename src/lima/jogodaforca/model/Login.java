package lima.jogodaforca.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import lima.jogodaforca.exceptions.ModelException;

public class Login {

	private static final String REGEX = "^[A-Za-z0-9_@\\\\.-]+$";

	private String login;
	private String password;

	public Login(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public static boolean validar(String login, String password) throws ModelException {
		boolean isLoginValido = false;
		boolean isPWValido = false;

		// login deve conter no mínimo 4 caracteres (até 20)
		if ((login.isBlank() == true) || (login.isEmpty() == true) || 
				(login.length() < 4) || (login.length() > 20)) {
			throw new ModelException("Seu login deve conter de 4 a 20 caracteres");
		}

		// password deve conter no mínimo 8 caracteres (até 32)
		if ((password.isBlank() == true) || (password.isEmpty() == true) || 
				(password.length() < 8) || (password.length() > 20)) {
			throw new ModelException("Seu password deve conter de 8 a 32 caracteres");
		}

		// login com caracteres invalidos
		if (Pattern.matches(Login.REGEX, login) == false) {
			List<Character> lista = Login.capturaCaracteresInvalidos(login, Login.REGEX);
			throw new ModelException("Seu login possui os seguintes caracteres inválidos: " + lista.toString());
		} else {
			isLoginValido = true;
		}

		// password com caracteres invalidos
		if (Pattern.matches(Login.REGEX, password) == false) {
			List<Character> lista = Login.capturaCaracteresInvalidos(password, Login.REGEX);
			throw new ModelException("Seu password possui os seguintes caracteres inválidos: " + lista.toString());
		} else {
			isPWValido = true;
		}

		// login e senha devem ser diferentes
		if (login.equals(password) == true) {
			throw new ModelException("Seu login e password devem ser diferentes");
		}

		// password sem letra maiúsculo
		if (Pattern.matches(".*[A-Z]+.*", password) == false) {
			throw new ModelException("Seu password deve possui pelo menos uma letra maiúcula");
		}
		
		// password sem número
		if (Pattern.matches(".*[0-9]+.*$", password) == false) {
			throw new ModelException("Seu password deve possui pelo menos um número de 0 a 9");
		}

		return isLoginValido && isPWValido;
	}

	private static List<Character> capturaCaracteresInvalidos(String str, String regex) {
		List<Character> lista = new ArrayList<>();
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			if (Pattern.matches(regex, s) == false) {
				lista.add(s.charAt(0));
			}
		}
		return lista;
	}
}
