import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 *
 * @author Francisco Rodrigues
 */
public class FileList {
    private java.util.Queue<TST> list;
    
    /**
     * Inicializa o atributo list.
     */
    public FileList() {
        this.list = new LinkedList<>();
    }

    /**
     * Metodo get que retorna a lista de tries.
     * @return list
     */
    public Queue<TST> getList() {
        return list;
    }
    
    /**
     * Metodo que adiciona uma trie na lista.
     * @param file 
     */
    private void add (TST file) {
        list.add(file);
    }
    
    /**
     * Função recebe uma String str
     * e retorna a String sem acentos nos
     * caracteres. 'á' -> 'a'.
     * @param str
     * @return String
     */
    private static String removeAccents (String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    
    /**
     * A funcao recebe uuma String e a retorna
     * sem acentos e apenas letras minusculas.
     * @param str
     * @return 
     */
    public String clearString (String str) {
        String aux = "";
        str = removeAccents (str);
        for (int i = 0 ; i < str.length(); i++) {
            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                aux += (char) (str.charAt(i) + 32);
            else
                aux += (char) str.charAt(i);
        }
        return aux;
    }
    
    /**
     * A funcao recebe um vector de String e dobra
     * seu tamanho (length*2).
     * @param find
     * @return 
     */
    public String[] resizeVector (String[] find) {
        String[] aux = new String[find.length*2];
        for (int i = 0; i < find.length; i++)
            aux[i] = find[i];
        return aux;
    }
    
    /**
     * A funcao recebe o diretorio de um arquivo e retorna
     * um vetor de String cotendo todas as palavras desse arquivo(.txt)
     * @param file
     * @return 
     */
    public String[] stringVector (String file) {
        Scanner scanner;
        String linha, palavra;
        String[] words = new String[1000];
        int i = 0;
        
        try {
            FileReader arq = new FileReader(file);
            //BufferedReader lerArq = new BufferedReader(arq);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader
                (new FileInputStream(file), "ISO-8859-1"));
   
            linha = lerArq.readLine(); 
            // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                scanner = new Scanner(linha);
                
                while (scanner.hasNext()) {
                    palavra = scanner.next();
                    palavra = clearString(palavra);
                    words[i++] = palavra;
                    if ((i + 1) == words.length)
                        words = resizeVector(words);
                }
                
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
              e.getMessage());
        }
        
