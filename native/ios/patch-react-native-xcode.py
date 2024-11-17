"""
PROJECT_ROOT=${PROJECT_ROOT:-"$PROJECT_DIR/.."}
needs to be updated in this structure, so it uses PROJECT_DIR directly,
Xcode calls this script to patch the script automatically.
"""

def patch_react_native_xcode(path: str):
    with open(path, 'r') as f:
        lines = f.readlines()

    target_line = None
    for i, line in enumerate(lines):
        if "PROJECT_ROOT=${PROJECT_ROOT:-\"$PROJECT_DIR/..\"}" in line:
            target_line = i
            break
    if target_line is None:
        print("Line not found")
        return

    lines[target_line] = 'PROJECT_ROOT=${PROJECT_ROOT:-"$PROJECT_DIR/"}'

    with open(path, 'w') as f:
        f.writelines(lines)

if __name__ == "__main__":
    patch_react_native_xcode("node_modules/react-native/scripts/react-native-xcode.sh")
