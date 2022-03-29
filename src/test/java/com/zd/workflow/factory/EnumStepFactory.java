package com.zd.workflow.factory;

import com.zd.workflow.datamodel.step.BaseStep;
import com.zd.workflow.datamodel.step.HttpStep;

public enum EnumStepFactory {

    HTTP("http") {
        @Override
        public BaseStep create() {
            return new HttpStep();
        }
    },
    UI("ui") {
        @Override
        public BaseStep create() {
            return null;
        }
    },
    DUBBO("dubbo") {
        @Override
        public BaseStep create() {
            return null;
        }
    },
    SQL("sql") {
        @Override
        public BaseStep create() {
            return null;
        }
    },
    ;


    private final String caseType;

    EnumStepFactory(String caseType) {
        this.caseType = caseType;
    }

    public static EnumStepFactory createFactory(String caseType) {
        return valueOf(caseType.toUpperCase());
    }

    public abstract BaseStep create();

}
