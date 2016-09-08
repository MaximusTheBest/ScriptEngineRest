package nashorn.exception;

public class ScriptNotFoundException extends Exception {

	private static final long serialVersionUID = 377062597601683681L;

	private String messageKey;

	public ScriptNotFoundException(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}

}
