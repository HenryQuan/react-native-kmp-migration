"""
See ../README.md for more information.
"""

import os

NODE_MODULES_DIR = "../../existing/node_modules"


def _read_file(file_path):
    if not os.path.exists(file_path):
        print(f"File not found: {file_path}")
        return None
    with open(file_path, "r") as file:
        lines = file.readlines()
    return lines


def patch_react_native_fast_encoder():
    file_path = f"{NODE_MODULES_DIR}/react-native-fast-encoder/android/build.gradle"
    lines = _read_file(file_path)

    with open(file_path, "w") as file:
        for line in lines:
            if "-DNODE_MODULES_DIR" in line:
                line = line.replace(
                    "${rootDir}/../node_modules", "${rootDir}/../../existing/node_modules"
                )
                print("Patched react-native-fast-encoder")
            file.write(line)


def patch_react_native_screens():
    file_path = f"{NODE_MODULES_DIR}/react-native-screens/android/build.gradle"
    lines = _read_file(file_path)

    with open(file_path, "w") as file:
        for line in lines:
            if "rnsDefaultMinSdkVersion" in line:
                line = line.replace("21", "24")
                print("Patched react-native-screens minSdkVersion")
            if 'file("$rootDir/../node_modules/react-native/android")' in line:
                line = line.replace(
                    'file("$rootDir/../node_modules/react-native/android")',
                    # this must point to the react-native android folder
                    'file("$rootDir/../../existing/node_modules/react-native/android")',
                )
                print("Patched react-native-screens android path")
            file.write(line)

def patch_react_native_safe_area_context():
    # need to target Android SDK 34, see https://github.com/flutter/flutter/issues/153281
    # there are many similar issues, but in the end, make sure to target Android SDK 34
    # if AAPT: error: resource android:attr/lStar not found is triggered
    file_path = f"{NODE_MODULES_DIR}/react-native-safe-area-context/android/build.gradle"
    lines = _read_file(file_path)

    # replace compileSdkVersion getExtOrDefault('compileSdkVersion', xx)
    with open(file_path, "w") as file:
        for line in lines:
            if "compileSdkVersion getExtOrDefault('compileSdkVersion'" in line:
                line = "    compileSdkVersion getExtOrDefault('compileSdkVersion', 34)"
                print("Patched react-native-safe-area-context compileSdkVersion")
            file.write(line)

def main():
    patch_react_native_fast_encoder()
    patch_react_native_screens()
    patch_react_native_safe_area_context()


if __name__ == "__main__":
    main()
