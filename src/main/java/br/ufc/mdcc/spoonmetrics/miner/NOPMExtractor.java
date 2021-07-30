package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.visitor.filter.TypeFilter;

public class NOPMExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {
		String qualifiedName = element.getQualifiedName();
		double count = 0.0;
		for (CtMethod method : element.getElements(new TypeFilter<CtMethod>(CtMethod.class))) {
			if(method.getModifiers().contains(ModifierKind.PUBLIC)){
				count++;
			}
		}
		Dataset.store(qualifiedName, new Measure(Metric.NOPM, count));
	}
}