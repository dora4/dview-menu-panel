dview-menu-panel
![Release](https://jitpack.io/v/dora4/dview-menu-panel.svg)
--------------------------------

#### 卡片

![DORA视图 菜单面板](https://github.com/user-attachments/assets/ddd6d8fa-cbf8-4c05-9b65-4fe940774a2c)
![DORA视图 DORA战士](https://github.com/user-attachments/assets/9233a84c-422a-408a-b812-acc2bb95ec4d)

#### Gradle依赖配置

```groovy
// 添加以下代码到项目根目录下的build.gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
// 添加以下代码到app模块的build.gradle
dependencies {
    implementation 'com.github.dora4:dview-menu-panel:1.47'
}
```

#### 使用

```kotlin
binding.menuPanel.addMenu(NormalMenuPanelItem("open_floating_permission", "打开悬浮窗权限"))
			.addMenuGroup(groupNormalItem("这是标题", "menu1" to "菜单1", "menu2" to "菜单2"))
			.setOnPanelMenuClickListener(object : MenuPanel.OnPanelMenuClickListener {
				override fun onMenuClick(
					position: Int,
					view: View,
					menuName: String,
					item: MenuPanelItem
				) {
					when (menuName) {
						"open_floating_permission" -> {
							if (!Settings.canDrawOverlays(this@MainActivity)) {
								val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
									Uri.parse("package:$packageName"))
								startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
							}
						}
					}
				}
			})
```
