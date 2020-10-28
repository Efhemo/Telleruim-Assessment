package com.efhem.farmapp.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

object Utils {

    fun removeBackSlash(url: String): String{
        return url.replace("\\","")
    }

    fun isEmail( value: String): Boolean {
        return value.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$".toRegex())
    }

    fun showDatePicker(
        fragmentManager: FragmentManager,
        /*initialDate: Date?,*/
        callback: ((chosenDate: String) -> Unit)?
    ) {
        class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val c: Calendar = Calendar.getInstance()
                //initialDate?.let { c.time = it }
                val dialog = DatePickerDialog(requireContext(),
                    this,
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH))
                dialog.datePicker.maxDate = Date().time
                return dialog
            }

            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                //callback?.invoke(String.format("%04d-%02d-%02d", year, month + 1, day))
                callback?.invoke(String.format("%02d-%02d-%04d", day, month + 1, year))
            }
        }

        val datePickerDialog = DatePickerFragment()
        datePickerDialog.show(fragmentManager, "datePicker")
    }

    fun EditText.disableTextSelection() {
        this.customSelectionActionModeCallback = object : android.view.ActionMode.Callback {
            override fun onActionItemClicked(mode: android.view.ActionMode?, item: MenuItem?): Boolean {
                return false
            }
            override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
                return false
            }
            override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
                return false
            }
            override fun onDestroyActionMode(mode: android.view.ActionMode?) {
            }
        }
    }
}