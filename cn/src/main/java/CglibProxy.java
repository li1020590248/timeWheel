import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private static Logger logger = LoggerFactory.getLogger(CglibProxy.class);

    private Object obj;

    public CglibProxy(Object obj){
        this.obj = obj;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        logger.info("代理开始");
        //methodProxy.invokeSuper(o,objects);
        methodProxy.invoke(obj,objects);
        logger.info("代理结束");
        return o;
    }
}
