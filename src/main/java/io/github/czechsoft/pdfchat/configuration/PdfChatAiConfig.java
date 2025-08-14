package io.github.czechsoft.pdfchat.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pdfchatai")
public record PdfChatAiConfig(
        ChromaDB chromaDB,
        ChatModel chatModel,
        String pdfDataFile
) {

    public record ChromaDB(
            String baseUrl
    ) {
    }

    public record ChatModel(
            String baseUrl,
            String apiKey,
            String modelName,
            Double temperature,
            Integer timeout
    ) {
    }
}
