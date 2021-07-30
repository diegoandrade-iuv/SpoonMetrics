package br.ufc.mdcc.spoonmetrics.util;

import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public class Util {

	public static boolean isValid(CtType<?> element) {
		return element != null && element.getQualifiedName() != null && (element.isClass() || element.isInterface())
				&& !element.isAnonymous() && !element.isLocalType() && !element.isShadow();
	}

	public static int getDepthOfInheritanceTree(CtTypeReference<?> type) {
		if (type.isShadow() || type.getSuperclass() == null) {
			return 0;
		} else {
			return 1 + getDepthOfInheritanceTree(type.getSuperclass());
		}
	}

	public static boolean inherits(CtTypeReference<?> type, CtTypeReference<?> parentCandidate) {
		if (!type.isShadow()) {
			CtTypeReference<?> parent = type.getSuperclass();
			if (parent != null) {
				String parentName = parent.getQualifiedName();
				String parentCandidateName = parentCandidate.getQualifiedName();
				if (parentName != null && parentCandidateName.equals(parentName)) {
					return true;
				} else {
					return inherits(parent, parentCandidate);
				}
			}
		}
		return false;
	}
}