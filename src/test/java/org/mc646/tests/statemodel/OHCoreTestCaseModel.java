package org.mc646.tests.statemodel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.graphwalker.core.machine.ExecutionContext;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public abstract class OHCoreTestCaseModel extends ExecutionContext {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public void cleanH2InMemoryDb() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		List<Object[]> show_tables = entityManager.createNativeQuery("SHOW TABLES").getResultList();
		show_tables
				.stream()
				.map(result -> (String) result[0])
				.forEach(s -> truncateTable(s, entityManager));
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void truncateTable(String name, EntityManager entityManager) {
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE " + name).executeUpdate();
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}
}
