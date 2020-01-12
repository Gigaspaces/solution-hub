package xap.model;

import java.io.Serializable;

public interface IDomainEntity<T>  extends Serializable{
	public T getId();

	public void setId(T id);
}