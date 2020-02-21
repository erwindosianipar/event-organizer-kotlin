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
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.evento.*

import com.example.evento.models.UserList
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.event_item.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.slide_item_container.*
import java.io.FileOutputStream


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View =inflater.inflate(R.layout.fragment_profile, container, false)


        val userViewModel = activity?.run {
            ViewModelProviders.of(requireActivity()).get(UserViewModel::class.java)
        }

        userViewModel?.users?.observe(this, Observer {
                user-> setUser(user)
        })


        view.btnUpgradeAccount.setOnClickListener {
            startActivity(Intent(activity,UpgradeAccountActivity::class.java))
        }

        view.imgSetting.setOnClickListener {
            startActivity(Intent(activity,SettingActivity::class.java))
            activity?.finish()
        }

//        view.btnCreateEvent.setOnClickListener {
//            startActivity(Intent(activity,CreateEventActivity::class.java))
//        }

        return view
    }

    private fun setUser(user: UserList?) {
        txtInfoName.setText(user!!.Users.name)
        txtInfoEmail.setText(user.Users.email)
        if(user.Users.submission_status == "submitted") {
            btnUpgradeAccount.visibility = View.GONE
            txtWaitingForApproval.visibility = View.VISIBLE
            txtStatusAccepted.visibility = View.GONE
        } else if (user.Users.submission_status == "not_submit") {
            txtWaitingForApproval.visibility = View.GONE
            btnUpgradeAccount.visibility = View.VISIBLE
            txtStatusAccepted.visibility = View.GONE
        } else if (user.Users.submission_status == "accepted") {
//            btnCreateEvent.visibility = View.VISIBLE
            txtWaitingForApproval.visibility = View.GONE
            btnUpgradeAccount.visibility = View.GONE
            txtStatusAccepted.visibility = View.VISIBLE
        }
        Log.d("User","INI USER")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Resume","INI RESUME")
        val userViewModel = activity?.run {
            ViewModelProviders.of(requireActivity()).get(UserViewModel::class.java)
        }

        userViewModel?.users?.observe(this, Observer {
                user-> setUser(user)
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d("Start","INI START")
    }
}
