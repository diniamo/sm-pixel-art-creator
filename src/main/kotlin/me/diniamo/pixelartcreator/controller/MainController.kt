package me.diniamo.pixelartcreator.controller

import com.beust.klaxon.JsonObject
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Label
import javafx.scene.paint.Paint
import javafx.stage.FileChooser
import javafx.util.Duration
import me.diniamo.pixelartcreator.*
import me.diniamo.pixelartcreator.view.MainView
import tornadofx.Controller
import tornadofx.removeFromParent
import tornadofx.runLater
import tornadofx.text
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.floor

class MainController : Controller() {
    private val mainView: MainView by inject()

    val widthProperty = SimpleStringProperty()
    val heightProperty = SimpleStringProperty()

    private var selectedFile: File? = null
    lateinit var selectedFileLabel: Label

    val prettyPrintProperty = SimpleBooleanProperty()

    private val green = Paint.valueOf("#00ff00")

    fun chooseFile() {
        val files = tornadofx.chooseFile(title = "Choose an image to convert", filters = arrayOf(
            FileChooser.ExtensionFilter("Compressed JPEG images", "jpg", "jpeg"),
            FileChooser.ExtensionFilter("Slightly compressed images with transparent background", "png"),
            FileChooser.ExtensionFilter("Uncompressed, raw images", "bmp"),
            FileChooser.ExtensionFilter("Non-animated compressed images with transparent background", "gif"),
        ))
        if(files.isNotEmpty()) {
            val file = files.first()

            selectedFileLabel.text = file.name
            selectedFile = file
        }
    }

    fun generate() {
        runAsync {
            val imageData = setSize(ImageIO.read(selectedFile),
                widthProperty.get()?.toIntOrNull() ?: -1, heightProperty.get()?.toIntOrNull() ?: -1)
            val pixels = imageData.data
            val blocks = mutableListOf<JsonObject>()
            val imageWidth = pixels.width

            for(i in 0 until (imageWidth * pixels.height * 4)) {
                if(i % 4 != 0 && i != 0) continue

                val x = (i / 4) % imageWidth
                val y = floor((i / 4) / imageWidth.toDouble()).toInt()

                val pixel = pixels.getPixel(x, y, IntArray(pixels.numBands))

                blocks.add(getBlock(x, y, pixel[0].toTwoDigitString() + pixel[1].toTwoDigitString() + pixel[2].toTwoDigitString(), "628b2d61-5ceb-43e9-8334-a4135566df7a"))
            }

            val base = getBase(blocks)
            base.toJsonString(prettyPrint = prettyPrintProperty.value).also {
                exportToClipBoard(it)
            }
        } ui { _ ->
//            tornadofx.find<ResultFragment>(params = mapOf("text" to result)).openWindow(stageStyle = StageStyle.UTILITY, modality = Modality.NONE, owner = null)
            with(mainView.root) {
                text("JSON successfully copied to clipboard.") {
                    fill = green

                    runLater(Duration.seconds(3.0)) {
                        removeFromParent()
                    }
                }
            }
        }
    }
}