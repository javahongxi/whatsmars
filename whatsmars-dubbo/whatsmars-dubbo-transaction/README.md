###### 处理分布式事务问题，无非两个方向，回滚或补全，以（最终）一致性为基本原则

# 分布式事务服务简介 产品详情

> 注意：为向用户提供更加优质的服务，蚂蚁金融云已对中间件产品完成进一步升级改造，并计划于 2018 年 3 月 31 日下线本产品。

分布式事务服务（Distributed Transaction Service，简称 DTS）是一个分布式事务框架，用来保障在大规模分布式环境下事务的最终一致性。DTS 从架构上分为 xts-client 和 xts-server 两部分，前者是一个嵌入客户端应用的 Jar 包，主要负责事务数据的写入和处理；后者是一个独立的系统，主要负责异常事务的恢复。

# 核心特性

传统关系型数据库的事务模型必须遵守 ACID 原则。在单数据库模式下，ACID 模型能有效保障数据的完整性，但是在大规模分布式环境下，一个业务往往会跨越多个数据库，如何保证这多个数据库之间的数据一致性，需要其他行之有效的策略。在 JavaEE 规范中使用 2PC (2 Phase Commit, 两阶段提交) 来处理跨 DB 环境下的事务问题，但是 2PC 是反可伸缩模式，也就是说，在事务处理过程中，参与者需要一直持有资源直到整个分布式事务结束。这样，当业务规模达到千万级以上时，2PC 的局限性就越来越明显，系统可伸缩性会变得很差。基于此，我们采用 BASE 的思想实现了一套类似 2PC 的分布式事务方案，这就是 DTS。DTS 在充分保障分布式环境下高可用性、高可靠性的同时兼顾数据一致性的要求，其最大的特点是保证数据最终一致 (Eventually consistent)。

> 简单的说，DTS 框架有如下特性：

- 最终一致：事务处理过程中，会有短暂不一致的情况，但通过恢复系统，可以让事务的数据达到最终一致的目标。
- 协议简单：DTS 定义了类似 2PC 的标准两阶段接口，业务系统只需要实现对应的接口就可以使用 DTS 的事务功能。
- 与 RPC 服务协议无关：在 SOA 架构下，一个或多个 DB 操作往往被包装成一个一个的 Service，Service 与 Service 之间通过 RPC 协议通信。DTS 框架构建在 SOA 架构上，与底层协议无关。
- 与底层事务实现无关： DTS 是一个抽象的基于 Service 层的概念，与底层事务实现无关，也就是说在 DTS 的范围内，无论是关系型数据库 MySQL，Oracle，还是 KV 存储 MemCache，或者列存数据库 HBase，只要将对其的操作包装成 DTS 的参与者，就可以接入到 DTS 事务范围内。

# 核心概念

在 DTS 内部，我们将一个分布式事务的关联方，分为发起方和参与者两类：

- 发起方： 分布式事务的发起方负责启动分布式事务，触发创建相应的主事务记录。发起方是分布式事务的协调者，负责调用参与者的服务，并记录相应的事务日志，感知整个分布式事务状态来决定整个事务是 COMMIT 还是 ROLLBACK。
- 参与者：参与者是分布式事务中的一个原子单位，所有参与者都必须在一阶段接口（Prepare）中标注（Annotation）参与者的标识，它定义了 prepare、commit、rollback 3个基本接口，业务系统需要实现这3个接口，并保证其业务数据的幂等性，也必须保证 prepare 中的数据操作能够被提交（COMMIT）或者回滚（ROLLBACK）。从存储结构上，DTS 的事务状态数据可以分为主事务记录（Activity）和分支事务记录（Action）两类：
- 主事务记录 Activity：主事务记录是整个分布式事务的主体，其最核心的数据结构是事务号（TX_ID）和事务状态（STATE），它是在启动分布式事务的时候持久化写入数据库的，它的状态决定了这笔分布式事务的状态。
- 分支事务记录 Action：分支事务记录是主事务记录的一个子集，它记录了一个参与者的信息，其中包括参与者的 NAME 名称，DTS 通过这个 NAME 来唯一定位一个参与者。通过这个分支事务信息，我们就可以对参与者进行提交或者回滚操作。

# 基础术语

1. 分布式事务（Distributed Transaction）

- 事务的参与者、支持事务的服务器、资源服务器以及事务管理器分别位于不同的分布式系统的不同节点之上

