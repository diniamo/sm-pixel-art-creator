package me.diniamo.pixelartcreator.fragment

import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.shape.FillRule
import javafx.scene.text.FontWeight
import tornadofx.*

class TitleBarFragment : Fragment("My View") {
    var x = 0.0
    var y = 0.0

    override val root = hbox {
        stylesheets.add("/style/title-bar.css")

        prefHeight = 50.0
        prefWidth = 900.0
        alignment = Pos.CENTER

        label("Pixel Art Generator") {
            alignment = Pos.CENTER_LEFT

            hboxConstraints {
                prefWidth = 900.0
                marginLeft = 50.0
            }
        }

        button {
            alignment = Pos.CENTER_RIGHT
            graphic = imageview(Image(this::class.java.getResourceAsStream("/icons/minimize.svg"))) {
                alignment = Pos.CENTER
            }

            minWidth = 50.0
            maxWidth = 50.0
            prefHeight = 50.0

            action {
                primaryStage.isIconified = true
            }
        }
        button {
            alignment = Pos.CENTER_RIGHT
            graphic = imageview(Image(this::class.java.getResourceAsStream("/icons/close.svg"))) {
                alignment = Pos.CENTER
            }

            minWidth = 50.0
            maxWidth = 50.0
            prefHeight = 50.0

            action {
                primaryStage.close()
            }
        }

        onMouseDragged = EventHandler { event ->
            primaryStage.x = event.screenX + x
            primaryStage.y = event.screenY + y
        }
        onMousePressed = EventHandler { event ->
            x = primaryStage.x - event.screenX
            y = primaryStage.y - event.screenY
        }
    }
}
