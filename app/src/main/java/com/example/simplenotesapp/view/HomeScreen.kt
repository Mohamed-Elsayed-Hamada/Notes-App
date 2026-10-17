package com.example.simplenotesapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.ui.theme.AppBackground
import com.example.simplenotesapp.ui.theme.BannerBg
import com.example.simplenotesapp.ui.theme.BannerCircleBg
import com.example.simplenotesapp.ui.theme.BannerIcon
import com.example.simplenotesapp.ui.theme.BannerRightIcon
import com.example.simplenotesapp.ui.theme.BannerText
import com.example.simplenotesapp.ui.theme.CardBorderHover
import com.example.simplenotesapp.ui.theme.PinnedText
import com.example.simplenotesapp.ui.theme.Primary
import com.example.simplenotesapp.ui.theme.PrimaryVariant
import com.example.simplenotesapp.ui.theme.PrimaryVibrant
import com.example.simplenotesapp.ui.theme.SurfaceLight
import com.example.simplenotesapp.ui.theme.TagPersonalBg
import com.example.simplenotesapp.ui.theme.TagPersonalText
import com.example.simplenotesapp.ui.theme.TextMuted
import com.example.simplenotesapp.ui.theme.TextPrimary
import com.example.simplenotesapp.ui.theme.TextSecondary
import com.example.simplenotesapp.view.components.AppIcons
import com.example.simplenotesapp.view.components.NoteCard
import com.example.simplenotesapp.view.uimodel.NotesScreenEffect
import com.example.simplenotesapp.view.uimodel.NotesScreenIntent
import com.example.simplenotesapp.view.uimodel.NotesScreenViewState
import com.example.simplenotesapp.view.viewmodel.NotesScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotesScreen(
    viewModel: NotesScreenViewModel,
    onOpenNote: (String?) -> Unit = {}
) {
    val state by viewModel.viewState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.effects) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is NotesScreenEffect.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = effect.actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.handleIntent(NotesScreenIntent.UndoDelete)
                    }
                }

                is NotesScreenEffect.NavigateToDetail -> {
                    onOpenNote(effect.noteId)
                }
            }
        }
    }

    NotesScreenContent(
        state = state,
        onIntent = viewModel::handleIntent,
        snackbarHostState = snackbarHostState,
        onOpenNote = onOpenNote
    )
}

