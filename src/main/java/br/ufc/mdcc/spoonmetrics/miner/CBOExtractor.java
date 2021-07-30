package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtTypeReference;

public class CBOExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double couplingBetweenObjects = 0.0;
			for (CtTypeReference<?> referredType : element.getReferencedTypes()) {
				if (referredType != null && !referredType.isShadow()) {
					couplingBetweenObjects++;
				}
			}
			Dataset.store(qualifiedName, new Measure(Metric.CBO, couplingBetweenObjects));
		}
	}
}
