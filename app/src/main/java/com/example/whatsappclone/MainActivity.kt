package com.example.whatsappclone




import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.whatsappclone.activity.AboutUs
import com.example.whatsappclone.activity.NumberActivity
import com.example.whatsappclone.activity.OTPActivity
import com.example.whatsappclone.activity.ProfileUpdateActivity
import com.example.whatsappclone.adapter.ViewPagerAdapter
import com.example.whatsappclone.databinding.ActivityMainBinding
import com.example.whatsappclone.ui.CallFragment
import com.example.whatsappclone.ui.ChatFragment
import com.example.whatsappclone.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
  binding!!.toolbar.title="Chat App"
        setSupportActionBar(binding!!.toolbar)

        val fragmentArrayList = ArrayList<Fragment>()

        // Fragment passing
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())


        auth = FirebaseAuth.getInstance()

        if (auth.currentUser==null){
            // if user is already login , he will be directed to Main Activity
            startActivity(Intent(this,NumberActivity::class.java))
            finish()   // otherwise by back button he returns to number activity
        }

// adapter initialising
        val adapter=ViewPagerAdapter(this,supportFragmentManager,fragmentArrayList)

        // adapter to view pager

        binding!!.viewPager.adapter=adapter

        binding!!.tabs.setupWithViewPager(binding!!.viewPager)












    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile-> {

                var intent =Intent(this, ProfileUpdateActivity::class.java)

                startActivity(intent)
            }
            R.id.About-> {

                var intent =Intent(this, AboutUs::class.java)

                startActivity(intent)
            }
            R.id.logout-> {
                val user = FirebaseAuth.getInstance()
                user.signOut()

                var intent =Intent(this, NumberActivity::class.java)

                startActivity(intent)
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }

}