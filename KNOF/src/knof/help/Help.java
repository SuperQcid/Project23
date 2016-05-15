package knof.help;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import knof.util.FileHelper;

/**
 * Reads in text files with help texts and display the text in a popup.
 */
public class Help {

    private ArrayList<Path> filePaths;

    public Help(){
        filePaths = new ArrayList<>();
        filePaths = FileHelper.getFilesInFolder("help");
    }
    
    /**
     * Returns an array of the filepaths in the help folder.
     * @return ArrayList
     */
    public ArrayList<Path> getPaths(){
        return filePaths;
    }

    /**
     * Returns a filepath as a string
     * @param path The path of the file which text should be returned
     * @return The text the file contains
     */
    public String getText(Path path){
        try {
            return FileHelper.readFile(path);
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }
}
