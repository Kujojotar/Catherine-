### OpenVswitch

基础

南向协议：

OpenFlow协议:上期讲的那玩意儿

OVSDB协议:负责数据库的配置



OpenFlow端口:标准端口：物理端口 交换机定义的虚拟端口   其他基本上是保留的虚拟端口



流表项简要解析 openflow1.3：

in_port:输入的端口号

actions:配备项执行的操作

Match Fields:匹配的段
Instructions Set:

Counters:

Priority:优先级，数字大流表项优先级高

TimeOut:流表过期的时间

Cookie:



下发到交换机的时间：

1.交互式：不知道该怎么转发去找交换机--防火墙





opencswitch工具：

ovs-ofctl:提供流表的配置方法

ovs-appctl:

ovs-vsctl:创建和删除网桥，端口，连接控制器

ovsdb-client:

ovsdb-tool: