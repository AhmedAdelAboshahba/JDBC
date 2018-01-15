package parserForSQL;

import java.sql.Date;

public class DateType extends Type {

	@Override
	public boolean validate(String value) {
		// TODO Auto-generated method stub
		try{
			Date test =Date.valueOf(value);
		}catch(IllegalArgumentException e){
			System.out.println("This value contradicts the column type");
			return false;
		}
		return true;
	}

	@Override
	public boolean compareValue(String operator, String currentValue, String valueToBeChecked) {
		// TODO Auto-generated method stub
		Date test = null;

		try {
			test = Date.valueOf(valueToBeChecked);
		} catch (IllegalArgumentException e) {
			System.out.println("you can't compare this value");
			return false;
		}
		Date current = Date.valueOf(currentValue);
		if (operator.equals("=")) {
			if (current.compareTo(test) == 0) {
				return true;
			}
		} else if (operator.equals("<")) {
			if (current.compareTo(test) < 0) {
				return true;
			}
		} else if (operator.equals(">")) {
			if (current.compareTo(test) > 0) {
				return true;
			}
		}
		return false;
	}

}
