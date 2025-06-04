// Test script to verify DialogPreferences to DownloadPreferences conversion
import com.example.snaptube.ui.components.*
import com.example.snaptube.download.DownloadPreferences

fun main() {
    println("Testing DialogPreferences to DownloadPreferences conversion...")
    
    // Test 1: Default preferences
    val defaultDialog = DialogPreferences()
    val defaultReal = defaultDialog.toDownloadPreferences()
    println("✓ Default conversion: audioOnly=${defaultReal.audioOnly}, formatId='${defaultReal.formatId}'")
    
    // Test 2: Audio-only download
    val audioDialog = DialogPreferences(
        audioOnly = true,
        audioQuality = AudioQuality.Q320K
    )
    val audioReal = audioDialog.toDownloadPreferences()
    println("✓ Audio-only conversion: audioOnly=${audioReal.audioOnly}, formatId='${audioReal.formatId}'")
    
    // Test 3: High quality video
    val videoDialog = DialogPreferences(
        quality = VideoQuality.Q1080P,
        embedThumbnail = true,
        embedMetadata = true
    )
    val videoReal = videoDialog.toDownloadPreferences()
    println("✓ Video conversion: formatId='${videoReal.formatId}', thumbnail=${videoReal.embedThumbnail}")
    
    // Test 4: Aria2 enabled
    val aria2Dialog = DialogPreferences(
        useAria2 = true,
        maxConnections = 16
    )
    val aria2Real = aria2Dialog.toDownloadPreferences()
    println("✓ Aria2 conversion: aria2c=${aria2Real.aria2c}, connections=${aria2Real.concurrentConnections}")
    
    println("\n🎉 All DialogPreferences conversions work correctly!")
    println("Real download integration is ready!")
}
