package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class LoCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			int startLine = element.getPosition().getLine();
			int endLine = element.getPosition().getEndLine();
			double totalLoC = ((endLine - startLine) == 0) ? (1) : ((endLine - startLine) - 1);
			Dataset.store(qualifiedName, new Measure(Metric.LoC, totalLoC));
		}
	}
}
