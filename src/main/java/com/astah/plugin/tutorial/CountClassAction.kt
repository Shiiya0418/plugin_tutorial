package com.astah.plugin.tutorial

import com.change_vision.jude.api.inf.ui.IPluginActionDelegate
import kotlin.Throws
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate.UnExpectedException
import com.change_vision.jude.api.inf.ui.IWindow
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.project.ProjectAccessor
import com.change_vision.jude.api.inf.model.IModel
import com.change_vision.jude.api.inf.model.IClass
import javax.swing.JOptionPane
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException
import com.astah.plugin.tutorial.CountClassAction.CalculateUnExpectedException
import java.lang.ClassNotFoundException
import com.change_vision.jude.api.inf.model.INamedElement
import com.change_vision.jude.api.inf.model.IPackage
import java.lang.Exception
import java.util.ArrayList

class CountClassAction : IPluginActionDelegate {
    @Throws(UnExpectedException::class)
    override fun run(window: IWindow){
        try {
            val api = AstahAPI.getAstahAPI()
            val projectAccessor = api.projectAccessor
            val iCurrentProject = projectAccessor.project
            val classeList: MutableList<IClass> = ArrayList()
            getAllClasses(iCurrentProject, classeList)
            JOptionPane.showMessageDialog(window.parent,
                    "There are " + classeList.size + " classes.")
        } catch (e: ProjectNotFoundException) {
            val message = "Please open a project"
            JOptionPane.showMessageDialog(window.parent, message,
                    "Warning", JOptionPane.WARNING_MESSAGE)
            throw CalculateUnExpectedException()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(window.parent,
                    "Exception occured", "Alert", JOptionPane.ERROR_MESSAGE)
            throw UnExpectedException()
        }
    }

    inner class CalculateUnExpectedException : UnExpectedException()

    @Throws(ClassNotFoundException::class, ProjectNotFoundException::class)
    private fun getAllClasses(element: INamedElement, classList: MutableList<IClass>) {
        if (element is IPackage) {
            for (ownedNamedElement in element.ownedElements) {
                getAllClasses(ownedNamedElement, classList)
            }
        } else if (element is IClass) {
            classList.add(element)
            for (nestedClasses in element.nestedClasses) {
                getAllClasses(nestedClasses, classList)
            }
        }
    }
}