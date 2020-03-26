import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dao {
    private static Logger logger = LoggerFactory.getLogger(Dao.class);
    public void update() {
        logger.info("PeopleDao.update()");
        select();
    }

    public void select() {
        logger.info("PeopleDao.select()");
    }
}
