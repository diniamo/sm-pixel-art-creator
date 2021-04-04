package me.diniamo.pixelartcreator.view

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import me.diniamo.pixelartcreator.controller.MainController
import tornadofx.*

class MainView : View("Pixel Art Generator") {
    private val controller: MainController by inject()

    override val root = vbox(alignment = Pos.TOP_CENTER) {
        label("Pixel Art Generator") {
            style {
                fontWeight = FontWeight.EXTRA_BOLD
                fontSize = 50.px
            }
        }
        label("This is a small concept that allows you to create a blueprint file for an image.") {
            vboxConstraints {
                marginBottom = 20.0
            }
        }

        vbox(alignment = Pos.CENTER) {
            hbox(alignment = Pos.CENTER) {
                label("Width: ")
                textfield(controller.widthProperty)
            }
            hbox(alignment = Pos.CENTER) {
                label("Height: ")
                textfield(controller.heightProperty)
            }
            hbox(alignment = Pos.CENTER) {
                vboxConstraints {
                    marginTop = 10.0
                    marginBottom = 10.0
                }

                label("File: ")
                button("Choose file") {
                    action {
                        controller.chooseFile()
                    }
                }
                controller.selectedFileLabel = label("No file chosen") {
                    translateX = 10.0
                }
            }
            checkbox(text = "Pretty Print", property = controller.prettyPrintProperty) {
                alignment = Pos.CENTER
            }
        }

        button("Generate") {
            vboxConstraints {
                marginTop = 25.0
            }

            action {
                controller.generate()
            }
        }
    }
}
