package com.zd.workflow.datamodel.step;

import com.zd.workflow.handler.*;

public class HttpStep extends BaseStep {

    @Override
    public void exec() {
        Handler.Builder builder = new Handler.Builder();

        builder.addHandler(new CaseWaitHandler())
                .addHandler(new PreScriptHandler())
                .addHandler(new HttpRequestHandler())
                .addHandler(new ResponseValidateHandler())
                .addHandler(new ResponseParamHandler())
                .addHandler(new PostScriptHandler())

        ;

        builder.build().doHandler(getWorkflowStep(), testContext);

    }
}
