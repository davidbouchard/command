from java.io import BufferedReader, InputStreamReader, IOException
from java.lang import Runtime, StringBuilder, InterruptedException
from sys import stderr

class Command:
    _ERR = 'COMMAND ERROR(s):\n'
    _LF, _SPC = '\n', ' '

    def __init__(c, command):
        c.command = command
        c.success = False

        c.outputBuffer = []
        c.runtime = Runtime.getRuntime()


    def __str__(c): return c.command + Command._SPC + `c.success`

    def getOutput(c): return c.outputBuffer[:]

    def getOutputAsTuple(c): return tuple(c.outputBuffer)


    def run(c):
        c.success = False
        del c.outputBuffer[:]

        try:
            process = c.runtime.exec(c.command)
            sb = StringBuilder(Command._ERR)

            out = BufferedReader(InputStreamReader(process.inputStream))
            err = BufferedReader(InputStreamReader(process.errorStream))

            while True:
                read = out.readLine()
                if not read: break
                c.outputBuffer.append(read)

            while True:
                read = err.readLine()
                if not read: break
                sb.append(read).append(Command._LF)
            if sb.length() != len(Command._ERR): print >> stderr, sb

            c.success = not process.waitFor()

        except IOException, e:
            print >> stderr, 'COMMAND ERROR: ' + e.message

        except InterruptedException, e:
            print >> stderr, 'COMMAND INTERRUPTED: ' + e.message

        return c.success