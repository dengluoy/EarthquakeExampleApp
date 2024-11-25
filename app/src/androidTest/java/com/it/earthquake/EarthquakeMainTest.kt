package com.it.earthquake

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.it.earthquake.ui.activity.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.it.earthquake.ui.adapter.EarthquakeListAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EarthquakeMainTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun testActivityLaunch() {
        assert(activityRule.activity != null)
    }

    @Test
    fun testRecyclerViewIsDisplayed() {
        onView(withId(R.id.earthquake_recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewPopulatesData() {
        // 模拟点击第一项
        onView(withId(R.id.earthquake_recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<EarthquakeListAdapter.VM>(
                    0,
                    click()
                )
            )

        // 检查点击后是否跳转到详细页面
        onView(withId(R.id.bd_mapView))
            .check(matches(isDisplayed()))
    }

}