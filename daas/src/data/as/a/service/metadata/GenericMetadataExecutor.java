package data.as.a.service.metadata;

import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;

public interface GenericMetadataExecutor<P, R> {

	R execute(P param) throws UserException, SystemException;
}
