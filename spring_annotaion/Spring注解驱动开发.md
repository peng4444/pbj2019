# Spring注解驱动开发
[【参考视频：视频Spring注解驱动开发】](https://www.bilibili.com/video/BV1ME411o7Uu)
[【参考书籍：Spring技术内幕】]()
[【参考书籍：Spring源码深度解析】]()
## 一,组件注册
### 1.给容器中注册组件  @Configuration @Bean
```markdown
1.使用XML配置文件开发  bean.xml中对Person对象注入数据   指定bean中的id
2.使用配置类开发 @Configuration SpringConfig用person注入数据   
     bean中的id默认为配置类方法的名字,通过修改方法名或者在@Bean("person01")注解上设置id。
```
### 2.包扫描 @ComponentScan
```markdown
1.配置包扫描，只要标注了@Controller，@Service，@Repository，@Component自动扫描加入容器中。
2.@ComponentScan指定：自动扫描组件和指定扫描组件excludeFilters，includeFilters
```
### 3.指定包扫描过滤规则 TypeFilter
```markdown
excludeFilters 扫描的时候指定不包含 excludeFilters = {
                                  @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class})
includeFilters 扫描的时候指定包含 includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})},useDefaultFilters = false
@ComponentScans可以多指定多个ComponentScan规则
FilterType.ANNOTATION :按照注释 @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class})
FilterType.ASSIGNABLE_TYPE :按照给定的类型 @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookService.class})
FilterType.ASPECTJ :使用ASPECTJ表达式
FilterType.REGEX :使用正则表达式
FilterType.CUSTOM :使用自定义的过滤规则，通过自定义类MyTypeFilter定义包扫描规则。
                @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class})
```
### 4.设置组件作用域 @Scope
```markdown
 @Scope,@Scope("prototyoe") 
 singleton:单实例的（默认值）ioc容器启动会调用方法创建对象放到IOC容器中，以后每次获取就是直接从容器（map.get()）中拿。
 prototype:多实例的ioc容器启动不会去调用方法创建对象放在容器中，每次获取的时候才会调用方法创建对象。
```
### 5.懒加载 @Lazy
```markdown
单实例的（默认值）,默认在容器启动会立刻调用方法创建对象放到IOC容器中。
懒加载：容器启动不创建对象，第一次使用(获取)Bean创建对象，并初始化。(只针对单实例bean)
```
### 6.按照条件给容器中注册bean @Conditional
```markdown
@Conditional({Condition}):按照一定的条件进行判断，满足条件给容器注册bean
不仅仅可以放在方法上，还可以放在类上，类中组件统计配置 满足当前条件，这个类中配置的所有的bean注册才能生效。
```
### 78910.给容器中注册组件总结 @import 
```markdown
1）包扫描+组件标注注解（@Controller、@Service、@Repository，@Component）【自己写的类】
2）@Bean 【导入的第三方包里面的组件】
3) @import 【快速给容器中导入一个组件】
类上加注解：@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
    1.@Import(要导入到容器中的组件) id默认是组件的全类名  @Import({Color.class},{Red.class}) 导入多个组件
    2.ImportSelector：自定义类，返回需要导入的组件的全类名数组
    3.ImportBeanDefinitionRegistrar:自定义类，手动注册bean到容器中
4）使用spring提供的FactoryBean（工厂bean）
    默认获取的是工厂bean调用getObejct创建的对象
    要获取工厂bean本身，需要在id前面加一个&  &colorFactoryBean
```

## 二,生命周期
### @Bean 指定初始化和销毁 
```markdown
bean的生命周期: bean创建 -- 初始化 -- 销毁的过程
容器管理bean的生命周期
    可以自定义初始化和销毁方法，容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
        构造（对象创建）：
            单实例：在容器启动的时候创建对象
            多实例：在每次获取的时候创建对象
        BeanPostProcessor.postProcessBeforeInitialization
        初始化：
            对象创建完成，并且赋值好，，调用初始化方法。。。
        BeanPostProcessor.postProcessAfterInitialization
        销毁：
            单实例：容器关闭的时候销毁
            多实例：容器不会管理这个bean;容器不会调用销毁。
BeanPostProcessor原理：遍历得到容器中所有的BeanPostProcessor；挨个执行beforeInitialization
        一旦返回null，跳出for循环，不会执行后面的BeanPostProcess.postProcessors.
    BeanPostProcessor的大致执行流程
        populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
        initializeBean{
            applyBeanPostProcessorsBeforeInitialization//for循环得到全部beanPost
            invokeInitMethods(beanName, wrappedBean, mbd);//初始化方法
            applyBeanPostProcessorsAfterInitialization//for循环得到全部beanPost
        }
可以自定义初始化和销毁方法，容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
    1）指定初始化和销毁方法 在@Bean注解里指定init方法和destroy方法
          bean.xml 文件中 指定init-method="" destroy-method="" 或者在 @Bean注解中指定init-method="" destroy-method=""
    2） 通过让bean实现 InitializingBean(定义初始化逻辑),DisposableBean（定义销毁逻辑）接口。
    3) 可以使用JSR250；
          1.通过@POSTConstruct：在bean创建完成并且属性赋值完成，来执行初始化方法
          2.通过@PreDestory 在容器销毁bean之前通知我们进行清理工作
    4） BeanPostProcessor【interface】 bean的后置处理器
          在bean初始化前后进行一些处理工作
            postProcessBeforeInitialization： 在初始化之前工作
            对象初始化
            postProcessAfterInitialization：  在初始化之后工作
Spring底层对BeanPostProcess接口的使用
    1).bean赋值，注入其它组件，@AutoWired，生命周期注解功能 @Async xxx都是通过BeanPostProcess进行完成的
    2).ApplicationContextAwareProcessor会在实现了ApplicationContextAware接口的bean里面
    3).通过((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);将容器注入到bean中
    4).InitDestroyAnnotationBeanPostProcessor会对@PostConstructor和@PreDestroy注解进行解析来达到容器的初始化和销毁方法的执行
```

## 三,属性赋值
### @Value和@PropertySource
```markdown
使用@Value赋值
1.基本数值    @Value("daada")
2.可以写SpELl：#{}    @Value("#{20-1}")
3.可以写${};取出配置【properties】文件中的值，（在运行环境变量里面的值）
     1.需要先设置person.properties ==》person.nickName=小助手
     2.然后在bean.xml文件中配置
          <!--导入配置文件person.properties中的值-->
               <context:property-placeholder location="classpath:person.properties"></context:property-placeholder>
    3.1.方法一：使用@PropertySource读取外部配置文件中的k/v值保存的环境变量中;
          加载完成外部文件以后使用${}取出配置文件的值
          @PropertySource(value = {"classpath:/person.properties"})
          在属性上设置 @Value("${person.nickName}")
    3.2方法二：取出配置文件中的值
         ConfigurableEnvironment environment = applicationContext.getEnvironment();
         String property = environment.getProperty("person.nickName");
         System.out.println(property);
```

## 四,自动装配
```markdown
 自动装配：
      spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
  1.@Autowired:自动注入
      1)默认优先按照类型去容器中找到对应的组件 applicationContext.getBean(BookDao.class)
      2)如果找到多个相同的类型的组件，再将属性的名称作为id去容器中查找 applicationContext.getBean(BookDao)
      3）@Qualifier("bookDao")//指定需要装配的组件的id，而不是使用属性名
      4)自动装配默认一定要将属性赋值好，没有会报错
          可以使用@Autowired(required=false)指定没有的时候不报错，输出null
       5）@Primary首选装配（没有指定的时候，指定的时候指定优先）
  2.Spring还支持使用@Resource（JSR250）和@Inject（JSR330）【Java规范】
      @Resource
          类似于@Autowired一样实现自动装配，默认是按照组件名称进行装配 @Resource(name="bookDao2")可以指定装配
          没有支持@Primary功能，没有支持@Autowired(required=false)
      @Inject
          需要导入包，类似于@Autowired一样实现自动装配，支持@Primary功能，没有支持@Autowired(required=false)

   @Autowired：spring定义的， @Resource @Inject是Java规范的

  3.@Autowired可以标注的位置 构造器，参数，方法，属性
      1) 标注在方法位置：@Bean+方法参数，参数从容器中获取；默认不写 @Autowired
      2) 标注在构造器上 ：如果组件只有一个构造器，这个构造器的@Autowired可以省略，参数的位置的组件还是可以自动从容器中获取
      3) 标注在参数上

  4.自定义组件想要使用spring容器底层的一些组件（ApplicationContext，BeanFactory，XXX ）
      自定义组件实现xxxAware ;在创建对象的时候，会调用接口规定的方法注入相关的组件
      把spring底层的一些组件注入到自定义的bean中
      xxxAware：功能使用xxxPorcessor
      ApplicationContextAware , BeanNameAware, EmbeddedValueResolverAware
  5.Profile
      Spring为我们提供的可以根据当前环境，动态的激活和切换一系列的组件的功能

 开发环境、测试环境、生产环境
 数据源（/A)(/B)(/C)

 @Profile:指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册

 1、加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中，默认是default环境
 2、写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能生效
 3、没有标注环境标识的bean在任何环境下都加载

 运行时如何指定运行环境：
  1、命令行参数，通过在虚拟机参数位置指定-Dspring.profiles.active=xxx来指定运行环境，标注了该环境的bean会被配置进容器中
  2、程序内指定：
          1、创建一个applicationContext
          2、设置需要激活的环境，applicationContext.getEnvironment().setActiveProfiles("");
          3、注册主配置类，applicationContext.register(xxx.class)
          4、启动刷新容器，applicationContext.refresh();
```
## IOC小结

## AOP
### AOP：【动态代理】
```markdown
指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式；
 1、导入aop模块；Spring AOP：(spring-aspects)
 2、定义一个业务逻辑类（MathCalculator）；在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常，xxx）
 3、定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行；
 		通知方法：
 			前置通知(@Before)：logStart：在目标方法(div)运行之前运行
			后置通知(@After)：logEnd：在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
 			返回通知(@AfterReturning)：logReturn：在目标方法(div)正常返回之后运行
			异常通知(@AfterThrowing)：logException：在目标方法(div)出现异常以后运行
			环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.procced()）
4、给切面类的目标方法标注何时何地运行（通知注解）；
5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中;
6、必须告诉Spring哪个类是切面类(给切面类上加一个注解：@Aspect)
 [7]、给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
		在Spring中很多的 @EnableXXX;
 三步：
 	1）、将业务逻辑组件和切面类都加入到容器中；告诉Spring哪个是切面类（@Aspect）
	2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
 3）、开启基于注解的aop模式；@EnableAspectJAutoProxy
```
### AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
```markdown
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
```markdown
**
 * 扩展原理
 * BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作的
 * 1、BeanFactoryPostProcessor：beanFactory的后置处理器，在beanFactory标注初始化后调用，所以bean的定义已经保存加载到beanFactory，但是bean的实例还未创建
 *      1、ioc容器创建对象
 *      2、执行invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessors
 *          如何找到所有的BeanFactoryPostProcessor并执行它们的方法：
 *              1、String[] postProcessorNames =beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
 *              2、在初始化创建其它组件前面执行
 *
 * 2、BeanDefinitionRegistryPostProcessor是BeanFactoryPostProcessor的子接口，BeanDefinitionRegistry是Bean定义信息的保存中心，BeanFactory就是按照其中保存的bean的定义信息创建bean实例的
 *      postProcessBeanDefinitionRegistry()方法，在所有bean定义信息将要被加载到，但是bean实例还未创建，优先于BeanFactoryPostProcess执行，可以利用其给容器中再来添加一些组件
 * 原理：
 *      1）、ioc容器创建对象
 *      2）、执行执行invokeBeanFactoryPostProcessors(beanFactory);
 *      3）、从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
 *          1、先触发 postProcessBeanDefinitionRegistry（）方法
 *          2、再触发 postProcessBeanFactory（）方法
 *      4）、再来从容器中执行BeanFactoryPostProcessor类型的组件，然后依次触发postProcessBeanFactory（）方法
 *
 * 3、ApplicationListener:监听容器中发布的事件，事件驱动模型的开发
 *      ApplicationListener<E extends ApplicationEvent>
 *      监听ApplicationEvent及其子类的相关事件
 *   步骤：
 *      1）、写一个监听器来监听某个事件（ApplicationEvent及其子类）
 *          @EventListener(class={})可以在普通的业务逻辑组件上的方法监听事件
 *          原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener注解，它实现了EventListenerMethodProcessor接口
 *                  SmartInitializingSingleton接口的原理：单实例bean全部创建完成后
 *                  1）ioc容器创建,refresh（）;
 *                  2）finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean；
 *                      1）一顿遍历先创建所有的单实例bean；
 *                      2）获取有创建好的单实例bean，判断是否是实现了 SmartInitializingSingleton接口类型的，
 *                          如果是就调用该接口的afterSingletonsInstantiated()方法
 *      2）、把监听器加入到容器中
 *      3）、只要容器中有相关类型的事件的发布，就能监听到这个事件
 *              ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件
 *              ContextClosedEvent：关闭容器发布这个事件
 *      4）、自定义发布一个事件 ioc容器.publishEvent(ApplicationEvent);
 *
 *    原理：
 *       ContextRefreshedEvent、IOCTest_Ext$1、ContextClosedEvent
 *       1、ContextRefreshedEvent事件：
 *          1）容器创建对象：refresh（）;
 *          2）finishRefresh（）方法中调用publishEvent(new ContextRefreshedEvent(this));
 *      2、自己发布的事件 publishEvent();
 *      3、ContextClosedEvent:close方法调用doClose方法发布ContextClosedEvent事件
 *
 *       【事件发布流程】即publishEvent方法：
 *           1、获取事件的多播器：getApplicationEventMulticaster();
 *           2、调用multicastEvent(applicationEvent, eventType)派发事件
 *           3、获取到所有的ApplicationListener,即getApplicationListeners()
 *                1、如果有Executor，可以支持使用Executor进行异步派发
 *                2、否则同步的方式直接执行invokeListener(listener, event);
 *               拿到listener回调onApplicationEvent方法
 *        【事件的多播器【派发器】】
 *           1、容器创建对象：refresh（）中
 *           2、initApplicationEventMulticaster();会初始化多播器
 *                  1、先去容器中有没有id="applicationEventMulticaster"的组件
 *                  2、如果没有，new SimpleApplicationEventMulticaster(beanFactory);同时注册到容器中，我们就可以在其它组件要派发事件，自动注入这个派发器
 *        【容器中有哪些监听器】
 *           1、容器创建对象：refresh（）中
 *           2、registerListeners();
 *              从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中；
 *              String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *              //将listener注册到多播器中
 *              for (String listenerBeanName : listenerBeanNames)
 * 			        getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *
 */
```
## Spring容器创建源码
```markdown
Spring容器的refresh()【创建刷新】

1、prepareRefresh()刷新前的预处理；
​ 1）、initPropertySources();初始化一些属性设置；子类自定义个性化属性设置方法；

​ 2）、getEnvironment().validateRequiredProperties();检验属性的合法等

​ 3）、this.earlyApplicationEvents = new LinkedHashSet<>();保存容器中的一些早期事件

2、obtainFreshBeanFactory();获取BeanFactory;
​ 1)、refreshBeanFactory();刷新【创建】beanFactory

