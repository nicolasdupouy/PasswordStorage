package com.ndu.passwordstorage.infrastructure.screen.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.databinding.ActivityMainBinding
import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.infrastructure.screen.adapter.ListEntryAdapter
import androidx.databinding.DataBindingUtil.setContentView
import com.ndu.passwordstorage.domain.dao.PasswordDao
import com.ndu.passwordstorage.application.utils.Injector

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var passwordDao: PasswordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        passwordDao = Injector.getPasswordDao(applicationContext)

        fillStorageList()
        setSupportActionBar(toolbar)

        setCreateAction()
    }

    private fun fillStorageList() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = ListEntryAdapter(this, passwordDao.selectAll())

        storage_list.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    private fun setCreateAction() {
        fab.setOnClickListener { view -> createMemo() }
    }

    private fun createMemo() {
        val memoActivityIntent = Intent(this, MemoActivity::class.java)
        startActivityForResult(memoActivityIntent, MemoActivity.CREATE_MEMO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                val pe = data.getParcelableExtra<PasswordEntry>(MemoActivity.EXCHANGE_DATA)
                Toast.makeText(applicationContext, pe.toString(), Toast.LENGTH_LONG).show()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun displayMemo(position: Int) {
        val memoActivityIntent = Intent(this, MemoActivity::class.java)

        val passwordEntries = passwordDao.selectAll()
        val passwordEntry = passwordEntries.get(position)
        memoActivityIntent.putExtra(MemoActivity.EXCHANGE_DATA, passwordEntry)
        startActivityForResult(memoActivityIntent, MemoActivity.DISPLAY_MEMO)
    }

    fun displayAlert(entryItem: PasswordEntry) {
        val items = arrayOf<CharSequence>("delete", "show password")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an action")
        builder.setItems(items) { dialog, item ->
            when (item) {
                0 -> deleteMemo(entryItem)
                1 -> showPassword(entryItem)
                else -> Toast.makeText(this, "Entry not found item = ${item}", Toast.LENGTH_SHORT).show()
            }
        }
        val alert = builder.create()
        alert.show()
    }

    private fun deleteMemo(passwordEntry: PasswordEntry) {
        Toast.makeText(this, "DeleteMemo ${passwordEntry}", Toast.LENGTH_SHORT).show()
    }

    private fun showPassword(passwordEntry: PasswordEntry) {
        Toast.makeText(this, "ShowPassword ${passwordEntry}", Toast.LENGTH_SHORT).show()
    }

    // Action Bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
