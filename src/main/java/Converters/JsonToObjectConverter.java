package Converters;

import Structures.InternetShopJSON;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonToObjectConverter {

    public static InternetShopJSON convert(String jsonFilePath) {
        try {
            // Используем библиотеку Moshi для создания адаптера
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<InternetShopJSON> jsonAdapter = moshi.adapter(InternetShopJSON.class);

            // Чтение JSON из файла
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

            // Преобразование JSON в объект
            return jsonAdapter.fromJson(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Обработайте исключение в зависимости от ваших потребностей
            return null;
        }
    }

}
