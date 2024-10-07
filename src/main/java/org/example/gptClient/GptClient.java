package org.example.gptClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.config.EnvLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GptClient {
    private static final String OPEN_AI_URL = EnvLoader.getApiUrl();
    private static final String OPEN_AI_KEY = EnvLoader.getApiKey();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String getResponseJson(String prompt) throws IOException {

        URL url;
        try {
            url = new URL(OPEN_AI_URL);
            log.info("URL created correctly");
        } catch (MalformedURLException e) {
            log.info("Invalid URL");
            throw new IllegalArgumentException("Invalid URL: " + OPEN_AI_URL, e);
        }


        HttpPost httpPost = new HttpPost(String.valueOf(url));
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("Authorization", "Bearer " + OPEN_AI_KEY);


        String json = String.format("{\"model\": \"gpt-3.5-turbo\"," +
                        " \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]," +
                        " \"max_tokens\": 1000}",
                prompt.replaceAll("\"", "\\\""));

        httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));


        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            log.info("GPT JSON generated");
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }


    public String getResponse(String prompt) throws IOException {

        String responseBody = getResponseJson(prompt);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);

        String content = rootNode.path("choices").get(0).path("message").path("content").asText();

        log.info("Formatted GPT response");
        log.info("Email content: \n" + content );

        return content;
    }

}
