package io.github.czechsoft.pdfchat.command;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.github.czechsoft.pdfchat.configuration.PdfChatAiConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.shell.command.annotation.Command;

import java.io.File;
import java.nio.file.Path;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Command
public class ChatCommand {

    private final EmbeddingStoreIngestor embeddingStoreIngestor;
    private final ConversationalRetrievalChain conversationalRetrievalChain;
    private final PdfChatAiConfig pdfChatAiConfig;

    public ChatCommand(EmbeddingStoreIngestor embeddingStoreIngestor, ConversationalRetrievalChain conversationalRetrievalChain, PdfChatAiConfig pdfChatAiConfig) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
        this.conversationalRetrievalChain = conversationalRetrievalChain;
        this.pdfChatAiConfig = pdfChatAiConfig;
    }

    @PostConstruct
    public void init() {
        Document document = loadDocument(toPath(pdfChatAiConfig.pdfDataFile()), new ApachePdfBoxDocumentParser());
        embeddingStoreIngestor.ingest(document);
    }

    private static Path toPath(String fileName) {
        File file = new File(fileName);
        return file.toPath();
    }

    @Command(command = "q")
    public String question(String message) {
        return conversationalRetrievalChain.execute(message);
    }
}
