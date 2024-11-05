# Native
Both Android and iOS are standalone projects acting as either a new project or an existing one. This showcases how you can use React Native or Flutter as another dependency, so it is native first. 
This is also a great way to understand how to setup different technologies either it is React Native, Flutter, or Kotlin Multiplatform.

## Issues
Patching is required for `react-native-fast-encoder` and `react-native-screens` due to my setup. 

### react-native-fast-encoder
```diff
react-native-fast-encoder/android/build.gradle
- arguments "-DNODE_MODULES_DIR=${rootDir}/../node_modules"
+ arguments "-DNODE_MODULES_DIR=${rootDir}/../../node_modules"
```
This is required due my change in the project structure where my native project is simply one layer down from the root.

### react-native-screens
```diff
react-native-screens/android/build.gradle
- rnsDefaultMinSdkVersion = 21
+ rnsDefaultMinSdkVersion = 24
```
This is probably because something else is using 24? Not sure why.
