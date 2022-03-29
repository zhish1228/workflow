package com.zd.workflow.datamodel.http;

import lombok.Data;

import java.util.Map;

@Data
public class HttpRequest {

    private String url;
    private String method;
    private Map<String, String> params;
    private String body;
    private Map<String, String> header;

}
