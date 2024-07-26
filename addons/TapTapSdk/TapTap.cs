using System;
using Godot;

namespace AcidWallStudio.AcidUtilities;

public partial class TapTapNode : Node
{
    private const string ClientId = "rqqdmriyug2wd5be2x";
    private const string ClientToken = "zdTRgbwTVUDIpU8hb51vyTmISQ0ZWujfARMOnN79";
    private const string PluginName = "TapTapSdk";
    
    public GodotObject Plugin;
    
    public event Action
        OnLogged,
        OnLoginNot,
        OnLoginFailed,
        OnLogOut,
        OnAntiPass,
        OnAntiAgeLess,
        OnAntiTimeout;

    public void Awake()
    {
        foreach (var s in Engine.GetSingletonList())
        {
            Logger.Log($"[Singleton]: {s}");
        }
        
        if (Engine.HasSingleton(PluginName))
        {
            Plugin = Engine.GetSingleton(PluginName);
            Plugin.Connect("logged", Callable.From(() => OnLogged?.Invoke()));
            Plugin.Connect("loginNot", Callable.From(() => OnLoginNot?.Invoke()));
            Plugin.Connect("loginFail", Callable.From(() => OnLoginFailed?.Invoke()));
            Plugin.Connect("loginOut", Callable.From(() => OnLogOut?.Invoke()));
            Plugin.Connect("antiPass", Callable.From(() => OnAntiPass?.Invoke()));
            Plugin.Connect("antiAgeLess", Callable.From(() => OnAntiAgeLess?.Invoke()));
            Plugin.Connect("antiTimeout", Callable.From(() => OnAntiTimeout?.Invoke()));
        }
        else
        {
            Logger.LogError("TapTap plugin not found");
        }
    }

    public void Init()
    {
        Plugin.Call("Init", ClientId);
        InitAntiAddiction();
    }

    public void InitAntiAddiction() => Plugin.Call("InitAntiAddiction", ClientId);
    public void StartupAntiAddiction() => Plugin.Call("startupAntiAddiction");
    public void Login() => Plugin.Call("Login");
    public void Logout() => Plugin.Call("Logout");
    public void IsLogged() => Plugin.Call("isLogged");
    public void ExitAntiAddiction() => Plugin.Call("exitAntiAddiction");
}