        return words;
    }
    
    /**
     * A funcao recebe um vetor de String e uma String
     * retorna um pair contendo a propria String, e mais duas strings
     * que contem 5 palavras antes e depois(Claro se for possivel, caso não seja
     * retorna a quantidade existente).
     * @param find
     * @param palavra
     * @return 
     */
    public Pair findString (String[] find, String palavra) {
        int where = 0, count = 5, aux;
        String before = "", after = "";
        for (int i = 0; i < find.length; i++) {
            if (find[i] != null)
                if (find[i].equals(palavra)) {
                    where = i;
                    break;
                }
        }
        aux = where - 1;
        while(count > 0 && aux >= 0) {
            if (find[aux] != null)
                before += find[aux] + " ";
            count--;
            aux--;
        }
        aux = where + 1;
        count = 5;
        while(count > 0 && aux <= find.length) {
            if (find[aux] != null)
                after += " " + find[aux];
            count--;
            aux++;
        }
        
        Pair ready = new Pair(before, after, palavra);
        return ready;
    }
    
    /**
     * Metodo recebe um diretorio de um arquivo
     * e adiciona seu texto em uma trie, por fim adiciona
     * essa trie em uma lista de trie.
     * @param file 
     * @param name 
     */
    public void makeTrie (String file, String name) {
        TST tst = new TST<>(name, file);
        Scanner scanner;
        String linha, palavra;
        String[] words = stringVector(file);
        
        try {
            FileReader arq = new FileReader(file);
            //BufferedReader lerArq = new BufferedReader(arq);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader
                (new FileInputStream(file), "ISO-8859-1"));
   
            linha = lerArq.readLine(); 
            // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                scanner = new Scanner(linha);
                
                while (scanner.hasNext()) {
                    palavra = scanner.next();
                    palavra = clearString(palavra);
                    Pair pair = findString(words, palavra);
                    //System.out.println(pair.getBefore() + " (***) " + pair.getAfter());
                    //System.out.println(palavra);
                    tst.put(palavra, pair);
                }
                
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
              e.getMessage());
        }
        
        this.list.add(tst);
    }
    
    /**
     * Metodo recebe um diretorio de arquivos, e aplica o metodo
     * "makeTrie" em todos os arquivos desse diretorio.
     * @param diretorio 
     */
    public void FilesTrie (String diretorio) {
        File listFile = new File(diretorio);
        File[] files = listFile.listFiles();
     
        for (int i = 0; i < files.length; i++)
            makeTrie(diretorio + "/" + files[i].getName(), files[i].getName());
    }
    
    /**
     * Retorna true caso o atomo passado como argumento
     * for um conectivo do alfabeto.
     * @param atom
     * @return 
     */
    public boolean isConectivo(String atom) {
        return atom.equals("AND") || atom.equals("ANDNOT") || atom.equals("OR") || atom.equals("ORNOT")
                || atom.equals("NOT");
    }
    
    /**
     * Retorna um array de booleanos referente à
     * avalição de cada atomo proposicional.
     * @param atoms
     * @param arq
     * @param pairs
     * @return 
     */
    public Boolean[] wordsAnalyser(String[] atoms, TST arq, Pair[] pairs) {
        Boolean[] words;
        int counter = 0, p = 0;
        for (String atom : atoms) 
            if (!isConectivo(atom)) ++counter;
        words = new Boolean[counter];
        for (String atom : atoms) { 
            if (!isConectivo(atom)) { 
                words[p] = arq.contains(atom);
                pairs[p] =(Pair) arq.get(atom);
                p++;
            }
        }        
        return words;
    }
    
    /**
     * Retorna um Iterable para os conectivos presentes
     * no vetor de atomos.
     * @param atoms
     * @return 
     */
    public Queue<String> conectivosAnalyser(String[] atoms) {
        Queue<String> q = new LinkedList<>();
        String atom = "";
        int i = 0;
        while (i < atoms.length) {
            if (isConectivo(atoms[i])) { 
                atom += atoms[i];
                if (i != atoms.length-1 && isConectivo(atoms[i+1])) {
                    atom += atoms[i+1];
                    ++i;
                }
            }
            q.add(atom);
            atom = "";
            ++i;
        }
        return q;
    }
    
    /**
     * Avalia o resultado da proposição composta
     * pelos conectivos e palavras.
     * @param conectivos
     * @param words
     * @return 
     */
    private boolean logicAnalyser(Queue<String> conectivos, Boolean[] words, Pair[] pairs) {
        int p = 0;
        System.out.println("vetor de pairs:");
        for (int i = 0; i < words.length; ++i)
            if (pairs[i] != null) {
                System.out.println(pairs[i].getName());
            }
        System.out.println("fim\n");
        for (String con : conectivos) {
            if (con.equals("NOT")) { 
                words[p] = !words[p];
                pairs[p] = null;
            }
            else if (con.equals("AND")) {
                words[p+1] = words[p] && words[p+1];
                ++p;
            }
            else if (con.equals("ANDNOT")) {
                words[p+1] = words[p] && !words[p+1];
                pairs[p+1] = null;
                ++p;
            }
            else if (con.equals("OR")) {
                words[p+1] = words[p] || words[p+1];
                ++p;
            }
            else if (con.equals("ORNOT")) {
                words[p+1] = words[p] || !words[p+1];
                pairs[p+1] = null;
                ++p;
            }
        }
        return words[p];
    }
    
    /**
     * Retorna uma lista de Informs dos arquivos
     * que atendem a consulta querry.
     * @param querry 
     * @return  
     */
    public java.util.Queue logicAnalyser(String querry) {
        String[] atoms = querry.split(" ");
        Boolean[] words;
        java.util.Queue<Pair> results;
        java.util.Queue<Inform> ret = new LinkedList<>();
        int counter = 0;
        Pair[] pairs = new Pair[100000];
        Queue<String> conectivos = conectivosAnalyser(atoms);
        for (TST arq : list) {
            results = new LinkedList<>();
            words = wordsAnalyser(atoms, arq, pairs);
            System.out.println("testando arquivo "+arq.getFileName());
            if (logicAnalyser(conectivos, words, pairs)) { 
                System.out.println("sucesso, eis os resultados:");
                for (int i = 0; i < words.length; ++i)
                    if (pairs[i] != null) results.add(pairs[i]);
                counter = 0;
                System.out.println("palavras:");
                for (Pair p : results) {
                    System.out.println("p"+counter+ "= " +p.getName());
                    ++counter;
                }
                Inform info = new Inform(results, arq.getFileName(), arq.getDirName());
                ret.add(info);
                System.out.println("");
            }
            else System.out.println("falha");
        }
        return ret;
    }
    
}