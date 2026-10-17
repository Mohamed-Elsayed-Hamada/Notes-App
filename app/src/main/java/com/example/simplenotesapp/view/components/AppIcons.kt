package com.example.simplenotesapp.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.simplenotesapp.R

/**
 * Pixel-precise vector icons for SimpleNotesApp.
 * Exposes type-safe ImageVectors backed by XML vector drawables,
 * ensuring full compatibility with Compose Icon(imageVector = ...)
 */
object AppIcons {

    // --- Standard Material Fallbacks ---
    val MoreVertStandard: ImageVector = Icons.Default.MoreVert
    val CheckStandard: ImageVector = Icons.Default.Check
    val ArrowForwardStandard: ImageVector = Icons.AutoMirrored.Filled.ArrowForward

    // --- Drawable Resource IDs ---
    val SearchRes: Int = R.drawable.ic_search
    val SettingsRes: Int = R.drawable.ic_setting
    val PersonRes: Int = R.drawable.ic_profile
    val MoreVertRes: Int = R.drawable.ic_note_options
    val AddRes: Int = R.drawable.ic_float_buttion_withoutbackground
    val DeleteRes: Int = R.drawable.ic_trash
    val CopyRes: Int = R.drawable.ic_copy_note
    val PinRes: Int = R.drawable.ic_pinned
    val SortArrowsRes: Int = R.drawable.ic_recent
    val GridViewRes: Int = R.drawable.button_toggle_grid_or_list_view
    val ListViewRes: Int = R.drawable.ic_list_view
    val NoteDocRes: Int = R.drawable.ic_app_logo
    val FolderRes: Int = R.drawable.ic_folder
    val FolderHomeRes: Int = R.drawable.ic_folder_home
    val TagIconRes: Int = R.drawable.ic_tags
    val BookRes: Int = R.drawable.ic_book
    val PaletteRes: Int = R.drawable.ic_palette
    val ChatRes: Int = R.drawable.ic_chat
    val LightbulbRes: Int = R.drawable.ic_lightbulb
    val LotusRes: Int = R.drawable.ic_lotus
    val AttachmentRes: Int = R.drawable.ic_attachment
    val ArrowForwardRes: Int = R.drawable.ic_arrow_forward
    val CheckRes: Int = R.drawable.ic_check

    // --- Figma Note Editor Icons ---
    val UndoRes: Int = R.drawable.ic_undo
    val RedoRes: Int = R.drawable.ic_redo
    val DeleteNoteRes: Int = R.drawable.ic_delete_note
    val SaveCheckRes: Int = R.drawable.ic_save_check
    val BadgeFolderRes: Int = R.drawable.ic_badge_folder
    val BadgePinRes: Int = R.drawable.ic_badge_pin
    val ClockRes: Int = R.drawable.ic_clock
    val CameraRes: Int = R.drawable.ic_camera
    val ExpandRes: Int = R.drawable.ic_expand
    val CheckMarkRes: Int = R.drawable.ic_check_mark
    val WordsRes: Int = R.drawable.ic_words
    val CharactersRes: Int = R.drawable.ic_characters
    val ShareRes: Int = R.drawable.ic_share
    val MoreHorizRes: Int = R.drawable.ic_more_horiz
    val FormatListRes: Int = R.drawable.ic_format_list
    val FormatCheckRes: Int = R.drawable.ic_format_check
    val FormatQuoteRes: Int = R.drawable.ic_format_quote
    val SparkleAiRes: Int = R.drawable.ic_sparkle_ai

    // --- Compose ImageVectors ---
    val Search: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_search)
    val Settings: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_setting)
    val Person: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_profile)
    val MoreVert: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_note_options)
    val Add: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_float_buttion_withoutbackground)
    val Delete: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_trash)
    val Copy: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_copy_note)
    val Pin: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_pinned)
    val SortArrows: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_recent)
    val GridView: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.button_toggle_grid_or_list_view)
    val ListView: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_list_view)
    val NoteDoc: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_app_logo)
    val Folder: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_folder)
    val FolderHome: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_folder_home)
    val TagIcon: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_tags)
    val Book: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_book)
    val Palette: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_palette)
    val Chat: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_chat)
    val Lightbulb: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_lightbulb)
    val Lotus: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_lotus)
    val Attachment: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_attachment)
    val ArrowForward: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_arrow_forward)
    val Check: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_check)

    // Figma Editor ImageVectors
    val Undo: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_undo)
    val Redo: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_redo)
    val DeleteNote: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_delete_note)
    val SaveCheck: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_save_check)
    val BadgeFolder: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_badge_folder)
    val BadgePin: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_badge_pin)
    val Clock: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_clock)
    val Camera: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_camera)
    val Expand: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_expand)
    val CheckMark: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_check_mark)
    val Words: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_words)
    val Characters: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_characters)
    val Share: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_share)
    val MoreHoriz: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_more_horiz)
    val FormatList: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_format_list)
    val FormatCheck: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_format_check)
    val FormatQuote: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_format_quote)
    val SparkleAi: ImageVector @Composable get() = ImageVector.vectorResource(R.drawable.ic_sparkle_ai)
}
