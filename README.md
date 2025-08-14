# PDF-CHAT-AI agent

PDF-CHAT-AI agent processes PDF document, extracts both text and images, generates image descriptions using an LLM (by using OLLAMA), 
and indexes the content with embeddings to enable retrieval-augmented generation (RAG).

### Input:
- **PDF data file**
    - (for example casandra.pdf it is in project folder)

### Output:
- **Command line chat**
   Example answer for question that data **exist** in pdf document:

      shell:>q "What is Casandra?"
      Based on the provided text, Cassandra is:
      * **A distributed storage system:** This means it stores data across multiple servers, not just one.
      * **Designed for large amounts of structured data:** It's built to handle big datasets with a defined format.
      * **Highly available:**  Even if some servers fail, Cassandra can keep functioning and providing access to data.
      * **Runs on commodity servers:** It uses standard, off-the-shelf hardware, making it cost-effective.
      * **Not a full relational database:** While it shares similarities with traditional databases, it doesn't support the full range of features like complex joins or transactions. Instead, it offers a simpler data model for clients to work with.
   
   Example answer for question that data **not exist** in pdf document:

        shell:>q "What is moon?"
        The provided text doesn't mention anything about "moon". It focuses on describing the data model and design aspects of Cassandra, a distributed storage system.
        Let me know if you have any other questions about Cassandra or need information on something else! 

## How to Use

PDF-CHAT-AI Agent requires Java 21, Chromadb vector database (tested on version 0.6.3) to be run, and Ollama/vLLM instance to be run.
Configuration file name ***pdfchatai.yml*** it is located in the project, example settings inside that file:
```yml
pdfchatai:
  chromaDB:
    baseUrl: "http://localhost:8000"
  chatModel:
    baseUrl: "http://20.185.83.16:8080/" #ollama address
    apiKey: "<API-KEY>" #ollama api key
    modelName: "gemma2" # language model name installed in ollama
    temperature: 0.0
    timeout: 3600 #in seconds
  pdfDataFile: "C:\\projects\\AI\\cassandra.pdf" # pdf file that is loaded to the chromadb vector database
```

To install chromadb locally:
- we need install python version 3.9
- then install chromadb using package manager: pip install chromadb==0.6.3
- run chromadb: chroma run --host localhost --port 8000