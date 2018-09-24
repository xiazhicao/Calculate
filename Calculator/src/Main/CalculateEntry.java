package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import Service.Calculate;

public class CalculateEntry
{

  public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
		CalculateEntry m = new CalculateEntry();
		m.doStart();
	}
	
  private final String REG_EXPR = "^[\\-][0-9]{1,}$|^[0-9]{1,}$";
  private final String COLON = " , ";
  
	/**
	 * Method to retrieve the data from console and call method to do calculation.
	 * @throws IOException
	 */
	public void doStart() throws IOException
	{
		Calculate cal = null;
		Stack<String> operator = null;
		Stack<String> number = null;
		List<Stack<String>> historyList = null;
		String expr = null;
		String input = null;
		int index = 0;
		String[] expression = null;
		String output = null;
		System.out.println("Welcome to my calculator (Type e/exit to leave calculator)");
		
		try (Reader in = new InputStreamReader(System.in);
		    BufferedReader reader = new BufferedReader(in))
		{
			operator = new Stack<String>();
			number = new Stack<String>();
			historyList = new ArrayList<>();

			while (true)
			{
				index = 0;
				System.out.print("> ");
				expr = reader.readLine();
				if (null == expr || "e".equalsIgnoreCase(expr)
				    || "exit".equalsIgnoreCase(expr))
				{
					break;
				}
				else if (expr.isEmpty())
				{
					continue;
				}
				expression = expr.split(" ");
				for (String exp : expression)
				{
					index++;
					cal = new Calculate(operator, number);
					if (exp.contains("CLEAR") || exp.contains("clear"))
					{
						number = cal.clear();
						historyList.clear();
					}
					else if (exp.contains("SQRT") || exp.contains("sqrt"))
					{
						number = cal.sqrt();
						historyList.add(number);
					}
					else if (exp.contains("UNDO") || exp.contains("undo"))
					{
						historyList.remove(historyList.size() - 1);
						number = (Stack<String>) historyList.get(historyList.size() - 1).clone();
					}
					else
					{
						input = exp;
						number = cal.getOutput(exp);
						output = number.lastElement();
						historyList.add((Stack<String>) number.clone());
					}
				}
				System.out.println("stack : " + Arrays.toString(number.toArray()));
			}
		}
		catch (ArithmeticException ex)
		{
			System.out.println("Arithmetic Exception£º" + ex.getMessage());
		}
		catch (RuntimeException ex)
		{
			System.out.println("operator " + input + " (position: " + (index * 2 - 1)
			    + ") : insucient parameters");
			System.out.println("stack: " + output);
			System.out.println("the " + getElements(expression, index)
			    + "were not pushed on to the stack due to the previous error");
		}
	}

	/**
	 * Get the left elements which cannot push into stack
	 * @param expression
	 * @param index
	 * @return
	 */
	public String getElements(String[] expression, int index)
	{
		StringBuilder sb = new StringBuilder();
		while (index < expression.length)
		{
			Pattern pattern = Pattern.compile(REG_EXPR);
			if (pattern.matcher(expression[index]).matches())
			{
				sb.append(expression[index]).append(COLON);
			}
			index++;
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

}
