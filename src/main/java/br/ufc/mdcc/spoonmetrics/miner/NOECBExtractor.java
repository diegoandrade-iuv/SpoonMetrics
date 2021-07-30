package br.ufc.mdcc.spoonmetrics.miner;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.model.Measure;
import br.ufc.mdcc.spoonmetrics.model.Metric;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class NOECBExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			double numOfEmptyCatchBlocks = 0.0;
			for (CtCatch catchBl : element.getElements(new TypeFilter<CtCatch>(CtCatch.class))) {
				if (catchBl != null) {
					if (catchBl.getBody().getStatements().size() == 0) {
						numOfEmptyCatchBlocks++;
					} else {
						boolean hasNoCommentSta = false;
						for (CtStatement statement : catchBl.getBody().getStatements()) {
							if (!(statement instanceof CtComment)) {
								hasNoCommentSta = true;
							}
						}
						if (!hasNoCommentSta) {
							numOfEmptyCatchBlocks++;
						}
					}
				}

			}
			Dataset.store(qualifiedName, new Measure(Metric.NOECB, numOfEmptyCatchBlocks));
		}
	}
}