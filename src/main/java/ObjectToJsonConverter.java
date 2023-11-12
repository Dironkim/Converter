import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okio.BufferedSink;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import okio.BufferedSink;
import okio.Okio;

public class ObjectToJsonConverter {

    public static void convertToJsonFile(InternetShopJSON internetShop, String filePath) {
        try {
            // Используем библиотеку Moshi для преобразования объекта в формат JSON
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<InternetShopJSON> jsonAdapter = moshi.adapter(InternetShopJSON.class);
            // Записываем JSON в строку
            File file = new File(filePath);
            String fileContents= jsonAdapter.toJson(internetShop);
            try (BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File(filePath)))) {
                // Используем JsonWriter с отступами
                com.squareup.moshi.JsonWriter jsonWriter = com.squareup.moshi.JsonWriter.of(bufferedSink);
                jsonWriter.setIndent("    ");
                jsonAdapter.toJson(jsonWriter, internetShop);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
