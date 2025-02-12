package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.convidados.R
import com.example.convidados.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        /* 1.0 Float action button
        Este botão é resposavel por abrir uma nova activity, para isso foi criado
        em UI a Activity GuestForm com o layout Fragmente all guests
        2.0 Modificando a navigation bar
        dentro da navigation bar agora existe o botão "todos os convidados", para isso substituimos
        o arquivo kotlin HomeFragment e sua view model para AllGuests, assim como o xml.
        Dentro de Menu em activity_main_drawer tems todos os itens de navegação, incluindo home que
        será alterado, podemos modficar o texto para "todos os convidados" e o id para nav_all_guests.
        podemos também alterar o titulo da activity utilizando o campo label em activity_main_drawer.
        mas para que possamos navegar para essa fragment (renomeada para fragment_all_guests)
        temos que modificar o id em mobile_navigation também, lembrando que aqui na MainActivity
        nos referenciamos as fragments em AppBarConfigure portando é necessario atualizar a referencia.
        Continua em 3.0 em GuestFormActivity "Criando a activity de formulario"
        Continua em 4.0 em GuesFormViewModel "Criando repo"
         */
        binding.appBarMain.fab.setOnClickListener { view ->
            startActivity(Intent(applicationContext, GuestFormActivity::class.java))
        }
        setupNavigation()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_all_guests, R.id.nav_absent, R.id.nav_present
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}