​ 在GenericApplicationContext对象构造时this.beanFactory=new DefaultListableBeanFactory();

​ 设置id

​ 2)、getBeanFactory();

​ 返回刚才GenericApplicationContext创建的BeanFactory【DefaultListableBeanFactory】对象；

3、prepareBeanFactory(beanFactory);BeanFactory的预准备工作（BeanFactory进行一些设置）
​ 1）、设置BeanFactory的类加载器、支持表达式解析器

​ 2）、添加部分BeanPostProcessor【ApplicationContextAwareProcessor】

​ 3）、设置忽略的自动装配的接口EnvironmentAware、EmbeddedValueResolverAware、xxx

​ 4）、注册可以解析的自动装配；我们能直接在任何组件中自动注入：BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext

​ 5）、添加BeanPostProcessor【ApplicationListenerDetector】

​ 6）、添加编译时的AspectJ；

​ 7）、给BeanFactory中注册一些能用的组件：

​ environment【ConfigurableEnvironment】、

​ systemProperties【Map<String,Object>】、

​ systemEnvironment【Map<String,Object>】

4、postProcessBeanFactory(beanFactory);BeanFactory准备工作完成后进行的后置处理工作；
​ 1）、子类通过重写这方法在BeanFactory创建并预准备完成以后做进一步的设置

