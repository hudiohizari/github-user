package id.hudiohizari.githubuser.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import id.hudiohizari.githubuser.R
import id.hudiohizari.githubuser.databinding.ActivityDashboardBinding

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        val navController = navHostFragment.navController
        setSupportActionBar(binding.tMain)
        setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fcvMain).navigateUp(
            AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.searchUserFragment
                ),
                fallbackOnNavigateUpListener = ::onSupportNavigateUp
            )
        )
    }
}