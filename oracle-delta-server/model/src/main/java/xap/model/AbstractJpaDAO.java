 
package xap.model;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class AbstractJpaDAO<T extends IDomainEntity<KeyType>, KeyType extends Serializable> {
	@PersistenceContext
	private EntityManager entityManager;

	protected abstract Class<?> getKlass();

	protected AbstractJpaDAO() {
	}

	public void persist(final T entity) {
		entityManager.persist(entity);
	}

	public void update(final T entity) {
		entityManager.merge(entity);
	}

	public void remove(final T entity) {
		entityManager.remove(entityManager.getReference(entity.getClass(),
				entity.getId()));
	}

	public Session getSession() {
		return (Session) entityManager.getDelegate();
	}

	@SuppressWarnings("unchecked")
	public T findByPrimaryKey(KeyType primaryKey) {
		return (T) entityManager.find(getKlass(), primaryKey);
	}

	protected void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}
