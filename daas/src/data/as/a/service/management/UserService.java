package data.as.a.service.management;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import data.as.a.service.access.entity.jpa.sys.UserEntity;
import data.as.a.service.access.repo.jpa.sys.UserRepository;
import data.as.a.service.metadata.config.MetadataAccessConfig;

public class UserService {

	public UserEntity register(String username, String password, String email) {
		UserEntity user = new UserEntity();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		UserRepository repo = ctx.getBean(UserRepository.class);
		if (repo.findByUsernameOrEmail(username, email) != null) {
			((ConfigurableApplicationContext) ctx).close();
			return null;
		}
		
		user = repo.save(user);
		((ConfigurableApplicationContext) ctx).close();
		return user;
	}
	
	public UserEntity login(String username, String password) {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		UserRepository repo = ctx.getBean(UserRepository.class);
		UserEntity user = repo.findByUsernameAndPassword(username, password);
		((ConfigurableApplicationContext) ctx).close();
		return user;
	}
	
	public void saveOne(UserEntity user) {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				MetadataAccessConfig.class);
		UserRepository repo = ctx.getBean(UserRepository.class);
		repo.save(user);
		((ConfigurableApplicationContext) ctx).close();
	}
}
