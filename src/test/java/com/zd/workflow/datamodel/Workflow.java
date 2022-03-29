package com.zd.workflow.datamodel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Workflow {

    private String workflowFile;
    private String name;
    private String desc;
    private List<WorkflowStep> steps = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

}
