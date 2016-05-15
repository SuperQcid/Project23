package knof.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {

    private static final String pluginDir = "plugins";

    /**
     * Find all plugins in the plugin directory
     * @return an array with all plugins
     */
    public static ArrayList<String> getJarList(){
        File f = new File(getPluginDir());
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
        File f = new File(getPluginDir());
        f.mkdirs();
    }

    /**
     * Initializes all plugins found by getJarlist function
     */
    public HashMap<String, Plugin> InitializePlugins(){
        ArrayList<String> jarList = getJarList();
        HashMap<String, Plugin> plugins = new HashMap<>();

        for (String jar : jarList){
            Plugin plugin = loadPlugin(jar);
            String gameName;
            if ((gameName = plugin.getGameName()) != null) {
                plugins.put(gameName, plugin);
            }
        }
        return plugins;
    }

    /**
     * Load a plugin to be used in the framework.
     * @param file the jar file to load
     * @return the plugin class or null when failed to load.
     */
    @SuppressWarnings(value = { "rawtypes", "resource"})
    public static Plugin loadPlugin(String file){
        File game = new File(pluginDir + File.separator + file);
        Plugin plugin = null;
        try {
            JarFile jarFile = new JarFile(game);
            Enumeration<JarEntry> e = jarFile.entries();

            URL[] urls = { new URL("jar:file:" + game+"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class")){
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0,je.getName().length()-6);
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);

                if (Plugin.class.isAssignableFrom(c)) {
                    plugin = (Plugin) c.newInstance();
                }
            }
//            JarFile jarFile = new JarFile(game);
//            URLClassLoader classLoader = new URLClassLoader(new URL[]{game.toURI().toURL()}, ClassLoader.getSystemClassLoader());
//
//            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements(); ) {
//                JarEntry jarEntry = entries.nextElement();
//
//                if (!jarEntry.getName().endsWith(".class")) {
//                    continue;
//                }
//
//                String className = jarEntry.getName().replace('/', '.').replace(".class", "");
//                Class<?> pluginClass = Class.forName(className, true, classLoader);
//
//                if (pluginClass.getSuperclass().equals(Plugin.class)){
//                    if (!Modifier.isAbstract(pluginClass.getModifiers()) && !Modifier.isPrivate(pluginClass.getModifiers())){
//                        return (Plugin)pluginClass.newInstance();
//                    }
//                }
//            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return plugin;
//        return null;
    }
}
