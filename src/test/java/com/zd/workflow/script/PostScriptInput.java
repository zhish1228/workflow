package com.zd.workflow.script;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PostScriptInput {

    private String body;

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> globalData = new HashMap<>();

}
