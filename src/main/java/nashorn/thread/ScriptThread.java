package nashorn.thread;

import java.io.StringWriter;
import java.util.concurrent.Callable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptThread implements Callable<String> {

	private final ScriptEngine scriptEngine;

	private StringWriter stringWriter;

	private String script;

	public ScriptThread(String script) {
		this.script = script;
		stringWriter = new StringWriter();
		scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

	}

	@Override
	public String call() throws Exception {
		scriptEngine.getContext().setWriter(stringWriter);
		scriptEngine.eval(script);
		return stringWriter.toString();
	}

}
