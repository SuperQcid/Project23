package knof.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarFile;

public class PluginLoader {

    private static String pluginDir = "plugins";

    /**
     * Find all plugins in the plugin directory
     * @return an array with all plugins
     */
    public static ArrayList<String> findPlugins(){
        File f = new File(pluginDir);
        ArrayList<String> jars = new ArrayList<>();

        if (!f.isDirectory()){
            System.out.println("Plugins folder does not exist! Creating one...");
            createPluginsFolder();
        }

        for (String jar : f.list()){
            if (jar.endsWith(".jar")){
                jars.add(jar);
            }
        }
        return jars;
    }

    /**
     * Gets the plugin directory.
     * @return plugin directory
     */
    public static String getPluginDir() {
    	return pluginDir;
    }

    /**
     * Creates the plugins folder when it does not exist.
     */
    public static void createPluginsFolder(){
        File f = new File(pluginDir);
        f.mkdirs();
    }

    /**
     * Load a plugin to be used in the framework.
     * @param game the jar file to load
     * @return the plugin class or null when failed to load.
     */
    @SuppressWarnings(value = { "rawtypes", "resource"})
    public static Plugin loadPlugin(String game){
        for (String plugin : findPlugins()){
            if (!plugin.toLowerCase().contains(game.toLowerCase())){
                continue;
            }
            File pluginFile  = new File(pluginDir + File.separator + plugin);

            try {
                URL[] url = new URL[]{pluginFile.toURI().toURL()};
                JarFile j = new JarFile(pluginFile);
                String mainClassName = j.getManifest().getMainAttributes().getValue("Plugin-Class");

				ClassLoader classLoader = new URLClassLoader(url);
                Class mainClass = classLoader.loadClass(mainClassName);
                return (Plugin) mainClass.newInstance();

            }catch(IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
