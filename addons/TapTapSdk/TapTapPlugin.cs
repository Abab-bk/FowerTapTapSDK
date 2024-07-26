#if TOOLS
using Godot;
using System;


[Tool]
public partial class TapTapPlugin : EditorPlugin
{
	private AndroidExportPlugin _exporter;
	
	public override void _EnterTree()
	{
		_exporter = new AndroidExportPlugin();
		AddExportPlugin(_exporter);
	}

	public override void _ExitTree()
	{
		RemoveExportPlugin(_exporter);
		_exporter = null;
	}
	
	private partial class AndroidExportPlugin : EditorExportPlugin
	{
		private const string PluginName = "TapTapSdkPlugin";

		public override bool _SupportsPlatform(EditorExportPlatform platform) => platform is EditorExportPlatformAndroid;

		public override string[] _GetAndroidLibraries(EditorExportPlatform platform, bool debug)
		{
			GD.Print("Test Plugin");
			return new[]
			{
				"res://addons/TapTapSdk/TapTapSdkPlugin-release.aar",
				"res://addons/TapTapSdk/Libs/AntiAddiction_3.29.2.aar",
				"res://addons/TapTapSdk/Libs/AntiAddictionUI_3.29.2.aar",
				"res://addons/TapTapSdk/Libs/TapBootstrap_3.29.2.aar",
				"res://addons/TapTapSdk/Libs/TapCommon_3.29.2.aar",
				"res://addons/TapTapSdk/Libs/TapLogin_3.29.2.aar",
			};
		}

		public override string _GetAndroidManifestElementContents(EditorExportPlatform platform, bool debug)
		{
			return "<uses-permission android:name=\"android.permission.INTERNET\"></uses-permission>";
		}

		public override string[] _GetAndroidDependencies(EditorExportPlatform platform, bool debug)
		{
			return new[]
			{
				"com.taptap:lc-storage-android:8.2.24",
				"com.taptap:lc-realtime-android:8.2.24",
				"io.reactivex.rxjava2:rxandroid:2.1.1"
			};
		}

		public override string _GetName() => PluginName;
	}
}
#endif
