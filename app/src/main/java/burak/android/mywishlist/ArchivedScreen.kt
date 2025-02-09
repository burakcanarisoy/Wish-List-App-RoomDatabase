package burak.android.mywishlist

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import burak.android.mywishlist.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArchivedScreen(navController: NavController, viewModel: WishViewModel){
    var showDialog by remember { mutableStateOf(false) }
    var wishToDelete by remember { mutableStateOf<Wish?>(null) }
    Scaffold(
        topBar = {AppBarView(title = "Archived Wishes", navController)
        {navController.navigateUp()}},
        containerColor = colorResource(id = R.color.background_color),

    ){
        val archivedWishes = viewModel.getArchivedWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(archivedWishes.value, key = {wish -> wish.id}){
                    wish ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart){
                            wishToDelete = wish
                            showDialog = true
                        }
                        true
                    },
                    positionalThreshold = { distance: Float -> distance * 0.8f }
                )
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) Color.Red else Color.Transparent
                        )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp), contentAlignment = alignment
                        ){
                            Icon(Icons.Default.Delete, contentDescription = "Delete Icon", tint = Color.White)
                        }
                    },
                    enableDismissFromStartToEnd = false,
                    enableDismissFromEndToStart = true
                ) {
                    ArchivedItem(wish= wish)
                }
                if(!showDialog){
                    LaunchedEffect(Unit) {
                        dismissState.reset()
                    }
                }

            }

        }
    }
    if (showDialog){
        AlertDialog(
            onDismissRequest = {showDialog = false},
            title = { Text(text = "Deletion") },
            text = { Text(text = "Are you sure you want to delete this wish?") },
            confirmButton = {
                TextButton(onClick = {
                    wishToDelete?.let { viewModel.deleteWish(it) }
                    showDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {showDialog = false}) {
                    Text("Cancel")
                }
            }
        )
    }

}

@Composable
fun ArchivedItem(wish: Wish){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), colors = CardDefaults.cardColors(containerColor = Color.White) )
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold, color = Color.Black, modifier = Modifier.padding(start = 4.dp, end = 4.dp))
            Text(text = wish.description, color = Color.Black, modifier = Modifier.padding(start = 4.dp, end = 4.dp))
        }

    }

}

