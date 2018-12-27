package com.filestorage.stub;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSONUtils {
	public static String prettyPrintJSON(String json) {

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");
		scriptEngine.put("jsonString", json);
		try {
			scriptEngine.eval("result = JSON.stringify(JSON.parse(jsonString), null, 2)");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		String prettyPrintedJson = (String) scriptEngine.get("result");
		return prettyPrintedJson;
	}
}
