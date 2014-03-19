/**
 * @(#)GrantTableResource.java, 2013-7-7. 
 * 
 */
package fabric.server.web.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import fabric.common.db.BusinessEntityImpl;
import fabric.common.db.BusinessManager;
import fabric.common.db.Status;
import fabric.common.web.ArrayParameters;
import fabric.common.web.BaseVO;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.common.web.WebParameters;
import fabric.server.entity.Account;
import fabric.server.entity.GrantTable;
import fabric.server.entity.ProductType;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;
import fabric.server.manager.FlowerTypeManager;
import fabric.server.manager.GrantTableManager;
import fabric.server.manager.SchemeManager;
import fabric.server.manager.ShopManager;
import fabric.server.vo.GrantTableVO;
import fabric.server.vo.ListVO;

/**
 * @author likaihua
 */
@Controller
@Scope("prototype")
public class GrantTableResource extends
    AuthResource<GrantTable, GrantTableManager> {

    @Autowired
    private GrantTableManager gtManager;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private SchemeManager schemeManager;

    @Autowired
    private FlowerTypeManager ftManager;

    @Autowired
    private ShopManager shopManager;

    @Override
    protected GrantTableManager getManager() {
        return gtManager;
    }

    public BaseVO multipleGrant(WebParameters parameters) {
        String name = parameters.getString("Name");
        String accountA = parameters.getString("Authorizer");
        String accountB = parameters.getString("Authorized");
        String description = parameters.getString("Description");
        Long productType = parameters.getLong("Type");
        ArrayParameters productParameters = webParametersFactory
            .createArrayParameters(parameters, "IDList");

        /**
         * 客户端需要添加单个产品授权<_>
         */
        if (accountA == null || StringUtils.isEmpty(accountA)
            || accountB == null || StringUtils.isEmpty(accountB)
            || productParameters == null || productType == null
        //|| productID == null
        ) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        ProductType type = ProductType.valueOf(productType);
        if (type == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        /**
         * 获取甲方乙方
         */
        Account partA = accountManager.getAccountByName(null, accountA);
        Account partB = accountManager.getAccountByName(null, accountB);
        if (partA == null || partB == null) {
            return new ErrorVO(ErrorCode.ACCOUNT_RULE_ERROR);
        }

        if (partA.getId() == partB.getId()) {
            return new ErrorVO(ErrorCode.GRANTABLE_GRANTSELF);
        }
        /**
         * 验证知识产权 account-操作者
         */
        UserRule rule = account.getRule();
        if (rule == UserRule.SUPER_ADMIN
            || (rule == UserRule.DESIGNER && partA.getName().equals(
                account.getName()))) {
            //正确
        } else {
            return new ErrorVO(ErrorCode.GRANTABLE_REJECT);
        }

        Set<BusinessEntityImpl> products = new HashSet();
        if (type == ProductType.Scheme) {
            getListByProductType(productParameters, partA, products,
                schemeManager);
        } else if (type == ProductType.FlowerType) {
            getListByProductType(productParameters, partA, products, ftManager);
        } else {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
            //getListByProductType(productParameters, partA, products, ftManager);
        }
        gtManager.grantlist(account, partA, partB, products, type);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[Name={}, Authorizer={}, Authorized={}, Description={},Type={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), name,
                    accountA, accountB, description, type.name(), "BaseVO" });
        return new BaseVO();
    }

    public BaseVO multipleDelete(WebParameters parameters) {
        ArrayParameters productParameters = webParametersFactory
            .createArrayParameters(parameters, "IDList");

        if (productParameters == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        List<Long> ids = new ArrayList();
        for (int nLoop = 0; nLoop < productParameters.length(); ++nLoop) {
            ids.add(productParameters.getLong(nLoop));
        }
        gtManager.deleteByList(account, ids);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[IDList={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(),
                    productParameters, "ListVO" });
        return new BaseVO();
    }

    public BaseVO grantList(WebParameters parameters) {
        Long productType = parameters.getLong("Type");
        if (productType == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        ProductType type = ProductType.valueOf(productType);
        if (type == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        List<GrantTable> list = gtManager.getAllByPAAndTypeAndStatus(account,
            type, Status.Normal, Status.Disabled);
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (GrantTable item: list) {
            GrantTableVO grantTableVO = new GrantTableVO(item);
            result.add(grantTableVO);
        }
        return result;
    }

    /**
     * 获取所有产品并且验证所有的权限
     * 
     * @param <T>
     * @param <M>
     * @param parameter
     * @param partA
     * @param list
     * @param manager
     */
    protected <T extends BusinessEntityImpl, M extends BusinessManager<T>> void getListByProductType(
        ArrayParameters parameter, Account partA, Set<BusinessEntityImpl> list,
        M manager) {
        if (parameter == null) {
            return;
        }
        int length = parameter.length();
        for (int index = 0; index < length; ++index) {
            T entity = manager.getByIdUseOwner(partA, parameter.getLong(index));
            if (entity != null) {
                list.add(entity);
            } else {
                logger.info(
                    "method:{}, partA:{}, manager:{}, id:{}",
                    new Object[] { this.getClass().getSimpleName(),
                        partA.getName(), manager, parameter.getLong(index) });
            }
        }
    }

}
