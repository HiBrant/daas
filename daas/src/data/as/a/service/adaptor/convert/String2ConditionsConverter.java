package data.as.a.service.adaptor.convert;

import data.as.a.service.adaptor.condition.ConditionAnalysis;
import data.as.a.service.adaptor.condition.Conditions;
import data.as.a.service.adaptor.exception.IllegalQueryExpressionException;
import data.as.a.service.convert.Converter;

public class String2ConditionsConverter implements
		Converter<String, Conditions, IllegalQueryExpressionException> {

	@Override
	public Conditions convert(String expression) throws IllegalQueryExpressionException {
		
		ConditionAnalysis ca = new ConditionAnalysis();
		return ca.execute(expression);
	}

}
