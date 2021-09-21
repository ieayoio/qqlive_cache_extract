# qqlive_cache_extract

安卓腾讯视频下载缓存ts文件提取合并

参考：https://blog.csdn.net/weixin_40562486/article/details/103287779

## 提取下载目录
腾讯下载目录：`/Android/data/com.tencent.qqlive/files/videos_XXXXX/`

视频下载目录下每个`.hls`结尾的文件夹为一个视频的缓存

例如一个视频`y0038k189n8.322013.hls`目录结构形如下:

```
├── offline.m3u8
├── tpt
│   ├── y0038k189n8.322013.hls.1
│   ├── y0038k189n8.322013.hls.2
│   ├── ......
├── y0038k189n8.322013.hls_0_29
│   ├── 0.ts
│   ├── 1.ts
│   ├── ......
│   └── 29.ts
├── y0038k189n8.322013.hls_30_59
│   ├── 30.ts
│   ├── 31.ts
│   ├── ......
│   └── 59.ts
├── y0038k189n8.322013.hls_60_89
│   ├── 60.ts
│   ├── 61.ts
│   ├── ......
│   └── 89.ts
└── ......
```

## 合并视频
```java
//一级目录路径
String path = "/Users/ieayoio/tmp/122/y0038k189n8.322013.hls";
//合并后的文件路径，合并后的文件名为:一级目录的字符串名.ts，如：y0038k189n8.322013.hls.ts
String targetPath = "/Users/ieayoio/tmp/122/122/";
MergeUtil.videoMergeForDirectoryLv2(path, targetPath);
```
