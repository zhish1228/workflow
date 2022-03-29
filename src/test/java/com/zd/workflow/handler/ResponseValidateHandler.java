package com.zd.workflow.handler;

import com.jayway.jsonpath.JsonPath;
import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.datamodel.common.ResponseValidation;
import com.zd.workflow.datamodel.common.ResponseValidationExpression;
import com.zd.workflow.utils.TestCaseUtils;
import lombok.Data;
import org.testng.Assert;

import java.util.List;

public class ResponseValidateHandler extends Handler {

    @Override
    public void doHandler(WorkflowStep workflowStep, TestContext testContext) {

        String testContent = workflowStep.getTestContent();
        RespValidation respValidation = TestCaseUtils.fromYamlStringIgnoreMissingField(testContent,
                RespValidation.class);
        ResponseValidation validation = respValidation.getValidation();
        if (validation == null) {
            return;

        }
        List<ResponseValidationExpression> expressions = validation.getExpressions();
        if (expressions == null || expressions.size() == 0) {

            return;

        }

        for (ResponseValidationExpression expression : expressions) {

            String value = JsonPath.read(workflowStep.getStepResult(), "$." + expression.getPath())
                    .toString();
            Assert.assertEquals(value, expression.getValue());

        }
        next.doHandler(workflowStep, testContext);
    }

    @Data
    public static class RespValidation {

        private ResponseValidation validation;

    }

}
