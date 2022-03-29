package com.zd.workflow.handler;

import com.jayway.jsonpath.JsonPath;
import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.datamodel.common.ResponseParam;
import com.zd.workflow.datamodel.common.ResponseParamExpression;
import com.zd.workflow.utils.TestCaseUtils;
import lombok.Data;

import java.util.List;

public class ResponseParamHandler extends Handler {

    @Override
    public void doHandler(WorkflowStep workflowStep, TestContext testContext) {

        String testContent = workflowStep.getTestContent();
        RespParam respParam = TestCaseUtils.fromYamlStringIgnoreMissingField(testContent,
                RespParam.class);
        ResponseParam responseParam = respParam.getResponseParam();
        if (responseParam == null) {
            return;

        }
        List<ResponseParamExpression> responseParamExpressions = responseParam.getExpressions();
        if (responseParamExpressions == null || responseParamExpressions.size() == 0) {

            return;

        }

        for (ResponseParamExpression expression : responseParamExpressions) {

            String jsonPath = "$." + expression.getPath();
            String respBody = workflowStep.getStepResult();
            String value = JsonPath.read(respBody, jsonPath).toString();
            String key = expression.getValue();
            testContext.getGlobalData().put(key, value);


        }
        next.doHandler(workflowStep, testContext);
    }

    @Data
    public static class RespParam {

        private ResponseParam responseParam;

    }

}