===================以上是BeanFactory的创建及预准备工作=====================

5、invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor的方法
​ BeanFactoryPostProcessor：BeanFactory的后置处理器，在BeanFactory标注初始化之后执行

​ 两个接口：BeanFactoryPostProcessor、BeanDefinitionRegistryPostProcessor接口

​ 1）、执行BeanFactoryPostProcessor的方法：

​ 1）、获取所有BeanDefinitionRegistryPostProcessor

​ 2）、先执行实现了PriorityOrdered优先级接口的、再执行实现了Ordered的接口的、最后执行其它的

​ 3）、获取所有BeanFactoryPostProcessor

​ 2）、先执行实现了PriorityOrdered优先级接口的、再执行实现了Ordered的接口的、最后执行其它的

6、registerBeanPostProcessors(beanFactory);注册BeanPostProcessor
​ BeanPostProcessor（Bean的后置处理器）【拦截Bean的创建过程】

​ 不同类型的BeanPostProcessor，在Bean创建前后的执行时机是不一样的

​ 有如下几类： BeanPostProcessor、

​ DestructionAwareBeanPostProcessor、

​ InstantiationAwareBeanPostProcessor、

​ SmartInstantiationAwareBeanPostProcessor、

​ MergedBeanDefinitionPostProcessor【internalPostProcessors】

