package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NoreExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numOfRaisedExceptions = 0.0;
			for (CtThrow thrownedEl : element.getElements(new TypeFilter<CtThrow>(CtThrow.class))) {
				if (thrownedEl != null) {
					numOfRaisedExceptions++;
				}
			}
			Dataset.store(qualifiedName, new Measure(Metric.NORE, numOfRaisedExceptions));
		}
	}

}
