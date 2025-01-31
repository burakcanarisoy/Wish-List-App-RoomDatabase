package burak.android.mywishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import burak.android.mywishlist.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    if(id != 0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState)},
        topBar = {AppBarView(title = if(id != 0L) "Update Wish" else "Add Wish" )
        {navController.navigateUp()} //Goes back the screen where we came from.[To HomeView]
        },
        containerColor = colorResource(id = R.color.background_color)
    ) {
        Column(modifier = Modifier.padding(it).wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = viewModel.wishTitleState, onValueChanged = {viewModel.onWishTitleChanged(it)})
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Description", value = viewModel.wishDescriptionState, onValueChanged = {viewModel.OnWishDescriptionChanged(it)})
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()) {
                        if (id != 0L) {
                            // Update Wish
                            viewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                        } else {
                            // Add Wish
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            scope.launch {
                                snackBarHostState.showSnackbar("Wish has been created")
                            }
                        }
                        scope.launch {
                            navController.navigateUp()
                        }
                    } else {
                        scope.launch {
                            snackBarHostState.showSnackbar("Enter fields to create a wish")
                        }
                    }


                },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonColors(containerColor = colorResource(id = R.color.app_bar_color), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.White)
            ) {
                Text(
                    text = if (id != 0L) "Update Wish" else "Add Wish",
                    style = TextStyle(fontSize = 18.sp)
                )
            }




        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(
        value = value, onValueChange = onValueChanged,
        label = {Text(text = label, color = Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), //There are another keyboard options, just to learn
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black, // Kullanıcı yazı yazarken siyah renk
            unfocusedTextColor = Color.Black, // Alan seçili değilken de siyah renk
            focusedContainerColor = Color.Transparent, // Arka plan rengi (şeffaf)
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black, // İmleç rengi siyah
            focusedIndicatorColor = Color.Black, // Çerçeve rengi (seçili)
            unfocusedIndicatorColor = Color.Gray, // Çerçeve rengi (seçili değil)
            disabledIndicatorColor = Color.Gray
        )
        )



}

@Preview(showBackground = true)
@Composable
fun WishTextFieldPrev(){
    WishTextField(label = "text", value = "text",  {})
}