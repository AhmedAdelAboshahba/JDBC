package parserForSQL;

public class FloatType extends Type {

	@Override
	public boolean validate(String value) {
		// TODO Auto-generated method stub
		try {
			java.lang.Float test = java.lang.Float.parseFloat(value);
		} catch (NumberFormatException e) {
			System.out.println("This value contradicts the column type");
			return false;
		}
		return true;
	}

	public boolean compareValue(String operator, String currentValue, String valueToBeChecked) {
		java.lang.Float test = null;
		java.lang.Float current = java.lang.Float.parseFloat(currentValue);
		try {
			test = java.lang.Float.parseFloat(valueToBeChecked);
		} catch (NumberFormatException e) {
			System.out.println("you can't compare this value");
			return false;
		}
		if (operator.equals("<")) {
			if (current < test) {
				return true;
			}
		} else if (operator.equals(">")) {
			if (current > test) {
				return true;
			}
		} else if (operator.equals("=")) {
			if (current.equals(test)) {
				return true;
			}
		}
		return false;
	}

}