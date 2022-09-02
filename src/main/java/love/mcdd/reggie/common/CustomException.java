package love.mcdd.reggie.common;

/**
 * 自定义 业务异常
 * @Author:Cola
 */
public class CustomException extends RuntimeException{

    public CustomException(String message){
        super(message);
    }
}
