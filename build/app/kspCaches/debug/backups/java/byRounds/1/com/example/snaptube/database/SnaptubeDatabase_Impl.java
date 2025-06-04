package com.example.snaptube.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.snaptube.database.dao.DownloadDao;
import com.example.snaptube.database.dao.DownloadDao_Impl;
import com.example.snaptube.database.dao.PlaylistDao;
import com.example.snaptube.database.dao.PlaylistDao_Impl;
import com.example.snaptube.database.dao.VideoInfoDao;
import com.example.snaptube.database.dao.VideoInfoDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SnaptubeDatabase_Impl extends SnaptubeDatabase {
  private volatile DownloadDao _downloadDao;

  private volatile VideoInfoDao _videoInfoDao;

  private volatile PlaylistDao _playlistDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `downloads` (`id` TEXT NOT NULL, `url` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `thumbnail` TEXT, `duration` INTEGER NOT NULL, `author` TEXT, `platform` TEXT NOT NULL, `formatId` TEXT NOT NULL, `qualityLabel` TEXT NOT NULL, `audioCodec` TEXT, `videoCodec` TEXT, `audioOnly` INTEGER NOT NULL, `videoOnly` INTEGER NOT NULL, `fileExtension` TEXT NOT NULL, `estimatedSize` INTEGER NOT NULL, `outputPath` TEXT NOT NULL, `filename` TEXT NOT NULL, `status` TEXT NOT NULL, `progress` REAL NOT NULL, `downloadedBytes` INTEGER NOT NULL, `totalBytes` INTEGER NOT NULL, `speed` INTEGER NOT NULL, `eta` INTEGER NOT NULL, `error` TEXT, `retryCount` INTEGER NOT NULL, `maxRetries` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `completedAt` INTEGER, `isVisible` INTEGER NOT NULL, `priority` INTEGER NOT NULL, `workerId` TEXT, `metadata` TEXT, `downloadPath` TEXT NOT NULL, `filePath` TEXT NOT NULL, `errorMessage` TEXT NOT NULL, `quality` TEXT NOT NULL, `extension` TEXT NOT NULL, `totalSize` INTEGER NOT NULL, `startedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `video_info_cache` (`url` TEXT NOT NULL, `videoId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `thumbnail` TEXT, `duration` INTEGER NOT NULL, `viewCount` INTEGER NOT NULL, `likeCount` INTEGER NOT NULL, `author` TEXT, `authorUrl` TEXT, `authorThumbnail` TEXT, `platform` TEXT NOT NULL, `uploadDate` TEXT, `isLive` INTEGER NOT NULL, `ageLimit` INTEGER NOT NULL, `categories` TEXT NOT NULL, `tags` TEXT NOT NULL, `language` TEXT, `subtitles` TEXT NOT NULL, `formats` TEXT NOT NULL, `qualityOptions` TEXT NOT NULL, `availableFormats` TEXT NOT NULL, `hasAudio` INTEGER NOT NULL, `hasVideo` INTEGER NOT NULL, `maxQuality` TEXT, `minQuality` TEXT, `recommendedFormat` TEXT, `fileSize` INTEGER NOT NULL, `bitrate` INTEGER NOT NULL, `fps` INTEGER NOT NULL, `resolution` TEXT, `aspectRatio` TEXT, `audioLanguage` TEXT, `videoLanguage` TEXT, `chapters` TEXT NOT NULL, `webpage` TEXT, `extractor` TEXT, `extractorKey` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `lastAccessed` INTEGER NOT NULL, `accessCount` INTEGER NOT NULL, `cacheExpiryDate` INTEGER, `isValid` INTEGER NOT NULL, `metadata` TEXT, PRIMARY KEY(`url`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `playlists` (`url` TEXT NOT NULL, `playlistId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `thumbnail` TEXT, `author` TEXT, `authorUrl` TEXT, `authorThumbnail` TEXT, `platform` TEXT NOT NULL, `videoCount` INTEGER NOT NULL, `downloadedCount` INTEGER NOT NULL, `failedCount` INTEGER NOT NULL, `pendingCount` INTEGER NOT NULL, `totalDuration` INTEGER NOT NULL, `totalSize` INTEGER NOT NULL, `videoUrls` TEXT NOT NULL, `videoTitles` TEXT NOT NULL, `videoDurations` TEXT NOT NULL, `videoThumbnails` TEXT NOT NULL, `isPublic` INTEGER NOT NULL, `isLive` INTEGER NOT NULL, `uploadDate` TEXT, `lastUpdated` TEXT, `language` TEXT, `tags` TEXT NOT NULL, `categories` TEXT NOT NULL, `viewCount` INTEGER NOT NULL, `subscriberCount` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `lastAccessed` INTEGER NOT NULL, `downloadStartedAt` INTEGER, `downloadCompletedAt` INTEGER, `accessCount` INTEGER NOT NULL, `downloadProgress` REAL NOT NULL, `selectedQuality` TEXT, `selectedFormat` TEXT, `downloadPath` TEXT, `isBookmarked` INTEGER NOT NULL, `priority` INTEGER NOT NULL, `downloadAllSelected` INTEGER NOT NULL, `selectedVideoIndices` TEXT NOT NULL, `failedVideoUrls` TEXT NOT NULL, `metadata` TEXT, PRIMARY KEY(`url`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '8dbd6452b907b9f8393ad343f79d69d6')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `downloads`");
        db.execSQL("DROP TABLE IF EXISTS `video_info_cache`");
        db.execSQL("DROP TABLE IF EXISTS `playlists`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsDownloads = new HashMap<String, TableInfo.Column>(41);
        _columnsDownloads.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("url", new TableInfo.Column("url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("thumbnail", new TableInfo.Column("thumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("author", new TableInfo.Column("author", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("platform", new TableInfo.Column("platform", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("formatId", new TableInfo.Column("formatId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("qualityLabel", new TableInfo.Column("qualityLabel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("audioCodec", new TableInfo.Column("audioCodec", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("videoCodec", new TableInfo.Column("videoCodec", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("audioOnly", new TableInfo.Column("audioOnly", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("videoOnly", new TableInfo.Column("videoOnly", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("fileExtension", new TableInfo.Column("fileExtension", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("estimatedSize", new TableInfo.Column("estimatedSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("outputPath", new TableInfo.Column("outputPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("filename", new TableInfo.Column("filename", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("progress", new TableInfo.Column("progress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("downloadedBytes", new TableInfo.Column("downloadedBytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("totalBytes", new TableInfo.Column("totalBytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("speed", new TableInfo.Column("speed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("eta", new TableInfo.Column("eta", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("error", new TableInfo.Column("error", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("retryCount", new TableInfo.Column("retryCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("maxRetries", new TableInfo.Column("maxRetries", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("isVisible", new TableInfo.Column("isVisible", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("workerId", new TableInfo.Column("workerId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("metadata", new TableInfo.Column("metadata", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("downloadPath", new TableInfo.Column("downloadPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("filePath", new TableInfo.Column("filePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("errorMessage", new TableInfo.Column("errorMessage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("quality", new TableInfo.Column("quality", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("extension", new TableInfo.Column("extension", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("totalSize", new TableInfo.Column("totalSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("startedAt", new TableInfo.Column("startedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDownloads = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDownloads = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDownloads = new TableInfo("downloads", _columnsDownloads, _foreignKeysDownloads, _indicesDownloads);
        final TableInfo _existingDownloads = TableInfo.read(db, "downloads");
        if (!_infoDownloads.equals(_existingDownloads)) {
          return new RoomOpenHelper.ValidationResult(false, "downloads(com.example.snaptube.database.entities.DownloadEntity).\n"
                  + " Expected:\n" + _infoDownloads + "\n"
                  + " Found:\n" + _existingDownloads);
        }
        final HashMap<String, TableInfo.Column> _columnsVideoInfoCache = new HashMap<String, TableInfo.Column>(45);
        _columnsVideoInfoCache.put("url", new TableInfo.Column("url", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("videoId", new TableInfo.Column("videoId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("thumbnail", new TableInfo.Column("thumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("viewCount", new TableInfo.Column("viewCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("likeCount", new TableInfo.Column("likeCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("author", new TableInfo.Column("author", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("authorUrl", new TableInfo.Column("authorUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("authorThumbnail", new TableInfo.Column("authorThumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("platform", new TableInfo.Column("platform", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("uploadDate", new TableInfo.Column("uploadDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("isLive", new TableInfo.Column("isLive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("ageLimit", new TableInfo.Column("ageLimit", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("categories", new TableInfo.Column("categories", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("language", new TableInfo.Column("language", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("subtitles", new TableInfo.Column("subtitles", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("formats", new TableInfo.Column("formats", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("qualityOptions", new TableInfo.Column("qualityOptions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("availableFormats", new TableInfo.Column("availableFormats", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("hasAudio", new TableInfo.Column("hasAudio", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("hasVideo", new TableInfo.Column("hasVideo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("maxQuality", new TableInfo.Column("maxQuality", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("minQuality", new TableInfo.Column("minQuality", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("recommendedFormat", new TableInfo.Column("recommendedFormat", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("fileSize", new TableInfo.Column("fileSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("bitrate", new TableInfo.Column("bitrate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("fps", new TableInfo.Column("fps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("resolution", new TableInfo.Column("resolution", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("aspectRatio", new TableInfo.Column("aspectRatio", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("audioLanguage", new TableInfo.Column("audioLanguage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("videoLanguage", new TableInfo.Column("videoLanguage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("chapters", new TableInfo.Column("chapters", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("webpage", new TableInfo.Column("webpage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("extractor", new TableInfo.Column("extractor", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("extractorKey", new TableInfo.Column("extractorKey", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("lastAccessed", new TableInfo.Column("lastAccessed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("accessCount", new TableInfo.Column("accessCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("cacheExpiryDate", new TableInfo.Column("cacheExpiryDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("isValid", new TableInfo.Column("isValid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoInfoCache.put("metadata", new TableInfo.Column("metadata", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVideoInfoCache = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVideoInfoCache = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVideoInfoCache = new TableInfo("video_info_cache", _columnsVideoInfoCache, _foreignKeysVideoInfoCache, _indicesVideoInfoCache);
        final TableInfo _existingVideoInfoCache = TableInfo.read(db, "video_info_cache");
        if (!_infoVideoInfoCache.equals(_existingVideoInfoCache)) {
          return new RoomOpenHelper.ValidationResult(false, "video_info_cache(com.example.snaptube.database.entities.VideoInfoEntity).\n"
                  + " Expected:\n" + _infoVideoInfoCache + "\n"
                  + " Found:\n" + _existingVideoInfoCache);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaylists = new HashMap<String, TableInfo.Column>(44);
        _columnsPlaylists.put("url", new TableInfo.Column("url", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("playlistId", new TableInfo.Column("playlistId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("thumbnail", new TableInfo.Column("thumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("author", new TableInfo.Column("author", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("authorUrl", new TableInfo.Column("authorUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("authorThumbnail", new TableInfo.Column("authorThumbnail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("platform", new TableInfo.Column("platform", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("videoCount", new TableInfo.Column("videoCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("downloadedCount", new TableInfo.Column("downloadedCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("failedCount", new TableInfo.Column("failedCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("pendingCount", new TableInfo.Column("pendingCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("totalDuration", new TableInfo.Column("totalDuration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("totalSize", new TableInfo.Column("totalSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("videoUrls", new TableInfo.Column("videoUrls", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("videoTitles", new TableInfo.Column("videoTitles", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("videoDurations", new TableInfo.Column("videoDurations", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("videoThumbnails", new TableInfo.Column("videoThumbnails", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("isPublic", new TableInfo.Column("isPublic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("isLive", new TableInfo.Column("isLive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("uploadDate", new TableInfo.Column("uploadDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("lastUpdated", new TableInfo.Column("lastUpdated", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("language", new TableInfo.Column("language", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("categories", new TableInfo.Column("categories", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("viewCount", new TableInfo.Column("viewCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("subscriberCount", new TableInfo.Column("subscriberCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("lastAccessed", new TableInfo.Column("lastAccessed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("downloadStartedAt", new TableInfo.Column("downloadStartedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("downloadCompletedAt", new TableInfo.Column("downloadCompletedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("accessCount", new TableInfo.Column("accessCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("downloadProgress", new TableInfo.Column("downloadProgress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("selectedQuality", new TableInfo.Column("selectedQuality", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("selectedFormat", new TableInfo.Column("selectedFormat", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("downloadPath", new TableInfo.Column("downloadPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("isBookmarked", new TableInfo.Column("isBookmarked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("downloadAllSelected", new TableInfo.Column("downloadAllSelected", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("selectedVideoIndices", new TableInfo.Column("selectedVideoIndices", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("failedVideoUrls", new TableInfo.Column("failedVideoUrls", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("metadata", new TableInfo.Column("metadata", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaylists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaylists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaylists = new TableInfo("playlists", _columnsPlaylists, _foreignKeysPlaylists, _indicesPlaylists);
        final TableInfo _existingPlaylists = TableInfo.read(db, "playlists");
        if (!_infoPlaylists.equals(_existingPlaylists)) {
          return new RoomOpenHelper.ValidationResult(false, "playlists(com.example.snaptube.database.entities.PlaylistEntity).\n"
                  + " Expected:\n" + _infoPlaylists + "\n"
                  + " Found:\n" + _existingPlaylists);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "8dbd6452b907b9f8393ad343f79d69d6", "dfad93f9ca19291f08aad5059be1d24f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "downloads","video_info_cache","playlists");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `downloads`");
      _db.execSQL("DELETE FROM `video_info_cache`");
      _db.execSQL("DELETE FROM `playlists`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(DownloadDao.class, DownloadDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VideoInfoDao.class, VideoInfoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PlaylistDao.class, PlaylistDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public DownloadDao downloadDao() {
    if (_downloadDao != null) {
      return _downloadDao;
    } else {
      synchronized(this) {
        if(_downloadDao == null) {
          _downloadDao = new DownloadDao_Impl(this);
        }
        return _downloadDao;
      }
    }
  }

  @Override
  public VideoInfoDao videoInfoDao() {
    if (_videoInfoDao != null) {
      return _videoInfoDao;
    } else {
      synchronized(this) {
        if(_videoInfoDao == null) {
          _videoInfoDao = new VideoInfoDao_Impl(this);
        }
        return _videoInfoDao;
      }
    }
  }

  @Override
  public PlaylistDao playlistDao() {
    if (_playlistDao != null) {
      return _playlistDao;
    } else {
      synchronized(this) {
        if(_playlistDao == null) {
          _playlistDao = new PlaylistDao_Impl(this);
        }
        return _playlistDao;
      }
    }
  }
}
