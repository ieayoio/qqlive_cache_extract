package com.ieayoio.qqlive;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeUtil {

    /**
     * 排除不符合条件的目录
     */
    private static String[] filterFolder(String filePath, String[] fileList) {
        List<String> files = new ArrayList<>();
        for (String fileStr : fileList) {
            File file = new File(filePath, fileStr);
            // 非文件夹
            if (!file.isDirectory()) {
                continue;
            }
            int index = fileStr.indexOf("_");
            if (index == -1) {
                continue;
            }
            // 默认不是切割序号
            String vCount = fileStr.substring(index).replace("_", "");
            if (!StringUtils.isNumeric(vCount)) {
                continue;
            }

            files.add(fileStr);
        }

        return files.toArray(new String[0]);
    }

    /**
     * 二级目录的批量合并
     *
     * @param filePath
     */
    public static void videoMergeForDirectoryLv2(String filePath, String targetPath) {

        File file = new File(filePath);
        //输入流
        FileInputStream fis = null;
        //输出流
        FileOutputStream fos = null;
        int len;
        //一次读取2M数据，将读取到的数据保存到byte字节数组中
        byte[] bytes = new byte[2 * 1024 * 1024];
        //判断该路径文件或文件夹是否存在
        if (!file.exists()) {
            //不存在退出
//            log.info("路径不存在");
            System.out.println("路径不存在");
            return;
        }
        //判断是否为文件夹
        boolean isDirectory = file.isDirectory();
        if (!isDirectory) {
//            log.info("路径不是文件目录！");
            System.out.println("路径不是文件目录！");
            return;
        }
        //获取目录下的所有文件或文件夹，然后按照文件后缀降序排序
        String[] fileList = filterFolder(filePath, file.list());
        Arrays.sort(fileList, new DirectoryCompare());
        try {
            fos = new FileOutputStream(targetPath + file.getName() + ".ts");

            //遍历一级目录下列表
            for (String s : fileList) {
                //判断是否为二级目录,不是目录直接跳过
                File fileLv2 = new File(file, s);
                if (!fileLv2.isDirectory()) {
                    continue;
                }
                //获取二级目录下列表，然后降序排序
                String[] fileListLv2 = fileLv2.list();
                Arrays.sort(fileListLv2, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        String num1 = o1.substring(0, o1.lastIndexOf("."));
                        String num2 = o2.substring(0, o2.lastIndexOf("."));
                        return Integer.valueOf(num1) - Integer.valueOf(num2);
                    }
                });
                //遍历二级目录
                for (String s1 : fileListLv2) {
                    //创建文件输入流
                    fis = new FileInputStream(new File(fileLv2, s1));
                    len = 0;
                    while ((len = fis.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);//bytes从指定字节数组写入。bytes:数据中的起始偏移量,len:写入的字数。
                    }
                    fis.close();
                }
                fos.flush();
            }
            System.out.println("合并完成");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}