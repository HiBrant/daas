package data.as.a.service.access.repo.jpa.sys;

import org.springframework.data.jpa.repository.JpaRepository;

import data.as.a.service.access.entity.jpa.sys.AppEntity;

public interface AppRepository extends JpaRepository<AppEntity, String> {

	AppEntity findByAppNameAndUserId(String appName, String userId);
}
