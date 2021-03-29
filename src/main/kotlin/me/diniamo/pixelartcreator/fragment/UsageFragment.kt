package me.diniamo.pixelartcreator.fragment

import tornadofx.Fragment
import tornadofx.label
import tornadofx.vbox

class UsageFragment : Fragment("How to use") {
    override val root = vbox {
        label("1) select the size that you want the resulting blueprint to be in")
        label("2) select the image")
        label("3) generate the blueprint")
    }
}