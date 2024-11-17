// run all patches and ensure node_modules is up to date from ../../existing

// probably need to have a bit different command for windows
def isWindows = System.properties['os.name'].toLowerCase().contains('windows')

def commands = [
    isWindows ? "cmd /c cd ../../existing && npm install" : "cd ../../existing && npm install",
    isWindows ? "cmd /c cd ../../flutter_module && flutter pub get" : "cd ../../flutter_module && flutter pub get",
    isWindows ? "python patch-flutter-groovy.py" : "python3 patch-flutter-groovy.py",
    isWindows ? "python patch-node-modules.py" : "python3 patch-node-modules.py"
]

commands.each { command ->
    println "Executing command: $command"
    def proc = command.execute()
    proc.waitForProcessOutput(System.out, System.err)

    def exitValue = proc.exitValue()
    if (exitValue != 0) {
        throw new RuntimeException("Failed to execute command: $command")
    }
}
