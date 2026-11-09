package ma.emsi.zouitina;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

import java.time.Duration;

public class Test2Couts {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");
        if (cle == null || cle.isBlank()) {
            throw new IllegalStateException("Variable GEMINI_KEY absente");
        }

        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .timeout(Duration.ofSeconds(90))
                .maxRetries(0)
                .build();

        String question = "Quelle est la capitale du Maroc ?";
        System.out.println("Question : " + question);

        ChatResponse reponse = modele.chat(UserMessage.from(question));
        var tokens = reponse.tokenUsage();

        int entree = tokens.inputTokenCount();
        int sortie = tokens.outputTokenCount();
        // Pour cette requête sans outil, la sortie facturable inclut le raisonnement.
        int sortieFacturable = tokens.totalTokenCount() - entree;
        double cout = (entree * 0.30 + sortieFacturable * 2.50) / 1_000_000;

        System.out.println("Réponse : " + reponse.aiMessage().text());
        System.out.println("Tokens d'entrée : " + entree);
        System.out.println("Tokens de sortie (texte) : " + sortie);
        System.out.println("Tokens de sortie (raisonnement compris) : " + sortieFacturable);
        System.out.printf("Coût estimé : %.8f USD%n", cout);
        System.out.printf("Requêtes similaires pour 1 USD : environ %.0f%n", 1 / cout);
    }
}
