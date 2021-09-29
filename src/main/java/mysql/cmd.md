**MySQL常用命令**

```sql
SHOW ENGINES;-- 查看存储引擎的支持情况
SHOW VARIABLES LIKE '%storage_engine%';-- 查看默认的存储引擎
show create table t_blog;-- 可以查看建表语句，在建表语句的最后，可以看见表用了什么存储引擎
alter table table_name engine = innodb;-- 修改表的存储引擎
set default_storage_engine = MyISAM;-- 设置其它存储引擎
show variables like 'datadir';-- 查看数据的存放位置
mysql -h hostname -u username -p;-- 登录mysql数据库
connect db_blog localhost;-- 连接到指定的 库，主机
set character_set_client = utf8;-- 设置当前客户端的字符集编码[这种方式的修改只针对当前窗口有效，如果新开一个命令行窗口就会重新读取my.ini文件]
show character set;-- 查看支持的字符集
show collation like 'latin1%';
show variables like 'character%';-- 查看各个层次的字符集
set @用户变量 = expr1[,@用户变量2 = expr2,...];-- 定义用户变量  
set @m = (select title from t_blog where id = 1);-- 将查询的结果赋值给变量，必须要是一行一列的结果才行
show variables ;-- 得到系统变量清单
show global variables ;-- 得到全局系统变量清单
show session variables;-- 得到会话系统变量清单
```

MySQL相关命令

|   命令    | 简写 |                   具体含义                   |
| :-------: | :--: | :------------------------------------------: |
|     ?     |  \?  |                 显示帮助信息                 |
|   clear   |  \c  |               明确当前输入语句               |
|  connect  |  \r  |      连接到服务器，可选参数数据库和主机      |
| delimiter |  \d  |                设置语句分隔符                |
|    ego    |  \G  |     发送命令到mysql服务器，和;是一个作用     |
|   exit    |  \q  |                  退出MySQL                   |
|    go     |  \g  |                    发送名                    |
|   help    |  \h  |                 显示帮助信息                 |
|   notee   |  t   |                 不写输出文件                 |
|   print   |  \p  |                 打印当前命令                 |
|  prompt   |  \R  |              改变mysql提示信息               |
|   quit    |  \q  |                  退出mysql                   |
|  rehash   | \ #  |                 重建完成散列                 |
|  source   | \ .  |  执行一个sql脚本文件，以一个文件名作为参数   |
|  status   |  \s  |         从服务器获取mysql的状态信息          |
|    tee    |  \T  | 设置输出文件，并将信息添加所有给定的输出文件 |
|    use    |  \u  |      用另一个数据库，数据库名称作为参数      |
|  charset  |  \C  |              切换到另一个字符集              |
| warnings  |  \W  |            每一个语句之后显示警告            |
| nowarning |  \w  |           每一个语句之后不显示警告           |

**转义序列**

| 序列 |         含义         |
| :--: | :------------------: |
|  \0  | 一个ASCII 0(nul)字符 |
|  \n  |      一个换行符      |
|  \r  |      一个回车符      |
|  \t  |      一个定位符      |
|  \b  |      一个退格符      |
|  \Z  |  一个ASCII 26 字符   |
| \ '  |      一个单引号      |
| \ "  |      一个双引号      |
| \ \  |      一个反斜线      |
|  \%  |        一个%         |
| \ _  |        一个_         |

