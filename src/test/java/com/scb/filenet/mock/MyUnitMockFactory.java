package com.scb.filenet.mock;

import static org.mockito.Mockito.spy;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scb.cs.client.CS;
import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.document.service.mock.impl.MockFilenetDocumentServiceImpl;

/**
 * For File-net services we should use mockito services to verify service calls and mock more complex logic.
 */
public class MyUnitMockFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyUnitMockFactory.class);

	private static Map<Class<?>, Object> services = new HashMap<Class<?>, Object>();

	public MyUnitMockFactory() {
		if (services.isEmpty()) {
			setDefaultServices();
		}
	}

	public static void setDefaultServices() {
		LOGGER.debug("Adding Mock Services");
		addMock(FilenetDocumentService.class, spy(new MockFilenetDocumentServiceImpl()));
		addMock(CS.class, spy(new CS("csURL")));
	}

	public static <S, T extends S> void addMock(final Class<S> interfaceClass, final T implementation) {
		services.put(interfaceClass, implementation);
	}

	@SuppressWarnings("unchecked")
	public <T> T lookup(final Class<T> clazz) {
		LOGGER.debug("looking up: " + clazz.getName());
		if (services.containsKey(clazz)) {
			LOGGER.debug("returning Mockito Mock Service");
			return (T) services.get(clazz);
		}
		throw new UnsupportedOperationException("Mock not implemented yet");
	}

}
