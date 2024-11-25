package com.it.earthquake.ui.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.it.earthquake.model.Feature
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TextBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["formatDate"])
        fun formatDate(textView: TextView, timestamp: Long) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            // 设置时区
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            // 转换时间戳为日期对象
            val date = Date(timestamp)
            // 格式化时间
            textView.text = dateFormat.format(date)
        }

        @JvmStatic
        @BindingAdapter(value = ["warningColor"])
        fun earthquakeTextColor(textView: TextView, mag: Double) {
            textView.setTextColor(if (mag >= 7.5) Color.RED else Color.BLACK)
        }

        @JvmStatic
        @BindingAdapter(value = ["earthquakeDesc"])
        fun earthquakeTextColor(textView: TextView, feature: Feature) {
            val text = "Magnitude ${feature.properties?.mag}，${feature.geometry!!.coordinates[2]}m"
            textView.text = text
        }

    }
}