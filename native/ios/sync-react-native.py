"""
Run npm i in the existing react-native project,
clear up pods, .xcode.env and run pod install.
"""

import subprocess
from patch_fast_encoder import patch_fast_encoder

def sync_react_native():
    # Run npm i in the existing react-native project
    subprocess.run("cd ../../existing && npm i", shell=True, check=True)

    # Clear up pods, .xcode.env and run pod install
    subprocess.run("rm -rf Pods build assets", shell=True, check=True)
    subprocess.run("rm -rf Podfile.lock main.jsbundle .xcode.env.local .xcode.env", shell=True, check=True)
    patch_fast_encoder("../../existing/node_modules/react-native-fast-encoder/ios/FastEncoderModule.h")
    subprocess.run("pod install", shell=True, check=True)

if __name__ == "__main__":
    user_input = input("Only run this script if there are any issues with React Native. Run? (y/n): ")
    if user_input.strip().lower() == "y":
        sync_react_native()
