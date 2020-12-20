package com.hok.forgod.pojo.service

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.hok.forgod.R

class WallpaperDialog : AppCompatDialogFragment() {
    private var listener: WallpaperDialogListener? = null
 override   fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom)
        builder.setTitle(getString(R.string.menu_wallpaper))
            .setMessage(getString(R.string.savewallpaper))
            .setNegativeButton(
                getString(R.string.cancel),
                DialogInterface.OnClickListener { dialogInterface, i -> })
            .setPositiveButton(
                getString(R.string.yes),
                DialogInterface.OnClickListener { dialogInterface, i -> listener!!.onYesClicked() })
        return builder.create()
    }

    interface WallpaperDialogListener {
        fun onYesClicked()
    }

   override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as WallpaperDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement WallpaperDialogListener")
        }
    }
}