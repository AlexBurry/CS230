package gui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class gets Liam's puzzle, solves it and
 * requests the Message Of The Day.
 *
 * @version 1.0
 * @Author Rex, Dom
 * @since 1.0
 */

public class MessageOfTheDay {

    /**
     * This method gets Liam's puzzle and
     * shifts every even number forward by their position
     * and every odd number backward by their position
     * adds "CS-230" to the end of the String (case-sensitive)
     * and adds the length of the String to the beginning.
     * Using the result it fetches the Message Of The Day.
     *
     * @return Message Of The Day.
     * @throws IOException
     * @throws InterruptedException
     */
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
                Sol += (char) ((((charArr[i] - 'A' - (i + 1)) % 26) + 26) % 26 + 'A');
                count++;
            } else {
                Sol += (char) ((((charArr[i] - 'A' + i + 1) % 26) + 26) % 26 + 'A');
                count--;
            }
        }

        Sol += "CS-230";
        count = Sol.length();
        Sol = count + Sol;
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

