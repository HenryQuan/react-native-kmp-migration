import subprocess

def build_ios(params):
    scheme = params['scheme']
    workspace = params['workspace']
    output_directory = params['output_directory']

    output_path = f"{output_directory}/out"
    output_archive_path = f"{output_path}.xcarchive"

    # workspace needs to be passed in since we use CocoaPods
    command = f"xcodebuild archive -workspace {workspace} -scheme {scheme} "
    command += "-destination 'generic/platform=iOS' " # or iOS Simulator
    # Configuration needs to be Debug for it to even work
    command += f"-configuration Release -archivePath '{output_path}' "
    # Try to remove all asserts
    command += "SWIFT_ENABLE_ASSERTIONS=NO"
    print("Building the app for any simulator")
    
    try:
        subprocess.run(command, shell=True, check=True)
    except subprocess.CalledProcessError as e:
        print(f"Build failed: {e}")
        return

    # allow the following commands to fail
    try:
        subprocess.run(f"mv -f {output_archive_path}/Products/Applications/*.app {output_directory}", shell=True, check=True)
        subprocess.run(f"rm -r {output_archive_path}", shell=True, check=True)
    except subprocess.CalledProcessError:
        print("Failed to move the app to the output directory")
        print(f"The app is located at {output_archive_path}/Products/Applications/")
    
    print("Build successful")

if __name__ == "__main__":
    params = {
        'scheme': 'KMPReactNative',
        'workspace': 'KMPReactNative.xcworkspace',
        'output_directory': 'output'
    }
    build_ios(params)
