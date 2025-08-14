package io.github.czechsoft.pdfchat.command;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.github.czechsoft.pdfchat.PdfChatAiApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Command
public class ChatCommand {

    private final EmbeddingStoreIngestor embeddingStoreIngestor;
    private final ConversationalRetrievalChain conversationalRetrievalChain;

    public ChatCommand(EmbeddingStoreIngestor embeddingStoreIngestor, ConversationalRetrievalChain conversationalRetrievalChain) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
        this.conversationalRetrievalChain = conversationalRetrievalChain;
    }

    @PostConstruct
    public void init() {
        System.err.println("Test 1");
        Document document = loadDocument(toPath("cassandra.pdf"), new ApachePdfBoxDocumentParser());
        System.err.println("Test 2");
        embeddingStoreIngestor.ingest(document);
        System.err.println("Test 3");
    }

    private static Path toPath(String fileName) {
        try {
            URL fileUrl = PdfChatAiApplication.class.getClassLoader().getResource(fileName);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Command(command = "q")
    public String question(String message) {
        return conversationalRetrievalChain.execute(message);
    }
}
