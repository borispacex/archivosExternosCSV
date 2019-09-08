package com.example.archivosexternos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {
    private static double sumaA = 0;
    private static double sumaB = 0;
    private static double sumaC = 0;
    private static double sumaD = 0;
    private static double sumaE = 0;
    private static double sumaF = 0;
    private static double sumaG = 0;
    private static double sumaI = 0;

    private TextView ptex;
    private TextView tA, tB, tC, tD, tE, tF, tG, tI;
    private TextView ttotal;

    private static String codProd [] = new String[3000];
    private static String tipoPrecio[] = new String[3000];
    private static String fechaVig[] = new String[3000];
    private static Double precioUni[] = new Double[3000];

    private static int vuelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ptex = (TextView)findViewById(R.id.texto);

        tA = (TextView)findViewById(R.id.tipoA);
        tB = (TextView)findViewById(R.id.tipoB);
        tC = (TextView)findViewById(R.id.tipoC);
        tD = (TextView)findViewById(R.id.tipoD);
        tE = (TextView)findViewById(R.id.tipoE);
        tF = (TextView)findViewById(R.id.tipoF);
        tG = (TextView)findViewById(R.id.tipoG);
        tI = (TextView)findViewById(R.id.tipoI);

        ttotal = (TextView)findViewById(R.id.total);

        String estado = Environment.getExternalStorageState();

        if (!estado.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "No hay SD Card!", Toast.LENGTH_SHORT).show();
            finish();
        }
        try {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            File dir = Environment.getExternalStorageDirectory();
            File pt = new File(dir.getAbsolutePath() + File.separator + "precios.csv");
            BufferedReader lee = new BufferedReader(new FileReader(pt));
            String res = "", linea;
            while ((linea=lee.readLine())!=null) {
                res = res + linea+ ";\n";
                extraer(linea+";");
            }

            ptex.setText("numero de datos: " + (vuelta-1) + "\n\n" + res);

            tA.setText(sumaA + "");
            tB.setText(sumaB + "");
            tC.setText(sumaC + "");
            tD.setText(sumaD + "");
            tE.setText(sumaE + "");
            tF.setText(sumaF + "");
            tG.setText(sumaG + "");
            tI.setText(sumaI + "");

            double suma = sumaA + sumaB + sumaC + sumaD + sumaE + sumaF + sumaG + sumaI;

            ttotal.setText(suma + "");

        } catch (Exception e) {  }

    }
    // funcion para extraer cadena
    // AAAA;B;CC/CC/CC;0.75;
    public static void extraer(String cadena) {
        String palabra = "", voca;
        int pos = 0;
        for (int i=1; i<=cadena.length(); i++) {
            voca = cadena.substring(i-1, i);
            if (voca.compareTo(";")==0) {
                switch(pos) {
                    case 0:
                        codProd[vuelta] = palabra;
                        break;
                    case 1:
                        tipoPrecio[vuelta] = palabra;
                        break;
                    case 2:
                        fechaVig[vuelta] = palabra;
                        break;
                    case 3:
                        precioUni[vuelta] = Double.parseDouble(palabra);
                        break;
                    default:
                        System.out.println("error dando valor a vector");
                }
                palabra = "";
                pos++;
            }else{
                palabra = palabra + voca;
            }
        }
        // sumamos
        switch(tipoPrecio[vuelta]) {
            case "A":
                sumaA = sumaA + precioUni[vuelta];
                break;
            case "B":
                sumaB = sumaB + precioUni[vuelta];
                break;
            case "C":
                sumaC = sumaC + precioUni[vuelta];
                break;
            case "D":
                sumaD = sumaD + precioUni[vuelta];
                break;
            case "E":
                sumaE = sumaE + precioUni[vuelta];
                break;
            case "F":
                sumaF = sumaF + precioUni[vuelta];
                break;
            case "G":
                sumaG = sumaG + precioUni[vuelta];
            case "I":
                sumaI = sumaI + precioUni[vuelta];
                break;
            default:
                System.out.println("error suma por vector");
        }
        vuelta++;
    }
}
