# 🌟 **Creating the Splash Screen**

The splash screen is the first screen users see when they open your app. It’s a great opportunity to create a visually appealing first impression(in my case I like to use the logo of my app to capture the users attention). In this section, you will learn how to create a simple splash screen using **Jetpack Compose**.

---

### 🚀 **Steps to Create a Splash Screen**

1. **Design the Splash Screen Layout**: Use `Box` to position elements in the center of the screen.
2. **Add a Logo or Image**: Display your app’s logo or any other image.
3. **Navigate After a Delay**: Automatically navigate to the main screen after a few seconds using `LaunchedEffect`.

---

### 💻 **Example Code**

Here’s how you can implement a basic splash screen in Jetpack Compose:

Kotlin
```
@Composable
override fun SplashScreen(navController: NavController) {
    // Center the logo/image in the screen
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.your_logo), // Here you can replace with your logo or image
            contentDescription = "App Logo or Image"
        )
    }

    // Navigate to the next screen after a delay
    LaunchedEffect(key1 = true) {
        delay(3000L) // 3-second delay
        navController.navigate("home_screen")
    }
}
```

### 📚 **Explanation of Code**
- **Box Layout**: We use `Box` to place the image in the center of the screen.
- **Image**: The `Image` composable displays the app logo or any splash image.
- **LaunchedEffect**: We use `LaunchedEffect` with `delay()` to pause for 3 seconds before navigating to the home screen.
- **Navigation**: After the delay, the `navController.navigate()` method moves the user to the next screen (in my case it would be the, login/register screen, but in yours it can be other like for example home screen).

---

### 🎨 **Customizing the Splash Screen**
Here are a few ways you can build up your splash screen:

- **Background Color**: Set a background color to match your logo or idea of your app.
- **Animations**: Add animations to your logo for a more dynamic effect.
- **Transition Duration**: Adjust the delay according to your needs.

---

### 📚 **Related Resources**
- [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- [Splash screens Views](https://developer.android.com/develop/ui/views/launch/splash-screen)
- [How to apply splash screen with jetpack compose](https://stackoverflow.com/questions/77187576/how-to-apply-splash-screen-with-jetpack-compose)
- [Splash Screen API with Jetpack Compose by Maria Luíza](https://medium.com/mobile-app-development-publication/splash-screen-api-with-jetpack-compose-9001c561b9c8)

---

With everything set up. Continue to **[Creating Lists in Jetpack Compose](layouts.md)**.
