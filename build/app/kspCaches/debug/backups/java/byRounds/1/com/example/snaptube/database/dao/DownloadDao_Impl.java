package com.example.snaptube.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.snaptube.database.converters.DateConverter;
import com.example.snaptube.database.converters.DownloadStatusConverter;
import com.example.snaptube.database.entities.DownloadEntity;
import com.example.snaptube.download.DownloadStatus;
import com.example.snaptube.repository.DownloadRepository;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DownloadDao_Impl implements DownloadDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DownloadEntity> __insertionAdapterOfDownloadEntity;

  private final DownloadStatusConverter __downloadStatusConverter = new DownloadStatusConverter();

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<DownloadEntity> __deletionAdapterOfDownloadEntity;

  private final EntityDeletionOrUpdateAdapter<DownloadEntity> __updateAdapterOfDownloadEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadProgress;

  private final SharedSQLiteStatement __preparedStmtOfMarkDownloadFailed;

  private final SharedSQLiteStatement __preparedStmtOfMarkDownloadCompleted;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadWorkerId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadPriority;

  private final SharedSQLiteStatement __preparedStmtOfHideDownload;

  private final SharedSQLiteStatement __preparedStmtOfSetDownloadVisibility;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDownloadById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDownloadsByStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteHiddenDownloads;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldCompletedDownloads;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllDownloads;

  private final SharedSQLiteStatement __preparedStmtOfCleanupStaleDownloads;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFailedDownloadsOlderThan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCompletedDownloadsOlderThan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFailedDownloads;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadFilePath;

  public DownloadDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDownloadEntity = new EntityInsertionAdapter<DownloadEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `downloads` (`id`,`url`,`title`,`description`,`thumbnail`,`duration`,`author`,`platform`,`formatId`,`qualityLabel`,`audioCodec`,`videoCodec`,`audioOnly`,`videoOnly`,`fileExtension`,`estimatedSize`,`outputPath`,`filename`,`status`,`progress`,`downloadedBytes`,`totalBytes`,`speed`,`eta`,`error`,`retryCount`,`maxRetries`,`createdAt`,`updatedAt`,`completedAt`,`isVisible`,`priority`,`workerId`,`metadata`,`downloadPath`,`filePath`,`errorMessage`,`quality`,`extension`,`totalSize`,`startedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DownloadEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUrl());
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
        if (entity.getAuthor() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAuthor());
        }
        statement.bindString(8, entity.getPlatform());
        statement.bindString(9, entity.getFormatId());
        statement.bindString(10, entity.getQualityLabel());
        if (entity.getAudioCodec() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAudioCodec());
        }
        if (entity.getVideoCodec() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getVideoCodec());
        }
        final int _tmp = entity.getAudioOnly() ? 1 : 0;
        statement.bindLong(13, _tmp);
        final int _tmp_1 = entity.getVideoOnly() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        statement.bindString(15, entity.getFileExtension());
        statement.bindLong(16, entity.getEstimatedSize());
        statement.bindString(17, entity.getOutputPath());
        statement.bindString(18, entity.getFilename());
        final String _tmp_2 = __downloadStatusConverter.fromDownloadStatus(entity.getStatus());
        statement.bindString(19, _tmp_2);
        statement.bindDouble(20, entity.getProgress());
        statement.bindLong(21, entity.getDownloadedBytes());
        statement.bindLong(22, entity.getTotalBytes());
        statement.bindLong(23, entity.getSpeed());
        statement.bindLong(24, entity.getEta());
        if (entity.getError() == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, entity.getError());
        }
        statement.bindLong(26, entity.getRetryCount());
        statement.bindLong(27, entity.getMaxRetries());
        final Long _tmp_3 = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(28);
        } else {
          statement.bindLong(28, _tmp_3);
        }
        final Long _tmp_4 = __dateConverter.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(29);
        } else {
          statement.bindLong(29, _tmp_4);
        }
        final Long _tmp_5 = __dateConverter.dateToTimestamp(entity.getCompletedAt());
        if (_tmp_5 == null) {
          statement.bindNull(30);
        } else {
          statement.bindLong(30, _tmp_5);
        }
        final int _tmp_6 = entity.isVisible() ? 1 : 0;
        statement.bindLong(31, _tmp_6);
        statement.bindLong(32, entity.getPriority());
        if (entity.getWorkerId() == null) {
          statement.bindNull(33);
        } else {
          statement.bindString(33, entity.getWorkerId());
        }
        if (entity.getMetadata() == null) {
          statement.bindNull(34);
        } else {
          statement.bindString(34, entity.getMetadata());
        }
        statement.bindString(35, entity.getDownloadPath());
        statement.bindString(36, entity.getFilePath());
        statement.bindString(37, entity.getErrorMessage());
        statement.bindString(38, entity.getQuality());
        statement.bindString(39, entity.getExtension());
        statement.bindLong(40, entity.getTotalSize());
        statement.bindLong(41, entity.getStartedAt());
      }
    };
    this.__deletionAdapterOfDownloadEntity = new EntityDeletionOrUpdateAdapter<DownloadEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `downloads` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DownloadEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfDownloadEntity = new EntityDeletionOrUpdateAdapter<DownloadEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `downloads` SET `id` = ?,`url` = ?,`title` = ?,`description` = ?,`thumbnail` = ?,`duration` = ?,`author` = ?,`platform` = ?,`formatId` = ?,`qualityLabel` = ?,`audioCodec` = ?,`videoCodec` = ?,`audioOnly` = ?,`videoOnly` = ?,`fileExtension` = ?,`estimatedSize` = ?,`outputPath` = ?,`filename` = ?,`status` = ?,`progress` = ?,`downloadedBytes` = ?,`totalBytes` = ?,`speed` = ?,`eta` = ?,`error` = ?,`retryCount` = ?,`maxRetries` = ?,`createdAt` = ?,`updatedAt` = ?,`completedAt` = ?,`isVisible` = ?,`priority` = ?,`workerId` = ?,`metadata` = ?,`downloadPath` = ?,`filePath` = ?,`errorMessage` = ?,`quality` = ?,`extension` = ?,`totalSize` = ?,`startedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DownloadEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUrl());
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
        if (entity.getAuthor() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAuthor());
        }
        statement.bindString(8, entity.getPlatform());
        statement.bindString(9, entity.getFormatId());
        statement.bindString(10, entity.getQualityLabel());
        if (entity.getAudioCodec() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAudioCodec());
        }
        if (entity.getVideoCodec() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getVideoCodec());
        }
        final int _tmp = entity.getAudioOnly() ? 1 : 0;
        statement.bindLong(13, _tmp);
        final int _tmp_1 = entity.getVideoOnly() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        statement.bindString(15, entity.getFileExtension());
        statement.bindLong(16, entity.getEstimatedSize());
        statement.bindString(17, entity.getOutputPath());
        statement.bindString(18, entity.getFilename());
        final String _tmp_2 = __downloadStatusConverter.fromDownloadStatus(entity.getStatus());
        statement.bindString(19, _tmp_2);
        statement.bindDouble(20, entity.getProgress());
        statement.bindLong(21, entity.getDownloadedBytes());
        statement.bindLong(22, entity.getTotalBytes());
        statement.bindLong(23, entity.getSpeed());
        statement.bindLong(24, entity.getEta());
        if (entity.getError() == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, entity.getError());
        }
        statement.bindLong(26, entity.getRetryCount());
        statement.bindLong(27, entity.getMaxRetries());
        final Long _tmp_3 = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(28);
        } else {
          statement.bindLong(28, _tmp_3);
        }
        final Long _tmp_4 = __dateConverter.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(29);
        } else {
          statement.bindLong(29, _tmp_4);
        }
        final Long _tmp_5 = __dateConverter.dateToTimestamp(entity.getCompletedAt());
        if (_tmp_5 == null) {
          statement.bindNull(30);
        } else {
          statement.bindLong(30, _tmp_5);
        }
        final int _tmp_6 = entity.isVisible() ? 1 : 0;
        statement.bindLong(31, _tmp_6);
        statement.bindLong(32, entity.getPriority());
        if (entity.getWorkerId() == null) {
          statement.bindNull(33);
        } else {
          statement.bindString(33, entity.getWorkerId());
        }
        if (entity.getMetadata() == null) {
          statement.bindNull(34);
        } else {
          statement.bindString(34, entity.getMetadata());
        }
        statement.bindString(35, entity.getDownloadPath());
        statement.bindString(36, entity.getFilePath());
        statement.bindString(37, entity.getErrorMessage());
        statement.bindString(38, entity.getQuality());
        statement.bindString(39, entity.getExtension());
        statement.bindLong(40, entity.getTotalSize());
        statement.bindLong(41, entity.getStartedAt());
        statement.bindString(42, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateDownloadStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET status = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadProgress = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET progress = ?, downloadedBytes = ?, speed = ?, eta = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkDownloadFailed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET status = ?, error = ?, retryCount = retryCount + 1, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkDownloadCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET status = ?, completedAt = ?, progress = 1.0, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadWorkerId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET workerId = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadPriority = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET priority = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfHideDownload = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET isVisible = 0, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetDownloadVisibility = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET isVisible = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteDownloadById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteDownloadsByStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads WHERE status = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteHiddenDownloads = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads WHERE isVisible = 0";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldCompletedDownloads = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads WHERE completedAt < ? AND status = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllDownloads = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads";
        return _query;
      }
    };
    this.__preparedStmtOfCleanupStaleDownloads = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET status = ? WHERE status = ? AND updatedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteFailedDownloadsOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads WHERE status = 'FAILED' AND updatedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCompletedDownloadsOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads WHERE status = 'COMPLETED' AND completedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteFailedDownloads = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM downloads WHERE status = 'FAILED'";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadFilePath = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE downloads SET outputPath = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertDownload(final DownloadEntity download,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDownloadEntity.insertAndReturnId(download);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertDownloads(final List<DownloadEntity> downloads,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfDownloadEntity.insertAndReturnIdsList(downloads);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDownload(final DownloadEntity download,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __deletionAdapterOfDownloadEntity.handle(download);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownload(final DownloadEntity download,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfDownloadEntity.handle(download);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloads(final List<DownloadEntity> downloads,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfDownloadEntity.handleMultiple(downloads);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadStatus(final String id, final DownloadStatus status,
      final Date updatedAt, final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUpdateDownloadStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadProgress(final String id, final float progress,
      final long downloadedBytes, final long speed, final long eta, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadProgress.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, progress);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, downloadedBytes);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, speed);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, eta);
        _argIndex = 5;
        final Long _tmp = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 6;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUpdateDownloadProgress.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markDownloadFailed(final String id, final DownloadStatus status, final String error,
      final Date updatedAt, final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkDownloadFailed.acquire();
        int _argIndex = 1;
        final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, error);
        _argIndex = 3;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 4;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfMarkDownloadFailed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markDownloadCompleted(final String id, final DownloadStatus status,
      final Date completedAt, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkDownloadCompleted.acquire();
        int _argIndex = 1;
        final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(completedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        final Long _tmp_2 = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp_2 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_2);
        }
        _argIndex = 4;
        _stmt.bindString(_argIndex, id);
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
  public Object updateDownloadWorkerId(final String id, final String workerId, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadWorkerId.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, workerId);
        _argIndex = 2;
        final Long _tmp = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUpdateDownloadWorkerId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadPriority(final String id, final int priority, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadPriority.acquire();
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
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUpdateDownloadPriority.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object hideDownload(final String id, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHideDownload.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfHideDownload.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setDownloadVisibility(final String id, final boolean visible, final Date updatedAt,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetDownloadVisibility.acquire();
        int _argIndex = 1;
        final int _tmp = visible ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(updatedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfSetDownloadVisibility.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDownloadById(final String id,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDownloadById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfDeleteDownloadById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDownloadsByStatus(final DownloadStatus status,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDownloadsByStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
        _stmt.bindString(_argIndex, _tmp);
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
          __preparedStmtOfDeleteDownloadsByStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHiddenDownloads(final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteHiddenDownloads.acquire();
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
          __preparedStmtOfDeleteHiddenDownloads.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldCompletedDownloads(final Date beforeDate, final DownloadStatus status,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldCompletedDownloads.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(beforeDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        final String _tmp_1 = __downloadStatusConverter.fromDownloadStatus(status);
        _stmt.bindString(_argIndex, _tmp_1);
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
          __preparedStmtOfDeleteOldCompletedDownloads.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllDownloads(final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllDownloads.acquire();
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
          __preparedStmtOfDeleteAllDownloads.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object cleanupStaleDownloads(final DownloadStatus oldStatus,
      final DownloadStatus newStatus, final Date beforeDate,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCleanupStaleDownloads.acquire();
        int _argIndex = 1;
        final String _tmp = __downloadStatusConverter.fromDownloadStatus(newStatus);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        final String _tmp_1 = __downloadStatusConverter.fromDownloadStatus(oldStatus);
        _stmt.bindString(_argIndex, _tmp_1);
        _argIndex = 3;
        final Long _tmp_2 = __dateConverter.dateToTimestamp(beforeDate);
        if (_tmp_2 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_2);
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
          __preparedStmtOfCleanupStaleDownloads.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFailedDownloadsOlderThan(final long timestamp,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFailedDownloadsOlderThan.acquire();
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
          __preparedStmtOfDeleteFailedDownloadsOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCompletedDownloadsOlderThan(final long timestamp,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCompletedDownloadsOlderThan.acquire();
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
          __preparedStmtOfDeleteCompletedDownloadsOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFailedDownloads(final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFailedDownloads.acquire();
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
          __preparedStmtOfDeleteFailedDownloads.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadFilePath(final String id, final String filePath,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadFilePath.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, filePath);
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUpdateDownloadFilePath.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DownloadEntity>> getAllDownloads() {
    final String _sql = "SELECT * FROM downloads ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Object getDownloadById(final String id,
      final Continuation<? super DownloadEntity> $completion) {
    final String _sql = "SELECT * FROM downloads WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DownloadEntity>() {
      @Override
      @Nullable
      public DownloadEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final DownloadEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _result = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> getDownloadsByPlatform(final String platform) {
    final String _sql = "SELECT * FROM downloads WHERE platform = ? AND isVisible = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, platform);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> searchDownloads(final String query) {
    final String _sql = "SELECT * FROM downloads WHERE title LIKE '%' || ? || '%' OR author LIKE '%' || ? || '%' AND isVisible = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> getDownloadsByDateRange(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM downloads WHERE createdAt BETWEEN ? AND ? AND isVisible = 1 ORDER BY createdAt DESC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp_2 != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_3 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_4);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_6;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_8 = __dateConverter.fromTimestamp(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_8;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_9;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_9);
            final boolean _tmpIsVisible;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_10 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Object getDownloadCountByStatus(final DownloadStatus status,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads WHERE status = ? AND isVisible = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(0);
            _result = _tmp_1;
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
  public Object getTotalDownloadCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads WHERE isVisible = 1";
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
  public Object getTotalDownloadedBytes(final DownloadStatus status,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(downloadedBytes) FROM downloads WHERE status = ? AND isVisible = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final long _tmp_1;
            _tmp_1 = _cursor.getLong(0);
            _result = _tmp_1;
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
  public Object getAverageProgress(final List<? extends DownloadStatus> activeStatuses,
      final Continuation<? super Float> $completion) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT AVG(progress) FROM downloads WHERE status IN (");
    final int _inputSize = activeStatuses.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") AND isVisible = 1");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (DownloadStatus _item : activeStatuses) {
      final String _tmp = __downloadStatusConverter.fromDownloadStatus(_item);
      _statement.bindString(_argIndex, _tmp);
      _argIndex++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final float _tmp_1;
            _tmp_1 = _cursor.getFloat(0);
            _result = _tmp_1;
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
  public Object getRetryableDownloads(final DownloadStatus status,
      final Continuation<? super List<DownloadEntity>> $completion) {
    final String _sql = "SELECT * FROM downloads WHERE status = ? AND retryCount < maxRetries AND isVisible = 1 ORDER BY retryCount ASC, createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp_1 != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_2 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_3);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_5 = __dateConverter.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_7 = __dateConverter.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_8);
            final boolean _tmpIsVisible;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_9 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Object getNextPendingDownload(final List<? extends DownloadStatus> statuses,
      final Continuation<? super DownloadEntity> $completion) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM downloads WHERE status IN (");
    final int _inputSize = statuses.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") AND isVisible = 1 ORDER BY priority DESC, createdAt ASC LIMIT 1");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (DownloadStatus _item : statuses) {
      final String _tmp = __downloadStatusConverter.fromDownloadStatus(_item);
      _statement.bindString(_argIndex, _tmp);
      _argIndex++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DownloadEntity>() {
      @Override
      @Nullable
      public DownloadEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final DownloadEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp_1 != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_2 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_3);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_5 = __dateConverter.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_7 = __dateConverter.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_8);
            final boolean _tmpIsVisible;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_9 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _result = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Object getAllPlatforms(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT platform FROM downloads WHERE isVisible = 1 ORDER BY platform ASC";
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
    final String _sql = "SELECT DISTINCT author FROM downloads WHERE author IS NOT NULL AND isVisible = 1 ORDER BY author ASC";
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
  public Object getDownloadStatistics(
      final Continuation<? super DownloadRepository.DownloadStatistics> $completion) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            COUNT(*) as totalDownloads,\n"
            + "            SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) as completedDownloads,\n"
            + "            SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) as failedDownloads,\n"
            + "            SUM(CASE WHEN status IN ('PENDING', 'DOWNLOADING', 'PROCESSING', 'EXTRACTING') THEN 1 ELSE 0 END) as activeDownloads,\n"
            + "            SUM(downloadedBytes) as totalSize,\n"
            + "            AVG(CASE WHEN status IN ('DOWNLOADING', 'PROCESSING') AND speed > 0 THEN speed ELSE NULL END) as averageSpeed\n"
            + "        FROM downloads WHERE isVisible = 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DownloadRepository.DownloadStatistics>() {
      @Override
      @NonNull
      public DownloadRepository.DownloadStatistics call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTotalDownloads = 0;
          final int _cursorIndexOfCompletedDownloads = 1;
          final int _cursorIndexOfFailedDownloads = 2;
          final int _cursorIndexOfActiveDownloads = 3;
          final int _cursorIndexOfTotalSize = 4;
          final int _cursorIndexOfAverageSpeed = 5;
          final DownloadRepository.DownloadStatistics _result;
          if (_cursor.moveToFirst()) {
            final int _tmpTotalDownloads;
            _tmpTotalDownloads = _cursor.getInt(_cursorIndexOfTotalDownloads);
            final int _tmpCompletedDownloads;
            _tmpCompletedDownloads = _cursor.getInt(_cursorIndexOfCompletedDownloads);
            final int _tmpFailedDownloads;
            _tmpFailedDownloads = _cursor.getInt(_cursorIndexOfFailedDownloads);
            final int _tmpActiveDownloads;
            _tmpActiveDownloads = _cursor.getInt(_cursorIndexOfActiveDownloads);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final double _tmpAverageSpeed;
            _tmpAverageSpeed = _cursor.getDouble(_cursorIndexOfAverageSpeed);
            _result = new DownloadRepository.DownloadStatistics(_tmpTotalDownloads,_tmpCompletedDownloads,_tmpFailedDownloads,_tmpActiveDownloads,_tmpTotalSize,_tmpAverageSpeed);
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
  public Object getDownloadCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads";
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
  public Object getActiveDownloadCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING')";
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
  public Flow<List<DownloadEntity>> getDownloadsByStatusFlow(final DownloadStatus status) {
    final String _sql = "SELECT * FROM downloads WHERE status = ? AND isVisible = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __downloadStatusConverter.fromDownloadStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp_1 != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_2 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_3);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_5 = __dateConverter.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_7 = __dateConverter.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_8);
            final boolean _tmpIsVisible;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_9 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> getAllDownloadsFlow() {
    final String _sql = "SELECT * FROM downloads ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> getActiveDownloadsFlow() {
    final String _sql = "SELECT * FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING', 'PENDING') AND isVisible = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> getCompletedDownloadsFlow() {
    final String _sql = "SELECT * FROM downloads WHERE status = 'COMPLETED' AND isVisible = 1 ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> getFailedDownloadsFlow() {
    final String _sql = "SELECT * FROM downloads WHERE status = 'FAILED' AND isVisible = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<DownloadEntity> getDownloadByIdFlow(final String id) {
    final String _sql = "SELECT * FROM downloads WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<DownloadEntity>() {
      @Override
      @Nullable
      public DownloadEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final DownloadEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _result = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
          } else {
            _result = null;
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
  public Object getStuckDownloads(final long staleTime,
      final Continuation<? super List<DownloadEntity>> $completion) {
    final String _sql = "SELECT * FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING') AND updatedAt < ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, staleTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Object getCompletedDownloadsWithoutFile(
      final Continuation<? super List<DownloadEntity>> $completion) {
    final String _sql = "SELECT * FROM downloads WHERE status = 'COMPLETED' AND outputPath NOT NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Object getTemporaryFiles(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT outputPath FROM downloads WHERE outputPath LIKE '%.tmp' OR outputPath LIKE '%.part'";
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
  public Object getTotalDownloads(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads WHERE isVisible = 1";
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
  public Object getCompletedDownloadsCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads WHERE status = 'COMPLETED' AND isVisible = 1";
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
  public Object getFailedDownloadsCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads WHERE status = 'FAILED' AND isVisible = 1";
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
  public Object getActiveDownloadsCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING', 'PENDING') AND isVisible = 1";
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
  public Object getTotalDownloadedSize(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(downloadedBytes) FROM downloads WHERE status = 'COMPLETED' AND isVisible = 1";
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
  public Object getAverageDownloadSpeed(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT AVG(speed) FROM downloads WHERE status = 'DOWNLOADING' AND speed > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
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
  public Object getFailedDownloads(final Continuation<? super List<DownloadEntity>> $completion) {
    final String _sql = "SELECT * FROM downloads WHERE status = 'FAILED' AND isVisible = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Flow<List<DownloadEntity>> getDownloadStatisticsFlow(final long timestamp) {
    final String _sql = "SELECT * FROM downloads WHERE updatedAt > ? ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, timestamp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"downloads"}, new Callable<List<DownloadEntity>>() {
      @Override
      @NonNull
      public List<DownloadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfFormatId = CursorUtil.getColumnIndexOrThrow(_cursor, "formatId");
          final int _cursorIndexOfQualityLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityLabel");
          final int _cursorIndexOfAudioCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "audioCodec");
          final int _cursorIndexOfVideoCodec = CursorUtil.getColumnIndexOrThrow(_cursor, "videoCodec");
          final int _cursorIndexOfAudioOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "audioOnly");
          final int _cursorIndexOfVideoOnly = CursorUtil.getColumnIndexOrThrow(_cursor, "videoOnly");
          final int _cursorIndexOfFileExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "fileExtension");
          final int _cursorIndexOfEstimatedSize = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedSize");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "filename");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfDownloadedBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadedBytes");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed");
          final int _cursorIndexOfEta = CursorUtil.getColumnIndexOrThrow(_cursor, "eta");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfMaxRetries = CursorUtil.getColumnIndexOrThrow(_cursor, "maxRetries");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "extension");
          final int _cursorIndexOfTotalSize = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSize");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<DownloadEntity> _result = new ArrayList<DownloadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DownloadEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
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
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final String _tmpFormatId;
            _tmpFormatId = _cursor.getString(_cursorIndexOfFormatId);
            final String _tmpQualityLabel;
            _tmpQualityLabel = _cursor.getString(_cursorIndexOfQualityLabel);
            final String _tmpAudioCodec;
            if (_cursor.isNull(_cursorIndexOfAudioCodec)) {
              _tmpAudioCodec = null;
            } else {
              _tmpAudioCodec = _cursor.getString(_cursorIndexOfAudioCodec);
            }
            final String _tmpVideoCodec;
            if (_cursor.isNull(_cursorIndexOfVideoCodec)) {
              _tmpVideoCodec = null;
            } else {
              _tmpVideoCodec = _cursor.getString(_cursorIndexOfVideoCodec);
            }
            final boolean _tmpAudioOnly;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAudioOnly);
            _tmpAudioOnly = _tmp != 0;
            final boolean _tmpVideoOnly;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideoOnly);
            _tmpVideoOnly = _tmp_1 != 0;
            final String _tmpFileExtension;
            _tmpFileExtension = _cursor.getString(_cursorIndexOfFileExtension);
            final long _tmpEstimatedSize;
            _tmpEstimatedSize = _cursor.getLong(_cursorIndexOfEstimatedSize);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpFilename;
            _tmpFilename = _cursor.getString(_cursorIndexOfFilename);
            final DownloadStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __downloadStatusConverter.toDownloadStatus(_tmp_2);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final long _tmpDownloadedBytes;
            _tmpDownloadedBytes = _cursor.getLong(_cursorIndexOfDownloadedBytes);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final long _tmpSpeed;
            _tmpSpeed = _cursor.getLong(_cursorIndexOfSpeed);
            final long _tmpEta;
            _tmpEta = _cursor.getLong(_cursorIndexOfEta);
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final int _tmpMaxRetries;
            _tmpMaxRetries = _cursor.getInt(_cursorIndexOfMaxRetries);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __dateConverter.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __dateConverter.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final Date _tmpCompletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __dateConverter.fromTimestamp(_tmp_7);
            final boolean _tmpIsVisible;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp_8 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final String _tmpWorkerId;
            if (_cursor.isNull(_cursorIndexOfWorkerId)) {
              _tmpWorkerId = null;
            } else {
              _tmpWorkerId = _cursor.getString(_cursorIndexOfWorkerId);
            }
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpDownloadPath;
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpErrorMessage;
            _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpExtension;
            _tmpExtension = _cursor.getString(_cursorIndexOfExtension);
            final long _tmpTotalSize;
            _tmpTotalSize = _cursor.getLong(_cursorIndexOfTotalSize);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new DownloadEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpThumbnail,_tmpDuration,_tmpAuthor,_tmpPlatform,_tmpFormatId,_tmpQualityLabel,_tmpAudioCodec,_tmpVideoCodec,_tmpAudioOnly,_tmpVideoOnly,_tmpFileExtension,_tmpEstimatedSize,_tmpOutputPath,_tmpFilename,_tmpStatus,_tmpProgress,_tmpDownloadedBytes,_tmpTotalBytes,_tmpSpeed,_tmpEta,_tmpError,_tmpRetryCount,_tmpMaxRetries,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpIsVisible,_tmpPriority,_tmpWorkerId,_tmpMetadata,_tmpDownloadPath,_tmpFilePath,_tmpErrorMessage,_tmpQuality,_tmpExtension,_tmpTotalSize,_tmpStartedAt);
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
  public Object clearInactiveWorkerIds(final List<? extends DownloadStatus> activeStatuses,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("UPDATE downloads SET workerId = NULL WHERE workerId IS NOT NULL AND status NOT IN (");
        final int _inputSize = activeStatuses.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (DownloadStatus _item : activeStatuses) {
          final String _tmp = __downloadStatusConverter.fromDownloadStatus(_item);
          _stmt.bindString(_argIndex, _tmp);
          _argIndex++;
        }
        __db.beginTransaction();
        try {
          final Integer _result = _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
