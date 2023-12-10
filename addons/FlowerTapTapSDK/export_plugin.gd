@tool
extends EditorPlugin

var export_plugin : AndroidExportPlugin

func _enter_tree():
    export_plugin = AndroidExportPlugin.new()
    add_export_plugin(export_plugin)
    add_autoload_singleton("Tap", "Tap.gd")


func _exit_tree():
    remove_export_plugin(export_plugin)
    export_plugin = null
    remove_autoload_singleton("Tap")


class AndroidExportPlugin extends EditorExportPlugin:
    var _plugin_name = "FlowerTapSDK"

    func _supports_platform(platform):
        if platform is EditorExportPlatformAndroid:
            return true
        return false

    func _get_android_libraries(platform, debug):
        if debug:
            return PackedStringArray([
            "FlowerTapTapSDK/bin/debug/RealTapSDK-debug.aar",
            "FlowerTapTapSDK/bin/libs/AntiAddiction_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/AntiAddictionUI_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/lib-rtc-1.1.0-release.aar",
            "FlowerTapTapSDK/bin/libs/TapAchievement_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapBillboard_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapBootstrap_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapCommon_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapLicense_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapLogin_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapMoment_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/THEMIS-release3.0.7.aar"
            ])
        else:
            return PackedStringArray([
            "FlowerTapTapSDK/bin/release/RealTapSDK-release.aar",
            "FlowerTapTapSDK/bin/libs/AntiAddiction_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/AntiAddictionUI_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/lib-rtc-1.1.0-release.aar",
            "FlowerTapTapSDK/bin/libs/TapAchievement_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapBillboard_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapBootstrap_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapCommon_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapLicense_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapLogin_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/TapMoment_3.24.0.aar",
            "FlowerTapTapSDK/bin/libs/THEMIS-release3.0.7.aar"
            ])


    func _get_android_dependencies(platform: EditorExportPlatform, debug: bool) -> PackedStringArray:
        return PackedStringArray([
            "cn.leancloud:storage-android:8.2.19",
            "io.reactivex.rxjava2:rxandroid:2.1.1",
            "cn.leancloud:realtime-android:8.2.19",
            "io.reactivex.rxjava2:rxandroid:2.1.1",
            
        ])

    func _get_android_manifest_element_contents(platform: EditorExportPlatform, debug: bool) -> String:
        return """
        <uses-permission android:name="android.permission.INTERNET"></uses-permission>
        """

    func _get_android_manifest_application_element_contents(platform: EditorExportPlatform, debug: bool) -> String:
        return """
        <activity
            android:name=".MainActivity"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
    
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        """

    func _get_name():
        return _plugin_name
