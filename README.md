# React Native with Kotlin Multiplatform

This is a quick example of how you can migrate your React Native project to Kotlin Multiplatform by firstly implementing the shared logic in Kotlin using Ktor (Service), Serialization (JSON/Model) and Coroutines (Async). Many more may work, but not tested. This is heavily depending on what Meta implements in their Hermes JS engine. For example, the fetch response doesn't support streaming in Ktor 2, and a patch is required to fix this issue, see `kmp/migration-build-move.py`.

After that, this shared module can be compiled to JavaScript using Kotlin/JS and imported in the existing React Native project as another package with typescript definitions. This may allow a smoother transition to Kotlin Multiplatform from the existing React Native project.

The main reason for this is my React Native app wasn't well structured, so I was putting everything together in the View. A refactor is required to further maintain the project, so why not refactor it with Kotlin Multiplatform instead? Maybe, one day I can go back to full native. However, I don't want to move everything in one go, because I am also not sure if Kotlin Multiplatform is the right choice. A new technology may appear one day and could change everything completely.

Wait, how about Flutter? Originally, I was considering Flutter and use it with React Native, see [this](https://github.com/HenryQuan/flutter-react-native). However, Flutter requires a complete rewrite, and I have to depend on it in the long run. I don't want to be locked to a single technology, if I have to, I may still pick JavaScript or Native instead in 2024.

Overall, this approach may be the best for me, I can keep React Native while migrating to Kotlin Multiplatform step by step. In case, anything goes wrong, I can still go back to React Native and decide what to do next.

# Folder Structure
Some patches and hacks are applied in order to place the root folders like they are. React Native apps assume that the Android/iOS folder should be under a node project, and this has caused lots of issues while setting up native apps to integrate React Native manually. Kotlin in general fine wherever you put it.

- existing (React Native)
  - android
  - ios
  - node_modules
  - src (js/ts source code)
- flutterapp
  - android
  - ios
  - lib
- kmp (Kotlin Multiplatform with Compose Multiplatform)
  - migration (the main project to share between iOS, Android & React Native)
    - src (Kotlin files)
      - share Service/UseCase/Model/Core Logic here and use multiplatform
    - build.gradle.kts (project plugins, libraries and other settings)
    - KmpMigration.podspec (for Xcode, cocoapods)
  - build.gradle.kts
  - kmp.versions.toml
    - Use any name as long as it is not libs.versions.toml as it may have some problems with the default `libs`
      ```
      versionCatalogs {
          create("kmp") { // <- don't use libs, and should be fine in general
              from(files("../../kmp/kmp.versions.toml"))
          }
      }
      ```
  - settings.gradles.kts
- native (New or Existing native projects)
  - ios
    - Other folders for Swift code
    - Gemfile (for React Native)
    - Podfile (for React Native, Flutter & Kotlin)
    - node_modules (link with existing/node_modules for React Native)
    - package.json (link with existing/package.json for React Native)
      - `Make sure not to call npm i under this project`
      - These three links are needed to trick React Native and fix many path issues
    - package-lock.json (link with existing/package-lock.json for React Native)
    - create_rnlinks.sh (fix React Native issues)
  - android
    - app
      - src (kotlin/java source code)
      - build.gradle.kts (android project settings)
    - build.gradle.kts
    - patch-modules.py (fix some node_modules required)
    - settings.gradle.kt (setup React Native, Flutter & Kotlin)

This could be a good reference for setting up React Native, Flutter and Kotlin/Compose Multiplatform. React Native is extremely challenging to setup and requires quite a few patches or hacks to even sync and run. Kotlin is in general easier, but require some understandings on the cocoapods/framework part. 

# Other Approaches

- https://github.com/voize-gmbh/reakt-native-toolkit
- https://github.com/ScottPierce/kotlin-react-native
- https://github.com/Foso/KotlinReactNativeMpp
- https://github.com/ptmt/kotlin-react-native-example

reakt-native-toolkit enables you to share the native module using Kotlin Multiplatform, so you can write the native module once in Kotlin and share it between Android and iOS for React Native. The rest three projects are using Kotlin/JS to implement a React Native class component, so they are using things like `RComponent<RProps, AppState>()`.

It is similar what I am doing here, but instead I only import my Kotlin/JS package and use it directly in my existing React Native project. There is a clear separation between the React Native and the Kotlin part.

# Other Kotlin Projects

- https://github.com/HenryQuan/objc-swift-kotlin
  - Compare performance between Kotlin, Swift, Objective-C and C at assembly level
- https://github.com/HenryQuan/kmm-demo
- https://github.com/HenryQuan/kotlin-cpp
- https://github.com/wowsinfo/PersonalRatingService

## Issues

- ~~In order to get React Native running in native projects, the project has to be inside a node project. Some dependencies are assuming that the Android/iOS folder is under a React Native folder. This makes sense, but means I have to place them under the existing project. It is not a big deal however~~
  - This is a minus however due to this constraint
  - Depending on the dependencies, it may work without placing them under a node project
  - **_Update: this is no longer an issue if some patches are used, but make sure the path is correct_**
