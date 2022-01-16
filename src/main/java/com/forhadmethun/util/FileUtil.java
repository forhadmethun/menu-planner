package com.forhadmethun.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    public static Map<String, String> readAllUserContents() throws IOException {
        Map<String, String> userContentByUserId = new HashMap<>();
        var usersResource = new ClassPathResource("data/users.txt");
        var userContent = getFileContent(usersResource);
        Arrays.stream(userContent.strip().split(","))
            .forEach(user -> {
                try {
                    var currentUserResource = new ClassPathResource(String.format("data/%s.json", user));
                    var currentUserContent = getFileContent(currentUserResource);
                    userContentByUserId.put(user, currentUserContent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        return userContentByUserId;
    }

    private static String getFileContent(Resource resource) throws IOException {
        InputStream inputStream = resource.getInputStream();
        var reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            var line = reader.readLine();
            sb.append(line);
        }
        return sb.toString();
    }

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

}
