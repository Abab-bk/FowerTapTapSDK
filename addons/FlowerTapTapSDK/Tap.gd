extends Node

const client_id:String = "改成你的"
const client_token:String = "改成你的"

signal logined
signal login_not
signal login_fail
signal log_out

signal init_ok

signal anti_timeout
signal anti_age_less
signal anti_pass


var _plugin_name = "FlowerTapSDK"
var _android_plugin


func _ready():
    if Engine.has_singleton(_plugin_name):
        _android_plugin = Engine.get_singleton(_plugin_name)
        _android_plugin.logined.connect(func(): logined.emit())
        _android_plugin.loginNot.connect(func(): login_not.emit())
        _android_plugin.loginFail.connect(func(): login_fail.emit())
        _android_plugin.antiAgeLess.connect(func(): anti_age_less.emit())
        _android_plugin.antiTimeout.connect(func(): anti_timeout.emit())
        _android_plugin.antiPass.connect(func(): anti_pass.emit())
        _android_plugin.initOk.connect(func(): init_ok.emit())
        _android_plugin.setClientIDAndClientToken(client_id, client_token)
        _android_plugin.initPlugin()
    else:
        print("Couldn't find plugin " + _plugin_name)


func is_login() -> void:
    if _android_plugin:
        _android_plugin.isLogined()


func login_out() -> void:
    if _android_plugin:
        _android_plugin.logOut()


func login() -> void:
    if _android_plugin:
        _android_plugin.Login()


func init_tap_anti() -> void:
    if _android_plugin:
        _android_plugin.initTapAnti()


func quick_anti() -> void:
    if _android_plugin:
        _android_plugin.qucikAnti()
