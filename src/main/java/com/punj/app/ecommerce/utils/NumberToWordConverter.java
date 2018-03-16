/**
 * 
 */
package com.punj.app.ecommerce.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author admin
 *
 */
public class NumberToWordConverter {

	public static void main(String[] args) {
		BigDecimal number = new BigDecimal("142845782.50");
		System.out.println("Please type a number(max upto 9 digits)");
		System.out.print("Number in words: " + convertBigDecimalToWords(number));
	}

	private static String numberToWord(int number) {
		// variable to hold string representation of number
		String words = "";
		String unitsArray[] = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen",
				"fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
		String tensArray[] = { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };

		if (number == 0) {
			return "zero";
		}
		// add minus before conversion if the number is less than 0
		if (number < 0) {
			// convert the number to a string
			String numberStr = "" + number;
			// remove minus before the number
			numberStr = numberStr.substring(1);
			// add minus before the number and convert the rest of number
			return "Negative " + numberToWord(Integer.parseInt(numberStr));
		}
		// check if number is divisible by 1 million
		if ((number / 10000000) > 0) {
			words += numberToWord(number / 10000000) + " crore ";
			number %= 10000000;
		}
		if ((number / 100000) > 0) {
			words += numberToWord(number / 100000) + " lac ";
			number %= 100000;
		}
		// check if number is divisible by 1 thousand
		if ((number / 1000) > 0) {
			words += numberToWord(number / 1000) + " thousand ";
			number %= 1000;
		}
		// check if number is divisible by 1 hundred
		if ((number / 100) > 0) {
			words += numberToWord(number / 100) + " hundred ";
			number %= 100;
		}

		if (number > 0) {
			// check if number is within teens
			if (number < 20) {
				// fetch the appropriate value from unit array
				words += unitsArray[number];
			} else {
				// fetch the appropriate value from tens array
				words += tensArray[number / 10];
				if ((number % 10) > 0) {
					words += "-" + unitsArray[number % 10];
				}
			}
		}

		return words;
	}

	public static String convertBigDecimalToWords(BigDecimal amount) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		String data = df.format(amount);
		
		String amounts[] = data.split("\\.");

		Integer beforeDecimal = Integer.parseInt(amounts[0]);
		Integer afterDecimal;
		if (amounts.length > 1) {
			if (amounts[1].length() == 1) {
				afterDecimal = Integer.parseInt(amounts[1]) * 10;
			} else {
				afterDecimal = Integer.parseInt(amounts[1]);
			}
		} else {
			afterDecimal = 0;
		}

		StringBuilder word = new StringBuilder();

		word.append(numberToWord(beforeDecimal));
		word.append(" rupees and ");
		word.append(numberToWord(afterDecimal));
		word.append(" paise only");

		return word.toString();

	}

}
