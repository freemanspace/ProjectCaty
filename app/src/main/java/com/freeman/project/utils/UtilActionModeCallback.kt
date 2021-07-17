package com.freeman.project.utils

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

/**
 * 禁止剪貼簿動作
 */
object UtilActionModeCallback : ActionMode.Callback{

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = false

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onDestroyActionMode(mode: ActionMode?) {}

    /**
     * 禁止剪貼簿
     */
    fun setEditText(editText: EditText) {
        editText.customInsertionActionModeCallback = this
        editText.customSelectionActionModeCallback = this
    }
}