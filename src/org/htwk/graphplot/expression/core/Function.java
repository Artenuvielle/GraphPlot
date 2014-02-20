package org.htwk.graphplot.expression.core;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.htwk.graphplot.expression.ClassEnumerator;
import org.htwk.graphplot.expression.FunctionInformation;
import org.htwk.graphplot.expression.InvalidExpressionException;

/**
 * This abstract class is a prototype for any function which should later be
 * implemented. It automatically loads all extending classes using reflections
 * for a inversion-of-control behavior. Extending classes must have in addition
 * to unimplemented method two static fields:
 * <ul>
 * <li>public static String functionName</li>
 * <li>public static int[] acceptedParameterNumber</li>
 * </ul>
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public abstract class Function implements Expression {

	public static LinkedList<FunctionInformation> availableFunctions = new LinkedList<FunctionInformation>();
	private static final Logger logger = Logger.getLogger(Function.class.getName());

	/**
	 * This method should be called once at the startup of the project to load
	 * all classes extending Function and making them accessible through the
	 * static list Function.availableFunctions.
	 */
	public static void loadAllFunctions() {
		List<Class<?>> classes = ClassEnumerator.getClassesForPackage(ClassEnumerator.class.getPackage());
		for (Class<?> currentClass : classes) {
			if (isClassExtendingFunction(currentClass)) {
				interpretFunctionClass((Class<? extends Function>) currentClass);
			}
		}
	}

	/**
	 * Tests if the given class is in the functions package and extends
	 * Function.
	 * 
	 * @param classToTest
	 *            The class which shall be tested
	 * @return True, if the class matches the searched criteria
	 */
	private static boolean isClassExtendingFunction(Class<?> classToTest) {
		return classToTest.getPackage().getName().contains("expression.functions") && classToTest.getSuperclass() == Function.class;
	}

	/**
	 * Performs a check if the class has all the needed static fields and loads
	 * its information.
	 * 
	 * @param functionClass
	 *            The class extending Function to load
	 */
	private static void interpretFunctionClass(Class<? extends Function> functionClass) {
		FunctionInformation functionInformationForCurrentClass = null;
		for (Field f : functionClass.getFields()) {
			try {
				if (f.getName().equals("functionInformation")) {
					if (f.get(null) != null) {
						functionInformationForCurrentClass = (FunctionInformation) f.get(null);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.severe("The class " + functionClass.getName() + " could not be loaded correctly because the functionInformation field was not readable.");
			}
		}
		if (functionInformationForCurrentClass != null) {
			availableFunctions.add(functionInformationForCurrentClass);
		} else {
			logger.warning("The class " + functionClass.getName() + " is in the expression.functions package but does not contain the expected function information.");
		}
	}

	/**
	 * Searches the List of all function information for the one which holds a
	 * given function name.
	 * 
	 * @param functionStringToLookFor
	 *            The function name to search for.
	 * @return The function information containing the searched function name
	 * @throws InvalidExpressionException
	 *             is thrown if a function is searched which was not previously
	 *             registered.
	 */
	public static FunctionInformation getFunctionInformationForFunctionName(String functionStringToLookFor) throws InvalidExpressionException {
		for (FunctionInformation currentInformation : availableFunctions) {
			if (functionStringToLookFor.equals(currentInformation.getFunctionName()))
				return currentInformation;
		}
		throw new InvalidExpressionException("No such function registered: " + functionStringToLookFor);
	}

}
