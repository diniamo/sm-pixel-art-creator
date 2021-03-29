package me.diniamo.pixelartcreator

import com.beust.klaxon.JsonObject
import javafx.scene.input.Clipboard
import javafx.scene.input.DataFormat
import java.awt.Toolkit
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.StringSelection
import java.awt.image.BufferedImage
import java.text.DateFormat

fun getBlock(x: Int, y: Int, color: String, shapeId: String) = JsonObject(mapOf(
        "bounds" to JsonObject(mapOf(
                "x" to 1,
                "y" to 1,
                "z" to 1
            )
        ), "color" to "#$color",
        "pos" to JsonObject(mapOf(
                "x" to x,
                "y" to y,
                "z" to 0
            )
        ), "shapeId" to shapeId,
        "xaxis" to 1,
        "zaxis" to 3
    )
)

fun getBase(blocks: List<JsonObject>) = JsonObject(mapOf(
    "bodies" to listOf(JsonObject(mapOf("childs" to blocks))),
    "version" to 3
))

fun setSize(oldImage: BufferedImage, newWidth: Int, newHeight: Int): BufferedImage {
    val image = BufferedImage(
        if(newWidth == -1) oldImage.width else newWidth,
        if(newHeight == -1) oldImage.height else newHeight,
        BufferedImage.TYPE_INT_ARGB
    )

    image.createGraphics().drawImage(oldImage, 0, 0, image.width, image.height, null)

    return image
}

fun exportToClipBoard(text: String) {
    with(Toolkit.getDefaultToolkit().systemClipboard) {
        val selection = StringSelection(text)
        setContents(selection, selection)
    }
}

fun Int.toTwoDigitString(): String {
    val string = this.toString(16)

    return if(string.length == 1) "0$string"
    else string
}