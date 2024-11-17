# Native
Both Android and iOS are standalone projects acting as either a new project or an existing one. This showcases how you can use React Native or Flutter as another dependency, so it is native first. 
This is also a great way to understand how to setup different technologies either it is React Native, Flutter, or Kotlin Multiplatform.

Ensure node 18+ is used for react native to run correctly.

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

### iOS
Clearly, the React Native module doesn't run on macOS, make sure to run on an iOS device instead to avoid weird issues. Flutter also doesn't support simulator release build, so connect a physical device for the release build.

React Native also requires some additional setups in Xcode to bundle release bundle/assets. Make sure to use the following command instead of the one from the official website:
```
set -x -e
REACT_NATIVE_DIR="node_modules/react-native"
WITH_ENVIRONMENT="$REACT_NATIVE_PATH/scripts/xcode/with-environment.sh"
REACT_NATIVE_XCODE="$REACT_NATIVE_PATH/scripts/react-native-xcode.sh"
# the default config doesn't seem to work, use the local environment config instead
/bin/bash -c "CONFIG_CMD='npx react-native config' $WITH_ENVIRONMENT $REACT_NATIVE_XCODE"
```
This may or may not needed, add it if the project doesn't build.

Hermes engine script error can be fixed by removing `.xcode.env.local` environment files, see https://github.com/facebook/react-native/issues/42221#issuecomment-1895955415. Use the `sync-react-native.py` to resync everything related to React Native.

Xcode may use `ArchivedData`, so it could be a good idea to clear it in case any errors occurs.

`metro.config.js` has to be also linked under the iOS folder for the final bundle to work correctly. 
