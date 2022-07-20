package com.funny.study.java.classloader;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbilityCollection {
    private Map<String, String> dbSourceMap = new HashMap<>();


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, MalformedURLException, NoSuchMethodException, InvocationTargetException {
        String html = RequestHelper.get("");

        //第一步，将字符内容解析成一个Document类
        Document doc = Jsoup.parse(html);

        //第二步，根据我们需要得到的标签，选择提取相应标签的内容
        Elements elements = doc.select("td");
        String lastJarURL = "";
        for (Element element : elements) {
            String url = element.select("a").attr("href").trim();
            if (url.contains("RELEASE")) {
                lastJarURL = url;
            }
        }

        System.out.println(lastJarURL);


        String htmlLatest = RequestHelper.get(lastJarURL);

        //第一步，将字符内容解析成一个Document类
        Document docLatest = Jsoup.parse(htmlLatest);

        //第二步，根据我们需要得到的标签，选择提取相应标签的内容
        Elements elementsLatest = docLatest.select("td");

        String lastJarPath = "";
        for (Element element : elementsLatest) {
            String url = element.select("a").attr("href").trim();
            if (url.contains("source")) {
                continue;
            }
            if (url.endsWith(".jar")) {
                lastJarPath = url;
            }
        }
        System.out.println(lastJarPath);

        URL targetUrl = new URL(lastJarPath);
        URLClassLoader loader = (URLClassLoader) AbilityCollection.class.getClassLoader();

        String classname = "SourceEnum";

        // 这个校验是为了避免重复加载的
        boolean isLoader = false;
        for (URL url : loader.getURLs()) {
            if (url.equals(targetUrl)) {
                isLoader = true;
                break;
            }
        }

        // 如果没有加载，通过反射获取URLClassLoader.allURL方法来加载jar包
        if (!isLoader) {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            add.setAccessible(true);
            add.invoke(loader, targetUrl);
        }

        // 加载指定的class，然后为其创建对象后执行其方法，这些操作都是用反射去做的
        Class<?> remoteClass = loader.loadClass(classname);

        List<Map<String,Object>> result = getEnumByClass(remoteClass);

        System.out.println(JSON.toJSONString(result));

    }

    public static List<Map<String,Object>> getEnumByClass(Class<?> clazz){
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            Object[] objects = clazz.getEnumConstants();
            Method getDbSource = clazz.getMethod("getDbSource");
            Method getDesc = clazz.getMethod("getDesc");
            for (Object object:objects) {
                Map<String,Object> map = new HashMap<>();
                String code = getDbSource.invoke(object).toString();
                String info = getDesc.invoke(object).toString();
                map.put(code,info);
                list.add(map);
            }
            return list;
        }catch (Exception e) {
            return list;
        }
    }
}
