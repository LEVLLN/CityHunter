package lk.simplecode.kz.cityhunter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView catName;
    TextView catColor;
    TextView catAge;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView  = (TextView)findViewById(R.id.textView);
        textView.setText("Вытянуть из объекта Json");
        catName = (TextView)findViewById(R.id.cat_name);
        catAge  = (TextView)findViewById(R.id.cat_age);
        catColor  = (TextView)findViewById(R.id.cat_color);
        button = (Button)findViewById(R.id.getJson);

    }


    public void toGson(View view) {
        Cat cat = new Cat();
        cat.lol = "Котэшка";
        cat.color= Color.BLACK;
        cat.age = 12;

        GsonBuilder builder = new GsonBuilder();
        Gson gson =builder.create();
        Log.i("GSON", gson.toJson(cat));
        textView.setText(gson.toJson(cat));
    }


    public void fromJson(View view) {
        String jsonText = "{\"age\":100500,\"color\":-16777216,\"name\":\"JSON_CAT_MONSTER\"}";
        GsonBuilder builder = new GsonBuilder();
        Gson gson =builder.create();
        Cat cat = gson.fromJson(jsonText,Cat.class);
        Log.i("GSON", "Имя: " + cat.lol + "\nВозраст: " + cat.age);
        catName.setText("ИМЯ: " + cat.lol);
        catAge.setText("ВОЗРАСТ: "+String.valueOf(cat.age));
        catColor.setText("ЦВЕТ: "+String.valueOf(cat.color));
    }
}
