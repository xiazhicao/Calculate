package Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;
import java.util.regex.Pattern;

public class Calculate
{

	private Stack<String> operator;
	private Stack<String> number;
	private final String REG_EXPR = "^[\\-][0-9]{1,}$|^[0-9]{1,}$";
	private final int precision = 15;

	public Calculate(Stack<String> operator, Stack<String> number)
	{
		this.operator = operator;
		this.number = number;
	}

	/**
	 * Calculate operations
	 * 
	 * @param symbol
	 * @param num1
	 * @param num2
	 * @return
	 */
	public String calculate(String symbol, String num1, String num2)
	{
		BigDecimal bdNum1 = new BigDecimal(num1);
		BigDecimal bdNum2 = new BigDecimal(num2);
		BigDecimal tempNum = new BigDecimal(0);
		switch (symbol)
		{
			case "+":
				return bdNum1.add(bdNum2).toString();
			case "-":
				return bdNum1.subtract(bdNum2).toString();
			case "*":
				return bdNum1.multiply(bdNum2).toString();
			case "/":
				return bdNum1.divide(bdNum2, precision, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toString();
			default:
				break;
		}
		return tempNum.toString();
	}

	/**
	 * push elements into stack and do calculation
	 * 
	 * @param expression
	 * @return
	 */
	public Stack<String> getOutput(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return null;
		}

		String num1 = null;
		String num2 = null;
		String oper = null;
		String output = null;

		Pattern pattern = Pattern.compile(REG_EXPR);

		if (pattern.matcher(expression).matches())
		{
			if (number.isEmpty())
			{
				number.add(expression);
			}
			else
			{
				if (!operator.isEmpty())
				{
					num2 = number.pop();
					num1 = number.pop();
					oper = operator.pop();
					output = calculate(oper, num1, num2);
					number.add(output);
				}
				else
				{
					number.add(expression);
				}
			}
		}
		else
		{
			operator.add(expression);
		}

		while (!operator.isEmpty())
		{
			num2 = number.pop();
			num1 = number.pop();
			oper = operator.pop();
			output = calculate(oper, num1, num2);
			number.add(output);
		}
		return number;
	}

	/**
	 * clear stack
	 * 
	 * @return
	 */
	public Stack<String> clear()
	{
		number.removeAllElements();
		return number;
	}

	/**
	 * do sqrt
	 * 
	 * @return
	 */
	public Stack<String> sqrt()
	{
		BigDecimal bdNum = new BigDecimal(number.pop());
		BigDecimal base = BigDecimal.valueOf(2.0);
		MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
		BigDecimal outNum = bdNum;
		int cnt = 0;
		// Get more precise result
		while (cnt < 100)
		{
			outNum = (outNum.add(bdNum.divide(outNum, mc))).divide(base, mc);
			cnt++;
		}
		outNum = outNum.setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		number.add(outNum.toString());
		return number;
	}
}
