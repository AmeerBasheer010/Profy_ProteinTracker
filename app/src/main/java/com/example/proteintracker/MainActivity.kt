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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate


fun formatProtein(value: Double): String {
    return if (value == value.toInt().toDouble()) {
        "${value.toInt()}"
    } else {
        String.format("%.1f", value)
    }
}

// ─── Food Data ────────────────────────────────────────────────────────────

data class FoodItem(val name: String, val proteinPerPiece: Double, val unit: String)

val indianFoodDatabase = listOf(
    FoodItem("Appam", 1.0, "piece"),
    FoodItem("Idli", 2.0, "piece"),
    FoodItem("Idiyappam", 1.0, "piece"),
    FoodItem("Dosa", 3.0, "piece"),
    FoodItem("Masala Dosa", 5.0, "piece"),
    FoodItem("Puttu", 3.0, "serving"),
    FoodItem("Pathiri", 2.0, "piece"),
    FoodItem("Unniyappam", 1.0, "piece"),
    FoodItem("Pazham Pori", 1.0, "piece"),
    FoodItem("Kappa (Tapioca)", 2.0, "cup"),
    FoodItem("Porotta", 3.0, "piece"),
    FoodItem("Chapati / Roti", 3.0, "piece"),
    FoodItem("Bread (White)", 2.0, "slice"),
    FoodItem("Bread (Brown)", 3.0, "slice"),
    FoodItem("Bread Bun", 4.0, "piece"),
    FoodItem("Rice", 4.0, "cup"),
    FoodItem("Fried Rice", 6.0, "cup"),
    FoodItem("Chicken Biriyani", 28.0, "plate"),
    FoodItem("Mutton Biriyani", 25.0, "plate"),
    FoodItem("Mandi (Chicken)", 30.0, "plate"),
    FoodItem("Mandi (Mutton)", 28.0, "plate"),
    FoodItem("Egg", 6.0, "piece"),
    FoodItem("Omelette", 12.0, "piece"),
    FoodItem("Egg Curry", 6.0, "piece"),
    FoodItem("Boiled Egg", 6.0, "piece"),
    FoodItem("Fried Egg", 6.0, "piece"),
    FoodItem("Chicken Curry", 25.0, "100g"),
    FoodItem("Chicken Fry", 28.0, "100g"),
    FoodItem("Butter Chicken", 22.0, "cup"),
    FoodItem("Kadai Chicken", 24.0, "cup"),
    FoodItem("Chicken Shawarma", 20.0, "piece"),
    FoodItem("Beef Curry", 22.0, "100g"),
    FoodItem("Beef Fry", 25.0, "100g"),
    FoodItem("Mutton Curry", 20.0, "100g"),
    FoodItem("Fish Curry", 20.0, "100g"),
    FoodItem("Fish Fry", 22.0, "100g"),
    FoodItem("Prawn Curry", 18.0, "100g"),
    FoodItem("Dal", 9.0, "cup"),
    FoodItem("Sambar", 5.0, "cup"),
    FoodItem("Rasam", 2.0, "cup"),
    FoodItem("Chana Masala", 12.0, "cup"),
    FoodItem("Palak Paneer", 10.0, "cup"),
    FoodItem("Mixed Veg Curry", 4.0, "cup"),
    FoodItem("Rajma", 15.0, "cup"),
    FoodItem("Paneer", 18.0, "100g"),
    FoodItem("Curd / Yogurt", 4.0, "100g"),
    FoodItem("Butter", 0.0, "tbsp"),
    FoodItem("Cheese Slice", 4.0, "piece"),
    FoodItem("Ghee", 0.0, "tbsp"),
    FoodItem("Samosa", 4.0, "piece"),
    FoodItem("Vada", 3.0, "piece"),
    FoodItem("Pani Puri", 1.0, "piece"),
    FoodItem("Vada Pav", 5.0, "piece"),
    FoodItem("Egg Puff", 8.0, "piece"),
    FoodItem("Chicken Puff", 10.0, "piece"),
    FoodItem("Veg Puff", 4.0, "piece"),
    FoodItem("Cake Slice", 4.0, "slice"),
    FoodItem("Biscuits", 2.0, "piece"),
    FoodItem("Banana Bread", 3.0, "slice"),
    FoodItem("Jalebi", 1.0, "piece"),
    FoodItem("Laddu", 3.0, "piece"),
    FoodItem("Halwa", 2.0, "serving"),
    FoodItem("Kheer", 5.0, "cup"),
    FoodItem("Gulab Jamun", 2.0, "piece"),
    FoodItem("Rasgulla", 2.0, "piece"),
    FoodItem("Banana", 1.0, "piece"),
    FoodItem("Apple", 1.0, "piece"),
    FoodItem("Mango", 2.0, "cup"),
    FoodItem("Watermelon", 1.0, "cup"),
    FoodItem("Grapes", 1.0, "cup"),
    FoodItem("Orange", 1.0, "piece"),
    FoodItem("Papaya", 1.0, "cup"),
    FoodItem("Pineapple", 1.0, "cup"),
    FoodItem("Pomegranate", 2.0, "cup"),
    FoodItem("Guava", 2.0, "piece"),
    FoodItem("Pear", 1.0, "piece"),
    FoodItem("Strawberry", 1.0, "cup"),
    FoodItem("Kiwi", 1.0, "piece"),
    FoodItem("Coconut", 2.0, "piece"),
    FoodItem("Jackfruit", 2.0, "cup"),
    FoodItem("Sapota (Chikoo)", 1.0, "piece"),
    FoodItem("Lychee", 1.0, "cup"),
    FoodItem("Fig", 1.0, "piece"),
    FoodItem("Dates", 2.0, "piece"),
    FoodItem("Spinach", 3.0, "cup"),
    FoodItem("Carrot", 1.0, "piece"),
    FoodItem("Beetroot", 2.0, "piece"),
    FoodItem("Potato", 2.0, "piece"),
    FoodItem("Broccoli", 4.0, "cup"),
    FoodItem("Green Peas", 5.0, "cup"),
    FoodItem("Cucumber", 1.0, "piece"),
    FoodItem("Tomato", 1.0, "piece"),
    FoodItem("Onion", 1.0, "piece"),
    FoodItem("Cabbage", 2.0, "cup"),
    FoodItem("Cauliflower", 3.0, "cup"),
    FoodItem("Beans", 2.0, "cup"),
    FoodItem("Drumstick (Moringa)", 2.0, "piece"),
    FoodItem("Bitter Gourd", 1.0, "piece"),
    FoodItem("Bottle Gourd", 1.0, "cup"),
    FoodItem("Pumpkin", 1.0, "cup"),
    FoodItem("Corn", 3.0, "piece"),
    FoodItem("Mushroom", 3.0, "cup"),
    FoodItem("Sweet Potato", 2.0, "piece"),
    FoodItem("Ash Gourd", 1.0, "cup"),
    FoodItem("Raw Banana", 2.0, "piece"),
    FoodItem("Yam", 2.0, "cup"),
    FoodItem("Milk", 8.0, "glass"),
    FoodItem("Chocolate Milk", 8.0, "glass"),
    FoodItem("Soy Milk", 7.0, "glass"),
    FoodItem("Almond Milk", 2.0, "glass"),
    FoodItem("Hot Coffee with Milk", 3.0, "cup"),
    FoodItem("Black Coffee", 0.0, "cup"),
    FoodItem("Hot Tea with Milk", 2.0, "cup"),
    FoodItem("Black Tea", 0.0, "cup"),
    FoodItem("Latte", 5.0, "cup"),
    FoodItem("Cappuccino", 4.0, "cup"),
    FoodItem("Green Tea", 0.0, "cup"),
    FoodItem("Masala Chai", 3.0, "cup"),
    FoodItem("Sharjah Shake", 6.0, "glass"),
    FoodItem("Pista Shake", 8.0, "glass"),
    FoodItem("Badam Shake", 7.0, "glass"),
    FoodItem("Banana Shake", 7.0, "glass"),
    FoodItem("Mango Shake", 5.0, "glass"),
    FoodItem("Chocolate Shake", 8.0, "glass"),
    FoodItem("Strawberry Shake", 5.0, "glass"),
    FoodItem("Cold Coffee", 5.0, "glass"),
    FoodItem("Vanilla Shake", 6.0, "glass"),
    FoodItem("Mixed Fruit Shake", 4.0, "glass"),
    FoodItem("Papaya Shake", 4.0, "glass"),
    FoodItem("Chikoo Shake", 4.0, "glass"),
    FoodItem("Avocado Shake", 5.0, "glass"),
    FoodItem("Dates Shake", 5.0, "glass"),
    FoodItem("Rose Milk", 4.0, "glass"),
    FoodItem("Lassi", 6.0, "glass"),
    FoodItem("Sweet Lassi", 5.0, "glass"),
    FoodItem("Mango Lassi", 5.0, "glass"),
    FoodItem("Buttermilk", 3.0, "glass"),
    FoodItem("Coconut Water", 2.0, "glass"),
    FoodItem("Protein Shake", 25.0, "glass"),
    FoodItem("Peanuts", 7.0, "handful"),
    FoodItem("Almonds", 6.0, "handful"),
    FoodItem("Cashews", 5.0, "handful"),
    FoodItem("Pistachios", 6.0, "handful"),
    FoodItem("Walnuts", 4.0, "handful"),
    FoodItem("Raisins", 1.0, "handful"),
    FoodItem("Chia Seeds", 3.0, "tbsp"),
    FoodItem("Flax Seeds", 2.0, "tbsp"),
    FoodItem("Sunflower Seeds", 3.0, "tbsp"),
    FoodItem("Pumpkin Seeds", 4.0, "tbsp"),
    FoodItem("Hemp Seeds", 5.0, "tbsp"),
    FoodItem("Sesame Seeds", 2.0, "tbsp"),
    FoodItem("Fox Nuts (Makhana)", 3.0, "handful"),
    FoodItem("Mixed Nuts", 5.0, "handful"),
    FoodItem("Whey Protein", 25.0, "scoop"),
    FoodItem("Whey Isolate", 27.0, "scoop"),
    FoodItem("Casein Protein", 24.0, "scoop"),
    FoodItem("Plant Protein", 20.0, "scoop"),
    FoodItem("Mass Gainer", 30.0, "scoop"),
    FoodItem("Protein Bar", 20.0, "piece"),
    FoodItem("Peanut Butter", 4.0, "tbsp"),
    FoodItem("BCAA", 0.0, "scoop"),
    FoodItem("Creatine", 0.0, "scoop"),
    FoodItem("Pre-Workout", 0.0, "scoop"),
    FoodItem("Multivitamin", 0.0, "piece"),
    FoodItem("Fish Oil", 0.0, "piece"),
    FoodItem("Vitamin D", 0.0, "piece"),
    FoodItem("Zinc", 0.0, "piece")
)

