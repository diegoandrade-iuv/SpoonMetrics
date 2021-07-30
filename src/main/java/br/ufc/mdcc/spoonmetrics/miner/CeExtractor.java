package br.ufc.mdcc.spoonmetrics.miner;

import java.util.Map;
import java.util.Set;

import br.ufc.mdcc.spoonmetrics.SpoonMetricsApi;
import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class CeExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double efferentCoupling = compute(qualifiedName);
			Dataset.store(qualifiedName, new Measure(Metric.Ce, efferentCoupling));
		}
	}
	
	public long compute(String typeQualifiedName) {
		Map<String, Set<String>> typeDependencyMap = SpoonMetricsApi.getTypeDependencyMap();
		if (typeDependencyMap.containsKey(typeQualifiedName)) {
			return typeDependencyMap.get(typeQualifiedName).size();
		} else {
			return 0;
		}
	}
}