1. 最终一致（Eventually consistent）

- 事务处理过程中，在特殊异常的情况（比如发起方crash）会有短暂不一致的情况（根据经验，一般概率小于0.01%），但通过恢复系统，可以让事务的数据达到最终一致的目标，用户无需感知

1. 两阶段提交（2PC）

- 一种协调所有分布式原子事务参与者，并决定提交或取消（回滚）的分布式算法

1. 发起方（Coordinator）

- 是指一笔分布式事务的发起方，一般是一个入口的业务系统

1. 参与者（Participant）

- 一笔分布式事务的参与者，提供符合 2PC 规范的接口实现

1. 主事务记录 (Activity)

- 是指用来代表一笔分布式事务的数据存储记录

1. 分支事务记录 (Action)

- 是指代表一个参与者的数据存储记录

1. 事务号（Transaction Identification）

- 一笔分布式事务的唯一编号

1. 业务类型（Business Type）

- 是指发起方用来发起分布式事务的业务类型，比如交易支付

1. 业务号（Business Identification）

- 是指代表发起方发起分布式事务的主体，比如交易号

1. 事务恢复（Transaction recovery）

- 是指一笔分布式事务二阶段失败之后，由恢复系统发起的事务恢复行为



# 分布式事务服务 DTS Sample

如何玩转 DTS，基本上使用 DTS 对发起方的配置要求会多一点。

## 添加 DTS 的依赖

> NOTE： 发起方和参与方都需要添加依赖。

如果使用 SOFA Lite，只需按照样例工程里的方式添加依赖：

```xml
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>slite-starter-xts</artifactId>
</dependency>
```

如果没有使用 SOFA Lite，那么需要在 pom 配置里加上 DTS 的依赖：

```xml
<dependency>
    <groupId>com.alipay.xts</groupId>
    <artifactId>xts-core</artifactId>
    <version>6.0.8</version>
</dependency>
<dependency>
    <groupId>com.alipay.xts</groupId>
    <artifactId>xts-adapter-sofa</artifactId>
    <version>6.0.8</version>
</dependency>
```

# 场景介绍

1. 首先我们假想这样一种场景：转账服务，从银行 A 某个账户转 100 元钱到银行 B 的某个账户，银行 A 和银行 B 可以认为是两个单独的系统，也就是两套单独的数据库。
2. 我们将账户系统简化成只有账户和余额 2 个字段，并且为了适应 DTS 的两阶段设计要求，业务上又增加了一个冻结金额（冻结金额是指在一笔转账期间，在一阶段的时候使用该字段临时存储转账金额，该转账额度不能被使用，只有等这笔分布式事务全部提交成功时，才会真正的计入可用余额）。按这样的设计，用户的可用余额等于账户余额减去冻结金额。这点是理解参与者设计的关键，也是 DTS 保证最终一致的业务约束。
3. 同时为了记录账户操作明细，我们设计了一张账户流水表用来记录每次账户的操作明细，所以领域对象简单设计如下：

```java
public class Account {
    /**
     * 账户
     */
    private String accountNo;
    /**
     * 余额
     */
    private double amount;
    /**
     * 冻结金额
     */
    private double freezedAmount;
```

```java
public class AccountTransaction {
    /**
     * 事务id
     */
    private String txId;
    /**
     * 操作账户
     */
    private String accountNo;
    /**
     * 操作金额
     */
    private double amount;
    /**
     * 操作类型，扣帐还是入账
     */
    private String type;
```

## A 银行参与者

我们假设需要从 A 账户扣 100 元钱，所以 A 系统提供了一个扣帐的服务，对应扣帐的一阶段接口和相应的二阶段接口如下：

```java
/**
 * A银行参与者，执行扣帐操作
 * @version $Id: FirstAction.java, v 0.1 2014年9月22日 下午5:32:59 Exp $
 */
public interface FirstAction {
  /**
   * 一阶段方法，注意要打上xts的标注哦
   * 
   * @param businessActionContext
   * @param accountNo
   * @param amount
   */
  @TwoPhaseBusinessAction(name = "firstAction", commitMethod = "commit", rollbackMethod = "rollback")
  public void prepare_minus(BusinessActionContext businessActionContext,String accountNo,double amount);
  /**
   * 二阶段的提交方法
   * @param businessActionContext
   * @return
   */
  public boolean commit(BusinessActionContext businessActionContext);
  /**
   * 二阶段的回滚方法
   * @param businessActionContext
   * @return
   */
  public boolean rollback(BusinessActionContext businessActionContext);
}
```

