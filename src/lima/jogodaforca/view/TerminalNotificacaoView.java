package lima.jogodaforca.view;

import lima.jogodaforca.utils.TerminalUtils;

public class TerminalNotificacaoView {
	
	public void imprimirNotificacao(String mensagem) {
		TerminalUtils.imprimirMensagem(mensagem);
	}
	
	public void imprimirNotificacaoErro(Exception e) {
		TerminalUtils.imprimirMensagemErro(e);
	}
}
