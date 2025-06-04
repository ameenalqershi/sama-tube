package com.example.snaptube.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.snaptube.database.converters.DateConverter;
import com.example.snaptube.database.converters.StringListConverter;
import com.example.snaptube.database.entities.PlaylistEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlaylistDao_Impl implements PlaylistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlaylistEntity> __insertionAdapterOfPlaylistEntity;

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<PlaylistEntity> __deletionAdapterOfPlaylistEntity;

  private final EntityDeletionOrUpdateAdapter<PlaylistEntity> __updateAdapterOfPlaylistEntity;

  private final SharedSQLiteStatement __preparedStmtOfIncrementAccessCount;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBookmarkStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlaylistsOlderThan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExcessPlaylists;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastAccessed;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadStats;

  private final SharedSQLiteStatement __preparedStmtOfMarkDownloadStarted;

  private final SharedSQLiteStatement __preparedStmtOfMarkDownloadCompleted;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadPreferences;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadPath;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePriority;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadAllSelected;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlaylistByUrl;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlaylistsByPlatform;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUnusedOldPlaylists;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldCompletedPlaylists;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUnusedPlaylists;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllPlaylists;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLeastRecentlyUsed;

  private final SharedSQLiteStatement __preparedStmtOfCleanupOldPlaylists;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlaylistStats;

  public PlaylistDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlaylistEntity = new EntityInsertionAdapter<PlaylistEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `playlists` (`url`,`playlistId`,`title`,`description`,`thumbnail`,`author`,`authorUrl`,`authorThumbnail`,`platform`,`videoCount`,`downloadedCount`,`failedCount`,`pendingCount`,`totalDuration`,`totalSize`,`videoUrls`,`videoTitles`,`videoDurations`,`videoThumbnails`,`isPublic`,`isLive`,`uploadDate`,`lastUpdated`,`language`,`tags`,`categories`,`viewCount`,`subscriberCount`,`createdAt`,`updatedAt`,`lastAccessed`,`downloadStartedAt`,`downloadCompletedAt`,`accessCount`,`downloadProgress`,`selectedQuality`,`selectedFormat`,`downloadPath`,`isBookmarked`,`priority`,`downloadAllSelected`,`selectedVideoIndices`,`failedVideoUrls`,`metadata`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaylistEntity entity) {
        statement.bindString(1, entity.getUrl());
        statement.bindString(2, entity.getPlaylistId());
        statement.bindString(3, entity.getTitle());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        if (entity.getThumbnail() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getThumbnail());
        }
        if (entity.getAuthor() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAuthor());
        }
        if (entity.getAuthorUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAuthorUrl());
        }
        if (entity.getAuthorThumbnail() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAuthorThumbnail());
        }
        statement.bindString(9, entity.getPlatform());
        statement.bindLong(10, entity.getVideoCount());
        statement.bindLong(11, entity.getDownloadedCount());
        statement.bindLong(12, entity.getFailedCount());
        statement.bindLong(13, entity.getPendingCount());
        statement.bindLong(14, entity.getTotalDuration());
        statement.bindLong(15, entity.getTotalSize());
        final String _tmp = __stringListConverter.fromStringList(entity.getVideoUrls());
        statement.bindString(16, _tmp);
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getVideoTitles());
        statement.bindString(17, _tmp_1);
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getVideoDurations());
        statement.bindString(18, _tmp_2);
        final String _tmp_3 = __stringListConverter.fromStringList(entity.getVideoThumbnails());
        statement.bindString(19, _tmp_3);
        final int _tmp_4 = entity.isPublic() ? 1 : 0;
        statement.bindLong(20, _tmp_4);
        final int _tmp_5 = entity.isLive() ? 1 : 0;
        statement.bindLong(21, _tmp_5);
        if (entity.getUploadDate() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getUploadDate());
        }
        if (entity.getLastUpdated() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getLastUpdated());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(24);
        } else {
          statement.bindString(24, entity.getLanguage());
        }
        final String _tmp_6 = __stringListConverter.fromStringList(entity.getTags());
        statement.bindString(25, _tmp_6);
        final String _tmp_7 = __stringListConverter.fromStringList(entity.getCategories());
        statement.bindString(26, _tmp_7);
        statement.bindLong(27, entity.getViewCount());
        statement.bindLong(28, entity.getSubscriberCount());
        final Long _tmp_8 = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_8 == null) {
          statement.bindNull(29);
        } else {
          statement.bindLong(29, _tmp_8);
        }
        final Long _tmp_9 = __dateConverter.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_9 == null) {
          statement.bindNull(30);
        } else {
          statement.bindLong(30, _tmp_9);
        }
        final Long _tmp_10 = __dateConverter.dateToTimestamp(entity.getLastAccessed());
        if (_tmp_10 == null) {
          statement.bindNull(31);
        } else {
          statement.bindLong(31, _tmp_10);
        }
        final Long _tmp_11 = __dateConverter.dateToTimestamp(entity.getDownloadStartedAt());
        if (_tmp_11 == null) {
          statement.bindNull(32);
        } else {
          statement.bindLong(32, _tmp_11);
        }
        final Long _tmp_12 = __dateConverter.dateToTimestamp(entity.getDownloadCompletedAt());
        if (_tmp_12 == null) {
          statement.bindNull(33);
        } else {
          statement.bindLong(33, _tmp_12);
        }
        statement.bindLong(34, entity.getAccessCount());
        statement.bindDouble(35, entity.getDownloadProgress());
        if (entity.getSelectedQuality() == null) {
          statement.bindNull(36);
        } else {
          statement.bindString(36, entity.getSelectedQuality());
        }
        if (entity.getSelectedFormat() == null) {
          statement.bindNull(37);
        } else {
          statement.bindString(37, entity.getSelectedFormat());
        }
        if (entity.getDownloadPath() == null) {
          statement.bindNull(38);
        } else {
          statement.bindString(38, entity.getDownloadPath());
        }
        final int _tmp_13 = entity.isBookmarked() ? 1 : 0;
        statement.bindLong(39, _tmp_13);
        statement.bindLong(40, entity.getPriority());
        final int _tmp_14 = entity.getDownloadAllSelected() ? 1 : 0;
        statement.bindLong(41, _tmp_14);
        final String _tmp_15 = __stringListConverter.fromStringList(entity.getSelectedVideoIndices());
        statement.bindString(42, _tmp_15);
        final String _tmp_16 = __stringListConverter.fromStringList(entity.getFailedVideoUrls());
        statement.bindString(43, _tmp_16);
        if (entity.getMetadata() == null) {
          statement.bindNull(44);
        } else {
          statement.bindString(44, entity.getMetadata());
        }
      }
    };
    this.__deletionAdapterOfPlaylistEntity = new EntityDeletionOrUpdateAdapter<PlaylistEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `playlists` WHERE `url` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaylistEntity entity) {
        statement.bindString(1, entity.getUrl());
      }
    };
    this.__updateAdapterOfPlaylistEntity = new EntityDeletionOrUpdateAdapter<PlaylistEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `playlists` SET `url` = ?,`playlistId` = ?,`title` = ?,`description` = ?,`thumbnail` = ?,`author` = ?,`authorUrl` = ?,`authorThumbnail` = ?,`platform` = ?,`videoCount` = ?,`downloadedCount` = ?,`failedCount` = ?,`pendingCount` = ?,`totalDuration` = ?,`totalSize` = ?,`videoUrls` = ?,`videoTitles` = ?,`videoDurations` = ?,`videoThumbnails` = ?,`isPublic` = ?,`isLive` = ?,`uploadDate` = ?,`lastUpdated` = ?,`language` = ?,`tags` = ?,`categories` = ?,`viewCount` = ?,`subscriberCount` = ?,`createdAt` = ?,`updatedAt` = ?,`lastAccessed` = ?,`downloadStartedAt` = ?,`downloadCompletedAt` = ?,`accessCount` = ?,`downloadProgress` = ?,`selectedQuality` = ?,`selectedFormat` = ?,`downloadPath` = ?,`isBookmarked` = ?,`priority` = ?,`downloadAllSelected` = ?,`selectedVideoIndices` = ?,`failedVideoUrls` = ?,`metadata` = ? WHERE `url` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaylistEntity entity) {
        statement.bindString(1, entity.getUrl());
        statement.bindString(2, entity.getPlaylistId());
        statement.bindString(3, entity.getTitle());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        if (entity.getThumbnail() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getThumbnail());
        }
        if (entity.getAuthor() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAuthor());
        }
        if (entity.getAuthorUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAuthorUrl());
        }
        if (entity.getAuthorThumbnail() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAuthorThumbnail());
        }
        statement.bindString(9, entity.getPlatform());
        statement.bindLong(10, entity.getVideoCount());
        statement.bindLong(11, entity.getDownloadedCount());
        statement.bindLong(12, entity.getFailedCount());
        statement.bindLong(13, entity.getPendingCount());
        statement.bindLong(14, entity.getTotalDuration());
        statement.bindLong(15, entity.getTotalSize());
        final String _tmp = __stringListConverter.fromStringList(entity.getVideoUrls());
        statement.bindString(16, _tmp);
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getVideoTitles());
        statement.bindString(17, _tmp_1);
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getVideoDurations());
        statement.bindString(18, _tmp_2);
        final String _tmp_3 = __stringListConverter.fromStringList(entity.getVideoThumbnails());
        statement.bindString(19, _tmp_3);
        final int _tmp_4 = entity.isPublic() ? 1 : 0;
        statement.bindLong(20, _tmp_4);
        final int _tmp_5 = entity.isLive() ? 1 : 0;
        statement.bindLong(21, _tmp_5);
        if (entity.getUploadDate() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getUploadDate());
        }
        if (entity.getLastUpdated() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getLastUpdated());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(24);
        } else {
          statement.bindString(24, entity.getLanguage());
        }
        final String _tmp_6 = __stringListConverter.fromStringList(entity.getTags());
        statement.bindString(25, _tmp_6);
        final String _tmp_7 = __stringListConverter.fromStringList(entity.getCategories());
        statement.bindString(26, _tmp_7);
        statement.bindLong(27, entity.getViewCount());
        statement.bindLong(28, entity.getSubscriberCount());
        final Long _tmp_8 = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_8 == null) {
          statement.bindNull(29);
        } else {
          statement.bindLong(29, _tmp_8);
        }
        final Long _tmp_9 = __dateConverter.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_9 == null) {
          statement.bindNull(30);
        } else {
          statement.bindLong(30, _tmp_9);
        }
        final Long _tmp_10 = __dateConverter.dateToTimestamp(entity.getLastAccessed());
        if (_tmp_10 == null) {
          statement.bindNull(31);
        } else {
          statement.bindLong(31, _tmp_10);
        }
        final Long _tmp_11 = __dateConverter.dateToTimestamp(entity.getDownloadStartedAt());
        if (_tmp_11 == null) {
          statement.bindNull(32);
        } else {
          statement.bindLong(32, _tmp_11);
        }
        final Long _tmp_12 = __dateConverter.dateToTimestamp(entity.getDownloadCompletedAt());
        if (_tmp_12 == null) {
          statement.bindNull(33);
        } else {
          statement.bindLong(33, _tmp_12);
        }
        statement.bindLong(34, entity.getAccessCount());
        statement.bindDouble(35, entity.getDownloadProgress());
        if (entity.getSelectedQuality() == null) {
          statement.bindNull(36);
        } else {
          statement.bindString(36, entity.getSelectedQuality());
        }
        if (entity.getSelectedFormat() == null) {
          statement.bindNull(37);
        } else {
          statement.bindString(37, entity.getSelectedFormat());
        }
        if (entity.getDownloadPath() == null) {
          statement.bindNull(38);
        } else {
          statement.bindString(38, entity.getDownloadPath());
        }
        final int _tmp_13 = entity.isBookmarked() ? 1 : 0;
        statement.bindLong(39, _tmp_13);
        statement.bindLong(40, entity.getPriority());
        final int _tmp_14 = entity.getDownloadAllSelected() ? 1 : 0;
        statement.bindLong(41, _tmp_14);
        final String _tmp_15 = __stringListConverter.fromStringList(entity.getSelectedVideoIndices());
        statement.bindString(42, _tmp_15);
        final String _tmp_16 = __stringListConverter.fromStringList(entity.getFailedVideoUrls());
        statement.bindString(43, _tmp_16);
        if (entity.getMetadata() == null) {
          statement.bindNull(44);
        } else {
          statement.bindString(44, entity.getMetadata());
        }
        statement.bindString(45, entity.getUrl());
      }
    };
    this.__preparedStmtOfIncrementAccessCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET accessCount = accessCount + 1, lastAccessed = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateBookmarkStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET isBookmarked = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePlaylistsOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE lastAccessed < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteExcessPlaylists = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE url NOT IN (SELECT url FROM playlists ORDER BY lastAccessed DESC LIMIT ?)";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastAccessed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET lastAccessed = ? WHERE playlistId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadStats = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET downloadedCount = ?, failedCount = ?, pendingCount = ?, downloadProgress = ?, updatedAt = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkDownloadStarted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET downloadStartedAt = ?, updatedAt = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkDownloadCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET downloadCompletedAt = ?, downloadProgress = 1.0, updatedAt = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadPreferences = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET selectedQuality = ?, selectedFormat = ?, updatedAt = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadPath = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET downloadPath = ?, updatedAt = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePriority = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET priority = ?, updatedAt = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadAllSelected = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET downloadAllSelected = ?, updatedAt = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePlaylistByUrl = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePlaylistsByPlatform = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE platform = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUnusedOldPlaylists = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE downloadedCount = 0 AND createdAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldCompletedPlaylists = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE downloadedCount = videoCount AND downloadCompletedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUnusedPlaylists = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE isBookmarked = 0 AND lastAccessed < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllPlaylists = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteLeastRecentlyUsed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE url IN (SELECT url FROM playlists WHERE isBookmarked = 0 ORDER BY lastAccessed ASC LIMIT ?)";
        return _query;
      }
    };
    this.__preparedStmtOfCleanupOldPlaylists = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE lastAccessed < ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePlaylistStats = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playlists SET videoCount = ?, totalDuration = ? WHERE playlistId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPlaylist(final PlaylistEntity playlist,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPlaylistEntity.insertAndReturnId(playlist);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPlaylists(final List<PlaylistEntity> playlists,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfPlaylistEntity.insertAndReturnIdsList(playlists);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePlaylist(final PlaylistEntity playlist,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __deletionAdapterOfPlaylistEntity.handle(playlist);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePlaylist(final PlaylistEntity playlist,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfPlaylistEntity.handle(playlist);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePlaylists(final List<PlaylistEntity> playlists,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfPlaylistEntity.handleMultiple(playlists);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertPlaylist(final PlaylistEntity playlist,
      final Continuation<? super Long> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> PlaylistDao.DefaultImpls.upsertPlaylist(PlaylistDao_Impl.this, playlist, __cont), $completion);
  }

  @Override
  public Object cleanupOldPlaylists(final int maxEntries, final long maxAge,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> PlaylistDao.DefaultImpls.cleanupOldPlaylists(PlaylistDao_Impl.this, maxEntries, maxAge, __cont), $completion);
  }

  @Override
  public Object incrementAccessCount(final String url, final Date accessTime,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementAccessCount.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(accessTime);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementAccessCount.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBookmarkStatus(final String url, final boolean isBookmarked,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBookmarkStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isBookmarked ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateBookmarkStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePlaylistsOlderThan(final long timestamp,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlaylistsOlderThan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePlaylistsOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExcessPlaylists(final int maxCount,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExcessPlaylists.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, maxCount);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteExcessPlaylists.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastAccessed(final String playlistId, final Date timestamp,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastAccessed.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(timestamp);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindString(_argIndex, playlistId);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLastAccessed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadStats(final String url, final int downloaded, final int failed,
      final int pending, final float progress, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadStats.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, downloaded);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, failed);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, pending);
        _argIndex = 4;
        _stmt.bindDouble(_argIndex, progress);
        _argIndex = 5;
        final Long _tmp = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 6;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateDownloadStats.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markDownloadStarted(final String url, final Date startedAt, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkDownloadStarted.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(startedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkDownloadStarted.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markDownloadCompleted(final String url, final Date completedAt,
      final Date updatedAt, final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkDownloadCompleted.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(completedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkDownloadCompleted.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadPreferences(final String url, final String quality,
      final String format, final Date updatedAt, final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadPreferences.acquire();
        int _argIndex = 1;
        if (quality == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, quality);
        }
        _argIndex = 2;
        if (format == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, format);
        }
        _argIndex = 3;
        final Long _tmp = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 4;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateDownloadPreferences.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadPath(final String url, final String path, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadPath.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, path);
        _argIndex = 2;
        final Long _tmp = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateDownloadPath.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePriority(final String url, final int priority, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePriority.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, priority);
        _argIndex = 2;
        final Long _tmp = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePriority.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadAllSelected(final String url, final boolean downloadAll,
      final Date updatedAt, final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadAllSelected.acquire();
        int _argIndex = 1;
        final int _tmp = downloadAll ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateDownloadAllSelected.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePlaylistByUrl(final String url,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlaylistByUrl.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, url);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePlaylistByUrl.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePlaylistsByPlatform(final String platform,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlaylistsByPlatform.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, platform);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePlaylistsByPlatform.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUnusedOldPlaylists(final Date beforeDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUnusedOldPlaylists.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(beforeDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteUnusedOldPlaylists.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldCompletedPlaylists(final Date beforeDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldCompletedPlaylists.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(beforeDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldCompletedPlaylists.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUnusedPlaylists(final Date beforeDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUnusedPlaylists.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(beforeDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteUnusedPlaylists.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllPlaylists(final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllPlaylists.acquire();
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllPlaylists.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLeastRecentlyUsed(final int count,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLeastRecentlyUsed.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, count);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteLeastRecentlyUsed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object cleanupOldPlaylists(final long cutoffDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCleanupOldPlaylists.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cutoffDate);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfCleanupOldPlaylists.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePlaylistStats(final String playlistId, final int videoCount,
      final long totalDuration, final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlaylistStats.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, videoCount);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, totalDuration);
        _argIndex = 3;
        _stmt.bindString(_argIndex, playlistId);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePlaylistStats.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlaylistEntity>> getAllPlaylists() {
    final String _sql = "SELECT * FROM playlists ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPlaylistByUrl(final String url,
      final Continuation<? super PlaylistEntity> $completion) {
    final String _sql = "SELECT * FROM playlists WHERE url = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, url);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaylistEntity>() {
      @Override
      @Nullable
      public PlaylistEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final PlaylistEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _result = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlaylistById(final String playlistId,
      final Continuation<? super PlaylistEntity> $completion) {
    final String _sql = "SELECT * FROM playlists WHERE playlistId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, playlistId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaylistEntity>() {
      @Override
      @Nullable
      public PlaylistEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final PlaylistEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _result = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlaylistEntity>> getPlaylistsByPlatform(final String platform) {
    final String _sql = "SELECT * FROM playlists WHERE platform = ? ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, platform);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getPlaylistsByAuthor(final String author) {
    final String _sql = "SELECT * FROM playlists WHERE author = ? ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, author);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getBookmarkedPlaylists() {
    final String _sql = "SELECT * FROM playlists WHERE isBookmarked = 1 ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getPlaylistsWithDownloads() {
    final String _sql = "SELECT * FROM playlists WHERE downloadedCount > 0 ORDER BY downloadProgress DESC, lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getCompletedPlaylists() {
    final String _sql = "SELECT * FROM playlists WHERE downloadedCount = videoCount AND videoCount > 0 ORDER BY downloadCompletedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getInProgressPlaylists() {
    final String _sql = "SELECT * FROM playlists WHERE downloadedCount > 0 AND downloadedCount < videoCount ORDER BY downloadProgress DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getPlaylistsWithFailures() {
    final String _sql = "SELECT * FROM playlists WHERE failedCount > 0 ORDER BY failedCount DESC, lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> searchPlaylists(final String query) {
    final String _sql = "SELECT * FROM playlists WHERE title LIKE '%' || ? || '%' OR author LIKE '%' || ? || '%' ORDER BY accessCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getPlaylistsByVideoCount(final int minCount,
      final int maxCount) {
    final String _sql = "SELECT * FROM playlists WHERE videoCount BETWEEN ? AND ? ORDER BY videoCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, minCount);
    _argIndex = 2;
    _statement.bindLong(_argIndex, maxCount);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getPlaylistsByDuration(final long minDuration,
      final long maxDuration) {
    final String _sql = "SELECT * FROM playlists WHERE totalDuration BETWEEN ? AND ? ORDER BY totalDuration DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, minDuration);
    _argIndex = 2;
    _statement.bindLong(_argIndex, maxDuration);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PlaylistEntity>> getPlaylistsByDateRange(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM playlists WHERE createdAt BETWEEN ? AND ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __dateConverter.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __dateConverter.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoTitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpVideoDurations;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_5);
            final boolean _tmpIsPublic;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_6 != 0;
            final boolean _tmpIsLive;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_7 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_8;
            _tmp_8 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_8);
            final List<String> _tmpCategories;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_9);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_11;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_13;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_15 = __dateConverter.fromTimestamp(_tmp_14);
            if (_tmp_15 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_15;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_16);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_17;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_17 = null;
            } else {
              _tmp_17 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_17);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_18;
            _tmp_18 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_18 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_19;
            _tmp_19 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_19 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_20;
            _tmp_20 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_20);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_21;
            _tmp_21 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_21);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getMostAccessedPlaylists(final int limit,
      final Continuation<? super List<PlaylistEntity>> $completion) {
    final String _sql = "SELECT * FROM playlists ORDER BY accessCount DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRecentlyAccessedPlaylists(final Date sinceDate,
      final Continuation<? super List<PlaylistEntity>> $completion) {
    final String _sql = "SELECT * FROM playlists WHERE lastAccessed >= ? ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __dateConverter.dateToTimestamp(sinceDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoTitles;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoDurations;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_4);
            final boolean _tmpIsPublic;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_5 != 0;
            final boolean _tmpIsLive;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_6 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_7);
            final List<String> _tmpCategories;
            final String _tmp_8;
            _tmp_8 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_8);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_9;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_10 = __dateConverter.fromTimestamp(_tmp_9);
            if (_tmp_10 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_10;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_11;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_12 = __dateConverter.fromTimestamp(_tmp_11);
            if (_tmp_12 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_12;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_13;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_13 = null;
            } else {
              _tmp_13 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_14 = __dateConverter.fromTimestamp(_tmp_13);
            if (_tmp_14 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_14;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_15);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_16);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_17 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_18;
            _tmp_18 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_18 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_19);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_20;
            _tmp_20 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_20);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalPlaylistCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlaylistCountByPlatform(final String platform,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM playlists WHERE platform = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, platform);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalVideoCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(videoCount) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalDownloadedCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(downloadedCount) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalPlaylistSize(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(totalSize) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final long _tmp;
            _tmp = _cursor.getLong(0);
            _result = _tmp;
          } else {
            _result = 0L;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalPlaylistDuration(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(totalDuration) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final long _tmp;
            _tmp = _cursor.getLong(0);
            _result = _tmp;
          } else {
            _result = 0L;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageDownloadProgress(final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(downloadProgress) FROM playlists WHERE downloadedCount > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final float _tmp;
            _tmp = _cursor.getFloat(0);
            _result = _tmp;
          } else {
            _result = 0f;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllPlatforms(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT platform FROM playlists ORDER BY platform ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllAuthors(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT author FROM playlists WHERE author IS NOT NULL ORDER BY author ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlaylistEntity>> getAllPlaylistsFlow() {
    final String _sql = "SELECT * FROM playlists ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<PlaylistEntity>>() {
      @Override
      @NonNull
      public List<PlaylistEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfVideoCount = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCount");
          final int _cursorIndexOfDownloadedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedCount");
          final int _cursorIndexOfFailedCount = CursorUtil.getColumnIndexOrThrow(_cursor, "failedCount");
          final int _cursorIndexOfPendingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingCount");
          final int _cursorIndexOfTotalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDuration");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrls");
          final int _cursorIndexOfVideoTitles = CursorUtil.getColumnIndexOrThrow(_cursor, "videoTitles");
          final int _cursorIndexOfVideoDurations = CursorUtil.getColumnIndexOrThrow(_cursor, "videoDurations");
          final int _cursorIndexOfVideoThumbnails = CursorUtil.getColumnIndexOrThrow(_cursor, "videoThumbnails");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfSubscriberCount = CursorUtil.getColumnIndexOrThrow(_cursor, "subscriberCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfDownloadStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadStartedAt");
          final int _cursorIndexOfDownloadCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadCompletedAt");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfDownloadProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadProgress");
          final int _cursorIndexOfSelectedQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedQuality");
          final int _cursorIndexOfSelectedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedFormat");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDownloadAllSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadAllSelected");
          final int _cursorIndexOfSelectedVideoIndices = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedVideoIndices");
          final int _cursorIndexOfFailedVideoUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "failedVideoUrls");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<PlaylistEntity> _result = new ArrayList<PlaylistEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpPlaylistId;
            _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorUrl;
            if (_cursor.isNull(_cursorIndexOfAuthorUrl)) {
              _tmpAuthorUrl = null;
            } else {
              _tmpAuthorUrl = _cursor.getString(_cursorIndexOfAuthorUrl);
            }
            final String _tmpAuthorThumbnail;
            if (_cursor.isNull(_cursorIndexOfAuthorThumbnail)) {
              _tmpAuthorThumbnail = null;
            } else {
              _tmpAuthorThumbnail = _cursor.getString(_cursorIndexOfAuthorThumbnail);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpVideoCount;
            _tmpVideoCount = _cursor.getInt(_cursorIndexOfVideoCount);
            final int _tmpDownloadedCount;
            _tmpDownloadedCount = _cursor.getInt(_cursorIndexOfDownloadedCount);
            final int _tmpFailedCount;
            _tmpFailedCount = _cursor.getInt(_cursorIndexOfFailedCount);
            final int _tmpPendingCount;
            _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final List<String> _tmpVideoUrls;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfVideoUrls);
            _tmpVideoUrls = __stringListConverter.toStringList(_tmp);
            final List<String> _tmpVideoTitles;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfVideoTitles);
            _tmpVideoTitles = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpVideoDurations;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfVideoDurations);
            _tmpVideoDurations = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpVideoThumbnails;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfVideoThumbnails);
            _tmpVideoThumbnails = __stringListConverter.toStringList(_tmp_3);
            final boolean _tmpIsPublic;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp_4 != 0;
            final boolean _tmpIsLive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_5 != 0;
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpTags;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpCategories;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_7);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpSubscriberCount;
            _tmpSubscriberCount = _cursor.getLong(_cursorIndexOfSubscriberCount);
            final Date _tmpCreatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_10;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_10 = null;
            } else {
              _tmp_10 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_11 = __dateConverter.fromTimestamp(_tmp_10);
            if (_tmp_11 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_11;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_12;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_12 = null;
            } else {
              _tmp_12 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_13 = __dateConverter.fromTimestamp(_tmp_12);
            if (_tmp_13 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_13;
            }
            final Date _tmpDownloadStartedAt;
            final Long _tmp_14;
            if (_cursor.isNull(_cursorIndexOfDownloadStartedAt)) {
              _tmp_14 = null;
            } else {
              _tmp_14 = _cursor.getLong(_cursorIndexOfDownloadStartedAt);
            }
            _tmpDownloadStartedAt = __dateConverter.fromTimestamp(_tmp_14);
            final Date _tmpDownloadCompletedAt;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfDownloadCompletedAt)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfDownloadCompletedAt);
            }
            _tmpDownloadCompletedAt = __dateConverter.fromTimestamp(_tmp_15);
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final float _tmpDownloadProgress;
            _tmpDownloadProgress = _cursor.getFloat(_cursorIndexOfDownloadProgress);
            final String _tmpSelectedQuality;
            if (_cursor.isNull(_cursorIndexOfSelectedQuality)) {
              _tmpSelectedQuality = null;
            } else {
              _tmpSelectedQuality = _cursor.getString(_cursorIndexOfSelectedQuality);
            }
            final String _tmpSelectedFormat;
            if (_cursor.isNull(_cursorIndexOfSelectedFormat)) {
              _tmpSelectedFormat = null;
            } else {
              _tmpSelectedFormat = _cursor.getString(_cursorIndexOfSelectedFormat);
            }
            final String _tmpDownloadPath;
            if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
              _tmpDownloadPath = null;
            } else {
              _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_16;
            _tmp_16 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_16 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpDownloadAllSelected;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfDownloadAllSelected);
            _tmpDownloadAllSelected = _tmp_17 != 0;
            final List<String> _tmpSelectedVideoIndices;
            final String _tmp_18;
            _tmp_18 = _cursor.getString(_cursorIndexOfSelectedVideoIndices);
            _tmpSelectedVideoIndices = __stringListConverter.toStringList(_tmp_18);
            final List<String> _tmpFailedVideoUrls;
            final String _tmp_19;
            _tmp_19 = _cursor.getString(_cursorIndexOfFailedVideoUrls);
            _tmpFailedVideoUrls = __stringListConverter.toStringList(_tmp_19);
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new PlaylistEntity(_tmpUrl,_tmpPlaylistId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpVideoCount,_tmpDownloadedCount,_tmpFailedCount,_tmpPendingCount,_tmpTotalDuration,_tmpTotalSize,_tmpVideoUrls,_tmpVideoTitles,_tmpVideoDurations,_tmpVideoThumbnails,_tmpIsPublic,_tmpIsLive,_tmpUploadDate,_tmpLastUpdated,_tmpLanguage,_tmpTags,_tmpCategories,_tmpViewCount,_tmpSubscriberCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpDownloadStartedAt,_tmpDownloadCompletedAt,_tmpAccessCount,_tmpDownloadProgress,_tmpSelectedQuality,_tmpSelectedFormat,_tmpDownloadPath,_tmpIsBookmarked,_tmpPriority,_tmpDownloadAllSelected,_tmpSelectedVideoIndices,_tmpFailedVideoUrls,_tmpMetadata);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPlaylistCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlaylistsOlderThanCount(final long timestamp,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM playlists WHERE lastAccessed < ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, timestamp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlaylistStatistics(
      final Continuation<? super PlaylistDao.PlaylistStatistics> $completion) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            COUNT(*) as total,\n"
            + "            COUNT(DISTINCT platform) as platforms,\n"
            + "            COUNT(DISTINCT author) as authors,\n"
            + "            SUM(videoCount) as totalVideos,\n"
            + "            SUM(downloadedCount) as totalDownloaded,\n"
            + "            SUM(failedCount) as totalFailed,\n"
            + "            AVG(downloadProgress) as avgProgress,\n"
            + "            SUM(totalSize) as totalSize,\n"
            + "            SUM(totalDuration) as totalDuration,\n"
            + "            COUNT(CASE WHEN isBookmarked = 1 THEN 1 END) as bookmarked\n"
            + "        FROM playlists\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaylistDao.PlaylistStatistics>() {
      @Override
      @NonNull
      public PlaylistDao.PlaylistStatistics call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTotal = 0;
          final int _cursorIndexOfPlatforms = 1;
          final int _cursorIndexOfAuthors = 2;
          final int _cursorIndexOfTotalVideos = 3;
          final int _cursorIndexOfTotalDownloaded = 4;
          final int _cursorIndexOfTotalFailed = 5;
          final int _cursorIndexOfAvgProgress = 6;
          final int _cursorIndexOfTotalSize = 7;
          final int _cursorIndexOfTotalDuration = 8;
          final int _cursorIndexOfBookmarked = 9;
          final PlaylistDao.PlaylistStatistics _result;
          if (_cursor.moveToFirst()) {
            final int _tmpTotal;
            _tmpTotal = _cursor.getInt(_cursorIndexOfTotal);
            final int _tmpPlatforms;
            _tmpPlatforms = _cursor.getInt(_cursorIndexOfPlatforms);
            final int _tmpAuthors;
            _tmpAuthors = _cursor.getInt(_cursorIndexOfAuthors);
            final int _tmpTotalVideos;
            _tmpTotalVideos = _cursor.getInt(_cursorIndexOfTotalVideos);
            final int _tmpTotalDownloaded;
            _tmpTotalDownloaded = _cursor.getInt(_cursorIndexOfTotalDownloaded);
            final int _tmpTotalFailed;
            _tmpTotalFailed = _cursor.getInt(_cursorIndexOfTotalFailed);
            final Float _tmpAvgProgress;
            if (_cursor.isNull(_cursorIndexOfAvgProgress)) {
              _tmpAvgProgress = null;
            } else {
              _tmpAvgProgress = _cursor.getFloat(_cursorIndexOfAvgProgress);
            }
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpTotalDuration;
            _tmpTotalDuration = _cursor.getLong(_cursorIndexOfTotalDuration);
            final int _tmpBookmarked;
            _tmpBookmarked = _cursor.getInt(_cursorIndexOfBookmarked);
            _result = new PlaylistDao.PlaylistStatistics(_tmpTotal,_tmpPlatforms,_tmpAuthors,_tmpTotalVideos,_tmpTotalDownloaded,_tmpTotalFailed,_tmpAvgProgress,_tmpTotalSize,_tmpTotalDuration,_tmpBookmarked);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlatformStatistics(
      final Continuation<? super List<PlaylistDao.PlatformStatistics>> $completion) {
    final String _sql = "\n"
            + "        SELECT platform, \n"
            + "               COUNT(*) as count, \n"
            + "               SUM(videoCount) as totalVideos,\n"
            + "               SUM(downloadedCount) as totalDownloaded,\n"
            + "               AVG(downloadProgress) as avgProgress,\n"
            + "               SUM(totalSize) as totalSize\n"
            + "        FROM playlists \n"
            + "        GROUP BY platform \n"
            + "        ORDER BY count DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaylistDao.PlatformStatistics>>() {
      @Override
      @NonNull
      public List<PlaylistDao.PlatformStatistics> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPlatform = 0;
          final int _cursorIndexOfCount = 1;
          final int _cursorIndexOfTotalVideos = 2;
          final int _cursorIndexOfTotalDownloaded = 3;
          final int _cursorIndexOfAvgProgress = 4;
          final int _cursorIndexOfTotalSize = 5;
          final List<PlaylistDao.PlatformStatistics> _result = new ArrayList<PlaylistDao.PlatformStatistics>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistDao.PlatformStatistics _item;
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            final int _tmpTotalVideos;
            _tmpTotalVideos = _cursor.getInt(_cursorIndexOfTotalVideos);
            final int _tmpTotalDownloaded;
            _tmpTotalDownloaded = _cursor.getInt(_cursorIndexOfTotalDownloaded);
            final Float _tmpAvgProgress;
            if (_cursor.isNull(_cursorIndexOfAvgProgress)) {
              _tmpAvgProgress = null;
            } else {
              _tmpAvgProgress = _cursor.getFloat(_cursorIndexOfAvgProgress);
            }
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            _item = new PlaylistDao.PlatformStatistics(_tmpPlatform,_tmpCount,_tmpTotalVideos,_tmpTotalDownloaded,_tmpAvgProgress,_tmpTotalSize);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAuthorStatistics(final int limit,
      final Continuation<? super List<PlaylistDao.AuthorStatistics>> $completion) {
    final String _sql = "\n"
            + "        SELECT author, \n"
            + "               COUNT(*) as playlistCount, \n"
            + "               SUM(videoCount) as totalVideos,\n"
            + "               SUM(downloadedCount) as totalDownloaded,\n"
            + "               AVG(downloadProgress) as avgProgress\n"
            + "        FROM playlists \n"
            + "        WHERE author IS NOT NULL\n"
            + "        GROUP BY author \n"
            + "        ORDER BY playlistCount DESC\n"
            + "        LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaylistDao.AuthorStatistics>>() {
      @Override
      @NonNull
      public List<PlaylistDao.AuthorStatistics> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAuthor = 0;
          final int _cursorIndexOfPlaylistCount = 1;
          final int _cursorIndexOfTotalVideos = 2;
          final int _cursorIndexOfTotalDownloaded = 3;
          final int _cursorIndexOfAvgProgress = 4;
          final List<PlaylistDao.AuthorStatistics> _result = new ArrayList<PlaylistDao.AuthorStatistics>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaylistDao.AuthorStatistics _item;
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final int _tmpPlaylistCount;
            _tmpPlaylistCount = _cursor.getInt(_cursorIndexOfPlaylistCount);
            final int _tmpTotalVideos;
            _tmpTotalVideos = _cursor.getInt(_cursorIndexOfTotalVideos);
            final int _tmpTotalDownloaded;
            _tmpTotalDownloaded = _cursor.getInt(_cursorIndexOfTotalDownloaded);
            final Float _tmpAvgProgress;
            if (_cursor.isNull(_cursorIndexOfAvgProgress)) {
              _tmpAvgProgress = null;
            } else {
              _tmpAvgProgress = _cursor.getFloat(_cursorIndexOfAvgProgress);
            }
            _item = new PlaylistDao.AuthorStatistics(_tmpAuthor,_tmpPlaylistCount,_tmpTotalVideos,_tmpTotalDownloaded,_tmpAvgProgress);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getVideoCountForPlaylist(final String playlistId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM playlists WHERE playlistId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, playlistId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalDurationForPlaylist(final String playlistId,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT totalDuration FROM playlists WHERE playlistId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, playlistId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getLong(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSumOfVideoCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(videoCount) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSumOfDownloadedCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(downloadedCount) FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getBookmarkedCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM playlists WHERE isBookmarked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDetailedStatistics(
      final Continuation<? super PlaylistDao.PlaylistStatistics> $completion) {
    return PlaylistDao.DefaultImpls.getDetailedStatistics(PlaylistDao_Impl.this, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
