package com.simple.tweet.util

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import kotlin.random.Random

object Util {
    private fun loadImages() {
        val imageDir = File(".", "post_images")
        imageDir.mkdir()

        val client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build()
        generateImageUrls().forEach { (imgId, url) ->
            val uri: URI = URI.create(url)
            val request: HttpRequest = HttpRequest.newBuilder().uri(uri).build()
            val input = client.send(request, HttpResponse.BodyHandlers.ofInputStream()).body()
            val img = File(imageDir, "${imgId}.jpg")
            val out = img.outputStream()
            input.copyTo(out)
            out.close()
            input.close()
            compress(img)
            println("Downloaded image ${imgId}.jpg")
        }
    }


    private fun compress(img: File) {
        val input = img
        val image = ImageIO.read(input)

        val output = img
        val out: OutputStream = FileOutputStream(output)

        val writer = ImageIO.getImageWritersByFormatName("jpg").next()
        val ios = ImageIO.createImageOutputStream(out)
        writer.output = ios

        val param = writer.defaultWriteParam
        if (param.canWriteCompressed()) {
            param.compressionMode = ImageWriteParam.MODE_EXPLICIT
            param.compressionQuality = 0.65f
        }

        writer.write(null, IIOImage(image, null, null), param)

        out.close()
        ios.close()
        writer.dispose()

    }

    private fun generateImageUrls(): List<Pair<Int, String>> {
        val rnd = Random(System.nanoTime())
        return (1 .. 200).map { id ->
            (id - 1) to "https://picsum.photos/seed/${id}/1024/768"
        }
    }

}