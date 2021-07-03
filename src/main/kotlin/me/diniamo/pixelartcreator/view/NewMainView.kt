package me.diniamo.pixelartcreator.view

import javafx.geometry.HPos
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.control.ContentDisplay
import javafx.scene.image.Image
import javafx.scene.shape.Rectangle
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import me.diniamo.pixelartcreator.fragment.TitleBarFragment
import tornadofx.*

class NewMainView : View("My View") {
    override val root = borderpane {
        stylesheets.add("/style/main-view.css")
        add<TitleBarFragment> {
            top = this.root
        }

        left = pane {
            borderpaneConstraints {
                marginTop = 15.0
                marginLeft = 50.0
            }

            label("Pixel Art Generator") {
                id = "title"
            }

            pane {
                translateY = 80.0

                imageview(Image(this::class.java.getResourceAsStream("/icons/descLine.svg")))
                label("This is a small program that allows you \nto create a blueprint file for an image.") {
                    id = "description"
                    translateX = 15.0
                }
            }
        }

        right = imageview(Image(this::class.java.getResourceAsStream("/icons/topGear.svg"))) {
            borderpaneConstraints {
                alignment = Pos.TOP_RIGHT
                marginRight = 50.0
                marginTop = 15.0
            }
        }

        bottom = gridpane {
            minHeight = 395.0
            borderpaneConstraints {
                marginLeft = 50.0
            }

            button("Choose\nFile") {
                prefHeight = 150.0
                prefWidth = 135.0
                id = "roundedButton"

                graphic = imageview(Image(this::class.java.getResourceAsStream("/icons/image.svg")))
                textAlignment = TextAlignment.CENTER
                contentDisplay = ContentDisplay.TOP

                gridpaneConstraints {
                    columnRowIndex(0, 0)
                }
            }

            stackpane {
                gridpaneConstraints {
                    columnRowIndex(0, 1)
                    marginTop = 114.0
                    marginLeft = 65.0
                }

                rectangle {
                    id = "roundedRect"

                    width = 335.0
                    height = 75.0
                }

                label("Generate Blueprint") {
                    id = "genBlueprint"
                    translateX = 15.0
                }
            }

            button {
                prefHeight = 150.0
                prefWidth = 135.0
                id = "roundedButton"
                gridpaneConstraints {
                    columnRowIndex(0, 1)
                    marginTop = 40.0
                }

                graphic = stackpane {
                    imageview(Image(this::class.java.getResourceAsStream("/icons/arrows.svg")))
                    imageview(Image(this::class.java.getResourceAsStream("/icons/smallGear.svg")))
                }
            }

            stackpane {
                gridpaneConstraints {
                    columnRowIndex(1, 0)
                    hAlignment = HPos.LEFT
                }

                rectangle {
                    id = "roundedRect"
                    width = 1.0
                    height = 1.0
                }
            }
        }
    }
}
