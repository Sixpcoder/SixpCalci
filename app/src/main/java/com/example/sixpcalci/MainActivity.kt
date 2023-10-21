package com.example.sixpcalci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.sixpcalci.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    var lastNumeric =false
    var stateerror = false
    var lastdot = false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }

    fun onclearclick(view: View) {

        binding.dataTv.text=""
        lastNumeric=false

    }


    fun onbackclick(view: View) {
        binding.dataTv.text =binding.dataTv.text.toString().dropLast(1)

        try {
            val lastchar = binding.dataTv.text.toString().last()

            if (lastchar.isDigit()) {
            onequal()
            }

        }catch (e:Exception){
            binding.tc.text =""
            binding.tc.visibility = View.GONE
            Log.e("Last char error",e.toString())


        }

    }



    fun onoperatorclick(view: View) {
        if (!stateerror&&lastNumeric){
            binding.dataTv.append((view as Button).text)
            lastdot = false
            lastNumeric = false
            onequal()
        }

    }


    fun onequalclick(view: View) {
        onequal()
        binding.dataTv.text = binding.tc.text.toString().drop(1)
    }


    fun onDigitclick(view: View) {

        if (stateerror){
            binding.dataTv.text = (view as Button).text
            stateerror = false
        }else{
            binding.dataTv.append((view as Button).text)

        }

        lastNumeric =true
        onequal()
    }




    fun onallclearclick(view: View) {

        binding.dataTv.text=""
        binding.tc.text = ""
        stateerror = false
        lastdot = false
        lastNumeric = false
        binding.tc.visibility = View.GONE
    }


    fun onequal(){

        if(lastNumeric && !stateerror){
             val txt = binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()

                binding.tc.visibility = View.VISIBLE

                binding.tc.text = "="+ result.toString()
            }catch (ex:ArithmeticException){
                Log.e("Exception error", ex.toString())
                binding.tc.text ="Error"
                stateerror  =true
                lastNumeric = false

            }


        }
    }
}