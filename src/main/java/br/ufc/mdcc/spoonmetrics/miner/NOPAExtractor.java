package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtFieldReference;

public class NOPAExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {
		String qualifiedName = element.getQualifiedName();
		double count = 0.0;
		for (CtFieldReference variable : element.getDeclaredFields()) {
			if(variable.getModifiers().contains(ModifierKind.PUBLIC)){
				count++;
			}
		}
		Dataset.store(qualifiedName, new Measure(Metric.NOPA, count));
	}
}