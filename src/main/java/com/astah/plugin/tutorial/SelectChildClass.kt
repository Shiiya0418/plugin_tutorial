package com.astah.plugin.tutorial

import com.change_vision.jude.api.inf.ui.IPluginActionDelegate
import kotlin.Throws
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate.UnExpectedException
import com.change_vision.jude.api.inf.ui.IWindow
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.project.ProjectAccessor
import javax.swing.JOptionPane
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException
import com.change_vision.jude.api.inf.model.IGeneralization
import com.change_vision.jude.api.inf.presentation.INodePresentation
import java.lang.Exception

class SelectChildClass : IPluginActionDelegate{
    override fun run(window : IWindow){
        // astahAPIから、現在開いている図に関するAPIを取得する。
        val diagramViewManager = AstahAPI.getAstahAPI().viewManager.diagramViewManager
        // 選択中の図要素を取得する。
        // 取得した図要素のLinkもとの図要素を取得する。
        val targetNodes = diagramViewManager.selectedPresentations
                .filterIsInstance<INodePresentation>()
                .flatMap { node ->
                    node.links
                            .filter { it.model is IGeneralization }
                            .filter { it.target == node }
                            .map { it.source }
                }.toTypedArray()
        // 取得した図要素を選択状態にする。
        diagramViewManager.select(targetNodes)
    }
}



/*
class TemplateAction : IPluginActionDelegate {
    @Throws(UnExpectedException::class)
    override fun run(window: IWindow){
        try {
            val api = AstahAPI.getAstahAPI()
            val projectAccessor = api.projectAccessor
            projectAccessor.project
            JOptionPane.showMessageDialog(window.parent, "Hello")
        } catch (e: ProjectNotFoundException) {
            val message = "Project is not opened.Please open the project or create new project."
            JOptionPane.showMessageDialog(window.parent, message, "Warning", JOptionPane.WARNING_MESSAGE)
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(window.parent, "Unexpected error has occurred.", "Alert", JOptionPane.ERROR_MESSAGE)
            throw UnExpectedException()
        }
    }
}
 */