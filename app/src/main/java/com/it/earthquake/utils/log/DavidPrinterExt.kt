package com.it.david.utils.log

import java.util.concurrent.Executor

/**
 * typealias 语法 用于为复杂的类型创建一个简洁的别名，这里的DavidLogPrinterInterceptor是一个高阶函数类型别名，
 * 它代表一个接受logPrinter、level、tag、messageAny、throwable参数并返回Unit的函数
 * LogFormatter则表示一个类似的函数，但返回值为String.
 *
 * 目的：这样做的好处是提高了代码的可读性，可以在需要传递复杂函数类型简化代码.
 * 问题：那Unit 是什么东西？
 * GPT答：Unit等于是没有返回值的意思，但它和Java的 Void有所不同.它更像是一个标志物的kotlin 类.代表没有返回.
  */

typealias DavidLogPrinterInterceptor = (logPrinter: DavidLogger.LogPrinter, level: DavidLogger.LogLevel, tag:String, messageAny:Any?, throwable: Throwable?) -> Unit

/**
 * 扩展函数
 * intercept 是DavidLogger.LogPrinter的扩展函数。扩展函数允许在不修改类本身的情况下，添加新的功能.
 * 参数详情：
 * 1. crossinline: 确保 interceptor Lambda 表达式不会直接使用 return 关键字。inline可以提高性能，尤其是在高频调用中.
 * 2. 返回新实例： DavidLogger.LogPrinter{...} 用于创建新的LogPrinter实例，且在其中执行 interceptor函数，方便后执行拦截逻辑.
 */

/**
 * 拦截器.
 */
inline fun DavidLogger.LogPrinter.intercept(crossinline interceptor: DavidLogPrinterInterceptor) =
    DavidLogger.LogPrinter { level, tag, messageAny, throwable ->
        interceptor(this@intercept, level, tag, messageAny, throwable)
    }

typealias LogFormatter = (level: DavidLogger.LogLevel, tag: String, messageAny: Any?, throwable: Throwable?)-> String

/**
 * 这个format函数通过 intercept 扩展实现日志的格式化。调用interceptr并传递一个LogFormatter会将格式化后的消息传递给LogPrinter.log。这相当于在原有log方法上添加了格式化层.
 */

/**
 * 设置日志.
 */
fun DavidLogger.LogPrinter.format(formatter: LogFormatter) =
    intercept {logPrinter, level, tag, messageAny, throwable
        -> val formattedMessage = formatter(level, tag, messageAny, throwable)
    logPrinter.log(level, tag, formattedMessage, throwable)
    }

/**
 * 这个logAt函数将日志记录动作放在指定的Executor线程中执行。executor.execute会将日志的实际记录任务交由executor处理。
 * 以避免阻塞主线程，尤其适合异步日志处理场景。
 */

/**
 * 设置日志记录线程.
 * 它等于是利用了intercept拦截器 无感的封装了logAt线程功能.
 */
fun DavidLogger.LogPrinter.logAt(executor: Executor) =
    intercept{logPrinter, level, tag, messageAny, throwable ->
        executor.execute {
            logPrinter.log(level, tag, messageAny, throwable)
        }
    }

/**
 * logAlso 是一个组合日志记录器。它会先调用当前的logPrinter，然后调用other.log.
 * 相当于一次性输出到多个日志通道，类似于多播.
 * 问题： also 是什么？？
 * 答：also 主要是用于官方封装的一个链式调用函数，比如在一个简单调用中额外增加一次调用就会用到他并且返回对象本身.
 * 例如：
 * val numbers = mutableListOf("one", "two", "three")
 *
 * // 在链式调用中，使用 also 添加元素并打印日志
 * numbers.also { println("The list before adding a new item: $it") }
 *        .add("four")
 *        .also { println("The list after adding a new item: $it") }
 * 打印出来的结果：
 * The list before adding a new item: [one, two, three]
 * The list after adding a new item: [one, two, three, four]
 */

/**
 * 添加额外的日志记录器.
 * 多播：两个LogPrinter各播一次.
 */
fun DavidLogger.LogPrinter.logAlso(other: DavidLogger.LogPrinter) =
    intercept { logPrinter, level, tag, messageAny, throwable ->
        logPrinter.log(level, tag, messageAny, throwable)
        other.log(level, tag, messageAny, throwable)
    }

/**
 * 这里重载了+操作符，可以方便的将多个 LogPrinter 合并在一起.
 * 这个其实是最终更新了 DavidLogger.logPrinter成员变量.
 */
operator fun DavidLogger.plusAssign(other: DavidLogger.LogPrinter) {
    logPrinter = logPrinter.logAlso(other)
}


/**
 * 问题：predicate 函数是做什么的？
 * 实则是官方的一个过滤条件语法.
 */

/**
 * 日志过滤.
 */
fun DavidLogger.LogPrinter.filter(
    predicate: (
            level: DavidLogger.LogLevel,
            tag: String,
            messageAny: Any?,
            throwable: Throwable?
            ) -> Boolean
) =
    intercept {logPrinter, level, tag, messageAny, throwable ->
        if (predicate(level, tag, messageAny, throwable)) {
            logPrinter.log(level, tag, messageAny, throwable)
        }
    }

/**
 *  filterLevel使用 filter函数，将日志级别抵御minLevel的日志进行过滤。这是最常见的级别过滤.
 *  问题：但是它这个 _,_是什么东西？？
 *  答：_代表是Kotlin中的占位符，代表忽略的参数.
 */

/**
 * 日志过滤.
 */
fun DavidLogger.LogPrinter.filterLevel(minLevel: DavidLogger.LogLevel) =
    filter { level, _, _, _ -> level >= minLevel }