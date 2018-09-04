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
		String unitsArray[] = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
				"Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
		String tensArray[] = { "Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };

		if (number == 0) {
			return "Zero";
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
			words += numberToWord(number / 10000000) + " Crore ";
			number %= 10000000;
		}
		if ((number / 100000) > 0) {
			words += numberToWord(number / 100000) + " Lac ";
			number %= 100000;
		}
		// check if number is divisible by 1 thousand
		if ((number / 1000) > 0) {
			words += numberToWord(number / 1000) + " Thousand ";
			number %= 1000;
		}
		// check if number is divisible by 1 hundred
		if ((number / 100) > 0) {
			words += numberToWord(number / 100) + " Hundred ";
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
		word.append(" Rupees and ");
		word.append(numberToWord(afterDecimal));
		word.append(" Paise Only");

		return word.toString();

	}

}
