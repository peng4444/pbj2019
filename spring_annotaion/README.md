# Spring注解驱动开发
[【参考视频：视频Spring注解驱动开发】]()
[【参考书籍：Spring技术内幕】]()
[【参考书籍：Spring源码深度解析】]()
## 组件注册
### 1.@Configuration @Bean
```java
    //给容器中注册组件 
    // 1.使用XML配置文件开发  bean.xml Person注入数据
    // 2.使用配置类开发 @Configuration     MainConfig Person注入数据   
```
### 2.@ComponentScan
```java
    //自动扫描组件和指定扫描组件excludeFilters，includeFilters
```
### 3.TypeFilter
```java
    //指定过滤规则
    //type = FilterType.ANNOTATION:按照注解指定过滤规则
    //type = FilterType.ASSIGNABLE_TYPE:按照给定的类型指定过滤规则
    //type = FilterType.ASPECTJ 使用ASPECTJ表达式
    //type = FilterType.REGEX 使用正则表达式
    //type = FilterType.CUSTOM 使用自定义规则
```
### 4.Scope
```java
    //设置组件作用域
    // @Scope("prototyoe")  
    // singleton:单实例的（默认值）ioc容器启动会调用方法创建对象放到IOC容器中
                       // 以后每次获取就是直接从容器（map.get()）中拿，
    // prototype:多实例的 ioc容器启动不会去调用方法创建对象放在容器中，
                       // 每次获取的时候才会调用方法创建对象
```
### 5.Lazy
```java
    // 懒加载，只有在单实例中，在调用对象的时候才加载对象
    ///单实例的（默认值）,ioc容器启动会立刻调用方法创建对象放到IOC容器中
```
### 6.Conditional
```markdown
    按照一定的条件进行判断，满足条件给容器中注册bean
    不仅仅可以放在方法上，还可以放在类上，类中组件统计配置 满足当前条件，这个类中配置的所有的bean注册才能生效
```
### 78910 import 
```java
    /**
         * 给容器中注册组件总结
         * 1）包扫描+组件标注注解（@Controller、@Service、@Repository，@Component）【自己写的类】
         * 2）@Bean 【导入的第三方包里面的组件】
         * 3) import 【快速给容器中导入一个组件】
         *      @Import(要导入到容器中的组件) id默认是组件的全类名  @Import({Color.class},{Red.class}) 导入多个组件
         *      ImportSelector ：返回需要导入的组件的全类名数组
         *      ImportBeanDefinitionRegistrar:手动注册bean到容器中
         * 4）使用spring提供的FactoryBean（工厂bean）
         *      默认获取的是工厂bean调用getObejct创建的对象
         *      要获取工厂bean本身，需要在id前面加一个&  &colorFactoryBean
         */
```
## 生命周期
### 11121314151617 
```java
    /**
     * bean的生命周期
     *      bean创建 -- 初始化 -- 销毁的过程
     * 容器管理bean的生命周期
     *  我们可以自定义初始化和销毁方法，容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
     *
     *  构造（对象创建）
     *      单实例：在容器启动的时候创建对象
     *      多实例：在每次获取的时候创建对象
     *   BeanPostProcessor.postProcessBeforeInitialization
     *  初始化：
     *      对象创建完成，并且赋值好，，调用初始化方法。。。
     *  BeanPostProcessor.postProcessAfterInitialization
     *  销毁：
     *      单实例：容器关闭的时候销毁
     *      多实例：容器不会管理这个bean;容器不会调用销毁
     *
     *      1）指定初始化和销毁方法
     *          bean.xml 文件中 init-method="" destroy-method="" 或者在 @Bean注解中指定init-method="" destroy-method=""
     *      2） 通过让bean实现 InitializingBean(定义初始化逻辑)
     *                        DisposableBean（定义销毁逻辑）
     *      3) 可以使用JSR250；
     *           通过@POSTConstruct：在bean创建完成并且属性赋值完成，来执行初始化方法
     *           通过@PreDestory 在容器销毁bean之前通知我们进行清理工作
     *      4） BeanPostProcessor【interface】 bean 的后置处理器
     *           在bean初始化前后进行一些处理工作
     *           postProcessBeforeInitialization： 在初始化之前工作
     *           postProcessAfterInitialization：  在初始化之后工作
     *
     * Spring底层对于BeanPostProcessor使用
     */
```
## 属性赋值
### 18@Value和19@PropertySource
```java
    /**
         * 使用@Value赋值
         * 1.基本数值    @Value("daada")
         * 2.可以写SpELl：#{}    @Value("#{20-1}")
         * 3.可以写${};取出配置【properties】文件中的值，（在运行环境变量里面的值）
         *      1.需要先设置person.properties ==》person.nickName=小助手
         *      2.然后在bean.xml文件中配置
         *          <!--导入配置文件person.properties中的值-->
                    <context:property-placeholder location="classpath:person.properties"></context:property-placeholder>
                3.1.方法一：使用@PropertySource读取外部配置文件中的k/v值保存的环境变量中;
                            加载完成外部文件以后使用${}取出配置文件的值
                  @PropertySource(value = {"classpath:/person.properties"})
                  在属性上设置 @Value("${person.nickName}")
                3.2方法二：取出配置文件中的值
                           ConfigurableEnvironment environment = applicationContext.getEnvironment();
                           String property = environment.getProperty("person.nickName");
                           System.out.println(property);
         */
```
## 自动装配
```java
        
/**
 * 自动装配：
 *      spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
 *  1.@Autowired:自动注入
 *      1)默认优先按照类型去容器中找到对应的组件 applicationContext.getBean(BookDao.class)
 *      2)如果找到多个相同的类型的组件，再将属性的名称作为id去容器中查找 applicationContext.getBean(BookDao)
 *      3）@Qualifier("bookDao")//指定需要装配的组件的id，而不是使用属性名
 *      4)自动装配默认一定要将属性赋值好，没有会报错
 *          可以使用@Autowired(required=false)指定没有的时候不报错，输出null
 *       5）@Primary首选装配（没有指定的时候，指定的时候指定优先）
 *  2.Spring还支持使用@Resource（JSR250）和@Inject（JSR330）【Java规范】
 *      @Resource
 *          类似于@Autowired一样实现自动装配，默认是按照组件名称进行装配 @Resource(name="bookDao2")可以指定装配
 *          没有支持@Primary功能，没有支持@Autowired(required=false)
 *      @Inject
 *          需要导入包，类似于@Autowired一样实现自动装配，支持@Primary功能，没有支持@Autowired(required=false)
 *
 *   @Autowired：spring定义的， @Resource @Inject是Java规范的
 *
 *  3.@Autowired可以标注的位置 构造器，参数，方法，属性
 *      1) 标注在方法位置：@Bean+方法参数，参数从容器中获取；默认不写 @Autowired
 *      2) 标注在构造器上 ：如果组件只有一个构造器，这个构造器的@Autowired可以省略，参数的位置的组件还是可以自动从容器中获取
 *      3) 标注在参数上
 *
 *  4.自定义组件想要使用spring容器底层的一些组件（ApplicationContext，BeanFactory，XXX ）
 *      自定义组件实现xxxAware ;在创建对象的时候，会调用接口规定的方法注入相关的组件
 *      把spring底层的一些组件注入到自定义的bean中
 *      xxxAware：功能使用xxxPorcessor
 *      ApplicationContextAware , BeanNameAware, EmbeddedValueResolverAware
 *  5.Profile
 *      Spring为我们提供的可以根据当前环境，动态的激活和切换一系列的组件的功能
 *      指定组件在哪个环境的情况下才能注册到容器中，不指定，如何环境都能够注册这个组件
 *      加了环境标识的bean，只有在这个环境被激活的时候才能注册到容器中；默认是default环境
 *      写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效
 *      没有标注环境标识的bean在，任何环境下都是加载的；
 */
```
## IOC小结

