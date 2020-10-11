package com.downloader;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * 文件名批量修改
 */
public class FileNameBatchEditor {

    public static void run(String dirPath,String fileNamePrefix,String fileNameSuffix) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.getName().endsWith(".ts")){
                String oldFileName = file.getName();
                int no = Integer.parseInt(oldFileName.replaceAll(fileNamePrefix,"").replaceAll(fileNameSuffix,""));
                if(no < 10){
                    FileUtil.rename(file,"index000" + no + ".ts",true);
                }else if(no < 100){
                    FileUtil.rename(file,"index00" + no + ".ts",true);
                }else if(no < 1000){
                    FileUtil.rename(file,"index0" + no + ".ts",true);
                }else if(no < 10000){
                    FileUtil.rename(file,"index" + no + ".ts",true);
                }
            }
        }
    }

}
