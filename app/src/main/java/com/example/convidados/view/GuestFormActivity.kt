package com.example.convidados.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.viewmodel.GuestFormViewModel
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel

/* 3.0 Criando a activity de formulario
Ja criei o layout desta activiy GuestForm, agoro crio faço a configuração do biding, além de instanciar
o evento de click. Também crio a view model para está activity e faça a conexão entre elas.

    18.0 Atualização de convidado
- Em onCreate eu uso loadData para carregar os dados, esse metodo criado verifica se houve algum bundle
enviado junto a criação da activity, para carregar os dados do usuario, por esse motivo é necessario
criar a função get dentro da viewModel e no repository
 */
class GuestFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

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

        observe()
        loadData()

    }


    /* 7.1 Chamando a inserção
    - Coletamos os dados a activity e enviamos para viewModel para se conectar com o repository
     */
    override fun onClick(view: View) {
        if (view.id == R.id.button_save) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked
            val model = GuestModel().apply {
                this.id = guestId
                this.name = name
                this.presence = presence
            }



            viewModel.save(model)
            //TODO temporario
            finish()
        }
    }

    private fun observe() {
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}