## AOP
```markdown
AOP：【动态代理】
 *  * 		指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式；
 *  *
 *  * 1、导入aop模块；Spring AOP：(spring-aspects)
 *  * 2、定义一个业务逻辑类（MathCalculator）；在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常，xxx）
 *  * 3、定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行；
 *  * 		通知方法：
 *  * 			前置通知(@Before)：logStart：在目标方法(div)运行之前运行
 *  * 			后置通知(@After)：logEnd：在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
 *  * 			返回通知(@AfterReturning)：logReturn：在目标方法(div)正常返回之后运行
 *  * 			异常通知(@AfterThrowing)：logException：在目标方法(div)出现异常以后运行
 *  * 			环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.procced()）
 *  * 4、给切面类的目标方法标注何时何地运行（通知注解）；
 *  * 5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中;
 *  * 6、必须告诉Spring哪个类是切面类(给切面类上加一个注解：@Aspect)
 *  * [7]、给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
 *  * 		在Spring中很多的 @EnableXXX;
 *  *
 *  * 三步：
 *  * 	1）、将业务逻辑组件和切面类都加入到容器中；告诉Spring哪个是切面类（@Aspect）
 *  * 	2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
 *  *  3）、开启基于注解的aop模式；@EnableAspectJAutoProxy
 *
 *  AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
 *  * 		@EnableAspectJAutoProxy；
 *  * 1、@EnableAspectJAutoProxy是什么？
 *  * 		@Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar
 *  * 			利用AspectJAutoProxyRegistrar自定义给容器中注册bean；BeanDefinetion
 *  * 			internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *  *
 *  * 		给容器中注册一个AnnotationAwareAspectJAutoProxyCreator；
 *   * 2、 AnnotationAwareAspectJAutoProxyCreator：
 *  * 		AnnotationAwareAspectJAutoProxyCreator
 *  * 			->AspectJAwareAdvisorAutoProxyCreator
 *  * 				->AbstractAdvisorAutoProxyCreator
 *  * 					->AbstractAutoProxyCreator
 *  * 							implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *  * 						关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 *  *
 *  * AbstractAutoProxyCreator.setBeanFactory()
 *  * AbstractAutoProxyCreator.有后置处理器的逻辑；
 *  *
 *  * AbstractAdvisorAutoProxyCreator.setBeanFactory()-》initBeanFactory()
 *  *
 *  * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *   * 流程：
 *  * 		1）、传入配置类，创建ioc容器
 *  * 		2）、注册配置类，调用refresh（）刷新容器；
 *  * 		3）、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建；
 *  * 			1）、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *  * 			2）、给容器中加别的BeanPostProcessor
 *  * 			3）、优先注册实现了PriorityOrdered接口的BeanPostProcessor；
 *  * 			4）、再给容器中注册实现了Ordered接口的BeanPostProcessor；
 *  * 			5）、注册没实现优先级接口的BeanPostProcessor；
 *  * 			6）、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中；
 *  * 				创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
 *  * 				1）、创建Bean的实例
 *  * 				2）、populateBean；给bean的各种属性赋值
 *  * 				3）、initializeBean：初始化bean；
 *  * 						1）、invokeAwareMethods()：处理Aware接口的方法回调
 *  * 						2）、applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的postProcessBeforeInitialization（）
 *  * 						3）、invokeInitMethods()；执行自定义的初始化方法
 *  * 						4）、applyBeanPostProcessorsAfterInitialization()；执行后置处理器的postProcessAfterInitialization（）；
 *  * 				4）、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功；--》aspectJAdvisorsBuilder
 *  * 			7）、把BeanPostProcessor注册到BeanFactory中；
 *  * 				beanFactory.addBeanPostProcessor(postProcessor);
 *  * =======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程========
 *  *
 *  * 			AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 *  * 		4）、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作；创建剩下的单实例bean
 *  * 			1）、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
 *  * 				getBean->doGetBean()->getSingleton()->
 *  * 			2）、创建bean
 *  * 				【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation()】
 *  * 				1）、先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建；
 *  * 					只要创建好的Bean都会被缓存起来
 *  * 				2）、createBean（）;创建bean；
 *  * 					AnnotationAwareAspectJAutoProxyCreator 会在任何bean创建之前先尝试返回bean的实例
 *  * 					【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 *  * 					【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 *  * 					1）、resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation
 *  * 						希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续
 *  * 						1）、后置处理器先尝试返回对象；
 *  * 							bean = applyBeanPostProcessorsBeforeInstantiation（）：
 *  * 								拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor;
 *  * 								就执行postProcessBeforeInstantiation
 *  * 							if (bean != null) {
 * 								bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                                                        }
 *  *
 *  * 					2）、doCreateBean(beanName, mbdToUse, args);真正的去创建一个bean实例；和3.6流程一样；
 *  * 					3）、
 *  *
 *  *
 *  * AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】	的作用：
 *  * 1）、每一个bean创建之前，调用postProcessBeforeInstantiation()；
 *  * 		关心MathCalculator和LogAspect的创建
 *  * 		1）、判断当前bean是否在advisedBeans中（保存了所有需要增强bean）
 *  * 		2）、判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean，
 *  * 			或者是否是切面（@Aspect）
 *  * 		3）、是否需要跳过
 *  * 			1）、获取候选的增强器（切面里面的通知方法）【List<Advisor> candidateAdvisors】
 *  * 				每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor；
 *  * 				判断每一个增强器是否是 AspectJPointcutAdvisor 类型的；返回true
 *  * 			2）、永远返回false
 *  *
 *  * 2）、创建对象
 *  * postProcessAfterInitialization；
 *  * 		return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
 *  * 		1）、获取当前bean的所有增强器（通知方法）  Object[]  specificInterceptors
 *  * 			1、找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）
 *  * 			2、获取到能在bean使用的增强器。
 *  * 			3、给增强器排序
 *  * 		2）、保存当前bean在advisedBeans中；
 *  * 		3）、如果当前bean需要增强，创建当前bean的代理对象；
 *  * 			1）、获取所有增强器（通知方法）
 *  * 			2）、保存到proxyFactory
 *  * 			3）、创建代理对象：Spring自动决定
 *  * 				JdkDynamicAopProxy(config);jdk动态代理；
 *  * 				ObjenesisCglibAopProxy(config);cglib的动态代理；
 *  * 		4）、给容器中返回当前组件使用cglib增强了的代理对象；
 *  * 		5）、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程；
 *  *
 *  *
 *  * 	3）、目标方法执行	；
 *  * 		容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象，xxx）；
 *  * 		1）、CglibAopProxy.intercept();拦截目标方法的执行
 *  * 		2）、根据ProxyFactory对象获取将要执行的目标方法拦截器链；
 *  * 			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *  * 			1）、List<Object> interceptorList保存所有拦截器 5
 *  * 				一个默认的ExposeInvocationInterceptor 和 4个增强器；
 *  * 			2）、遍历所有的增强器，将其转为Interceptor；
 *  * 				registry.getInterceptors(advisor);
 *  * 			3）、将增强器转为List<MethodInterceptor>；
 *  * 				如果是MethodInterceptor，直接加入到集合中
 *  * 				如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor；
 *  * 				转换完成返回MethodInterceptor数组；
 *  *
 *  * 		3）、如果没有拦截器链，直接执行目标方法;
 *  * 			拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
 *  * 		4）、如果有拦截器链，把需要执行的目标对象，目标方法，
 *  * 			拦截器链等信息传入创建一个 CglibMethodInvocation 对象，
 *  * 			并调用 Object retVal =  mi.proceed();
 *  * 		5）、拦截器链的触发过程;
 *  * 			1)、如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）执行目标方法；
 *  * 			2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行；
 *  * 				拦截器链的机制，保证通知方法与目标方法的执行顺序；
 *  *
 *  * 	总结：
 *  * 		1）、  @EnableAspectJAutoProxy注解 开启AOP功能
 *  * 		2）、 @EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 *  * 		3）、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器；
 *  * 		4）、容器的创建流程：
 *  * 			1）、registerBeanPostProcessors（）注册后置处理器；创建AnnotationAwareAspectJAutoProxyCreator对象
 *  * 			2）、finishBeanFactoryInitialization（）初始化剩下的单实例bean
 *  * 				1）、创建业务逻辑组件和切面组件
 *  * 				2）、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 *  * 				3）、组件创建完之后，判断组件是否需要增强
 *  * 					是：切面的通知方法，包装成增强器（Advisor）;给业务逻辑组件创建一个代理对象（cglib）；
 *  * 		5）、执行目标方法：
 *  * 			1）、代理对象执行目标方法
 *  * 			2）、CglibAopProxy.intercept()；
 *  * 				1）、得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 *  * 				2）、利用拦截器的链式机制，依次进入每一个拦截器进行执行；
 *  * 				3）、效果：
 *  * 					正常执行：前置通知-》目标方法-》后置通知-》返回通知
 *  * 					出现异常：前置通知-》目标方法-》后置通知-》异常通知
```
## 声明式事务
```markdown
 * Spring事务控制
 *  1.导入相关依赖
 *      数据源，数据库驱动，Spring-jdbc模块
 *  2.配置数据源，JdbcTemplate(Spring提供的简化数据库操作的工具)操作数据
 *   3、给方法上标注 @Transactional 表示当前方法是一个事务方法；
 *  * 4、 @EnableTransactionManagement 开启基于注解的事务管理功能；
 *  * 		@EnableXXX
 *  * 5、配置事务管理器来控制事务;
 *  * 		@Bean
 *  * 		public PlatformTransactionManager transactionManager()
 *  *
 *  *
 *  * 原理：
 *  * 1）、@EnableTransactionManagement
 *  * 			利用TransactionManagementConfigurationSelector给容器中会导入组件
 *  * 			导入两个组件
 *  * 			AutoProxyRegistrar
 *  * 			ProxyTransactionManagementConfiguration
 *  * 2）、AutoProxyRegistrar：
 *  * 			给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；
 *  * 			InfrastructureAdvisorAutoProxyCreator：？
 *  * 			利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；
 *  *
 *  * 3）、ProxyTransactionManagementConfiguration 做了什么？
 *  * 			1、给容器中注册事务增强器；
 *  * 				1）、事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
 *  * 				2）、事务拦截器：
 *  * 					TransactionInterceptor；保存了事务属性信息，事务管理器；
 *  * 					他是一个 MethodInterceptor；
 *  * 					在目标方法执行的时候；
 *  * 						执行拦截器链；
 *  * 						事务拦截器：
 *  * 							1）、先获取事务相关的属性
 *  * 							2）、再获取PlatformTransactionManager，如果事先没有添加指定任何transactionmanger
 *  * 								最终会从容器中按照类型获取一个PlatformTransactionManager；
 *  * 							3）、执行目标方法
 *  * 								如果异常，获取到事务管理器，利用事务管理回滚操作；
 *  * 								如果正常，利用事务管理器，提交事务
```
## 扩展原理

## Spring容器创建源码

## Servlet3.0


## SpringMVC