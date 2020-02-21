package com.example.evento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.evento.fragments.EventFragment
import com.example.evento.fragments.HomeFragment
import com.example.evento.fragments.ProfileFragment
import com.example.evento.fragments.TransactionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment:HomeFragment
    lateinit var profileFragment: ProfileFragment
    lateinit var transactionFragment: TransactionFragment
    lateinit var eventFragment: EventFragment
    lateinit var userViewModel:UserViewModel
    lateinit var eventViewModel:EventViewModel
//    lateinit var fileOutputStream: FileOutputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.createUserService(readFile("id_user.txt"))

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        eventViewModel.createEventService()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.btnNav)

        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.home->{
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.transaction->{
                    transactionFragment = TransactionFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,transactionFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.profile->{
                    profileFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.event->{
                    eventFragment = EventFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,eventFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }
    }

    private fun readFile(fileName: String): String{
        //Read file in strogae internal
        var fileInputStream: FileInputStream
        fileInputStream = openFileInput(fileName)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine();text }() != null) {
            stringBuilder.append(text)
        }

        return stringBuilder.toString()
    }
}
