package com.example.scanscience.ui.result

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scanscience.R
import com.example.scanscience.data.Result
import com.example.scanscience.databinding.ActivityResultBinding
import com.example.scanscience.ml.Model20
import com.example.scanscience.ui.profile.ProfileViewModel
import com.example.scanscience.ui.resultfact.ResultFactActivity
import com.example.scanscience.utils.ViewModelFactory
import com.example.scanscience.utils.reduceFileImage
import com.example.scanscience.utils.uriToFile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@ResultActivity)
        val viewModel: ResultViewModel by viewModels {
            factory
        }

        binding.apply {
            buttonEffect(btnSave)
            buttonEffect(btnUpload)
        }

        val stringUri = intent.getStringExtra(EXTRA_URI)
        val uri = Uri.parse(stringUri)
        binding.ivPictureResult.setImageURI(uri)

        outputGenerator(uri, binding.btnObjectResult)

        viewModel.saveImageResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    //val data = result.data
                    showToast("Berhasil menyimpan")
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast("Gagal menyimpan. Silakan coba lagi")
                }
                else -> showLoading(false)
            }
        }

        binding.btnSave.setOnClickListener {
//            saveImage(uri)
            showToast("Fitur dalam pengembangan")
        }

        binding.btnUpload.setOnClickListener {
            showToast("Fitur dalam pengembangan")
        }

        binding.btnObjectResult.setOnClickListener {
            viewFact(binding.btnObjectResult.text.toString())
        }
    }

    private fun viewFact(objects: String) {
        val intent = Intent(this@ResultActivity, ResultFactActivity::class.java)
        intent.putExtra(ResultFactActivity.EXTRA_OBJECT_NAME, objects)
        startActivity(intent)
    }

    fun  outputGenerator(uri: Uri, view: Button) {

        val model = Model20.newInstance(this)

        var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        val dimension = min(bitmap.width, bitmap.height)
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension)
        bitmap = Bitmap.createScaledBitmap(bitmap, 299, 299, false)
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 299, 299, 3), DataType.FLOAT32)

        val byteBuffer = ByteBuffer.allocateDirect(4 * 299 * 299 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(299*299)
        bitmap.getPixels(intValues,0, bitmap.width,0,0, bitmap.width, bitmap.height)
        var pixel = 0

        for(i in 0..<299){
            for(j in 0..<299){
                val value = intValues[pixel++]
                byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f/255))
                byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f/255))
                byteBuffer.putFloat((value and 0xFF) * (1f/255))
            }
        }
        inputFeature0.loadBuffer(byteBuffer)

         val labels = arrayOf("Anjing Laut", "Babi Hutan", "Badak", "Beruang", "Burung Enggang", "Bison")

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        // Find label with highest confidence
        val confidences: FloatArray = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f

        for(i in confidences.indices){
            if(confidences[i] > maxConfidence){
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        // Set text
        view.text = labels[maxPos]


        // Releases model resources if no longer used.
        model.close()
    }

    private fun waitingResult() {
        binding.apply {
            tvWait.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE

            tvHasil.visibility = View.GONE
            ivPictureResult.visibility = View.GONE
            tvObjectsCount.visibility = View.GONE
            btnObjectResult.visibility = View.GONE
            btnSave.visibility = View.GONE
            btnUpload.visibility = View.GONE
            etDescription.visibility = View.GONE
        }
    }

    private fun result() {
        binding.apply {
            tvWait.visibility = View.GONE
            progressBar.visibility = View.GONE

            tvHasil.visibility = View.VISIBLE
            ivPictureResult.visibility = View.VISIBLE
            tvObjectsCount.visibility = View.VISIBLE
            btnObjectResult.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
            btnUpload.visibility = View.VISIBLE
            etDescription.visibility = View.VISIBLE
        }
    }

    private fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }

    private fun saveImage(uri: Uri) {
        val description = binding.etDescription.text.toString().trim()
        val nama = binding.btnObjectResult.text.toString()

        if (description == "") {
            showToast("Deskripsi tidak boleh kosong")
        } else {
            showLoading(true)
            Log.d("Check Uri", "Uri ${uri.toString()}")
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val requestBody1 = nama.toRequestBody("text/plain".toMediaType())
            val requestBody2 = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            val factory: ViewModelFactory = ViewModelFactory.getInstance(this@ResultActivity)
            val viewModel: ResultViewModel by viewModels {
                factory
            }

            viewModel.token.observe(this) {
                viewModel.saveImage(it, multipartBody, requestBody1, requestBody2)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ResultActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnSave.isClickable = false
            binding.btnUpload.isClickable = false
            binding.btnObjectResult.isClickable = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnSave.isClickable = true
            binding.btnUpload.isClickable = true
            binding.btnObjectResult.isClickable = true
        }
    }

    companion object {
        const val EXTRA_URI = "extra_uri"
    }
}