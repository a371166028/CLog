package com.cvt.clog

import android.content.Context
import com.cvt.library.clog.CLog
import com.cvt.library.clog.Log4aFileLogEngine
import com.cvt.library.clog.LogOptions

/**
 * Date: 2019-09-21 11:50
 * Author: chenchengyin on
 * Email: itmarshon@163.com
 */
class KotlinLogTest {
    fun testLog(applicationContext: Context) {
        //日志初始化
        val absolutePath = applicationContext.cacheDir.absolutePath
        CLog.init(true, "全局TAG", absolutePath, "ccy")
        //打印一条消息
        CLog.v("一条普通的消息")
        CLog.i("一条普通的消息")
        CLog.d("一条普通的消息")
        CLog.w("一条普通的消息")
        CLog.e("一条普通的消息")
        CLog.e("一条普通的消息", RuntimeException("error"))

        //自带TAG
        CLog.d("我是自定义TAG", "这是一条带自定义TAG的消息")

        //漂亮地呈现一条log
        val builder = StringBuilder()
        var count = 0
        while (count <= 100) {
            builder.append("这是一条很长的消息$count ")
            count++
        }
        val toString = builder.toString()
        CLog.pretty(toString)

        //打印Json
        CLog.json("""{"key":"va"}""")

        //打印消息,并带上当前的调用栈
        CLog.stackTrace("看一看调用栈")

        //自定义装饰
        CLog.init(true, "全局TAG", absolutePath, "ccy", MyLogDecoration())
        CLog.i("一条带装饰的日志") //全局
        CLog.i("TAG", "临时的装饰日志", MyLogDecoration())

        //切换到其他日志引擎
        CLog.init(true, "全局TAG", absolutePath, "ccy", null, UseOtherLogEngine())
        CLog.d("TAG", "一条使用其他日志引擎打印的消息")

        //打印消息到控制台和文件,需初始化时提前将日志文件夹,文件名等信息设置好
        CLog.file("打印到日志到文件")
        //单个文件最大大小,最大保存时间,默认清除时间15天; OPS

        //使用Log4a高性能打印到文件
        val logOptions = LogOptions()
        logOptions.logDir = absolutePath
        logOptions.logFileNamePrefix = "ccy"
        logOptions.fileLogEngine = Log4aFileLogEngine(applicationContext)
        CLog.init(true, logOptions)
        CLog.file("使用Log4a打印到文件")


        //封装后打印的log
        logOptions.customWrapper = true
        CLog.init(true, logOptions)
        LogPrinter.d("二次封装打印")

        // 输出字符串
//        LogUtils.d("12345")

    }
}