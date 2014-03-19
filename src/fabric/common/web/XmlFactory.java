/**
 * @(#)XmlFactory.java, 2013-8-8. 
 * 
 */
package fabric.common.web;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author likaihua
 */
@Component
@Scope("singleton")
public class XmlFactory {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public XmlHelper create(String path, XmlType type) {
        if (path == null || path.isEmpty() || type == null) {
            return null;
        }
        File file = new File(path);
        XmlHelper xmlHelper;
        try {
            xmlHelper = new ProductXml(file, type);
        } catch (Exception e) {
            logger.info("Create xmlHelper fail: path:{}, type{}", new Object[] {
                path, type });
            return null;
        }
        return xmlHelper;
    }

    
    public static void main(String[] args) throws Exception {
        
        XmlFactory factory = new XmlFactory();
        String path = "E:\\scheme.xml";
        XmlType type = XmlType.SCHEME;
        XmlHelper XmlHelper =factory.create(path, type);
        
        

        String id = XmlHelper.getID();
        System.out.println(id);
        id = "XmlFactory1234562225";
        XmlHelper.setID(id);
        String localpath = XmlHelper.getLocalPath();
        System.out.println(localpath);
        localpath = "XmlFactoryNetEase";
        XmlHelper.setLocalPath(localpath);
        
        List<Long> list = XmlHelper.getLongListByNodeName("PatternID2Path", "PatternPath", "ID");
        
        for(int nLoop = 0; nLoop < list.size(); ++nLoop){
            System.out.println(list.get(nLoop));
        }
        XmlHelper.save("E:\\", "kk1025.xml");
        XmlHelper.save("E:\\", "scheme.xml");
        System.out.println(path + File.separatorChar);
    }

}
