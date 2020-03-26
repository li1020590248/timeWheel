import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UseProxy {

    private static Logger logger = LoggerFactory.getLogger(CglibProxy.class);
    public static void main(String[] args) {
        logger.debug("start invoker");
        CglibProxy cglibProxy = new CglibProxy(new Dao());
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallback(cglibProxy);
        Dao dao = (Dao)enhancer.create();
        dao.update();
        dao.select();

    }
}
