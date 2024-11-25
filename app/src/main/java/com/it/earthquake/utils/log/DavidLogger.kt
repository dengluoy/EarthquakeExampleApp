package com.it.earthquake.utils.log

import com.it.david.utils.log.createPlatformDefaultLogPrinter

/**
 * 日志工具类.
 * 1. Logger 日志门面，主要提供便捷的方法.
 * 2. LogPrinter 日志输出，主要完成各种输出接口的适配.
 * 3. LoggerFactory 日志共产，主要用于生产于Logger.
 */
open class DavidLogger(
    var tag: String = "DavidLogger",
    var level: LogLevel = LogLevel.VERBOSE,
    var logPrinter: LogPrinter = createPlatformDefaultLogPrinter()
) {

    inline fun v(block: () -> Any?) =
        log(LogLevel.VERBOSE, null, block)

    inline fun d(block: () -> Any?) =
        log(LogLevel.DEBUG, null, block)

    inline fun i(block: () -> Any?) =
        log(LogLevel.INFO, null, block)

    inline fun w(throwable: Throwable? = null, block: () -> Any?) =
        log(LogLevel.WARNING, throwable, block)

    inline fun e(throwable: Throwable? = null, block: () -> Any?) =
        log(LogLevel.ERROR, throwable, block)

    inline fun wtf(throwable: Throwable? = null, block: () -> Any?) =
        log(LogLevel.WTF, throwable, block)

    fun v(message: Any?) =
        log(LogLevel.VERBOSE, message, null)

    fun d(message: Any?) =
        log(LogLevel.DEBUG, message, null)

    fun i(message: Any?) =
        log(LogLevel.INFO, message, null)

    @JvmOverloads
    fun w(message: Any?, throwable: Throwable? = null) =
        log(LogLevel.WARNING, message, throwable)

    @JvmOverloads
    fun e(message: Any?, throwable: Throwable? = null) =
        log(LogLevel.ERROR, message, throwable)

    @JvmOverloads
    fun wtf(message: Any?, throwable: Throwable? = null) =
        log(LogLevel.WTF, message, throwable)

    fun log(
        level: LogLevel,
        message: Any?,
        throwable: Throwable? = null
    ) {
        if (this.level <= level) {
            logPrinter.log(level, this.tag, message, throwable)
        }
    }

    inline fun log(
        level: LogLevel,
        throwable: Throwable? = null,
        block: () -> Any?
    ) {
        if (this.level <= level) {
            log(level, block(), throwable)
        }
    }

    /**
     * 生产子Logger的方法，必要时可以冲重新设置
     */
    var loggerFactory: (childTag: String) -> DavidLogger = ::defaultLoggerFactory

    /**
     * 创建子Logger
     * @param subTag 次级tag, 一般为模块名.
     */
    operator fun get(subTag: String) = loggerFactory(subTag)

    companion object INSTANCE : DavidLogger(tag = "Logger")

    /**
     * 日志等级.
     */
    enum class LogLevel(val shortName: String) {
        VERBOSE("V"),
        DEBUG("D"),
        INFO("I"),
        WARNING("W"),
        ERROR("E"),
        WTF("WTF")
    }

    /**
     * 日志输出.
     * 这里，LogPrinter 是一个函数式接口。 有它只定义了一个抽象方法log，可以被用作Lambda表达式或方法引用。 func interface 的关键特性是保障接口只能有一个抽象方法，因此它可以用于Lambda表达方式.
     *  如果是Java的方式可以是这样：
     *  @FunctionalInterface
     * public interface LogPrinter {
     *     void log(LogLevel level, String tag, Object messageAny, Throwable throwable);
     * }
     */
    fun interface LogPrinter {
        fun log(
            level: LogLevel,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
        )

    }
}

internal fun DavidLogger.defaultLoggerFactory(subTag: String) =
    DavidLogger("$tag-$subTag", level, logPrinter)

fun DavidLogger.copy(
    tag: String = this.tag,
    level: DavidLogger.LogLevel = this.level,
    logPrinter: DavidLogger.LogPrinter = this.logPrinter,
    loggerFactory: (childTag: String) -> DavidLogger = ::defaultLoggerFactory,
) = DavidLogger(tag, level, logPrinter).also { it.loggerFactory = loggerFactory }

fun Any.loggerForClass() = DavidLogger[javaClass.simpleName]