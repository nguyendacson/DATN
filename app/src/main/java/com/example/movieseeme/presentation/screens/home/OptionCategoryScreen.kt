import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieseeme.domain.model.enum.MovieHomeOption
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.HomeViewModel

@Composable
fun OptionCategory(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    LaunchedEffect(true) {
        homeViewModel.getAllCategory()
    }
    val categories by homeViewModel.listCategory.collectAsState()

    var selectedItem by remember {
        mutableStateOf<String?>(null)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(categories) { category ->
                    val isChecked = selectedItem == category.slug
                    ItemCheck(
                        text = category.name,
                        isChecked = isChecked,
                        onCheckedChange = {
                            selectedItem = if (isChecked) null else category.slug
                            selectedItem?.let {
                                homeViewModel.onCategoryChange(category.slug)
                                homeViewModel.onOptionHomeChange(MovieHomeOption.THEM)
                                homeViewModel.onTypeChange("series")
                            }
                        }
                    )
                }
            }
            Close(onClick = {
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun ItemCheck(
    text: String,
    isChecked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
            .clickable { onCheckedChange() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleHeader2
        )
        if (isChecked) Icon(
            imageVector = Icons.Default.Check,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(20.dp),
            contentDescription = "chose check item"
        )

    }
}

@Composable
fun Close(onClick: () -> Unit) {
    val colorOnBackground = MaterialTheme.colorScheme.onBackground
    val colorBackground = MaterialTheme.colorScheme.background

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(colorOnBackground, CircleShape)
            .border(1.dp, colorOnBackground, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Close",
            tint = colorBackground,
            modifier = Modifier.size(24.dp)
        )
    }
}