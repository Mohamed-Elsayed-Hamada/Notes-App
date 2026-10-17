package com.example.simplenotesapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.repository.NoteRepositoryImp
import com.example.simplenotesapp.ui.theme.BannerCircleBg
import com.example.simplenotesapp.ui.theme.SurfaceLight
import com.example.simplenotesapp.ui.theme.TextSecondary
import com.example.simplenotesapp.view.components.AppIcons
import kotlinx.coroutines.launch

// Figma Colors from Node 1:258
private val ScreenBg = Color(0xFFFBF8FF)
private val BrandBlue = Color(0xFF3525CD)
private val TextMain = Color(0xFF1A1B22)
private val TextMuted = Color(0xFF464555)
private val SubHeaderBtnBg = Color(0xFFF4F2FD)
private val SubHeaderSavedBg = Color(0xFFEEEDF7)
private val BadgeFolderBg = Color(0xFFE8E7F1)
private val BadgePinnedBg = Color(0xFFFFDCC3)
private val BadgePinnedText = Color(0xFF6E3900)
private val DividerColor = Color(0xFFE8E7F1)
private val AiButtonBg = Color(0xFFE2DFFF)
private val AiButtonIcon = Color(0xFF0F0069)
private val CheckboxUncheckedBg = Color(0xFFE3E1EC)
private val FooterBg = Color(0xFFF4F2FD)
private val DotSeparatorColor = Color(0xFFC7C4D8)

/**
 * NoteDetailScreen implementing Figma node 1:258 with pixel-perfect
 * colors, typography, layout dimensions, and assets.
 */
@Composable
fun NoteDetailScreen(
    noteId: String,
    onNavigateBack: () -> Unit = {}
) {
    var noteState by remember { mutableStateOf<Note>(Note("", "")) }
//    var title by remember { mutableStateOf("") }
//    var isPinned by remember { mutableStateOf(false) }
//    var body by remember { mutableStateOf("") }

//    val checklistItems = remember {
//        mutableStateListOf<String>(
////            "Audit existing mobile typography scales" to true,
////            "Refine pill toolbar haptic micro-interactions" to false,
////            "Publish tactile guidelines to team workspace" to false
//        )
//    }
    var scope = rememberCoroutineScope  ()
    var repository = NoteRepositoryImp()
    LaunchedEffect(key1 = null) {
        var note: Note? = repository.getNoteById(noteId)
        if (note != null) {
            noteState = note
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ScreenBg
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
        ) {
            // 1. Subtle Sub-Header Action Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Saved pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(SubHeaderSavedBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(BrandBlue)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Saved 2m ago",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextMuted,
                        letterSpacing = 0.02.sp
                    )
                }

                // Right: Action Buttons (Undo, Redo, Delete, Save)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Undo
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SubHeaderBtnBg)
                            .clickable { /* Undo action */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.Undo,
                            contentDescription = "Undo",
                            tint = TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                    }

                    // Redo
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SubHeaderBtnBg)
                            .clickable { /* Redo action */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.Redo,
                            contentDescription = "Redo",
                            tint = TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                    }

                    // Delete Note
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SubHeaderBtnBg)
                            .clickable { onNavigateBack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.DeleteNote,
                            contentDescription = "Delete",
                            tint = TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                    }

                    // Save Button
                    Row(
                        modifier = Modifier
                            .height(36.dp)
                            .clip(RoundedCornerShape(9999.dp))
                            .background(BrandBlue)
                            .clickable {
                               scope.launch {
                                   repository.updateNote(noteState)
                               }
//                                onNavigateBack()

                            }
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.SaveCheck,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(11.dp)
                        )
                        Text(
                            text = "Save",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // 2. Pinned / Folder Meta Badge Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Folder / Category Badge
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BadgeFolderBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.BadgeFolder,
                        contentDescription = null,
                        tint = BrandBlue,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "Personal",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextMain
                    )
                }

                // Pinned Badge
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BadgePinnedBg)
                        .clickable { noteState.copy(isPinned = !noteState.isPinned) }
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.BadgePin,
                        contentDescription = null,
                        tint = BadgePinnedText,
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        text = if (noteState.isPinned) "Pinned" else "Pin",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = BadgePinnedText
                    )
                }
            }

            // 3. Sticky Mobile Formatting Accessory Bar
            Column(
                Modifier
                    .clip( shape = RoundedCornerShape(10.dp))

                    .background(SurfaceLight)
                    .padding(5.dp).fillMaxSize().weight(1f)
            ) {
                Text("Created just now ", style = TextStyle(color = TextSecondary))
                Spacer(Modifier.height(10.dp))
                BasicTextField(
                    value = noteState.title,
                    onValueChange = { noteState = noteState.copy(title = it) },
                    textStyle = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMain,
                        lineHeight = 32.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(BannerCircleBg)


                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(BannerCircleBg)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // B
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { /* Bold */ }
                                .padding(horizontal = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "B",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMain
                            )
                        }

                        // I
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { /* Italic */ }
                                .padding(horizontal = 17.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "I",
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Medium,
                                color = TextMain
                            )
                        }

                        // H1
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { /* Heading 1 */ }
                                .padding(horizontal = 11.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "H1",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextMain
                            )
                        }

                        // Divider
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(20.dp)
                                .background(DividerColor)
                        )

                        // Format List
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { /* List */ }
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AppIcons.FormatList,
                                contentDescription = "Bullet List",
                                tint = TextMain,
                                modifier = Modifier.size(15.dp)
                            )
                        }

                        // Format Check
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { /* Checklist */ }
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AppIcons.FormatCheck,
                                contentDescription = "Checklist",
                                tint = TextMain,
                                modifier = Modifier.size(15.dp)
                            )
                        }

                        // Format Quote
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { /* Quote */ }
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AppIcons.FormatQuote,
                                contentDescription = "Quote",
                                tint = TextMain,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        // Divider
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(20.dp)
                                .background(DividerColor)
                        )

                        // AI / Sparkle Button
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(AiButtonBg)
                                .clickable { /* AI Assist */ }
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AppIcons.SparkleAi,
                                contentDescription = "AI",
                                tint = AiButtonIcon,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
                BasicTextField(
                    value = noteState.body,
                    onValueChange = { noteState = noteState.copy(body = it) },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextMain,
                        lineHeight = 25.sp
                    ),
                    modifier = Modifier.fillMaxSize()
                )


            }

