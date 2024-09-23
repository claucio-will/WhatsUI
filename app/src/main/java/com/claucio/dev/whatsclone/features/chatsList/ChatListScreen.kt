package com.claucio.dev.whatsclone.features.chatsList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claucio.dev.whatsclone.ui.theme.WhatsCloneTheme
import kotlin.random.Random

class ChatsListScreenState(
    val currentUser: User,
    val filters: List<String> = emptyList(),
    val chats: List<Chat> = emptyList(),

    )

class Chat(
    val avatar: String?,
    val name: String,
    val lastMessage: Message,
    val unreadMessages: Int
)

class Message(
    val text: String,
    val date: String,
    val isRead: Boolean,
    val author: User
)

data class User(
    val name: String,
)

@Composable
fun ChatsListScreen(
    modifier: Modifier = Modifier,
    state: ChatsListScreenState,

    ) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //editText and tootip filters
        item {
            Row(
                Modifier
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(10.dp)
            )
            {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Search...")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                state.filters.forEach { filter ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .padding(16.dp, 8.dp)
                    ) {
                        Text(text = filter)
                    }
                }
            }
        }

        //Chats list
        items(state.chats) { chat ->
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier.fillMaxSize()
            ) {

                //avatar icon null
                chat.avatar?.let {
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(Color(
                                Random.nextInt(0, 255),
                                Random.nextInt(0, 255),
                                Random.nextInt(0, 255)
                            ))
                    )
                } ?: Box( // avatar icon not null
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                        .background(Color(
                            Random.nextInt(0, 255),
                            Random.nextInt(0, 255),
                            Random.nextInt(0, 255)
                        ))
                ) {
                    Text(
                        text = chat.name.first().toString(),
                        Modifier.align(Alignment.Center),
                        style = TextStyle.Default.copy(
                            fontSize = 24.sp,)
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    modifier = Modifier.heightIn(55.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = chat.name,
                            style = TextStyle.Default.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = chat.lastMessage.date,
                            style = TextStyle.Default.copy(fontSize = 12.sp)
                        )
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(Modifier.weight(1f)) {
                            if (state.currentUser != chat.lastMessage.author)
                                Icon(Icons.Default.DoneAll, contentDescription = null)
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = chat.lastMessage.text,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                        //Icon number message
                        Box(
                            modifier = Modifier
                                .background(Color.Green, CircleShape)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .size(20.dp)
                        ) {
                            Text(
                                text = "${chat.unreadMessages}",
                                Modifier.align(Alignment.Center),
                                style = TextStyle.Default.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatsListScreenPreview() {
    WhatsCloneTheme {
        Surface {
            ChatsListScreen(
                state = ChatsListScreenState(
                    currentUser = User("Claucio"),
                    listOf("All", "Unread", "Groups"),
                    chats = List(10) {
                        Chat(
                            avatar = if (Random.nextBoolean()) "Avatar" else null,
                            name = LoremIpsum(Random.nextInt(1, 20)).values.first(),
                            lastMessage = Message(
                                text = LoremIpsum(Random.nextInt(1, 20)).values.first(),
                                date = "01/09/2024",
                                isRead = true,
                                author = if (Random.nextBoolean()) User("Claucio") else User("Other")
                            ),
                            unreadMessages = Random.nextInt(1, 20)
                        )
                    }
                ),


                )
        }
    }

}