​ 1）、获取所有的BeanPostProcessor；

​ 后置处理器都默认可以通过PriorityOrdered、Ordered来指定优先级

​ 2）、先注册PriorityOrdered优先级接口的BeanPostProcessor

​ 把每一个BeanPostProcessor添加到BeanFactory中，

​ beanFactory.addBeanPostProcessor(postProcessor);

​ 3）、再注册了实现Ordered接口的

​ 4）、最后注册其它的

​ 5）、最终注册MergedBeanDefinitionPostProcessor类型的

​ 6）、注册一个ApplicationListenerDetector；来在Bean创建完成后检查是否是ApplicationListener

​ addApplicationListener((ApplicationListener<?>) bean);

7、initMessageSource();初始化MessageSource组件（做国际化功能；消息绑定；消息解析等功能）
​ 1）、获取BeanFactory

​ 2）、看容器中是否有id为messageSource，类型是MessageSource的组件

​ 如果有赋值给messageSource，如果没有自己创建一个DelegatingMessageSource；

​ MessageSource：取出国际化配置文件中的某个key的值；能按照区域信息获取；

​ 3）、把创建好的MessageSource注册在容器中，以后获取国际化配置文件的值的时候，可以自动注入MessageSource；调用其方法可以获得相关配置属性

​ beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);

