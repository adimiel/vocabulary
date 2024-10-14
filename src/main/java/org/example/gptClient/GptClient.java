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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GptClient {    
    private static final Logger logger = LoggerFactory.getLogger(GptClient.class);

    private static final String OPEN_AI_URL = EnvLoader.getApiUrl();
    private static final String OPEN_AI_KEY = EnvLoader.getApiKey();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String getResponseJson(String prompt) throws IOException {

        URL url;
        try {
            url = new URL(OPEN_AI_URL);
            logger.debug("URL created correctly - {}", url.toString());
        } catch (MalformedURLException e) {
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

        logger.debug(httpPost.toString());

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            logger.debug("Return code = {}", response.getStatusLine().getStatusCode());
            logger.debug("Response - {}", response.getEntity().getContent());
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }


    public String getResponse(String prompt) throws IOException {

        String responseBody = getResponseJson(prompt);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);

        String content = rootNode.path("choices").get(0).path("message").path("content").asText();

        logger.debug("Email content: \n" + content );

        return content;
    }

}
