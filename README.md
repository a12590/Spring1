# Spring1
理解Spring依赖注入的方式
——————————————————————————————————————————————————————————————
1，注解方式
dao层(试试换了位置的commit效果)
// 把当前对象加入ioc容器
@Component("userDao")   //  相当于bean.xml 【<bean id=userDao class=".." />】
service层
// 会从IOC容器中找userDao对象，注入到当前字段
	/*
	 * <bean id="" class=""> 
	 *	  <property name="userDao" ref="userDao" />    @Resource相当于这里的配置
	 * </bean>
	 */
	
	@Resource(name = "userDao")
  
 action层
 @Component("userAction")  // 加入IOC容器
 @Resource(name = "userService")
	private UserService userService;
  
  // 创建容器对象
	private ApplicationContext ac = 
		new ClassPathXmlApplicationContext("cn/itcast/e_anno/bean.xml");
// 从容器中获取Action
		UserAction userAction = (UserAction) ac.getBean("userAction");
		userAction.execute();
 这个流程————————————————————————————————————————————————————
 
 
 1dao
 
// 把当前对象加入ioc容器
//@Component("userDao")   //  相当于bean.xml 【<bean id=userDao class=".." />】

//@Component  // 加入ioc容器的UserDao对象的引用名称， 默认与类名一样， 且第一个字母小写

//@Repository   // 在持久层可以选择用这个注解
2service
@Service   // 表示业务逻辑层的组件
public class UserService {
	
//	@Resource					//  根据类型查找 【在容器中要确保该类型只有一个变量】
	
	@Resource(name = "userDao")  // 根据名称查找
	private UserDao userDao;  // 去容器中招UserDao类型的变量，找到后就赋值
3，Controller
@Controller  // 控制层的组件
public class UserAction {

	@Resource
	private UserService userService;
这是进一步改进的注解方式——————————————————————————————

还有一点：关于创建bean的时候，多例，单例，初始化，撤销等的理解
