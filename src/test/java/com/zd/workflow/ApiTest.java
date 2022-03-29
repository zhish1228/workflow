package com.zd.workflow;

import com.zd.workflow.datamodel.Workflow;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.datamodel.step.BaseStep;
import com.zd.workflow.dataprovider.WorkflowDataprovider;
import com.zd.workflow.factory.EnumStepFactory;
import com.zd.workflow.utils.TestCaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

@Slf4j
public class ApiTest extends BaseTest {

    @Test(dataProviderClass = WorkflowDataprovider.class, dataProvider = "workflow")
    public void runner(Workflow workflow) {
        List<WorkflowStep> workflowSteps = workflow.getSteps();

        for (WorkflowStep workflowStep : workflowSteps) {
            Map<String, String> map = TestCaseUtils.fromYamlString(workflowStep.getTestContent(),
                    Map.class);
            String caseType = map.get("caseType");
            EnumStepFactory stepFactory = EnumStepFactory.createFactory(caseType);
            BaseStep baseStep = stepFactory.create();
            baseStep.setWorkflowStep(workflowStep);
            baseStep.setTestContext(testContext);
            baseStep.exec();
        }
    }
}
