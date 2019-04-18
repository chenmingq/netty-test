package com.netty.test.utils;


import com.netty.test.annotation.ReqMapping;
import com.netty.test.common.cache.ClassCache;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;


public class ClassUtil {

    public static void main(String[] args) {

        String packageName = "com.netty.test";
        lordClazz(packageName);

    }


    public static void lordClazz(String packageName) {
        Set<Class<?>> mappingClassSet = new HashSet<>();
        try {
            String packageDirName = packageName.replace(".", "/");
            Set<Class<?>> classSet = new HashSet<>();
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                // 必须以文件的形式保存的
                if (!"file".equals(protocol)) {
                    continue;
                }
                // 获取包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackageByFile(packageName, filePath, true, classSet);
            }
            for (Class<?> aClass : classSet) {
                ReqMapping annotation = aClass.getAnnotation(ReqMapping.class);
                if (null == annotation) {
                    continue;
                }
                mappingClassSet.add(aClass);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ClassCache.reqMappingSet = mappingClassSet;
    }

    /**
     * 查找和添加包下的所有class文件
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, boolean recursive, Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        if (null == dirFiles) {
            return;
        }
        // 循环所有文件
        for (File file : dirFiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
