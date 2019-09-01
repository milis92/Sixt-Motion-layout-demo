package io.milis.sixt.home

import android.view.Gravity
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import io.milis.sixt.home.ui.home.HomeActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(HomeActivity::class.java)


    @Test
    fun drawerClosedOnStartup() {
        onView(withId(R.id.drawerLayout))
                .check(matches(isClosed(Gravity.END)))
    }

    @Test
    fun menuButtonOpensDrawer() {
        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.END)))
        onView(withId(R.id.mt_nav)).perform(click())
        onView(withId(R.id.drawerLayout)).check(matches(isOpen(Gravity.END)))
    }

    @Test
    fun motionClosedByDefault() {
        onView(withId(R.id.motionLayout)).check(matches(isAtState(R.id.baseStart)))
    }

    @Test
    fun motionExpandedOnSwipe() {
        onView(withId(R.id.motionLayout)).check(matches(isAtState(R.id.baseStart)))
        onView(withId(R.id.topImageContainer)).perform(swipeUp())
        onView(withId(R.id.motionLayout)).check(matches(isAtState(R.id.baseEnd)))
    }

    private fun isAtState(currentState: Int): Matcher<View> {
        return object : BoundedMatcher<View, MotionLayout>(MotionLayout::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("is at state")
            }

            public override fun matchesSafely(drawer: MotionLayout): Boolean {
                return drawer.currentState == currentState
            }
        }
    }
}
