package com.example.proteintracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Data ─────────────────────────────────────────────────────────────────

data class FoodItem(val name: String, val proteinPerPiece: Int, val unit: String)
data class AddedFood(val name: String, val proteinPerPiece: Int, val unit: String, val quantity: Int)

val indianFoodDatabase = listOf(

    // ─── Kerala / South Indian ───────────────────────────────
    FoodItem("Appam", 1, "piece"),
    FoodItem("Idli", 2, "piece"),
    FoodItem("Idiyappam", 1, "piece"),
    FoodItem("Dosa", 3, "piece"),
    FoodItem("Masala Dosa", 5, "piece"),
    FoodItem("Puttu", 3, "serving"),
    FoodItem("Pathiri", 2, "piece"),
    FoodItem("Unniyappam", 1, "piece"),
    FoodItem("Pazham Pori", 1, "piece"),
    FoodItem("Kappa (Tapioca)", 2, "cup"),
    FoodItem("Porotta", 3, "piece"),

    // ─── Breads ──────────────────────────────────────────────
    FoodItem("Chapati / Roti", 3, "piece"),
    FoodItem("Bread (White)", 2, "slice"),
    FoodItem("Bread (Brown)", 3, "slice"),
    FoodItem("Bread Bun", 4, "piece"),

    // ─── Rice & Biriyani ─────────────────────────────────────
    FoodItem("Rice", 4, "cup"),
    FoodItem("Fried Rice", 6, "cup"),
    FoodItem("Chicken Biriyani", 28, "plate"),
    FoodItem("Mutton Biriyani", 25, "plate"),
    FoodItem("Mandi (Chicken)", 30, "plate"),
    FoodItem("Mandi (Mutton)", 28, "plate"),

    // ─── Eggs ────────────────────────────────────────────────
    FoodItem("Egg", 6, "piece"),
    FoodItem("Omelette", 12, "piece"),
    FoodItem("Egg Curry", 6, "piece"),
    FoodItem("Boiled Egg", 6, "piece"),
    FoodItem("Fried Egg", 6, "piece"),

    // ─── Chicken ─────────────────────────────────────────────
    FoodItem("Chicken Curry", 25, "100g"),
    FoodItem("Chicken Fry", 28, "100g"),
    FoodItem("Butter Chicken", 22, "cup"),
    FoodItem("Kadai Chicken", 24, "cup"),
    FoodItem("Chicken Shawarma", 20, "piece"),

    // ─── Beef & Mutton ───────────────────────────────────────
    FoodItem("Beef Curry", 22, "100g"),
    FoodItem("Beef Fry", 25, "100g"),
    FoodItem("Mutton Curry", 20, "100g"),

    // ─── Fish & Seafood ──────────────────────────────────────
    FoodItem("Fish Curry", 20, "100g"),
    FoodItem("Fish Fry", 22, "100g"),
    FoodItem("Prawn Curry", 18, "100g"),

    // ─── Dal & Curries ───────────────────────────────────────
    FoodItem("Dal", 9, "cup"),
    FoodItem("Sambar", 5, "cup"),
    FoodItem("Rasam", 2, "cup"),
    FoodItem("Chana Masala", 12, "cup"),
    FoodItem("Palak Paneer", 10, "cup"),
    FoodItem("Mixed Veg Curry", 4, "cup"),
    FoodItem("Rajma", 15, "cup"),

    // ─── Dairy ───────────────────────────────────────────────
    FoodItem("Paneer", 18, "100g"),
    FoodItem("Curd / Yogurt", 4, "100g"),
    FoodItem("Butter", 0, "tbsp"),
    FoodItem("Cheese Slice", 4, "piece"),
    FoodItem("Ghee", 0, "tbsp"),

    // ─── Snacks & Street Food ────────────────────────────────
    FoodItem("Samosa", 4, "piece"),
    FoodItem("Vada", 3, "piece"),
    FoodItem("Pani Puri", 1, "piece"),
    FoodItem("Vada Pav", 5, "piece"),

    // ─── Bakery ──────────────────────────────────────────────
    FoodItem("Egg Puff", 8, "piece"),
    FoodItem("Chicken Puff", 10, "piece"),
    FoodItem("Veg Puff", 4, "piece"),
    FoodItem("Cake Slice", 4, "slice"),
    FoodItem("Biscuits", 2, "piece"),
    FoodItem("Banana Bread", 3, "slice"),

    // ─── Sweets ──────────────────────────────────────────────
    FoodItem("Jalebi", 1, "piece"),
    FoodItem("Laddu", 3, "piece"),
    FoodItem("Halwa", 2, "serving"),
    FoodItem("Kheer", 5, "cup"),
    FoodItem("Gulab Jamun", 2, "piece"),
    FoodItem("Rasgulla", 2, "piece"),

    // ─── Fruits ──────────────────────────────────────────────
    FoodItem("Banana", 1, "piece"),
    FoodItem("Apple", 1, "piece"),
    FoodItem("Mango", 2, "cup"),
    FoodItem("Watermelon", 1, "cup"),
    FoodItem("Grapes", 1, "cup"),
    FoodItem("Orange", 1, "piece"),
    FoodItem("Papaya", 1, "cup"),
    FoodItem("Pineapple", 1, "cup"),
    FoodItem("Pomegranate", 2, "cup"),
    FoodItem("Guava", 2, "piece"),
    FoodItem("Pear", 1, "piece"),
    FoodItem("Strawberry", 1, "cup"),
    FoodItem("Kiwi", 1, "piece"),
    FoodItem("Coconut", 2, "piece"),
    FoodItem("Jackfruit", 2, "cup"),
    FoodItem("Sapota (Chikoo)", 1, "piece"),
    FoodItem("Lychee", 1, "cup"),
    FoodItem("Fig", 1, "piece"),
    FoodItem("Dates", 2, "piece"),

    // ─── Vegetables ──────────────────────────────────────────
    FoodItem("Spinach", 3, "cup"),
    FoodItem("Carrot", 1, "piece"),
    FoodItem("Beetroot", 2, "piece"),
    FoodItem("Potato", 2, "piece"),
    FoodItem("Broccoli", 4, "cup"),
    FoodItem("Green Peas", 5, "cup"),
    FoodItem("Cucumber", 1, "piece"),
    FoodItem("Tomato", 1, "piece"),
    FoodItem("Onion", 1, "piece"),
    FoodItem("Cabbage", 2, "cup"),
    FoodItem("Cauliflower", 3, "cup"),
    FoodItem("Beans", 2, "cup"),
    FoodItem("Drumstick (Moringa)", 2, "piece"),
    FoodItem("Bitter Gourd", 1, "piece"),
    FoodItem("Bottle Gourd", 1, "cup"),
    FoodItem("Pumpkin", 1, "cup"),
    FoodItem("Corn", 3, "piece"),
    FoodItem("Mushroom", 3, "cup"),
    FoodItem("Sweet Potato", 2, "piece"),
    FoodItem("Ash Gourd", 1, "cup"),
    FoodItem("Raw Banana", 2, "piece"),
    FoodItem("Yam", 2, "cup"),

    // ─── Milk ────────────────────────────────────────────────
    FoodItem("Milk", 8, "glass"),
    FoodItem("Chocolate Milk", 8, "glass"),
    FoodItem("Soy Milk", 7, "glass"),
    FoodItem("Almond Milk", 2, "glass"),

    // ─── Hot Drinks ───────────────────────────────────────────
    FoodItem("Hot Coffee with Milk", 3, "cup"),
    FoodItem("Black Coffee", 0, "cup"),
    FoodItem("Hot Tea with Milk", 2, "cup"),
    FoodItem("Black Tea", 0, "cup"),
    FoodItem("Latte", 5, "cup"),
    FoodItem("Cappuccino", 4, "cup"),
    FoodItem("Green Tea", 0, "cup"),
    FoodItem("Masala Chai", 3, "cup"),

    // ─── Shakes ──────────────────────────────────────────────
    FoodItem("Sharjah Shake", 6, "glass"),
    FoodItem("Pista Shake", 8, "glass"),
    FoodItem("Badam Shake", 7, "glass"),
    FoodItem("Banana Shake", 7, "glass"),
    FoodItem("Mango Shake", 5, "glass"),
    FoodItem("Chocolate Shake", 8, "glass"),
    FoodItem("Strawberry Shake", 5, "glass"),
    FoodItem("Cold Coffee", 5, "glass"),
    FoodItem("Vanilla Shake", 6, "glass"),
    FoodItem("Mixed Fruit Shake", 4, "glass"),
    FoodItem("Papaya Shake", 4, "glass"),
    FoodItem("Chikoo Shake", 4, "glass"),
    FoodItem("Avocado Shake", 5, "glass"),
    FoodItem("Dates Shake", 5, "glass"),
    FoodItem("Rose Milk", 4, "glass"),
    FoodItem("Lassi", 6, "glass"),
    FoodItem("Sweet Lassi", 5, "glass"),
    FoodItem("Mango Lassi", 5, "glass"),
    FoodItem("Buttermilk", 3, "glass"),
    FoodItem("Coconut Water", 2, "glass"),
    FoodItem("Protein Shake", 25, "glass"),

    // ─── Nuts & Seeds ────────────────────────────────────────
    FoodItem("Peanuts", 7, "handful"),
    FoodItem("Almonds", 6, "handful"),
    FoodItem("Cashews", 5, "handful"),
    FoodItem("Pistachios", 6, "handful"),
    FoodItem("Walnuts", 4, "handful"),
    FoodItem("Raisins", 1, "handful"),
    FoodItem("Chia Seeds", 3, "tbsp"),
    FoodItem("Flax Seeds", 2, "tbsp"),
    FoodItem("Sunflower Seeds", 3, "tbsp"),
    FoodItem("Pumpkin Seeds", 4, "tbsp"),
    FoodItem("Hemp Seeds", 5, "tbsp"),
    FoodItem("Sesame Seeds", 2, "tbsp"),
    FoodItem("Fox Nuts (Makhana)", 3, "handful"),
    FoodItem("Mixed Nuts", 5, "handful"),

    // ─── Supplements ─────────────────────────────────────────
    FoodItem("Whey Protein", 25, "scoop"),
    FoodItem("Whey Isolate", 27, "scoop"),
    FoodItem("Casein Protein", 24, "scoop"),
    FoodItem("Plant Protein", 20, "scoop"),
    FoodItem("Mass Gainer", 30, "scoop"),
    FoodItem("Protein Bar", 20, "piece"),
    FoodItem("Peanut Butter", 4, "tbsp"),
    FoodItem("BCAA", 0, "scoop"),
    FoodItem("Creatine", 0, "scoop"),
    FoodItem("Pre-Workout", 0, "scoop"),
    FoodItem("Multivitamin", 0, "piece"),
    FoodItem("Fish Oil", 0, "piece"),
    FoodItem("Vitamin D", 0, "piece"),
    FoodItem("Zinc", 0, "piece")
)


