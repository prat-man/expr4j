/**
 * Copyright 2019 Pratanu Mandal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package tk.pratanumandal.expr4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tk.pratanumandal.expr4j.common.Expr4jConstants;
import tk.pratanumandal.expr4j.common.MathExtras;
import tk.pratanumandal.expr4j.exception.Expr4jException;
import tk.pratanumandal.expr4j.token.Function;
import tk.pratanumandal.expr4j.token.Operand;
import tk.pratanumandal.expr4j.token.Operator;
import tk.pratanumandal.expr4j.token.Operator.Properties;
import tk.pratanumandal.expr4j.token.Operator.Properties.Associativity;

/**
 * The <code>OperatorRepository</code> class is the repository of all operators, functions, variables, and constants in the environment.<br><br>
 * 
 * This class can be used to create user defined functions.
 * For example, we can create a function si(principal, rate, time) to calculate simple interest in the following manner:<br><br>
 * 
 * <pre>
 *	// using lambda expression
 * 	OperatorRepository.addFunction("si", 3, (ops) -&gt; {
 * 		return new Operand((ops[0].toDouble() * ops[1].toDouble() * ops[2].toDouble()) / 100.0);
 * 	});
 * 	
 * 	// using anonymous class
 * 	OperatorRepository.addFunction("si", 3,
 * 		new Function() {
 * 			&#64;Override
 * 			public Operand evaluate(Operand ... ops) {
 * 				return new Operand((ops[0].toDouble() * ops[1].toDouble() * ops[2].toDouble()) / 100.0);
 * 			}
 * 		}
 * 	);
 * </pre>
 * 
 * @author Pratanu Mandal
 * @since 0.0.2
 *
 */
public class OperatorRepository {
	
	/**
	 * Utility classes should not have public constructors.
	 */
	private OperatorRepository() {}

	/**
	 * A map of all operators, functions, variables, and constants supported.
	 */
	protected static final Map<String, Properties> OPERATORS;
	
