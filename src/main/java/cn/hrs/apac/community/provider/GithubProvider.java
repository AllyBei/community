package cn.hrs.apac.community.provider;

import cn.hrs.apac.community.dto.GithubAccessTokenDTO;
import cn.hrs.apac.community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    // Get github access token with personal github default OAuth settings
    public String getGithubAccessToken(GithubAccessTokenDTO githubAccessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        // Add access token to response body
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(githubAccessTokenDTO));

        // Create new request to https://github.com/login/oauth/access_token
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        // Get the response with JSON from https://github.com/login/oauth/access_token
        try (Response response = client.newCall(request).execute()) {
            // Get response from https://github.com/login/oauth/access_token
            String responseBody = response.body().string();
            // Split token from response
            String token = responseBody.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if IOException occurs
        return null;
    }

    // Get User information by accessToken
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        // Create a new request to github with access token
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {

            // Get JSON from response return body
            String json = response.body().string();

            // Get Java object from JSON
            GithubUser githubUser = JSON.parseObject(json, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return null if IOException occurs
        return null;
    }
}
