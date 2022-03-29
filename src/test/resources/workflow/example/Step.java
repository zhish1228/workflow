package workflow.example;

import com.zd.workflow.script.PostScriptInput;
import com.zd.workflow.script.PreScriptInput;
import com.zd.workflow.script.ScriptBase;

import java.util.HashMap;
import java.util.Map;

public class Step extends ScriptBase {

    @Override
    public Map<String, String> preProcess(PreScriptInput preScriptInput) {
        Map<String, String> map = new HashMap<>();
        map.put("ts", String.valueOf(System.currentTimeMillis()));
        return map;
    }

    @Override
    public void postProcess(PostScriptInput postScriptInput) {
        System.out.printf("this is postProcess method");
    }
}
