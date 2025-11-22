import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun ItemTextField(
    placeHolder: String? = null,
    title: String,
    value: String,
    onChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        Box(
            modifier = Modifier.width(80.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleHeader2
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Column(modifier = Modifier.weight(1f))
        {
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { onChanged(it.trimStart()) },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = placeHolder ?: "",
                                style = MaterialTheme.typography.titleHeader2.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                    innerTextField()
                }

            )
            Spacer(modifier = Modifier.height(2.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
        }

    }
}
