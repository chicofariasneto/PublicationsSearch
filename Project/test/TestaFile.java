/**
 *
 * @author Lucas Tiago
 */
public class TestaFile {
    public static void main(String[] args) {
        FileList f = new FileList();
        f.FilesTrie("/home/void/Desktop/doc");
        java.util.Queue<Inform> resultado = f.logicAnalyser("NOT lerolero AND telma");
        for (Inform info : resultado) {
            System.out.println("Arquivo " + info.getFileName() + " atende a consulta");
            for (Pair p : info.getPalavras()) {
                System.out.println(p.getBefore());
                System.out.println(p.getName());
                System.out.println(p.getAfter());
            }
            System.out.println("");
        }
    }
}