对应的一阶段扣帐实现

```java
public void prepare_minus(final BusinessActionContext businessActionContext,
                          final String accountNo, final double amount) {
    transactionTemplate.execute(new TransactionCallback() {
        @Override
        public Object doInTransaction(TransactionStatus status) {
            try {
                try {
                        //锁定账户
                        Account account = accountDAO.getAccount(accountNo);
                        if (account.getAmount() - amount < 0) {
                            throw new TransactionFailException("余额不足");
                        }
                        //先记一笔账户操作流水
                        AccountTransaction accountTransaction = new AccountTransaction();
                        accountTransaction.setTxId(businessActionContext.getTxId());
                        accountTransaction.setAccountNo(accountNo);
                        accountTransaction.setAmount(amount);
                        accountTransaction.setType("minus");
                        //初始状态，如果提交则更新为C状态，如果失败则删除记录
                        accountTransaction.setStatus("I");
                        accountTransactionDAO.addTransaction(accountTransaction);
                        //再递增冻结金额，表示这部分钱已经被冻结，不能使用
                        double freezedAmount = account.getFreezedAmount() + amount;
                        account.setFreezedAmount(freezedAmount);
                        accountDAO.updateFreezedAmount(account);
                    } catch (Exception e) {
                        System.out.println("一阶段异常," + e);
                        throw new TransactionFailException("一阶段操作失败", e);
                    }
            return null;
        }
    });
}
```

对应的二阶段提交操作

```java
public boolean commit(final BusinessActionContext businessActionContext) {
    transactionTemplate.execute(new TransactionCallback() {
        @Override
        public Object doInTransaction(TransactionStatus status) {
            try {
                    //找到账户操作流水
                    AccountTransaction accountTransaction = accountTransactionDAO
                        .findTransaction(businessActionContext.getTxId());
                    //事务数据被删除了
                    if (accountTransaction == null) {
                        throw new TransactionFailException("事务信息被删除");
                    }
                    //重复提交幂等保证只做一次
                    if (StringUtils.equalsIgnoreCase("C", accountTransaction.getStatus())) {
                        return true;
                    }
                    Account account = accountDAO.getAccount(accountTransaction.getAccountNo());
                    //扣钱
                    double amount = account.getAmount() - accountTransaction.getAmount();
                    if (amount < 0) {
                        throw new TransactionFailException("余额不足");
                    }
                    account.setAmount(amount);
                    accountDAO.updateAmount(account);
                    //冻结金额相应减少
                    account.setFreezedAmount(account.getFreezedAmount()
                                             - accountTransaction.getAmount());
                    accountDAO.updateFreezedAmount(account);
                    //事务成功之后更新为C
                    accountTransactionDAO.updateTransaction(businessActionContext.getTxId(), "C");
                } catch (Exception e) {
                    System.out.println("二阶段异常," + e);
                    throw new TransactionFailException("二阶段操作失败", e);
                }
            return null;
        }
    });
    return false;
}
```

对应的二阶段回滚操作

```java
public boolean rollback(final BusinessActionContext businessActionContext) {
    transactionTemplate.execute(new TransactionCallback() {
        @Override
        public Object doInTransaction(TransactionStatus status) {
            try {
                    //回滚冻结金额
                    AccountTransaction accountTransaction = accountTransactionDAO
                        .findTransaction(businessActionContext.getTxId());
                    if (accountTransaction == null) {
                        System.out.println("二阶段---空回滚成功");
                        return null;
                    }
                    Account account = accountDAO.getAccount(accountTransaction.getAccountNo());
                    account.setFreezedAmount(account.getFreezedAmount()
                                             - accountTransaction.getAmount());
                    accountDAO.updateFreezedAmount(account);
                    //删除流水
                    accountTransactionDAO.deleteTransaction(businessActionContext.getTxId());
                } catch (Exception e) {
                    System.out.println("二阶段异常," + e);
                    throw new TransactionFailException("二阶段操作失败", e);
                }
              return null;
        }
   });
   return false;
}
```

## B 银行参与者