8、initApplicationEventMulticaster();初始化事件派发器【多播器】
​ 1）、获取BeanFactory

​ 2）、从BeanFactory获取applicationEventMulticaster的组件

​ 3）、如果上一步没有配置；创建一个SimpleApplicationEventMulticaster

​ 4）、将创建的ApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入

9、onRefresh();留给子容器（子类）
​ 1）、子类重写这个方法，在容器刷新的时候可以自定义逻辑；

10、registerListeners();给容器中将所有项目里面的ApplicationListener注册进来
​ 1）、从容器中拿到所有ApplicationListener组件

​ 2）、将每个监听器添加到事件派发器中

​ getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);

​ 3）、派发之前步骤产生的事件；

11、finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例bean
​ 1）、beanFactory.preInstantiateSingletons();初始化剩下的单实例bean

​ 1）、获取容器中的所有Bean，依次进行初始化和创建对象

​ List beanNames = new ArrayList<>(this.beanDefinitionNames);

​ 2）、遍历beanNames获取bean 的定义信息；

​ RootBanDefinition bd = getMergedLocalBeanDefinition(beanName);

​ 3）、Bean不是抽象的，是单实例的，是懒加载；

​ 1）、判断是否是FactoryBean；是否是实现FactoryBean接口的Bean；

