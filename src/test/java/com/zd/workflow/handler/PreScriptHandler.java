package com.zd.workflow.handler;

import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.datamodel.constant.CommonConstants;
import com.zd.workflow.script.PreScriptInput;
import com.zd.workflow.script.ScriptBase;
import com.zd.workflow.utils.ScriptUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class PreScriptHandler extends Handler {


    @Override
    public void doHandler(WorkflowStep workflowStep, TestContext testContext) {
        if (!StringUtils.isEmpty(workflowStep.getScriptContent())) {

            String workflowName = workflowStep.getWorkflowName();

            String script = workflowStep.getScript();
            String scriptFilePath =
                    CommonConstants.WORKFLOW_ROOT_PATH + "/" + workflowName + "/" + script;

            ScriptBase scriptInstance = ScriptUtils.getScriptInstance(scriptFilePath, workflowName);

            PreScriptInput preScriptInput = new PreScriptInput();
            preScriptInput.setGlobalData(testContext.getGlobalData());

            Map<String, String> preScriptData = scriptInstance.preProcess(preScriptInput);

            testContext.getGlobalData().putAll(preScriptData);

        }
        next.doHandler(workflowStep, testContext);


    }
}