我们假设需要对 B 账户入账 100 元钱，所以 B 系统提供了一个入账的服务，对应入账的一阶段接口和相应的二阶段接口基本和 A 银行参与者类似，这里不多做介绍，可以直接查看样例工程下的 xts-sample 工程代码。

## 发起方

前面介绍了参与者的实现细节，接下来看看发起方系统是如何协调这 2 个参与者，达到分布式事务下数据的最终一致性的。相比参与者，发起方的配置要复杂一些。

1. 在发起方自己的数据库里创建 DTS 的表
2. 配置 BusinessActivityControlService

BusinessActivityControlService 是 DTS 分布式事务的启动类，在 SOFA 环境中，我们可以这样使用

```xml
<!-- 分布式事务的服务，用来发起分布式事务 -->
<sofa:xts id="businessActivityControlService">
  <!-- 发起方自己的数据源，建议使用zdal数据源组件，这里简单使用dbcp数据源 -->
   <sofa:datasource ref="activityDataSource"/>
  <!-- 如果使用zdal数据源，可以不用配置这个属性，这个dbType是用来区分目标库的类型，以方便xts设置sqlmap -->
   <sofa:dbtype value="mysql"/>
</sofa:xts>
```

在其他环境中，我们也可以将它配置成一个普通 Bean，配置如下

```xml
<!-- 分布式事务的服务，用来发起分布式事务 -->
<bean name="businessActivityControlService" class="com.alipay.xts.client.api.impl.sofa.BusinessActivityControlServiceImplSofa">
   <!-- 发起方自己的数据源，建议使用zdal数据源组件，这里简单使用dbcp数据源 -->
   <property name="dataSource" ref="activityDataSource"/>
   <!-- 如果使用zdal数据源，可以不用配置这个属性，这个dbType是用来区分目标库的类型，以方便xts设置sqlmap -->
   <property name="dbType" value="mysql"/>
</bean>
```

1. 配置参与者服务和拦截器。如果是在 SOFA 环境中，DTS 框架会自动拦截参与者方法，拦截器就不用配置了

```xml
<!-- 第一个参与者的代理 -->
<bean id="firstAction" class="org.springframework.aop.framework.ProxyFactoryBean">
   <property name="proxyInterfaces" value="com.alipay.xts.client.sample.action.FirstAction"/>
   <property name="target" ref="firstActionTarget"/>
   <property name="interceptorNames">
      <list>
          <value>businessActionInterceptor</value>
      </list>
   </property>
</bean>
<!-- 第一个参与者 -->
<bean id="firstActionTarget" class="com.alipay.xts.client.sample.action.impl.FirstActionImpl">
   <property name="accountTransactionDAO">
      <ref bean="firstActionAccountTransactionDAO" />
   </property>
   <property name="accountDAO">
      <ref bean="firstActionAccountDAO" />
   </property>
   <property name="transactionTemplate">
      <ref bean="firstActionTransactionTemplate" />
   </property>
</bean>
<!-- 第二个参与者的代理 -->
<bean id="secondAction" class="org.springframework.aop.framework.ProxyFactoryBean">
   <property name="proxyInterfaces" value="com.alipay.xts.client.sample.action.SecondAction"/>
   <property name="target" ref="secondActionTarget"/>
   <property name="interceptorNames">
     <list>
        <value>businessActionInterceptor</value>
     </list>
   </property>
</bean>
<!-- 第二个参与者 -->
<bean id="secondActionTarget" class="com.alipay.xts.client.sample.action.impl.SecondActionImpl">
   <property name="accountTransactionDAO">
      <ref bean="secondActionAccountTransactionDAO" />
   </property>
   <property name="accountDAO">
      <ref bean="secondActionAccountDAO" />
   </property>
   <property name="transactionTemplate">
      <ref bean="secondActionTransactionTemplate" />
   </property>
</bean>
<!-- 拦截器，在参与者调用前生效，插入参与者的action记录 -->
<bean id="businessActionInterceptor"
     class="com.alipay.sofa.platform.xts.bacs.integration.BusinessActionInterceptor">
   <property name="businessActivityControlService" ref="businessActivityControlService"/>
</bean>
```

1. 发起分布式事务

启动分布式事务的入口方法

