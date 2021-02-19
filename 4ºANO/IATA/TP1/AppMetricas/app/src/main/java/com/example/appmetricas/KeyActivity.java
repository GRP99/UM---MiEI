package com.example.appmetricas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeyActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);


        /*
        * Ler do ficheiro e guardar nas estruturas de dados
        */

        Map<String, Integer> teclasTotal = new HashMap<String,Integer>();

        int aux;
        String tecla = "";
        int val_init = 1;


        try {

            InputStream is = getAssets().open("fileCP.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while ((tecla = reader.readLine()) != null) {

                System.out.println(tecla);

                if(teclasTotal.containsKey(tecla)){        //se contém a chave
                    aux = teclasTotal.get(tecla);          //vai buscar o valor correspondente
                    aux ++;                                //aumenta o valor
                    teclasTotal.replace(tecla,aux);        // substitui o valor
                }else{
                    teclasTotal.put(tecla,val_init);
                }
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Ler do ficheiro e guardar na estrutura caracteres pressionados por minuto
         */

        ArrayList<Integer> keyspermiutefromfile = new ArrayList<>();

        int aux1;
        String tecla1 = "";
        int val_init1 = 1;

        try {

            InputStream is = getAssets().open("keysperminute.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while ((tecla = reader.readLine()) != null) {

                System.out.println("AQUI AQUI AQUI AQUI" + tecla);
                    keyspermiutefromfile.add(Integer.parseInt(tecla));
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        *  Desenho do gráfico de barras
        */

        /*
        //BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> charsToBarChart = new ArrayList<>();

        for (String key : teclasTotal.keySet()) {

            if(key.equals("KEY.SPACE")){
                charsToBarChart.add(new BarEntry(' ', teclasTotal.get(key)));
            }
            if(key.equals("KEY.ENTER")){
                charsToBarChart.add(new BarEntry('§', teclasTotal.get(key)));
            }
            charsToBarChart.add(new BarEntry(key.charAt(0),teclasTotal.get(key)));
        }

        ArrayList<String> xAxisLabel = new ArrayList<>();
        for(String s : teclasTotal.keySet()){
            System.out.println("FOGO " + s);
            xAxisLabel.add(s);
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        BarDataSet barDataSet = new BarDataSet(charsToBarChart,"Pressed characters");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Pressed characters");
        barChart.animateY(2000);
        barChart.invalidate();
*/
        // --------------------------------------------------------------------------------------

        /*
         *  Desenho do Pie Chart
         */

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> letras = new ArrayList<>();

        // ordenar o map num LinkedHashMap do maior para o mais pequeno

        System.out.println("Unsorted Map : " + teclasTotal);

        //LinkedHashMap preserve the ordering of elements in which they are inserted
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        //Use Comparator.reverseOrder() for reverse ordering
        teclasTotal.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        System.out.println("Reverse Sorted Map   : " + reverseSortedMap);

        //popular o array para o grafico
        int max=10;

        //retirar as primeiras X teclas mais pressionadas

        List<String> keys = teclasTotal.entrySet().stream()
                .map(Map.Entry::getKey)
                .sorted()
                .limit(5)
                .collect(Collectors.toList());

        System.out.println("PRIMEIRAS CHAVES   : " + keys);

        for (String s: keys){
            letras.add(new PieEntry(teclasTotal.get(s),s));
        }

        /*
        Construção do pie chart das X teclas mais pressionadas
         */

        PieDataSet pieDataSet = new PieDataSet(letras, "Most pressed characters");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Characters");
        pieChart.animate();


        /*
        Construção do gráfico de barras de caracteres pressionados por minuto
         */

        BarChart barChartperminute = findViewById(R.id.barChartPorMin);
        ArrayList<BarEntry> barChartMin = new ArrayList<>();

        int min=1;
        for(int i:keyspermiutefromfile){
            barChartMin.add(new BarEntry(min, i));
            min++;
        }

        BarDataSet barDataSet1 = new BarDataSet(barChartMin,"Characters pressed per minute");
        barDataSet1.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet1.setValueTextColor(Color.BLACK);
        barDataSet1.setValueTextSize(16f);

        BarData barData1 = new BarData(barDataSet1);

        barChartperminute.setFitBars(true);
        barChartperminute.setData(barData1);
        barChartperminute.getDescription().setTextSize(15f);
        barChartperminute.getDescription().setText("Total time of : 10 min");
        barChartperminute.animateY(2000);

    }

}