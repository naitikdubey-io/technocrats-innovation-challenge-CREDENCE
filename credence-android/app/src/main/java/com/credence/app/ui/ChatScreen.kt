package com.credence.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.credence.app.R
import com.credence.app.ui.theme.*
import com.credence.app.viewmodel.MainViewModel

import kotlinx.coroutines.launch

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatScreen(navController: NavHostController, viewModel: MainViewModel) {
    var inputText by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf(ChatMessage("\"I'm here. Take a deep breath. Where is the pain located right now?\"", false)))
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val aiResponse by viewModel.chatResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Auto-scroll to bottom when AI replies
    LaunchedEffect(aiResponse) {
        aiResponse?.let {
            messages = messages + ChatMessage(it.reply, false)
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmIvory)
            .systemBarsPadding()
            .imePadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(SoftCream)
                    .align(Alignment.CenterStart)
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = DeepEspresso
                )
            }

            Image(
                painter = painterResource(id = R.drawable.cree_character),
                contentDescription = "Cree Avatar",
                modifier = Modifier.size(64.dp) // Small enough to not crowd the screen
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(50))
                .background(SoftCream)
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Today, 10:15 AM • Biometrics anchoring active",
                style = MaterialTheme.typography.labelMedium,
                color = SoftMushroom
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f) // Takes up remaining space, shrinking when keyboard opens
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(messages) { msg ->
                ChatBubble(message = msg)
            }
            if (isLoading) {
                item {
                    Text(
                        text = "Cree is typing...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SoftMushroom,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                onClick = {
                    viewModel.completeChatSession()
                    navController.navigate(Screen.Timeline.route) {
                        popUpTo(Screen.Home.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DeepEspresso),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                modifier = Modifier.shadow(8.dp, RoundedCornerShape(50))
            ) {
                Text(
                    text = "End & Synthesize",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                    color = Color.White
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text Input Field
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .border(1.dp, SubtleGray, RoundedCornerShape(50))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (inputText.isEmpty()) {
                    Text(
                        text = "Describe how you're feeling...",
                        style = MaterialTheme.typography.bodyLarge,
                        color =
                            PlaceholderGray
                    )
                }
                BasicTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = DeepEspresso),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Dynamic Mic / Send Button
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(BlushPink)
                    .clickable {
                        if (inputText.isNotBlank()) {
                            // SEND LOGIC
                            messages = messages + ChatMessage(inputText, true)
                            viewModel.sendChatMessage(inputText)
                            inputText = ""

                            // Scroll to bottom after sending
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size)
                            }
                        } else {
                            // TODO: Future Voice Recognition Logic goes here
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = if (inputText.isNotBlank()) painterResource(R.drawable.jet) else painterResource(R.drawable.mic),
                    contentDescription = if (inputText.isNotBlank()) "Send" else "Microphone",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomStart = if (message.isUser) 24.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 24.dp
                    )
                )
                .background(if (message.isUser) Color.White else SoftCream)
                .border(
                    width = if (message.isUser) 1.dp else 0.dp,
                    color = if (message.isUser) SubtleGray else Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = 24.dp, topEnd = 24.dp,
                        bottomStart = if (message.isUser) 24.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 24.dp
                    )
                )
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyLarge,
                color = DeepEspresso
            )
        }
    }
}