import java.util.Queue;

/**
 * Classe que representa o resultado de uma consulta bem sucedida.
 * @author Lucas Tiago
 */
public class Inform {
    private java.util.Queue<Pair> palavras; // vetor de palavras que foram consultadas 
    private String FileName; // Nome do arquivo
    private String DirName; // Nome do diretorio do arquivo
    
    Inform(java.util.Queue<Pair> palavras, String FileName, String DirName) {
        this.palavras = palavras;
        this.FileName = FileName;
        this.DirName = DirName;
    }
    
    public Queue<Pair> getPalavras() {
        return palavras;
    }

    public String getFileName() {
        return FileName;
    }

    public String getDirName() {
        return DirName;
    }

    public void setPalavras(Queue<Pair> palavras) {
        this.palavras = palavras;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public void setDirName(String DirName) {
        this.DirName = DirName;
    }
    
    
}
