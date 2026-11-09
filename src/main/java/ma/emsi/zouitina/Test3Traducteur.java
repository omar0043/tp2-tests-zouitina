package ma.emsi.zouitina;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.time.Duration;
import java.util.Map;

public class Test3Traducteur {

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

        String texte = "Bonjour, comment allez-vous ?";

        // Variante 1 : seul le texte à traduire est variable.
        PromptTemplate versAnglais = PromptTemplate.from(
                "Traduis en anglais et réponds uniquement avec la traduction : {{texte}}"
        );
        Prompt promptAnglais = versAnglais.apply(Map.of("texte", texte));
        System.out.println("Anglais : " + modele.chat(promptAnglais.text()));

        // Variante 2 : le texte et la langue sont variables.
        PromptTemplate versLangue = PromptTemplate.from(
                "Traduis en {{langue}} et réponds uniquement avec la traduction : {{texte}}"
        );
        Prompt promptEspagnol = versLangue.apply(Map.of(
                "langue", "espagnol",
                "texte", texte
        ));
        System.out.println("Espagnol : " + modele.chat(promptEspagnol.text()));
    }
}
