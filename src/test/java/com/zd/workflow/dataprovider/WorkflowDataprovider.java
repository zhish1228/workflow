package com.zd.workflow.dataprovider;

import com.zd.workflow.datamodel.Workflow;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.datamodel.constant.CommonConstants;
import com.zd.workflow.utils.CompilerUtils;
import com.zd.workflow.utils.TestCaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class WorkflowDataprovider {

    private final String TEST_TAG = "tag";

    @DataProvider(name = "workflow")
    public Iterator<Workflow> createData() {
        List<Workflow> workflows = getWorkflows();
        return workflows.iterator();

    }

    private List<Workflow> getWorkflows() {
        ArrayList<Workflow> workflows = new ArrayList<>();
        String testTag = System.getProperty(TEST_TAG);

        if (StringUtils.isEmpty(testTag)) {
            log.error("miss variable tag. cmd is: mvn clean test -Dtag=xxx ");
            return workflows;
        }

        List<File> workflowFiles = TestCaseUtils.getFilesInResources(CommonConstants.WORKFLOW_ROOT_PATH,
                ".workflow.yaml");
        if (workflowFiles.size() == 0) {
            return workflows;
        }

        for (File workflowFile : workflowFiles) {

            String workflFileContent = TestCaseUtils.readFileContent(workflowFile);
            Workflow workflow = TestCaseUtils.fromYamlString(workflFileContent, Workflow.class);
            workflow.setWorkflowFile(workflowFile.getName());

            if (workflow.getTags().contains(testTag)) {
                List<WorkflowStep> steps = workflow.getSteps();
                for (WorkflowStep step : steps) {
                    String stepFileContent = getTestContent(workflow.getName(), step.getTest());
                    step.setTestContent(stepFileContent);
                    String scriptFileContent = getScriptContent(workflow.getName(), step.getScript());
                    step.setScriptContent(scriptFileContent);
                    step.setWorkflowName(workflow.getName());
                }
                workflows.add(workflow);
            }
        }
        return workflows;
    }


    private String getTestContent(String workflowName, String testFileName) {

        String testFilePath =
                CommonConstants.WORKFLOW_ROOT_PATH + "/" + workflowName + "/" + testFileName;

        File testFile = TestCaseUtils.getFileInResources(testFilePath);
        if (testFile == null) {
            log.error(testFilePath + " : can not be found");
            System.exit(1);
        }
        String testFileContent = TestCaseUtils.readFileContent(testFile).trim();
        return testFileContent;

    }


    private String getScriptContent(String workflowName, String scriptFileName) {

        if (StringUtils.isEmpty(scriptFileName)) {
            return "";
        }

        String scriptFilePath =
                CommonConstants.WORKFLOW_ROOT_PATH + "/" + workflowName + "/" + scriptFileName;
        File scriptFile = TestCaseUtils.getFileInResources(scriptFilePath);
        if (scriptFile == null) {
            log.error(scriptFilePath + " : can not be found");
            System.exit(1);
        }
        String scriptFileContent = TestCaseUtils.readFileContent(scriptFile);

        String subString = scriptFileContent.substring(0, scriptFileContent.indexOf(";"));
        String pkgName = subString.replace("package", "").replace("workflow.", "").trim();
        if (!pkgName.equalsIgnoreCase(workflowName)) {
            log.error(scriptFileName + ": package name incorrect");
            System.exit(1);
        }

        if (!CompilerUtils.compile(scriptFileContent)) {
            log.error(scriptFileName + " : compile failed");
            System.exit(1);
        }
        return scriptFileContent;
    }

}
