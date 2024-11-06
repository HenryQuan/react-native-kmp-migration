"""
See ../README.md for more information.
"""

import os

NODE_MODULES_DIR = "../../node_modules"


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
                    "${rootDir}/../node_modules", "${rootDir}/../../node_modules"
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
                print("Patched react-native-screens")
            file.write(line)


def main():
    patch_react_native_fast_encoder()
    patch_react_native_screens()


if __name__ == "__main__":
    main()