// ─── Main ──────────────────────────────────────────────────────────────────

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = AppDatabase.getDatabase(this)
        userPrefs = UserPreferences(this)
        setContent { ProfyApp(database, userPrefs) }
    }
}

// ─── App Navigator ─────────────────────────────────────────────────────────

@Composable
fun ProfyApp(database: AppDatabase, userPrefs: UserPreferences) {
    var showOnboarding by remember { mutableStateOf(userPrefs.isFirstTime()) }
    var showAddFoodScreen by remember { mutableStateOf(false) }
    val today = LocalDate.now().toString()
    val dao = database.foodDao()
    val customFoodDao = database.customFoodDao()
    val foodEntries by dao.getFoodsByDate(today).collectAsState(initial = emptyList())
    val customFoods by customFoodDao.getAllCustomFoods().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    when {
        showOnboarding -> {
            OnboardingScreen(
                onComplete = { name, goal ->
                    userPrefs.saveUserData(name, goal)
                    showOnboarding = false
                }
            )
        }
        showAddFoodScreen -> {
            AddFoodScreen(
                customFoods = customFoods,
                onBack = { showAddFoodScreen = false },
                onFoodAdded = { name, protein, unit, quantity ->
                    scope.launch {
                        dao.insertFood(
                            FoodEntry(
                                name = name,
                                proteinPerPiece = protein,
                                unit = unit,
                                quantity = quantity,
                                date = today
                            )
                        )
                    }
                    showAddFoodScreen = false
                },
                onCustomFoodSaved = { name, protein, unit ->
                    scope.launch {
                        customFoodDao.insertCustomFood(
                            CustomFoodEntry(name = name, proteinPerUnit = protein, unit = unit)
                        )
                    }
                },
                onDeleteCustomFood = { customFood ->
                    scope.launch { customFoodDao.deleteCustomFood(customFood) }
                }
            )
        }
        else -> {
            HomeScreen(
                userName = userPrefs.getUserName(),
                dailyGoal = userPrefs.getDailyGoal(),
                foodEntries = foodEntries,
                onAddFoodClick = { showAddFoodScreen = true },
                onDeleteFood = { food ->
                    scope.launch { dao.deleteFood(food) }
                }
            )
        }
    }
}

