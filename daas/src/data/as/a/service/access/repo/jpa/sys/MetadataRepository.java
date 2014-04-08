package data.as.a.service.access.repo.jpa.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import data.as.a.service.access.entity.jpa.sys.MetadataEntity;

public interface MetadataRepository extends
		JpaRepository<MetadataEntity, String> {

	MetadataEntity findByAppidAndModelNameAndVersion(String appid,
			String modelName, int version);

	List<MetadataEntity> findByAppid(String appid);

	List<MetadataEntity> findByAppidAndModelName(String appid, String modelName);
}
