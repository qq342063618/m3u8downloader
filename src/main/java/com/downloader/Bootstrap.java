package com.downloader;

import java.util.Scanner;

public class Bootstrap {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入任务编号并回车：[1:下载m3u8及片段,2:批量重命名TS片段,3:合并TS片段,0:退出]：");
        String input = scanner.nextLine();
        if("0".equals(input)){

        }else if("1".equals(input)){
            System.out.print("请输入保存文件夹的绝对路径(已存在的文件夹将会被删除)：");
            String absolutePath = scanner.nextLine();

            System.out.print("请输入m3u8地址：");
            String m3u8Url = scanner.nextLine();

            System.out.print("请输入线程数(建议50)：");
            String threadSize = scanner.nextLine();
            M3u8Downloader.run(absolutePath,m3u8Url, Integer.parseInt(threadSize));
        }else if("2".equals(input)){
            System.out.println("tips:程序只支持重命名最大9999个TS片段，将index23.ts改为index0023.ts的形式");
            System.out.print("请输入TS文件所在文件夹的绝对路径：");
            String absolutePath = scanner.nextLine();
            System.out.print("请输入TS文件名数字部分的前缀(如index-0022A.ts为index-)：");
            String prefix = scanner.nextLine();
            System.out.print("请输入TS文件名数字部分的后缀(如index-0022A.ts为A.ts)：");
            String suffix = scanner.nextLine();
            FileNameBatchEditor.run(absolutePath,prefix,suffix);
        }else if("3".equals(input)){
            System.out.println("Windows环境下，CMD执行指令【copy /b {TS片段存储路径/*.ts} new.ts】，其中{}部分请根据实际目录填写");
        }
        scanner.close();
    }

}
