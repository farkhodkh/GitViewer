package ru.farkhodkhaknazarov.gitviewer.viewes

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class AbstractGistsFragments: Fragment()  {
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}