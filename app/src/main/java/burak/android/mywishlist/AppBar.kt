package burak.android.mywishlist

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(title: String, navController: NavController, onBackNavClicked:() -> Unit = {}) {

    val navigationIcon : (@Composable () -> Unit) = {
        if(!title.contains("My Wish List")){
            IconButton(onClick = {onBackNavClicked()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }else{
            null
        }
    }
    TopAppBar(title =
    { Text(text = title, color = colorResource(id = R.color.white),
        modifier = Modifier.padding(4.dp).heightIn(max = 24.dp)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = colorResource(id = R.color.app_bar_color)),
        navigationIcon = navigationIcon,
        actions = {
            if(title.contains("My Wish List")){
                IconButton(
                    onClick = {navController.navigate("archived_screen")},
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_archive_24),
                        contentDescription = null, tint = Color.White)
                }
            }
        }
        )
}
