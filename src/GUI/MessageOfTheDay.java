package GUI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MessageOfTheDay {

    public String getMessage() throws IOException, InterruptedException {
        String Sol = "";
        int count = 1;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://cswebcat.swansea.ac.uk/puzzle"))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String str = response.body();

        System.out.println("Input From Website: " + str);

        char[] charArr = str.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (count == 1) {
                Sol += (char) ((((charArr[i] - 'A' - (i+1)) % 26) + 26) % 26  + 'A');
                count++;
            } else {
                Sol += (char) ((((charArr[i] - 'A' + i+1) % 26) + 26) % 26  + 'A');
                count--;
            }
        }

        Sol += "CS-230";
        count = Sol.length();
        Sol = count+Sol;
        System.out.println(Sol);

        HttpClient clientS = HttpClient.newHttpClient();
        HttpRequest requestS = HttpRequest.newBuilder()
                .uri(URI.create("http://cswebcat.swansea.ac.uk/message?solution=" + Sol))
                .build();

        HttpResponse<String> responseS = clientS.send(requestS,
                HttpResponse.BodyHandlers.ofString());

        System.out.print(responseS.body());
        return responseS.body();
    }
}

