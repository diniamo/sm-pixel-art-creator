package me.diniamo.pixelartcreator

import com.beust.klaxon.JsonObject
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.image.BufferedImage
import java.io.File
import java.util.*

fun saveBlueprint(json: String): UUID {
    val uuid = UUID.randomUUID()
    val blueprintFolder = getNewBlueprintFolder(uuid)
    println(blueprintFolder.path)
    val blueprintJson = File(blueprintFolder.path + "\\blueprint.json").also { it.createNewFile() }
    val descriptionJson = File(blueprintFolder.path + "\\description.json").also { it.createNewFile() }

    blueprintJson.writeText(json)
    descriptionJson.writeText(
        getDescription(uuid.toString()).toJsonString(prettyPrint = true)
    )

    return uuid
}

private val APPDATA = System.getenv("APPDATA")
fun getNewBlueprintFolder(uuid: UUID): File {
    val smUsers = File("$APPDATA\\Axolot Games\\Scrap Mechanic\\User")
    val latestModified = smUsers.listFiles()?.filter(File::isDirectory)?.maxByOrNull { it.lastModified() } ?: throw IllegalAccessException("No Scrap Mechanic users found")
    return File("${latestModified.path}\\Blueprints\\${uuid}").also { it.mkdir() }
}

fun getDescription(uuid: String) = JsonObject(mapOf(
    "description" to "#{STEAM_WORKSHOP_NO_DESCRIPTION}",
    "localId" to uuid,
    "name" to uuid,
    "type" to "Blueprint",
    "version" to 0
))

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