// ─── Onboarding Screen ───────────────────────────────────────────────────────

data class ActivityLevel(
    val title: String,
    val description: String,
    val multiplier: Double
)

val activityLevels = listOf(
    ActivityLevel("Not Exercising", "Little or no exercise", 0.8),
    ActivityLevel("Light Exercise", "1-3 workouts per week", 1.2),
    ActivityLevel("Regular Exercise", "3-5 workouts per week", 1.6),
    ActivityLevel("Heavy Training", "Gym or intense training, 5-6 times per week", 2.0)
)

val weightRanges = listOf(
    "Under 50kg" to 45,
    "50-60kg" to 55,
    "60-70kg" to 65,
    "70-80kg" to 75,
    "80-90kg" to 85,
    "Above 90kg" to 95
)

@Composable
fun OnboardingScreen(onComplete: (String, Int) -> Unit) {
    var currentStep by remember { mutableStateOf(1) }
    var name by remember { mutableStateOf("") }
    var exactWeight by remember { mutableStateOf("") }
    var selectedWeightRange by remember { mutableStateOf<String?>(null) }
    var useExactWeight by remember { mutableStateOf(true) }
    var selectedActivity by remember { mutableStateOf<ActivityLevel?>(null) }
    var manualGoal by remember { mutableStateOf("") }
    var useManualGoal by remember { mutableStateOf(false) }

    val finalWeight: Int = if (useExactWeight) {
        exactWeight.toIntOrNull() ?: 65
    } else {
        weightRanges.find { it.first == selectedWeightRange }?.second ?: 65
    }

    val calculatedGoal: Int = if (useManualGoal) {
        manualGoal.toIntOrNull() ?: 120
    } else {
        ((finalWeight * (selectedActivity?.multiplier ?: 1.2)).toInt())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D6A4F))
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            if (index < currentStep) Color(0xFF95D5B2) else Color(0xFF1B4332),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when (currentStep) {
                1 -> NameStep(name) { name = it }
                2 -> WeightStep(
                    exactWeight = exactWeight,
                    onExactWeightChange = { exactWeight = it },
                    useExactWeight = useExactWeight,
                    onToggleMode = {
                        useExactWeight = it
                        if (it) selectedWeightRange = null else exactWeight = ""
                    },
                    selectedRange = selectedWeightRange,
                    onRangeSelected = { selectedWeightRange = it }
                )
                3 -> ActivityStep(selectedActivity) { selectedActivity = it }
                4 -> ResultStep(
                    name = name,
                    calculatedGoal = calculatedGoal,
                    useManualGoal = useManualGoal,
                    manualGoal = manualGoal,
                    onToggleManual = { useManualGoal = it },
                    onManualGoalChange = { manualGoal = it }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStep > 1) {
                TextButton(onClick = { currentStep-- }) {
                    Text("Back", color = Color(0xFFB7E4C7))
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            val canProceed = when (currentStep) {
                1 -> name.isNotBlank()
                2 -> if (useExactWeight) exactWeight.isNotBlank() else selectedWeightRange != null
                3 -> selectedActivity != null
                4 -> true
                else -> false
            }

            Button(
                onClick = {
                    if (currentStep < 4) {
                        currentStep++
                    } else {
                        onComplete(name, calculatedGoal)
                    }
                },
                enabled = canProceed,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF95D5B2)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    if (currentStep < 4) "Next" else "Let's go 🚀",
                    color = Color(0xFF1B4332),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NameStep(name: String, onNameChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Text("PROFY", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("What's your name?", fontSize = 16.sp, color = Color(0xFFB7E4C7))
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = onboardingFieldColors()
        )
    }
}

@Composable
fun WeightStep(
    exactWeight: String,
    onExactWeightChange: (String) -> Unit,
    useExactWeight: Boolean,
    onToggleMode: (Boolean) -> Unit,
    selectedRange: String?,
    onRangeSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("What's your weight?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Text("This helps us calculate your protein needs", fontSize = 13.sp, color = Color(0xFFB7E4C7))
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onToggleMode(true) },
                colors = CardDefaults.cardColors(
                    containerColor = if (useExactWeight) Color(0xFF95D5B2) else Color(0xFF1B4332)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "I know my weight",
                    modifier = Modifier.padding(14.dp).fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = if (useExactWeight) Color(0xFF1B4332) else Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onToggleMode(false) },
                colors = CardDefaults.cardColors(
                    containerColor = if (!useExactWeight) Color(0xFF95D5B2) else Color(0xFF1B4332)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Not sure",
                    modifier = Modifier.padding(14.dp).fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = if (!useExactWeight) Color(0xFF1B4332) else Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Only ONE section shows at a time, and switching clears the other's selection
        if (useExactWeight) {
            OutlinedTextField(
                value = exactWeight,
                onValueChange = onExactWeightChange,
                label = { Text("Weight (kg)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = onboardingFieldColors()
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(weightRanges) { (label, _) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRangeSelected(label) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedRange == label) Color(0xFF95D5B2) else Color(0xFF1B4332)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            label,
                            modifier = Modifier.padding(16.dp),
                            color = if (selectedRange == label) Color(0xFF1B4332) else Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityStep(selected: ActivityLevel?, onSelect: (ActivityLevel) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("How active are you?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))

        activityLevels.forEach { level ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { onSelect(level) },
                colors = CardDefaults.cardColors(
                    containerColor = if (selected == level) Color(0xFF95D5B2) else Color(0xFF1B4332)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        level.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (selected == level) Color(0xFF1B4332) else Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        level.description,
                        fontSize = 13.sp,
                        color = if (selected == level) Color(0xFF1B4332) else Color(0xFFB7E4C7)
                    )
                }
            }
        }
    }
}

@Composable
fun ResultStep(
    name: String,
    calculatedGoal: Int,
    useManualGoal: Boolean,
    manualGoal: String,
    onToggleManual: (Boolean) -> Unit,
    onManualGoalChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Hey $name 👋",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(28.dp))

        if (!useManualGoal) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B4332))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "YOUR DAILY GOAL",
                        fontSize = 12.sp,
                        color = Color(0xFFB7E4C7),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "${calculatedGoal}g",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF95D5B2)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "of protein every day",
                        fontSize = 14.sp,
                        color = Color(0xFFB7E4C7)
                    )
                }
            }
        } else {
            OutlinedTextField(
                value = manualGoal,
                onValueChange = onManualGoalChange,
                label = { Text("Set your own goal (g)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = onboardingFieldColors()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick = { onToggleManual(!useManualGoal) }) {
            Text(
                if (!useManualGoal) "Set my own goal instead" else "Use calculated goal instead",
                color = Color(0xFFB7E4C7),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun onboardingFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color(0xFFB7E4C7),
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color(0xFFB7E4C7),
    cursorColor = Color.White
)

// ─── Home Screen ───────────────────────────────────────────────────────────

@Composable
fun HomeScreen(
    userName: String,
    dailyGoal: Int,
    foodEntries: List<FoodEntry>,
    onAddFoodClick: () -> Unit,
    onDeleteFood: (FoodEntry) -> Unit
) {
    val consumed = foodEntries.sumOf { it.proteinPerPiece * it.quantity }
    val remaining = dailyGoal - consumed
    var foodToDelete by remember { mutableStateOf<FoodEntry?>(null) }

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
                    ProgressStat("${formatProtein(consumed)}g", "Consumed")
                    ProgressStat("${formatProtein(remaining)}g", "Remaining")
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { (consumed.toFloat() / dailyGoal).coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().height(8.dp),
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

        if (foodEntries.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
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
                items(foodEntries) { food ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                                    "${formatProtein(food.proteinPerPiece * food.quantity)}g protein",
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
            modifier = Modifier.fillMaxWidth().height(52.dp),
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
    customFoods: List<CustomFoodEntry>,
    onBack: () -> Unit,
    onFoodAdded: (String, Double, String, Int) -> Unit,
    onCustomFoodSaved: (String, Double, String) -> Unit,
    onDeleteCustomFood: (CustomFoodEntry) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFood by remember { mutableStateOf<FoodItem?>(null) }
    var selectedCustomFood by remember { mutableStateOf<CustomFoodEntry?>(null) }
    var quantity by remember { mutableStateOf(1) }
    var showCustomFoodForm by remember { mutableStateOf(false) }
    var customFoodToDelete by remember { mutableStateOf<CustomFoodEntry?>(null) }

    val filteredFoods = indianFoodDatabase.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    val filteredCustomFoods = customFoods.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    if (showCustomFoodForm) {
        CustomFoodScreen(
            onBack = { showCustomFoodForm = false },
            onFoodAdded = { name, protein, unit, qty ->
                onCustomFoodSaved(name, protein, unit)
                onFoodAdded(name, protein, unit, qty)
            }
        )
        return
    }

    // Permanent delete confirmation for custom foods
    if (customFoodToDelete != null) {
        AlertDialog(
            onDismissRequest = { customFoodToDelete = null },
            title = { Text("Remove from your foods?") },
            text = { Text("This will permanently delete \"${customFoodToDelete!!.name}\" from your custom food list.") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteCustomFood(customFoodToDelete!!)
                    customFoodToDelete = null
                }) {
                    Text("Delete", color = Color(0xFFE63946))
                }
            },
            dismissButton = {
                TextButton(onClick = { customFoodToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search foods...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Can't find your food? Add it manually →",
                fontSize = 13.sp,
                color = Color(0xFF2D6A4F),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { showCustomFoodForm = true }
                    .padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Selected built-in food quantity card
            if (selectedFood != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2D6A4F))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(selectedFood!!.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            "Protein per ${selectedFood!!.unit}: ${formatProtein(selectedFood!!.proteinPerPiece)}g",
                            color = Color(0xFFB7E4C7),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("How many ${selectedFood!!.unit}s?", color = Color.White)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Button(
                                    onClick = { if (quantity > 1) quantity-- },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                ) { Text("-") }
                                Text("$quantity", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
                                Button(
                                    onClick = { quantity++ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                ) { Text("+") }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Total Protein: ${formatProtein(selectedFood!!.proteinPerPiece * quantity)}g",
                            color = Color(0xFF95D5B2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                onFoodAdded(selectedFood!!.name, selectedFood!!.proteinPerPiece, selectedFood!!.unit, quantity)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF95D5B2))
                        ) {
                            Text("Add to my list ✓", color = Color(0xFF1B4332), fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Selected custom food quantity card
            if (selectedCustomFood != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2D6A4F))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(selectedCustomFood!!.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            "Protein per ${selectedCustomFood!!.unit}: ${formatProtein(selectedCustomFood!!.proteinPerUnit)}g",
                            color = Color(0xFFB7E4C7),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("How many ${selectedCustomFood!!.unit}s?", color = Color.White)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Button(
                                    onClick = { if (quantity > 1) quantity-- },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                ) { Text("-") }
                                Text("$quantity", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
                                Button(
                                    onClick = { quantity++ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                ) { Text("+") }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Total Protein: ${formatProtein(selectedCustomFood!!.proteinPerUnit * quantity)}g",
                            color = Color(0xFF95D5B2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                onFoodAdded(selectedCustomFood!!.name, selectedCustomFood!!.proteinPerUnit, selectedCustomFood!!.unit, quantity)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF95D5B2))
                        ) {
                            Text("Add to my list ✓", color = Color(0xFF1B4332), fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Custom foods section (user's own added foods)
                if (filteredCustomFoods.isNotEmpty()) {
                    items(filteredCustomFoods) { food ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedCustomFood = food
                                    selectedFood = null
                                    quantity = 1
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedCustomFood == food) Color(0xFFD8F3DC) else Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(food.name, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            "Custom",
                                            fontSize = 10.sp,
                                            color = Color(0xFF2D6A4F),
                                            modifier = Modifier
                                                .background(Color(0xFFD8F3DC), RoundedCornerShape(6.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text("per ${food.unit}", fontSize = 12.sp, color = Color(0xFFAAAAAA))
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("${formatProtein(food.proteinPerUnit)}g", color = Color(0xFF2D6A4F), fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete permanently",
                                        tint = Color(0xFFE63946),
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { customFoodToDelete = food }
                                    )
                                }
                            }
                        }
                    }
                }

                // Built-in foods section
                items(filteredFoods) { food ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedFood = food
                                selectedCustomFood = null
                                quantity = 1
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedFood == food) Color(0xFFD8F3DC) else Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(food.name, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                                Text("per ${food.unit}", fontSize = 12.sp, color = Color(0xFFAAAAAA))
                            }
                            Text("${formatProtein(food.proteinPerPiece)}g", color = Color(0xFF2D6A4F), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ─── Custom Food Screen ─────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFoodScreen(
    onBack: () -> Unit,
    onFoodAdded: (String, Double, String, Int) -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("piece") }
    var unitDropdownExpanded by remember { mutableStateOf(false) }
    var proteinPerUnit by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(1) }

    val unitOptions = listOf("piece", "cup", "tbsp", "plate", "100g", "glass", "handful", "scoop", "slice", "serving")

    val proteinInt = proteinPerUnit.toDoubleOrNull() ?: 0.0
    val totalProtein = proteinInt * quantity
    val canAdd = foodName.isNotBlank() && proteinPerUnit.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
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
                text = "Add Custom Food",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Food Name",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF555555)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = foodName,
                onValueChange = { foodName = it },
                placeholder = { Text("e.g. Grandma's Special Curry") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Quantity Unit",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF555555)
            )
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = unitDropdownExpanded,
                onExpandedChange = { unitDropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedUnit,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitDropdownExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = unitDropdownExpanded,
                    onDismissRequest = { unitDropdownExpanded = false }
                ) {
                    unitOptions.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedUnit = unit
                                unitDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Protein per $selectedUnit (g)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF555555)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = proteinPerUnit,
                onValueChange = { proteinPerUnit = it },
                placeholder = { Text("e.g. 12") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "💡 Tip: Search \"$foodName protein content\" online if you're not sure",
                fontSize = 11.sp,
                color = Color(0xFFAAAAAA)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2D6A4F))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("How many ${selectedUnit}s?", color = Color.White)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { if (quantity > 1) quantity-- },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
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
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
                                contentPadding = PaddingValues(horizontal = 12.dp)
                            ) { Text("+") }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Total Protein: ${formatProtein(totalProtein)}g",
                        color = Color(0xFF95D5B2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onFoodAdded(foodName, proteinInt, selectedUnit, quantity)
                },
                enabled = canAdd,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D6A4F))
            ) {
                Text("Add to my list ✓", fontWeight = FontWeight.Bold)
            }
        }
    }
}