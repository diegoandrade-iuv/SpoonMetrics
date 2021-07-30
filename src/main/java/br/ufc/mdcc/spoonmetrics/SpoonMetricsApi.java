package br.ufc.mdcc.spoonmetrics;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufc.mdcc.spoonmetrics.model.Dataset;
import br.ufc.mdcc.spoonmetrics.util.Util;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public class SpoonMetricsApi {

	private static Map<String, String> superTypeMap = new HashMap<>();

	private static Map<String, Set<String>> typeDependencyMap = new HashMap<>();
	
	private static SpoonAPI spoon;

	public static void mine(String reportName, String path) {
		build(path);
		configure();
		process();
		report(reportName);
	}

	private static void build(String path) {
		spoon = new Launcher();
		spoon.addInputResource(path);
		spoon.getEnvironment().setComplianceLevel(9);
		spoon.buildModel();
		fillTypeDependencyMapping();
		fillSuperTypeMapping();
	}

	private static void configure() {
		addCKMiners();
		addOOMiners();
		addOtherMiners();
	}

	private static void addCKMiners() {
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NOCExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.CBOExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.DITExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.RFCExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.WMCExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.LCOMExtractor");
	}

	private static void addOOMiners() {
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.LoCExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.LCOM2Extractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.LCOM3Extractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.CeExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.CaExtractor");
	}
	
	private static void addOtherMiners() {
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NODAExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NOPAExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NOPRAExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NODMExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NOPMExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NOPRMExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.DNIFExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.DNFORExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NOECBExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NoseExtractor");
		spoon.addProcessor("br.ufc.mdcc.spoonmetrics.miner.NoreExtractor");
	}

	private static void process() {
		spoon.process();
	}
	
	private static void report(String reportName) {
		Dataset.generateCSVFile(reportName);
	}
	
	
	private static void fillTypeDependencyMapping() {
		for (CtType<?> type : getAllValidTypes()) {
			typeDependencyMap.put(type.getQualifiedName(), new HashSet<>());
			for (CtTypeReference<?> referredType : type.getReferencedTypes()) {
				if (referredType != null && !referredType.isShadow() && referredType.getQualifiedName() != null) {
					typeDependencyMap.get(type.getQualifiedName()).add(referredType.getQualifiedName());
				}
			}
		}
	}
	
	private static void fillSuperTypeMapping() {
		for (CtType<?> type : getAllValidTypes()) {
			CtTypeReference<?> parent = null;
			if (type.isClass()) {
				parent = type.getSuperclass();
			}
			if (parent != null && parent.getQualifiedName() != null) {
				superTypeMap.put(type.getQualifiedName(), parent.getQualifiedName());
			} else {
				superTypeMap.put(type.getQualifiedName(), "java.lang.Object");
			}
		}
	}

	private static Collection<CtType<?>> getAllValidTypes() {
		return spoon.getModel().getAllTypes().stream().filter(t -> Util.isValid(t)).collect(Collectors.<CtType<?>> toList());
	}
	
	public static Map<String, Set<String>> getTypeDependencyMap() {
		return typeDependencyMap;
	}
	
	public static Map<String, String> getSuperTypeMap() {
		return superTypeMap;
	}
}
