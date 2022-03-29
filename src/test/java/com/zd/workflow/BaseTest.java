package com.zd.workflow;

import com.zd.workflow.datamodel.TestContext;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    private final String GLOBALDATA_YAML_FILE_NAME = "globaldata.yaml";

    protected TestContext testContext = new TestContext();

    @BeforeSuite
    public void beforeSuite() {
        initGlobalData();

    }

    private void initGlobalData() {

    }
}
