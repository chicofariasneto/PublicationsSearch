
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Francisco Rodrigues
 */
public class LendoFile {
    public static void main(String[] args) {
        String diretorio = "C:\\Users\\Francisco Rodrigues\\Documents"
                + "\\BoletimTXT\\acelera_promocao_de_servidor-73-2018.txt";
        try {
            FileReader arq = new FileReader(diretorio);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine(); 
            // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                System.out.printf("%s\n", linha);

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
              e.getMessage());
        }
    }
}
