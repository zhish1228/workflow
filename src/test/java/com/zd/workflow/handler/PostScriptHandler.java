package com.zd.workflow.handler;

import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.datamodel.constant.CommonConstants;
import com.zd.workflow.script.PostScriptInput;
import com.zd.workflow.script.ScriptBase;
import com.zd.workflow.utils.ScriptUtils;
import org.apache.commons.lang3.StringUtils;

public class PostScriptHandler extends Handler {


    @Override
    public void doHandler(WorkflowStep workflowStep, TestContext testContext) {
        if (!StringUtils.isEmpty(workflowStep.getScriptContent())) {

            PostScriptInput postScriptInput = new PostScriptInput();
            postScriptInput.setBody(workflowStep.getStepResult());
            postScriptInput.setGlobalData(testContext.getGlobalData());

            String workflowName = workflowStep.getWorkflowName();

            String script = workflowStep.getScript();
            String scriptFilePath =
                    CommonConstants.WORKFLOW_ROOT_PATH + "/" + workflowName + "/" + script;

            ScriptBase scriptInstance = ScriptUtils.getScriptInstance(scriptFilePath, workflowName);
            scriptInstance.postProcess(postScriptInput);

        }
    }
}
