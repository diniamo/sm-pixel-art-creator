package me.diniamo.pixelartcreator.view

import me.diniamo.pixelartcreator.fragment.TitleBarFragment
import tornadofx.*

class NewMainView : View("My View") {
    override val root = anchorpane {
        add<TitleBarFragment>()
    }
}
