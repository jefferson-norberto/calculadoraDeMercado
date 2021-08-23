package com.norberto.calculadorademercado.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.norberto.calculadorademercado.R
import com.norberto.calculadorademercado.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //para excluir a action bar acima no app
        supportActionBar!!.hide()

        button_0.setOnClickListener { AcrescentarExpressao("0", false) }
        button_1.setOnClickListener { AcrescentarExpressao("1", false) }
        button_2.setOnClickListener { AcrescentarExpressao("2", false) }
        button_3.setOnClickListener { AcrescentarExpressao("3", false) }
        button_4.setOnClickListener { AcrescentarExpressao("4", false) }
        button_5.setOnClickListener { AcrescentarExpressao("5", false) }
        button_6.setOnClickListener { AcrescentarExpressao("6", false) }
        button_7.setOnClickListener { AcrescentarExpressao("7", false) }
        button_8.setOnClickListener { AcrescentarExpressao("8", false) }
        button_9.setOnClickListener { AcrescentarExpressao("9", false) }
        button_star.setOnClickListener { AcrescentarExpressao(".", false) }

        //operadores
        button_sum.setOnClickListener { acaoSomar() }
        button_sub.setOnClickListener { acaoSubtrair() }
        button_multiplication.setOnClickListener { acaoMultiplicar() }
        button_division.setOnClickListener {  }


        //limpar AC
        button_AC.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("VOCÊ QUER APAGAR TUDO?")
                .setPositiveButton("SIM",
                    DialogInterface.OnClickListener { dialog, id ->
                        text_valor.text = ""
                        text_total.text = ""
                    })
                .setNegativeButton("NÃO",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    }).show()

        }

        //Backspace
        button_delete.setOnClickListener {
            val srt = text_valor.text.toString()

            if(srt.isNotBlank()){
                text_valor.text = srt.substring(0, srt.length-1)
            }

        }

        //resultado
        button_equal.setOnClickListener {
            try {
                //aqui foi utilizado a biblioteca que realiza os calculos
                val valor = ExpressionBuilder(text_valor.text.toString()).build()

                if(text_total.text.isEmpty()){
                    val total  = valor.evaluate()
                    text_total.text = total.toString()
                }else{
                    val total = valor.evaluate() + text_total.text.toString().toDouble()
                    text_total.text = total.toString()
                }
            }catch (e: Exception){

            }
        }

    }

    fun AcrescentarExpressao(srt :String, limparDados :Boolean){
        text_valor.append(srt)

    }

    fun acaoSomar(){
        if(text_total.text.isNotEmpty() && text_valor.text.isNotEmpty()){
            val v1 = text_valor.text.toString().toDouble()
            val v2 = text_total.text.toString().toDouble()
            text_total.text = somar(v1, v2).toString()
        }else if(text_total.text.isEmpty()){
            val v1 = text_valor.text.toString().toDouble()
            text_total.text = somar(v1, 0.00).toString()
        }else if(text_valor.text.isEmpty()){
            val v2 = text_total.text.toString().toDouble()
            text_total.text = somar(0.00, v2).toString()
        }else{
            text_total.text = ""
        }
        text_valor.text = ""
    }

    fun acaoSubtrair(){
        if(text_total.text.isNotEmpty() && text_valor.text.isNotEmpty()){
            val v1 = text_valor.text.toString().toDouble()
            val v2 = text_total.text.toString().toDouble()
            text_total.text = subtrair(v2, v1).toString()
        }else if(text_total.text.isEmpty()){
            val v1 = text_valor.text.toString().toDouble()
            text_total.text = subtrair(v1, 0.00).toString()
        }else if(text_valor.text.isEmpty()){
            val v2 = text_total.text.toString().toDouble()
            text_total.text = subtrair(0.00, v2).toString()
        }else{
            text_total.text = ""
        }
        text_valor.text = ""
    }

    fun acaoMultiplicar(){
        AcrescentarExpressao("*", true)

    }

    fun somar(v1: Double, v2: Double): Double{
        return v1+v2
    }

    fun subtrair(v1: Double, v2: Double): Double{
        return v1-v2
    }

    fun multiplicar(v1: Double, v2: Double): Double{
        return v1*v2
    }

    fun dividir(numerador: Double, denominador: Double): Double{
        if(denominador != 0.00){
            return numerador/denominador
        }else{
            return 0.00
        }
    }
}