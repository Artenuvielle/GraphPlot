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
	 * Prototype constructor for an operation combining two undefined
	 * expressions (two instances of the number 0 are used instead)
	 */
	public Operation() {
		setExpressionBeforeOperator(new Number(0));
		setExpressionAfterOperator(new Number(0));
	}

	/**
	 * Sets the expression before the operator.
	 * 
	 * @param expr
	 *            Expression to set
	 */
	public void setExpressionBeforeOperator(Expression expr) {
		this.expressionBeforeOperator = expr;
	}

	/**
	 * Sets the expression after the operator.
	 * 
	 * @param expr
	 *            Expression to set
	 */
	public void setExpressionAfterOperator(Expression expr) {
		this.expressionAfterOperator = expr;
	}

	/**
	 * Gets the expression before the operator.
	 * 
	 * @return Expression before operator
	 */
	protected Expression getExpressionBeforeOperator() {
		return this.expressionBeforeOperator;
	}

	/**
	 * Gets the expression after the operator.
	 * 
	 * @return Expression after operator
	 */
	protected Expression getExpressionAfterOperator() {
		return this.expressionAfterOperator;
	}

	/**
	 * This method should be called once at the startup of the project to load
	 * all classes extending Operation and making them accessible through the
	 * static list Operation.availableOperations.
	 */
	public static void loadAllOperators() {
		List<Class<?>> classes = ClassEnumerator.getClassesForPackage(ClassEnumerator.class.getPackage());
		for (Class<?> currentClass : classes) {
			if (isClassExtendingOperation(currentClass)) {
				interpretOperationClass((Class<Operation>) currentClass);
			}
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

	/**
	 * Performs a check if the class has all the needed static fields and loads
	 * its information.
	 * 
	 * @param operationClass
	 *            The class extending Operation to load
	 */
	private static void interpretOperationClass(Class<Operation> operationClass) {
		OperationInformation operationInformation = null;
		for (Field f : operationClass.getFields()) {
			try {
				if (f.getName().equals("operationInformation")) {
					if (f.get(null) != null) {
						operationInformation = (OperationInformation) f.get(null);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.severe("The class " + operationClass.getName() + " could not be loaded correctly because the expected operationInformation field was not readable.");
			}
		}
		if (operationInformation != null) {
			if (availableOperations.isEmpty()) {
				availableOperations.add(operationInformation);
			} else {
				// Cycle through the list of operation until the importance of
				// the current class is bigger than the importance of the next
				// operation information
				ListIterator<OperationInformation> importanceIterator = availableOperations.listIterator();
				while (importanceIterator.hasNext() && importanceIterator.next().getOperationImportance().biggerThan(operationInformation.getOperationImportance()))
					;
				if (importanceIterator.previous().getOperationImportance().biggerThan(operationInformation.getOperationImportance())) {
					importanceIterator.next();
				}
				importanceIterator.add(operationInformation);
			}
		} else {
			logger.warning("The class " + operationClass.getName() + " is in the expression.operations package " + "but does not contain the expected values.");
		}
	}

	/**
	 * Searches the List of all operation information for the one which holds a
	 * given operator string.
	 * 
	 * @param operationStringToLookFor
	 *            The operator string to search for.
	 * @return The operation information containing the searched operator string
	 * @throws InvalidExpressionException
	 *             is thrown if an operator string is searched which was not
	 *             previously registered.
	 */
	public static OperationInformation getOperationInformationForOperator(String operationStringToLookFor) throws InvalidExpressionException {
		for (OperationInformation currentInformation : availableOperations) {
			if (operationStringToLookFor.equals(currentInformation.getOperationString()))
				return currentInformation;
		}
		throw new InvalidExpressionException("No such operation registered: " + operationStringToLookFor);
	}

	/**
	 * This enumeration is used to give operators the possibility to be
	 * calculated before others by having a higher importance. This is useful
	 * for rules like
	 * "subtraction and addition after multiplication or division".
	 * 
	 * @author Sophie Eckenstaler, René Martin
	 * @version 1.0
	 */
	public enum OperationImportance {

		MOST_IMPORTANT(10), VERY_IMPORTANT(5), A_BIT_IMPORTANT(1), NORMALLY_IMPORTANT(0), NOT_VERY_IMPORTANT(-5), LEAST_IMPORTANT(-10);

		private int importanceValue;

		/**
		 * Instantiates an importance object with a given importance value.
		 * 
		 * @param importanceValue
		 *            The importance value. the higher the value, the earlier is
		 *            is calculated.
		 */
		private OperationImportance(int importanceValue) {
			this.importanceValue = importanceValue;
		}

		/**
		 * Compares the importance value of this object to the one of another
		 * object.
		 * 
		 * @param other
		 *            The other operations importance value
		 * @return True if the importance of this operation is higher.
		 */
		public boolean biggerThan(OperationImportance other) {
			return importanceValue > other.importanceValue;
		}

	}

}