@Composable
fun NotesScreenContent(
    state: NotesScreenViewState,
    onIntent: (NotesScreenIntent) -> Unit,
    snackbarHostState: SnackbarHostState,
    onOpenNote: (String?) -> Unit = {}
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var lastFabClickTime by remember { mutableLongStateOf(0L) }
    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        containerColor = AppBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            NotesTopBar(
                isSearchExpanded = isSearchExpanded,
                searchQuery = state.searchQuery,
                onSearchToggle = { isSearchExpanded = !isSearchExpanded },
                onSearchQueryChange = { onIntent(NotesScreenIntent.SearchNotes(it)) }
            )
        },
        bottomBar = {
            NotesBottomNavBar(
                activeTab = state.activeTab,
                onTabSelect = { onIntent(NotesScreenIntent.SetActiveTab(it)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val currentTime = android.os.SystemClock.elapsedRealtime()
                    if (currentTime - lastFabClickTime >= 600L) {
                        lastFabClickTime = currentTime
                        onIntent(NotesScreenIntent.CreateQuickNote)
                    }
                },
                containerColor = PrimaryVibrant,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(56.dp)
                    .shadow(8.dp, CircleShape, spotColor = PrimaryVibrant)
            ) {
                Icon(
                    imageVector = AppIcons.Add,
                    contentDescription = "New Note",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            // Header Stats Row: 12 notes, Synced just now, Recent, Grid view
            NotesHeaderStats(
                totalNotes = state.totalNotesCount,
                isGridView = state.isGridView,
                selectedSort = state.selectedSort,
                onToggleLayout = { onIntent(NotesScreenIntent.SetLayoutMode(!state.isGridView)) },
                onSortChange = { onIntent(NotesScreenIntent.SetSort(it)) }
            )

            Spacer(Modifier.height(14.dp))

            // PINNED SECTION
            if (state.pinnedNotes.isNotEmpty()) {
                PinnedSectionHeader(pinnedCount = state.pinnedNotes.size)
                Spacer(Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    state.pinnedNotes.forEach { note ->
                        NoteCard(
                            note = note,
                            isMenuOpen = state.activeMenuNoteId == note.id,
                            onMenuToggle = { isOpen ->
                                onIntent(
                                    NotesScreenIntent.SetActiveMenuNote(
                                        if (isOpen) note.id else null
                                    )
                                )
                            },
                            onPinToggle = { onIntent(NotesScreenIntent.TogglePin(note.id)) },
                            onDuplicate = { onIntent(NotesScreenIntent.DuplicateNote(note.id)) },
                            onDelete = { onIntent(NotesScreenIntent.DeleteNote(note.id)) },
                            onChecklistToggle = { itemId ->
                                onIntent(
                                    NotesScreenIntent.ToggleChecklistItem(
                                        note.id,
                                        itemId
                                    )
                                )
                            },
                            onClick = { onOpenNote(note.id) }
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))
            }

            // ALL NOTES SECTION
            AllNotesSectionHeader(
                selectedSort = state.selectedSort,
                onSortSelect = { onIntent(NotesScreenIntent.SetSort(it)) }
            )
            Spacer(Modifier.height(10.dp))

            if (state.allNotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (state.searchQuery.isBlank()) "No notes yet" else "No matching notes found",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            } else if (state.isGridView) {
                TwoColumnNotesGrid(
                    notes = state.allNotes,
                    activeMenuNoteId = state.activeMenuNoteId,
                    onIntent = onIntent,
                    onOpenNote = onOpenNote
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    state.allNotes.forEach { note ->
                        NoteCard(
                            note = note,
                            isMenuOpen = state.activeMenuNoteId == note.id,
                            onMenuToggle = { isOpen ->
                                onIntent(
                                    NotesScreenIntent.SetActiveMenuNote(
                                        if (isOpen) note.id else null
                                    )
                                )
                            },
                            onPinToggle = { onIntent(NotesScreenIntent.TogglePin(note.id)) },
                            onDuplicate = { onIntent(NotesScreenIntent.DuplicateNote(note.id)) },
                            onDelete = { onIntent(NotesScreenIntent.DeleteNote(note.id)) },
                            onChecklistToggle = { itemId ->
                                onIntent(
                                    NotesScreenIntent.ToggleChecklistItem(
                                        note.id,
                                        itemId
                                    )
                                )
                            },
                            onClick = { onOpenNote(note.id) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            // Bottom Quick Capture Banner
            QuickCaptureBanner(
                onClick = { onIntent(NotesScreenIntent.CreateQuickNote) }
            )

            // Extra spacing for FAB clearance
            Spacer(Modifier.height(72.dp))
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF5F5F8).copy(alpha = 0.34f))
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        onClick = {}
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary)
            }
        }
    }
}

@Composable
fun TwoColumnNotesGrid(
    notes: List<Note>,
    activeMenuNoteId: String?,
    onIntent: (NotesScreenIntent) -> Unit,
    onOpenNote: (String) -> Unit = {}
) {
    val column1Notes = notes.filterIndexed { index, _ -> index % 2 == 0 }
    val column2Notes = notes.filterIndexed { index, _ -> index % 2 != 0 }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            column1Notes.forEach { note ->
                NoteCard(
                    note = note,
                    isMenuOpen = activeMenuNoteId == note.id,
                    onMenuToggle = { isOpen ->
                        onIntent(
                            NotesScreenIntent.SetActiveMenuNote(
                                if (isOpen) note.id else null
                            )
                        )
                    },
                    onPinToggle = { onIntent(NotesScreenIntent.TogglePin(note.id)) },
                    onDuplicate = { onIntent(NotesScreenIntent.DuplicateNote(note.id)) },
                    onDelete = { onIntent(NotesScreenIntent.DeleteNote(note.id)) },
                    onChecklistToggle = { itemId ->
                        onIntent(
                            NotesScreenIntent.ToggleChecklistItem(
                                note.id,
                                itemId
                            )
                        )
                    },
                    onClick = { onOpenNote(note.id) }
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            column2Notes.forEach { note ->
                NoteCard(
                    note = note,
                    isMenuOpen = activeMenuNoteId == note.id,
                    onMenuToggle = { isOpen ->
                        onIntent(
                            NotesScreenIntent.SetActiveMenuNote(
                                if (isOpen) note.id else null
                            )
                        )
                    },
                    onPinToggle = { onIntent(NotesScreenIntent.TogglePin(note.id)) },
                    onDuplicate = { onIntent(NotesScreenIntent.DuplicateNote(note.id)) },
                    onDelete = { onIntent(NotesScreenIntent.DeleteNote(note.id)) },
                    onChecklistToggle = { itemId ->
                        onIntent(
                            NotesScreenIntent.ToggleChecklistItem(
                                note.id,
                                itemId
                            )
                        )
                    },
                    onClick = { onOpenNote(note.id) }
                )
            }
        }
    }
}

@Composable
fun NotesTopBar(
    isSearchExpanded: Boolean,
    searchQuery: String,
    onSearchToggle: () -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: App Logo + Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = AppIcons.NoteDoc,
                        contentDescription = "App Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text = "MY NOTES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "All Notes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            // Right Action Icons: Search, Settings, Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onSearchToggle) {
                    Icon(
                        imageVector = AppIcons.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF334155),
                        modifier = Modifier.size(21.dp)
                    )
                }
                IconButton(onClick = { /* Settings */ }) {
                    Icon(
                        imageVector = AppIcons.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFF334155),
                        modifier = Modifier.size(21.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(PrimaryVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = AppIcons.Person,
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        if (isSearchExpanded) {
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search notes...", fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = CardBorderHover,
                    focusedContainerColor = SurfaceLight,
                    unfocusedContainerColor = SurfaceLight
                )
            )
        }
    }
}

@Composable
fun NotesHeaderStats(
    totalNotes: Int,
    isGridView: Boolean,
    selectedSort: String,
    onToggleLayout: () -> Unit,
    onSortChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: "12 notes" + "Synced just now"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(TagPersonalBg)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$totalNotes notes",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = TagPersonalText
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Synced just now",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        // Right: Sort (⇅ Recent) + Grid/List Switch
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            var sortMenuOpen by remember { mutableStateOf(false) }
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceLight)
                        .border(1.dp, CardBorderHover, RoundedCornerShape(50))
                        .clickable { sortMenuOpen = true }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.SortArrows,
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = selectedSort,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                }

                DropdownMenu(
                    expanded = sortMenuOpen,
                    onDismissRequest = { sortMenuOpen = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Recent") },
                        onClick = {
                            onSortChange("Recent")
                            sortMenuOpen = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Title") },
                        onClick = {
                            onSortChange("Title")
                            sortMenuOpen = false
                        }
                    )
                }
            }

            // Grid / List toggle button
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceLight)
                    .border(1.dp, CardBorderHover, RoundedCornerShape(8.dp))
                    .clickable(onClick = onToggleLayout),
                contentAlignment = Alignment.Center
            ) {
                if (isGridView) {
                    Icon(
                        imageVector = AppIcons.GridView,
                        contentDescription = "Toggle Layout",
                        tint = Color(0xFF334155),
                        modifier = Modifier.size(26.dp)
                    )
                } else
                    Icon(
                        imageVector = AppIcons.ListView,
                        contentDescription = "Toggle Layout",
                        tint = Color(0xFF334155),
                        modifier = Modifier.size(17.dp)
                    )
            }
        }
    }
}

