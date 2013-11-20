package org.netflexity.api.orm;

import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.DatasourceMetadataFactory;

/**
 * @author Max Fedorov
 *
 * Test factory
 */
public class DatasourceMetadataFactoryTest {

	public static void main(String[] args) {
		DatasourceMetadata dmd = DatasourceMetadataFactory.getInstance("org/netflexity/dorm/test/dorm-sample.xml").getDatasourceMetadataByName("MY");
		MetadataTest.printDatasourceMetadataContents(dmd);
	}
}
