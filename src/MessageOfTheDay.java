import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MessageOfTheDay {

    public static void main(String[] args) throws IOException, InterruptedException {

        String Sol;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://cswebcat.swansea.ac.uk/puzzle"))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String str = response.body();

        // Creating array of string length
        char[] cr = new char[str.length()];
        Sol = Integer.toString(str.length() + 6);

        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            cr[i] = str.charAt(i);
        }

        System.out.println();

        // Printing content of array <will be deleted>
        for (char c : cr) {
            Sol += c;
        }

        Sol += "CS-230";
        System.out.println(Sol);

        HttpClient clientS = HttpClient.newHttpClient();
        HttpRequest requestS = HttpRequest.newBuilder()
                .uri(URI.create("http://cswebcat.swansea.ac.uk/message?solution=" + Sol))
                .build();

        HttpResponse<String> responseS = clientS.send(requestS,
                HttpResponse.BodyHandlers.ofString());

                System.out.print(responseS.body());
    }
}