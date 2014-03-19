package fabric.server.web.resource;

import java.io.File;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fabric.common.db.BaseEntity;
import fabric.common.db.BaseManager;
import fabric.common.db.Status;
import fabric.common.utils.AppUtils;
import fabric.common.web.BaseResource;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.common.web.XmlFactory;
import fabric.server.entity.Account;
import fabric.server.entity.AccountSession;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;
import fabric.server.web.session.SessionPool;

public abstract class AuthResource<T extends BaseEntity, M extends BaseManager<T>>
    extends BaseResource<T, M> {

    // 日志输出
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Account account;

    protected AccountSession accountSession;

    @Autowired
    protected SessionPool sessionPool;

    @Autowired
    protected AccountManager accountManager;

    @Autowired
    protected XmlFactory xmlFactory;

    @Get
    public Representation doGet() {
        if (!intercept()) {
            return new ErrorVO(ErrorCode.NOPERMISSIONS).representation();
        }
        return super.doGet();
    }

    @Post
    public Representation doPost(Representation entity) {
        if (intercept() == false) {
            return new ErrorVO(ErrorCode.NOPERMISSIONS).representation();
        }
        return super.doPost(entity);
    }

    /**
     * @return account
     */
    private AccountSession getAccountBySession() {
        try {
            int sid = Integer.valueOf(getRequest().getCookies().getFirstValue(
                "Sid"));
            String ip = getRequest().getClientInfo().getAddress();
            return sessionPool.find(sid, ip);
        } catch (Exception e) {
            logger.info("getCookies SID error.\n" + e.getMessage());
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * 角色拦截
     * 
     * @return boolean
     */
    private boolean intercept() {

        String action = getAction();

        /**
         * 登陆
         */
        if (action.equals("login")) {
            return true;
        }

        /**
         * 验证登陆状态
         */
        accountSession = getAccountBySession();
        if (accountSession == null) {
            return false;
        }
        account = accountSession.getAccount();

        logger.info("Account:{}, ip:{}, power:{}, method:{}, action:{}.",
            new Object[] { account.getName(), accountSession.getIp(),
                accountSession.getPower().name(),
                this.getClass().getSimpleName(), action });

        if (action.equals("accountList")) {
            return true;
        }

        if (account.getStatus() != Status.Normal)
            return false;
        UserRule rule = account.getRule();

        //超级管理员有任何角色功能
        if (rule == UserRule.SUPER_ADMIN)
            return true;

        //设计师有添加和修改，删除或者更新
        if (action.contains("add") || action.contains("update")
            || action.contains("delete") || action.contains("grant")) {
            if (rule == UserRule.DESIGNER) {
                return true;
            } else {
                return false;
            }
        } else if (action.contains("account")) {
            //超级管理员才能查看所有账户信息
            return false;
        } else if (action.contains("order")) {
            //店老板才可以查看所有订单下单以及修改订单
            if (rule == UserRule.BUSINESS) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * @param dirFrom
     *            原路径
     * @param fileName
     *            文件名
     * @param uuid
     * @param dirTo
     *            目标路劲
     * @param type
     *            类型
     * @return
     */
    protected DataFile createDataFile(String dirFrom, String fileName,
        String uuid, String dirTo, FileType type) {
        String file = dirFrom + File.separator + uuid;
        File checkFile = new File(file);
        if (!checkFile.exists()) {
            return null;
        }
        if (AppUtils.copyFile(file, dirTo, fileName)) {
            //AppUtils.deleteFile(file);
        } else {
            return null;
        }
        DataFile dataFile = new DataFile(fileName, type, uuid);
        return dataFile;
    }
}
