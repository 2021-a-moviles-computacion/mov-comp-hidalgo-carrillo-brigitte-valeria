package com.ferrifrancis.andorid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class HHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_http)
        metodoGet()
    }

    fun metodoGet()
    {
        "https://jsonplaceholder.typicode.com/posts/1".httpGet()
            .responseString{ req, res, result ->
                when (result){
                    is Result.Failure ->{
                        val error = result.getException()
                        Log.i("http-klaxon","Error: ${error}")
                    }
                    is Result.Success -> {
                        val getString = result.get()
                        Log.i("http-klaxon", "${getString}")
                    }
                }

            }
    }

}