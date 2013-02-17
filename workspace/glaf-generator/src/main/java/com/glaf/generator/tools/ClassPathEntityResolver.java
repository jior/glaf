package com.glaf.generator.tools;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

public class ClassPathEntityResolver implements org.xml.sax.EntityResolver {
	public org.xml.sax.InputSource resolveEntity(String publicId,
			String systemId) {
		Resource resource = new ClassPathResource(
				"/org/hibernate/hibernate-mapping-3.0.dtd");
		InputSource is = new InputSource();
		is.setPublicId(resource.getFilename());
		is.setSystemId(resource.getFilename());
		return is;
	}
}