​ 1）、如果是，利用工厂方法创建对象

​ 2）、不是工厂Bean，利用getBean（过程如下）（beanName）；创建对象

​ 4）、所有Bean都利用getBean创建完成以后；

​ 检查所有的Bean是否是SmartInitializingSingleton接口类型的，如果是就执行 afterSingletonsInstantiated()方法;

12、finishRefresh();完成BeanFactory初始化创建工作；IOC容器就创建完成
​ 1）、initLifecycleProcessor();初始化声明周期有关的后置处理器

​ 允许我们写一个LifecycleProcessor的实现类，可以在BeanFactory进行到特定生命周期时进行调用

​ 默认从容器中找是否有LifeCycleProcessor的组件，如果没有，默认会创建一个

​ new DefaultLifecycleProcessor();然后加入到容器中

​ 2）、getLifecycleProcessor().onRefresh();拿到所有前面定义的生命周期处理器回调onRefresh()方法

​ 3）、publishEvent(new ContextRefreshedEvent(this));发布容器刷新完成事件

​ 4）、LiveBeansView.registerApplicationContext(this);

getBean
getBean(beanName);ioc.getBean;
	1、AbstractBeanFactory.doGetBean();
	2、先获取缓存中保存的单实例bean，如果能获取到，说明这Bean之前被创建过（所有创建过的单实例Bean都会被缓存起来）从singletonObjects=new ConcurrentHashMap<String,Object>中获取到
	3、缓存中获取不到，开始Bean的创建对象流程；
	4、标记当前Bean已经被创建，markBeanAsCreated(beanName);
	5、获取Bean的定义信息
	6、获取当前Bean依赖的其它Bean；如果有，按照getBean（）把依赖的Bean先创建出来
	7、启动单实例Bean的创建流程
		1、createBean(beanName,mbd,args);
			/**
			*先让BeanPostProcessor【InstantiationAwareBeanPostProcessor】先拦截返回代理对象
			*先触发所有该接口的postProcessBeforeInstantiation()方法，如果有返回对象，调用					*applyBeanPostProcessorsAfterInitialization()，即会执行所有的BeanPostProcessor的			   *postProcessAfterInitialization()方法，将bean返回
			*/
			1、Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
			2、如果没有返回bean，调用doCreateBean();
				Object beanInstance = doCreateBean(beanName, mbdToUse, args);
				//利用工厂方法或者对象的构造器等创建bean实例
                1、createBeanInstance(beanName, mbd, args);
				//调用MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition()
				2、applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
				//为bean的属性赋值
				3、populateBean(beanName, mbd, instanceWrapper);
					1、拿到InstantiationAwareBeanPostProcessor类型的后置处理器
					   执行postProcessAfterInstantiation();
					2、拿到InstantiationAwareBeanPostProcessor类型的后置处理器
						执行postProcessProperties();
					//应用Bean属性的值，为属性利用setter方法等进行赋值
					3、applyPropertyValues(beanName, mbd, bw, pvs);
				//【Bean初始化】
				4、initializeBean(beanName, exposedObject, mbd);
					//执行xxxAware接口的方法
					1、invokeAwareMethods(beanName, bean);
					   BeanNameAware\BeanClassLoaderAware\BeanFactoryAware
					//执行后置处理器在初始化之前
					2、applyBeanPostProcessorsBeforeInitialization();
					   BeanPostProcessor.postProcessBeforeInitialization();
					//执行初始化方法
					3、invokeInitMethods();
						1、判断是否是InitializingBean接口的实现；执行接口规定的初始化
						2、是否自定义初始化方法
					//执行后置处理器在初始化之后
					4、applyBeanPostProcessorsAfterInitialization();
				//注册Bean的销毁方法
				5、registerDisposableBeanIfNecessary(beanName, bean, mbd);
			//Bean的实例创建完成，将bean添加到缓存中
			3、addSingleton(beanName, singletonObject);
			ioc容器就是这些Map;很多Map里面保存了单实例bean，环境信息。。。
