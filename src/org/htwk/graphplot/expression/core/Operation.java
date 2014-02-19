package org.htwk.graphplot.expression.core;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import org.htwk.graphplot.expression.ClassEnumerator;
import org.htwk.graphplot.expression.InvalidExpressionException;
import org.htwk.graphplot.expression.OperationInformation;

/**
 * This abstract class is a prototype for any operation which should later be
 * implemented. It automatically loads all extending classes using reflections
 * for a inversion-of-control behavior. Extending classes must have in addition
 * to unimplemented method two static fields:
 * <ul>
 * <li>public static String operationString</li>
 * <li>public static OperationImportance operationImportance</li>
 * </ul>
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public abstract class Operation implements Expression {

	public static LinkedList<OperationInformation> availableOperations = new LinkedList<OperationInformation>();
	private static final Logger logger = Logger.getLogger(Function.class.getName());

	private Expression expressionBeforeOperator, expressionAfterOperator;

	/**
	 * Prototype constructor for an operation combining two expressions
	 * 
	 * @param expressionBeforeOperator
	 *            The expression before the operator
	 * @param expressionAfterOperator
	 *            The expression after the operator
	 */
	public Operation(Expression expressionBeforeOperator, Expression expressionAfterOperator) {
		setExpressionBeforeOperator(expressionBeforeOperator);
		setExpressionAfterOperator(expressionAfterOperator);
	}

	/**
	 * Prototype constructor for an operation combining two expressions
	 * 
	 * @param expressionBeforeOperator
	 *            The expression before the operator
	 * @param expressionAfterOperator
	 *            The expression after the operator
	 */
	public Operation() {
		setExpressionBeforeOperator(new Number(0));
		setExpressionAfterOperator(new Number(0));
	}

	public void setExpressionBeforeOperator(Expression expr) {
		this.expressionBeforeOperator = expr;
	}

	public void setExpressionAfterOperator(Expression expr) {
		this.expressionAfterOperator = expr;
	}

	protected Expression getExpressionBeforeOperator() {
		return this.expressionBeforeOperator;
	}

	protected Expression getExpressionAfterOperator() {
		return this.expressionAfterOperator;
	}

	public static void loadAllOperators() {
		List<Class<?>> classes = ClassEnumerator.getClassesForPackage(ClassEnumerator.class.getPackage());
		for (Class<?> currentClass : classes) {
			if (isClassExtendingOperation(currentClass)) {
				interpretOperationClass((Class<Operation>) currentClass);
			}
		}
	}

	private static void interpretOperationClass(Class<Operation> operationClass) {
		String operationStringInClass = null;
		OperationImportance operationImportanceInClass = null;
		for (Field f : operationClass.getFields()) {
			try {
				switch (f.getName()) {
				case "operationString":
					if (f.get(null) != null)
						operationStringInClass = (String) f.get(null);
					break;
				case "operationImportance":
					if (f.get(null) != null)
						operationImportanceInClass = (OperationImportance) f.get(null);
					break;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.severe("The class " + operationClass.getName() + " could not be loaded correctly because the expected fields were not readable.");
			}
		}
		if (operationStringInClass != null && operationImportanceInClass != null) {
			if (availableOperations.isEmpty()) {
				availableOperations.add(new OperationInformation(operationClass, operationStringInClass, operationImportanceInClass));
			} else {
				// Cycle through the list of operation until the importance of
				// the current class is bigger than the importance of the next
				// operation information
				ListIterator<OperationInformation> importanceIterator = availableOperations.listIterator();
				while (importanceIterator.hasNext() && importanceIterator.next().getOperationImportance().biggerThan(operationImportanceInClass))
					;
				if (importanceIterator.previous().getOperationImportance().biggerThan(operationImportanceInClass)) {
					importanceIterator.next();
				}
				importanceIterator.add(new OperationInformation(operationClass, operationStringInClass, operationImportanceInClass));
			}
		} else {
			logger.warning("The class " + operationClass.getName() + " is in the expression.operations package " + "but does not contain the expected values.");
		}
	}

	/**
	 * Tests if the given class is in the operations package and extends
	 * Operation.
	 * 
	 * @param classToTest
	 *            The class which shall be tested
	 * @return True, if the class matches the searched criteria
	 */
	private static boolean isClassExtendingOperation(Class<?> classToTest) {
		return classToTest.getPackage().getName().contains("expression.operations") && classToTest.getSuperclass() == Operation.class;
	}

	public static OperationInformation getOperationInformationForOperator(String operationStringToLookFor) throws InvalidExpressionException {
		for (OperationInformation currentInformation : availableOperations) {
			if (operationStringToLookFor.equals(currentInformation.getOperationString()))
				return currentInformation;
		}
		throw new InvalidExpressionException("No such operation registered: " + operationStringToLookFor);
	}

	public enum OperationImportance {
		MOST_IMPORTANT(10), VERY_IMPORTANT(5), NORMALLY_IMPORTANT(0), NOT_VERY_IMPORTANT(-5), LEAST_IMPORTANT(-10);

		private int importanceValue;

		private OperationImportance(int importanceValue) {
			this.importanceValue = importanceValue;
		}

		public boolean biggerThan(OperationImportance other) {
			return importanceValue > other.importanceValue;
		}
	}

}
