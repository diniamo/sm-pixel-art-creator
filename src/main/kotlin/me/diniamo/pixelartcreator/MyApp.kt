package me.diniamo.pixelartcreator

import javafx.stage.Stage
import me.diniamo.pixelartcreator.view.MainView
import tornadofx.App
import tornadofx.launch

class MyApp : App(MainView::class) {
    override fun start(stage: Stage) {
        super.start(stage)

        stage.minWidth = 500.0
        stage.minHeight = 340.0
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}