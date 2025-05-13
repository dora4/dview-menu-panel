dview-menu-panel
![Release](https://jitpack.io/v/dora4/dview-menu-panel.svg)
--------------------------------

#### 卡片

![DORA视图 菜单面板](https://github.com/user-attachments/assets/ddd6d8fa-cbf8-4c05-9b65-4fe940774a2c)
![DORA视图 DORA战士](https://github.com/user-attachments/assets/9233a84c-422a-408a-b812-acc2bb95ec4d)

##### 卡名：Dora视图 MenuPanel
###### 卡片类型：效果怪兽
###### 属性：风
###### 星级：4
###### 种族：植物族
###### 攻击力/防御力：1500/1600
###### 效果：此卡不会因为对方卡的效果而破坏，并可使其无效化。此卡攻击里侧守备表示的怪兽时，若攻击力高于其守备力，则给予对方此卡原攻击力的伤害，并抽一张卡。此卡一回合可以攻击两次。

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
    implementation 'com.github.dora4:dview-menu-panel:1.35'
}
```

#### 使用

```kotlin
binding.menuPanel.addMenu(NormalMenuPanelItem("open_floating_permission", "打开悬浮窗权限"))
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
