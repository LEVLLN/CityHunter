package lk.simplecode.kz.cityhunter;

import android.graphics.Color;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ruiner {

    public static void toGson(){
        Cat cat = new Cat();
        cat.lol = "Котэшка";
        cat.color= Color.BLACK;
        cat.age = 12;

        GsonBuilder builder = new GsonBuilder();
        Gson gson =builder.create();
        Log.i("GSON",gson.toJson(cat));

    }

    public static void main(String[] args) {
        toGson();
    }
}
