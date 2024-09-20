# 4. 🛠️ **Basic Composables**

In this section, you’ll learn about **Composables**, the basics building blocks of Jetpack Compose UI. Composables allow you to define the structure and appearance of your app’s UI.

---

### 📖 **What are Composables?**
**Composables** are functions annotated with `@Composable` that allow you to define your UI in Jetpack Compose. It is the basic elements you use to create and arrange all UI components in your app. Whether it's displaying text, creating buttons, or more complex layouts, **Composables** give you complete flexibility.

Kotlin:
```
@Composable
fun Example(name: String) {
    Text(text = "Hello, $name!")
}
```

In the example above, Example is a Composable that displays a simple text Example.

---

### 📝 **Text and Button Composables**

Let’s explore two fundamental Composables: **Text** and **Button**. These are essential for displaying information and interacting with users.

#### 1. **Text Composable**
The `Text` Composable is used to display a simple string of text. Here's a basic example:

Kotlin:
```
@Composable
fun SimpleText() {
    Text(text = "Welcome to Jetpack Compose!")
}
```

In this example, SimpleText displays a string on the screen. You can easily customize the font size, style, and color:

Kotlin:
```
@Composable
fun StyledText() {
    Text(
        text = "Hello!",
        fontSize = 24.sp,
        color = Color.Blue
    )
}
```

---

#### 2. **Button Composable**
The `Button` Composable is used to create a clickable button that performs an action. Here’s how you can create a simple button:

Kotlin:
```
@Composable
fun SimpleButton() {
    Button(onClick = { /* Perform an action */ }) {
        Text(text = "Click Me")
    }
}

```

In this example, clicking the button triggers the onClick action. You can also style your button to match your app’s design:

Kotlin:
```
@Composable
fun StyledButton() {
    Button(
        onClick = { /* Perform an action */ },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
    ) {
        Text(text = "Styled Button", color = Color.White)
    }
}
```

---

### ✨ **Combining Text and Button**
Now, let’s combine the `Text` and `Button` Composables to create a simple interaction:

Kotlin:
```
@Composable
fun example() {
    var example by remember { mutableStateOf("Hello!") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = example, fontSize = 24.sp)
        Button(onClick = { example = "Button Clicked!" }) {
            Text(text = "Click to Change Example")
        }
    }
}
```

In this example:

- The `Text` Composable displays a example.
- The `Button` Composable updates the text when clicked, showcasing basic state management in Jetpack Compose.


---

### 📚 **Related Resources**

- [Jetpack Compose Text](https://developer.android.com/develop/ui/compose/text)
- [Jetpack Compose Buttons](https://developer.android.com/develop/ui/compose/components/button)

---

With the fundamental building blocks covered. Continue to **[Creating the Splash Screen](splash_screen.md)**.