```java
/**
 * 启动一个业务活动。
 * 
 * 为了保证业务活动的唯一性，对同样的businessType与businessId，只能有一次成功记录。
 * 
 * 系统允许多次调用start方式启动业务活动，如果当前业务活动已经存在，再次启动业务活动不会有任何效果，也不会检查业务类型与业务号是否匹配。
 * 
 * @param businessType 业务类型，由业务系统自定义，比如'trade_pay'代表交易支付
 * @param businessId 业务号，如交易号
 * @notice 事务号的格式为: businessType+"-"+businessId，总长度为128
 * @return 
 */
BusinessActivityId start(String businessType, String businessId, Map<String, Object> properties);
```

> businessType + businessId 就是最终的事务号，properties 可以让发起方设置一些全局的事务上下文信息。

转账服务发起分布式事务

```java
/**
 * 执行转账操作
 * 
 * @param from
 * @param to
 * @param amount
 */
public void transfer(final String from, final String to, final double amount) {
    /**
     * 注意：开启xts服务必须包含在发起方的本地事务模版中
     */
    transactionTemplate.execute(new TransactionCallback() {
        @Override
        public Object doInTransaction(TransactionStatus status) {
           System.out.println("开始启动xts分布式事务活动");
                //启动分布式事务，第三个是分布式事务的全局上下文信息
                Map<String, Object> properties = new HashMap<String, Object>();
                BusinessActivityId businessActivityId = businessActivityControlService.start("pay",
                    businessId, properties);
                System.out.println("=====启动分布式事务成功，事务号：" + businessActivityId.toStringForm()
                                   + "=====");
                System.out.println("=====一阶段,准备从B银行执行入账操作=====");
                //第二个参与者入账操作
                if (secondAction.prepare_add(null, to, amount)) {
                    System.out.println("=====一阶段,从B银行执行入账操作成功=====");
                } else {
                    System.out.println("=====一阶段,从B银行执行入账操作失败，准备回滚=====");
                    status.setRollbackOnly();
                    return null;
                }
                System.out.println("=====一阶段,准备从A银行执行扣账操作=====");
                //第一个参与者扣账操作
                if (firstAction.prepare_minus(null, from, amount)) {
                    System.out.println("=====一阶段,从A银行执行扣账操作成功=====");
                } else {
                    System.out.println("=====一阶段,从A银行执行扣账操作失败，准备回滚=====");
                    status.setRollbackOnly();
                }
            return null;
        }
    });
    System.out.println("二阶段----转账成功，钱已到位");
}
```

## 小结

使用 DTS 开发需要关注的就是以上内容。对于参与者来说，最关键的是业务上如何实现两阶段处理来保证最终一致性，对于发起方来说，主要是要配置 DTS 的表。



# 典型场景和实现原理

首先来看一个典型的分布式事务场景

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts1.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts1.png)

- 在这个例子中，app1 作为分布式事务的发起方调用了参与者 app2 的 insert 操作和 app3 的 update 操作，之后调用自己的本地 insert 操作，在这个分布式事务中包含了 3 次对 db 的操作，而 3 个 db 分属于不同的系统，图中虚线覆盖的范围是 app1 的一个本地事务模版的代码范围。

来看看在 DTS 内部针对这个场景是如何实现的。 [![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts2.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts2.png)

- app2 和 app3 作为参与者分别实现了 prepare，commit 和 rollback 接口
- 我们将 prepare 阶段称为第一阶段，commit/rollback 阶段称为第二阶段
- 上图就是典型的 DTS 事务流转示例，第一阶段被包含在发起方的本地事务模版中，发起方的本地事务结束后，开始执行二阶段操作，二阶段结束，DTS 事务整个结束。

# 针对以上运行流程，我们可以总结如下

1. DTS 分布式事务是基于两阶段提交（ 2 phase commit，简称 2pc）原理
2. 事务发起方是分布式事务的协调者
3. 事务发起方本地事务的最终状态（提交或回滚）决定整个分布式事务的最终状态
4. 分布式事务必须在本地事务模板中进行
5. 参与者通过配置在 xml 中的拦截器来完成 action 信息的获取和数据插入
6. 事务参与者的接口需要支持两阶段。发起方（使用者）只关注第一阶段的方法，第二阶段由框架自动调用。

再来看看以上的说明对应的发起方代码是怎样的

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts3.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts3.png)

对应的参与者接口是这样的

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts4.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts4.png)

> 最核心的地方在于 prepare 接口打上了@TwoPhaseBusinessAction 标注，通过这个标注DTS 框架可以感知到这个服务就是一个 DTS 的参与者

