package de.dustboystudios.jarfinder.thread;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import de.dustboystudios.jarfinder.Finder;

public class JarFinderThread extends Thread
{
	private final String className;
	private final File searchDirectory;
	private final JarFinderThreadFinishedListener listener;
	private volatile String result;
	
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
	 * Returns the result, <code>null</code> if the thread has not finished execution yet.
	 * @return the result
	 */
	public String getResult()
	{
		return result;
	}
	
	public interface JarFinderThreadFinishedListener
	{
		void onJarFinderThreadFinished(JarFinderThread thread);
	}
}
