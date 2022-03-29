package com.zd.workflow.datamodel.common;

import lombok.Data;

import java.util.List;

@Data
public class ResponseValidation {

    private List<ResponseValidationExpression> expressions;

}
