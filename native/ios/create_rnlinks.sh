# In order to place the iOS project outside a React Native project
# we need to create symbolic links to the React Native project

path="../../existing"

echo "Make very sure that you don't run npm install under this folder"
echo "If you do, you have to reset the symbolic links again"
# unlike the node_modules
rm "node_modules"
ln -s "$path/node_modules" "node_modules"
ln -s "$path/package.json" "package.json"
ln -s "$path/package-lock.json" "package-lock.json"