@Composable
fun PinnedSectionHeader(pinnedCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = AppIcons.Pin,
                contentDescription = null,
                tint = PinnedText,
                modifier = Modifier.size(13.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "PINNED",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PinnedText,
                letterSpacing = 0.5.sp
            )
        }
        Text(
            text = "$pinnedCount notes",
            fontSize = 11.5.sp,
            color = TextMuted
        )
    }
}

@Composable
fun AllNotesSectionHeader(
    selectedSort: String,
    onSortSelect: (String) -> Unit
) {
    var sortOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = AppIcons.FolderHome,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = "ALL NOTES",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 0.5.sp
            )
        }

        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { sortOpen = true }
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Date modified",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Primary
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "⌵",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }

            DropdownMenu(
                expanded = sortOpen,
                onDismissRequest = { sortOpen = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Date modified") },
                    onClick = {
                        onSortSelect("Date modified")
                        sortOpen = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Alphabetical") },
                    onClick = {
                        onSortSelect("Alphabetical")
                        sortOpen = false
                    }
                )
            }
        }
    }
}

@Composable
fun QuickCaptureBanner(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(BannerBg)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BannerCircleBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.Lightbulb,
                    contentDescription = null,
                    tint = BannerIcon,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = "Capture spontaneous thoughts",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BannerText
                )
                Text(
                    text = "Markdown, audio, and checklists supported",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }
        Icon(
            imageVector = AppIcons.Lotus,
            contentDescription = null,
            tint = BannerRightIcon,
            modifier = Modifier.size(19.dp)
        )
    }
}

@Composable
fun NotesBottomNavBar(
    activeTab: String,
    onTabSelect: (String) -> Unit
) {
    val tabs = listOf(
        Triple("Notes", AppIcons.NoteDoc, "Notes"),
        Triple("Folders", AppIcons.Folder, "Folders"),
        Triple("Pinned", AppIcons.Pin, "Pinned"),
        Triple("Tags", AppIcons.TagIcon, "Tags")
    )

    NavigationBar(
        containerColor = SurfaceLight,
        tonalElevation = 6.dp,
        modifier = Modifier.height(64.dp)
    ) {
        tabs.forEach { (title, icon, tabKey) ->
            val isSelected = activeTab == tabKey
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelect(tabKey) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(19.dp)
                    )
                },
                label = {
                    Text(
                        text = title,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}