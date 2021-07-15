package com.example.appmetricas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MouseActivity extends AppCompatActivity {

    TextView textNumEsq;
    TextView textNumDir;
    TextView textNumDest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse);

        String tecla = "";
        int numEsq=0;
        int numDir=0;
        float dist=0;

        /*
            Leitura do ficheiro mouseData.txt dos dados do rato
         */

        try {
            InputStream is = getAssets().open("mouseData.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String num1 = reader.readLine();
            numEsq = Integer.parseInt(num1 == null ? "0": num1);

            String num2 = reader.readLine();
            numDir = Integer.parseInt(num2 == null ? "0": num2);

            String num3 = reader.readLine();
            dist = Float.parseFloat(num3 == null ? "0.00": num3);

            System.out.println("Esquerda: " + num1 + "\n" + "Direita: " + num2 + "\n" + "Distancia: " + num3 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
            Encontras as textViews e definir os valores
         */

        textNumEsq = (TextView) findViewById(R.id.textEsq);
        textNumDir = (TextView) findViewById(R.id.textDir);
        textNumDest = (TextView) findViewById(R.id.textDist);

        textNumEsq.setText("Left clicks: " + numEsq);
        textNumDir.setText("Right clicks: " + numDir);
        textNumDest.setText("Travelled distance by the mouse: " + dist);


        /*
           Construção do gráfico de barras de Clicks por minuto
         */
        ArrayList<Integer> clickspermiutefromfile = new ArrayList<>();

        /*
            Leitura do ficheiro clicks per minute
         */

        try {
            InputStream is = getAssets().open("clicksperminute.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while ((tecla = reader.readLine()) != null) {

                clickspermiutefromfile.add(Integer.parseInt(tecla));
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BarChart barChartclickperminute = findViewById(R.id.barChartM);
        ArrayList<BarEntry> barChartMin = new ArrayList<>();

        int min=1;
        for(int i:clickspermiutefromfile){
            barChartMin.add(new BarEntry(min, i));
            min++;
        }

        BarDataSet barDataSet1 = new BarDataSet(barChartMin,"Left licks per minute");
        barDataSet1.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet1.setValueTextColor(Color.BLACK);
        barDataSet1.setValueTextSize(16f);

        BarData barData1 = new BarData(barDataSet1);

        barChartclickperminute.setFitBars(true);
        barChartclickperminute.setData(barData1);
        barChartclickperminute.getDescription().setTextSize(15f);
        barChartclickperminute.getDescription().setText("Total time of : 10 min");
        barChartclickperminute.animateY(2000);

    }


}