package com.example.todolist.answerai;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * 사용 방법
 * getnerateQuest()의 메서드의 매개변수로 사용자가 할일에 대한 일을 입력합니다.
 * generateQuestion()의 메서드의 반환 값은 AI에 대한 질문 입니다. 이 질문을 반환하여 사용자에게 다시 보여주고
 * 사용자의 답변을 checkAnswer()의 메서드에 할일에 대한 일의 문자와 사용자의 답변을 매개변수로 입력합니다.
 * checkAnswer()의 메서드는 적절함/부적절함 두가지로 분류 되면서 이를 확인할 수 있습니다.
 *
 * (+) -> 앞으로 개발해야 할 부분
 * 적절함을 받았을 때는 포인트를 +1 상승하고 부적절함을 받은 경우 2번의 기회가 있어서 2번을 모두 입력할 때에도 부적절함 일 경우 -1 포인트를 진행합니다.
 */
@Service
public class TogetherAIAnswer {

    private static final String API_URL = "https://api.together.xyz/v1/chat/completions";
    private static final String API_KEY = "API_KEY"; // 🔥 OpenAI API 키 직접 입력

    public static String generateQuestion(String task) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String prompt = "사용자가 완료한 할 일은 '" + task + "'입니다. "
                + "이 할 일과 관련된 간단한 질문을 한국어로 만들어주세요.";

        JSONObject json = new JSONObject();
        json.put("model", "meta-llama/Llama-3.3-70B-Instruct-Turbo");
        json.put("messages", new org.json.JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "너는 할 일 검증을 위한 AI야."))
                .put(new JSONObject().put("role", "user").put("content", prompt))
        );
        json.put("max_tokens", 100);
        json.put("temperature", 0.7);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            JSONObject responseBody = new JSONObject(response.body().string());
            return responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        }
    }

    public static String checkAnswer(String task, String userAnswer) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String prompt = "사용자가 완료한 할 일은 '" + task + "'입니다. "
                + "이 할 일과 관련된 질문에 대한 사용자의 답변: '" + userAnswer + "'\n"
                + "이 답변이 적절한지 판단해 주세요. 적절하면 '적절함', 적절하지 않으면 '부적절함'을 출력하세요.";

        JSONObject json = new JSONObject();
        json.put("model", "meta-llama/Llama-3.3-70B-Instruct-Turbo");
        json.put("messages", new org.json.JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "너는 사용자의 답변이 올바른지 판단하는 AI야."))
                .put(new JSONObject().put("role", "user").put("content", prompt))
        );
        json.put("max_tokens", 100);
        json.put("temperature", 0.5);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            JSONObject responseBody = new JSONObject(response.body().string());
            return responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        }
    }
}
