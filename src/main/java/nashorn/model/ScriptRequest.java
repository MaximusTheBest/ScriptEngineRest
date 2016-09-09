package nashorn.model;

public class ScriptRequest {
	
	public ScriptRequest(){
	}

	public ScriptRequest(String script) {
		this.script = script;
	}

	private String script;

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

}
