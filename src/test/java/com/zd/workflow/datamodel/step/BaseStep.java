package com.zd.workflow.datamodel.step;

import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;
import lombok.Data;

@Data
public abstract class BaseStep {

    protected TestContext testContext = new TestContext();
    private WorkflowStep workflowStep;

    public abstract void exec();


}
