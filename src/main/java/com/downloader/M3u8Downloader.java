package com.downloader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * M3u8文件下载
 */
public class M3u8Downloader {

    private static String targetPath;

    private static List<String> beDownloadTsList = new ArrayList<>();

    public static void run(String absoluteTargetPath,String m3u8Url,int threadSize) {
        if(!absoluteTargetPath.endsWith("/")){
            absoluteTargetPath = absoluteTargetPath + "/";
        }
        targetPath = absoluteTargetPath;

        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(threadSize);

        FileUtil.del(absoluteTargetPath);
        FileUtil.mkdir(absoluteTargetPath);

        readFile(m3u8Url);

        for(int i = 0; i<beDownloadTsList.size(); i++){
            DownloadThread runnable = new DownloadThread();
            String url = beDownloadTsList.get(i);
            runnable.setName(url.substring(url.lastIndexOf("/") + 1));
            runnable.setUrl(url);
            threadPoolExecutor.execute(runnable);
        }
        System.out.println("下载完毕");
    }

    private static void readFile(String m3u8Url){
        long time = HttpUtil.downloadFile(m3u8Url,targetPath + "index.m3u8");
        String urlPrefix = m3u8Url.substring(0,m3u8Url.lastIndexOf("/"));
        if(time != -1){
            File m3u8File = new File(targetPath + "index.m3u8");
            if(m3u8File != null && m3u8File.isFile() && m3u8File.canRead()){
                List<String> stringList = FileUtil.readLines(m3u8File,"UTF-8");
                for(String lineStr : stringList){
                    if(!lineStr.startsWith("#") && lineStr.endsWith(".ts")){
                        beDownloadTsList.add(urlPrefix + "/" + lineStr);
                    }
                }
            }
        }
        System.out.println("m3u8读取完毕");
    }

    private static class DownloadThread implements Runnable{
        private String name;
        private String url;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            if(url != null){
                System.out.println("线程" + name + "开始下载");
                long time = HttpUtil.downloadFile(url,targetPath + name);
                System.out.println("线程" + name + "下载完毕,耗时：" + time);
            }
        }
    }

}
