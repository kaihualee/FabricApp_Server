import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

/**
 * @(#)XmlBean.java, 2013-7-24. 
 * 
 */

/**
 * @author likaihua
 */
public class XmlBean {

    public static void main(String[] args) {

        @SuppressWarnings("deprecation")
        BeanFactory factory = new XmlBeanFactory(new FileSystemResource(
            "C:/beans.xml"));
        //MyBean myBean = (MyBean) factory.getBean("myBean");
        
        
        ApplicationContext context = new FileSystemXmlApplicationContext(
            "C:/fii.xml");

        context = new ClassPathXmlApplicationContext("foo.xml");
        
    }
    
    
    
}
class Stage{
    private Stage() {}
    
    private static class StageSingletonHolder{
        static Stage instance = new Stage();
    }
    
    public static Stage getInstance() {
        return StageSingletonHolder.instance;
    }
}


class Instrumentlist implements InitializingBean, DisposableBean{
    /**
     * 定义销毁方法
     */
    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub
        
    }
    /**
     * 定义初始化方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}

class RefreshListener implements ApplicationListener{
    @Override
    public void onApplicationEvent(ApplicationEvent arg0) {
        //
    }
}
