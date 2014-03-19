package fabric.common.utils;

import org.junit.Before;
import org.junit.Test;
public class AccountSessionTest {

 
	@Test
	public void testUrl(){
		String filename = "likaihua.jpg";
		Long id = new Long(1);
		String urlpath = AppUtils.fetchScenePath(id, filename);
		System.out.println(urlpath);
		
		urlpath = AppUtils.fetchDesignPath(id, filename);
		System.out.println(urlpath);
		
		urlpath = AppUtils.fetchSchemePath(id, filename);
		System.out.println(urlpath);
	}
}
