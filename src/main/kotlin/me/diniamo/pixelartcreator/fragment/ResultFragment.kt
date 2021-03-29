package me.diniamo.pixelartcreator.fragment

import tornadofx.Fragment
import tornadofx.textarea

class ResultFragment : Fragment("Result") {
    override val root = textarea(params["text"] as? String) {

    }
}