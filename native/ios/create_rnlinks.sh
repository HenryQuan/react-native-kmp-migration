# In order to place the iOS project outside a React Native project
# we need to create symbolic links to the React Native project

path="../../existing"

ln -s "$path/node_modules" "node_modules"
ln -s "$path/package.json" "package.json"
ln -s "$path/package-lock.json" "package-lock.json"
