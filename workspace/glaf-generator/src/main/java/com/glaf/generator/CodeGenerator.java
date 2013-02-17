package com.glaf.generator;

import java.util.Map;

import com.glaf.base.api.ClassDefinition;

public interface CodeGenerator {

	String process(ClassDefinition classDefinition, Map<String, Object> context);

}
