package com.zd.workflow.handler;

import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.utils.TestCaseUtils;
import lombok.Data;

import java.util.concurrent.TimeUnit;

public class CaseWaitHandler extends Handler {


    @Override
    public void doHandler(WorkflowStep workflowStep, TestContext testContext) {
        String testContent = workflowStep.getTestContent();
        CaseWaitTime caseWaitTime = TestCaseUtils.fromYamlStringIgnoreMissingField(testContent,
                CaseWaitTime.class);
        try {
            TimeUnit.SECONDS.sleep(caseWaitTime.getWaitTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        next.doHandler(workflowStep, testContext);
    }

    @Data
    public static class CaseWaitTime {

        private int waitTime;
    }

}