```
## Spring容器创建总结
```markdown
1、Spring容器在启动的时候，先会保存所有注册进来的Bean的定义信息
​ 1、xml注册bean
​ 2、使用注解；@Service、@Bean、@Component、...
2、Spring容器会在合适的时机创建这些Bean
​ 1、用到这个bean的时候，利用getBean方法创建bean，创建好以后保存在容器中
​ 2、统一创建剩下所有bean的时候，即finishBeanFactoryInitialize();
3、后置处理器
​ 1、每一个bean创建完成，都会使用各种后置处理器进行处理，来增强bean 的功能
​ AutowiredAnnotationBeanPostProcessor会处理自动注入功能
​ AnnotationAwareAspectJAutoProxyCreator来做AOP功能；
​ xxx
4、事件驱动模型：
​ ApplicationListener：事件监听
​ ApplicationEventMulticaster：事件派发：
```
## Web
### Servlet3.0
```markdown
Shared libraries（共享库）/runtimes pluggability（运行时插件能力）
1、Servlet容器启动会扫描，当前应用里面每一个jar包的ServletContainerInitializer的实现
2、提供ServletContainerInitializer的实现类，必须绑定在
META-INF/services/javax.servlet.ServletContainerInitializer
​ 文件的内容就是ServletContainerInitializer实现类的全类名
总结：容器在启动应用的时候，会扫描当前应用每一个jar包里面
META-INF/services/javax.servlet.ServletContainerInitializer
指定的实现类。启动并运行这个实现类的方法,可以传入感兴趣的类型
//容器启动的时候会将@HandlesTypes指定的这个类型下面的子类（实现类，子接口）传递过来给Set<Class<?>>参数
@HandlesTypes(WebApplicationInitializer.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {
     * 应用启动的时候，会运行onStartup方法
     * ServletContext　代表当前web应用的ServletContext，一个Web应用对应一个
     * Set<Class<?>> 感兴趣类型的所有子类型
     *      可使用ServletContext注册Web组件（Servlet、Filter、Listener）
     *      使用编码的方式，再项目启动的时候给ServletContext里面添加组件
     *          必须在项目启动的时候添加
     *          1）ServletContainerInitializer得到的ServletContext
     *          2）ServletContextListener得到的ServletContext
     */
    @Override
    public void onStartup(Set<Class<?>> args0, ServletContext sc){
    }
}
异步请求
在Servlet3.0之前，是由一个线程来接受请求，并进行业务处理再返回的。
```
### SpringMVC与servlet3.0
```markdown
注解配置
1、web容器启动的时候，会扫描每个jar包下的META-INF/services/javax.servlet.ServletContainerInitializer
2、加载这个文件指定的SpringServletContainerInitializer
3、spring的应用一启动会加载感兴趣的WebApplicationInitializer接口下的所有组件
4、并且为WebApplicationInitializer组件创建对象（组件不是接口和抽象类）
​ 1、AbstractContextLoaderInitializer；创建根容器createRootApplicationContext();
​ 2、AbstractDispatcherServletInitializer：
​ 创建一个web的ioc容器createServletApplicationContext();
​ 创建一个DispatcherServlet：createDispatcherServlet(servletAppContext);
​ 将创建的DispatcherServlet添加到ServletContext中：
​ servletContext.addServlet(servletName, dispatcherServlet);
​ 3、AbstractAnnotationConfigDispatcherServletInitializer：注解方式配置的DispatcherServlet初始化器

​ 创建根容器createRootApplicationContext()；

​ getRootConfigClasses();传入一个配置类

​ 创建web的ioc容器：createServletApplicationContext()；

​ getServletConfigClasses();获取配置类

总结：以注解方式来启动SpringMVC，继承AbstractAnnotationConfigDispatcherServletInitializer，实现抽象方法指定DispatcherServlet的配置信息

@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    @Override
	public void onStartup(@Nullable Set<Class<?>> webAppInitializerClasses, ServletContext servletContext)
			throws ServletException {

		List<WebApplicationInitializer> initializers = new LinkedList<>();

		if (webAppInitializerClasses != null) {
			for (Class<?> waiClass : webAppInitializerClasses) {
				// Be defensive: Some servlet containers provide us with invalid classes,
				// no matter what @HandlesTypes says...
				if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
						WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
					try {
						initializers.add((WebApplicationInitializer)
								ReflectionUtils.accessibleConstructor(waiClass).newInstance());
					}
					catch (Throwable ex) {
						throw new ServletException("Failed to instantiate WebApplicationInitializer class", ex);
					}
				}
			}
		}

		if (initializers.isEmpty()) {
			servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
			return;
		}

		servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
		AnnotationAwareOrderComparator.sort(initializers);
		for (WebApplicationInitializer initializer : initializers) {
			initializer.onStartup(servletContext);
		}
	}
