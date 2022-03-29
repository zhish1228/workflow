package com.zd.workflow.datamodel.common;

import lombok.Data;

@Data
public class ResponseValidationExpression {

    private String condition;
    private String path;
    private String value;


}
