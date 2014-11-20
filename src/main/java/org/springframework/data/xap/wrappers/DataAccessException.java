package org.springframework.data.xap.wrappers;

/**
 * @author Anna_Babich
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(){
        super();
    }

    public DataAccessException(String message){
        super(message);
    }

    public DataAccessException(Throwable e){
        super(e);
    }

    public DataAccessException(Throwable e, String message){
        super(message, e);
    }
}
