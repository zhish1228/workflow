package com.zd.workflow.datamodel;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TestContext {

    private Map<String, String> globalData = new HashMap<>();


}
