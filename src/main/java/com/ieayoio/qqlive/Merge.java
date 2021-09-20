package com.ieayoio.qqlive;


public class Merge {

    public static void main(String[] args) {
        //一级目录路径
        String path = "/Users/ieayoio/tmp/122/y0038k189n8.322013.hls";
        //合并后的文件路径，合并后的文件名为:一级目录的字符串名.ts，如：y0038k189n8.322013.hls.ts
        String targetPath = "/Users/ieayoio/tmp/122/122/";
        MergeUtil.videoMergeForDirectoryLv2(path, targetPath);
    }
}