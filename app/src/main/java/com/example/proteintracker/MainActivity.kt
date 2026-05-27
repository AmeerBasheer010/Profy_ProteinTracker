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
    // Kerala / South Indian
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
    FoodItem("Banana", 1, "piece"),

    // Breads
    FoodItem("Chapati / Roti", 3, "piece"),
    FoodItem("Porotta", 3, "piece"),
    FoodItem("Bread (White)", 2, "slice"),
    FoodItem("Bread (Brown)", 3, "slice"),
    FoodItem("Bread Bun", 4, "piece"),

    // Rice
    FoodItem("Rice", 4, "cup"),
    FoodItem("Fried Rice", 6, "cup"),
    FoodItem("Chicken Biriyani", 28, "plate"),
    FoodItem("Mutton Biriyani", 25, "plate"),
    FoodItem("Mandi (Chicken)", 30, "plate"),
    FoodItem("Mandi (Mutton)", 28, "plate"),

    // Eggs
    FoodItem("Egg", 6, "piece"),
    FoodItem("Omelette", 12, "piece"),
    FoodItem("Egg Curry", 6, "egg"),

    // Chicken
    FoodItem("Chicken Curry", 25, "100g"),
    FoodItem("Chicken Fry", 28, "100g"),
    FoodItem("Butter Chicken", 22, "cup"),
    FoodItem("Kadai Chicken", 24, "cup"),

    // Beef & Mutton
    FoodItem("Beef Curry", 22, "100g"),
    FoodItem("Beef Fry", 25, "100g"),
    FoodItem("Mutton Curry", 20, "100g"),

    // Fish & Seafood
    FoodItem("Fish Curry", 20, "100g"),
    FoodItem("Fish Fry", 22, "100g"),
    FoodItem("Prawn Curry", 18, "100g"),

    // Dal & Curry
    FoodItem("Dal", 9, "cup"),
    FoodItem("Sambar", 5, "cup"),
    FoodItem("Rasam", 2, "cup"),
    FoodItem("Chana Masala", 12, "cup"),
    FoodItem("Palak Paneer", 10, "cup"),
    FoodItem("Mixed Veg Curry", 4, "cup"),

    // Dairy
    FoodItem("Milk", 8, "glass"),
    FoodItem("Curd / Yogurt", 4, "100g"),
    FoodItem("Paneer", 18, "100g"),
    FoodItem("Butter", 0, "tbsp"),

    // Snacks & Street Food
    FoodItem("Shawarma", 20, "piece"),
    FoodItem("Samosa", 4, "piece"),
    FoodItem("Vada", 3, "piece"),
    FoodItem("Pani Puri", 1, "piece"),
    FoodItem("Peanuts", 7, "handful"),
    FoodItem("Soya Chunks", 52, "100g"),

    // Bakery
    FoodItem("Egg Puff", 8, "piece"),
    FoodItem("Chicken Puff", 10, "piece"),
    FoodItem("Veg Puff", 4, "piece"),
    FoodItem("Cake Slice", 4, "slice"),
    FoodItem("Biscuits", 2, "piece"),
    FoodItem("Banana Bread", 3, "slice"),

    // Sweets
    FoodItem("Jalebi", 1, "piece"),
    FoodItem("Laddu", 3, "piece"),
    FoodItem("Halwa", 2, "serving"),
    FoodItem("Kheer", 5, "cup")
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