//            Spacer(Modifier.height(14.dp))

            // 4. Note Writing Surface
            // Title Input
//            BasicTextField(
//                value = noteState.title,
//                onValueChange = { noteState = noteState.copy(title = it) },
//                textStyle = TextStyle(
//                    fontSize = 26.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = TextMain,
//                    lineHeight = 32.sp
//                ),
//                modifier = Modifier.fillMaxWidth()
//            )

            Spacer(Modifier.height(8.dp))

            // Metadata Snippet


            Spacer(Modifier.height(16.dp))

            // Body Paragraphs Editor
//                    Spacer(Modifier.height(4.dp))
//                    Text(
//                        text = "Every element must serve readability. Avoid high-saturation decorative items that distract from reading and writing.",
//                        fontSize = 17.sp,
//                        fontWeight = FontWeight.Normal,
//                        color = TextMain,
//                        lineHeight = 27.6.sp
//                    )
//                }
//
//                // Section 2
//                Column {
//                    Text(
//                        text = "2. Consistent Spacing & Rhythms",
//                        fontSize = 17.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        color = TextMain,
//                        lineHeight = 22.sp
//                    )
//                    Spacer(Modifier.height(4.dp))
//                    Text(
//                        text = "Use 8pt grid increments for vertical cadence. Headings should feel anchored to their preceding paragraphs with deliberate whitespace.",
//                        fontSize = 17.sp,
//                        fontWeight = FontWeight.Normal,
//                        color = TextMain,
//                        lineHeight = 27.6.sp
//                    )
//                }
//
//                // Section 3
//                Column {
//                    Text(
//                        text = "3. Accessible Contrast",
//                        fontSize = 17.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        color = TextMain,
//                        lineHeight = 22.sp
//                    )
//                    Spacer(Modifier.height(4.dp))
//                    Text(
//                        text = "Ensure minimum 4.5:1 ratio for body copy against light backgrounds.",
//                        fontSize = 17.sp,
//                        fontWeight = FontWeight.Normal,
//                        color = TextMain,
//                        lineHeight = 27.6.sp
//                    )
//                }
//            }

            Spacer(Modifier.height(18.dp))

            // Interactive Checklists
//            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                checklistItems.forEachIndexed { index, (text, isChecked) ->
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                checklistItems[index] = text to !isChecked
//                            },
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(10.dp)
//                    ) {
//                        // Checkbox
//                        Box(
//                            modifier = Modifier
//                                .size(20.dp)
//                                .clip(RoundedCornerShape(4.dp))
//                                .background(if (isChecked) BrandBlue else CheckboxUncheckedBg),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            if (isChecked) {
//                                Icon(
//                                    imageVector = AppIcons.CheckMark,
//                                    contentDescription = null,
//                                    tint = Color.White,
//                                    modifier = Modifier.size(10.dp)
//                                )
//                            }
//                        }
//
//                        Text(
//                            text = text,
//                            fontSize = 15.sp,
//                            fontWeight = FontWeight.Normal,
//                            color = TextMain,
//                            lineHeight = 22.sp,
//                            textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
//                        )
//                    }
//                }
//            }

            Spacer(Modifier.height(32.dp))

            // 5. Interactive Bottom Note Telemetry Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(FooterBg)
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left metrics: 74 words · 559 characters
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Words,
                            contentDescription = null,
                            tint = BrandBlue,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "74 words",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextMuted
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(DotSeparatorColor)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Characters,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "559 characters",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextMuted
                        )
                    }
                }

                // Right actions: Share & More
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { /* Share note */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.Share,
                            contentDescription = "Share",
                            tint = TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { /* More options */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.MoreHoriz,
                            contentDescription = "More",
                            tint = TextMuted,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

