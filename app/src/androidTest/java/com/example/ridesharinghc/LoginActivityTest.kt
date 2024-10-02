import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.ridesharinghc.activities.LoginActivity
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    @Test
    fun testLoginButtonVisible() {
        composeTestRule.onNodeWithTag("loginButton")
            .assertExists()
    }

    @Test
    fun testSuccessfulLogin() {
        composeTestRule.onNodeWithTag("loginButton")
            .performClick()
    }
}
