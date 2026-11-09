package ma.emsi.zouitina;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

import java.time.Duration;

public class Test1 {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        if (cle == null || cle.isBlank()) {
            throw new IllegalStateException("Variable GEMINI_KEY absente");
        }

        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash")
                .temperature(0.7)
                .timeout(Duration.ofSeconds(90))
                .maxRetries(0)
                .build();

        poserQuestion(modele, "Quelle est la capitale du Maroc ?");
        poserQuestion(modele, "Quelle heure est-il actuellement à Casablanca ?");

        // Chaque appel envoie sa propre question, sans l'historique.
        poserQuestion(modele, "Bonjour, je m'appelle Omar Zouitina.");
        poserQuestion(modele, "Comment est-ce que je m'appelle ?");
    }

    private static void poserQuestion(ChatModel modele, String question) {
        System.out.println("Question : " + question);
        System.out.println("Réponse : " + modele.chat(question));
        System.out.println();
    }
}