import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun CustomToast(
    value: String,
    duration: Long = 2000L,
    onDismiss: (() -> Unit)? = null
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(value) { // chỉ trigger khi value mới
        visible = true
        kotlinx.coroutines.delay(duration)
        visible = false
        onDismiss?.invoke()
    }

    Box(
        modifier = Modifier
            .padding(bottom = 30.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(35.dp)
                    .padding(horizontal = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleHeader2.copy(
                        color = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewCustomToast() {
    CustomToast(
        value = "Đã thêm vào danh sách yêu thích",
        duration = 2000L
    )
}