/**
 * @(#)PatternParameter.java, 2013-8-9. 
 * 
 */
package fabric.common.web;

/**
 *
 * @author likaihua
 *
 */
public class XmlParameter {
    private Long ID;

    private boolean main;

    /**
     * 
     */
    public XmlParameter() {
    }

    /**
     * @return the main
     */
    public boolean isMain() {
        return main;
    }

    /**
     * @param main
     *            the main to set
     */
    public void setMain(String main) {
        if("1".equals(main)){
            this.main = true;
        }else{
            this.main = false;
        }
    }

    /**
     * @return the iD
     */
    public Long getID() {
        return ID;
    }

    /**
     * @param iD
     *            the iD to set
     */
    public void setID(String iD) {
        ID = Long.valueOf(iD);
    }

}
