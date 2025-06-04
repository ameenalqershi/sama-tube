package com.example.snaptube.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.snaptube.ui.viewmodels.LibraryItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryItemCard(
    item: LibraryItem,
    onPlayClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onPlayClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // صورة مصغرة أو أيقونة
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (item.thumbnailUrl != null) {
                    AsyncImage(
                        model = item.thumbnailUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // أيقونة تشغيل صغيرة في الزاوية
                    Surface(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    ) {
                        Icon(
                            imageVector = if (item.isAudioOnly) Icons.Default.MusicNote else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(4.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Icon(
                            imageVector = if (item.isAudioOnly) Icons.Default.MusicNote else Icons.Default.VideoFile,
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // معلومات الملف
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // العنوان
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // المؤلف
                Text(
                    text = item.author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // معلومات إضافية
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // نوع الملف
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (item.isAudioOnly) 
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                        else 
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = if (item.isAudioOnly) "صوت" else "فيديو",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (item.isAudioOnly) 
                                MaterialTheme.colorScheme.onTertiary
                            else 
                                MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // المدة
                    Text(
                        text = item.duration,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // حجم الملف
                    Text(
                        text = item.getFormattedFileSize(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // قائمة الخيارات
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "خيارات إضافية"
                    )
                }
                
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("تشغيل") },
                        onClick = {
                            onPlayClick()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                if (item.isAudioOnly) Icons.Default.MusicNote else Icons.Default.PlayArrow,
                                contentDescription = null
                            )
                        }
                    )
                    
                    DropdownMenuItem(
                        text = { Text("مشاركة") },
                        onClick = {
                            onShareClick()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Share, contentDescription = null)
                        }
                    )
                    
                    DropdownMenuItem(
                        text = { Text("حذف") },
                        onClick = {
                            onDeleteClick()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    )
                }
            }
        }
    }
}
