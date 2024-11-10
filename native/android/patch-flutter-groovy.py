"""
This script patches the include_flutter.groovy file to work with Kotlin Gradle DSL.
See the NOTE under https://docs.flutter.dev/add-to-app/android/project-setup#updating-settings-gradle
Any version lower than 3.27 which is currently the master branch, won't work.
"""

import os
flutter_groovy_path = "../../flutter_module/.android/include_flutter.groovy"

# https://github.com/flutter/flutter/blob/286eb1303c7cd2dcbda31504433fa890e98b28ba/packages/flutter_tools/templates/module/android/library_new_embedding/include_flutter.groovy.copy.tmpl
# See commit https://github.com/flutter/flutter/commit/d1d9954c456f6c3de85305a7130661ed194519a9
content = """
// https://github.com/flutter/flutter/blob/286eb1303c7cd2dcbda31504433fa890e98b28ba/packages/flutter_tools/templates/module/android/library_new_embedding/include_flutter.groovy.copy.tmpl
// See commit https://github.com/flutter/flutter/commit/d1d9954c456f6c3de85305a7130661ed194519a9
//
def gradle = null
def flutterProjectRoot = null

// The second block handles the original syntax for including Flutter modules, which used a Groovy
// method that isn't a part of the Kotlin Gradle DSL (setBinding). The first block handles the
// preferred way of including Flutter modules, which is to use the apply from: Gradle syntax.
if (!getBinding().getVariables().containsKey("gradle")) {
    gradle = this
    flutterProjectRoot = gradle.buildscript.getSourceFile().getParentFile().getParentFile().absolutePath
} else {
    gradle = getBinding().getVariables().get("gradle")
    def scriptFile = getClass().protectionDomain.codeSource.location.toURI()
    flutterProjectRoot = new File(scriptFile).parentFile.parentFile.absolutePath
}

gradle.include ":flutter"
gradle.project(":flutter").projectDir = new File(flutterProjectRoot, ".android/Flutter")

def localPropertiesFile = new File(flutterProjectRoot, ".android/local.properties")
def properties = new Properties()

assert localPropertiesFile.exists(), "❗️The Flutter module doesn't have a `$localPropertiesFile` file." +
                                     "You must run `flutter pub get` in `$flutterProjectRoot`."
localPropertiesFile.withReader("UTF-8") { reader -> properties.load(reader) }

def flutterSdkPath = properties.getProperty("flutter.sdk")
assert flutterSdkPath != null, "flutter.sdk not set in local.properties"
gradle.apply from: "$flutterSdkPath/packages/flutter_tools/gradle/module_plugin_loader.gradle"
"""

def patch_flutter_groovy():
    if not os.path.exists(flutter_groovy_path):
        print("Run `flutter pub get` to create the default .android folder")
    with open(flutter_groovy_path, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"Patched {flutter_groovy_path}")

if __name__ == "__main__":
    patch_flutter_groovy()