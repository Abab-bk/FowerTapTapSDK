# FowerTapTapSDK

TapTapSDK for Godot4.2

## 啥？

Godot4.2 及以上适用的 TapTapSDK 接入。

## 实现功能：

- TapTap 登录（单纯认证）

- 防沉迷

别的为什么不做？

因为我没有备案域名，测试不了。

## 使用方法：

- clone 本仓库

- 复制 addons 文件夹到你的项目目录

- 修改 addons/TapTapSdk/TapTap.cs 中的 ClientId 变量

- 启用插件

## Tip

当前只有 C# 包装，我没写 gds 包装，有需要可以自己写一下

## 自己编译

进入 SourceCode/TapTapSdkPlugin/build.gradle，修改以下代码：

```
compileOnly files('../libs/godot-lib.template_release.aar')
```

如果你使用非 Stable 版本，就在 Project/Android/build/libs/release 里找到 godot-lib.template_release.aar 并覆盖对应文件

如果你使用 Stable 版本，就改为：

```
implementation("org.godotengine:godot:4.2.0.stable") // 改成你的 Godot 版本
```

同时删除 godot-lib.template_release.aar

## API：

直接看源码吧
