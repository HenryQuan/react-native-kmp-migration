# React Native with Kotlin Multiplatform

This is a quick example of how you can migrate your React Native project to Kotlin Multiplatform by firstly implementing the shared logic in Kotlin using Ktor (Service), Serialization (JSON/Model) and Coroutines (Async). Many more may work, but not tested.

After that, this shared module can be compiled to JavaScript using Kotlin/JS and used in the existing React Native project as another package with typescript definitions. This may allow a smoother transition to Kotlin Multiplatform from the existing React Native project.

The main reason for this is my React Native app wasn't well structured, so I was putting everything together in the View. A refactor is required to further maintain the project, so why not refactor it with Kotlin Multiplatform instead? Maybe, one day I can go back to full native. However, I don't want to move everything in one go because I also not sure if Kotlin Multiplatform is the right choice. A new technology may appear one day and completely change everything.

Wait, how about Flutter? Originally, I was considering Flutter and use it with React Native, see [this](https://github.com/HenryQuan/flutter-react-native). However, Flutter requires a complete rewrite, and I have to depend on it in the long run. I don't want to be locked to a single technology, if I have to, I may still pick JavaScript or Native instead in 2024.

Overall, this approach may the best for me, I can keep React Native while migrating to Kotlin Multiplatform step by step. In case, anything goes wrong, I can still go back to React Native and decide what to do next.

# Other Approaches

- https://github.com/voize-gmbh/reakt-native-toolkit
- https://github.com/ScottPierce/kotlin-react-native
- https://github.com/Foso/KotlinReactNativeMpp
- https://github.com/ptmt/kotlin-react-native-example

reakt-native-toolkit enables you to share the native module using Kotlin Multiplatform, so you can write the native module in Kotlin and share it between Android and iOS for React Native. The rest three projects are using Kotlin/JS to implement a React Native component, so they are using things like `RComponent<RProps, AppState>()`.

It is similar what I am doing here, but instead I only import my Kotlin/JS package and use it directly in my existing React Native project. There is a clear separation between the React Native and the Kotlin part.

## Issues
- ~~In order to get React Native running in native projects, the project has to be inside a node project. Some dependencies are assuming that the Android/iOS folder is under a React Native folder. This makes sense, but means I have to place them under the existing project. It is not a big deal however~~
    - This is a minus however due to this constraint
    - Depending on the dependencies, it may work without placing them under a node project
    - ***Update: this is no longer an issue if some patches are used, but make sure the path is correct***
