package de.dustboystudios.jarfinder;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The functionality to find the jars 
 *
 * @author Manuel Vögele
 */
public class Finder
{
	/**
	 * Returns a collection containing the files which contain the class name searched for
	 * @param className the full qualified class name to search for e.g. java.util.Date
	 * @param searchDirectory the directory to search in
	 * @return a collection containing the files
	 */
	public static Collection<File> findJars(String className, File searchDirectory)
	{
		Collection<File> jars = new HashSet<File>();
		findJars(className, searchDirectory, jars);
		return jars;
	}
	
	/**
	 * Finds the jar files containing the specified class and adds them to the specified collection
	 * @param className the full qualified class name to search for e.g. java.util.Date
	 * @param searchDirectory the directory to search in
	 * @param jars the collection to add the found jars to
	 */
	public static void findJars(String className, File searchDirectory, Collection<File> jars)
	{
		for (File file : searchDirectory.listFiles())
		{
			if (file.isDirectory())
			{
				findJars(className, file, jars);
			}
			else
			{
				try
				{
					if (jarFileContains(new JarFile(file), className))
					{
						jars.add(file);
					}
				}
				catch (IOException e)
				{
					// Nothing to do
				}
			}
		}
	}
	
	/**
	 * Returns whether the jar contains the specified class
	 * @param jar the jar file
	 * @param className the full qualified name of the class e.g. java.util.Date
	 * @return whether the jar contains the specified class
	 */
	public static boolean jarFileContains(JarFile jar, String className)
	{
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements())
		{
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			if (name.endsWith(".class") || name.endsWith(".java"))
			{
				name = name.substring(0, name.lastIndexOf('.'));
				name = name.replace('/', '.');
				name = name.replace('$', '.');
				if (className.equals(name))
				{
					return true;
				}
			}
		}
		return false;
	}
}
