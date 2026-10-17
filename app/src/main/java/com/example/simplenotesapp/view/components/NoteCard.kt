package com.example.simplenotesapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplenotesapp.model.ChecklistItem
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.ui.theme.CardBorder
import com.example.simplenotesapp.ui.theme.Error
import com.example.simplenotesapp.ui.theme.PinnedBadgeBg
import com.example.simplenotesapp.ui.theme.PinnedBadgeText
import com.example.simplenotesapp.ui.theme.Primary
import com.example.simplenotesapp.ui.theme.SurfaceLight
import com.example.simplenotesapp.ui.theme.TagDesignBg
import com.example.simplenotesapp.ui.theme.TagDesignText
import com.example.simplenotesapp.ui.theme.TagLifeBg
import com.example.simplenotesapp.ui.theme.TagLifeText
import com.example.simplenotesapp.ui.theme.TagPersonalBg
import com.example.simplenotesapp.ui.theme.TagPersonalText
import com.example.simplenotesapp.ui.theme.TagReadingBg
import com.example.simplenotesapp.ui.theme.TagReadingText
import com.example.simplenotesapp.ui.theme.TagSyncBg
import com.example.simplenotesapp.ui.theme.TagSyncText
import com.example.simplenotesapp.ui.theme.TagWorkBg
import com.example.simplenotesapp.ui.theme.TagWorkText
import com.example.simplenotesapp.ui.theme.TextMuted
import com.example.simplenotesapp.ui.theme.TextPrimary
import com.example.simplenotesapp.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    isMenuOpen: Boolean = false,
    onMenuToggle: (Boolean) -> Unit = {},
    onPinToggle: () -> Unit = {},
    onDuplicate: () -> Unit = {},
    onDelete: () -> Unit = {},
    onChecklistToggle: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceLight)
            .border(1.dp, CardBorder, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header: Badges & 3-dots Menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    if (note.isPinned) {
                        PinnedBadge()
                    }
                    CategoryTag(category = note.category)
                }

                // 3-dots Menu trigger
                Box {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(if (isMenuOpen) Color(0xFFF1F5F9) else Color.Transparent)
                            .clickable { onMenuToggle(!isMenuOpen) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.MoreVert,
                            contentDescription = "Note actions",
                            tint = if (isMenuOpen) TextPrimary else TextMuted,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Floating action popup matching screenshot
                    DropdownMenu(
                        expanded = isMenuOpen,
                        onDismissRequest = { onMenuToggle(false) },
                        modifier = Modifier
                            .background(SurfaceLight)
                            .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = if (note.isPinned) "Unpin" else "Pin to top",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = AppIcons.Pin,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            onClick = {
                                onPinToggle()
                                onMenuToggle(false)
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Duplicate",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = AppIcons.Copy,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            onClick = {
                                onDuplicate()
                                onMenuToggle(false)
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Delete",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Error
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = AppIcons.Delete,
                                    contentDescription = null,
                                    tint = Error,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            onClick = {
                                onDelete()
                                onMenuToggle(false)
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Title
            Text(
                text = note.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                lineHeight = 20.sp
            )

            // Content: Checklist OR Body snippet
            if (note.checklist.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    note.checklist.forEach { item ->
                        ChecklistItemRow(
                            item = item,
                            onToggle = { onChecklistToggle(item.id) }
                        )
                    }
                }
            } else if (note.body.isNotBlank()) {
                Spacer(Modifier.height(5.dp))
                Text(
                    text = note.body,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 17.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            // Footer: Date and Right indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.dateFormatted,
                    fontSize = 11.sp,
                    color = TextMuted
                )

                // Right Footer Icon / Badge
                when {
                    note.attachmentsCount > 0 -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF5F3FF))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Attachment,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(Modifier.width(3.dp))
                            Text(
                                text = "${note.attachmentsCount} files",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                    }
                    note.isPinned && note.checklist.isNotEmpty() -> {
                        Icon(
                            imageVector = AppIcons.ArrowForward,
                            contentDescription = "Open",
                            tint = Color(0xFFC4B5FD),
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    note.cardIcon == "book" -> {
                        Icon(
                            imageVector = AppIcons.Book,
                            contentDescription = "Book",
                            tint = TextMuted,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    note.cardIcon == "palette" -> {
                        Icon(
                            imageVector = AppIcons.Palette,
                            contentDescription = "Palette",
                            tint = TextMuted,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    note.cardIcon == "chat" -> {
                        Icon(
                            imageVector = AppIcons.Chat,
                            contentDescription = "Chat",
                            tint = TextMuted,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Pixel-accurate custom checklist item row matching the screenshot.
 */
@Composable
fun ChecklistItemRow(
    item: ChecklistItem,
    onToggle: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onToggle
            )
            .padding(vertical = 2.dp)
    ) {
        // Custom Styled Checkbox
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (item.isChecked) Primary else SurfaceLight)
                .border(
                    width = 1.dp,
                    color = if (item.isChecked) Primary else Color(0xFFCBD5E1),
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (item.isChecked) {
                Icon(
                    imageVector = AppIcons.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(11.dp)
                )
            }
        }

        Spacer(Modifier.width(7.dp))

        Text(
            text = item.text,
            fontSize = 12.sp,
            color = if (item.isChecked) TextMuted else TextPrimary,
            textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PinnedBadge() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(PinnedBadgeBg)
            .padding(horizontal = 7.dp, vertical = 2.5.dp)
    ) {
        Icon(
            imageVector = AppIcons.Pin,
            contentDescription = null,
            tint = PinnedBadgeText,
            modifier = Modifier.size(10.dp)
        )
        Spacer(Modifier.width(3.dp))
        Text(
            text = "Pinned",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = PinnedBadgeText
        )
    }
}

@Composable
fun CategoryTag(category: String) {
    val (bg, textColor) = when (category) {
        "Personal" -> TagPersonalBg to TagPersonalText
        "Work • Artemis", "Work" -> TagWorkBg to TagWorkText
        "Reading" -> TagReadingBg to TagReadingText
        "Design" -> TagDesignBg to TagDesignText
        "Life" -> TagLifeBg to TagLifeText
        "Sync" -> TagSyncBg to TagSyncText
        else -> TagPersonalBg to TagPersonalText
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(horizontal = 7.dp, vertical = 2.5.dp)
    ) {
        Text(
            text = category,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}