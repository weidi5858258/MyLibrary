public interface DBOperatedInterface{
	long insertBean(T t);
	boolean deleteBean(T t);
	long updateBean(ContentValues wheres, ContentValues values);
	T queryBean(T t);
	boolean isBeanExist(T t);
}