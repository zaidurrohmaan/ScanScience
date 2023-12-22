package com.example.scanscience.ui.resultfact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanscience.data.Result
import com.example.scanscience.databinding.ActivityResultFactBinding
import com.example.scanscience.utils.ViewModelFactory

class ResultFactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultFactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultFactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: ResultFactViewModel by viewModels {
            factory
        }

        val objectName = intent.getStringExtra(EXTRA_OBJECT_NAME)

        viewModel.token.observe(this) {
            if(it != null && objectName != null){
                viewModel.getFact(it, objectName)
            }
        }

        viewModel.factResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    binding.tvObjectName.text = result.data?.data?.nama
                    binding.tvObjectDefinition.text = result.data?.data?.definisi
                    val rvDeskripsi = binding.rvDeskripsiUmum
                    rvDeskripsi.layoutManager = LinearLayoutManager(this)
                    val adapterDeskripsi = result.data?.data?.deskripsi_umum?.let { ResultDefinitionAdapter(it) }
                    rvDeskripsi.adapter = adapterDeskripsi
                    val rvFakta = binding.rvFakta
                    rvFakta.layoutManager = LinearLayoutManager(this)
                    val adapterFakta = result.data?.data?.fakta?.let { ResultFactAdapter(it) }
                    rvFakta.adapter = adapterFakta
                }

                is Result.Error -> {
                    showLoading(false)
                    //showToast(getString(R.string.email_tidak_tersedia_silakan_gunakan_email_lain))
                }

                else -> {
                    showLoading(false)
                    //showToast(getString(R.string.terjadi_kesalahan))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE

        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_OBJECT_NAME = "EXTRA_OBJECT_NAME"
    }
}