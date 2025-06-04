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
import com.example.snaptube.database.entities.VideoInfoEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
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
public final class VideoInfoDao_Impl implements VideoInfoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VideoInfoEntity> __insertionAdapterOfVideoInfoEntity;

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<VideoInfoEntity> __deletionAdapterOfVideoInfoEntity;

  private final EntityDeletionOrUpdateAdapter<VideoInfoEntity> __updateAdapterOfVideoInfoEntity;

  private final SharedSQLiteStatement __preparedStmtOfIncrementAccessCount;

  private final SharedSQLiteStatement __preparedStmtOfUpdateValidityStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCacheExpiry;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVideoInfoByUrl;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVideoInfoByPlatform;

  private final SharedSQLiteStatement __preparedStmtOfDeleteInvalidVideoInfo;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldVideoInfo;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVideoInfoOlderThan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExcessVideoInfo;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastAccessed;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExpiredCache;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllVideoInfo;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLeastRecentlyUsed;

  private final SharedSQLiteStatement __preparedStmtOfMarkStaleEntriesInvalid;

  public VideoInfoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVideoInfoEntity = new EntityInsertionAdapter<VideoInfoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `video_info_cache` (`url`,`videoId`,`title`,`description`,`thumbnail`,`duration`,`viewCount`,`likeCount`,`author`,`authorUrl`,`authorThumbnail`,`platform`,`uploadDate`,`isLive`,`ageLimit`,`categories`,`tags`,`language`,`subtitles`,`formats`,`qualityOptions`,`availableFormats`,`hasAudio`,`hasVideo`,`maxQuality`,`minQuality`,`recommendedFormat`,`fileSize`,`bitrate`,`fps`,`resolution`,`aspectRatio`,`audioLanguage`,`videoLanguage`,`chapters`,`webpage`,`extractor`,`extractorKey`,`createdAt`,`updatedAt`,`lastAccessed`,`accessCount`,`cacheExpiryDate`,`isValid`,`metadata`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VideoInfoEntity entity) {
        statement.bindString(1, entity.getUrl());
        statement.bindString(2, entity.getVideoId());
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
        statement.bindLong(6, entity.getDuration());
        statement.bindLong(7, entity.getViewCount());
        statement.bindLong(8, entity.getLikeCount());
        if (entity.getAuthor() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAuthor());
        }
        if (entity.getAuthorUrl() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAuthorUrl());
        }
        if (entity.getAuthorThumbnail() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAuthorThumbnail());
        }
        statement.bindString(12, entity.getPlatform());
        if (entity.getUploadDate() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getUploadDate());
        }
        final int _tmp = entity.isLive() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindLong(15, entity.getAgeLimit());
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getCategories());
        statement.bindString(16, _tmp_1);
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getTags());
        statement.bindString(17, _tmp_2);
        if (entity.getLanguage() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getLanguage());
        }
        final String _tmp_3 = __stringListConverter.fromStringList(entity.getSubtitles());
        statement.bindString(19, _tmp_3);
        final String _tmp_4 = __stringListConverter.fromStringList(entity.getFormats());
        statement.bindString(20, _tmp_4);
        final String _tmp_5 = __stringListConverter.fromStringList(entity.getQualityOptions());
        statement.bindString(21, _tmp_5);
        final String _tmp_6 = __stringListConverter.fromStringList(entity.getAvailableFormats());
        statement.bindString(22, _tmp_6);
        final int _tmp_7 = entity.getHasAudio() ? 1 : 0;
        statement.bindLong(23, _tmp_7);
        final int _tmp_8 = entity.getHasVideo() ? 1 : 0;
        statement.bindLong(24, _tmp_8);
        if (entity.getMaxQuality() == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, entity.getMaxQuality());
        }
        if (entity.getMinQuality() == null) {
          statement.bindNull(26);
        } else {
          statement.bindString(26, entity.getMinQuality());
        }
        if (entity.getRecommendedFormat() == null) {
          statement.bindNull(27);
        } else {
          statement.bindString(27, entity.getRecommendedFormat());
        }
        statement.bindLong(28, entity.getFileSize());
        statement.bindLong(29, entity.getBitrate());
        statement.bindLong(30, entity.getFps());
        if (entity.getResolution() == null) {
          statement.bindNull(31);
        } else {
          statement.bindString(31, entity.getResolution());
        }
        if (entity.getAspectRatio() == null) {
          statement.bindNull(32);
        } else {
          statement.bindString(32, entity.getAspectRatio());
        }
        if (entity.getAudioLanguage() == null) {
          statement.bindNull(33);
        } else {
          statement.bindString(33, entity.getAudioLanguage());
        }
        if (entity.getVideoLanguage() == null) {
          statement.bindNull(34);
        } else {
          statement.bindString(34, entity.getVideoLanguage());
        }
        final String _tmp_9 = __stringListConverter.fromStringList(entity.getChapters());
        statement.bindString(35, _tmp_9);
        if (entity.getWebpage() == null) {
          statement.bindNull(36);
        } else {
          statement.bindString(36, entity.getWebpage());
        }
        if (entity.getExtractor() == null) {
          statement.bindNull(37);
        } else {
          statement.bindString(37, entity.getExtractor());
        }
        if (entity.getExtractorKey() == null) {
          statement.bindNull(38);
        } else {
          statement.bindString(38, entity.getExtractorKey());
        }
        final Long _tmp_10 = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_10 == null) {
          statement.bindNull(39);
        } else {
          statement.bindLong(39, _tmp_10);
        }
        final Long _tmp_11 = __dateConverter.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_11 == null) {
          statement.bindNull(40);
        } else {
          statement.bindLong(40, _tmp_11);
        }
        final Long _tmp_12 = __dateConverter.dateToTimestamp(entity.getLastAccessed());
        if (_tmp_12 == null) {
          statement.bindNull(41);
        } else {
          statement.bindLong(41, _tmp_12);
        }
        statement.bindLong(42, entity.getAccessCount());
        final Long _tmp_13 = __dateConverter.dateToTimestamp(entity.getCacheExpiryDate());
        if (_tmp_13 == null) {
          statement.bindNull(43);
        } else {
          statement.bindLong(43, _tmp_13);
        }
        final int _tmp_14 = entity.isValid() ? 1 : 0;
        statement.bindLong(44, _tmp_14);
        if (entity.getMetadata() == null) {
          statement.bindNull(45);
        } else {
          statement.bindString(45, entity.getMetadata());
        }
      }
    };
    this.__deletionAdapterOfVideoInfoEntity = new EntityDeletionOrUpdateAdapter<VideoInfoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `video_info_cache` WHERE `url` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VideoInfoEntity entity) {
        statement.bindString(1, entity.getUrl());
      }
    };
    this.__updateAdapterOfVideoInfoEntity = new EntityDeletionOrUpdateAdapter<VideoInfoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `video_info_cache` SET `url` = ?,`videoId` = ?,`title` = ?,`description` = ?,`thumbnail` = ?,`duration` = ?,`viewCount` = ?,`likeCount` = ?,`author` = ?,`authorUrl` = ?,`authorThumbnail` = ?,`platform` = ?,`uploadDate` = ?,`isLive` = ?,`ageLimit` = ?,`categories` = ?,`tags` = ?,`language` = ?,`subtitles` = ?,`formats` = ?,`qualityOptions` = ?,`availableFormats` = ?,`hasAudio` = ?,`hasVideo` = ?,`maxQuality` = ?,`minQuality` = ?,`recommendedFormat` = ?,`fileSize` = ?,`bitrate` = ?,`fps` = ?,`resolution` = ?,`aspectRatio` = ?,`audioLanguage` = ?,`videoLanguage` = ?,`chapters` = ?,`webpage` = ?,`extractor` = ?,`extractorKey` = ?,`createdAt` = ?,`updatedAt` = ?,`lastAccessed` = ?,`accessCount` = ?,`cacheExpiryDate` = ?,`isValid` = ?,`metadata` = ? WHERE `url` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VideoInfoEntity entity) {
        statement.bindString(1, entity.getUrl());
        statement.bindString(2, entity.getVideoId());
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
        statement.bindLong(6, entity.getDuration());
        statement.bindLong(7, entity.getViewCount());
        statement.bindLong(8, entity.getLikeCount());
        if (entity.getAuthor() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAuthor());
        }
        if (entity.getAuthorUrl() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAuthorUrl());
        }
        if (entity.getAuthorThumbnail() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAuthorThumbnail());
        }
        statement.bindString(12, entity.getPlatform());
        if (entity.getUploadDate() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getUploadDate());
        }
        final int _tmp = entity.isLive() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindLong(15, entity.getAgeLimit());
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getCategories());
        statement.bindString(16, _tmp_1);
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getTags());
        statement.bindString(17, _tmp_2);
        if (entity.getLanguage() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getLanguage());
        }
        final String _tmp_3 = __stringListConverter.fromStringList(entity.getSubtitles());
        statement.bindString(19, _tmp_3);
        final String _tmp_4 = __stringListConverter.fromStringList(entity.getFormats());
        statement.bindString(20, _tmp_4);
        final String _tmp_5 = __stringListConverter.fromStringList(entity.getQualityOptions());
        statement.bindString(21, _tmp_5);
        final String _tmp_6 = __stringListConverter.fromStringList(entity.getAvailableFormats());
        statement.bindString(22, _tmp_6);
        final int _tmp_7 = entity.getHasAudio() ? 1 : 0;
        statement.bindLong(23, _tmp_7);
        final int _tmp_8 = entity.getHasVideo() ? 1 : 0;
        statement.bindLong(24, _tmp_8);
        if (entity.getMaxQuality() == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, entity.getMaxQuality());
        }
        if (entity.getMinQuality() == null) {
          statement.bindNull(26);
        } else {
          statement.bindString(26, entity.getMinQuality());
        }
        if (entity.getRecommendedFormat() == null) {
          statement.bindNull(27);
        } else {
          statement.bindString(27, entity.getRecommendedFormat());
        }
        statement.bindLong(28, entity.getFileSize());
        statement.bindLong(29, entity.getBitrate());
        statement.bindLong(30, entity.getFps());
        if (entity.getResolution() == null) {
          statement.bindNull(31);
        } else {
          statement.bindString(31, entity.getResolution());
        }
        if (entity.getAspectRatio() == null) {
          statement.bindNull(32);
        } else {
          statement.bindString(32, entity.getAspectRatio());
        }
        if (entity.getAudioLanguage() == null) {
          statement.bindNull(33);
        } else {
          statement.bindString(33, entity.getAudioLanguage());
        }
        if (entity.getVideoLanguage() == null) {
          statement.bindNull(34);
        } else {
          statement.bindString(34, entity.getVideoLanguage());
        }
        final String _tmp_9 = __stringListConverter.fromStringList(entity.getChapters());
        statement.bindString(35, _tmp_9);
        if (entity.getWebpage() == null) {
          statement.bindNull(36);
        } else {
          statement.bindString(36, entity.getWebpage());
        }
        if (entity.getExtractor() == null) {
          statement.bindNull(37);
        } else {
          statement.bindString(37, entity.getExtractor());
        }
        if (entity.getExtractorKey() == null) {
          statement.bindNull(38);
        } else {
          statement.bindString(38, entity.getExtractorKey());
        }
        final Long _tmp_10 = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_10 == null) {
          statement.bindNull(39);
        } else {
          statement.bindLong(39, _tmp_10);
        }
        final Long _tmp_11 = __dateConverter.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_11 == null) {
          statement.bindNull(40);
        } else {
          statement.bindLong(40, _tmp_11);
        }
        final Long _tmp_12 = __dateConverter.dateToTimestamp(entity.getLastAccessed());
        if (_tmp_12 == null) {
          statement.bindNull(41);
        } else {
          statement.bindLong(41, _tmp_12);
        }
        statement.bindLong(42, entity.getAccessCount());
        final Long _tmp_13 = __dateConverter.dateToTimestamp(entity.getCacheExpiryDate());
        if (_tmp_13 == null) {
          statement.bindNull(43);
        } else {
          statement.bindLong(43, _tmp_13);
        }
        final int _tmp_14 = entity.isValid() ? 1 : 0;
        statement.bindLong(44, _tmp_14);
        if (entity.getMetadata() == null) {
          statement.bindNull(45);
        } else {
          statement.bindString(45, entity.getMetadata());
        }
        statement.bindString(46, entity.getUrl());
      }
    };
    this.__preparedStmtOfIncrementAccessCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE video_info_cache SET accessCount = accessCount + 1, lastAccessed = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateValidityStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE video_info_cache SET isValid = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCacheExpiry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE video_info_cache SET cacheExpiryDate = ? WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteVideoInfoByUrl = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE url = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteVideoInfoByPlatform = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE platform = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteInvalidVideoInfo = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE isValid = 0";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldVideoInfo = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE updatedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteVideoInfoOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE updatedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteExcessVideoInfo = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE url NOT IN (SELECT url FROM video_info_cache ORDER BY lastAccessed DESC LIMIT ?)";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastAccessed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE video_info_cache SET lastAccessed = ? WHERE videoId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteExpiredCache = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE createdAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllVideoInfo = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteLeastRecentlyUsed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_info_cache WHERE url IN (SELECT url FROM video_info_cache ORDER BY lastAccessed ASC LIMIT ?)";
        return _query;
      }
    };
    this.__preparedStmtOfMarkStaleEntriesInvalid = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE video_info_cache SET isValid = 0 WHERE updatedAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertVideoInfo(final VideoInfoEntity videoInfo,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfVideoInfoEntity.insertAndReturnId(videoInfo);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertVideoInfoList(final List<VideoInfoEntity> videoInfoList,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfVideoInfoEntity.insertAndReturnIdsList(videoInfoList);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVideoInfo(final VideoInfoEntity videoInfo,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __deletionAdapterOfVideoInfoEntity.handle(videoInfo);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateVideoInfo(final VideoInfoEntity videoInfo,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfVideoInfoEntity.handle(videoInfo);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object cleanupCache(final int maxEntries, final long maxAge,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> VideoInfoDao.DefaultImpls.cleanupCache(VideoInfoDao_Impl.this, maxEntries, maxAge, __cont), $completion);
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
  public Object updateValidityStatus(final String url, final boolean isValid,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateValidityStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isValid ? 1 : 0;
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
          __preparedStmtOfUpdateValidityStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCacheExpiry(final String url, final Date expiryDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCacheExpiry.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(expiryDate);
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
          __preparedStmtOfUpdateCacheExpiry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVideoInfoByUrl(final String url,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVideoInfoByUrl.acquire();
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
          __preparedStmtOfDeleteVideoInfoByUrl.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVideoInfoByPlatform(final String platform,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVideoInfoByPlatform.acquire();
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
          __preparedStmtOfDeleteVideoInfoByPlatform.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteInvalidVideoInfo(final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteInvalidVideoInfo.acquire();
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
          __preparedStmtOfDeleteInvalidVideoInfo.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldVideoInfo(final Date beforeDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldVideoInfo.acquire();
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
          __preparedStmtOfDeleteOldVideoInfo.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVideoInfoOlderThan(final long timestamp,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVideoInfoOlderThan.acquire();
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
          __preparedStmtOfDeleteVideoInfoOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExcessVideoInfo(final int maxCount,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExcessVideoInfo.acquire();
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
          __preparedStmtOfDeleteExcessVideoInfo.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastAccessed(final String videoId, final Date timestamp,
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
        _stmt.bindString(_argIndex, videoId);
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
  public Object deleteExpiredCache(final Date expiryDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExpiredCache.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(expiryDate);
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
          __preparedStmtOfDeleteExpiredCache.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllVideoInfo(final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllVideoInfo.acquire();
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
          __preparedStmtOfDeleteAllVideoInfo.release(_stmt);
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
  public Object markStaleEntriesInvalid(final Date staleDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkStaleEntriesInvalid.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(staleDate);
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
          __preparedStmtOfMarkStaleEntriesInvalid.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getVideoInfoByUrl(final String url,
      final Continuation<? super VideoInfoEntity> $completion) {
    final String _sql = "SELECT * FROM video_info_cache WHERE url = ? AND isValid = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, url);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VideoInfoEntity>() {
      @Override
      @Nullable
      public VideoInfoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final VideoInfoEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _result = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Object getVideoInfoById(final String videoId,
      final Continuation<? super VideoInfoEntity> $completion) {
    final String _sql = "SELECT * FROM video_info_cache WHERE videoId = ? AND isValid = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, videoId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VideoInfoEntity>() {
      @Override
      @Nullable
      public VideoInfoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final VideoInfoEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _result = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Flow<List<VideoInfoEntity>> getVideoInfoByPlatform(final String platform) {
    final String _sql = "SELECT * FROM video_info_cache WHERE platform = ? AND isValid = 1 ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, platform);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_info_cache"}, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Object getRecentVideoInfo(final int limit,
      final Continuation<? super List<VideoInfoEntity>> $completion) {
    final String _sql = "SELECT * FROM video_info_cache WHERE isValid = 1 ORDER BY lastAccessed DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Object getPopularVideoInfo(final int minCount, final int limit,
      final Continuation<? super List<VideoInfoEntity>> $completion) {
    final String _sql = "SELECT * FROM video_info_cache WHERE accessCount >= ? AND isValid = 1 ORDER BY accessCount DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, minCount);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Flow<List<VideoInfoEntity>> searchVideoInfo(final String query) {
    final String _sql = "SELECT * FROM video_info_cache WHERE title LIKE '%' || ? || '%' OR author LIKE '%' || ? || '%' AND isValid = 1 ORDER BY accessCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_info_cache"}, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Flow<List<VideoInfoEntity>> getVideoInfoByAuthor(final String author) {
    final String _sql = "SELECT * FROM video_info_cache WHERE author = ? AND isValid = 1 ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, author);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_info_cache"}, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Flow<List<VideoInfoEntity>> getVideoInfoByDurationRange(final long minDuration,
      final long maxDuration) {
    final String _sql = "SELECT * FROM video_info_cache WHERE duration BETWEEN ? AND ? AND isValid = 1 ORDER BY lastAccessed DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, minDuration);
    _argIndex = 2;
    _statement.bindLong(_argIndex, maxDuration);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_info_cache"}, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Object getExpiredVideoInfo(final Date beforeDate,
      final Continuation<? super List<VideoInfoEntity>> $completion) {
    final String _sql = "SELECT * FROM video_info_cache WHERE updatedAt < ? AND isValid = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __dateConverter.dateToTimestamp(beforeDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_1 != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpTags;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_3);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpFormats;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpQualityOptions;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpAvailableFormats;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpHasAudio;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_8 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_9 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_10;
            _tmp_10 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_10);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_11;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_12 = __dateConverter.fromTimestamp(_tmp_11);
            if (_tmp_12 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_12;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_13;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_13 = null;
            } else {
              _tmp_13 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_14 = __dateConverter.fromTimestamp(_tmp_13);
            if (_tmp_14 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_14;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_16 = __dateConverter.fromTimestamp(_tmp_15);
            if (_tmp_16 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_16;
            }
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_17;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_17 = null;
            } else {
              _tmp_17 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_17);
            final boolean _tmpIsValid;
            final int _tmp_18;
            _tmp_18 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_18 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Object getExpiredCache(final Date currentDate,
      final Continuation<? super List<VideoInfoEntity>> $completion) {
    final String _sql = "SELECT * FROM video_info_cache WHERE cacheExpiryDate < ? AND isValid = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __dateConverter.dateToTimestamp(currentDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp_1 != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpTags;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_3);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpFormats;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpQualityOptions;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpAvailableFormats;
            final String _tmp_7;
            _tmp_7 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpHasAudio;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_8 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_9 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_10;
            _tmp_10 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_10);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_11;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_12 = __dateConverter.fromTimestamp(_tmp_11);
            if (_tmp_12 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_12;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_13;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_13 = null;
            } else {
              _tmp_13 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_14 = __dateConverter.fromTimestamp(_tmp_13);
            if (_tmp_14 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_14;
            }
            final Date _tmpLastAccessed;
            final Long _tmp_15;
            if (_cursor.isNull(_cursorIndexOfLastAccessed)) {
              _tmp_15 = null;
            } else {
              _tmp_15 = _cursor.getLong(_cursorIndexOfLastAccessed);
            }
            final Date _tmp_16 = __dateConverter.fromTimestamp(_tmp_15);
            if (_tmp_16 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastAccessed = _tmp_16;
            }
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_17;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_17 = null;
            } else {
              _tmp_17 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_17);
            final boolean _tmpIsValid;
            final int _tmp_18;
            _tmp_18 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_18 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Object getVideoCountByPlatform(final String platform,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM video_info_cache WHERE platform = ? AND isValid = 1";
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
    final String _sql = "SELECT COUNT(*) FROM video_info_cache WHERE isValid = 1";
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
  public Object getUniquePlatformCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(DISTINCT platform) FROM video_info_cache";
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
  public Object getTotalCacheSize(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(fileSize) FROM video_info_cache WHERE isValid = 1";
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
  public Object getAllPlatforms(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT platform FROM video_info_cache WHERE isValid = 1 ORDER BY platform ASC";
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
    final String _sql = "SELECT DISTINCT author FROM video_info_cache WHERE author IS NOT NULL AND isValid = 1 ORDER BY author ASC";
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
  public Object getAllLanguages(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT language FROM video_info_cache WHERE language IS NOT NULL AND isValid = 1 ORDER BY language ASC";
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
  public Object getFrequentVideoInfo(final int minCount, final int limit,
      final Continuation<? super List<VideoInfoEntity>> $completion) {
    final String _sql = "SELECT * FROM video_info_cache WHERE accessCount >= ? AND isValid = 1 ORDER BY accessCount DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, minCount);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VideoInfoEntity>>() {
      @Override
      @NonNull
      public List<VideoInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfVideoId = CursorUtil.getColumnIndexOrThrow(_cursor, "videoId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "authorUrl");
          final int _cursorIndexOfAuthorThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "authorThumbnail");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUploadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadDate");
          final int _cursorIndexOfIsLive = CursorUtil.getColumnIndexOrThrow(_cursor, "isLive");
          final int _cursorIndexOfAgeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "ageLimit");
          final int _cursorIndexOfCategories = CursorUtil.getColumnIndexOrThrow(_cursor, "categories");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfSubtitles = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitles");
          final int _cursorIndexOfFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "formats");
          final int _cursorIndexOfQualityOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityOptions");
          final int _cursorIndexOfAvailableFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFormats");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final int _cursorIndexOfHasVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "hasVideo");
          final int _cursorIndexOfMaxQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "maxQuality");
          final int _cursorIndexOfMinQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "minQuality");
          final int _cursorIndexOfRecommendedFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendedFormat");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitrate");
          final int _cursorIndexOfFps = CursorUtil.getColumnIndexOrThrow(_cursor, "fps");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final int _cursorIndexOfAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "aspectRatio");
          final int _cursorIndexOfAudioLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "audioLanguage");
          final int _cursorIndexOfVideoLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "videoLanguage");
          final int _cursorIndexOfChapters = CursorUtil.getColumnIndexOrThrow(_cursor, "chapters");
          final int _cursorIndexOfWebpage = CursorUtil.getColumnIndexOrThrow(_cursor, "webpage");
          final int _cursorIndexOfExtractor = CursorUtil.getColumnIndexOrThrow(_cursor, "extractor");
          final int _cursorIndexOfExtractorKey = CursorUtil.getColumnIndexOrThrow(_cursor, "extractorKey");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastAccessed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessed");
          final int _cursorIndexOfAccessCount = CursorUtil.getColumnIndexOrThrow(_cursor, "accessCount");
          final int _cursorIndexOfCacheExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheExpiryDate");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final List<VideoInfoEntity> _result = new ArrayList<VideoInfoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoEntity _item;
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpVideoId;
            _tmpVideoId = _cursor.getString(_cursorIndexOfVideoId);
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
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final long _tmpViewCount;
            _tmpViewCount = _cursor.getLong(_cursorIndexOfViewCount);
            final long _tmpLikeCount;
            _tmpLikeCount = _cursor.getLong(_cursorIndexOfLikeCount);
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
            final String _tmpUploadDate;
            if (_cursor.isNull(_cursorIndexOfUploadDate)) {
              _tmpUploadDate = null;
            } else {
              _tmpUploadDate = _cursor.getString(_cursorIndexOfUploadDate);
            }
            final boolean _tmpIsLive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLive);
            _tmpIsLive = _tmp != 0;
            final int _tmpAgeLimit;
            _tmpAgeLimit = _cursor.getInt(_cursorIndexOfAgeLimit);
            final List<String> _tmpCategories;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategories);
            _tmpCategories = __stringListConverter.toStringList(_tmp_1);
            final List<String> _tmpTags;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.toStringList(_tmp_2);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final List<String> _tmpSubtitles;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSubtitles);
            _tmpSubtitles = __stringListConverter.toStringList(_tmp_3);
            final List<String> _tmpFormats;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFormats);
            _tmpFormats = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpQualityOptions;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfQualityOptions);
            _tmpQualityOptions = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpAvailableFormats;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAvailableFormats);
            _tmpAvailableFormats = __stringListConverter.toStringList(_tmp_6);
            final boolean _tmpHasAudio;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_7 != 0;
            final boolean _tmpHasVideo;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfHasVideo);
            _tmpHasVideo = _tmp_8 != 0;
            final String _tmpMaxQuality;
            if (_cursor.isNull(_cursorIndexOfMaxQuality)) {
              _tmpMaxQuality = null;
            } else {
              _tmpMaxQuality = _cursor.getString(_cursorIndexOfMaxQuality);
            }
            final String _tmpMinQuality;
            if (_cursor.isNull(_cursorIndexOfMinQuality)) {
              _tmpMinQuality = null;
            } else {
              _tmpMinQuality = _cursor.getString(_cursorIndexOfMinQuality);
            }
            final String _tmpRecommendedFormat;
            if (_cursor.isNull(_cursorIndexOfRecommendedFormat)) {
              _tmpRecommendedFormat = null;
            } else {
              _tmpRecommendedFormat = _cursor.getString(_cursorIndexOfRecommendedFormat);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpBitrate;
            _tmpBitrate = _cursor.getInt(_cursorIndexOfBitrate);
            final int _tmpFps;
            _tmpFps = _cursor.getInt(_cursorIndexOfFps);
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            final String _tmpAspectRatio;
            if (_cursor.isNull(_cursorIndexOfAspectRatio)) {
              _tmpAspectRatio = null;
            } else {
              _tmpAspectRatio = _cursor.getString(_cursorIndexOfAspectRatio);
            }
            final String _tmpAudioLanguage;
            if (_cursor.isNull(_cursorIndexOfAudioLanguage)) {
              _tmpAudioLanguage = null;
            } else {
              _tmpAudioLanguage = _cursor.getString(_cursorIndexOfAudioLanguage);
            }
            final String _tmpVideoLanguage;
            if (_cursor.isNull(_cursorIndexOfVideoLanguage)) {
              _tmpVideoLanguage = null;
            } else {
              _tmpVideoLanguage = _cursor.getString(_cursorIndexOfVideoLanguage);
            }
            final List<String> _tmpChapters;
            final String _tmp_9;
            _tmp_9 = _cursor.getString(_cursorIndexOfChapters);
            _tmpChapters = __stringListConverter.toStringList(_tmp_9);
            final String _tmpWebpage;
            if (_cursor.isNull(_cursorIndexOfWebpage)) {
              _tmpWebpage = null;
            } else {
              _tmpWebpage = _cursor.getString(_cursorIndexOfWebpage);
            }
            final String _tmpExtractor;
            if (_cursor.isNull(_cursorIndexOfExtractor)) {
              _tmpExtractor = null;
            } else {
              _tmpExtractor = _cursor.getString(_cursorIndexOfExtractor);
            }
            final String _tmpExtractorKey;
            if (_cursor.isNull(_cursorIndexOfExtractorKey)) {
              _tmpExtractorKey = null;
            } else {
              _tmpExtractorKey = _cursor.getString(_cursorIndexOfExtractorKey);
            }
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
            final int _tmpAccessCount;
            _tmpAccessCount = _cursor.getInt(_cursorIndexOfAccessCount);
            final Date _tmpCacheExpiryDate;
            final Long _tmp_16;
            if (_cursor.isNull(_cursorIndexOfCacheExpiryDate)) {
              _tmp_16 = null;
            } else {
              _tmp_16 = _cursor.getLong(_cursorIndexOfCacheExpiryDate);
            }
            _tmpCacheExpiryDate = __dateConverter.fromTimestamp(_tmp_16);
            final boolean _tmpIsValid;
            final int _tmp_17;
            _tmp_17 = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp_17 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            _item = new VideoInfoEntity(_tmpUrl,_tmpVideoId,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpViewCount,_tmpLikeCount,_tmpAuthor,_tmpAuthorUrl,_tmpAuthorThumbnail,_tmpPlatform,_tmpUploadDate,_tmpIsLive,_tmpAgeLimit,_tmpCategories,_tmpTags,_tmpLanguage,_tmpSubtitles,_tmpFormats,_tmpQualityOptions,_tmpAvailableFormats,_tmpHasAudio,_tmpHasVideo,_tmpMaxQuality,_tmpMinQuality,_tmpRecommendedFormat,_tmpFileSize,_tmpBitrate,_tmpFps,_tmpResolution,_tmpAspectRatio,_tmpAudioLanguage,_tmpVideoLanguage,_tmpChapters,_tmpWebpage,_tmpExtractor,_tmpExtractorKey,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastAccessed,_tmpAccessCount,_tmpCacheExpiryDate,_tmpIsValid,_tmpMetadata);
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
  public Object getVideoInfoCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM video_info_cache WHERE isValid = 1";
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
  public Object getTotalVideoInfoSize(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(fileSize) FROM video_info_cache WHERE isValid = 1";
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
  public Object getVideoInfoOlderThanCount(final long timestamp,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM video_info_cache WHERE updatedAt < ? AND isValid = 1";
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
  public Object getCacheEntryCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM video_info_cache";
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
  public Object getCacheStatisticsAdvanced(
      final Continuation<? super VideoInfoDao.CacheStatistics> $completion) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            COUNT(*) as total,\n"
            + "            COUNT(DISTINCT platform) as platforms,\n"
            + "            COUNT(DISTINCT author) as authors,\n"
            + "            AVG(duration) as avgDuration,\n"
            + "            SUM(fileSize) as totalSize,\n"
            + "            MAX(accessCount) as maxAccess,\n"
            + "            AVG(accessCount) as avgAccess\n"
            + "        FROM video_info_cache WHERE isValid = 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VideoInfoDao.CacheStatistics>() {
      @Override
      @NonNull
      public VideoInfoDao.CacheStatistics call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTotal = 0;
          final int _cursorIndexOfPlatforms = 1;
          final int _cursorIndexOfAuthors = 2;
          final int _cursorIndexOfAvgDuration = 3;
          final int _cursorIndexOfTotalSize = 4;
          final int _cursorIndexOfMaxAccess = 5;
          final int _cursorIndexOfAvgAccess = 6;
          final VideoInfoDao.CacheStatistics _result;
          if (_cursor.moveToFirst()) {
            final int _tmpTotal;
            _tmpTotal = _cursor.getInt(_cursorIndexOfTotal);
            final int _tmpPlatforms;
            _tmpPlatforms = _cursor.getInt(_cursorIndexOfPlatforms);
            final int _tmpAuthors;
            _tmpAuthors = _cursor.getInt(_cursorIndexOfAuthors);
            final Double _tmpAvgDuration;
            if (_cursor.isNull(_cursorIndexOfAvgDuration)) {
              _tmpAvgDuration = null;
            } else {
              _tmpAvgDuration = _cursor.getDouble(_cursorIndexOfAvgDuration);
            }
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final int _tmpMaxAccess;
            _tmpMaxAccess = _cursor.getInt(_cursorIndexOfMaxAccess);
            final Double _tmpAvgAccess;
            if (_cursor.isNull(_cursorIndexOfAvgAccess)) {
              _tmpAvgAccess = null;
            } else {
              _tmpAvgAccess = _cursor.getDouble(_cursorIndexOfAvgAccess);
            }
            _result = new VideoInfoDao.CacheStatistics(_tmpTotal,_tmpPlatforms,_tmpAuthors,_tmpAvgDuration,_tmpTotalSize,_tmpMaxAccess,_tmpAvgAccess);
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
      final Continuation<? super List<VideoInfoDao.PlatformStatistics>> $completion) {
    final String _sql = "\n"
            + "        SELECT platform, COUNT(*) as count, AVG(duration) as avgDuration, SUM(fileSize) as totalSize\n"
            + "        FROM video_info_cache \n"
            + "        WHERE isValid = 1 \n"
            + "        GROUP BY platform \n"
            + "        ORDER BY count DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VideoInfoDao.PlatformStatistics>>() {
      @Override
      @NonNull
      public List<VideoInfoDao.PlatformStatistics> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPlatform = 0;
          final int _cursorIndexOfCount = 1;
          final int _cursorIndexOfAvgDuration = 2;
          final int _cursorIndexOfTotalSize = 3;
          final List<VideoInfoDao.PlatformStatistics> _result = new ArrayList<VideoInfoDao.PlatformStatistics>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VideoInfoDao.PlatformStatistics _item;
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            final Double _tmpAvgDuration;
            if (_cursor.isNull(_cursorIndexOfAvgDuration)) {
              _tmpAvgDuration = null;
            } else {
              _tmpAvgDuration = _cursor.getDouble(_cursorIndexOfAvgDuration);
            }
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            _item = new VideoInfoDao.PlatformStatistics(_tmpPlatform,_tmpCount,_tmpAvgDuration,_tmpTotalSize);
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
  public Object getCacheStatistics(
      final Continuation<? super VideoInfoDao.CacheStatistics> $completion) {
    return VideoInfoDao.DefaultImpls.getCacheStatistics(VideoInfoDao_Impl.this, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