//在web容器启动的时候创建对象，调用方法来初始化容器以及前端控制器
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    //获取根容器的配置类：（Spring的配置文件）父容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    //获取web容器的配置类（SpringMVC配置文件）子容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    //获取DispatcherServlet的映射信息
    // /:拦截所有请求（包括静态资源，不包括.jsp）
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
定制SpringMVC：

@Configuration
@EnableWebMvc//接管springmvc
public class WebConfig implements WebMvcConfigurer {
	配置组件（视图解析器、视图映射、静态资源映射、拦截器）
    // Implement configuration methods...
}
异步请求
Callable

@PostMapping
public Callable<String> processUpload(final MultipartFile file) {

    return new Callable<String>() {
        public String call() throws Exception {
            // ...
            return "someView";
        }
    };
}
1、SpringMVC异步处理，将Callable提交到TaskExecutor使用一个隔离的线程进行处理

2、DispatcherServlet和所有的Filter退出web容器线程，response仍保持打开状态

3、最终Callable返回一个结果，SpringMVC将请求返回值派发给Servlet容器，进行处理

4、根据Callable返回的结果。SpringMVC继续进行视图渲染流程等

DeferredResult

@GetMapping("/quotes")
@ResponseBody
public DeferredResult<String> quotes() {
    DeferredResult<String> deferredResult = new DeferredResult<String>();
    // Save the deferredResult somewhere..
    return deferredResult;
}
// From some other thread...
deferredResult.setResult(result)
```