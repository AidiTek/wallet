package com.example.wallet.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.data.entity.AccountData
import com.example.wallet.data.sharedPreferences.AuthUtils
import com.example.wallet.databinding.ActivityMainBinding
import com.example.wallet.presentation.adapters.rvAdapter.AccountAdapter
import com.example.wallet.presentation.app.App
import com.example.wallet.presentation.viewModel.OperationViewModel
import com.example.wallet.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private val operationViewModel: OperationViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var authUtils: AuthUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authUtils = AuthUtils(this)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (authUtils.isLogedIn()) {
            navController.navigate(R.id.mainFragment)
        }else {
            navController.navigate(R.id.registrationOrLoginEmailGoogle)
        }

        navController.addOnDestinationChangedListener{_,Destination,_->
            when(Destination.id){
                R.id.registrationOrLoginEmailGoogle ->{
                    binding.toolbar.visibility = View.GONE
                }
                else -> {
                    // Показать TabLayout при всех других фрагментах
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
        }

        // Настройка NavigationView
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.main_page -> navController.navigate(R.id.mainFragment)
                R.id.invoice_page -> navController.navigate(R.id.invoiceFragment)
                R.id.graphics_page -> navController.navigate(R.id.graphicsFragment)
                R.id.reminder_page -> navController.navigate(R.id.reminderFragment)
                R.id.contacting_the_developer_page -> navController.navigate(R.id.conectingWithDeveloperFragment)
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Логика для левой иконки
        binding.ivLeftIcon.setOnClickListener { view ->
            // Анимация для ivLeftIcon
            view.animate().scaleX(0.9f) // Уменьшение по ширине
                .scaleY(0.9f) // Уменьшение по высоте
                .setDuration(100) // Длительность уменьшения
                .withEndAction {
                    view.animate().scaleX(1f) // Возвращение к оригинальному размеру
                        .scaleY(1f).setDuration(100) // Длительность увеличения
                        .withEndAction {
                            // Выполнение логики после анимации
                            handleLeftIcon(navController)
                        }.start()
                }.start()
        }

// Логика для правой иконки
        binding.ivRightIcon.setOnClickListener { view ->
            // Анимация для ivRightIcon
            view.animate().scaleX(0.9f) // Уменьшение по ширине
                .scaleY(0.9f) // Уменьшение по высоте
                .setDuration(100) // Длительность уменьшения
                .withEndAction {
                    view.animate().scaleX(1f) // Возвращение к оригинальному размеру
                        .scaleY(1f).setDuration(100) // Длительность увеличения
                        .withEndAction {
                            // Выполнение логики после анимации
                            navController.navigate(R.id.action_mainFragment_to_allOperatonFragment)
                        }.start()
                }.start()
        }

        binding.ivChangeInvoice.setOnClickListener {
            showAccountDialog()
        }

        // Слушатель изменений навигации
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> setupLeftIconForDrawer()
                else -> setupLeftIconForBack(navController)
            }
        }

        // Обработка кнопки "Назад"
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDestination = navController.currentDestination?.id
                if (currentDestination == R.id.mainFragment) {
                    finish()
                } else {
                    navController.navigate(R.id.mainFragment) // Возврат на mainFragment
                }
            }
        })

        operationViewModel.totalBalance.observe(this) { balance ->
            val balanceTextView = binding.toolbar.findViewById<TextView>(R.id.tv_moneyCount)
            balanceTextView.text = balance.toString()
        }

    }

    private fun handleLeftIcon(navController: NavController) {
        val currentDestination = navController.currentDestination?.id
        if (currentDestination == R.id.allOperatonFragment || currentDestination == R.id.addOperationFragment) {
            navController.navigate(R.id.mainFragment)
        } else {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView)
            } else {
                binding.drawerLayout.openDrawer(binding.navigationView)
            }
        }
    }

    private fun setupLeftIconForDrawer() {
        binding.ivLeftIcon.apply {
            setImageResource(R.drawable.side_navigation_24dp)
            setOnClickListener {
                if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                    // Замедленное закрытие Drawer
                    binding.drawerLayout.closeDrawer(binding.navigationView)
                } else {
                    // Замедленное открытие Drawer
                    binding.navigationView.animate()
                        .translationX(0f) // Перемещение панели в видимую область
                        .setDuration(600) // Длительность анимации (600 мс)
                        .withStartAction {
                            binding.drawerLayout.openDrawer(binding.navigationView)
                        }.start()
                }
            }
        }
    }

    private fun setupLeftIconForBack(navController: NavController) {
        binding.ivLeftIcon.apply {
            setImageResource(R.drawable.arrow_back_24dp)
            setOnClickListener {
                handleLeftIcon(navController)
            }
        }
    }

    private fun showAccountDialog() {

        lifecycleScope.launch {
            val dataBase = (application as App).dataBase
            val accounts = dataBase.operationDao().getAllAccounts().first()

            if (accounts.isNotEmpty()) {

                val dialogView = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.dialog_account_list, null)

                val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rv_account)
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                val dialog = AlertDialog.Builder(this@MainActivity, R.style.CustomAlertDialogThem)
                    .setTitle("Выберите счет")
                    .setView(dialogView)
                    .setNegativeButton("Отмена", null)
                    .create()

                val adapter = AccountAdapter(accounts) { selectedAccount ->
                    binding.tvInvoice.text = selectedAccount.category
                    binding.tvMoneyCount.text = "${selectedAccount.amount}"

                    val accountData = AccountData(
                        id = selectedAccount.id,
                        title = selectedAccount.category,
                        balance = selectedAccount.amount
                    )

                    sharedViewModel.setSelectedAccount(accountData.id)

                    dialog.dismiss()
                }
                recyclerView.adapter = adapter
                dialog.show()
            } else {
                Toast.makeText(this@MainActivity, "Нет доступных счетов", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
