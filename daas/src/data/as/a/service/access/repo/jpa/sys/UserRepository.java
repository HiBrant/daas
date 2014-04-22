package data.as.a.service.access.repo.jpa.sys;

import org.springframework.data.jpa.repository.JpaRepository;

import data.as.a.service.access.entity.jpa.sys.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	UserEntity findByUsernameOrEmail(String username, String email);
	UserEntity findByUsernameAndPassword(String username, String password);
}