// ─── Main ──────────────────────────────────────────────────────────────────

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ProfyApp() }
    }
}

// ─── App Navigator ─────────────────────────────────────────────────────────

@Composable
fun ProfyApp() {
    val addedFoods = remember { mutableStateListOf<AddedFood>() }
    var showAddFoodScreen by remember { mutableStateOf(false) }

    if (showAddFoodScreen) {
        AddFoodScreen(
            onBack = { showAddFoodScreen = false },
            onFoodAdded = { food -> addedFoods.add(food) }
        )
    } else {
        HomeScreen(
            addedFoods = addedFoods,
            onAddFoodClick = { showAddFoodScreen = true },
            onDeleteFood = { food -> addedFoods.remove(food) }
        )
    }
}

// ─── Home Screen ───────────────────────────────────────────────────────────

@Composable
fun HomeScreen(
    addedFoods: List<AddedFood>,
    onAddFoodClick: () -> Unit,
    onDeleteFood: (AddedFood) -> Unit
) {
    val userName = "Ameer"
    val dailyGoal = 120
    val consumed = addedFoods.sumOf { it.proteinPerPiece * it.quantity }
    val remaining = dailyGoal - consumed
    var foodToDelete by remember { mutableStateOf<AddedFood?>(null) }

    if (foodToDelete != null) {
        AlertDialog(
            onDismissRequest = { foodToDelete = null },
            title = { Text("Remove Food?") },
            text = { Text("Do you want to remove ${foodToDelete!!.name} from your list?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteFood(foodToDelete!!)
                    foodToDelete = null
                }) {
                    Text("Yes", color = Color(0xFFE63946))
                }
            },
            dismissButton = {
                TextButton(onClick = { foodToDelete = null }) {
                    Text("No")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(20.dp)
    ) {
        Text(
            text = "PROFY",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2D6A4F)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Hey $userName, How's it going? 👋",
            fontSize = 16.sp,
            color = Color(0xFF555555)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Progress Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2D6A4F))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Today's Progress", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProgressStat("${dailyGoal}g", "Goal")
                    ProgressStat("${consumed}g", "Consumed")
                    ProgressStat("${remaining}g", "Remaining")
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { (consumed.toFloat() / dailyGoal).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color(0xFF95D5B2),
                    trackColor = Color(0xFF1B4332)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "What did you eat today? 🍽️",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1a1a2e)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (addedFoods.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No foods added yet.\nTap + Add Food to get started!",
                    color = Color(0xFFAAAAAA),
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(addedFoods) { food ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    food.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    "${food.quantity} ${food.unit}",
                                    fontSize = 12.sp,
                                    color = Color(0xFFAAAAAA)
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "${food.proteinPerPiece * food.quantity}g protein",
                                    fontSize = 14.sp,
                                    color = Color(0xFF2D6A4F),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color(0xFFE63946),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { foodToDelete = food }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onAddFoodClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D6A4F))
        ) {
            Text("+ Add Food", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ─── Progress Stat ─────────────────────────────────────────────────────────

@Composable
fun ProgressStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(label, color = Color(0xFFB7E4C7), fontSize = 12.sp)
    }
}

// ─── Add Food Screen ───────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    onBack: () -> Unit,
    onFoodAdded: (AddedFood) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFood by remember { mutableStateOf<FoodItem?>(null) }
    var quantity by remember { mutableStateOf(1) }

    val filteredFoods = indianFoodDatabase.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2D6A4F))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Add Food",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search foods...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Selected food quantity card
            if (selectedFood != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2D6A4F))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            selectedFood!!.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            "Protein per ${selectedFood!!.unit}: ${selectedFood!!.proteinPerPiece}g",
                            color = Color(0xFFB7E4C7),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "How many ${selectedFood!!.unit}s?",
                                color = Color.White
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Button(
                                    onClick = { if (quantity > 1) quantity-- },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF1B4332)
                                    ),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                ) { Text("-") }

                                Text(
                                    "$quantity",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )

                                Button(
                                    onClick = { quantity++ },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF1B4332)
                                    ),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                ) { Text("+") }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Total Protein: ${selectedFood!!.proteinPerPiece * quantity}g",
                            color = Color(0xFF95D5B2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                onFoodAdded(
                                    AddedFood(
                                        name = selectedFood!!.name,
                                        proteinPerPiece = selectedFood!!.proteinPerPiece,
                                        unit = selectedFood!!.unit,
                                        quantity = quantity
                                    )
                                )
                                selectedFood = null
                                quantity = 1
                                onBack()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF95D5B2)
                            )
                        ) {
                            Text(
                                "Add to my list ✓",
                                color = Color(0xFF1B4332),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Food list
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredFoods) { food ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedFood = food
                                quantity = 1
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedFood == food)
                                Color(0xFFD8F3DC) else Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    food.name,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                                Text(
                                    "per ${food.unit}",
                                    fontSize = 12.sp,
                                    color = Color(0xFFAAAAAA)
                                )
                            }
                            Text(
                                "${food.proteinPerPiece}g",
                                color = Color(0xFF2D6A4F),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}