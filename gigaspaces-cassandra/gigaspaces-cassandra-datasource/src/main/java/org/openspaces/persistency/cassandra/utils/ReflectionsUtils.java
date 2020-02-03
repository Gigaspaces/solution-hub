package org.openspaces.persistency.cassandra.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public interface ReflectionsUtils {
    Logger logger = LoggerFactory.getLogger(ReflectionsUtils.class);
     static List<Class<?>> getClassesInPackage(String packageName) {
         logger.info("get classes in package {}",packageName);
        String path = packageName.replaceAll("\\.", "/");
        List<Class<?>> classes = new ArrayList<>();
        String[] classPathEntries = System.getProperty("java.class.path").split(System.getProperty("path.separator"));

        String name;
        for (String classpathEntry : classPathEntries) {
            if (classpathEntry.endsWith(".jar")) {
                File jar = new File(classpathEntry);
                try {
                    JarInputStream is = new JarInputStream(new FileInputStream(jar));
                    JarEntry entry;
                    while((entry = is.getNextJarEntry()) != null) {
                        name = entry.getName();
                        if (name.endsWith(".class")) {
                            if (name.contains(path) && name.endsWith(".class")) {
                                String classPath = name.substring(0, entry.getName().length() - 6);
                                classPath = classPath.replaceAll("[\\|/]", ".");
                                classes.add(Class.forName(classPath));
                            }
                        }
                    }
                } catch (Exception ex) {
                    logger.warn("fail to scan jar {}",jar,ex);
                }
            } else {
                try {
                    File base = new File(classpathEntry + "/" + path);
                    File[] results = base.listFiles();
                    if(results!=null) {
                        for (File file : results) {
                            name = file.getName();
                            if (name.endsWith(".class")) {
                                name = name.substring(0, name.length() - 6);
                                classes.add(Class.forName(packageName + "." + name));
                            }
                        }
                    }
                } catch (Exception ex) {
                    logger.warn("fail to scan path {}",classpathEntry + "/" + path,ex);
                }
            }
        }

        return classes;
    }
}
