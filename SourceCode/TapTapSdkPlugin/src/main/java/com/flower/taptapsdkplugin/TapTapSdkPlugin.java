package com.flower.taptapsdkplugin;

import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;

import com.tapsdk.antiaddiction.Config;
import com.tapsdk.antiaddictionui.AntiAddictionUICallback;
import com.tapsdk.antiaddictionui.AntiAddictionUIKit;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.AccountGlobalError;
import com.taptap.sdk.Profile;
import com.taptap.sdk.TapLoginHelper;
import com.taptap.sdk.net.Api;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TapTapSdkPlugin extends GodotPlugin {
    private final String TAG = "TapPlugin";

    public SignalInfo logged = new SignalInfo("logged");
    public SignalInfo loginNot = new SignalInfo("loginNot");
    public SignalInfo loginFail = new SignalInfo("loginFail");
    public SignalInfo loginOut = new SignalInfo("loginOut");

    public SignalInfo antiTimeout = new SignalInfo("antiTimeout");
    public SignalInfo antiAgeLess = new SignalInfo("antiAgeLess");
    public SignalInfo antiPass = new SignalInfo("antiPass");

    private String userIdentifier = "玩家的唯一标识";

    public TapTapSdkPlugin(Godot godot) {
        super(godot);
    }

    private Activity GetActivity() {
        return getActivity();
    }


    @NonNull
    @Override
    public String getPluginName() {
        return "TapTapSdk";
    }


    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        HashSet<SignalInfo> signals = new HashSet<SignalInfo>();
        signals.add(logged);
        signals.add(loginNot);
        signals.add(loginFail);
        signals.add(loginOut);
        signals.add(antiAgeLess);
        signals.add(antiTimeout);
        signals.add(antiPass);
        return signals;
    }


    @UsedByGodot
    public void Init(String clientId) {
        TapLoginHelper.init(GetActivity(), clientId);

        TapLoginHelper.TapLoginResultCallback loginCallback = new TapLoginHelper.TapLoginResultCallback() {
            @Override
            public void onLoginSuccess(AccessToken accessToken) {
                Log.d(TAG, "TapTap authorization succeed");
                // 开发者调用 TapLoginHelper.getCurrentProfile() 可以获得当前用户的一些基本信息，例如名称、头像。
                Profile profile = TapLoginHelper.getCurrentProfile();
                userIdentifier = profile.getOpenid();
                emitSignal(logged.getName());
            }

            @Override
            public void onLoginCancel() {
                Log.d(TAG, "TapTap authorization cancelled");
                emitSignal(loginFail.getName());
            }

            @Override
            public void onLoginError(AccountGlobalError accountGlobalError) {
                Log.d(TAG, "TapTap authorization failed. cause: " + accountGlobalError.getMessage());
                emitSignal(loginFail.getName());
            }
        };

        TapLoginHelper.registerLoginCallback(loginCallback);
    }

    @UsedByGodot
    public void InitAntiAddiction(String clientId) {
        // Android SDK 的各接口第一个参数是当前 Activity，以下不再说明
        Config config = new Config.Builder()
                .withClientId(clientId) // TapTap 开发者中心对应 Client ID
                .showSwitchAccount(false) // 是否显示切换账号按钮
                .useAgeRange(true) //是否使用年龄段信息
                .build();

        //设置配置与回调，callback 为开发者实现的自定义防沉迷回调对象
        AntiAddictionUIKit.init(GetActivity(), config);
        AntiAddictionUIKit.setAntiAddictionCallback(new AntiAddictionUICallback() {
            @Override
            public void onCallback(int code, Map<String, Object> map) {
                switch (code) {
                    case 500:
                        Log.d(TAG, "防沉迷登陆成功");
                        emitSignal(antiPass.getName());
                        break;
                    case 1000:
                        Log.d(TAG, "退出账号");
                        break;
                    case 1050:
                        Log.d(TAG, "时长限制");
                        emitSignal(antiTimeout.getName());
                        break;
                    case 1100:
                        Log.d(TAG, "防沉迷未成年玩家无法进行游戏");
                        emitSignal(antiAgeLess.getName());
                        break;
                    case 9002:
                        Log.d(TAG, "防沉迷实名认证过程中点击了关闭实名窗");
                        break;
                    case 1001:
                        Log.d(TAG, "防沉迷实名认证过程中点击了切换账号按钮");
                        break;
                }
            }
        });
    }

    @UsedByGodot
    public void  startupAntiAddiction() {
        AntiAddictionUIKit.startupWithTapTap(GetActivity(), userIdentifier);
    }

    @UsedByGodot
    public void Login() {
        TapLoginHelper.startTapLogin(GetActivity(), TapLoginHelper.SCOPE_PUBLIC_PROFILE);
    }


    @UsedByGodot
    public void Logout() {
        TapLoginHelper.logout();
        ExitAntiAddiction();
        emitSignal(loginOut.getName());
    }


    @UsedByGodot
    public void isLogged() {
        // 获取登录状态
        TapLoginHelper.getCurrentAccessToken();
        // 获取用户信息
        TapLoginHelper.getCurrentProfile();
        TapLoginHelper.fetchProfileForCurrentAccessToken(new Api.ApiCallback<Profile>() {
            @Override
            public void onSuccess(Profile profile) {
                emitSignal(logged.getName());
            }

            @Override
            public void onError(Throwable throwable) {
                emitSignal(loginNot.getName());
            }
        });
    }

    @UsedByGodot
    public void ExitAntiAddiction() {
        AntiAddictionUIKit.exit();
    }
}
