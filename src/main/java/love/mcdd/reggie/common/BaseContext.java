package love.mcdd.reggie.common;

/**
 * 基于 ThreadLocal 封装工具类 用户保存和获取当前登录用户的 id
 * @Author:Cola
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal= new ThreadLocal<>();

    /**
     * 设置用户 id
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取用户 id
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
