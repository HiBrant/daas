package data.as.a.service.access.repo.jpa.sys;

import org.springframework.data.jpa.repository.JpaRepository;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;

public interface MetadataRepository extends
		JpaRepository<MetadataEntity, String> {

	MetadataEntity findByAppidAndModelNameAndVersion(String appid,
			String modelName, int version);
}
