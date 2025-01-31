package burak.android.mywishlist


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import burak.android.mywishlist.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, viewModel: WishViewModel){
    Scaffold(
        topBar = { AppBarView(title = "WishList")
        },
        containerColor = colorResource(id = R.color.background_color),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = {navController.navigate(Screen.AddScreen.route + "/0L")},
                ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }

        }

    ) {
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {

            items(wishlist.value, key = {wish -> wish.id}){
                wish ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart){
                            viewModel.deleteWish(wish)
                        }
                        true
                    },
                    positionalThreshold = { distance: Float -> distance * 0.5f }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) Color.Red else Color.Transparent
                        )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp), contentAlignment = alignment
                        ){
                            Icon(Icons.Default.Delete, contentDescription = "Delete Icon", tint = Color.White)
                        }
                    },
                    enableDismissFromStartToEnd = false,
                    enableDismissFromEndToStart = true
                ) {
                    WishItem(wish= wish) {
                        val id = wish.id
                        navController.navigate(Screen.AddScreen.route + "/$id")
                    }
                }

            }

        }

    }
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit){
    Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), colors = CardDefaults.cardColors(containerColor = Color.White) )
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Text(text = wish.description, color = Color.Black)
        }

    }

}