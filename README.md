# AppRecordLog
封装App的Logcat的日志输出，可以控制log输出，记录App的Log到文件

开发时候直接用Logcat把log输出到控制台，但是测试的时候是交给测试来做的，测试发现问题但是不知道如果抓取Log。对于这种
情况，我只能去根据测试的描述来复现问题，尤其是设计到多账号的时候，非常痛苦。

这个Lib是Logcat的封装，可以控制在Debug/Release模式下的Log是否输出。可以在记录到App自己打印的Log到文件。默认是
把Log存储到SD卡（getExternalCacheDir()）。
