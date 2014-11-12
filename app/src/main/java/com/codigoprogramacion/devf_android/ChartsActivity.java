package com.codigoprogramacion.devf_android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class ChartsActivity extends Activity {

    private GraphicalView chart;
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer renderer;

    private XYSeriesRenderer rendererLineal,rendererSenoidal;
    private XYSeries serieLineal,serieSenoidal;
    private LinearLayout contenedor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        //inicializamos nuestro contenedor(cualquier ViewGroup)
        contenedor = (LinearLayout)findViewById(R.id.chart_container);

        //Inicializamos renderer y dataset principales
        dataset = new XYMultipleSeriesDataset();
        renderer = new XYMultipleSeriesRenderer();
    }

    @Override
    protected void onStart(){
        super.onStart(); //llamada a metodo original

        //inicializamos nuestras series de datos
        serieLineal= new XYSeries("Función Lineal");
        serieSenoidal = new XYSeries("Función Senoidal");

        //añadimos nuestras series al dataSet
        dataset.addSeries(serieLineal);
        dataset.addSeries(serieSenoidal);

        //llenamos las series con datos
        for(double i = -10d;i < 10d;i+=0.25d
                ,serieLineal.add(i, i),serieSenoidal.add(i,Math.sin(i)));

        //creamos renderers de series
        rendererLineal = new XYSeriesRenderer();
        rendererSenoidal = new XYSeriesRenderer();

        //cambiamos color a renderer Lineal
        rendererLineal.setColor(Color.GREEN);

        //añadimos renderers a rendererMultiple
        renderer.addSeriesRenderer(rendererLineal);
        renderer.addSeriesRenderer(rendererSenoidal);
        //se añaden 2 dado que tenemos 2 series en este chart

        //Remover background que por defecto es de color  negro
        renderer.setApplyBackgroundColor(true);
        //renderer.setBackgroundColor(Color.argb(0,255,255,255)); //buggy
        renderer.setMarginsColor(Color.argb(0,255,255,255));

        //Fabricamos añadimos la vista Chart al contenedor
        chart = ChartFactory.getCubeLineChartView(this, dataset, renderer,0.3f);
        //prueben otros metodos para fabricar diferentes tipos de charts
        //chart = ChartFactory.getLineChartView(this, dataset, renderer);
        //chart = ChartFactory.getBarChartView(this, dataset, renderer, BarChart.Type.DEFAULT);

        contenedor.addView(chart);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.charts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
