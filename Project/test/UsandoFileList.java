
import java.io.FileNotFoundException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Francisco Rodrigues
 */
public class UsandoFileList {
    public static void main(String[] args) throws FileNotFoundException {
        FileList list = new FileList();
        
        String diretorio = "C:\\Users\\Francisco Rodrigues\\Documents\\txts";
        
        list.FilesTrie(diretorio);
        
        System.out.println(list.getList().size());
    }
}
