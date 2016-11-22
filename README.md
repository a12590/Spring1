# Spring1
## 理解Spring依赖注入的方式
## IoC是什么
> 当有了IoC/DI的容器后，在客户端类中不再主动去创建这些对象了
> 由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。
## DI是什么

# Spring配置的可选方案
	1）在XML中进行显示配置
	# dao层
		// 把当前对象加入ioc容器
		@Component("userDao")   
	> 相当于bean.xml 【<bean id=userDao class=".." />】

	# service层
	// 会从IOC容器中找userDao对象，注入到当前字段
		/*
		 * <bean id="" class=""> 
		 *	  <property name="userDao" ref="userDao" />    @Resource相当于这里的配置
		 * </bean>
		 */

		@Resource(name = "userDao")

	# action层
	 @Component("userAction")  // 加入IOC容器
	 @Resource(name = "userService")
		private UserService userService;

	  // 创建容器对象
		private ApplicationContext ac = 
			new ClassPathXmlApplicationContext("cn/itcast/e_anno/bean.xml");
	// 从容器中获取Action
			UserAction userAction = (UserAction) ac.getBean("userAction");
			userAction.execute();

	2）在Java中进行显示配置
	3）隐式的bean发现机制和自动装配(最优)
	Spring从两个角度来实现自动化装配：
	组件扫描（component scanning）:Spring会自动发现应用上下文中所创建的bean
<!-- 加了下面2局就可以直接使用spring注解 -->
<!-- 自动扫描的包名 -->
<context:component-scan base-package="com.tengj.demo" />
<!-- 默认的注解映射的支持 -->
<mvc:annotation-driven />
	自动装配（autowiring）：Spring自动满足bean之间的依赖
# Controller action
	@Controller
	public class UserController{
	    @Autowired
		UserService userService;
	}

# service
	public interface UserService {
		public void addUser() throws Exception;
	}
	@Service("userService")
	public class UserServiceImpl implements UserService{
	@Override
		public void addUser() throws Exception {
			System.out.println("新增用户");
		}
	}

# dao 这里只是测试，不写dao层
# 测试
	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration({"classpath:spring/applicationContext.xml"})
	public class UserConTrollerTest {
		@Autowired
		UserService userService;

		@Test
		public void checkServiceIsNull(){
			Assert.assertNotNull(userService);
		}
	}
	UserConTrollerTest使用了Spring的SpringJUnit4ClassRunner，以便在测试开始的时候自动创建Spring的应用上下文，注解@ContextConfiguration会告诉它需要在UserConTrollerTest中加载配置。所以读取applicationContext.xml配置文件，配置文件里面有这个启用自动扫描的元素。


 
 
 # dao
 
	// 把当前对象加入ioc容器
	//@Component("userDao")   //  相当于bean.xml 【<bean id=userDao class=".." />】

	//@Component  // 加入ioc容器的UserDao对象的引用名称， 默认与类名一样， 且第一个字母小写

	//@Repository   // 在持久层可以选择用这个注解
	@Repository(value=”userDao”)注解是告诉Spring，让Spring创建一个名字叫“userDao”的UserDao实例。
# service
	@Service   // 表示业务逻辑层的组件
	public class UserService {

	//	@Resource					//  根据类型查找 【在容器中要确保该类型只有一个变量】

		@Resource(name = "userDao")  // 根据名称查找
		private UserDao userDao;  // 去容器中招UserDao类型的变量，找到后就赋值
# Controller
	@Controller  // 控制层的组件
	public class UserAction {

		@Resource
		private UserService userService;


# 还有一点：关于创建bean的时候，多例，单例，初始化，撤销等的理解
