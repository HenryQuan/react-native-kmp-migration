# Native
Both Android and iOS are standalone projects acting as either a new project or an existing one. This showcases how you can use React Native or Flutter as another dependency, so it is native first. 
This is also a great way to understand how to setup different technologies either it is React Native, Flutter, or Kotlin Multiplatform.

## Issues
### Android
Patching is required for `react-native-fast-encoder` and `react-native-screens` due to my setup. If after calling `python patch-modules.py`, Android Studio is still complaining, you may need to check the path. If the React Native side is complaining about the KMP package, simply run `npm i` to install it.

#### react-native-fast-encoder
```diff
react-native-fast-encoder/android/build.gradle
- arguments "-DNODE_MODULES_DIR=${rootDir}/../node_modules"
+ arguments "-DNODE_MODULES_DIR=${rootDir}/../../node_modules"
```
This is required due my change in the project structure where my native project is simply one layer down from the root.

#### react-native-screens
```diff
react-native-screens/android/build.gradle
- rnsDefaultMinSdkVersion = 21
+ rnsDefaultMinSdkVersion = 24
```
This is probably because something else is using 24? Not sure why.

```diff
react-native-screens/android/build.gradle
- File standardRnAndroidDirLocation = file("$rootDir/../node_modules/react-native/android")
+ File standardRnAndroidDirLocation = file("$rootDir/../../existing/node_modules/react-native/android")
```
This will help to find the `react-native` folder.
