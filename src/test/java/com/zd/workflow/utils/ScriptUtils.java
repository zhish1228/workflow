package com.zd.workflow.utils;

import com.zd.workflow.script.ScriptBase;

import java.io.File;

public class ScriptUtils {

    public static ScriptBase getScriptInstance(String scriptFilePath, String workflowName) {
        File scriptFile = TestCaseUtils.getFileInResources(scriptFilePath);
        return CompilerUtils.getInstance(scriptFile, workflowName);

    }


}
