package xap.model;


public interface ISpaceService<T> {

	public abstract T findByRowId(final String id);

	public abstract void updateSpace(final String rowId);

	public abstract void insertSpace(final String rowId);

	public abstract void removeSpace(final String rowId);

}