接下来，结合上面这个例子让我们详细分析下 DTS 内部的工作原理，大体如下图 [![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts5.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts5.png)

## 核心点：

1. 使用数据库持久化记录事务数据，且使用独立的事务模版，也就是单独事务
2. 特别关注红线对应的 sql，这是一句 update 主事务表的 sql，而这句 sql 是在发起方的本地事务中的，这样一来就和发起方的事务绑定了，如果发起方本地事务成功，则这句 update 语句必然成功，如果发起方本地事务失败，则这句 update 语句必然失败，这样我们就可以根据 activity 表的事务记录的状态来决定这笔分布式最终的状态是成功还是失败了
3. 在调用参与者前，启动单独事务插入代表这个参与者的分支事务记录，以供后续恢复使用
4. 二阶段是通过 spring 提供的事务同步器实现的，如果发起方的本地事务失败，则二阶段自动回滚所有参与者，如果发起方的本地事务成功，则二阶段自动提交所有参与者。二阶段结束后，删除所有事务记录



# 事务恢复

学习了 DTS 的原理之后，可能你会问，如果二阶段失败会怎样？比如需要 commit app2 和 app3，如果 commit app2 的时候断电了，这笔事务数据是否还能正常提交？答案是肯定的，通过我们的 xts-server 这个恢复系统来保证事务一定会被提交/回滚。在某些特殊情况下（比如断电，jvm crash等导致分布式事务没有处理完就结束了），xts-server 靠持久化记录到 db 的事务数据来完成恢复

## 恢复系统的特点

1. 恢复系统需要配置所有参与者信息，比如参与者的名称，全类名以及提交和回滚方法名
2. 恢复系统需要连接发起方的数据库，来获取对应的事务数据
3. 恢复系统是定时恢复的，每隔一分钟从发起方的数据库获取一次数据
4. 恢复系统获取的数据都是一分钟之前待处理的数据，这个一分钟是一个经验值，我们认为 99.9999% 的分布式事务一分钟就应该结束了，事实也确实如此

# 嵌套事务支持

在前面的典型场景里是 A->B 单层调用的关系，随着业务越来越复杂，可能会出现 A->B->C 的嵌套场景，在这个场景下，A 仍然是作为事务的发起方，我们把 B 称为嵌套参与者，C 为普通参与者，如果所示

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts6.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts6.png)

可以看到，一阶段和二阶段的调用链路是完全一致的，需要注意的是对于嵌套参与者 B 来说需要 DB 资源来存放下游参与者（这里是 C）的分支事务记录，在 B 调用 C 的一阶段的时候会记录代表 C 的分支事务记录，在二阶段 XTS 框架在提交完 B 这个参与者之后，会捞取 B 的分支事务表，找到 C 的记录，从而发起对 C 的二阶段提交

## 例子

我们假设 A 系统提供一个充值服务，调用 B 系统，B 系统再调用 C 系统完成充值，对于 A 系统发起方代码看起来是这样的

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts7.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts7.png)

来看看 B 这个嵌套参与者的接口，和普通参与者没什么差别

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts8.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts8.png)

看看对应的实现

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts9.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts9.png)

可以看到对应 B 参与者来说代码里并没有什么特殊的点，那么 XTS 框架是如何做到在提交 B 的时候自动提交 C 的呢？答案就是拦截器，对于嵌套参与者我们需要配置一个特殊的拦截器NestedBusinessActionInterceptor

> 看看 NestedBusinessActionInterceptor 的配置

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts10.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts10.png)

NestedBusinessActionInterceptor 作用于 B 服务端拦截 B 的分布式服务，和 A 系统没关系

> 前面提到对于嵌套参与者需要提供 DB 资源来存储下游的分支事务记录，所以对于 B 系统也需要配置 BusinessActivityControlService 来让 XTS 框架感知 B 的 DB 信息

[![image](https://github.com/csy512889371/learnDoc/raw/master/image/2018/fbs/dts11.png)](https://github.com/csy512889371/learnDoc/blob/master/image/2018/fbs/dts11.png)

## 总结

对于嵌套参与者的使用：

1. 需要提供 DB 资源，来让 XTS 框架持久化分支事务记录
2. 配置 NestedBusinessActionInterceptor 拦截器