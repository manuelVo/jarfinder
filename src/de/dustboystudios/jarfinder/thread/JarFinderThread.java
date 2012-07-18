package de.dustboystudios.jarfinder.thread;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import de.dustboystudios.jarfinder.Finder;

/**
 * A thread for finding the jars 
 *
 * @author Manuel Vögele
 */
public class JarFinderThread extends Thread
{
	/**
	 * The name of the class to search
	 */
	private final String className;
	
	/**
	 * The directory to search in
	 */
	private final File searchDirectory;
	
	/**
	 * The listener to call after the thread finished its job
	 */
	private final JarFinderThreadFinishedListener listener;
	
	/**
	 * The result
	 */
	private volatile String result;
	
	/**
	 * Initializes a new JarFinderThread
	 * 
	 * @param className
	 *           the name of the class to search
	 * @param searchDirectory
	 *           the directory to search in
	 * @param listener
	 *           the listener to call after the thread finished its job
	 */
	public JarFinderThread(String className, File searchDirectory, JarFinderThreadFinishedListener listener)
	{
		this.className = className;
		this.searchDirectory = searchDirectory;
		this.listener = listener;
	}
	
	@Override
	public void run()
	{
		try
		{
			Collection<File> jars = Finder.findJars(className, searchDirectory);
			StringBuilder builder = new StringBuilder();
			for (File file : jars)
			{
				try
				{
					builder.append(file.getCanonicalPath()).append('\n');
				}
				catch (IOException e)
				{
					// Nothing to do
				}
			}
			result = builder.toString();
		}
		catch (Exception e)
		{
			StringBuilder builder = new StringBuilder();
			builder.append(e.toString());
			for (StackTraceElement element : e.getStackTrace())
			{
				builder.append('\n').append(element.toString());
			}
			result = builder.toString();
		}
		listener.onJarFinderThreadFinished(this);
	}
	
	/**
	 * Returns the result, <code>null</code> if the thread has not finished
	 * execution yet.
	 * 
	 * @return the result
	 */
	public String getResult()
	{
		return result;
	}
	
	/**
	 * A listener called after the {@link JarFinderThread} finished its job 
	 *
	 * @author Manuel Vögele
	 */
	public interface JarFinderThreadFinishedListener
	{
		/**
		 * Called when the {@link JarFinderThread}
		 * 
		 * @param thread
		 *           the thread that finished its job
		 */
		void onJarFinderThreadFinished(JarFinderThread thread);
	}
}
