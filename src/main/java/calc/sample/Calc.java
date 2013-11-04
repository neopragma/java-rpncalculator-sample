package calc.sample;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Sample RPN calculator.
 * @author Dave
 */
public class Calc {
	
	private Stack<BigDecimal> stack;
	private List<String> currentMessages;
	private static final String[] EMPTY_STRING_ARRAY = { "" };
	
	private static final String[] helpText = {
			"c clear calculator memory",
			"h display help text",
			"q quit",
			"r display current result",
			"+ add",
			"- subract",
			"* multiply",
			"/ divide",
			"% modulo", 
			"^ exponentiation" };
	
	public Calc() {
		stack = new Stack<BigDecimal>();
	}
	
	public void help() {
		setCurrentMessages(helpText);
	}
	
	public void clear() {
		stack.clear();
	}
	
	public void enter(String token) {
		if (token == null || token.length() == 0) {
			return;
		}
        if (token.length() == 1) {
	   	    switch (token.charAt(0)) {
	    	    case 'q': 
	    		    setCurrentMessages("Goodbye!");
	    		    return;
	    	    case 'r':
	    	        setCurrentMessages(result());
	    	    	return;
	    	    case 'c':
	    	        clear();
	    	    	return;
	    	    case 'h':
	    	        help();
	    	    	return;
	        }
	    }
		try {
			BigDecimal value = new BigDecimal(token);
			stack.push(value);
		} catch (NumberFormatException nonNumericValueWasEntered) {
			computeWith(token);
		}
	}
	
	public String result() {
		try {
			return stack.peek().toString();
		} catch (EmptyStackException e) {
			return "";
		}
	}
	
	private void computeWith(String token) {
		try {
			BigDecimal divisor = null;
			switch (token.charAt(0)) {
				case '+': 
					stack.push(stack.pop().add(stack.pop()));
					break;
				case '-':
					BigDecimal subtrahend = stack.pop();
					stack.push(stack.pop().subtract(subtrahend));
					break;
				case '*': 
					stack.push(stack.pop().multiply(stack.pop()));
					break;
				case '/':
					divisor = stack.pop();
					stack.push(stack.pop().divide(divisor, 9, RoundingMode.UP));
					break;
				case '%':
					divisor = stack.pop();
					stack.push(stack.pop().remainder(divisor));
					break;
				case '^':
					Double exponent = stack.pop().doubleValue();
					stack.push(new BigDecimal(Math.pow(stack.pop().doubleValue(), exponent)));
					break;
				default:
					setCurrentMessages("Unrecognized input '" + token + "'");
			}
		} catch (WrongNumberOfArgumentsException e) {
			setCurrentMessages("Wrong number of arguments for the '" + e.getMessage() + "' operator");
		}
	}

	public String getWelcomeMessage() {
	    return "Welcome to the Java RPN calculator";
	}
	
	public String[] getCurrentMessages() {
		return messageReady() ? currentMessages.toArray(new String[currentMessages.size()]) : EMPTY_STRING_ARRAY;
	}
	
	private boolean messageReady() {
		return (currentMessages != null && !currentMessages.isEmpty());
	}
	
	private void setCurrentMessages(String... messages) {
	    currentMessages = new ArrayList<String>();
        for (String message : messages) {
        	currentMessages.add(message);
        }
	}
}
