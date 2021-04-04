package me.diniamo.pixelartcreator

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory
import de.codecentric.centerdevice.javafxsvg.dimension.PrimitiveDimensionProvider
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import me.diniamo.pixelartcreator.view.NewMainView
import tornadofx.App
import tornadofx.importStylesheet
import tornadofx.launch
import tornadofx.loadFont
import java.io.File

class MyApp : App(NewMainView::class) {
    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.TRANSPARENT)
        super.start(stage)
        stage.scene.fill = Color.TRANSPARENT

        stage.minWidth = 900.0
        stage.minHeight = 675.0

        importStylesheet("/style/dark-mode.css")
        importStylesheet("/style/application.css")
        importStylesheet("file:///" + File("test.css").absolutePath.replace("\\", "/"))
    }
}

fun main(args: Array<String>) {
    SvgImageLoaderFactory.install(PrimitiveDimensionProvider())
    launch<MyApp>(args)
}