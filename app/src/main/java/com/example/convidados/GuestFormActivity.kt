package com.example.convidados

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.databinding.ActivityGuestFormBinding

/* 3.0 Criando a activity de formulario
Ja criei o layout desta activiy GuestForm, agoro crio faço a configuração do biding, além de instanciar
o evento de click. Também crio a view model para está activity e faça a conexão entre elas.
 */
class GuestFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)
        binding.buttonSave.setOnClickListener(this)

        /*Configurações padrão
        - Radio present inicia como marcado
         */
        binding.radioConfirmation.check(R.id.radio_present)

    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_save) {

        }
    }
}