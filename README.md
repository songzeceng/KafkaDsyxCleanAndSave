贵广电视院线数仓的数据清洗和存储模块，由生产者消费者和数据清洗两部分构成


生产者消费者模块：

1、行为数据生产者：目前通过读取数据文件来生产，启动类为com.profile.producer.BehaviorFlowProducerBootstrap。相关参数有三个：bootstrapServer、sourceUrl和topic，既可以在命令行中按顺序指定(必须按顺序且完整)，也可以在相关配置类里改变值，然后再idea里测试。建议先采用后者进行调试

2、行为数据消费者：连接行为数据生产者进行数据清洗和存储，启动类为com.profile.consumer.BehaviorFlowConsumerBootstrap，目前是存到mongo里。相关参数有七个，kafka的参数有bootstrapServer、topic、groupId，mongo的参数有ip、port、dbName和collectionName，使用方法同行为数据生产者参数

3、行为数据分析消费者：用来对行为数据进行分析，启动类为com.profile.consumer.BehaviorFlowAnalysisConsumerBootstrap，实现方式为spark-streaming。这个类目前比较原始，没有命令行参数，连接的是行为数据生产者。相关配置参数有bootstrap.servers、group.id和topicsSet

4、媒资数据消费者：准确来说，这不是一个kafka消费者，它只负责把媒资数据存到hbase里，启动类为com.profile.consumer.MediaConsumerBootstrap。相关参数包括媒资文件地址mediaFileURL和HBase参数：zookeeperIp、zookeeperPort、tableName、columnFamily，列标识固定为tag，使用方法同行为数据生产者参数。


数据清洗模块，负责对行为数据消费者得到的原始数据进行清洗：

1、主要的类都在com.profile.clean包下，其中cleaner包下的类都是清洗器，入口为GeneralDataCleaner，它负责对传过来的json对象进行公有必需字段的检验，然后根据bizType所指定的不同的业务场景分发给具体的Cleaner

2、具体的Cleaner的清理逻辑和GeneralDataCleaner类似，先对json对象进行公有必需字段的检验，然后根据eventId所指定的不同事件，对其他必需字段进行完整性和合法性检验和修改(包括有效值、类型)，如果检验或修改成功，返回true，否则返回false

3、共同的检验逻辑都放在了com.profile.util.JsonUtils类中

4、第2点最后说的修改，是指对值正确但格式不对(比如"2.0"修改成"2")的字段进行修改，如果是其他字符串内容不对、必需字段不全等，不会进行修改

5、GeneralDataCleaner的main()方法可用来测试，建议先运行它

6、com.profile.clean.collector包下的UncleanDataInfoCollector用来打印或存储清洗中出现的问题

7、CleanConfig类存储需要验证的事件id、必需字段、有效值


com.profile.db负责对数据库操作进行封装，说明如下：

1、目前用的数据库是HBase和mongo，前者存放媒资，后者存放行为数据，都采用了单例

2、对于媒资，HBase的表结构如下：表名默认为profile，列族默认为tag，行键为数据的id字段（如果id不存在或者值为空或null值，就生成一个随机id，位数和其他有效id一致），列标识为键(id除外)，值为字段值

3、对于mongo，将传入的json对象存进去即可。主要是统计userCount和itemCount，由于distinct方法在数据量大的时候不稳定，目前是采用分组聚合统计的方式实现的


com.profile.util包用于放置一些工具类，说明如下：

1、FileUtils：用于读取hdfs文件、存储数据清洗的日志等

2、HdfsUtils：用于配置hdfs常量，主要是媒资和行为数据文件的地址

3、JsonUtils：json数据清洗，包括验证和修改

4、KafkaConfig：配置kafka常量，包括服务器地址、话题；根据入参构建一般的生产者和消费者

5、TextUtils：对字符串进行判空、生成随机id、判断字符串是否在数组中等

6、TimeUtils：格式化时间(长整型或Date对象)，计算时间间隔


未来可能的改进：

1、行为数据消费者：把原始数据和清洗后的数据分别存到HBase里，可能是一张表的两个列标识，也可能是两张表

2、对于行为数据，HBase表怎么设计，特别是行键

3、分析行为数据时，要在hive中创建hbase的外表，那么行为数据分析消费者应该连接的是hive，而不是生产者

4、行为数据分析消费者的命令行参数

5、命令行参数都没有测


其他注意事项：

1、行为数据分析消费者的scala编译运行版本为2.10，如果用2.11会产生一堆莫名其妙的错

2、jdk版本为1.8
