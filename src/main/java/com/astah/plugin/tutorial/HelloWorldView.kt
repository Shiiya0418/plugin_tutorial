package com.astah.plugin.tutorial

import javax.swing.JPanel
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView
import com.change_vision.jude.api.inf.project.ProjectEventListener
import java.awt.BorderLayout
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.project.ProjectAccessor
import java.lang.ClassNotFoundException
import javax.swing.JLabel
import javax.swing.JScrollPane
import com.change_vision.jude.api.inf.project.ProjectEvent
import com.change_vision.jude.api.inf.ui.ISelectionListener
import java.awt.Component
import java.awt.Container

class HelloWorldView : JPanel(), IPluginExtraTabView, ProjectEventListener {
    private fun initComponents() {
        layout = BorderLayout()
        add(createLabelPane(), BorderLayout.CENTER)
        addProjectEventListener()
    }

    private fun addProjectEventListener() {
        try {
            val api = AstahAPI.getAstahAPI()
            val projectAccessor = api.projectAccessor
            projectAccessor.addProjectEventListener(this)
        } catch (e: ClassNotFoundException) {
            e.message
        }
    }

    private fun createLabelPane(): Container {
        val label = JLabel("hello world")
        return JScrollPane(label)
    }

    override fun projectChanged(e: ProjectEvent) {}
    override fun projectClosed(e: ProjectEvent) {}
    override fun projectOpened(e: ProjectEvent) {}
    override fun addSelectionListener(listener: ISelectionListener) {}
    override fun getComponent(): Component {
        return this
    }

    override fun getDescription(): String {
        return "Show Hello World here"
    }

    override fun getTitle(): String {
        return "Hello World View"
    }

    override fun activated() {}
    override fun deactivated() {}

    init {
        initComponents()
    }
}