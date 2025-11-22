import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieseeme.presentation.theme.extension.lineNav
import com.example.movieseeme.presentation.theme.extension.shadow

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val bottomNavItems = listOf(
    BottomNavItem("home_main", Icons.Default.Home, "Trang chủ"),
    BottomNavItem("new_hot", Icons.Default.Whatshot, "Thịnh hành"),
    BottomNavItem("profile", Icons.Default.Person, "Bạn")
)

@Composable
fun NavBottom(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val colorLine = MaterialTheme.colorScheme.shadow
    val colorIcon = MaterialTheme.colorScheme.lineNav

    Column {
        HorizontalDivider(
            color = colorLine,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        NavigationBar(
            modifier = Modifier.height(63.dp)
        ) {
            bottomNavItems.forEach { item ->
                val isSelected = currentRoute == item.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { navController.navigate(item.route) },
                    icon = {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(top = 5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(25.dp),
                                tint = if (isSelected) colorIcon else Color.Gray
                            )
                            Text(
                                text = item.label,
                                color = if (isSelected) colorIcon else Color.Gray,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorIcon,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = colorIcon,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
