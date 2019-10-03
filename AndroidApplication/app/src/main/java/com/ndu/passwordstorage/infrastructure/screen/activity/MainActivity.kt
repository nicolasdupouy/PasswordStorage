package com.ndu.passwordstorage.infrastructure.screen.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.databinding.ActivityMainBinding
import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.infrastructure.screen.adapter.ListEntryAdapter
import androidx.databinding.DataBindingUtil.setContentView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val draftDataSet = listOf(
        PasswordEntry(1, "Site 1", "login 1", "password 1"),
        PasswordEntry(2, "Site 2", "login 2", "password 2"),
        PasswordEntry(3, "Site 3", "login 3", "password 3"),
        PasswordEntry(4, "Site 4", "login 4", "password 4"),
        PasswordEntry(5, "Site 5", "login 5", "password 5"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        fillStorageList()
        setSupportActionBar(toolbar)

        setCreateAction()
    }

    private fun fillStorageList() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = ListEntryAdapter(draftDataSet)

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

        val passwordEntry = PasswordEntry(33, "site_nicolas", "login_nicolas", "password_nicolas")
        passwordEntry.putInfos(memoActivityIntent)

        Log.d("TEST_NICOLAS", "startActivityForResult MemoActivity")
        startActivityForResult(memoActivityIntent, 2 /*MemoActivity.CREATE_MEMO*/)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.d("TEST_NICOLAS", "retour de MemoActivity: ${data?.toString()} / resultCode = $resultCode")
        if (resultCode == Activity.RESULT_OK) {
            val passwordEntryUpdated = PasswordEntry.readInfos(data)

            Toast.makeText(applicationContext, passwordEntryUpdated.toString(), Toast.LENGTH_LONG).show()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


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
