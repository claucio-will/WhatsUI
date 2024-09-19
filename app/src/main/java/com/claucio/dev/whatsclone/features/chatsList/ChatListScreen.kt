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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.claucio.dev.whatsclone.ui.theme.WhatsCloneTheme

@Composable
fun ChatsListScreen(modifier: Modifier = Modifier) {
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
                val filters = remember {
                    mutableStateListOf("All", "Unread", "Groups")
                }
                filters.forEach { filter ->
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
        items(10) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier.fillMaxSize()
            ) {
                //avatar icon
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    modifier = Modifier.heightIn(55.dp),
                    verticalArrangement = Arrangement.Center) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Chat name")
                        Text(text = "last message date")
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(Modifier.weight(1f)) {
                            Icon(Icons.Default.DoneAll, contentDescription = null)
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "last message")
                        }
                        //Icon number message
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Green)
                                .padding(4.dp)
                        ) {
                            Text(text = "99")
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
            ChatsListScreen()
        }
    }

}