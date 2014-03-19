/**
 * @(#)FabricAppInitMain.java, 2013-7-9. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.init;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author nisonghai
 */
public class FabricAppInitMain {

    protected ApplicationContext ctx;

    private FabricAppServerInitManager initManager;

    private SessionFactory sessionFactory;

    private static String SCENE_FILE_NAME = "场景";

    private static String FLOWER_FILE_NAME = "花型";

    private static String SCHEME_FILE_NAME = "方案";

    public FabricAppInitMain() {
        String[] configs = { "classpath*:spring/*.xml" };
        ctx = new FileSystemXmlApplicationContext(configs);
        initManager = ctx.getBean("fabricAppServerInitManager",
            FabricAppServerInitManager.class);
        sessionFactory = ctx.getBean("sessionFactory", SessionFactory.class);
    }

    public void help() {
        System.out.println("init\t\t初始化数据");
        System.out.println("create\t\t");
        System.out.println("\t\t\tcreate account DESIGNER <name> 1");
        System.out.println("\t\t\tcreate account BUSINESS <name> 1");
        System.out.println("\t\t\t create productTag");
        System.out.println("\t\t\t add flowerRefTimes");
        System.out.println("\t\t\t modifyColor");
        System.out
            .println("\t\t\t create flowertype <designer name> /E:/Data/家纺/花型");
        System.out
            .println("\t\t\t create scheme <designer name> /E:/Data/家纺/方案 100");
        System.out.println("\t\t\t build all <designer name> /E:/Data/家纺 100");
        System.out.println("\t\t\t clear scene");
        System.out.println("\t\t\t clear flowertype");
        System.out.println("\t\t\t clear scheme");
        System.out.println("\t\t\t clear all");

        /**
         * /E:/Data/家纺 输入参数 recreate scene /E:/Data/家纺/场景 recreate flowertype
         * designer /E:/Data/家纺/花型
         */
    }

    public void modify(){
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory,
            new SessionHolder(session));
        initManager.modifyColor();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
    }
    /**
     * 初始化数据
     */
    public void init(String... args) throws Exception {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory,
            new SessionHolder(session));
        initManager.initAccount();
        initManager.initFlowerTag();
        initManager.initScenePosition();
        initManager.initSceneStyle();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
    }

    public void create(String... args) throws Exception {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
       // TransactionSynchronizationManager.bindResource(sessionFactory,
       ///     new SessionHolder(session));
        if (args == null || args.length == 0) {
            return;
        }
        if (args[0].equalsIgnoreCase("account")) {
            String ruleName = args[1];
            String startName = args[2];
            int size = Integer.valueOf(args[3]);
            initManager.createAccountForDesigner(ruleName, startName, size);
        } else if (args[0].equalsIgnoreCase("scene")) {
            String path = args[1];
            initManager.createScene(path);
        } else if (args[0].equalsIgnoreCase("flowertype")) {
            String user = args[1];
            String path = args[2];
            int nCount = Integer.valueOf(args[3]);
            initManager.createFlowerType(user, path, nCount);
            //initManager.createFlowerType(user, path);
        } else if (args[0].equalsIgnoreCase("scheme")) {
            String user = args[1];
            String path = args[2];
            int nCount = Integer.valueOf(args[3]);
            initManager.createScheme(user, path, nCount);
        }else if(args[0].equalsIgnoreCase("productTag")){
            initManager.initProductTag();
        }
       // TransactionSynchronizationManager.unbindResource(sessionFactory);
    }

    public void clear(String... args) throws Exception {
        if (args == null || args.length == 0) {
            return;
        }
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory,
            new SessionHolder(session));
        if (args[0].equalsIgnoreCase("scene")) {
            initManager.deleteScene();
        } else if (args[0].equalsIgnoreCase("scheme")) {
            initManager.deleteScheme();
        } else if (args[0].equalsIgnoreCase("flower")) {
            initManager.deleteFlower();
        } else if (args[0].equalsIgnoreCase("all")) {
            initManager.deleteFlower();
            initManager.deleteScheme();
            initManager.deleteScene();
        }
        TransactionSynchronizationManager.unbindResource(sessionFactory);
    }

    public void build(String... args) throws Exception {
        if (args == null || args.length == 0) {
            return;
        }
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory,
            new SessionHolder(session));
        if (args[0].equalsIgnoreCase("all")) {
            /**
             * 清空工程目录
             */
            initManager.deleteFlower();
            initManager.deleteScheme();
            initManager.deleteScene();

            /**
             * 初始化花型数据
             */
            String user = args[1];
            String path = args[2];
            int nCount = Integer.valueOf(args[3]);
            String flowerPath = path + File.separator + FLOWER_FILE_NAME;
            initManager.createFlowerType(user, flowerPath, nCount);
            //initManager.createFlowerType(user, flowerPath);
            /**
             * 初始化场景数据
             */
            String scenePath = path + File.separator + SCENE_FILE_NAME;
            initManager.createScene(scenePath);
            /**
             * 初始化方案数据
             */
            String schemePath = path + File.separator + SCHEME_FILE_NAME;
            initManager.createScheme(user, schemePath, nCount);
        }
        TransactionSynchronizationManager.unbindResource(sessionFactory);
    }

    public void add(String... args) throws Exception {
        if (args == null || args.length == 0) {
            return;
        }
        if (args[0].equalsIgnoreCase("scene")) {
            String path = args[1];
            initManager.addScene(path);
        } else if (args[0].equalsIgnoreCase("scheme")) {
            System.out.println("not implements add scheme.");
        } else if (args[0].equalsIgnoreCase("flower")) {
            System.out.println("not implements add flower.");
        } else if (args[0].equalsIgnoreCase("all")) {
            System.out.println("not implements add all.");
        }else if(args[0].equalsIgnoreCase("flowerRefTimes")){
            Session session = SessionFactoryUtils.getSession(sessionFactory, true);
            TransactionSynchronizationManager.bindResource(sessionFactory,
                new SessionHolder(session));
            initManager.addFlowerRefTimes();
            TransactionSynchronizationManager.unbindResource(sessionFactory);
        }
    }
    
    private static String[] copy(String[] args, int startIndex) {
        int length = args.length - startIndex;
        if (length == 0) {
            return new String[0];
        }
        String[] result = new String[length];
        System.arraycopy(args, startIndex, result, 0, length);
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        FabricAppInitMain app = new FabricAppInitMain();
        try {
            if (args == null || args.length == 0
                || args[0].equalsIgnoreCase("help")) {
                app.help();
            } else if (args[0].equalsIgnoreCase("init")) {
                app.init(copy(args, 1));
            } else if (args[0].equalsIgnoreCase("create")) {
                app.create(copy(args, 1));
            } else if (args[0].equalsIgnoreCase("clear")) {
                app.clear(copy(args, 1));
            } else if (args[0].equalsIgnoreCase("build")) {
                app.build(copy(args, 1));
            } else if(args[0].equalsIgnoreCase("add")){
                app.add(copy(args,1));
            }else if(args[0].equalsIgnoreCase("modifyColor")){
                app.modify();
            }else{
                app.help();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
