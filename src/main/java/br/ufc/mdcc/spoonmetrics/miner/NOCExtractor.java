package br.ufc.mdcc.spoonmetrics.miner;

import java.util.Map;

import br.ufc.mdcc.spoonmetrics.SpoonMetricsApi;
import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class NOCExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numberOfChildren = compute(qualifiedName);
			Dataset.store(qualifiedName, new Measure(Metric.NOC, numberOfChildren));
		}
	}
	
	public long compute(String typeQualifiedName) {
		Map<String, String> superTypeMap = SpoonMetricsApi.getSuperTypeMap();
		return superTypeMap.values().stream().filter(set -> set.contains(typeQualifiedName)).count();
	}
}
