#THINGSdb

一个针对自然语言（中文）的数据服务器。


主要开发语言
    
    Java
    
设计背景

    社会分工更加专业化是历史发展的趋势。
    即将到来的技术革命也应为此方向，我想。
    
    现在的分工
       专业人士描述业务知识，由IT技术者代为开发自己业务领域的系统工具。
       
    未来的分工
       专业人士自己写关于自己业务领域的系统工具，
       他们是利用自然语言描述的，由专业工具自动生成系统工具。
       这个专业工具，我称之为AI。
       其中核心组件，我称之为THINGs

目标功能

    可以通过自然语言操作本系统完成作业指示。
    
理论构架

    万事皆是数据！
    
    数据是数据；
    程序也是数据；
    自然语言既可以是数据，也可以是程序，归根到底还是数据。
    正所谓，道生一，一生而二，二生三，三生无穷的道理。
    
自然语言处理的理论构架
    
    道生一，一生而二，二生三，三生无穷
    一为【什么】。。。。。。。。。。。。。。。。注1
    二为  |---【什么】【关系】。。。。。。。。注1注2
    三为 以上全体为三个对象。。。。。。。。。。注3
    
    注1【什么】为名次为数据，指向对象
    注2【关系】为动词为程序，表示对象与对象之间关系的数据
    注3 整体表达一个完整的对象，其小，可以指代单一物体，其大，可以指代整个世界，故称【三生无穷】
    注4 【什么】为阴，【关系】为阳
    注5 【什么】为双数，【关系】为单数

主要组件

    1,THINGSdb      负责数据存取;
    2,SDP解释器      负责sdp解析执行;
    3,Stanford NLP  负责自然语言解析（他社）;
    4,J2Cache       二级缓存（他社）;

【THINGSdb】

    是一个key-value数据库
    主要记忆数据与数据的【关系】
    被设计为大数据库

【SDP解释器】

    自然语言不会直接被做成程序，它充当的是索引链条的作用。
    而SDP的本质就是这些索引链条的数据化，
    传入的自然语言
        会被转译成SDP，
        让【SDP解释器】执行，
        最后存在数据库。
        

【Stanford NLP】

    感谢斯坦福大学提供的组件。
    可以将中文进行词法+语法分析进行分析。
    然后输出语法树。
    
【J2Cache】

    感谢J2Cache的组件提供。
    由于THINGSdb的速度太慢，不得不追加缓存机制。
            --2019/1/15
    郁闷的是，速度还是提不上去
    看来人家的成熟的数据库技术不是白写的
            --2019/1/20
            
工作方式

    以webservice形式提供对外接口服务。
    需要与外围工具配合完成任务。
    例，THINGSui，或THINGSbatch
    
    
导入手顺

    1,THINGSdb 的安装手顺
    
      （略，回头追加预定）
       stanford-chinese-corenlp-2018-10-05-models.jar 的Jar太大，也不在maven上，要自己下载加到项目里。 2019-1-27 
    
    2,SDP解释器 的安装手顺
    
      （略，回头追加预定）
      
    3,Stanford NLP  的安装手顺
    
      （略，回头追加预定）
      
    4,J2Cache
      
      安装手顺
      （略，回头追加预定）
      
      启动手顺
      redis-server
      
      关于Redis
      参考https://redis.io/topics/quickstart
