<button onclick="window.history.back()" class="back-button">← Back</button>

#  🧩 **Layouts and Arrangement: Row and Column**

In Jetpack Compose, organizing UI components in a **Row** or **Column** layout is important to building dynamic designs. The `Row` and `Column` provide flexibility to align, space, and arrange elements easily.

---

### 🛠️ **Arranging Elements in Row and Column**

#### **Row Composable**: Horizontal Layout
A `Row` allows you to arrange elements horizontally. For example, you can place buttons, images, or text next to each other.

Kotlin:
```
@Composable
fun HorizontalLayout() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Left", modifier = Modifier.padding(8.dp))
        Text(text = "Center", modifier = Modifier.padding(8.dp))
        Text(text = "Right", modifier = Modifier.padding(8.dp))
    }
},
```
In the code above:

- `horizontalArrangement = Arrangement.SpaceBetween`: Spreads out elements evenly with space in between.
- `verticalAlignment = Alignment.CenterVertically`: Aligns the elements vertically in the center of the row.

---

#### **Column Composable**: Vertical Layout
A `Column` arranges elements vertically, ideal for stacking items one on top of the other, such as titles, images, and buttons.

Kotlin:
```
@Composable
fun VerticalLayout() {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Top", modifier = Modifier.padding(8.dp))
        Text(text = "Middle", modifier = Modifier.padding(8.dp))
        Text(text = "Bottom", modifier = Modifier.padding(8.dp))
    }
},
```
In this example:

- `verticalArrangement = Arrangement.SpaceAround`: Distributes the elements evenly, with space around them.
- `horizontalAlignment = Alignment.CenterHorizontally`: Aligns the elements horizontally in the center of the column.

---

### ✨ **Aligning and Spacing Elements**
Using `Modifier`, you can easily align and space your elements inside the `Row` or `Column`. Here are a few useful properties:

- **Padding**: `Modifier.padding()` adds space around or inside an element.
- **Alignment**: You can control alignment with `horizontalAlignment` (for `Column`) and `verticalAlignment` (for `Row`).
- **Size**: `Modifier.fillMaxWidth()` or `Modifier.size()` adjusts the size of the component to match its parent or a fixed size.

---

### 📚 **Find more Examples and Related Resources**

- [Jetpack Compose Layouts](https://developer.android.com/develop/ui/compose/layouts)
- [Compose Modifiers Guide](https://developer.android.com/develop/ui/compose/modifiers)

---

With everything set up. Continue to **[Creating Lists in Jetpack Compose](list.md)**.

