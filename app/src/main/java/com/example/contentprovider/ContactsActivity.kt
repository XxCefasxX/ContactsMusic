package com.example.contentprovider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.contentprovider.databinding.ActivityContactsBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "ContactsActivity"
private const val REQUEST_CODE_READ_CONTACTS = 1

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    private lateinit var contactNames: ListView

    // private var readGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactNames = findViewById(R.id.contact_names)

        val hasReadContactsPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        )

        if (hasReadContactsPermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: requesting permission")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_READ_CONTACTS
            )
        }
        else{
            val home = Intent(this,MainActivity::class.java)
            startActivity(home)
        }
        getContacts()

    }

    private fun getContacts() {

        // if (readGranted) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);

            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
            )

            val contacts = ArrayList<String>()
            cursor?.use {
                while (it.moveToNext()) {
                    contacts.add(it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)))
                }
            }

            val adapter =
                ArrayAdapter<String>(this, R.layout.contact_item, R.id.tv_contact_name, contacts)
            contactNames.adapter = adapter
        } else {
            Snackbar.make(
                binding.root,
                "Please grant access to your Contacts",
                Snackbar.LENGTH_LONG
            ).setAction("Grant Access") {
                Log.d(TAG, "Snackbar onClick: starts")

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    Log.d(TAG, "Snackbar onClick: calling request permission")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        REQUEST_CODE_READ_CONTACTS
                    )
                } else {
                    Log.d(TAG, "Snackbar onClick: calling request permission")
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", this.packageName, null)
                    Log.d(TAG, "Snackbar onClick: Uri is $uri")
                    intent.data = uri
                    this.startActivity(intent)
                }
                Log.d(TAG, "Snackbar onClick: ends")

                Toast.makeText(it.context, "Snackbar action clicked", Toast.LENGTH_SHORT).show()
            }.show()
        }

        Log.d(TAG, "Fab onClick: ends")
    }

    override fun onResume() {
        super.onResume()
        getContacts()
    }
}