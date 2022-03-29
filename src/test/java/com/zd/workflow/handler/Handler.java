package com.zd.workflow.handler;

import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;

public abstract class Handler {

    protected Handler next;

    public void next(Handler next) {
        this.next = next;
    }

    public abstract void doHandler(WorkflowStep workflowStep, TestContext testContext);

    public static class Builder {

        private Handler head;
        private Handler tail;

        public Builder addHandler(Handler handler) {
            if (this.head == null) {
                this.head = this.tail = handler;
                return this;
            }
            this.tail.next(handler);
            this.tail = handler;
            return this;

        }

        public Handler build() {
            return this.head;
        }

    }


}
