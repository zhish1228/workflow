package com.zd.workflow.utils;

import com.zd.workflow.script.ScriptBase;
import lombok.extern.slf4j.Slf4j;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CompilerUtils {

    private static String javaClassPath = null;

    private static String getJavaClassPath() {
        if (javaClassPath == null) {
            String testClassPath = CompilerUtils.class.getResource("/").getPath();

            StringBuilder classpathBuilder = new StringBuilder();
            classpathBuilder.append(System.getProperty("java.class.path"));
            classpathBuilder.append(System.getProperty("path.separator"));
            classpathBuilder.append(testClassPath);

            String targetPath = new File(testClassPath).getParent();
            String dependencyPath = targetPath + "/dependency";
            File[] dependencyFiles = new File(dependencyPath).listFiles(
                    ((dir, name) -> name.toLowerCase().endsWith(".jar")));
            if (dependencyFiles != null) {
                for (File file : dependencyFiles) {
                    classpathBuilder.append(System.getProperty("path.separator")).append(file.getPath());
                }
            }
            javaClassPath = classpathBuilder.toString();

        }
        return javaClassPath;
    }

    private static String getClassNameOfJava(String script) {
        int start = script.indexOf(" class ");
        int end = script.indexOf(" extends");
        if (start < 0 || end < 0) {
            return "";
        }
        String str = script.substring(start + 6, end).trim();
        int index = str.indexOf("<");
        if (index == -1) {
            return str;
        }
        return str.substring(0, index);
    }

    public static boolean compile(String source) {
        String className = getClassNameOfJava(source);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector, null,
                null);

        List<String> options = new ArrayList<>();
        options.add("-classpath");
        options.add(getJavaClassPath());
        JavaSourceFromString sourceObject = new JavaSourceFromString(className, source);

        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector,
                options, null, fileObjects);

        try {
            if (!new File("target/test-classes").exists()) {
                new File("target/test-classes");
            }
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File("target/test-classes")));

        } catch (IOException e) {
            log.error("set class target location failed", e);
        }
        Boolean call = task.call();
        return call;

    }


    public static ScriptBase getInstance(File scriptFile, String workflowName) {
        ScriptBase scriptInstance = null;
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(
                    new URL[]{scriptFile.getParentFile().toURI().toURL()});
            Class<?> cls;
            cls = Class.forName("workflow" + "." + workflowName + "." + scriptFile.getName()
                    .substring(0, scriptFile.getName().lastIndexOf(".")), true, classLoader);

            Object instance = cls.newInstance();
            if (instance instanceof ScriptBase) {
                scriptInstance = (ScriptBase) instance;
            }

        } catch (Exception e) {
            log.error("compile {} file failed.", scriptFile.getName(), e);
        }
        return scriptInstance;

    }


}


class JavaSourceFromString extends SimpleJavaFileObject {

    final String code;

    JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

}