	/**
	 * A list of all predefined operators, functions, variables, and constants.
	 */
	protected static final List<String> PREDEFINED_OPERATORS;
	
	
	static {
		
		// initialize static variables
		
		OPERATORS = new HashMap<>();
		
		PREDEFINED_OPERATORS = new ArrayList<>();
		
		
		
		// initialize predefined operators
		// priority is in increasing order from 0 to infinity
		
		// parenthesis - 0
		OPERATORS.put("(", new Properties(1, 0));
		OPERATORS.put(")", new Properties(1, 0));
		
		// no argument - 0
		OPERATORS.put(",", new Properties(0, 0));
		
		// binary - 1
		OPERATORS.put("+", new Properties(2, 1));
		OPERATORS.put("-", new Properties(2, 1));
		
		// binary - 2
		OPERATORS.put("*", new Properties(2, 2));
		OPERATORS.put("/", new Properties(2, 2));
		OPERATORS.put("%", new Properties(2, 2));
		
		// binary - 3
		OPERATORS.put("^", new Properties(2, 3));
		
		// unary - 4
		OPERATORS.put("uminus", new Properties(1, 4, Associativity.NO));
		OPERATORS.put("uplus", new Properties(1, 4, Associativity.NO));
		
		// functions - 4
		OPERATORS.put("abs", new Properties(1, 4, (operands) -> {return new Operand(Math.abs(operands[0].toDouble()));}));
		
		OPERATORS.put("sin", new Properties(1, 4, (operands) -> {return new Operand(Math.sin(operands[0].toDouble()));}));
		OPERATORS.put("cos", new Properties(1, 4, (operands) -> {return new Operand(Math.cos(operands[0].toDouble()));}));
		OPERATORS.put("tan", new Properties(1, 4, (operands) -> {return new Operand(Math.tan(operands[0].toDouble()));}));
		
		OPERATORS.put("asin", new Properties(1, 4, (operands) -> {return new Operand(Math.asin(operands[0].toDouble()));}));
		OPERATORS.put("acos", new Properties(1, 4, (operands) -> {return new Operand(Math.acos(operands[0].toDouble()));}));
		OPERATORS.put("atan", new Properties(1, 4, (operands) -> {return new Operand(Math.atan(operands[0].toDouble()));}));
		
		OPERATORS.put("sinh", new Properties(1, 4, (operands) -> {return new Operand(Math.sinh(operands[0].toDouble()));}));
		OPERATORS.put("cosh", new Properties(1, 4, (operands) -> {return new Operand(Math.cosh(operands[0].toDouble()));}));
		OPERATORS.put("tanh", new Properties(1, 4, (operands) -> {return new Operand(Math.tanh(operands[0].toDouble()));}));
		
		OPERATORS.put("asinh", new Properties(1, 4, (operands) -> {return new Operand(MathExtras.asinh(operands[0].toDouble()));}));
		OPERATORS.put("acosh", new Properties(1, 4, (operands) -> {return new Operand(MathExtras.acosh(operands[0].toDouble()));}));
		OPERATORS.put("atanh", new Properties(1, 4, (operands) -> {return new Operand(MathExtras.atanh(operands[0].toDouble()));}));
		
		OPERATORS.put("deg", new Properties(1, 4, (operands) -> {return new Operand(Math.toDegrees(operands[0].toDouble()));}));
		OPERATORS.put("rad", new Properties(1, 4, (operands) -> {return new Operand(Math.toRadians(operands[0].toDouble()));}));
		
		OPERATORS.put("round", new Properties(1, 4, (operands) -> {return new Operand(Math.round(operands[0].toDouble()));}));
		OPERATORS.put("floor", new Properties(1, 4, (operands) -> {return new Operand(Math.floor(operands[0].toDouble()));}));
		OPERATORS.put("ceil", new Properties(1, 4, (operands) -> {return new Operand(Math.ceil(operands[0].toDouble()));}));
		
		OPERATORS.put("ln", new Properties(1, 4, (operands) -> {return new Operand(Math.log(operands[0].toDouble()));}));
		OPERATORS.put("log10", new Properties(1, 4, (operands) -> {return new Operand(Math.log10(operands[0].toDouble()));}));
		OPERATORS.put("log", new Properties(2, 4, (operands) -> {return new Operand(MathExtras.log(operands[0].toDouble(), operands[1].toDouble()));}));
		
		OPERATORS.put("sqrt", new Properties(1, 4, (operands) -> {return new Operand(Math.sqrt(operands[0].toDouble()));}));
		OPERATORS.put("cbrt", new Properties(1, 4, (operands) -> {return new Operand(Math.cbrt(operands[0].toDouble()));}));
		
		OPERATORS.put("fact", new Properties(1, 4, (operands) -> {return new Operand(MathExtras.factorial(operands[0].toDouble() + 1));}));
		
		OPERATORS.put("max", new Properties(2, 4, (operands) -> {return new Operand(Math.max(operands[0].toDouble(), operands[1].toDouble()));}));
		OPERATORS.put("min", new Properties(2, 4, (operands) -> {return new Operand(Math.min(operands[0].toDouble(), operands[1].toDouble()));}));
		
		// variable argument functions - 4
		OPERATORS.put("mean", new Properties(-1, 4, (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	}));
		
		// zero argument - 5
		OPERATORS.put("rand", new Properties(0, 5, (operands) -> {return new Operand(Math.random());}));
		OPERATORS.put("pi", new Properties(0, 5,  (operands) -> {return new Operand(Math.PI);}));
		OPERATORS.put("e", new Properties(0, 5,  (operands) -> {return new Operand(Math.E);}));
		
		
		
		// create list of predefined operators
		
		Set<String> opSet = OPERATORS.keySet();
		
	    for (String op : opSet) {
	    	PREDEFINED_OPERATORS.add(op);
	    }
	    
	}
	
