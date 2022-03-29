package com.zd.workflow.script;

import java.util.Map;

public abstract class ScriptBase {

    public abstract Map<String, String> preProcess(PreScriptInput preScriptInput);

    public abstract void postProcess(PostScriptInput postScriptInput);
}
