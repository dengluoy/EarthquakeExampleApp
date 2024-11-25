package com.it.earthquake.utils

import kotlin.math.log10
import kotlin.math.sqrt

class Utils private constructor() {

    companion object {

        @JvmStatic
        fun calculateRadius(mag: Float, dept: Double): Double {
            // 配置参数
            val minRadius = 5000.0       // 最小半径
            val maxRadius = 50000.0      // 最大半径
            val magWeight = 1000.0       // 震级权重
            val depthWeight = 5.0        // 深度权重
            val scaleFactor = 2.0        // 缩放因子
            val depth = log10(dept)
            // 对震级和深度进行平滑处理
            val adjustedMag = sqrt(mag) // 或者 Math.log(mag)
            val adjustedDepth = sqrt(depth) // 或者 Math.log(depth)

            // 计算初始半径
            val rawRadius = scaleFactor * (magWeight * adjustedMag + depthWeight * adjustedDepth)

            // 限制范围
            return rawRadius.coerceIn(minRadius, maxRadius)
        }

        @JvmStatic
        fun calculateResultLinear(x: Double, xMin: Double = 10.0, xMax: Double = 600.0): Float {
            val yMin = 6.0
            val yMax = 12.0
            return (yMin + (yMax - yMin) * (xMax - x) / (xMax - xMin)).toFloat()
        }
    }
}