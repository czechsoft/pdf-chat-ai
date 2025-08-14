package io.github.czechsoft.pdfchat.configuration;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static java.time.Duration.ofSeconds;

@Configuration
public class BeanConfiguration {

    private final PdfChatAiConfig  pdfChatAiConfig;

    public BeanConfiguration(PdfChatAiConfig pdfChatAiConfig) {
        this.pdfChatAiConfig = pdfChatAiConfig;
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    public ChromaEmbeddingStore  chromaEmbeddingStore() {
        return ChromaEmbeddingStore.builder()
                .baseUrl(pdfChatAiConfig.chromaDB().baseUrl())
                .collectionName(""+System.currentTimeMillis())
                .build();
    }

    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor() {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .embeddingModel(embeddingModel())
                .embeddingStore(chromaEmbeddingStore())
                .build();
    }

    @Bean
    public ConversationalRetrievalChain conversationalRetrievalChain() {
        return ConversationalRetrievalChain.builder()
                .chatModel(OllamaChatModel.builder()
                        .customHeaders(Map.of("Authorization", "Bearer " + pdfChatAiConfig.chatModel().apiKey()))
                        .baseUrl(pdfChatAiConfig.chatModel().baseUrl())
                        .temperature(pdfChatAiConfig.chatModel().temperature())
                        .modelName(pdfChatAiConfig.chatModel().modelName())
                        .timeout(ofSeconds(pdfChatAiConfig.chatModel().timeout()))
                        .build())
                .contentRetriever(EmbeddingStoreContentRetriever.builder()
                        .embeddingModel(embeddingModel())
                        .embeddingStore(chromaEmbeddingStore())
                        .build())
                .build();
    }

}
