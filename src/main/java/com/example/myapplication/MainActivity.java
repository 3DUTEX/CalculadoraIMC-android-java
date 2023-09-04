package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText inputPeso, inputAltura;
    RadioButton radioM, radioF;
    Button calcular;

    TextView totalTestes, acima, abaixo, ideal;

    double qtdAbaixo = 0;
    double qtdAcima = 0;
    double qtdIdeal = 0;
    ArrayList arrayTestes = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        associa();


        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(validacao()){

                    double pesoValue = Double.parseDouble(inputPeso.getText().toString());
                    double alturaValue = Double.parseDouble(inputAltura.getText().toString());

                    //formatando cm para metros
                    if(alturaValue > 3){
                        alturaValue = alturaValue/100;
                        inputAltura.setText(alturaValue + "");
                        Toast.makeText(MainActivity.this, "Altura em centímetros convertida para metros", Toast.LENGTH_SHORT).show();
                    }
                    calculaImc(pesoValue, alturaValue);
                }
            }

            private boolean validacao() {
                String pesoString = inputPeso.getText().toString();
                String altString = inputAltura.getText().toString();
                if(pesoString.trim().length() <= 0){
                    inputPeso.setError("Por favor informe um peso!");
                    //Toast.makeText(MainActivity.this, "Por favor informe seu peso!", Toast.LENGTH_SHORT).show();
                    return false;
                } else if(altString.trim().length() <= 0){
                    inputAltura.setError("Por favor informe uma altura!");
                    //Toast.makeText(MainActivity.this, "Por favor informe sua altura!", Toast.LENGTH_SHORT).show();
                    return false;
                } else if(!radioF.isChecked() && !radioM.isChecked()){
                    Toast.makeText(MainActivity.this, "Por favor selecione o seu sexo!", Toast.LENGTH_SHORT).show();
                    return  false;
                }
                else{
                    return true;
                }
            }
        });
    }

    private void calculaImc(double peso, double altura) {
        double pesoValue = peso;
        double alturaValue = altura;

        double imc = pesoValue / (alturaValue * alturaValue);


        if(radioM.isChecked()){
            if(imc < 19){
                //abaixo
                Toast.makeText(MainActivity.this, "Seu imc está abaixo", Toast.LENGTH_SHORT).show();
                arrayTestes.add("abaixo");
                qtdAbaixo++;
            }else if(imc > 25){
                //cima
                Toast.makeText(MainActivity.this, "Seu imc está acima" , Toast.LENGTH_SHORT).show();
                arrayTestes.add("acima");
                qtdAcima++;
            }else{
                //ideal
                Toast.makeText(MainActivity.this, "Seu imc está ideal", Toast.LENGTH_SHORT).show();
                arrayTestes.add("ideal");
                qtdIdeal++;
            }
        }else{
            if(imc < 18){
                //abaixo
                Toast.makeText(MainActivity.this, "Seu imc está abaixo", Toast.LENGTH_SHORT).show();
                arrayTestes.add("abaixo");
                qtdAbaixo++;

            }else if(imc > 26){
                //acima
                Toast.makeText(MainActivity.this, "Seu imc está acima", Toast.LENGTH_SHORT).show();
                arrayTestes.add("acima");
                qtdAcima++;
            }else{
                //ideal
                Toast.makeText(MainActivity.this, "Seu imc está ideal ", Toast.LENGTH_SHORT).show();
                arrayTestes.add("ideal");
                qtdIdeal++;
            }
        }

        setStats();
    }

    private void setStats() {
        //pegando tamanho do array testes
        int tamanhoArray = arrayTestes.toArray().length;

        //set texto total de testes baseado no tamanho do array
        totalTestes.setText("Total testes: " + tamanhoArray);

        //fazendo o calculo da porcentagem
        double totalAbaixo = (qtdAbaixo/ tamanhoArray) * 100;
        double totalAcima = (qtdAcima/ tamanhoArray) * 100;
        double totalIdeal = (qtdIdeal/ tamanhoArray) * 100;

        //arredondando e retornando na mesma variável
        totalAbaixo = Math.round(totalAbaixo * 100.0)/100;
        totalAcima = Math.round(totalAcima * 100.0)/100;
        totalIdeal = Math.round(totalIdeal * 100.0)/100;

        //setando texto porcentagem
        abaixo.setText("% abaixo: " + totalAbaixo);
        acima.setText("% acima: " + totalAcima);
        ideal.setText("$ ideal: " + totalIdeal);
    }

    private void associa() {
        inputPeso = findViewById(R.id.inputPeso);
        inputAltura = findViewById(R.id.inputAltura);
        radioM = findViewById(R.id.radioM);
        radioF = findViewById(R.id.radioF);
        calcular = findViewById(R.id.calcular);
        totalTestes = findViewById(R.id.totalTestes);
        acima = findViewById(R.id.acima);
        abaixo = findViewById(R.id.abaixo);
        ideal = findViewById(R.id.ideal);
    }
}