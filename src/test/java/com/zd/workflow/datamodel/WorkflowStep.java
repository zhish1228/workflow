package com.zd.workflow.datamodel;

import com.zd.workflow.script.ScriptBase;
import lombok.Data;

@Data
public class WorkflowStep {

    private String workflowName;
    private String test;
    private String testContent;
    private String script;
    private String scriptContent;
    private ScriptBase scriptInstance;
    private String stepResult;

}
