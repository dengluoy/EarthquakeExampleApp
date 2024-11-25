package com.it.david.utils.log

import android.util.Log
import com.it.earthquake.utils.log.DavidLogger
import java.io.PrintStream

open class EmptyLogPrinter : DavidLogger.LogPrinter {
    override fun log(
        level: DavidLogger.LogLevel,
        tag: String,
        messageAny: Any?,
        throwable: Throwable?
    ) = Unit
}

class ConsoleLogPrinter(lineBreak: Boolean = true) : DavidLogger.LogPrinter {
    val appendLog: (PrintStream, String) -> Unit =
        if (lineBreak) PrintStream::println else PrintStream::print

    override fun log(
        level: DavidLogger.LogLevel,
        tag: String,
        messageAny: Any?,
        throwable: Throwable?
    ) {
        val message = messageAny ?: return
        val target = if (level <= DavidLogger.LogLevel.INFO) System.out else System.err
        appendLog(target, message.toString())
    }
}

class AndroidLogPrinter() : DavidLogger.LogPrinter {
    override fun log(
        level: DavidLogger.LogLevel,
        tag: String,
        messageAny: Any?,
        throwable: Throwable?
    ) {
        val message = messageAny.toString()
        val androidLogLevel = level.ordinal + 2
        val messageWithError = if (throwable != null) {
            message + "\n" + Log.getStackTraceString(throwable)
        } else message

        Log.println(androidLogLevel, tag, messageWithError)

    }

//    override fun getStackTraceString(t: Throwable) {
//        Log.getStackTraceString()
//    }
}

/**
 * 这段代表意义：通过 反射调用android.util.Log。判断是否会出现异常。如果没有异常则代表是在Android平台.
 *
 * lazy代表懒加载，当使用的时候才执行下面的语句.
 */
private val isAndroidPlatform by lazy {
    runCatching {
        Class.forName("android.util.Log", false, AndroidLogPrinter::class.java.classLoader)
    }.isSuccess
}

@PublishedApi
internal fun createPlatformDefaultLogPrinter(): DavidLogger.LogPrinter {
    return if (isAndroidPlatform) AndroidLogPrinter() else ConsoleLogPrinter(true)
}