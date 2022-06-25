package com.mimo.poketeamapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mimo.poketeamapp.login.LoginActivity

class LogoutConfirmationDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.logout_confirmation))
            .setPositiveButton(getString(R.string.logout_confirmation_ok)) { _,_ ->
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel)) { _,_ -> dismiss() }
            .create()

    companion object {
        const val TAG = "LogoutConfirmationDialog"
    }
}
