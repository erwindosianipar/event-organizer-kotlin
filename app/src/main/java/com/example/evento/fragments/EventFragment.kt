package com.example.evento.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.evento.CreateEventActivity

import com.example.evento.R
import com.example.evento.UserViewModel
import com.example.evento.models.UserList
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.fragment_profile.*


class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_event, container, false)

        val userViewModel = activity?.run {
            ViewModelProviders.of(requireActivity()).get(UserViewModel::class.java)
        }

        userViewModel?.users?.observe(this, Observer {
                user-> setUser(user)
        })

        view.fabAdd.setOnClickListener {
            startActivity(Intent(activity,CreateEventActivity::class.java))
        }

        return view
    }

    private fun setUser(user: UserList?) {
        if(user?.Users?.submission_status == "submitted") {
            // fabAdd.visibility = View.GONE
            Toast.makeText(context, "You must be an event organizer", Toast.LENGTH_SHORT).show()
            fabAdd.isClickable = false
        } else if (user?.Users?.submission_status == "not_submit") {
            Toast.makeText(context, "You must wait to confirm your submission event organizer", Toast.LENGTH_SHORT).show()
            fabAdd.isClickable = false
        } else if (user?.Users?.submission_status == "accepted") {
            fabAdd.isClickable = true
        }
        Log.d("User","INI USER")
    }
}
