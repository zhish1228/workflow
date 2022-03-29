package com.zd.workflow.datamodel.http;

import com.zd.workflow.datamodel.common.ResponseParam;
import com.zd.workflow.datamodel.common.ResponseValidation;
import lombok.Data;

@Data
public class HttpTestCase {


    private HttpRequest request;

    private ResponseValidation validation;
    private ResponseParam responseParam;
    private String caseType;
    private int waitTime;
    private String desc;

}
