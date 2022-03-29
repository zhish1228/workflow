package com.zd.workflow.handler;

import com.zd.workflow.datamodel.TestContext;
import com.zd.workflow.datamodel.WorkflowStep;
import com.zd.workflow.datamodel.http.HttpRequest;
import com.zd.workflow.datamodel.http.HttpResponse;
import com.zd.workflow.datamodel.http.HttpTestCase;
import com.zd.workflow.utils.TestCaseUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Map;

public class HttpRequestHandler extends Handler {


    @Override
    public void doHandler(WorkflowStep workflowStep, TestContext testContext) {

        HttpTestCase httpTestCase = buildHttpTestCase(workflowStep, testContext.getGlobalData());
        HttpResponse httpResponse = execRequest(httpTestCase.getRequest());
        workflowStep.setStepResult(httpResponse.getBody());
        next.doHandler(workflowStep, testContext);


    }

    private HttpTestCase buildHttpTestCase(WorkflowStep workflowStep,
                                           Map<String, String> globalData) {
        String testContent = workflowStep.getTestContent();
        if (globalData != null && globalData.size() > 0) {

            for (Map.Entry<String, String> ent : globalData.entrySet()) {

                testContent = testContent.replaceAll(String.format("\\{\\{%s\\}\\}", ent.getKey()),
                        ent.getValue());

            }

        }

        HttpTestCase httpTestCase = TestCaseUtils.fromYamlString(testContent, HttpTestCase.class);

        return httpTestCase;
    }

    HttpResponse execRequest(HttpRequest httpRequest) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = httpRequest.getUrl();
        Map<String, String> header = httpRequest.getHeader();
        String method = httpRequest.getMethod();
        String body = httpRequest.getBody();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            httpGet.setHeader(entry.getKey(), entry.getValue());
        }
        StringEntity entity = new StringEntity(body, "utf-8");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpEntity entity1 = response.getEntity();
        String respBody = null;
        try {
            respBody = EntityUtils.toString(entity1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setBody(respBody);
        return httpResponse;


    }

}
