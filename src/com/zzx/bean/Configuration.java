package com.zzx.bean;

/**
 * 管理配置信息
 *  * Class.forName("com.mysql.cj.jdbc.Driver");
 * 	        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/planman?useSSL=false&serverTimezone=UTC", "root", "nihao886");
 *
 */
public class Configuration {

    private String driver;//驱动类
    private String url;//jdbc的url
    private String user;//数据库用户名
    private String password;//数据库密码
    private String usingDB;//正在使用哪个数据库
    private String srcPath;//项目源码路径
    private String poPackage;//扫描生成java类的包（po意思是persistence object持久化对象）
//项目使用查询类的路径
    private String queryClass;
    private  int poolMinSize;//连接池中最小的连接数
    private int poolMaxSize;
    public Configuration() {
    }

    public Configuration(String driver, String url, String user, String password, String usingDB, String srcPath, String poPackage) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
    }

    public int getPoolMinSize() {
        return poolMinSize;
    }

    public void setPoolMinSize(int poolMinSize) {
        this.poolMinSize = poolMinSize;
    }

    public int getPoolMaxSize() {
        return poolMaxSize;
    }

    public void setPoolMaxSize(int poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
    }

    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }
}
