/*
 * File adopted from ddopson
 * https://github.com/ddopson/java-class-enumerator
 * JavaDoc written by
 * Sophie Eckenstaler, René Martin
 */

package org.htwk.graphplot.expression;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * This class provides static functions which can be used to scan a specified
 * package and receive all the contained classes. It is designed to work with
 * both directories and class paths inside JAR files.
 * 
 * @author ddopson, Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class ClassEnumerator {

	private static final Logger logger = Logger.getLogger(ClassEnumerator.class.getName());

	/**
	 * Creates searches a class by name.
	 * 
	 * @param className
	 *            the given class name to search for
	 * @return Class for the given name
	 */
	private static Class<?> loadClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
		}
	}

	/**
	 * Reads the files in a directory and identifies contained classes.
	 * 
	 * @param directory
	 *            Directory to scan in
	 * @param pkgname
	 *            Package name to scan for
	 * @param classes
	 *            List of classes which will be expanded by the found classes
	 */
	private static void processDirectory(File directory, String pkgname, ArrayList<Class<?>> classes) {
		logger.info("Reading Directory '" + directory + "'");
		// Get the list of the files contained in the package
		String[] files = directory.list();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i];
			String className = null;
			// we are only interested in .class files
			if (fileName.endsWith(".class")) {
				// removes the .class extension
				className = pkgname + '.' + fileName.substring(0, fileName.length() - 6);
			}
			logger.fine("FileName '" + fileName + "'  =>  class '" + className + "'");
			if (className != null) {
				classes.add(loadClass(className));
			}
			File subdir = new File(directory, fileName);
			if (subdir.isDirectory()) {
				processDirectory(subdir, pkgname + '.' + fileName, classes);
			}
		}
	}

	/**
	 * Reads the files in a jar file and identifies contained classes.
	 * 
	 * @param resource
	 *            Jar file to scan in
	 * @param pkgname
	 *            Package name to scan for
	 * @param classes
	 *            List of classes which will be expanded by the found classes
	 */
	private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes) {
		String relPath = pkgname.replace('.', '/');
		String resPath = resource.getPath();
		String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
		logger.info("Reading JAR file: '" + jarPath + "'");
		JarFile jarFile;
		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
		}
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			String className = null;
			if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
				className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
			}
			logger.fine("JarEntry '" + entryName + "'  =>  class '" + className + "'");
			if (className != null) {
				classes.add(loadClass(className));
			}
		}
		try {
			jarFile.close();
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException closing JAR File '" + jarPath + "'", e);
		}
	}

	/**
	 * This Method scans a Package inside directories or JAR-file and finds all
	 * contained classes.
	 * 
	 * @param pkg
	 *            the Package that shall be scanned
	 * @return a List of the classes found within the given Package
	 */
	public static ArrayList<Class<?>> getClassesForPackage(Package pkg) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		String pkgname = pkg.getName();
		String relPath = pkgname.replace('.', '/');

		// Get a File object for the package
		URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
		if (resource == null) {
			throw new RuntimeException("Unexpected problem: No resource for " + relPath);
		}
		logger.info("Package: '" + pkgname + "' becomes Resource: '" + resource.toString() + "'");

		resource.getPath();
		if (resource.toString().startsWith("jar:")) {
			processJarfile(resource, pkgname, classes);
		} else {
			processDirectory(new File(resource.getPath()), pkgname, classes);
		}

		return classes;
	}

}
