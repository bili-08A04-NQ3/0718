package top.niqiu.core.Text;

import com.alibaba.fastjson2.JSONObject;
import top.niqiu.Main;
import top.niqiu.core.Scenario.Scenario;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TranslatedText extends Text {
    public static JSONObject json = new JSONObject();
    public String key;

    public TranslatedText(String key) {
        this.key = key;
    }

    public String getDisplayText() {
        String s = json.getString(key);
        return s == null ? key : s;
    }

    public static void init() {
        try {
            InputStream inputStream = Main.class.getResourceAsStream("/assets/lang/zh_cn.json");
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            while (bufferedReader.ready()) {
                builder.append(bufferedReader.readLine());
            }
            json = JSONObject.parseObject(builder.toString());
            System.out.println(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
