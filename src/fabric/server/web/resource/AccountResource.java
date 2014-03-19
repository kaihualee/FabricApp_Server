package fabric.server.web.resource;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import fabric.common.db.Status;
import fabric.common.web.BaseVO;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.common.web.WebParameters;
import fabric.server.entity.Account;
import fabric.server.entity.ClientPower;
import fabric.server.entity.Shop;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;
import fabric.server.manager.AccountSessionManager;
import fabric.server.manager.ShopManager;
import fabric.server.vo.AccountVO;
import fabric.server.vo.ListVO;
import fabric.server.vo.MapVO;
import fabric.server.vo.ShopVO;

/**
 * @author likaihua
 */
@Controller
@Scope("prototype")
public class AccountResource extends AuthResource<Account, AccountManager> {

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private ShopManager shopManager;

    @Override
    protected AccountManager getManager() {
        return accountManager;
    }

    /**
     * 用户名查重
     * 
     * @param parameters
     * @return
     */
    public BaseVO check(WebParameters parameters) {
        String userName = parameters.getString("UserName");
        if(userName == null || userName.isEmpty()){
            return new ErrorVO(ErrorCode.ACCOUNT_CHECKNAME_NULL);
        }
        Account accountTmp = accountManager.getAccountByName(null, userName, null);
        MapVO result = new MapVO("Result", accountTmp != null);
        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[UserName={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), userName,
                    accountTmp != null });
        return result;
    }

    /**
     * 昵称查重
     * 
     * @param parameters
     * @return
     */
    public BaseVO checkNickName(WebParameters parameters) {
        String nickname = parameters.getString("NickName");
        if (nickname == null || StringUtils.isEmpty(nickname)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        boolean isExisted = accountManager.checkNickname(null, account,
            nickname);
        MapVO result = new MapVO("Result", !isExisted);
        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[NickName={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), nickname,
                    !isExisted });
        return result;

    }

    /**
     * 用户登陆
     * 
     * @param parameters
     * @return
     */
    public BaseVO login(WebParameters parameters) {
        String powerStr = parameters.getString("Power");
        String userName = parameters.getString("UserName");
        String password = parameters.getString("Password");

        logger.info("Login user={}, password={}, power={}", new Object[] {
            userName, password, powerStr });

        if (userName == null || StringUtils.isEmpty(userName)
            || password == null || StringUtils.isEmpty(password)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        ClientPower power = null;
        if (powerStr != null && !StringUtils.isEmpty(powerStr)) {
            try {
                power = ClientPower.valueOf(Integer.valueOf(powerStr));
            } catch (Exception e) {
                logger.error("Value of power error, str=" + powerStr, e);
                return new ErrorVO(ErrorCode.ACCOUNT_RULE_ERROR);
            }
        } else {
            return new ErrorVO(ErrorCode.ACCOUNT_RULE_ERROR);
        }

        Account account = accountManager.getAccountByName(null, userName);

        if (account == null) {
            return new ErrorVO(ErrorCode.ACCOUNT_NO_EXIST);
        }

        /**
         * 检查账户状态有效性
         */
        Status status = account.getStatus();
        if (status == Status.Normal) {
            //正常状态
        } else if (status == Status.Disabled) {
            return new ErrorVO(ErrorCode.ACCOUNT_DISABLE);
        } else {
            return new ErrorVO(ErrorCode.ACCOUNT_NO_EXIST);
        }

        /**
         * 验证账户
         */
        boolean result = accountManager
            .verifyAccount(account.getId(), password);

        /**
         * 验证账户和登陆界面
         */
        if (result) {
            if (power.checkRule(account.getRule())) {
                String ip = getRequest().getClientInfo().getAddress();
                accountSession = sessionPool.insert(account, ip, power);

                logger
                    .info(
                        "Account:{}, method:{}, execute:{}, parameters:[powerStr={}, userName={}, password={}], Result:{}",
                        new Object[] { account.getName(),
                            this.getClass().getSimpleName(), getAction(),
                            powerStr, userName, password,
                            accountSession.getSid() });

                return new MapVO("sid", accountSession.getSid());
            } else {
                return new ErrorVO(ErrorCode.ACCOUNT_RULE_ERROR);
            }
        } else {
            return new ErrorVO(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
    }

    /**
     * 注销
     * 
     * @return
     */
    public BaseVO logout() {
        return logout("");
    }

    public BaseVO logout(WebParameters parameters) {
        return logout("");
    }

    private BaseVO logout(String name) {
        if (accountSession == null) {
            logger.info("Logout require valid sid");
        } else {
            Account account = accountSession.getAccount();
            logger.info("Logout sid ={} user={}, password={}, power={}",
                new Object[] { accountSession.getSid(), account.getName(),
                    account.getPassword(), account.getRule().getName() });
            sessionPool.remove(accountSession);
        }
        return new BaseVO();
    }

    /**
     * 更改密码
     * 
     * @param parameters
     * @return
     */
    public BaseVO alterPWD(WebParameters parameters) {
        String oldPassword = parameters.getString("OldPWD");
        String newPassword = parameters.getString("NewPWD");
        if (oldPassword == null || newPassword == null
            || StringUtils.isEmpty(newPassword)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        if (oldPassword == null || StringUtils.isEmpty(oldPassword)) {
            if (account.getRule() != UserRule.SUPER_ADMIN) {
                return new ErrorVO(ErrorCode.NOPERMISSIONS);
            }
        } else {
            if (!oldPassword.equals(account.getPassword())) {
                return new ErrorVO(ErrorCode.ACCOUNT_PASSWORD_ERROR);
            }

        }
        account.setPassword(newPassword);
        accountManager.update(account);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[oldPassword={}, newPassword={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), oldPassword,
                    newPassword, "true" });
        return new BaseVO();

    }

    /**
     * 创建账户
     * 
     * @param parameters
     * @return
     */
    public BaseVO create(WebParameters parameters) {
        String userName = parameters.getString("UserName");
        String realName = parameters.getString("RealName");
        String password = parameters.getString("Password");
        String nickName = parameters.getString("NickName");
        String info = parameters.getString("Info");
        String powerStr = parameters.getString("Power");

        Account accountTmp = accountManager.getAccountByName(null, userName,
            null);
        if (accountTmp != null) {
            return new ErrorVO(ErrorCode.ACCOUNT_NAME_IS_EXISTED);
        }

        /**
         * 验证数据完整性
         */
        if (userName == null || StringUtils.isEmpty(userName)
            || password == null || StringUtils.isEmpty(password)
            || realName == null || StringUtils.isEmpty(realName)
            || powerStr == null || StringUtils.isEmpty(powerStr)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        Integer power = Integer.valueOf(powerStr);
        if (power == null)
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        UserRule rule = UserRule.valueOf(power);

        if (rule == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        Account account = new Account();
        Shop shop = new Shop();
        if (rule == UserRule.BUSINESS) {
            String shopName = parameters.getString("ShopName");
            //商家名不允许重复
            if (shopManager.getByName(shopName) != null) {
                return new ErrorVO(ErrorCode.SHOP_NAME_EXIST);
            }
            shop.setName(shopName);
            shop.setAddress("atexco");

            account.setShop(shop);
        }
        account.setName(userName);
        account.setRealname(realName);
        account.setNickname(nickName);
        account.setPassword(password);
        account.setInfo(info);
        account.setRule(rule);
        accountManager.save(account);

        MapVO mapVO = new MapVO();
        mapVO.put("ID", account.getId());
        if (rule == UserRule.BUSINESS) {
            mapVO.put("ShopID", shop.getId());
        }

        logger
            .info(
                "Account:{},method:{}, execute:{}, parameters:[userName={}, realName={}, password={},nickName={},powerStr={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), userName,
                    realName, password, nickName, powerStr, account.getId() });
        return mapVO;

    }

    /**
     * 修改个人信息
     * 
     * @param parameters
     * @return
     */
    public BaseVO modify(WebParameters parameters) {
        String name = parameters.getString("UserName");
        String realname = parameters.getString("RealName");
        String nickname = parameters.getString("NickName");
        Account account = accountManager.getAccountByName(null, name);
        if (account == null) {
            return new ErrorVO(ErrorCode.ACCOUNT_NO_EXIST);
        }
        account.setNickname(nickname);
        account.setRealname(realname);
        accountManager.update(account);

        logger
            .info(
                "Account:{},method:{}, execute:{}, parameters:[name={}, realname={}, nickname={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), name,
                    realname, nickname, "BaseVO" });
        return new BaseVO();
    }

    /**
     * 重置密码
     * 
     * @param parameters
     * @return
     */
    public BaseVO accountReset(WebParameters parameters) {
        String name = parameters.getString("UserName");
        String password = parameters.getString("Password");

        if (name == null || StringUtils.isEmpty(name)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        Account account = accountManager.getAccountByName(null, name);
        if (account == null) {
            return new ErrorVO(ErrorCode.ACCOUNT_NO_EXIST);
        }
        account.setPassword(password);
        accountManager.update(account);

        logger
            .info(
                "Account:{},method:{}, execute:{}, parameters:[name={}, password={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), name,
                    password, "BaseVO" });

        return new BaseVO();

    }

    /**
     * 根据用户名查找账户
     * 
     * @param parameters
     * @return
     */
    public BaseVO findByUserName(WebParameters parameters) {
        String name = parameters.getString("UserName");

        if (name == null || StringUtils.isEmpty(name)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        Account account = accountManager.getAccountByName(null, name);
        if (account == null) {
            return new ErrorVO(ErrorCode.ACCOUNT_NO_EXIST);
        }

        AccountVO accountVO = new AccountVO(account);

        logger
            .info(
                "Account:{},method:{}, execute:{}, parameters:[name={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), name,
                    "BaseVO" });
        return accountVO;
    }

    /**
     * 删除账户
     * 
     * @param parameters
     * @return
     */
    public BaseVO accountDisable(WebParameters parameters) {
        String userName = parameters.getString("UserName");
        Account account = accountManager.getAccountByName(null, userName);
        if (account == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        accountManager.delete(account);

        logger
            .info(
                "Account:{},method:{} execute:{}, parameters:[UserName={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), userName,
                    "BaseVO" });
        return new BaseVO();
    }

    public BaseVO list(WebParameters parameters) {
        String powerStr = parameters.getString("Power");
        if (powerStr == null || StringUtils.isEmpty(powerStr)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        Integer power = Integer.valueOf(powerStr);
        if (power == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        UserRule rule = UserRule.valueOf(power);
        if (rule == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        List<Account> list = accountManager.getAllByRule(account, rule);
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Account item: list) {
            AccountVO accountVO = new AccountVO(item);
            result.add(accountVO);
        }

        logger
            .info(
                "Account:{},method:{}, execute:{}, parameters:[Power={}], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), powerStr,
                    result });
        return result;

    }

    /**
     * 获取账户列表
     * 
     * @return
     */
    public BaseVO accountList() {
        if (account.getRule() != UserRule.SUPER_ADMIN) {
            AccountVO actVO = new AccountVO(account);
            ListVO result = new ListVO();
            result.add(actVO);

            logger.info("Account:{}, execute:{}, parameters:[], Result:{}",
                new Object[] { account.getName(), getAction(), result });
            return result;
        }

        List<Account> list = accountManager.getAll(account.getId());
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Account item: list) {
            AccountVO accountVO = new AccountVO(item);
            result.add(accountVO);
        }

        logger.info(
            "Account:{},method:{}, execute:{}, parameters:[], Result:{}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;
    }

    /**
     * 获取店家列表
     * 
     * @return
     */
    public BaseVO shopList() {
        List<Shop> list = shopManager.getAll();
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Shop item: list) {
            ShopVO shopVO = new ShopVO(item);
            result.add(shopVO);
        }

        logger.info(
            "Account:{},method:{}, execute:{}, parameters:[], Result:{}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;

    }

}
