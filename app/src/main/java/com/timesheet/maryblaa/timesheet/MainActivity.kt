package com.timesheet.maryblaa.timesheet

import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    enum class TimerState {
        Stopped, Pause, Running
    }

    private var timerState = TimerState.Stopped
    private var pauseOffset = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val spinner: Spinner = findViewById(R.id.projectSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.work_projects,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    /** Called when the user taps to pause the timer  */
    fun timerPause(view: View) {
        // Do something in response to button
        if(timerState != TimerState.Running) return

        chronometer.stop()
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.base

        timerState = TimerState.Pause
        updateTimerButtons()
    }

    /** Called when the user taps to start the timer  */
    fun timerStart(view: View) {
        // Do something in response to button
        timerState = TimerState.Running
        updateTimerButtons()

        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }


    /** Called when the user taps to continue the timer  */
    fun timerContinue(view: View) {
        // Do something in response to button
        timerState = TimerState.Running

        chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
        chronometer.start()
        pauseOffset = 0

        updateTimerButtons()
    }

    /** Called when the user taps to stop the timer  */
    fun timerStop(view: View) {

        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0

        timerState = TimerState.Stopped
        updateTimerButtons()
    }

    private fun updateTimerButtons() {
        when(timerState) {
            TimerState.Stopped -> {
                btnStartTimer.visibility = View.VISIBLE
                btnStopTimer.visibility = View.INVISIBLE
                btnPauseTimer.visibility = View.VISIBLE
                btnContinueTimer.visibility = View.INVISIBLE
            }
            TimerState.Running -> {
                btnStartTimer.visibility = View.INVISIBLE
                btnStopTimer.visibility = View.VISIBLE
                btnPauseTimer.visibility = View.VISIBLE
                btnContinueTimer.visibility = View.INVISIBLE
            }
            TimerState.Pause -> {
                btnContinueTimer.visibility = View.VISIBLE
                btnPauseTimer.visibility = View.INVISIBLE
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
