package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class DNIFExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double maxOfNeastedif = 0.0;
			for (CtIf ifEl : element.getElements(new TypeFilter<CtIf>(CtIf.class))) {
				double numOfNeastedif = countNeastedIfs(ifEl);
				if (numOfNeastedif > maxOfNeastedif) {
					maxOfNeastedif = numOfNeastedif;
				}
			}
			Dataset.store(qualifiedName, new Measure(Metric.DNIF, maxOfNeastedif));
		}
	}

	private double countNeastedIfs(CtIf ifEl) {
		double greaterDepthOfNeasted = 0.0;
		if (ifEl.getElements(new TypeFilter<CtIf>(CtIf.class)).size() > 0) {
			for (CtIf children : ifEl.getThenStatement().getElements(new TypeFilter<CtIf>(CtIf.class))) {
				double value = countNeastedIfs(children) + 1.0;
				if(value > greaterDepthOfNeasted) {
					greaterDepthOfNeasted = value;
				}
			}
		} else {
			return 0;
		}
		return greaterDepthOfNeasted;
	}
}