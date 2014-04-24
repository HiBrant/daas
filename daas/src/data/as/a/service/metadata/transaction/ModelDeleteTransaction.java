package data.as.a.service.metadata.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;
import data.as.a.service.access.repo.jpa.sys.MetadataRepository;
import data.as.a.service.exception.UserException;
import data.as.a.service.exception.metadata.NoModelReferToThisIDException;
import data.as.a.service.util.DateFormatter;

@Service
public class ModelDeleteTransaction {

	@Autowired
	private MetadataRepository repo;

	@Transactional
	public void execute(String modelId) throws UserException {
		MetadataEntity meta = repo.findOne(modelId);
		if (meta == null || meta.isDiscard()) {
			throw new NoModelReferToThisIDException(modelId);
		}
		
		meta.setDiscard(true);
		meta.setLastModifyTime(DateFormatter.now());
		repo.save(meta);
	}
}
