package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class DNFORExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double maxOfNeastedFor = 0.0;
			for (CtFor forEl : element.getElements(new TypeFilter<CtFor>(CtFor.class))) {
				double numOfNeastedFor = countNeastedFors(forEl, 1);
				if (numOfNeastedFor > maxOfNeastedFor) {
					maxOfNeastedFor = numOfNeastedFor;
				}
			}
			Dataset.store(qualifiedName, new Measure(Metric.DNFOR, maxOfNeastedFor));
		}
	}

	private int countNeastedFors(CtFor forEl, int num) {
		for (CtFor children : forEl.getBody().getElements(new TypeFilter<CtFor>(CtFor.class))) {
			countNeastedFors(children, num++);
		}
		return num++;
	}
}
