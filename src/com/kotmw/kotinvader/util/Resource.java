package com.kotmw.kotinvader.util;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Resource {

    List<String> resources;

    private static final Resource instance = new Resource();

    public static Resource getInstance() { return instance; }

    private Resource() {
        URL root = Thread.currentThread().getContextClassLoader().getResource("resources");
        if (root != null) {
            switch (root.getProtocol()) {
                case "file":
                    resources = Arrays.asList(Objects.requireNonNull(new File(root.getFile()).list()));
                    break;
                case "jar":
                    try (JarFile jarFile = ((JarURLConnection) root.openConnection()).getJarFile()) {
                        resources = Collections.list(jarFile.entries())
                                .stream()
                                .filter(entry -> entry.getName().startsWith("resources"))
                                .map(entry -> entry.getName().replaceAll("resources/", ""))
                                .collect(Collectors.toList());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else System.out.println("Root is Null");
    }

    public List<String> getResources() {
        return resources;
    }
}
