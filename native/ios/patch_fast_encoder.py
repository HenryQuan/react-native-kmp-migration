

# where is the FastEncoderModuleSpec.h?
patched_version = """
#ifdef __cplusplus
#import "react-native-fast-encoder.h"
#endif

#import <React/RCTBridgeModule.h>
@interface FastEncoderModule : NSObject <RCTBridgeModule>
@property (nonatomic, assign) BOOL setBridgeOnMainQueue;

@end
""".strip()
import os

def patch_fast_encoder(path: str):
    with open(path, 'w') as f:
        f.write(patched_version)

if __name__ == "__main__":
    patch_fast_encoder("../../existing/node_modules/react-native-fast-encoder/ios/FastEncoderModule.h")
    print("Patched FastEncoderModule.h")
    os.system("pod install")