	/**
	 * Utility method to get the properties of an operator.
	 * 
	 * @param value The operator value as a string
	 * @return The properties of the operator
	 */
	public static Operator.Properties getOperatorProperties(String value) {
		return OPERATORS.get(value);
	}
	
	/**
	 * Utility method to determine if a string is a supported operator.
	 * 
	 * @param op The string to check
	 * @return true if the string is a supported operator, otherwise false
	 */
	public static boolean isOperator(String op) {
		return OPERATORS.containsKey(op);
	}
	
	/**
	 * Utility method to determine if a string is a supported function.
	 * 
	 * @param op The string to check
	 * @return true if the string is a supported function, otherwise false
	 */
	public static boolean isFunction(String op) {
		return OPERATORS.get(op) != null && OPERATORS.get(op).function != null;
	}
	
	/**
	 * Utility method to determine if a string is a supported variable or constant.
	 * 
	 * @param op The string to check
	 * @return true if the string is a supported variable or constant, otherwise false
	 */
	public static boolean isVariableOrConstant(String op) {
		return OPERATORS.get(op) != null && OPERATORS.get(op).params == 0;
	}
	
	/**
	 * Utility method to add user defined function to the environment.<br>
	 * Method names can consist of lowercase letters, uppercase letters, and digits. However, first character must not be a digit.
	 * 
	 * @param fName Function name - e.g. for function name 'myFunc', it will be called as myFunc(...)
	 * @param params Number of parameters
	 * @param function Function definition
	 */
	public static void addFunction(String fName, int params, Function function) {
		if (fName == null) throw new NullPointerException("Function name cannot be null.");
		if (!fName.matches("[a-zA-Z][a-zA-Z0-9]+")) throw new Expr4jException("Not a valid function name: " + fName + ".");
		if (function == null) throw new NullPointerException("Function cannot be null.");
		if (PREDEFINED_OPERATORS.contains(fName)) throw new Expr4jException("Cannot override predefined function: " + fName + ".");
		OPERATORS.put(fName, new Properties(params, 4, function));
	}
	
	/**
	 * Utility method to add user defined function with variable number of parameters to the environment.<br>
	 * Method names can consist of lowercase letters, uppercase letters, and digits. However, first character must not be a digit.
	 * 
	 * @param fName Function name - e.g. for function name 'myFunc', it will be called as myFunc(...)
	 * @param function Function definition
	 */
	public static void addFunction(String fName, Function function) {
		OperatorRepository.addFunction(fName, Expr4jConstants.VARIABLE_PARAMETERS, function);
	}
	
	/**
	 * Utility method to remove user defined function from the environment.<br>
	 * Method names can consist of lowercase letters, uppercase letters, and digits. However, first character must not be a digit.
	 * 
	 * @param fName Function name - e.g. for function name 'myFunc', it will be called as myFunc(...)
	 */
	public static void removeFunction(String fName) {
		if (fName == null) throw new NullPointerException("Function name cannot be null.");
		if (!fName.matches("[a-zA-Z0-9]+")) throw new Expr4jException("Not a valid function name: " + fName + ".");
		if (!isOperator(fName) || !isFunction(fName)) throw new Expr4jException("Function not found: " + fName + ".");
		if (PREDEFINED_OPERATORS.contains(fName)) throw new Expr4jException("Cannot remove predefined function: " + fName + ".");
		OPERATORS.remove(fName);
	}
	
	/**
	 * Utility method to get list of all operator names currently supported by the environment.<br>
	 * The returned list is sorted lexicographically by operator names.
	 * 
	 * @return List of operator names currently supported by the environment
	 */
	public static List<String> getOperatorList() {
		List<String> opList = new ArrayList<>();
		Set<String> opSet = OPERATORS.keySet();
	    for (String op : opSet) {
	    	opList.add(op);
	    }
	    Collections.sort(opList);
	    return opList;
	}
	
}
