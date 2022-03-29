package com.zd.workflow.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TestCaseUtils {

    public static List<File> getFilesInResources(String folder, String extension) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            return new ArrayList<>();
        }
        URL url = loader.getResource(folder);
        if (url == null) {
            return new ArrayList<>();
        }

        String suffix = extension.toLowerCase();
        File[] files = new File(url.getPath()).listFiles(
                ((dir, name) -> name.toLowerCase().endsWith(suffix)));
        if (files == null) {
            return new ArrayList<>();
        }
        Arrays.sort(files);
        ArrayList<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (!file.isDirectory()) {
                fileList.add(file);
            }
        }

        return fileList;
    }

    public static File getFileInResources(String path) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            return null;
        }
        URL url = loader.getResource(path);
        if (url == null) {
            return null;
        }
        return new File(url.getPath());

    }


    public static String readFileContent(File file) {
        try (InputStream is = new FileInputStream(
                file); InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            long length = file.length();
            char[] bytes = new char[(int) length];
            reader.read(bytes);
            return new String(bytes);

        } catch (Exception e) {

            log.error("read file {} error", file.getName(), e);

        }
        return "";
    }

    public static <T> T fromYamlString(String yamlString, Class<T> clazz) {

        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(new Constructor(clazz), representer);
        return yaml.loadAs(yamlString, clazz);

    }

    public static <T> T fromYamlStringIgnoreMissingField(String yamlString, Class<T> clazz) {

        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(new Constructor(clazz), representer);
        return yaml.loadAs(yamlString, clazz);

    }

}
