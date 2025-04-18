import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class SteamApiExample {
    public static void main(String[] args) {

        // Chargement des infos sensibles depuis config.properties
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration : " + e.getMessage());
            return;
        }

        String apiKey = props.getProperty("steam.api.key");
        String steamId = props.getProperty("steam.user.id");

        if (apiKey == null || steamId == null) {
            System.err.println("Clé API ou Steam ID manquants dans config.properties");
            return;
        }

        // Création des URLs
        String profileUrl = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/" +
                "?key=" + apiKey + "&steamids=" + steamId;

        String bansUrl = "https://api.steampowered.com/ISteamUser/GetPlayerBans/v1/" +
                "?key=" + apiKey + "&steamids=" + steamId;

        String ownedGamesUrl = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/" +
                "?key=" + apiKey + "&steamid=" + steamId + "&include_appinfo=true&include_played_free_games=true";

        // Requêtes HTTP (My mentor told me to "comment," so I comment...)
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest profileRequest = HttpRequest.newBuilder()
                .uri(URI.create(profileUrl))
                .GET()
                .build();

        HttpRequest bansRequest = HttpRequest.newBuilder()
                .uri(URI.create(bansUrl))
                .GET()
                .build();

        HttpRequest ownedGamesRequest = HttpRequest.newBuilder()
                .uri(URI.create(ownedGamesUrl))
                .GET()
                .build();

        // Exécution et affichage
        System.out.println("Récupération des informations de profil...");
        client.sendAsync(profileRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    System.out.println("Informations de profil :");
                    System.out.println(response);
                })
                .join();

        System.out.println("\nRécupération des informations de bannissement...");
        client.sendAsync(bansRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    System.out.println("Informations de bannissement :");
                    System.out.println(response);
                })
                .join();

        System.out.println("\nRécupération des informations des jeux possédés...");
        client.sendAsync(ownedGamesRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    System.out.println("Informations des jeux possédés :");
                    System.out.println(response);
                })
                .join();
    }
}


//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class SteamApiExample {
//    public static void main(String[] args) {
//        // Clé API web Steam
//        String apiKey = "F7F12002E531112607CDD5EACE530AEA";
//
//        // ID Steam de l'utilisateur
//        String steamId = "76561198082974906";
//
//        // URL pour récupérer les informations sur l'utilisateur
//        String profileUrl = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/" +
//                "?key=" + apiKey + "&steamids=" + steamId;
//
//        // URL pour récupérer les informations de bannissement
//        String bansUrl = "https://api.steampowered.com/ISteamUser/GetPlayerBans/v1/" +
//                "?key=" + apiKey + "&steamids=" + steamId;
//
//        // Infos générales
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest profileRequest = HttpRequest.newBuilder()
//                .uri(URI.create(profileUrl))
//                .GET()
//                .build();
//
//        // Infos bannissements
//        HttpRequest bansRequest = HttpRequest.newBuilder()
//                .uri(URI.create(bansUrl))
//                .GET()
//                .build();
//
//        // Envoi des requêtes et gestion des réponses
//        System.out.println("Récupération des informations de profil...");
//        client.sendAsync(profileRequest, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenAccept(response -> {
//                    System.out.println("Informations de profil :");
//                    System.out.println(response);
//                })
//                .join();
//
//        System.out.println("\nRécupération des informations de bannissement...");
//        client.sendAsync(bansRequest, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenAccept(response -> {
//                    System.out.println("Informations de bannissement :");
//                    System.out.println(response);
//                })
//                .join();
//    }
//}