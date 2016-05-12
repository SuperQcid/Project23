package knof.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class which can be used to read files in folders.
 */
public final class FileHelper {

    private static ArrayList<Path> filePaths;

    /**
     * Returns filepaths of all files in a given folder.
     * @return An ArrayList of the paths of all the files in the folder help.
     */
    public static ArrayList<Path> getFilesInFolder(String path){
        ArrayList<Path> filePaths = new ArrayList<>();
        File file = new File(System.getProperty("user.dir"));
        String directory = file.getName();
        String files = "";
        if(directory.equals("KNOF")){
        	files = path;
        } else {
        	files = "KNOF/" + path;
        }
        try {
            Files.walk(Paths.get(files)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    filePaths.add(filePath);
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
        return filePaths;
    }
    
    /**
     *
     * @param path The path of the file to be read
     * @return The text written in the file
     * @throws IOException
     */
    public static String readFile(Path path) throws IOException{
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String text ="";
        String str;
        while((str = reader.readLine())!= null && str.length()!=0){
            text += "\n" + str;
        }
        return text;
    }

    /**
     *
     * @return An ArrayList of all the filepaths in the folder help
     */
    public static ArrayList<Path> getPaths(){
        return filePaths;
    }

}
