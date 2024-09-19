package com.claucio.dev.whatsclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claucio.dev.whatsclone.ui.theme.WhatsCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatsCloneTheme {
                App()
            }
        }
    }
}

class BottomAppBarItem(
    val icon: ImageVector,
    val label: String,
)

class TopAppBarItem(
    val icons: List<ImageVector> = emptyList(),
    val title: String,
)

//sealed class é usada para ser apenas extendida.
//data object são classes que herdam de NavItem
sealed class ScreenItem(
    val topAppBarItem: TopAppBarItem,
    val bottomAppBarItem: BottomAppBarItem

) {
    data object Chats : ScreenItem(
        topAppBarItem = TopAppBarItem(
            icons = listOf(
                Icons.Default.CameraAlt,
                Icons.Default.MoreVert
            ),
            title = "Whats"),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.AutoMirrored.Filled.Message,
            label = "Chats"
        )
    )

    data object Update :
        ScreenItem(
            topAppBarItem = TopAppBarItem(
                icons = listOf(
                    Icons.Default.CameraAlt,
                    Icons.Default.Search,
                    Icons.Default.MoreVert
                ),
                title = "Update"
            ),
            bottomAppBarItem = BottomAppBarItem(
                icon = Icons.Default.CircleNotifications,
                label = "Update"
            )
        )

    data object Communities :
        ScreenItem(
            topAppBarItem = TopAppBarItem(
                icons = listOf(
                    Icons.Default.CameraAlt,
                    Icons.Default.Search,

                    ),
                title = "Communities"
            ),
            bottomAppBarItem = BottomAppBarItem(
                icon = Icons.Default.People,
                label = "Communities"
            )
        )

    data object Call : ScreenItem(
        topAppBarItem = TopAppBarItem(
            icons = listOf(
                Icons.Default.CameraAlt,
                Icons.Default.Search,
                Icons.Default.MoreVert
            ),
            title = "Call"
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.Call,
            label = "Call"
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun App() {
    //Icons BottomBar
    val screens = remember {
        listOf(
            ScreenItem.Chats,
            ScreenItem.Update,
            ScreenItem.Communities,
            ScreenItem.Call,
        )
    }

    //Item que já vai iniciar marcado
    var currentScreen by remember {
        mutableStateOf(screens.first())
    }
    //Cria a quantidade de paginas
    val pagerState = rememberPagerState {
        screens.size
    }
    //Fazendo a navegação usando o bottombar
    LaunchedEffect(currentScreen) {
        pagerState.animateScrollToPage(screens.indexOf(currentScreen))
    }
    //Sincronizando o scroll com a bottombar
    LaunchedEffect(pagerState.targetPage) {
        currentScreen = screens[pagerState.targetPage]
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = currentScreen.topAppBarItem.title,
                    style = TextStyle.Default.copy(fontWeight = FontWeight.Bold, fontSize = 32.sp)
                )
            }, actions = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    currentScreen.topAppBarItem.icons.forEach { icon ->
                        Icon(icon, contentDescription = null)
                    }

                }
            })
        },
        bottomBar = {
            BottomAppBar {
                screens.forEach { screen ->
                    //Usando with já pegamos os valores que tem dentro dele
                    //como por exemplo, icon e label
                    with(screen.bottomAppBarItem) {
                        NavigationBarItem(
                            selected = screen == currentScreen,
                            onClick = { currentScreen = screen },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = label) })
                    }
                }
            }
        }
    ) { innerPadding ->
        //Criando a paginação
        HorizontalPager(state = pagerState, modifier = Modifier.padding(innerPadding)) { page ->
            //Pegando cada pagina
            val item = screens[page]
            when (item) {
                ScreenItem.Call -> CallScreen()
                ScreenItem.Chats -> ChatsListScreen()
                ScreenItem.Communities -> CommunitiesScreen()
                ScreenItem.Update -> UpdateScreen()
            }
        }
    }
}

//Screen ------------------------------------------------------------------
@Composable
fun ChatsListScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Chats List", style = TextStyle.Default.copy(
                fontSize = 32.sp, color = Color.White
            )
        )
    }
}

@Composable
fun UpdateScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Update", style = TextStyle.Default.copy(
                fontSize = 32.sp, color = Color.White
            )
        )
    }
}

@Composable
fun CommunitiesScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Communities", style = TextStyle.Default.copy(
                fontSize = 32.sp, color = Color.White
            )
        )
    }
}

@Composable
fun CallScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Call", style = TextStyle.Default.copy(
                fontSize = 32.sp, color = Color.White
            )
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    WhatsCloneTheme {
        App()
    }
}
