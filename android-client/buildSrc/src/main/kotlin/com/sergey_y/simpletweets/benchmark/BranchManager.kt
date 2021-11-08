package com.sergey_y.simpletweets.benchmark

import java.io.File

class BranchManager(
    private val projectDir: File
) {
    fun switchBranch(onBranchSwitched: () -> Unit) {
        for (branch in composeVersionBranches) {
            val (code, message) = "git checkout main".runCommand(projectDir)
            println(message)
            if (code == 0) {
                onBranchSwitched()
            }
        }
    }

    fun gradleBuild() = println("sh gradlew clean".runCommand(projectDir))

    companion object {
        val composeVersionBranches = listOf(
            "compose-1.0.2",
            "compose-1.0.3",
            "compose-1.0.4",
            "compose-1.1.0-beta01",
        )
    }
}


private fun String.runCommand(workingDir: File): Pair<Int, String?> {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        proc.waitFor(1, java.util.concurrent.TimeUnit.MINUTES)
        val exitCode = proc.exitValue()
        return if (exitCode != 0) {
            exitCode to proc.errorStream.bufferedReader().readText()
        } else {
            exitCode to proc.inputStream.bufferedReader().readText()
        }
    } catch (e: java.io.IOException) {
        e.printStackTrace()
        return 1 to null
    }
}