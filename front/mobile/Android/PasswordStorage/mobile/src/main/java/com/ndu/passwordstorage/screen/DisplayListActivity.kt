package com.ndu.passwordstorage.screen

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.adapter.DisplayListEntryAdapter
import com.ndu.passwordstorage.data.PasswordDatabase
import com.ndu.passwordstorage.data.PasswordDatabaseImpl
import com.ndu.passwordstorage.model.PasswordEntry

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

class DisplayListActivity : ListActivity() {

    private var passwordDatabase: PasswordDatabase? = null
    private var namesAdapter: ArrayAdapter<PasswordEntry>? = null
    private var unbinder: Unbinder? = null

    @BindView(R.id.fab)
    internal var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_list)
        unbinder = ButterKnife.bind(this)
        passwordDatabase = PasswordDatabaseImpl(applicationContext)

        registerForContextMenu(listView)
        defineDisplay()
        setCreateAction()
    }

    private fun defineDisplay() {
        this.namesAdapter = DisplayListEntryAdapter(this, passwordDatabase!!.select())
        listView.adapter = namesAdapter
    }

    private fun setCreateAction() {
        fab!!.setOnClickListener { view -> createMemo() }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
        passwordDatabase!!.closeDatabase()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        menu.add(0, CONTEXT_MENU_DELETE_ID, 0, R.string.delete)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == CONTEXT_MENU_DELETE_ID) {
            val currentPositionInList = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
            deleteMemo(currentPositionInList)
            updateEntryList()
        }

        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            val passwordEntryUpdated = PasswordEntry.readInfos(data)

            if (requestCode == MemoActivity.CREATE_MEMO) {
                passwordDatabase!!.insert(passwordEntryUpdated)
            } else if (requestCode == MemoActivity.DISPLAY_MEMO) {
                passwordDatabase!!.update(passwordEntryUpdated)
            }

            updateEntryList()
        }
    }

    private fun updateEntryList() {
        this.namesAdapter!!.clear()
        this.namesAdapter!!.addAll(passwordDatabase!!.select())

    }

    private fun createMemo() {
        val memoActivityIntent = Intent(this, MemoActivity::class.java)

        val passwordEntry = PasswordEntry.makeNew()
        passwordEntry.putInfos(memoActivityIntent)

        startActivityForResult(memoActivityIntent, MemoActivity.CREATE_MEMO)
    }

    private fun deleteMemo(position: Int) {
        val passwordEntries = passwordDatabase!!.select()
        val passwordEntry = passwordEntries[position]
        passwordDatabase!!.delete(passwordEntry)
    }

    fun displayMemo(position: Int) {
        val memoActivityIntent = Intent(this, MemoActivity::class.java)

        val passwordEntries = passwordDatabase!!.select()
        val passwordEntry = passwordEntries[position]
        passwordEntry.putInfos(memoActivityIntent)

        startActivityForResult(memoActivityIntent, MemoActivity.DISPLAY_MEMO)
    }

    companion object {

        private val CONTEXT_MENU_DELETE_ID = 1
    }
}
